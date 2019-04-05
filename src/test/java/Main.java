import de.niklasriepen.mailapi.mail.Mail;
import de.niklasriepen.mailapi.session.Session;
import de.niklasriepen.mailapi.session.exceptions.authentication.AuthenticationFailedException;
import de.niklasriepen.mailapi.session.exceptions.mailtransport.InvalidAddressException;
import de.niklasriepen.mailapi.session.exceptions.mailtransport.MailTransportFailedException;
import de.niklasriepen.mailapi.session.exceptions.tls.TLSException;
import de.niklasriepen.mailapi.session.authentication.AuthenticationData;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Session session = new Session("smtp.gmail.com", 587, true);
        session.setAuthenticationData(new AuthenticationData("messenger.activation@gmail.com", "Caq@6=cJuyK2L57G"));
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1; i++) {
            Mail mail = new Mail("messenger.activation@gmail.com", "riepenniklas@gmail.com");
            mail.setSubject("Hallo Jakob");
            long time = System.currentTimeMillis() - start;
            double seconds = time / 1000d;
            mail.setContent("Hallo Jakob, das ist die " + (i+1) + ". E-Mail innerhalb von " + time + " Millisekunden, daher innerhalb von " + seconds + " Sekunden!\n" +
                    "Mit freundlichen Grüßen\n" +
                    "Niklas Riepen");
            try {
                session.sendMail(mail);
            } catch (TLSException | AuthenticationFailedException | IOException | InvalidAddressException | MailTransportFailedException e) {
                e.printStackTrace();
            }
        }
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
