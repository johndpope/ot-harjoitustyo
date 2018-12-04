package roguelike.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class LoggerTest {
    @Before
    public void setupLogger() {
        Logger.nrLines = 0;
    }

    @Test
    public void messagesNotLoggedIfNoLines() {
        Logger.log("");
        assertEquals(Logger.messages.size(), 0);
    }

    @Test
    public void messageGetsLoggedIfHasLines() {
        Logger.nrLines = 1;
        Logger.log("First");
        assertEquals(Logger.messages.size(), 1);
        assertEquals(Logger.messages.get(0), "First");
        Logger.log("Second");
        assertEquals(Logger.messages.size(), 1);
        assertEquals(Logger.messages.get(0), "Second");
    }

    @Test
    public void clearingLogMessagesWorks() {
        Logger.nrLines = 1;
        Logger.log("First");
        assertEquals(Logger.messages.size(), 1);
        Logger.clearLog();
        assertEquals(Logger.messages.size(), 0);
    }
}