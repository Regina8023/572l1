package ve572.l1.regina;

import static java.lang.System.out;

public class MyServer {
    public final static String IP = "127.0.0.1";
    public final static int port = 45000;

    public static void main(String args[]) {
        ClientServerSocket theServer;
        theServer = new ClientServerSocket(IP, port);
        theServer.startServer();
        while (true) {
            String temp = theServer.recvString();
            out.println(temp);
        	if (temp.equals("END")) return;
        }
    }
}