package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);
            System.out.println("Подключено к серверу.");

            // Получаем потоки ввода и вывода для обмена данными с сервером
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Отправляем JSON-сообщение серверу (пример: сообщение login)
//            JSONObject loginMessage = new JSONObject();
//            loginMessage.put("type", "login");
//            JSONObject loginData = new JSONObject();
//            loginData.put("username", "user123");
//            loginData.put("password", "secret");
//            loginMessage.put("data", loginData);
//            out.println(loginMessage.toString());

            // Читаем ответ от сервера
//            String responseString = in.readLine();
//            JSONObject response = new JSONObject(responseString);

            // Обработка ответа
//            String status = response.getString("status");
//            String message = response.getString("message");
//            System.out.println("Статус ответа: " + status);
//            System.out.println("Сообщение от сервера: " + message);

            // Закрываем ресурсы
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
