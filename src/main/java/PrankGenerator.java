import mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrankGenerator {


    public PrankGenerator(){

    }
    public List<Person> readVictimList(InputStream victims) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(victims));
        List<Person> victimList = new ArrayList<Person>();
        String line;
        while ((line = br.readLine()) != null && line.length() != 0) {

            String firstName = line.substring(0,line.indexOf("."));
            String lastName = line.substring(line.indexOf(".")+1, line.indexOf("@"));

            Person p = new Person(firstName, lastName, line);
            victimList.add(p);
        }
        return victimList;
    }

}
