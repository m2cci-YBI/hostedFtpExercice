package config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigurationProperties {
    private ConfigurationProperties() {}
    private static final Properties props = new Properties();
    private static String jwtSecret;
    private static long jwtExpirationMs;

    static {
        try {
            try (InputStream in = ConfigurationProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (in == null) {
                    throw new RuntimeException("Cannot find application.properties on the classpath");
                }
                props.load(in);
            }

            jwtSecret = required("jwt.secret.key");

            String exp = props.getProperty("jwt.expiration.ms");
            if (exp == null || exp.trim().isEmpty()) {
                jwtExpirationMs = 3600000L; // default 1 hour
            } else {
                try {
                    jwtExpirationMs = Long.parseLong(exp.trim());
                } catch (NumberFormatException nfe) {
                    jwtExpirationMs = 3600000L; // fallback
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("ConfigurationProperties init error", e);
        }
    }

    private static String required(String key) {
        String v = props.getProperty(key);
        if (v == null || v.trim().isEmpty()) {
            throw new RuntimeException("Missing required property: " + key);
        }
        return v;
    }

    public static String getJwtSecret() { return jwtSecret; }
    public static long getJwtExpirationMs() { return jwtExpirationMs; }

}
