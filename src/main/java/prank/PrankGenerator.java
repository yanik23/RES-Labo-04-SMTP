package prank;

import mail.Group;
import mail.Mail;
import mail.Message;
import mail.Person;

import java.io.*;
import java.util.*;

public class PrankGenerator {


    private final int MIN_NUMBER_OF_VICTIMS = 3;
    public PrankGenerator(){

    }
    public List<Person> readVictimList(InputStream victims) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(victims));
        List<Person> victimList = new ArrayList<Person>();
        String line;
        while ((line = br.readLine()) != null && line.length() != 0) {

            String firstName = line.substring(0,line.indexOf("."));
            String lastName = line.substring(line.indexOf(".")+1, line.indexOf("@"));

            Person p = new Person(firstName, lastName, line);
            victimList.add(p);
        }
        return victimList;
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

    public List<Mail> createRandomMails(List<Group> listOfGroups, InputStream messages) throws IOException {
        List<Mail> mailList = new ArrayList<>();
        List<Message> messageList = readMessageList(messages);

        for(int i = 0; i < listOfGroups.size(); ++i){
            Message message = getRandomMessage(messageList);
            Mail mail = new Mail(message.getSubject(), message.getContent(), listOfGroups.get(i).getFrom(), listOfGroups.get(i).getTo());
            mailList.add(mail);
        }

        return mailList;
    }

    public Message getRandomMessage(List<Message> messageList){
        Random rand = new Random();
        return messageList.get(rand.nextInt(messageList.size()));
    }
    public List<Message> readMessageList(InputStream messages) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(messages));
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
