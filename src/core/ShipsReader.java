package core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class ShipsReader {
    public static List<Ship> read(String fileName) {
        Gson gson = new Gson();
        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(ShipsReader.class.getResourceAsStream("ShipsTemplates/" + fileName)))) {
            Type listType = new TypeToken<List<Ship>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
