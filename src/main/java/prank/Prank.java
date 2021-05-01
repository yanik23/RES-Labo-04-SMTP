package prank;

import mail.Group;
import mail.Mail;

public class Prank {
    private Mail mail;
    private Group group;



    public Prank(Mail m, Group g){
        mail = m;
        group = g;
    }

    public Mail getMail() {
        return mail;
    }

    public Group getGroup() {
        return group;
    }
}
