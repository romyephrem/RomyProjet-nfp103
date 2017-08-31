/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupListManager implements Serializable {

    private static List<Group> myGroupListe = new ArrayList();

    public GroupListManager() {
        super();
    }

    public static List<Group> getGroupList() {
        return myGroupListe;
    }

    public void setGroupList(List<Group> myGroupListe) {
        this.myGroupListe = myGroupListe;
    }

    public static void addGroup(Group A) {
        myGroupListe.add(A);
        save(myGroupListe);
    }

    public static void delGroup(Group A) {
        myGroupListe.remove(A);
        save(myGroupListe);
    }

    public static void updateGroup(Group A) {
    }

    public static void save(List<Group> E) {
        try {
            FileOutputStream out = new FileOutputStream("Groups.out");
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(E);
            oos.flush();
            oos.close();
            out.close();
        } catch (IOException ex) {
            System.out.println("problem serializing " + ex);
        } catch (Exception ex) {
            System.out.println("problem serializing " + ex);
        }
    }

    public static void read() {
        try {
            FileInputStream in = new FileInputStream("Groups.out");
            ObjectInputStream ois = new ObjectInputStream(in);
            myGroupListe = (List<Group>) (ois.readObject());
            in.close();
            ois.close();
        } catch (IOException ex) {
            myGroupListe = new ArrayList<Group>();
        } catch (Exception ex) {
            System.out.println("problem serializing " + ex);
        }
    }
}
