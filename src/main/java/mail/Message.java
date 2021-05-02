/**
 * Model of a message
 * @author Janssens Emmanuel
 * @author Lange Yanik
 */
package mail;

public class Message {
    private String subject;
    private String content;

    public Message(String subject, String content){
        this.subject = subject;
        this.content = content;
    }
    public String getSubject(){
        return subject;
    }
    public String getContent(){
        return content;
    }

    @Override
    public String toString() {
        return "subject = " + subject + ", content = " + content;
    }
}
