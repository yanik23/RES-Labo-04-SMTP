package prank;

import mail.Group;
import mail.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PrankGenerator {

    private class Message{
        String subject;
        String content;
    }
    List<Person> allVictims;
    List<Message> allMessages;

    private final int MIN_NUMBER_OF_VICTIMS = 3;

    public PrankGenerator()
    {
        allVictims = new ArrayList<>();
        allMessages = new ArrayList<>();
    }

    public void printMessages()
    {
        for (Message m : allMessages)
        {
            System.out.println(m.subject);
            System.out.println(m.content);
        }
    }
    public void readMessageFromInputStream(InputStream inputStream)throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Message m = new Message();
            StringBuilder content = new StringBuilder();
            String subject = "";
            String subjectDefiner = "Subject:";
            while ((line = br.readLine()) != null) {
                if(line.contains(subjectDefiner)){
                    subject = line.substring(line.indexOf(subjectDefiner)+subjectDefiner.length());
                }
                else if(line.contains("==")) {
                    m.content = content.toString();
                    m.subject = subject;
                    allMessages.add(m);
                    m = new Message();
                    content = new StringBuilder();

                }
                else
                {
                    if(!line.isEmpty())
                        content.append( line + "\r\n");
                }
            }
        }
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
    public Prank buildPrank(){

        return null;
    }



}
