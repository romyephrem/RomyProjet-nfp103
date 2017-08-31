package projetaccovromy.Server;
/**
 *
 * @author Romy
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SACAThread extends Thread {
private Socket socket = null;
    private static final Radar radar = new Radar();
    private int vitx, vity, vitz, cap, alt;
    
    public SACAThread(Socket socket) {
        this.socket = socket;
    }
    
    public void EntrezCoordonne(String nomAvion)
    { synchronized(System.in)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez Vx: (" + nomAvion + ")");
            vitx = scanner.nextInt();
            System.out.println("Entrez Vy: (" + nomAvion + ")");
            vity = scanner.nextInt();
            System.out.println("Entrez Vz: (" + nomAvion + ")");
            vitz = scanner.nextInt();
            System.out.println("Entrez le cap: (" + nomAvion + ")");
            cap = scanner.nextInt();
            System.out.println("Entrez l'altitude: (" + nomAvion + ")");
            alt = scanner.nextInt();
            System.out.println();
        }
    }
        
    public void run() {
        Controleur controle = new Controleur();
        String[] msg;
        String s;        
        
        try
        {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            msg = reader.readLine().split("-");
            radar.Afficher(msg[0], msg[1], msg[2], "0", "0", "0", "0", "0", "0", "0", "0", "Depart");
            
            EntrezCoordonne(msg[1]);            
            s = controle.RenouvelerCoordonnees(vitx, vity, vitz, cap, alt);//changer les coordonnées de l'avion
            
            while (true) {                
                writer.println(s);
                
                String [] pos = reader.readLine().split("-");;
                String [] vit = s.split("-");
                
                if((pos.length >= 4)&&(pos[3].equals("crash"))){
                    radar.Afficher(msg[0], msg[1], msg[2], pos[0], pos[1],pos[2], vit[0], vit[1], vit[2], vit[3], vit[4],"Crash de l'avion");
                    reader.close();
                    writer.close();
                    socket.close();
                }
                else if((pos.length >= 4)&&(pos[3].equals("fin"))){
                    radar.Afficher(msg[0], msg[1], msg[2], pos[0], pos[1],pos[2], vit[0], vit[1], vit[2], vit[3], vit[4],"Arrivé à destination");
                    reader.close();
                    writer.close();
                    socket.close();
                }
                else if ((pos.length >= 4)&&(pos[3].equals("vitesse")))
                {
                    radar.Afficher(msg[0], msg[1], msg[2], "0", "0", "0", "0", "0", "0", "0", "0","Vitesse incorrect(très petite/grande)");
                    reader.close();
                    writer.close();
                    socket.close();
                }
                else{
                    radar.Afficher(msg[0], msg[1], msg[2], pos[0], pos[1],pos[2], vit[0], vit[1], vit[2], vit[3], vit[4],"Dans l'air");
                    
                    EntrezCoordonne(msg[1]);
                    s = controle.RenouvelerCoordonnees(vitx, vity, vitz, cap, alt);//changer les coordonnées de l'avion
                }

            }
            
        } catch (IOException e) {
            System.out.println("terminaison du thread");
            System.out.println();
            System.out.println();
        }
    }
}
