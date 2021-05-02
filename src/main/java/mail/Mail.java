/**
 * Model of a mail class
 *  @author Janssens Emmanuel
 *  @author Lange Yanik
 */
package mail;

import java.util.List;

public class Mail {

    private String subject;
    private String content;
    private Person from;
    private List<Person> to;
    private List<Person> cc;


    public Mail(String subject, String content, Person from, List<Person> to, List<Person> cc){
        this.subject = subject;
        this.content = content;
        this.from = from;
        this.to = to;
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Person getFrom() {
        return from;
    }

    public List<Person> getTo() {
        return to;
    }

    public List<Person> getCc() {
        return cc;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
