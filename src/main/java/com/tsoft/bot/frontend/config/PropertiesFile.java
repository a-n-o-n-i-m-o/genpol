package com.tsoft.bot.frontend.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
    private static Properties properties = null;
    public static PropertiesFile instance = null;
    private static final String propertyFilePath= "properties/genpol.properties";

    public static PropertiesFile getInstance() {
        instance = new PropertiesFile();
        return instance;
    }
    private PropertiesFile(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }


    public String getApplicationUrl(String company) {
        String url = properties.getProperty("genpol.url." + company.toUpperCase());
        if(url != null) return url;
        else throw new RuntimeException("la url " + company + " no fue definida en el archivo: " + propertyFilePath);
    }
    public String getDBProperties(String property) {
        String data = properties.getProperty("genpol.DB." + property.toUpperCase());
        if(data != null) return data;
        else throw new RuntimeException("la propiedad de la Base de Datos: " + property + " no fue definida en el archivo:" + propertyFilePath);
    }
    public String getDBProperties(String property, String core, String company) {
        String data = properties.getProperty(core.toUpperCase() + ".DB."  + company.toUpperCase() + "." + property.toUpperCase());
        if(data != null) return data;
        else throw new RuntimeException("la propiedad de la Base de Datos: " + property + " no fue definida en el archivo:" + propertyFilePath);
    }
}
