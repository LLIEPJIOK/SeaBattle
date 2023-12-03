package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private boolean isFinishing = false;
    private int port;

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket firstClientSocket = serverSocket.accept();
            BufferedReader firstClientIn = new BufferedReader(new InputStreamReader(firstClientSocket.getInputStream()));
            PrintWriter firstClientOut = new PrintWriter(firstClientSocket.getOutputStream(), true);

            Socket secondClientSocket = serverSocket.accept();
            BufferedReader secondClientIn = new BufferedReader(new InputStreamReader(firstClientSocket.getInputStream()));
            PrintWriter secondClientOut = new PrintWriter(firstClientSocket.getOutputStream(), true);
            while (!isFinishing) {

                // Читаем JSON-сообщение от клиента
//                while ((inputLine = in.readLine()) != null)
//                String jsonString = in.readLine();
//                JSONObject request = new JSONObject(jsonString);
//
//                // Обработка различных типов сообщений
//                String messageType = request.getString("type");
//                switch (messageType) {
//                    case "login":
//                        String username = request.getJSONObject("data").getString("username");
//                        String password = request.getJSONObject("data").getString("password");
//                        // Здесь могут быть дополнительные действия для обработки входа в систему
//                        System.out.println("Получено сообщение login от " + username);
//                        break;
//                    case "message":
//                        String text = request.getJSONObject("data").getString("text");
//                        // Здесь могут быть дополнительные действия для обработки текстового сообщения
//                        System.out.println("Получено текстовое сообщение: " + text);
//                        break;
//                    default:
//                        System.out.println("Неизвестный тип сообщения");
//                        break;
//                }

                // Отправляем ответ клиенту
//                JSONObject response = new JSONObject();
//                response.put("status", "success");
//                response.put("message", "Сообщение получено успешно");
//                out.println(response.toString());

                // Закрываем ресурсы для текущего клиента
//                in.close();
//                out.close();
//                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        isFinishing = true;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        startServer();
    }
}
