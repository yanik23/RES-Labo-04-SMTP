import mail.Person;
import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        PrankGenerator prankGenerator = new PrankGenerator();

        List<Person> list = new ArrayList<Person>();
        list = prankGenerator.readVictimList(new FileInputStream("./config/victimList.txt"));

        for(int i = 0; i < list.size(); ++i){
            System.out.println(list.get(i));
        }

       /* try{
            ConfigurationManager.readFromInputStream(new FileInputStream("app.config"));
            System.out.println(ConfigurationManager.getPropertyValue("smtpServerAdress"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }*/

    }
}
