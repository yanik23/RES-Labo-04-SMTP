import mail.Group;
import mail.Mail;
import mail.Message;
import mail.Person;
import prank.PrankGenerator;
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

        /*for(int i = 0; i < groupList.size(); ++i){
            System.out.println("Group nr " + i + " : ");
            System.out.println(groupList.get(i));
        }*/

        List<Mail> mailList = prankGenerator.createRandomMails(groupList, new FileInputStream("./config/messages.txt"));
        List<Message> messageList = prankGenerator.readMessageList(new FileInputStream("./config/messages.txt"));
        System.out.println("printing");
        for(int i = 0; i < mailList.size(); ++i){
            System.out.println(mailList.get(i));
        }

        SmtpClient smtpClient = new SmtpClient();
        smtpClient.sendSmtpRequest();
       /* try{
            ConfigurationManager.readFromInputStream(new FileInputStream("app.config"));
            System.out.println(ConfigurationManager.getPropertyValue("smtpServerAdress"));

        }catch (Exception e)
        {
            e.printStackTrace();
        }*/

    }
}
