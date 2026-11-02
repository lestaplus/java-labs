package service;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {
    public static void setup() {
        Logger rootLogger = Logger.getLogger("");

        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        try {
            FileHandler fileHandler = new FileHandler("app.log", false);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            rootLogger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(Level.INFO);
            rootLogger.addHandler(consoleHandler);

            rootLogger.setLevel(Level.INFO);

            Logger.getLogger("controller").setLevel(Level.ALL);
            Logger.getLogger("service").setLevel(Level.ALL);
            Logger.getLogger("model").setLevel(Level.ALL);
            Logger.getLogger("Main").setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Logger initialization error: " + e.getMessage());
        }
    }
}
