package roguelike.util;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UtilTest {
    @Test
    public void coordinatesAreEncodedCorrectly() {
        Random r = new Random(1337);

        int total = 0;
        for (int i = 0; i < 100; i++) {
            int y = r.nextInt(1000);
            int x = r.nextInt(1000);
            int width = r.nextInt(50) + 1;
            total += Util.getEncodedCoordinate(y, x, width);
        }
        
        assertEquals(total, 1418552);
    }

    @Test
    public void coordinatesAreDecodedCorrectly() {
        Random r = new Random(1337);

        int total = 0;
        for (int i = 0; i < 100; i++) {
            int encoded = r.nextInt(1000);
            int width = r.nextInt(50) + 1;

            int y = Util.getYFromEncodedCoordinate(encoded, width);
            int x = Util.getXFromEncodedCoordinate(encoded, width);
            total += (y * x);
        }
        
        assertEquals(total, 20619);
    }

    @Test
    public void distanceIsCalculatedCorrectly() {
        Random r = new Random(1337);

        int total = 0;
        for (int i = 0; i < 100; i++) {
            int y1 = r.nextInt(1000);
            int x1 = r.nextInt(1000);
            int y2 = r.nextInt(1000);
            int x2 = r.nextInt(1000);

            total += Util.totalDistanceBetweenCoordinates(y1, x1, y2, x2);
        }
        
        assertEquals(total, 62068);
    }
}