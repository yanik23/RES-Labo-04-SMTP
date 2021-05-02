/**
 * This class simulates a simple smtp client that sends mails
 * generated with the prank generator
 * @author Janssens Emmanuel
 * @author Lange Yanik
 *
 */
package smtp;

import config.ConfigurationManager;
import mail.Group;
import mail.Mail;
import mail.Person;
import prank.PrankGenerator;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class SmtpClient {

    private final String OK = "250";
    private final String WELCOME = "220";
    private final String BYE = "221";
    private final String ENDDATA = "354";

    private final String EHLO = "EHLO";
    private final String MAILFROM = "MAIL FROM: ";
    private final String RCPTTO = "RCPT TO: ";
    private final  String DATA = "DATA";
    private final String QUIT = "QUIT";

    private Socket _client;
    private BufferedReader _smtpInputStream;
    private BufferedWriter _smtpOutputStream;

    PrankGenerator _prankGenerator = new PrankGenerator();

    /**
     * Initialises the configuration manager
     * @throws IOException
     */
    public SmtpClient() throws IOException {
        ConfigurationManager.readFromInputStream(new FileInputStream("./config/config.properties"));
    }

    /**
     * Connect to the smtp server
     * @throws IOException
     */
    public void connect() throws IOException {
        try{
            _client = new Socket(ConfigurationManager.getPropertyValue("smtpServerAdress"),Integer.parseInt(ConfigurationManager.getPropertyValue("smtpServerPort")));
            _smtpOutputStream = new BufferedWriter(new OutputStreamWriter(_client.getOutputStream(), StandardCharsets.UTF_8));
            _smtpInputStream = new BufferedReader(new InputStreamReader(_client.getInputStream(), StandardCharsets.UTF_8));

        }catch (IOException e){
            throw new IOException("could not connect to server: " + ConfigurationManager.getPropertyValue("smtpServerAdress") + " at port " + ConfigurationManager.getPropertyValue("smtpServerPort"));
        }
    }

    private void disconnect() throws  IOException{
        _smtpInputStream.close();
        _smtpOutputStream.close();
        _client.close();
    }
    /**
     * Write and send message to the server
     * @param cmdLine SMTP command to be send
     * @param args if any arguments are required
     * @throws IOException
     */
    public void sendCommand(String cmdLine, String args) throws IOException {

        System.out.println("c: "+ cmdLine);

        _smtpOutputStream.write(cmdLine + " " + args + "\r\n");
        _smtpOutputStream.flush();

    }

    private void printExpectedOutput(String code) throws IOException {
        String line;

        try{
            do{
                line = _smtpInputStream.readLine();
                System.out.println(line);
            }while(line != null && !line.startsWith(code));
        }
        catch(Exception e){
            disconnect();
        }
    }
    private String formatMailheader(Person p){
        return p.getName() + " " + p.getSurname() + "<" + p.getEmail() + ">";
    }
    /**
     * Write the mail header
     * @param mail mail data
     */
    private void writeData(Mail mail) throws IOException {

        sendCommand("From:",formatMailheader(mail.getFrom()));

        StringBuilder argsTo = new StringBuilder();
        for(Person personTo : mail.getTo()){
            argsTo.append(formatMailheader(personTo)).append(",");
        }
        sendCommand("To:", argsTo.toString());

        StringBuilder argsCC = new StringBuilder();
        for(Person personTo : mail.getCc()){
            argsCC.append(formatMailheader(personTo)).append(",");
        }
        sendCommand("cc:", argsCC.toString());

        sendCommand("Subject:", "=?UTF-8?B?"+ Base64.getEncoder().encodeToString(mail.getMessage().getSubject().getBytes(StandardCharsets.UTF_8)) +"?=" );

        sendCommand("Content-Type:"," text/plain; charset=utf-8" );

        sendCommand(mail.getMessage().getContent(),"");
        sendCommand("\r\n.\r\n","");
    }


    /**
     * executes the mail sending protocol once
     * @param mail
     * @throws IOException
     */
    private void sendMail(Mail mail) throws IOException {
        connect();
        printExpectedOutput(WELCOME);
        if (_smtpOutputStream != null && _smtpInputStream != null && _smtpInputStream != null) {

            //send EHLO
            sendCommand(EHLO, ConfigurationManager.getPropertyValue("smtpServerAdress"));
            printExpectedOutput(OK);

            //Write mail writer
            sendCommand(MAILFROM, mail.getFrom().getEmail());
            printExpectedOutput(OK);

            //write each recipient
            for(Person rcptTo : mail.getTo()){
                sendCommand(RCPTTO,rcptTo.getEmail());
                printExpectedOutput(OK);

            }

            for(Person cc: mail.getCc()){
                sendCommand(RCPTTO, cc.getEmail());
                printExpectedOutput(OK);

            }
            sendCommand(DATA,"");
            printExpectedOutput(ENDDATA);

            writeData(mail);
            printExpectedOutput(OK);

            sendCommand(QUIT,"");
            printExpectedOutput(BYE);

        }

    }

    /**
     * For each group generated a random message is selected and send to each victims
     * @throws IOException
     */
    public void run() throws IOException {

        List<Person> personlist = _prankGenerator.readVictimList(new FileInputStream(ConfigurationManager.getPropertyValue("victimFile")));
        List<Group> groupList = _prankGenerator.buildRandomGroups(personlist, Integer.parseInt(ConfigurationManager.getPropertyValue("numberOfGroups")));
        List<Mail> mailList = _prankGenerator.createRandomMails(groupList, new FileInputStream(ConfigurationManager.getPropertyValue("messagesFile")));

        try{
            for(Mail mail : mailList) {
                sendMail(mail);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
