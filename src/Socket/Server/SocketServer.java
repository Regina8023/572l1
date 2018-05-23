package Socket.Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
	
public class SocketServer {
    public final static int port = 9090;
    public final static String xml_file = "decoding.xml";
    public final static String bin_file = "data_1.bin";
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(port);
        int counter = 0;
        try {
            while (true) {
                System.out.println("Client counter:" + counter);
                Socket socket = listener.accept();
                counter ++;
                String command;
                try {	
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    PrintWriter output =
                        new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
                    
                    System.out.println("Connection build."); 
                    while ((command = ReadCommand(dis)) != null) {
                        command = command.trim();
                        String[] tokens = ParseParameters(command);
                        if("END".equals(tokens[0])) {
                            output.println("OK");
                            socket.close();
                            System.out.println("End connection.");
                            break;
                        }
                        Execute(tokens, dis, output);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                } finally{
                    try {
                        if(!socket.isClosed()){
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        finally {
            listener.close();
        }
    }
    public static String ReadCommand(DataInputStream dis) throws IOException{
        String command = "";
        int r;
        while((r = dis.readByte()) != -1){
            char next = (char) r;
            if(r == ';') {
                return command;
            }
            if(Character.isLetter(next) || Character.isDigit(next) || Character.isWhitespace(next)){
                command += next;
            }
        }
        return null;
    }
    public static String[] ParseParameters(String command) {
        
        String[] tokens = command.split("\\s+");
        System.out.print("Client:");
        for (String token : tokens) {
            System.out.print(token+" ");
        }
        System.out.println("");        
        return tokens;
    }
    public static void Execute(String[] args, DataInputStream dis, PrintWriter output) {
        if(args[0] == null) {
            output.println("Invalid input.");
            return;
        }
        else if ("BEGIN".equals(args[0])) {
            output.println("OK");
            return;
        }
        else if ("SIZE".equals(args[0])) {
            output.println("OK");
            try {
                if ("XML".equals(args[1])) {
                    RecieveFile(xml_file, dis, Integer.valueOf(args[2]).intValue());
                }
                else if ("BIN".equals(args[1])) {
                    RecieveFile(bin_file, dis, Integer.valueOf(args[2]).intValue());
                }
                else {
                    output.println("Invalid input.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        else if ("QUERY".equals(args[0])) {
            // FIXME:
            // eg. args[1] = MAX; args[2] =startTime
            // output String result
            output.println("OK");
            String result = "RESULT startTime OF Time 75 ms FROM 42 POINTS";
            output.println(result);
            return;
        }
        else if ("END".equals(args[0])) {
            output.println("end not OK");
            return;
        }
        else {
            output.println("Invalid input.");
            return;
        }
    }
    public static void RecieveFile(String filename, DataInputStream dis, int size) throws IOException{
        File file = new File(filename);
        if(!file.exists()) file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
        System.out.println("Start recieving file.");
        byte[] buffer = new byte[4096];
		int block = 0;
		int done = 0;
		int res = size;
		while((block = dis.read(buffer, 0, Math.min(buffer.length, res))) > 0) {
			done += block;
			res -= block;
            fos.write(buffer, 0, block);
            fos.flush();
		}
        System.out.println("read " + done + " characters.");
		
		fos.close();
        /*
        FileWriter fw = new FileWriter(file);
        char[] buffer = new char[4096];
		int block = 0;
		int done = 0;
		int res = size;
		while((block = input.read(buffer, 0, Math.min(buffer.length, res))) > 0) {
			done += block;
			res -= block;
            fw.write(buffer, 0, block);
            fw.flush();
		}
        System.out.println("read " + done + " characters.");
        
        fw.close();
        */
    }
}