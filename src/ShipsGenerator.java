import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ShipsGenerator {
    public static List<Ship> generate() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("resources/ShipsTemplates/1.json")) {
            Type listType = new TypeToken<List<Ship>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
