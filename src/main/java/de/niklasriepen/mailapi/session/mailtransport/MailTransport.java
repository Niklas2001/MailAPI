package de.niklasriepen.mailapi.session.mailtransport;

import de.niklasriepen.mailapi.logging.Logger;
import de.niklasriepen.mailapi.mail.Mail;
import de.niklasriepen.mailapi.session.exceptions.mailtransport.InvalidAddressException;
import de.niklasriepen.mailapi.session.exceptions.mailtransport.MailTransportFailedException;
import de.niklasriepen.mailapi.transport.Transport;

import java.io.IOException;

public class MailTransport {

    private Transport transport;

    private Logger logger;

    public MailTransport(Transport transport, Logger logger) {
        this.transport = transport;
        this.logger = logger;
    }

    public void send(Mail mail) throws InvalidAddressException, MailTransportFailedException, IOException {
        sendSender(mail.getSenderAddress());
        sendRecipient(mail.getRecipientAddress());
        prepareContent(mail.getSenderAddress(), mail.getRecipientAddress());
        sendContent(mail.getSubject(), mail.getContent());
    }

    private void sendSender(String sender) throws IOException, InvalidAddressException {
        transport.writeLine("MAIL FROM:<" + sender + ">");
        if (!readNextInput().startsWith("250 2.1.0")) throw new InvalidAddressException(sender, "sender");
    }

    private void sendRecipient(String recipient) throws IOException, InvalidAddressException {
        transport.writeLine("RCPT TO:<" + recipient + ">");
        if (!readNextInput().startsWith("250 2.1.5")) throw new InvalidAddressException(recipient, "recipient");
    }

    private void prepareContent(String sender, String recipient) throws IOException, MailTransportFailedException {
        transport.writeLine("DATA");
        if (!readNextInput().startsWith("354")) throw new MailTransportFailedException();
        transport.writeLine("From: " + sender);
        transport.writeLine("To: " + recipient);
    }

    private void sendContent(String subject, String content) throws IOException, MailTransportFailedException {
        transport.writeLine("Subject: " + subject);
        transport.writeLine(content);
        transport.writeLine(".");
        if (!readNextInput().startsWith("250 2.0.0")) throw new MailTransportFailedException();
    }

    private String readNextInput() throws IOException {
        String line = transport.readLine();
        if (logger != null)
            logger.logInformation(line);
        return line;
    }

}
