package net.ezchat.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JsonUtils {

    public JsonObject deserialize(byte[] content) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(content);
                JsonReader reader = Json.createReader(bais)) {
            return reader.readObject();
        }
    }

}
