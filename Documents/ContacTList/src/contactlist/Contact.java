/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist ;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.*;

public class Contact implements Serializable {
    private String nom ;
    private String prenom ;
    private String ville ;
    private String [] [] phonenumber ;
    public Contact (String n , String p , String v , String[][] S) 
    {
        nom = n;
        prenom = p;
        ville = v ;
        phonenumber = S ;
    }
    
    public String toString(){return this.nom +" " +this.prenom + " -  " + this.ville;}
    public String getnom () { return nom ;}
    public String getprenom () { return prenom ;}
    public String getville () { return ville ;}
    public String[][] getphonenumber () {return phonenumber ;}
    
    public void setnom (String n ) { nom = n ;}
    public void setprenom (String p ) { prenom = p ;}
    public void setville (String v ) { ville = v ;}
    public void setphonenumber (String [][] S) 
    {
       for (int i=0;i<7;i++)
       {
            for (int j=0;j<2;j++)
            {
               phonenumber[i][j]= S[i][j]; 
            }
       }
    }
}
