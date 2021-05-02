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

    private final String EHLO = "EHLO";
    private final String MAILFROM = "MAIL FROM: ";
    private final String RCPTTO = "RCPT TO: ";
    private final  String DATA = "DATA";
    private final String HEADER_FROM = "From: ";
    private final String HEADER_TO = "To: ";
    private final String CC = "cc: ";
    private final String QUIT = "QUIT";

    private Socket _client;

    private BufferedReader _smtpInputStram;
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
     * display response from the smtp server
     * @throws IOException
     */
    private void displayServerResponse() throws IOException {
        String s = _smtpInputStram.readLine();
        System.out.println(s);
    }

    /**
     * Connect to the smtp server
     * @throws IOException
     */
    public void connect() throws IOException {
        try{
            _client = new Socket(ConfigurationManager.getPropertyValue("smtpServerAdress"),Integer.parseInt(ConfigurationManager.getPropertyValue("smtpServerPort")));
            _smtpOutputStream = new BufferedWriter(new OutputStreamWriter(_client.getOutputStream(), StandardCharsets.UTF_8));
            _smtpInputStram = new BufferedReader(new InputStreamReader(_client.getInputStream(), StandardCharsets.UTF_8));
            displayServerResponse();

        }catch (IOException e){
            throw new IOException("could not connect to server: " + ConfigurationManager.getPropertyValue("smtpServerAdress") + " at port " + ConfigurationManager.getPropertyValue("smtpServerPort"));
        }
    }

    /**
     * Write and send message to the server
     * @param cmdLine SMTP command to be send
     * @param args if any arguments are required
     * @throws IOException
     */
    public void sendCommand(String cmdLine, String args) throws IOException {
        _smtpOutputStream.write(cmdLine + " " + args + "\r\n");
        _smtpOutputStream.flush();

        displayServerResponse();

    }

    /**
     * Write the mail header
     * @param mail mail data
     * @return
     */
    private String writeHeader(Mail mail){
        StringBuilder cmd = new StringBuilder();
        cmd.append("Subject: =?UTF-8?B?"+ Base64.getEncoder().encodeToString(mail.getSubject().getBytes(StandardCharsets.UTF_8)) +"?=\r\n");
        cmd.append("Content-Type: text/plain; charset=utf-8\r\n");
        StringBuilder argsCc = new StringBuilder();
        for(Person cc : mail.getCc()){
            argsCc.append(cc.getEmail()).append(",");
        }
        cmd.append("cc: "+argsCc.toString()+"\r\n");
        return cmd.toString();
    }

    /**
     * Write the content body of the mail
     * @param mail
     * @throws IOException
     */
    private void writeContent(Mail mail) throws IOException {
        StringBuilder cmd = new StringBuilder(writeHeader(mail));
        cmd.append(mail.getContent());
        cmd.append("\r\n.\r\n");
        sendCommand(cmd.toString(),"");
    }

    /**
     * executes the mail sending protocol once
     * @param mail
     * @throws IOException
     */
    private void sendMail(Mail mail) throws IOException {
        connect();

        if (_smtpOutputStream != null && _smtpInputStram != null && _smtpInputStram != null) {

            //send EHLO
            sendCommand(EHLO, ConfigurationManager.getPropertyValue("smtpServerAdress"));

            //Write mail writer
            Person from = mail.getFrom();
            sendCommand(MAILFROM,from.getSurname() + " " + from.getName()+ "<"+from.getEmail()+">");

            //write each recipient
            for(Person rcptTo : mail.getTo()){
                sendCommand(RCPTTO,rcptTo.getEmail());
            }

            sendCommand(DATA,"");

            sendCommand(HEADER_FROM,mail.getFrom().getEmail());

            StringBuilder argsTo = new StringBuilder();
            for(Person personTo : mail.getTo()){
                argsTo.append(personTo.getSurname()).append(" ").append(personTo.getName()).append("<").append(personTo.getEmail()).append(">").append(",");
            }
            sendCommand(HEADER_TO,argsTo.toString());

            writeContent(mail);

            sendCommand(QUIT,"");

        }
        _smtpInputStram.close();
        _smtpOutputStream.close();
        _client.close();
    }

    /**
     * For each group generated a random message is selected and send to each victims
     * @throws IOException
     */
    public void run() throws IOException {

        List<Person> personlist = _prankGenerator.readVictimList(new FileInputStream(ConfigurationManager.getPropertyValue("victimFile")));
        List<Group> groupList = _prankGenerator.buildRandomGroups(personlist, 8);
        List<Mail> mailList = _prankGenerator.createRandomMails(groupList, new FileInputStream(ConfigurationManager.getPropertyValue("messagesFile")));

        for(Mail mail : mailList) {
            sendMail(mail);
        }
    }
}
