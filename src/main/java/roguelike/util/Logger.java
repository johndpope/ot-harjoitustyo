package roguelike.util;

import java.util.ArrayList;

public final class Logger {
    public static ArrayList<String> messages = new ArrayList<>();
    public static int nrLines = 0;
    private static Runnable onLogFunc = null;

    /**
     * Logs a message to the screen
     * @param message The message that should be logged
     */
    public static void log(String message) {
        if (nrLines == 0) {
            return;
        }

        if (messages.size() < nrLines) {
            messages.add(0, message);
        } else {
            // Shift all messages
            for (int i = messages.size() - 1; i >= 1; i--) {
                messages.set(i, messages.get(i - 1));
            }

            messages.set(0, message);
        }

        if (onLogFunc != null) {
            onLogFunc.run();
        }
    }

    public static void setOnLog(Runnable func) {
        onLogFunc = func;
    }

    public static void clearLog() {
        messages.clear();
    }
}