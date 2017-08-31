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
import java.util.*;
import java.io.Serializable;

public class ContactListManager implements Serializable {

    private static List<Contact> myContactList = new ArrayList();

    public ContactListManager() {
        super();
    }

    public static List<Contact> getContactList() {
        return myContactList;
    }

    public void setContactList(List<Contact> myContactList) {
        this.myContactList = myContactList;
    }

    public static void addContact(Contact c) {
        myContactList.add(c);
        save(myContactList);
    }

    public static void deletecontact(Contact c) {
        myContactList.remove(c);
        save(myContactList);
    }

    public static void updateContact(Contact c) {
        myContactList.add(c);
        save(myContactList);
    }

    public static void save(List<Contact> E) {
        try {
            FileOutputStream out = new FileOutputStream("Contacts.out");
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(E);
            oos.flush();
            oos.close();
            out.close();
        } catch (IOException ex) {
            System.out.println("problem serializing " + ex);
        }
    }

    public static void read() {
        try {
            FileInputStream in = new FileInputStream("Contacts.out");
            ObjectInputStream ois = new ObjectInputStream(in);
            myContactList = (List<Contact>) (ois.readObject());
            in.close();
            ois.close();

        } catch (IOException ex) {
            myContactList = new ArrayList<Contact>();
        } catch (Exception ex) {
            System.out.println("problem serializing " + ex);
        }
    }

    public static Contact getContact(Contact c) {
        for (int i = 0; i < myContactList.size(); i++) {
            if (c == myContactList.get(i)) {
                return myContactList.get(i);
            }
        }
        return null;
    }
}
