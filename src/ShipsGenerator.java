import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ShipsGenerator {
    public List<Ship> generate() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("example.json")) {
            // Создайте тип для представления списка объектов DataObject
            Type listType = new TypeToken<List<Ship>>() {}.getType();

            // Прочитайте JSON-массив в список объектов DataObject
            List<Ship> ships = gson.fromJson(reader, listType);

            // Выведите данные
            for (Ship ship : ships) {
//                System.out.println("n: " + ship.getN());
                System.out.println("Points:");
//                for (Point point : ship.getPoints()) {
//                    System.out.println("  x: " + point.getX() + ", y: " + point.getY());
//                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
