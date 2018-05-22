package ve572.l1.regina;

import static java.lang.System.out;

public class MyClient {
    public final static String IP = "127.0.0.1";
    public final static int port = 45000;

    public static void main(String args[]) {
    	
    	
        ClientServerSocket theClient;
        theClient = new ClientServerSocket(IP, port);
        theClient.startClient();
        theClient.sendString("test;");
        theClient.sendString("END;");
        theClient.sendString("test2;");
    }
}