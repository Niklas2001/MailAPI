package de.niklasriepen.mailapi.logging;

public abstract class Logger {

    static final String PREFIX = "[MailAPI] ";

    public static ConsoleLogger getDefault() {
        return ConsoleLogger.instance;
    }

    public abstract void logInformation(String message);

    public abstract void logError(String message);

}
