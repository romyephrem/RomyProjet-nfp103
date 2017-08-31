package Server;
/**
 *
 * @author Romy
 */

public class Controleur {
    public String RenouvelerCoordonnees() {
        return Math.random()*1000 + "-" + Math.random()*1000 + "-" + Math.random()*1000 + "-" + Math.random()*100 + "-" + Math.random()*20000;
    }
    
    public String RenouvelerCoordonnees(int vitx, int vity, int vitz, int cap, int alt) {
        return vitx + "-" + vity + "-" + vitz + "-" + cap + "-" + alt;
    }
}
