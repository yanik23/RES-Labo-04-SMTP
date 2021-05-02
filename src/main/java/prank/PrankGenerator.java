/**
 * Generates all necessary data to send a prank mail
 * @author Janssens Emmanuel
 * @author Lange Yanik
 */
package prank;

import config.ConfigurationManager;
import mail.Group;
import mail.Mail;
import mail.Message;
import mail.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PrankGenerator {


    private final int MIN_NUMBER_OF_VICTIMS = 3;
    public PrankGenerator(){

    }

    private Person readFromMailAdress(String mailAdress){
        String firstName = mailAdress.substring(0,mailAdress.indexOf("."));
        String lastName = mailAdress.substring(mailAdress.indexOf(".")+1, mailAdress.indexOf("@"));

        return new Person(firstName, lastName, mailAdress);
    }
    /**
     * Reads a file and creates a list of victims
     * @param victims
     * @return
     * @throws IOException
     */
    public List<Person> readVictimList(InputStream victims) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(victims, StandardCharsets.UTF_8));
        List<Person> victimList = new ArrayList<Person>();
        String line;
        while ((line = br.readLine()) != null && line.length() != 0) {
            victimList.add(readFromMailAdress(line));
        }
        return victimList;
    }
    public List<Person> readWitnesses(){
        List<String> witnesses = Arrays.asList(ConfigurationManager.getPropertyValue("withnessToCC").split(","));
        List<Person> persons = new ArrayList<>();
        for(String mail : witnesses)
        {
            persons.add(readFromMailAdress(mail));
        }
        return persons;
    }
    /**
     *
     * @param personList list of persons where we will pick randomly
     * @param numberOfGroups number of groups we want to have
     * @return
     */
    public List<Group> buildRandomGroups(List<Person> personList, int numberOfGroups){
        int upperBound = personList.size();
        Random rand = new Random();
        List<Group> groupList = new ArrayList<>();

        for(int i = 0; i < numberOfGroups; ++i){

            //randomly chosing a number of victims for a group between 3 and the number of persons on the list
            int numberOfVictims = rand.nextInt(upperBound-MIN_NUMBER_OF_VICTIMS)+MIN_NUMBER_OF_VICTIMS;

            List<Person> victimList = new ArrayList<>();

            Collections.shuffle(personList); //randomly shuffling the list of persons

            for(int j = 0; j < numberOfVictims; ++j){
                victimList.add(personList.get(j));  //chosing victims from randomly shuffled personList
            }

            Person sender = victimList.get(numberOfVictims-1); //choosing a sender
            victimList.remove(sender); //removing the sender so he doesn't sends himself a mail

            Group g = new Group(sender, victimList);
            groupList.add(g);
        }
        return groupList;
    }

    /**
     * Selects a random message from a list of messages
     * and builds list of Mail/Prank that will be sent to each group
     * @param listOfGroups list of groups generated
     * @param messages file input message
     * @return
     * @throws IOException
     */
    public List<Mail> createRandomMails(List<Group> listOfGroups, InputStream messages) throws IOException {
        List<Mail> mailList = new ArrayList<>();
        List<Message> messageList = readMessageList(messages);

        for(int i = 0; i < listOfGroups.size(); ++i){
            Message message = getRandomMessage(messageList);
            Mail mail = new Mail(message, listOfGroups.get(i).getFrom(), listOfGroups.get(i).getTo(), readWitnesses());
            mailList.add(mail);
        }

        return mailList;
    }

    /**
     * Select a random message from a list
     * @param messageList list to be selected from
     * @return a random message
     */
    public Message getRandomMessage(List<Message> messageList){
        Random rand = new Random();
        return messageList.get(rand.nextInt(messageList.size()));
    }

    /**
     * Reads a list of messages
     * @param messages
     * @return
     * @throws IOException
     */
    public List<Message> readMessageList(InputStream messages) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(messages, StandardCharsets.UTF_8));
        List<Message> messageList = new ArrayList<Message>();

        String subject = "";
        String content = "";
        String line;
        while((line = br.readLine()) != null) {
            if(line.contains("Subject:")) {
                subject = line.substring(line.indexOf(":") + 1);
            }
            else if (!line.contains("==")) {
                content += line + "\r\n";
            }

            if(line.contains("==")) {
                Message message = new Message(subject, content);
                messageList.add(message);
                content = "";
            }
        }
        return messageList;

    }


}
