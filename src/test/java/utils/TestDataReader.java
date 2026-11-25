package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDataReader {

    private final String jsonContent;

    public TestDataReader(String resourceFileName) {
        try {
            Path path = Path.of("src", "test", "resources", resourceFileName);
            this.jsonContent = Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data file: " + resourceFileName, e);
        }
    }

    public String getString(String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(jsonContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Key not found in JSON: " + key);
    }
}
