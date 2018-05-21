// SocketServer
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(9090);
        ExecutorService threadPool = Executors.newFixedThreadPool(100); 
        try {
            while (true) {
                Socket socket = listener.accept();
                Runnable thread=()->{
                    try {
                        BufferedReader input =
                            new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter output =
                            new PrintWriter(socket.getOutputStream(), true);
                        String command;
                        System.out.println("Connection build."); 
                        while ((command = input.readLine()) != null  && command != "END") {
                            String[] tokens = ParseParameters(command);
                            for (int i = 0; i < tokens.length; i++) {
                                System.out.println(tokens[i]);
                                output.println(tokens[i]);
                            }
                        }
                        if("END".equals(command)){
                            System.out.println("End connection.");
                            output.println("OK");
                        }
                        else{
                            System.out.println("Not end correctly.");
                            output.println("Not end correctly.");
                        }
                    } catch(Exception e){
                        System.out.println("Error:"+e);
                    } finally{
                        try {
                            socket.close();
                        } catch (Exception e) {
                            System.out.println("Error:"+e);
                        }
                    }
                };
                threadPool.submit(thread);
            }
        }
        finally {
            listener.close();
        }
    }
    public static String[] ParseParameters(String command) {
        String delims = "[ ]+";
        String[] tokens = command.split(delims);
        return tokens;
    }
}