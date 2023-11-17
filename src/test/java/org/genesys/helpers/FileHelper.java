package org.genesys.helpers;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.genesys.TestRunner;

import java.io.File;
import java.io.IOException;

public class FileHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String filePath = "src/test/resources/";
    private static final Logger logger = LogManager.getLogger(FileHelper.class);

    public static JsonNode getParsedJson(String filename) {
        try {
            JsonNode rootNode = null;
            rootNode = objectMapper.readTree(new File(filePath + filename));
            return rootNode;
        } catch (IOException ex) {
            logger.error("An error has occured: " + ex.getMessage());
        }
        return null;
    }
}
