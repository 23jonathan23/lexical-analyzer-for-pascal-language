package src.presentation;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {
    private static final String CONFIG_FILE = ".\\resources\\config.properties";

    public static Properties getProperties() {
        Properties props = new Properties();

        try {
            props.load(new FileInputStream(CONFIG_FILE));

            return props;

        } catch(Exception e) { return null;}
    }
}
