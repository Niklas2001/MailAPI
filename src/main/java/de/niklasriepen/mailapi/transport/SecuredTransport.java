package de.niklasriepen.mailapi.transport;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SecuredTransport extends Transport {

    private SSLSocket socket;

    SecuredTransport(Socket socket) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        this.socket = (SSLSocket) factory.createSocket(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);
        reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        writer = new PrintWriter(this.socket.getOutputStream(), true);
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    @Override
    public String getHostname() {
        return socket.getInetAddress().getHostName();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

}
