package com.example.snake.utils;

import com.example.snake.models.JNDIfields;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class JNDIHelper {

    public static final String PROVIDER_URL = "file:C:/Programiranje/";
    public static final String CONFIGURATION_FILE_NAME = "configuration.properties";

    private static InitialContext context;

    private static InitialContext getInitialContext() throws NamingException {
        if (context == null) {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
            props.setProperty(Context.PROVIDER_URL, PROVIDER_URL );
            context = new InitialContext(props);
        }
        return context;
    }

    public static String getConfigurationParameter(String paramName) throws NamingException, IOException {

        Object configurationFileName = getInitialContext().lookup(CONFIGURATION_FILE_NAME);
        Properties configurationProperties = new Properties();
        configurationProperties.load(new FileReader(configurationFileName.toString()));
        return configurationProperties.getProperty(paramName);
    }
}
