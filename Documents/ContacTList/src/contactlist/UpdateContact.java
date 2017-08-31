/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class UpdateContact {

    private JFrame frame = new JFrame("Project NFA035");
    private JLabel Up, Le, fn, ln, c, mod, phone, Cancel, Save;
    private JPanel east, west, north, center, firstname, lastname, city, title, bigpanel, south2, check, buttons, mod2, p, tab;
    private JTextField F, L, C;
    private JButton save = new JButton("Save");
    private JButton cancel = new JButton("Cancel");
    String[][] Number = new String[7][2];
    String[] ColumnNames = {"Region Code", "Phone Number"};
    private JTable table = new JTable(Number, ColumnNames);
    String[] options = {"yes", "no"};
    private Contact E;
    private java.util.List<Group> myGroupList;
    private JScrollPane scrollPane;
    private String s = "";
    private String removed = "";

    public UpdateContact(String nom, String prenom, String Ville, String[][] number, Contact D) {
        frame.setPreferredSize(new Dimension(450, 490));
        frame.setResizable(false);


        E = D;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                Number[i][j] = (String) number[i][j];
            }
        }

        cancel.addActionListener(new upListener());
        save.addActionListener(new upListener());

        F = new JTextField(nom, 20);
        L = new JTextField(prenom, 20);
        C = new JTextField(Ville, 23);

        Up = new JLabel("Gestion Des Contacts");
        Up.setForeground(Color.blue);
        Le = new JLabel("Uppdate Contact");
        Le.setForeground(Color.red);
        fn = new JLabel("FirstName");
        ln = new JLabel("LastName");
        c = new JLabel("City");
        phone = new JLabel("Phone Numbers");
        mod = new JLabel("Modifier contact groups");
        Cancel = new JLabel("Vous Voulez Quitter Cette Fenetre ?");
        Save = new JLabel("Un contact doit avoir un nom , un prenom et un numero du telephone ");

        east = new JPanel();
        west = new JPanel();
        north = new JPanel();
        center = new JPanel();
        firstname = new JPanel();
        lastname = new JPanel();
        city = new JPanel();
        bigpanel = new JPanel();
        south2 = new JPanel();
        check = new JPanel();
        buttons = new JPanel();
        mod2 = new JPanel();
        tab = new JPanel();
        p = new JPanel();
        title = new JPanel();

        p.add(phone);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setMaximumSize(new Dimension(280, 150));
        table.setPreferredScrollableViewportSize(new Dimension(200, 100));
        if (table.getCellEditor() != null) {
            table.getCellEditor().stopCellEditing();
        }
        tab.add(table.getTableHeader(), BorderLayout.NORTH);
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
        tab.add(scrollPane, BorderLayout.CENTER);

        title.setLayout(new BoxLayout(title, BoxLayout.PAGE_AXIS));
        title.add(p);
        title.add(tab);

        mod2.add(mod);

        buttons.add(save);
        buttons.add(cancel);

        GroupListManager.read();
        myGroupList = GroupListManager.getGroupList();
        check.setLayout(new BoxLayout(check, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < myGroupList.size(); i++) {
            String s1 = (String) myGroupList.get(i).getnom();
            JCheckBox S1 = new JCheckBox(myGroupList.get(i).getnom());
            check.add(S1);
            S1.addItemListener(Listener);
            for (int j = 0; j < myGroupList.get(i).getListofContacts().size(); j++) {
                String name = myGroupList.get(i).getListofContacts().get(j).getnom();
                String lastname = myGroupList.get(i).getListofContacts().get(j).getprenom();
                String city = myGroupList.get(i).getListofContacts().get(j).getville();
                String[][] num = myGroupList.get(i).getListofContacts().get(j).getphonenumber();
//                boolean res = samenumber(D.getphonenumber() , num);
//                System.out.println(res);
                if (name.equals(nom) && lastname.equals(prenom) && city.equals(Ville)) {
                    S1.setSelected(true);
                    break;
                }
            }
        }
        south2.setLayout(new BoxLayout(south2, BoxLayout.PAGE_AXIS));
        south2.add(mod2);
        south2.add(check);
        south2.add(buttons);

        firstname.add(fn);
        firstname.add(F);

        lastname.add(ln);
        lastname.add(L);

        city.add(c);
        city.add(C);

        bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.PAGE_AXIS));
        bigpanel.add(firstname);
        bigpanel.add(lastname);
        bigpanel.add(city);

        east.setLayout(new BorderLayout());
        east.add(bigpanel, BorderLayout.NORTH);
        east.add(title, BorderLayout.CENTER);
        east.add(south2, BorderLayout.SOUTH);

        north.add(Up);

        west.setPreferredSize(new Dimension(100, 200));
        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
        west.add(Box.createVerticalStrut(20));
        west.add(Le);

        center.setBackground(Color.cyan);

        frame.add(north, BorderLayout.NORTH);
        frame.add(west, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        frame.add(east, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }

    private class upListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] f = s.split(" ");
            String[] r = removed.split(" ");
            ArrayList<String> List = new ArrayList();
            String nom = (String) F.getText();
            String pre = (String) L.getText();
            String city = (String) C.getText();
            String[][] numero = Number.clone();
            Contact c = new Contact(nom, pre, city, numero);
            String li = "";

            Object ob = e.getSource();
            if (ob == cancel) {
                int dialogResult = JOptionPane.showOptionDialog(null, Cancel, "Confirm Message ", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    new ContactsWindow();
                } else if (dialogResult == JOptionPane.NO_OPTION) {
                }
            } else if (ob == save) {

                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 2; j++) {
                        String s = "";
                        s = (String) table.getValueAt(i, j);
                        if (s != null) {
                            li += table.getValueAt(i, j).toString();
                        } else {
                            li += null;
                        }
                    }
                    List.add(li);
                    li = "";
                }

                boolean TV = getTableValue(table);

                if (nom.equals("") || pre.equals("") || TV != false) {
                    JOptionPane.showMessageDialog(null, Save, "Error Message ", JOptionPane.ERROR_MESSAGE);
                } else {
                    ContactListManager.deletecontact(E);
                    ContactListManager.addContact(c);
                    for (int i = 0; i < f.length; i++) {
                        for (int j = 0; j < myGroupList.size(); j++) {
                            boolean b = containsContact((ArrayList<Contact>) myGroupList.get(j).getListofContacts(), c);
                            if (f[i].equals(myGroupList.get(j).getnom()) && b == false) {
                                myGroupList.get(j).getListofContacts().add(c);
                                GroupListManager.save(myGroupList);
                            }
                        }
                    }
                    for (int i = 0; i < r.length; i++) {
                        for (int j = 0; j < myGroupList.size(); j++) {
                            boolean b = containsContact((ArrayList<Contact>) myGroupList.get(j).getListofContacts(), c);
                            if (r[i].equals(myGroupList.get(j).getnom()) && b == true) {
                                ArrayList<Contact> newliste = new ArrayList<Contact>();
                                newliste = (ArrayList<Contact>) myGroupList.get(j).getListofContacts();
                                newliste.remove(c);
                                myGroupList.get(j).setListofContacts(newliste);
                                GroupListManager.save(myGroupList);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean getTableValue(JTable table) {
        boolean b = true;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                String s = "";
                s = (String) table.getValueAt(i, j);
                if (s != null) {
                    b = false;
                    return b;
                }
            }

        }
        return b;
    }
    ItemListener Listener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent ie) {
            for (int i = 0; i < myGroupList.size(); i++) {
                Object source = ie.getItemSelectable();
                if (myGroupList.get(i).getnom().equals(((JCheckBox) source).getActionCommand()) && ((JCheckBox) source).isSelected()) {
                    s += myGroupList.get(i).getnom() + " ";
                    String newremoved;
                    String del = myGroupList.get(i).getnom() + " ";
                    newremoved = removed.replace(del, "");
                    removed = newremoved;
                } else if (myGroupList.get(i).getnom().equals(((JCheckBox) source).getActionCommand()) && !((JCheckBox) source).isSelected()) {
                    String no = myGroupList.get(i).getnom() + " ";
                    String news;
                    news = s.replace(no, "");
                    s = news;
                    removed += no + " ";
                }
            }
        }
    };

    public boolean samenumber(String[][] s, String[][] f) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                if (s[i][j] != (String) f[i][j]) {
                    return false;
                }

            }
        }
        return true;
    }

    public boolean containsContact(ArrayList<Contact> liste, Contact c) {
        String nomc = c.getnom();
        String prenomc = c.getprenom();
        String villec = c.getville();
        for (int i = 0; i < liste.size(); i++) {
            for (int j = 0; j < liste.size(); j++) {
                String name = liste.get(j).getnom();
                String lastname = liste.get(j).getprenom();
                String city = liste.get(j).getville();
                String[][] num = liste.get(j).getphonenumber();
                if (nomc.equals(name) && prenomc.equals(lastname) && villec.equals(city)) {
                    return true;
                }
            }
        }
        return false;
    }
}
