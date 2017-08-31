/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactlist;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

public class ViewContactWindow {

    private JFrame frame = new JFrame("Project NFA035");
    private JLabel Up, Le, fn, ln, c, mod, phone;
    private JPanel east, west, north, center, firstname, lastname, title, city, bigpanel, south2, check, mod2, p, tab;
    private JTextField F, L, C;
    String[][] Number = new String[7][2];
    String[] Header = {"Region Code", "Phone Number"};
    private JTable table = new JTable(Number, Header);
    String Fn, Ln, cityy;
    private JScrollPane scrollPane;
    private List<Group> myGroupList;

    public ViewContactWindow(String nom, String prenom, String Ville, String[][] number) {
        frame.setPreferredSize(new Dimension(500, 400));
        frame.setResizable(false);


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                Number[i][j] = (String) number[i][j];
            }
        }
        F = new JTextField(nom, 20);
        F.setEditable(false);
        F.setDisabledTextColor(Color.GRAY);
        L = new JTextField(prenom, 20);
        L.setEditable(false);
        L.setDisabledTextColor(Color.GRAY);
        C = new JTextField(Ville, 23);
        C.setEditable(false);
        C.setDisabledTextColor(Color.GRAY);

        Up = new JLabel("Gestion Des Contacts");
        Up.setForeground(Color.blue);
        Le = new JLabel("Uppdate Contact");
        Le.setForeground(Color.red);
        fn = new JLabel("FirstName");
        ln = new JLabel("LastName");
        c = new JLabel("City");
        phone = new JLabel("Phone Numbers");
        mod = new JLabel("Contact Groups");

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
        mod2 = new JPanel();
        tab = new JPanel();
        p = new JPanel();
        title = new JPanel();

        GroupListManager.read();
        myGroupList = (ArrayList<Group>) GroupListManager.getGroupList();
        
        check.setLayout(new BoxLayout(check, BoxLayout.PAGE_AXIS));
        
        for (int i = 0; i < myGroupList.size(); i++) {
            for (int j = 0; j < myGroupList.get(i).getListofContacts().size(); j++) {
                String name = myGroupList.get(i).getListofContacts().get(j).getnom();
                String lastname = myGroupList.get(i).getListofContacts().get(j).getprenom();
                String city = myGroupList.get(i).getListofContacts().get(j).getville();
                String [][] num = myGroupList.get(i).getListofContacts().get(j).getphonenumber().clone();
                if (name.equals(nom) && lastname.equals(prenom) && city.equals(Ville) ) {
                     String s = (String) myGroupList.get(i).getnom();
                     JCheckBox S = new JCheckBox(myGroupList.get(i).getnom());
                     S.setSelected(true);
                     S.setEnabled(false);
                     check.add(S);
                }
            }
        }

        p.add(phone);

        mod2.add(mod);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setMaximumSize(new Dimension(280, 150));
        tab.add(table.getTableHeader(), BorderLayout.NORTH);
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
        tab.add(scrollPane, BorderLayout.CENTER);
        table.setEnabled(false);

        title.setLayout(new BoxLayout(title, BoxLayout.PAGE_AXIS));
        title.add(p);
        title.add(tab);

        south2.setLayout(new BoxLayout(south2, BoxLayout.PAGE_AXIS));
        south2.add(mod2);
        south2.add(check);

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

    private class TempListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object ob = e.getSource();
        }
    }
}
