package Server;
/**
 *
 * @author Romy
 */
import java.io.IOException;
import java.net.ServerSocket;

public class SACA {
     public static int port = 2024;       
    
    public static void main(String args[]) throws IOException {
        try(ServerSocket ss = new ServerSocket(port))
        {
            while(true)
            {
                new SACAThread(ss.accept()).start();       
            }
        }catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
    }
}
