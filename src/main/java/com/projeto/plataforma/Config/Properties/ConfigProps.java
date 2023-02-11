package com.projeto.plataforma.Config.Properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class ConfigProps {

    private String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private String configPropsPath = rootPath + "config.properties";

    public Properties props = getProps();

    private Properties getProps() {
        try {
            InputStream input = new FileInputStream(configPropsPath);
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (Exception e) {
            return new Properties();
        }
    }

    public String getProp(String propKey) {
        return props.getProperty(propKey);
    }
}
