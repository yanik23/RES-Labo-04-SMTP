
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager{

    private static class ConfigurationProperty {
        String name;
        String value;
    }

    public static List<ConfigurationProperty> properties = new ArrayList<>();

    public static void readFromInputStream(InputStream inputStream)throws IOException {

    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String name = line.substring(0,line.indexOf("="));
                String value = line.substring(line.indexOf("=")+1);

                ConfigurationProperty prop = new ConfigurationProperty();
                prop.name = name;
                prop.value = value;
                properties.add(prop);

            }
        }
    }

    public static String getPropertyValue(String name)
    {
        for(ConfigurationProperty p : properties)
        {
            if(p.name.equals(name))
                return p.value;
        }
        return null;
    }
}