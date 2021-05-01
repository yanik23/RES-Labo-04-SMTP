package mail;

import java.util.List;

public class Mail {

    private String subject;
    private String content;
    private Person from;
    private List<Person> to;

    public Mail(String subject, String content, Person from, List<Person> to){
        this.subject = subject;
        this.content = content;
        this.from = from;
        this.to = to;
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
