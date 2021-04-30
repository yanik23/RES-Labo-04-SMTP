import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        try{
            ConfigurationManager.readFromInputStream(new FileInputStream("app.config"));
            System.out.println(ConfigurationManager.getPropertyValue("smtpServerAdress"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
