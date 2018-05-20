package server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static void main(String[] args) throws Exception {
	    // set port
	    int port = 55533;
	    ServerSocket server = new ServerSocket(port);
	    
	    System.out.println("waiting");
	    Socket socket = server.accept();
	    
	    InputStream inputStream = socket.getInputStream();
	    byte[] bytes = new byte[1024];
	    int len;
	    StringBuilder sb = new StringBuilder();
	    while ((len = inputStream.read(bytes)) != -1) {
	      sb.append(new String(bytes, 0, len,"UTF-8"));
	    }
	    System.out.println("get message from client: " + sb);
	    inputStream.close();
	    socket.close();
	    server.close();
	  }
}
