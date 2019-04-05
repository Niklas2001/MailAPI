package de.niklasriepen.mailapi.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class OpenTransport extends Transport {

    private Socket socket;

    //Connection data
    private String smtpHost;
    private int smtpPort;

    public OpenTransport(String smtpHost, int smtpPort) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    @Override
    public String getHostname() {
        return smtpHost;
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public void start() throws IOException {
        socket = new Socket(smtpHost, smtpPort);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void close() throws IOException {
        socket.close();
    }

    public SecuredTransport getSecuredTransport() throws IOException {
        return new SecuredTransport(socket);
    }

}
