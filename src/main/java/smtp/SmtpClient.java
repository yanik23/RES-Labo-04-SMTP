package smtp;

import config.ConfigurationManager;
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

    private final String EHLO = "EHLO";
    private final String MAILFROM = "MAIL FROM: ";
    private final String RCPTTO = "RCPT TO: ";
    private final  String DATA = "DATA";
    private final String HEADER_FROM = "From: ";
    private final String HEADER_TO = "To: ";
    private final String QUIT = "QUIT";

    private Socket _client;

    private BufferedReader _smtpInputStram;
    private BufferedWriter _smtpOutputStream;

    PrankGenerator prankGenerator = new PrankGenerator();

    public SmtpClient() throws IOException {
        ConfigurationManager.readFromInputStream(new FileInputStream("./config/app.config"));
    }

    private void displayServerResponse() throws IOException {
        String s = _smtpInputStram.readLine();
        System.out.println(s);
    }

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
    public void sendCommand(String cmdLine, String args) throws IOException {
        _smtpOutputStream.write(cmdLine + " " + args + "\r\n");
        _smtpOutputStream.flush();

        displayServerResponse();

    }
    private String writeHeader(Mail mail){
        StringBuilder cmd = new StringBuilder();
        cmd.append("Subject: =?UTF-8?B?"+ Base64.getEncoder().encodeToString(mail.getSubject().getBytes(StandardCharsets.UTF_8)) +"?=\r\n");
        cmd.append("Content-Type: text/plain; charset=utf-8\r\n");
        return cmd.toString();
    }
    private void writeContent(Mail mail) throws IOException {
        StringBuilder cmd = new StringBuilder(writeHeader(mail));
        cmd.append(mail.getContent());
        cmd.append("\r\n.\r\n");
        sendCommand(cmd.toString(),"");
    }

    private void sendMail(Mail mail) throws IOException {
        connect();

        if (_smtpOutputStream != null && _smtpInputStram != null && _smtpInputStram != null) {

            //send EHLO
            sendCommand(EHLO, ConfigurationManager.getPropertyValue("smtpServerAdress"));

            //Write mail writer
            sendCommand(MAILFROM,mail.getFrom().getEmail());

            //write each recipient
            for(Person rcptTo : mail.getTo()){
                sendCommand(RCPTTO,rcptTo.getEmail());
            }

            sendCommand(DATA,"");

            sendCommand(HEADER_FROM,mail.getFrom().getEmail());

            StringBuilder argsTo = new StringBuilder();
            for(Person personTo : mail.getTo()){
                argsTo.append(personTo.getEmail()).append(",");
            }
            sendCommand(HEADER_TO,argsTo.toString());

            writeContent(mail);

            sendCommand(QUIT,"");

        }
        _smtpInputStram.close();
        _smtpOutputStream.close();
        _client.close();
    }
    public void run() throws IOException {

        List<Person> personlist = new ArrayList<Person>();

        personlist = prankGenerator.readVictimList(new FileInputStream("./config/victimList.txt"));
        List<Group> groupList = prankGenerator.buildRandomGroups(personlist, 8);
        List<Mail> mailList = prankGenerator.createRandomMails(groupList, new FileInputStream("./config/messages.txt"));

        for(Mail mail : mailList) {
            sendMail(mail);
        }
    }
}
