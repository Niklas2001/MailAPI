package de.niklasriepen.mailapi.session;

import de.niklasriepen.mailapi.logging.Logger;
import de.niklasriepen.mailapi.mail.Mail;
import de.niklasriepen.mailapi.session.authentication.AuthenticationData;
import de.niklasriepen.mailapi.session.exceptions.authentication.AuthenticationFailedException;
import de.niklasriepen.mailapi.session.exceptions.mailtransport.InvalidAddressException;
import de.niklasriepen.mailapi.session.exceptions.mailtransport.MailTransportFailedException;
import de.niklasriepen.mailapi.session.exceptions.tls.TLSException;
import de.niklasriepen.mailapi.session.handshake.SMTPHandshake;
import de.niklasriepen.mailapi.session.mailtransport.MailTransport;
import de.niklasriepen.mailapi.transport.OpenTransport;
import de.niklasriepen.mailapi.transport.Transport;

import java.io.IOException;

public class Session {

    private Transport transport;

    //Authentication
    private AuthenticationData authenticationData;

    private boolean useTLS;

    public Session(String smtpHost, int smtpPort, boolean useTLS) {
        transport = new OpenTransport(smtpHost, smtpPort);
        this.useTLS = useTLS;
    }

    public void sendMail(Mail mail) throws TLSException, AuthenticationFailedException,
            IOException, InvalidAddressException, MailTransportFailedException {
        if (!transport.isConnected()) {
            SMTPHandshake handshake = new SMTPHandshake((OpenTransport) transport, useTLS, logger);
            transport = handshake.start(authenticationData);
        }
        MailTransport mailTransport = new MailTransport(transport, logger);
        mailTransport.send(mail);
    }

    public void setAuthenticationData(AuthenticationData authenticationData) {
        this.authenticationData = authenticationData;
    }

    public void close() throws IOException {
        transport.writeLine("QUIT");
        transport.close();
    }

    /*
            Debugging
     */
    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
