import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketClient {
    public final static String host = "192.168.137.1";
    public final static int port = 9090;
    public final static String xml_file = "decoding.xml";
    public final static String bin_file = "data_1.bin";
    public static void main(String[] args) throws IOException,InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            System.out.println("Unnable to build connetction.");
            System.exit(0);
        }
        PrintWriter output =
            new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        
        output.print("BEGIN;");
        output.flush();
        output.print("SIZE XML 17055;");
        output.flush();
        SendFile(xml_file, socket.getOutputStream());
        output.flush();
        TimeUnit.SECONDS.sleep(2);
        output.print("SIZE XML 17055;");
        output.flush();
        SendFile(xml_file, socket.getOutputStream());
        output.flush();
        TimeUnit.SECONDS.sleep(2);        
        output.print("SIZE BIN 252084;");
        output.flush();
        SendFile(bin_file, socket.getOutputStream());
        output.flush();
        TimeUnit.SECONDS.sleep(2);
        output.print("QUERY MAX startTime;");
        output.flush();
        output.print("QUERY AVG totalLength;");
        output.flush();
        output.print("END;");
        output.flush();
        
        String command;
        while ((command = input.readLine()) != null) {
            System.out.println("Server:"+command);
        }
        socket.close();
        System.exit(0);
    }
    public static void SendFile(String filename, OutputStream os) throws IOException{
        File file = new File(filename);
        int size = (int) file.length();
        System.out.println(size);
        DataOutputStream dos = new DataOutputStream(os);
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