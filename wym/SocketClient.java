import java.io.*;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        String host = "10.162.180.167";
        int port = 9090;
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println("Unnable to build connetction.");
            System.exit(0);
        }
        PrintWriter output =
            new PrintWriter(socket.getOutputStream(), true);
        output.println("BEGIN");
        BufferedReader input =
            new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String command;
        while ((command = input.readLine()) != null) {
            System.out.println("Server:"+command);
        }
        socket.close();
        System.exit(0);
    }
}