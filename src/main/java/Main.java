import mail.Group;
import mail.Person;
import smtp.SmtpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        PrankGenerator prankGenerator = new PrankGenerator();

        List<Person> personlist = new ArrayList<Person>();
        personlist = prankGenerator.readVictimList(new FileInputStream("./config/victimList.txt"));
        List<Group> groupList = prankGenerator.buildRandomGroups(personlist, 8);

        for(int i = 0; i < groupList.size(); ++i){
            System.out.println("Group nr " + i + " : ");
            System.out.println(groupList.get(i));
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
