package roguelike.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PairTest {
    @Test
    public void pairHasCorrectValues() {
        Pair p1 = new Pair(5, 2);
        Pair p2 = new Pair(-216, -999999999);
        assertEquals(p1.key, 5);
        assertEquals(p1.value, 2);
        assertEquals(p2.key, -216);
        assertEquals(p2.value, -999999999);
    }
}