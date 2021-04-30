package mail;

import java.util.List;

public class Group {

    private Person from;
    private List<Person> to;

    public Group(Person from, List<Person> to){
        this.from = from;
        this.to = to;
    }

    public Person getFrom(){
        return from;
    }

    public List<Person> getTo(){
        return to;
    }

    @Override
    public String toString() {
        return "from = " + from + "\r\n"  + " to =  " + to + "\r\n";
    }
}
