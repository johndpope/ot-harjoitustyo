package roguelike.domain;

import java.util.ArrayList;

import roguelike.util.Util;

/**
 * A zombie unit which looks for the player and attacks it
 */
public class Zombie extends Unit {
    private int movesBeforePathUpdate = 0;
    private int currentPathIndex = 0;
    private int idleCounter = 0;
    private ArrayList<Integer> path;

    public Zombie(int y, int x, String name, Level level) {
        super(y, x, name, level);
        this.health = 13;
        this.damage = 2;
        this.path = new ArrayList<>();
    }
    
    /**
     * Calculates a move for the zombie and then moves it
     */
    public void calculateMove() {
        if (this.calculateAttack()) {
            return;
        }

        if (calculatePath()) {
            int nextY = Util.getYFromEncodedCoordinate(path.get(this.currentPathIndex), this.level.getWidth());
            int nextX = Util.getXFromEncodedCoordinate(path.get(this.currentPathIndex), this.level.getWidth());
            boolean success = this.move(nextY - this.y, nextX - this.x);
            
            if (success) {
                this.movesBeforePathUpdate--;
                this.currentPathIndex++;
                this.idleCounter = 0;
            } else {
                this.idleCounter++;
            }
        }        
    }

    /**
     * Calculates whether or not the zombie should attack the player
     * @return True if the zombie attacked the player, false otherwise
     */
    private boolean calculateAttack() {
        if (this.level.player == null) {
            return true;
        }

        int distToPlayer = Util.totalDistanceBetweenCoordinates(this.level.player.y, this.level.player.x, this.y, this.x);
        if (this.bombs.size() > 0 && distToPlayer <= this.bombs.get(0).blastRadius) {
            this.useBomb();
            return true;
        } else if (distToPlayer == 1) {
            this.attack(this.level.player);
            return true;
        }

        return false;
    }

    /**
     * Recalculates path for this zombie, if needed
     * @return True if move calculation should be continued, false otherwise
     */
    private boolean calculatePath() {
        if (this.idleCounter >= 2 || this.movesBeforePathUpdate == 0 || this.currentPathIndex >= this.path.size()) {
            ArrayList<Integer> newPath = this.level.findPathBetweenCoordinates(
                this.y, this.x, this.level.player.y, this.level.player.x
            );

            if (newPath.size() > 0) {
                this.setNewPath(newPath);
            } else if (this.currentPathIndex >= this.path.size()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Sets a new path for this zombie
     * @param newPath The new path that should be used
     */
    private void setNewPath(ArrayList<Integer> newPath) {
        this.path = newPath;
        this.currentPathIndex = 0;
        
        if (path.size() > 50) {
            this.movesBeforePathUpdate = 15;
        } else if (path.size() > 25) {
            this.movesBeforePathUpdate = 10;
        } else if (path.size() > 8) {
            this.movesBeforePathUpdate = 3;
        } else {
            this.movesBeforePathUpdate = 1;
        }
    }
}