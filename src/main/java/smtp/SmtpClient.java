package smtp;

import config.ConfigurationManager;
import mail.Mail;
import prank.PrankGenerator;

import java.io.*;
import java.net.Socket;

public class SmtpClient {
    private Socket _client;

    private BufferedReader smtpInputStram;
    private BufferedWriter smtpOutputStream;

    public void displayServerResponse() throws  IOException{
        if(smtpInputStram != null)
            System.out.println(smtpInputStram.readLine());
    }

    public void connect() throws IOException {
        try{
            _client = new Socket(ConfigurationManager.getPropertyValue("smtpServerAdress"),Integer.parseInt(ConfigurationManager.getPropertyValue("smtpServerPort")));
            displayServerResponse();
        }catch (IOException e){
            throw new IOException("could not connect to server: " + ConfigurationManager.getPropertyValue("smtpServerAdress") + " at port " + ConfigurationManager.getPropertyValue("smtpServerPort"));
        }
    }
    public void sendMessage(){

    }
    public void sendSmtpRequest() throws IOException {

        ConfigurationManager.readFromInputStream(new FileInputStream("./config/config.properties"));
        PrankGenerator prankGenerator = new PrankGenerator();

        Mail mailToSend = prankGenerator.buildPrank(new FileInputStream("./config/victimList.utf8"), new FileInputStream("./config/messages.utf8"));


        String smtpServerAddress = "localhost";
        int smtpServerPort = Integer.parseInt(ConfigurationManager.getPropertyValue("smtpServerPort"));
        String ehlo = "EHLO localhost\r\n";
        String mailFrom = "MAIL FROM:<robin.demarta@heig-vd.ch>\r\n";
        String rcptTo = "RCPT To:<emmanuel.janssens@heig-vd.ch>\r\n";
        String rcptTo1 = "RCPT To:<kevin.janssens@heig-vd.ch>\r\n";
        String rcptTo2 = "RCPT To:<didier.janssens@heig-vd.ch>\r\n";

        String data = "DATA\r\n";
        String from = "From: \"Robin\"<robin.demarta@heig-vd.ch>\r\n";
        String to = "To: \"Manu\"<emmanuel.janssens@heig-vd.ch>,\"Kevin\"<kevin.janssens@heig-vd.ch>,\"Didier\"<didier.janssens@heig-vd.ch>\r\n";
        String subject = "Subject : Bonjour comment on va ?\r\n\r\n";
        String content = "Hèllo world. \r\n.\r\n";
        String quit = "QUIT\r\n";

        Socket clientSocket = new Socket(smtpServerAddress, smtpServerPort);
        BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8")); //version bufferisée
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8")); //version bufferisée
        //InputStreamReader is = new InputStreamReader(clientSocket.getInputStream()); //version non bufferisée
        //OutputStreamWriter os = new OutputStreamWriter(clientSocket.getOutputStream()); //version non bufferisée

        if(clientSocket != null && is != null && os != null){
            System.out.println(is.readLine());
            os.write(ehlo);
            os.flush();

            System.out.println(is.readLine());
            os.write(mailFrom);
            os.flush();

            System.out.println(is.readLine());
            os.write(rcptTo);
            os.flush();

            System.out.println(is.readLine());
            os.write(rcptTo1);
            os.flush();

            System.out.println(is.readLine());
            os.write(rcptTo2);
            os.flush();

            System.out.println(is.readLine());
            os.write(data);
            os.flush();

            System.out.println(is.readLine());
            os.write(from);
            os.flush();

            System.out.println(is.readLine());
            os.write(to);
            os.flush();

            System.out.println(is.readLine());
            os.write(subject);
            os.flush();

            os.write(content);
            os.flush();

            System.out.println(is.readLine());
            os.write(quit);
            os.flush();

            System.out.println(is.readLine());

        }
    }
}
