package roguelike.domain;

import roguelike.util.Util;
import roguelike.util.Pair;
import roguelike.util.Logger;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Level {
    public int number;
    private char[][] levelData;
    public Player player;
    public ArrayList<Zombie> zombies;
    private Runnable onLevelExitFunc;

    private char[][] initialLevelData;

    /**
     * Creates a new level object
     * @param levelData Character data of the level
     * @param number The number of this level
     */
    public Level(char[][] levelData, int number) {
        this.levelData = levelData;
        this.zombies = new ArrayList<>();
        this.onLevelExitFunc = null;
        this.number = number;

        // Copy level to a backup object for level resetting
        this.initialLevelData = new char[levelData.length][levelData[0].length];
        for (int y = 0; y < levelData.length; y++) {
            for (int x = 0; x < levelData[0].length; x++) {
                this.initialLevelData[y][x] = levelData[y][x];
            }
        }
        
        this.resetLevel();
    }

    /**
     * Resets the level to its initial state
     */
    public void resetLevel() {
        this.player = null;
        this.zombies.clear();

        // Copy values from backup object
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                this.levelData[y][x] = this.initialLevelData[y][x];
            }
        }
        
        // Find units on the map and create them
        for (int y = 0; y < this.levelData.length; y++) {
            for (int x = 0; x < this.levelData[0].length; x++) {
                char tile = this.levelData[y][x];

                if (tile == '@' && this.player == null) {
                    this.player = new Player(y, x, "Player", this);
                } else if (tile == 'Z') {
                    this.zombies.add(new Zombie(y, x, "xRay Zombie " + Integer.toString(this.zombies.size() + 1), this));
                }
            }
        }
    }

    /**
     * Moves all enemies in the level
     */
    public void moveEnemies() {
        for (int i = this.zombies.size() - 1; i >= 0; i--) {
            this.zombies.get(i).calculateMove();
        }
    }

    /**
     * Detonates a bomb
     * @param y Y coordinate of the bomb
     * @param x X coordinate of the bomb
     * @param b The bomb that should be detonated
     * @param detonator The unit that detonated this bomb
     */
    public void detonateBomb(int y, int x, Bomb b, Unit detonator) {
        if (detonator == null) {
            return;
        }

        Logger.log(detonator.name + " detonated a bomb!");

        for (int i = y - b.blastRadius; i <= y + b.blastRadius; i++) {
            for (int j = x - b.blastRadius; j <= x + b.blastRadius; j++) {
                if ((i == y && j == x) || isOutOfBounds(i, j) ||
                    Util.totalDistanceBetweenCoordinates(y, x, i, j) > b.blastRadius) {
                    continue;
                }

                handleBombTile(i, j, b, detonator);
            }
        }
    }

    /**
     * Handles a tile that is being detonated by a bomb
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @param b The bomb that is being detonated
     * @param detonator The unit that detonated the bomb
     */
    private void handleBombTile(int y, int x, Bomb b, Unit detonator) {
        char tile = this.levelData[y][x];

        if (tile == '#' || tile == 'B') {
            if (y == 0 || x == 0 || y == getHeight() - 1 || x == getWidth() - 1) {
                setTile(y, x, ' ');
            } else {
                setTile(y, x, '.');
            }
        } else if (tile == 'Z' || tile == '@') {
            Unit u = findUnitAtLocation(y, x);
            u.takeHit(b.blastDamage);
        }
    }

    /**
     * Removes a unit from the level
     * @param u The unit that should be removed
     */
    public void removeUnit(Unit u) {
        if (u == null) {
            return;
        }

        if (this.player != null && this.player.y == u.y && this.player.x == u.x) {
            killPlayer();
        } else {
            int idx = 0;
            for (Zombie z : this.zombies) {
                if (z.y == u.y && z.x == u.x) {
                    killZombie(idx, z);
                    break;
                }
                
                idx++;
            }
        }
    }

    /**
     * Finds a unit at a designated location
     * @param y Y coordinate of the location
     * @param x X coordinate of the location
     * @return Unit at the location or null if there was none
     */
    public Unit findUnitAtLocation(int y, int x) {
        if (this.player.y == y && this.player.x == x) {
            return this.player;
        }

        for (Zombie z : this.zombies) {
            if (z.y == y && z.x == x) {
                return z;
            }
        }

        return null;
    }

    /**
     * Kills and removes the player from the level
     */
    private void killPlayer() {
        if (this.player != null) {
            Logger.log("Game Over. Press an arrow key to exit...");
            this.setTile(this.player.y, this.player.x, '.');
            this.player = null;
        }
    }

    /**
     * Kills and removes a zombie from the level
     * @param idx The index of the zombie
     * @param z The Zombie that should be removed
     */
    private void killZombie(int idx, Zombie z) {
        if (z != null) {
            if (this.player != null) {
                Logger.log("Player has " + this.player.health + " health left...");
            }
            
            this.setTile(z.y, z.x, '.');
            this.zombies.remove(idx);
        }
    }

    /**
     * Sets a tile in the map to a new character
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @param c The character that the tile should be set to
     */
    public void setTile(int y, int x, char c) {
        if (isOutOfBounds(y, x)) {
            return;
        }

        this.levelData[y][x] = c;
    }

    /**
     * @param y Y coordinate of wanted tile
     * @param x X coordinate of wanted tile
     * @return Character at the wanted coordinates in the level's map
     */
    public char getTile(int y, int x) {
        if (isOutOfBounds(y, x)) {
            return 'N';
        }

        return this.levelData[y][x];
    }

    /**
     * Swaps two tiles in this level's map with each other
     * @param y1 First Y coordinate
     * @param x1 First X coordinate
     * @param y2 Second Y coordinate
     * @param x2 Second X coordinate
     */
    public void swapTiles(int y1, int x1, int y2, int x2) {
        if (isOutOfBounds(y1, x1) || isOutOfBounds(y2, x2)) {
            return;
        }

        char c = this.levelData[y1][x1];
        this.levelData[y1][x1] = this.levelData[y2][x2];
        this.levelData[y2][x2] = c;
    }

    /**
     * Checks whether a coordinate is out of bounds of the map
     * @param y Y coordinate
     * @param x X coodinate
     * @return True if the coordinate is out of bounds, false otherwise
     */
    public boolean isOutOfBounds(int y, int x) {
        return (y < 0 || y >= this.levelData.length || x < 0 || x >= this.levelData[0].length);
    }

    public ArrayList<Integer> getAdjacentTiles(int y, int x) {
        ArrayList<Integer> tiles = new ArrayList<>();

        if (isOutOfBounds(y, x)) {
            return tiles;
        }

        if (!isOutOfBounds(y - 1, x)) {
            tiles.add((y - 1) * this.getWidth() + x);
        }

        if (!isOutOfBounds(y + 1, x)) {
            tiles.add((y + 1) * this.getWidth() + x);
        }

        if (!isOutOfBounds(y, x - 1)) {
            tiles.add(y * this.getWidth() + x - 1);
        }

        if (!isOutOfBounds(y, x + 1)) {
            tiles.add(y * this.getWidth() + x + 1);
        }

        return tiles;
    }

    /**
     * Checks whether a unit can move to a tile
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @return True if a unit can move to the tile in the specified coordinates, false otherwise
     */
    public boolean isValidUnitMove(int y, int x) {
        if (isOutOfBounds(y, x)) {
            return false;
        }

        char tile = getTile(y, x);
        return (!(tile == ' ' || tile == '#' || tile == '@' || tile == 'Z' || tile == 'O'));
    }

    /**
     * Checks if a specified tile has a unit and returns it if it does
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @return Unit in the tile if it exists, null otherwise
     */
    public Unit getUnitAtTile(int y, int x) {
        if (this.player != null && this.player.y == y && this.player.x == x) {
            return this.player;
        }

        for (Zombie z : this.zombies) {
            if (z.y == y && z.x == x) {
                return z;
            }
        }

        return null;
    }

    /**
     * Checks whether a tile is the exit to the level
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @return True if the tile is the exit, false otherwise
     */
    public boolean isExitAtTile(int y, int x) {
        return this.getTile(y, x) == 'O';
    }

    /**
     * Exits this level
     */
    public void exitLevel() {
        if (this.onLevelExitFunc != null) {
            this.onLevelExitFunc.run();
        }
    }

    /**
     * Sets an event listener for when this level is exited
     * @param cb Callback function to be called when this event fires
     */
    public void onLevelExit(Runnable cb) {
        this.onLevelExitFunc = cb;
    }

    /**
     * Checks whether a tile is valid to be used when calculating a path
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @return True if the tile is valid to be used for a path, false otherwise
     */
    public boolean isValidPathTile(int y, int x) {
        if (isOutOfBounds(y, x)) {
            return false;
        }

        char tile = getTile(y, x);
        return (!(tile == ' ' || tile == '#' || tile == 'O'));
    }

    /**
     * Gives neighboring tiles that can be entered from a specified tile when path finding
     * @param y Y coordinate of the tile
     * @param x X coordinate of the tile
     * @return A list of neighboring tile coordinates that can be entered
     */
    public ArrayList<Integer> getValidTileNeighborsForPath(int y, int x) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        ArrayList<Integer> tiles = getAdjacentTiles(y, x);
        for (int tileCoord : tiles) {
            int tileY = Util.getYFromEncodedCoordinate(tileCoord, getWidth());
            int tileX = Util.getXFromEncodedCoordinate(tileCoord, getWidth());

            if (isValidPathTile(tileY, tileX)) {
                neighbors.add(tileCoord);
            }
        }

        return neighbors;
    }

    /**
     * @return The height of the level
     */
    public int getHeight() {
        return this.levelData.length;
    }

    /**
     * @return The width of the level
     */
    public int getWidth() {
        return (this.levelData.length == 0) ? 0 : this.levelData[0].length;
    }

    public char[] getRow(int row) {
        if (row < 0 || row >= this.levelData.length) {
            return new char[0];
        }

        return this.levelData[row];
    }

    /**
     * Finds a path between two positions in the level
     * @param y1 Y coordinate of the first position
     * @param x1 X coordinate of the first position
     * @param y2 Y coordinate of the second position
     * @param x2 X coordinate of the second position
     * @return A list of coordinates that contain the steps in the path
     */
    public ArrayList<Integer> findPathBetweenCoordinates(int y1, int x1, int y2, int x2) {
        // Create priorityqueue for path finding
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(new Comparator<Pair>() {
            public int compare(Pair a1, Pair a2) {
                return a1.key - a2.key;
            }
        });
        
        // Store path and visited cells
        int[] cameFromMap = new int[getHeight() * getWidth()];
        int[][] visitedMap = new int[getHeight()][getWidth()];
        pq.add(new Pair(0, Util.getEncodedCoordinate(y1, x1, getWidth())));
        boolean foundPath = false;

        int cost = 0;
        while (pq.size() > 0) {
            // Get current coordinate and decode it
            int currCoord = pq.poll().value;
            int currY = Util.getYFromEncodedCoordinate(currCoord, getWidth());
            int currX = Util.getXFromEncodedCoordinate(currCoord, getWidth());

            // At destination
            if (currY == y2 && currX == x2) {
                foundPath = true;
                break;
            }

            // Don't revisit already visited tiles
            if (visitedMap[currY][currX] == 1) {
                continue;
            }

            visitedMap[currY][currX] = 1;

            ArrayList<Integer> neighbors = getValidTileNeighborsForPath(currY, currX);
            for (int neighbor : neighbors) {
                int neighborY = Util.getYFromEncodedCoordinate(neighbor, getWidth());
                int neighborX = Util.getXFromEncodedCoordinate(neighbor, getWidth());

                if (visitedMap[neighborY][neighborX] == 0) {
                    cameFromMap[neighbor] = currCoord;
                    pq.add(
                        new Pair(cost + 1 + Util.totalDistanceBetweenCoordinates(y2, x2, neighborY, neighborX), neighbor)
                    );
                }
            }

            cost++;
        }

        if (!foundPath) {
            return new ArrayList<>();
        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(Util.getEncodedCoordinate(y2, x2, getWidth()));
        int currCoord = cameFromMap[Util.getEncodedCoordinate(y2, x2, getWidth())];

        // Construct path
        while (true) {
            path.add(0, currCoord);
            currCoord = cameFromMap[currCoord];

            // Done constructing path
            if (currCoord == Util.getEncodedCoordinate(y1, x1, getWidth())) {
                break;
            }
        }

        return path;
    }
}