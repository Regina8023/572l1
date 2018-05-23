package ve572.l1.regina;

import static java.lang.System.out;

public class MyServer {
    public final static String IP = "127.0.0.1";
    public final static int port = 45000;

    public static void main(String args[]) {
		ClientServerSocket theServer;
		String buffer = "";
		try {
			theServer = new ClientServerSocket(IP, port);
			theServer.startServer();
			while (!buffer.equals("BEGIN")) {
				buffer = theServer.recvString();
			}
			buffer = theServer.recvString();
			String[] instruction = new String[3];
			instruction = buffer.split(" ");
	        if (instruction[0]!="SIZE" || instruction[1]!="XML") throw new Exception("SIZE XML error"); 
			while (true) {
				out.println(buffer);
				if (buffer.equals("END"))
					return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}