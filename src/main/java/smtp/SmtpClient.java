package smtp;

import mail.Group;
import mail.Mail;
import mail.Person;
import prank.PrankGenerator;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SmtpClient {
    public void sendSmtpRequest() throws IOException {

        PrankGenerator prankGenerator = new PrankGenerator();
        List<Person> personlist = new ArrayList<Person>();
        personlist = prankGenerator.readVictimList(new FileInputStream("./config/victimList.txt"));
        List<Group> groupList = prankGenerator.buildRandomGroups(personlist, 3);
        List<Mail> mailList = prankGenerator.createRandomMails(groupList, new FileInputStream("./config/messages.txt"));


        String smtpServerAddress = "localhost";
        int smtpServerPort = 25;

        for(Mail mail : mailList) {
            String ehlo = "EHLO localhost\r\n";
            String mailFrom = "MAIL FROM: " + mail.getFrom().getEmail() + "\r\n";

            List<String> rcptToList = new ArrayList<>();
            for(Person person : mail.getTo()) {
                rcptToList.add("RCPT TO: " + person.getEmail() + "\r\n");
            }

            String data = "DATA\r\n";
            String from = "From: " + mail.getFrom().getEmail() + "\r\n";

            List<String> toList = new ArrayList<>();
            String to = "to: ";
            for(Person person : mail.getTo()) {
                to += person.getEmail() + ",";
            }
            to += "\r\n";

            //String subject = "Subject: " + mail.getSubject() + "\r\n";
            String subject = "Subject: =?UTF-8?B?"+ Base64.getEncoder().encodeToString(mail.getSubject().getBytes(StandardCharsets.UTF_8))
                    +"?=\r\n";;

            String contentType = "Content-Type: text/plain; charset=utf-8\r\n";
            String content = mail.getContent() + "\r\n";
            String endingSubject = ".\r\n";
            String quit = "QUIT\r\n";

            Socket clientSocket = new Socket(smtpServerAddress, smtpServerPort);
            BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8)); //version bufferisée
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8)); //version bufferisée
            //InputStreamReader is = new InputStreamReader(clientSocket.getInputStream()); //version non bufferisée
            //OutputStreamWriter os = new OutputStreamWriter(clientSocket.getOutputStream()); //version non bufferisée

            if (clientSocket != null && is != null && os != null) {
                os.write(ehlo);
                os.flush();
                os.write(mailFrom);
                os.flush();
                for(String rcptTo : rcptToList){
                    os.write(rcptTo);
                    os.flush();
                }
                os.write(data);
                os.flush();
                os.write(from);
                os.flush();
                os.write(to);
                os.flush();
                os.write(subject);
                os.flush();
                os.write(contentType);
                os.flush();
                os.write(content);
                os.flush();
                os.write(endingSubject);
                os.flush();
                os.write(quit);
                os.flush();
            }
        }
    }
}
