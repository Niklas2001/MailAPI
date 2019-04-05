package de.niklasriepen.mailapi.logging;

public class ConsoleLogger extends Logger {

    static final ConsoleLogger instance = new ConsoleLogger();

    @Override
    public void logInformation(String message) {
        System.out.println(PREFIX + message);
    }

    @Override
    public void logError(String message) {
        System.err.println(PREFIX + message);
    }
}
