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

public class Group implements Serializable{

    private String nom;
    private String description;
    private List<Contact> Liste = new ArrayList<Contact>();

    public Group(String n, String d , ArrayList <Contact> c) {
        nom = n;
        description = d;
        Liste = c;
    }
    public String getnom() {
        return nom;
    }

    public String getdescription() {
        return description;
    }

    public List<Contact> getListofContacts() {
        return Liste;
    }

    public void setnom(String n) {
        nom = n;
    }

    public void setdescription(String d) {
        description = d;
    }

    public void setListofContacts(List<Contact> d) {
        for (int i=0;i<d.size();i++)
        {
            Liste.set(i, d.get(i));
        }
    }
}
