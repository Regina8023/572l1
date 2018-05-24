package handler;
import java.io.*;
import java.net.Socket;
//import java.util.concurrent.TimeUnit;

public class SocketClient {
    public final static String host = "127.0.0.1";
    public final static int port = 9090;
    public final static String xml_file = "/Users/haoxin/Documents/JI/VE572/Lab/l1/validate.xml";
    public final static String bin_file = "/Users/haoxin/Documents/JI/VE572/Lab/l1/validate.bin";
    public static void main(String[] args) throws IOException,InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println("Unnable to build connetction.");
            System.exit(0);
        }
        BufferedReader input =
            new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeBytes("BEGIN;");
        dos.flush();
        dos.writeBytes("SIZE XML 11120;");
        dos.flush();
        SendFile(xml_file, dos);
        dos.flush();
        dos.writeBytes("SIZE BIN 170952;");
        dos.flush();
        SendFile(bin_file, dos);
        dos.flush();
        dos.writeBytes("QUERY MAX Ronald_Drump;");
        dos.flush();
        dos.writeBytes("QUERY AVG 😂😏😏😏🙄;");
        dos.flush();
        dos.writeBytes("END;");
        dos.flush();
        
        String command;
        while ((command = input.readLine()) != null) {
            System.out.println("Server:"+command);
        }
        socket.close();
        System.exit(0);
    }
    public static void SendFile(String filename, DataOutputStream dos) throws IOException{
        File file = new File(filename);
        int size = (int) file.length();
        System.out.println(size);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];
        
        int block = 0;
		int done = 0;
		int res = size;
		while((block = fis.read(buffer, 0, Math.min(buffer.length, res))) > 0) {
			done += block;
			res -= block;
            dos.write(buffer, 0, block);
            dos.flush();
		}

		fis.close();
        /*
        FileReader fr = new FileReader(file);
        int r;
        while((r=fr.read()) != -1) {
            char c = (char) r;
            output.print(c);
            System.out.print(c);
        }
        output.flush();
        System.out.flush();
        fr.close();
        */
    }
}