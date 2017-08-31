package projetaccovromy;
/**
 *
 * @author Romy
 */
public final class Avion {
    private int idAv ;
    private String nomAv ;
    private int cap ;
    private int altitude ;
    private int posX , posY , posZ ;
    private final int destX;
    private final int destY;
    private final int destZ;
    private int vitX , vitY , vitZ ;
    private String status ;
    private int vitesse;
    private int[] position = new int[3] ;
    //private int[] vitess = new int[3] ;
    public static int altMin = 0 ;
    public static final int altMax = 20000 ;
    public static final int vitMax = 1000 ;
    public static final int vitMin = 200 ; 
    public int depX;
    public int depY;
    public static final int PAUSE = 2;
    public static final String localHost = "localhost";
    public static final int portNb = 2000 ;
    
 public Avion ( int id, String nom, int destX, int destY, int destZ ) {
        this.destX = destX;
        this.destY = destY;
        this.destZ = destZ;
        this.setIdAv(id);
        this.setNomAv(nom);
        this.setPosition(0, 0 , 0);
        this.setVitesse(0);
        this.setVitesse(0, 0, 0);
        this.setCap(0) ;
        this.setAltitude(0);
        this.status = "off" ;
    }
 
    public int getIdAv (){
        return this.idAv;
    }
 
    public void setIdAv ( int id ){
        this.idAv = id;
    }
    
    public String getNomAv (){
        return this.nomAv;
    }
 
    public void setNomAv ( String nom ){
        this.nomAv = nom ;
    }
    
    public String getDestination()
    {
        return this.destX + ";" + this.destY + ";" + this.destZ; 
    }
    
    public String getPosition(){
        return (getPosX() + " , " + getPosY() + " , " + getPosZ());
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public int getPosZ(){
        return this.posZ;
    }
    
    public void setPosition (int vitX , int vitY , int vitZ){
        this.posX = this.posX + (vitX) ;
        this.posY = this.posY + (vitY) ;
        this.posZ = this.posZ + (vitZ) ;
        position[0]= (int) this.posX ;
        position[1]= (int) this.posY ;
        position[2]= (int) this.posZ ;
    }
    
    public double getVitesse(){
        return this.vitesse;
    }
    
    public void setVitesse(int vit){
        this.vitesse = vit;
    }
    
    public void setVitesse(int vitX, int vitY, int vitZ)
    {
        this.vitX = vitX;
        this.vitY = vitY;
        this.vitZ = vitZ;
    }
    
    public String getStatus (){
        return this.status;
    }
    
    public void setStatus(){
        if ( (int) position[0]==0 && (int) position[1]==0 && (int) position[2]==0 )
            this.status="off";
        else this.status="on";
            }
    
    public void setStatus(String x){
       if(x.equals("on"))
           this.status = "on";
       else this.status="off";
    }
    
    public double getCap (){
        return this.cap;
    }
 
    public void setCap ( int c ){
        this.cap = c;
    }
    
    public double getAltitude (){
        return this.altitude;
    }
 
    public void setAltitude ( int a ){
        this.altitude = a;
    }
    
    public void AfficherInfo(){
    System.out.println("le nom de l'avion est : " + getNomAv());
    System.out.println("l'id de l'avion est : " + getIdAv());
    System.out.println("le status de l'avion est : " + getStatus());
    System.out.println("la position de l'avion est : " + getPosition());
    System.out.println("l'attitude de l'avion est : " + getAltitude());
    System.out.println("l'angle de l'avion est : " + getCap()); 
    System.out.println("la vitesse de l'avion est : " + getVitesse());
        
    }
    
     // initialisation d'avion aleatoirement
    public void initialiserAvion (){
        // status = on -> avion en vol mode
        this.status = "on" ;
        // je donne des coordonnees aleatoires pour positionner l'avion
        posX = (int) (1000 + Math.random() % 1000) ;
        posY = (int) (1000 + Math.random() % 1000) ;
        posZ = (int) (1000 + Math.random() % 1000) ;
        // attitude aleatoire et angle aleatoire
        this.altitude = (int) (900 + Math.random() % 100) ;
        this.cap = (int) (Math.random() % 360) ;
        // vitesse aleatoire de l'avion
        this.vitesse = (int) (600 + Math.random() % 200) ;
        
    }
    
    public int ChangerCoordonnees(int vitX, int vitY, int vitZ, int cap, int alt)
    {
        this.setVitesse(vitX, vitY, vitZ);
        this.setCap(cap);
        this.setAltitude(alt);
        this.setPosition(vitX, vitY, vitZ);
        int vitesse = (int) Math.sqrt(vitX*vitX + vitY*vitY + vitZ*vitZ);
        
        return changerVitesse(vitesse);        
    }
    
    public int changerVitesse(int v){
        if (this.posX == this.destX && this.posY == this.destY && this.posZ == this.destZ)
        {
            System.out.println("arrivé à destination");
            return -1;
        }
        else if (this.altitude > altMax)
        {
            System.out.println("crash de l'avion");
            return -2;
        }
        else if(status.equals("on") && (vitesse + v) <=vitMax && (vitesse + v)>=vitMin) {
               this.vitesse = vitesse + v ;
               System.out.println("l'avion est dans l'air");
               return 0;
            }
        else if ( status.equals("on") && (vitesse + v)<=vitMin){
                this.vitesse = 0 ;
                this.status = "off";
                System.out.println("crash de l'avion");
                return -2;
            } 
        else if ( status.equals("on") && (vitesse + v)>=vitMax){
                this.vitesse = 0 ;
                this.status = "off";
                System.out.println("crash de l'avion");
                return -2;
            } 
        else if(status.equals("off") && (vitesse + v)>=vitMin && (vitesse + v)<=vitMax){
             //demarrage de l'avion avec une vitesse v
                this.setVitesse((int) (this.getVitesse()+v)) ;
                this.setStatus("on") ;
                System.out.println("l'avion a demarré");
                return 0;
            }
        else if (status.equals("off") && (vitesse + v) >= vitMax){
            this.vitesse = 0;
            System.out.println("l'avion ne peut pas demarrer avec cette grande vitesse");
            return -3;
        }
        else if (status.equals("off") && (vitesse + v) <= vitMin){
            this.vitesse = 0;
            System.out.println("l'avion ne peut pas demarrer avec cette petite vitesse");
            return -3;
        } 
        else{
            System.out.println("la valeur entrer pour changer la vitesse est refusé");
            return -3;
        }
   }
    
    public void changerCap(int c){
        //changement de l'angle de l'avion
        if((this.cap >=0) && (c <360))
            this.cap=c ;
        else System.out.println("l'avion est inactive"); 
    }
    
    public void changerAltitude(int i){
        if(altitude< 0)
            this.altitude=0;
        else if (i>altMax) 
            this.altitude = altMax ;
        else this.altitude = i;
    }
    
    public void calculDeplacement(){
        float cosinus , sinus ;
        float depX , depY ;
        int nb ;
        if(this.vitesse < vitMin){
            System.out.println("vitesse trop faible : crash de l'avion");
            //fermer communication 
            //exit(3);
        }
        cosinus = (float) Math.cos(this.getCap()* 2 * Math.PI / 360 );
        sinus = (float) Math.sin(this.cap * 2 * Math.PI / 360 );
        depX = cosinus * vitesse * 10 / vitMin;
        depY = sinus * vitesse * 10 / vitMin;
        if ((depX > 0 )&& (depX < 1)) depX = 1 ; 
        if ((depX < 0 )&& (depX > -1)) depX = -1 ; 
        if ((depY > 0 )&& (depY < 1)) depX = 1 ; 
        if ((depY < 0 )&& (depY < 1)) depX = 1 ; 
        this.setPosition((int)posX+ (int)depX , (int)posY+ (int)depY, posZ);
        this.AfficherInfo();
    }
    
//    public void seDeplacer(){
//        while(1){
//            sleep(PAUSE);
//            calculDeplacement();
//            envoyerCaracteristiques();
//        }     }
}
