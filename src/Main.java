import server.HttpTaskServer;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            HttpTaskServer server = new HttpTaskServer();
            server.start();
        } catch (IOException e) {
            System.out.println("Ошибка при запуске сервера");
        }
    }
}
