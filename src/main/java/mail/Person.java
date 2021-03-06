/**
 * Model of a person/victim
   * @author Janssens Emmanuel
   * @author Lange Yanik
 */
package mail;

public class Person {
    private String name;
    private String surname;
    private String email;

    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString() {
        return "name = " + name + ", surname = " + surname  + ", email = " + email + "\r\n";
    }
}
