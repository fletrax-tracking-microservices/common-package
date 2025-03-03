package com.fletrax.tracking.commonpackage.configuration.lang;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Configuration
public class LanguageSource {

    private final Map<String, Map<String, Object>> messages;

    public LanguageSource() {
        this.messages = loadYamlMessages();
    }

    private Map<String, Map<String, Object>> loadYamlMessages() {
        try {
            Yaml yaml = new Yaml(); // Fixed Constructor
            Map<String, Map<String, Object>> translations = new java.util.HashMap<>();

            String[] languages = { "en", "ar", "tr" };
            for (String lang : languages) {
                InputStream inputStream = new ClassPathResource("lang/" + lang + ".yaml").getInputStream();
                Map<String, Object> data = yaml.load(inputStream);

                InputStream inputCommonStream = new ClassPathResource("lang/common_" + lang + ".yaml").getInputStream();
                Map<String, Object> commonData = yaml.load(inputCommonStream);

                // Merge commonData into data
                if (commonData != null) {
                    commonData.forEach(data::putIfAbsent);
                }

                translations.put(lang, data);
            }
            return translations;
        } catch (Exception e) {
            throw new RuntimeException("Error loading translation files", e);
        }
    }

    public String getMessage(String key, String lang, String... args) {
        String[] keys = key.split("\\.");
        Object value = messages.getOrDefault(lang, messages.get("en"));

        for (String k : keys) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(k);
            } else {
                return key; // Return key if not found
            }
        }

        if (value instanceof String) {
            String message = (String) value;
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{" + i + "}", args[i]);
            }
            return message;
        }
        return key;
    }
}
