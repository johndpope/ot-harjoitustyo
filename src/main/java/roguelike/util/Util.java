package roguelike.util;

/**
 * Offers static utility functions
 */
public final class Util {
    /**
     * Encodes a coordinate into a single integer value
     * @param y Y coodinate
     * @param x X coordinate
     * @param mapWidth Width of the map where the coordinate is located
     * @return Encoded coordinate
     */
    public static int getEncodedCoordinate(int y, int x, int mapWidth) {
        return (y * mapWidth + x);
    }

    /**
     * Gets the y coordinate from an encoded coordinate
     * @param coord The encoded coordinate
     * @param mapWidth Width of the map where the coordinate is located
     * @return Y coordinate from the encoded coordinate
     */
    public static int getYFromEncodedCoordinate(int coord, int mapWidth) {
        return (coord - getXFromEncodedCoordinate(coord, mapWidth)) / mapWidth;
    }

    /**
     * Gets the x coordinate from an encoded coordinate
     * @param coord The encoded coordinate
     * @param mapWidth Width of the map where the coordinate is located
     * @return X coordinate from the encoded coordinate
     */
    public static int getXFromEncodedCoordinate(int coord, int mapWidth) {
        return (coord % mapWidth);
    }

    /**
     * Calculates the total distance (y distance + x distance) between two coordinates
     * @param y1 First Y coordinate
     * @param x1 First X coordinate
     * @param y2 Second Y coordinate
     * @param x2 Second X coordinate
     * @return Total distance between the coordinates
     */
    public static int totalDistanceBetweenCoordinates(int y1, int x1, int y2, int x2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}