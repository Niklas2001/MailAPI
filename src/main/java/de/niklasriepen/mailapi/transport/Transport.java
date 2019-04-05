package de.niklasriepen.mailapi.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Transport {

    BufferedReader reader;
    PrintWriter writer;

    public abstract String getHostname();

    public abstract boolean isConnected();

    public abstract void close() throws IOException;

    public void writeLine(String line) {
        writer.println(line);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public boolean isInputAvailable() throws IOException {
        return reader.ready();
    }

}
