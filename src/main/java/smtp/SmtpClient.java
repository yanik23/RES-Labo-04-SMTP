package smtp;

import java.io.*;
import java.net.Socket;

public class SmtpClient {
    public void sendSmtpRequest() throws IOException {
        String smtpServerAddress = "localhost";
        int smtpServerPort = 25;
        String ehlo = "ELHO\r\n";
        String mailFrom = "MAIL FROM: yanik.lange@heig-vd.ch\r\n";
        String rcptTo = "RCPT To: emmanuel.janssens@heig-vd.ch\r\n";
        String data = "DATA\r\n";
        String from = "From: robin.demarta@heig-vd.ch\r\n";
        String to = "To: emmanuel.janssens@heig-vd.ch\r\n";
        String subject = "Bonjour comment on va ?";
        String endingSubject = "\r\n.\r\n";
        String quit = "QUIT\r\n";

        Socket clientSocket = new Socket(smtpServerAddress, smtpServerPort);
        BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //version bufferisée
        BufferedWriter os = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())); //version bufferisée
        //InputStreamReader is = new InputStreamReader(clientSocket.getInputStream()); //version non bufferisée
        //OutputStreamWriter os = new OutputStreamWriter(clientSocket.getOutputStream()); //version non bufferisée

        if(clientSocket != null && is != null && os != null){
            os.write(ehlo);
            os.flush();
            os.write(mailFrom);
            os.flush();
            os.write(rcptTo);
            os.flush();
            os.write(data);
            os.flush();
            os.write(from);
            os.flush();
            os.write(to);
            os.flush();
            os.write(subject);
            os.flush();
            os.write(endingSubject);
            os.flush();
            os.write(quit);
            os.flush();
        }
    }
}
