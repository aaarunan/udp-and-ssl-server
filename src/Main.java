import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        Server server = null;
        int port = 4445;
        try {
            server = new Server(port);
        } catch (SocketException e) {
            System.err.println("Kunne ikke starte server!\n" + e.getMessage());
        } catch (UnknownHostException e) {
            System.err.println("Kunne ikke finne host!\n" + e.getMessage());
        }
        System.out.printf("Server started on port %d\n", port);
        try {
            assert server != null;
            server.run();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}