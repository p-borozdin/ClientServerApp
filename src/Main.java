import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            var server = new Server();
            server.start();
        }
        catch (RuntimeException _) {}
    }
}
