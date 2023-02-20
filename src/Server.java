import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Server {

    private final int port;
    private boolean loop = false;
    public final static int BUFFER_LENGTH = 65535;

    public Server(int port) throws SocketException, UnknownHostException {
        this.port = port;
    }

    public void run() throws SocketException {
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            loop = true;
            while(loop) {
                main(datagramSocket);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void main(DatagramSocket datagramSocket) throws IOException {
        byte[] buffer = new byte[BUFFER_LENGTH];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        datagramSocket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        received = received.substring(0 , received.length()-1);
        String message;

        try {
            message = Integer.toString(calculate(received));
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }

        byte[] response = (message + "\n").getBytes();
        DatagramPacket out = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
        datagramSocket.send(out);
    }

    private int calculate(String string) {
        String[] split = string.split("-");

        System.out.println(Arrays.toString(split));

        if (split.length > 2) {
            throw new IllegalArgumentException("To many operator signs, only one allowed.");
        }
        if (split.length < 2) {
            throw new IllegalArgumentException("Wrong format, too few arguments");
        }

        int sum;

        try {
            int num1 = Integer.parseInt(split[0]);
            int num2 = Integer.parseInt(split[1]);
            sum = num1 - num2;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input must be integers.");
        }
        return sum;
    }

}
