import config.ConfigurationManager;
import mail.Group;
import mail.Person;
import prank.PrankGenerator;
import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        //.readFromInputStream(new FileInputStream("./config/config.properties"));
        //PrankGenerator prankGenerator = new PrankGenerator();

       // System.out.println(prankGenerator.buildPrank(new FileInputStream("./config/victimList.utf8"), new FileInputStream("./config/messages.utf8")));
        SmtpClient smtp = new SmtpClient();
        smtp.sendSmtpRequest();
    }
}
