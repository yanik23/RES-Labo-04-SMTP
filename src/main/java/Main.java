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

        ConfigurationManager.readFromInputStream(new FileInputStream("./config/app.config"));
        PrankGenerator prankGenerator = new PrankGenerator();

        System.out.println(prankGenerator.buildPrank(new FileInputStream("./config/victimList.txt"), new FileInputStream("./config/messages.txt")));
        
    }
}
