import smtp.SmtpClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        SmtpClient smtpClient = new SmtpClient();
        System.out.println("sending mail...");
        smtpClient.sendSmtpRequest();
        System.out.println("mail send.");

    }
}
