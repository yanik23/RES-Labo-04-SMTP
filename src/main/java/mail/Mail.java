/**
 * Model of a mail class
 *  @author Janssens Emmanuel
 *  @author Lange Yanik
 */
package mail;

import java.util.List;

public class Mail {

    private Message message;



    private Person from;
    private List<Person> to;
    private List<Person> cc;


    public Mail(Message message, Person from, List<Person> to, List<Person> cc){
        this.message = message;
        this.from = from;
        this.to = to;
        this.cc = cc;
    }


    public Message getMessage() {
        return message;
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
                message +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
