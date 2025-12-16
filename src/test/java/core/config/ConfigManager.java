package core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        load();
    }

    private static void load() {
        String environment = System.getProperty("environment", "dev");
        String fileName = String.format("config-%s.properties", environment);

        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new RuntimeException("Arquivo de configuração não encontrado: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar arquivo de configuração: " + fileName, e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }


    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
