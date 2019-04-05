package de.niklasriepen.mailapi.session.handshake;

import de.niklasriepen.mailapi.logging.Logger;
import de.niklasriepen.mailapi.session.authentication.AuthenticationData;
import de.niklasriepen.mailapi.session.exceptions.authentication.AuthenticationFailedException;
import de.niklasriepen.mailapi.session.exceptions.tls.TLSConnectionFailedException;
import de.niklasriepen.mailapi.session.exceptions.tls.TLSException;
import de.niklasriepen.mailapi.session.exceptions.tls.TLSNotSupportedException;
import de.niklasriepen.mailapi.transport.OpenTransport;
import de.niklasriepen.mailapi.transport.Transport;

import java.io.IOException;
import java.util.ArrayList;

public class SMTPHandshake {

    private Logger logger;
    private Transport transport;

    private boolean useTLS;

    private ArrayList<String> smtpExtensions = new ArrayList<>();

    public SMTPHandshake(OpenTransport transport, boolean useTLS, Logger logger) {
        this.transport = transport;
        this.logger = logger;
        this.useTLS = useTLS;
    }

    public Transport start(AuthenticationData authenticationData) throws TLSException, AuthenticationFailedException, IOException {
        ((OpenTransport) transport).start();
        try {
            readNextInput();
            doEHLORequest();
            if (useTLS) startSecuredConnection();
            if (authenticationData != null) authenticate(authenticationData);
        } catch (IOException e) {
            logger.logError(e.getMessage());
        }
        return transport;
    }

    private void doEHLORequest() throws IOException {
        transport.writeLine("EHLO " + transport.getHostname());
        do {
            smtpExtensions.add(readNextInput());
        } while (transport.isInputAvailable());
    }

    private void startSecuredConnection() throws IOException, TLSException {
        if (!smtpExtensions.contains("250-STARTTLS")) throw new TLSNotSupportedException();
        transport.writeLine("STARTTLS");
        if (!readNextInput().startsWith("220")) throw new TLSConnectionFailedException();
        transport = ((OpenTransport) transport).getSecuredTransport();
    }

    private void authenticate(AuthenticationData authenticationData) throws IOException, AuthenticationFailedException {
        transport.writeLine("AUTH LOGIN");
        readNextInput();
        transport.writeLine(authenticationData.getUsernameBase64());
        readNextInput();
        transport.writeLine(authenticationData.getPasswordBase64());
        if (!readNextInput().equals("235 2.7.0 Accepted")) throw new AuthenticationFailedException();
    }

    private String readNextInput() throws IOException {
        String line = transport.readLine();
        if (logger != null)
            logger.logInformation(line);
        return line;
    }
}
