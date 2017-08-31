package Server;
/**
 *
 * @author Romy
 */
import java.util.ArrayList;

public class Radar {
  
    private static final Object sync = new Object();
    public ArrayList<ArrayList<String>> listAvions = new ArrayList<>();
    String[] info = {"id","nom","destination","posX","posY","posiZ","cap","vitX","vitY","vitZ","altitude","situation"};
    
    private boolean Collision(ArrayList<ArrayList<String>> listAvions)
    {
        for(int i=0;i<listAvions.size();i++)
        {
            for(int j=i+1;j<listAvions.size();j++)
            {
                if (listAvions.get(i).get(3).equals(listAvions.get(j).get(3)) && listAvions.get(i).get(4).equals(listAvions.get(j).get(4)) 
                        && listAvions.get(i).get(5).equals(listAvions.get(j).get(5)) && listAvions.get(i).get(10).equals(listAvions.get(j).get(10)))
                    return true;
            }
        }
        return false;
    }
    
    public void Afficher(String id, String nom, String destination, String posX, String posY, String posZ, String vitX, String vitY, String vitZ, String cap, String alt, String text)
    {   synchronized (sync)
        {
            boolean avionPresent =false;
            for (int i=0;i<listAvions.size();i++){
                for(int j=0;j<info.length;j++){
                    if(listAvions.get(i).get(j).equals(id)){
                        ArrayList<String> avion = new ArrayList<>();
                        avion.add(id);
                        avion.add(nom);
                        avion.add(destination);
                        avion.add(posX);
                        avion.add(posY);
                        avion.add(posZ);
                        avion.add(cap);
                        avion.add(vitX);
                        avion.add(vitY);
                        avion.add(vitZ);
                        avion.add(alt);
                        avion.add(text);
                        listAvions.set(i, avion);

                        avionPresent=true;
                        break;
                    }                
                }
            }

            if (!avionPresent)
            {
                ArrayList<String> avion = new ArrayList<>();
                    avion.add(id);
                    avion.add(nom);
                    avion.add(destination);
                    avion.add(posX);
                    avion.add(posY);
                    avion.add(posZ);
                    avion.add(cap);
                    avion.add(vitX);
                    avion.add(vitY);
                    avion.add(vitZ);
                    avion.add(alt);
                    avion.add(text);
                listAvions.add(avion);
            }
            
            System.out.println("---------------------");
            for(int i=0;i<listAvions.size();i++)
            {
                for(int j=0;j<info.length;j++)
                {
                    System.out.println(info[j] + " : " + listAvions.get(i).get(j));
                }
                System.out.println("---------------------");
            }
            if(Collision(listAvions))
                System.out.println("Collision détectée");
            System.out.println();
        }
    }
}
