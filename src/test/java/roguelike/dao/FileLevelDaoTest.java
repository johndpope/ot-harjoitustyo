package roguelike.dao;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import roguelike.domain.Level;

public class FileLevelDaoTest {
    @Test
    public void loadingValidLevelReturnsLoadedLevel() {
        FileLevelDao dao = new FileLevelDao();
        Level l = dao.loadLevel(1);
        assertFalse(l.player == null);
        assertEquals(l.getTile(0, 0), '#');
    }
}