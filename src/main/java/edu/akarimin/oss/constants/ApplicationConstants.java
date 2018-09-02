package edu.akarimin.oss.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author akarimin
 */

public final class ApplicationConstants {

    private static Properties CONFIG = new Properties();
    private static final String NAME = "application.properties";
    private static final String AMAZON_URL = "%s?method=%s&mkt=%d&l=en_US&x=String&search-alias=%s&q=%s";
    public static final String USER_AGENT = "User-Agent";

    static {
        try {
            InputStream inputStream = ApplicationConstants.class.getClassLoader().getResourceAsStream(NAME);
            CONFIG.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAmazonCompletionHost() {
        return CONFIG.getProperty("amazon.completion.host");
    }

    public static String getAmazonCompletionUrl() {
        return AMAZON_URL;
    }

    public static String getUserAgent() {
        return USER_AGENT;
    }
}
