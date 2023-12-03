package core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class ShipsGenerator {
    public static List<Ship> generate() {
        Gson gson = new Gson();

        try (InputStreamReader  reader = new InputStreamReader(Objects.requireNonNull(ShipsGenerator.class.getResourceAsStream("ShipsTemplates/1.json")))) {
            Type listType = new TypeToken<List<Ship>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
