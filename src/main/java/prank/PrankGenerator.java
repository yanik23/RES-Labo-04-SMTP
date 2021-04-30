package prank;

import mail.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PrankGenerator {

    private class Message{
        String subject;
        String content;
    }
    List<Person> allVictims;
    List<Message> allMessages;

    PrankGenerator()
    {
        allVictims = new ArrayList<>();
        allMessages = new ArrayList<>();
    }
    public void readPersonFromInputStream(InputStream inputStream)throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String mail = line;
                String name = line.substring(0,line.indexOf("."));
                String surname = line.substring(line.indexOf(".")+1,line.indexOf("@"));
                Person p = new Person(name,surname,mail);
                allVictims.add(p);
            }
        }
    }


    public void readMessageFromInputStream(InputStream inputStream)throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Message m = new Message();
            String content = "";

            while ((line = br.readLine()) != null) {
                if(line.contains("Subject :")){
                    m.subject = line.substring(0,line.indexOf("Subject : "));;
                }
                else if(!line.contains("==")) {
                    content += line;
                    m.content = content;
                    allMessages.add(m);
                }
                else
                {
                    content = "";
                }
            }
        }
    }

    public Prank buildPrank(){

        return null;
    }



}
