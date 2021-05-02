import smtp.SmtpClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        SmtpClient smtpClient = new SmtpClient();
        smtpClient.run();


    }
}
