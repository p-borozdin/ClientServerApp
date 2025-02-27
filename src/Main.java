import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var server = new Server();
        try {
            server.start();
        }
        catch (RuntimeException _) {
            server.close();
        }
    }
}
