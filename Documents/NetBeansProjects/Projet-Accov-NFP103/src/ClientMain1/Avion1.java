package ClientMain1;
/**
 *
 * @author Romy 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import projet.accov.nfp103.Avion;

public class Avion1 {
         // For TCP connection
    public static Socket s;
    public static BufferedReader reader;
    public static int port = 2024;
    public static PrintWriter writer;
    public static Avion avion;

    public static void main(String[] args) throws IOException, InterruptedException {
        avion = new Avion(1, "Avion1", 1500, 2000, 3000);
        s = new Socket("127.0.0.1", port);
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        writer = new PrintWriter (s.getOutputStream(), true);
        String[] msg;
        writer.println(avion.getIdAv() + "-" + avion.getNomAv() + "-" + avion.getDestination() + "-" + avion.getPosX() + "-" + avion.getPosY() + "-" + avion.getPosZ());
        
       while (true)
        {
            msg = reader.readLine().split("-");
            int res = avion.ChangerCoordonnees(Integer.parseInt(msg[0]), Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]), Integer.parseInt(msg[4]));
            if(res == -3)
            {
                writer.println(avion.getPosX() + "-" + avion.getPosY() + "-" + avion.getPosZ() + "-vitesse");
                break;
            }
            if(res == -2)
            {
                writer.println(avion.getPosX() + "-" + avion.getPosY() + "-" + avion.getPosZ() + "-crash");
                break;
            }
            if(res == -1)
            {
                writer.println(avion.getPosX() + "-" + avion.getPosY() + "-" + avion.getPosZ() + "-fin");
                break;
            }
            sleep(3000);

            writer.println(avion.getPosX() + "-" + avion.getPosY() + "-" + avion.getPosZ());           
        }
        
        reader.close();
        writer.close();
        s.close();
    }  
}
