package contactlist;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;

public class NewContactWindow {

    private JFrame New = new JFrame("Project NFA035");
    private JLabel gestion, newC, Fname, Lname, City, phonenumber, add, Cancel, Save;
    private JPanel north, west, east, center, Firstname, Lastname, city, pnumber, bigpanel, center2, add2, south2, tab, buttons;
    private JTextField Fn, Ln, C;
    String[][] Number = new String[7][2];
    String[] ColumnNames = {"Region Code", "Phone Number"};
    private JTable table = new JTable(Number, ColumnNames);
    private JButton save, cancel, yes, no;
    String[] options = {"yes", "no"};
    private JScrollPane scrollPane;
    private List<Group> myGroupList;
    private JCheckBox none;
    private String s = "";

    public NewContactWindow() {
        New.setPreferredSize(new Dimension(500, 495));
        New.setResizable(false);


        gestion = new JLabel("Gestion Des Contacts");
        gestion.setForeground(Color.blue);
        newC = new JLabel("New Contact");
        newC.setForeground(Color.red);
        Fname = new JLabel("FirstName");
        Lname = new JLabel("LastName");
        City = new JLabel("City");
        phonenumber = new JLabel("Phone Number");
        add = new JLabel("Add the contact to Groups");
        Cancel = new JLabel("Vous Voulez Quitter Cette Fenetre ?");
        Save = new JLabel("Un contact doit avoir un nom , un prenom et un numero du telephone ");

        save = new JButton("Save");
        save.addActionListener(new NewListener());
        cancel = new JButton("Cancel");
        cancel.addActionListener(new NewListener());

        none = new JCheckBox("No Groups");
        none.setActionCommand("Fred");
        none.addItemListener(Listener);

        tab = new JPanel();
        north = new JPanel();
        east = new JPanel();
        west = new JPanel();
        west.setPreferredSize(new Dimension(75, 100));
        center = new JPanel();
        city = new JPanel();
        Lastname = new JPanel();
        Firstname = new JPanel();
        pnumber = new JPanel();
        bigpanel = new JPanel();
        center2 = new JPanel();
        add2 = new JPanel();
        south2 = new JPanel();
        buttons = new JPanel();

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        if (table.getCellEditor() != null) {
            table.getCellEditor().stopCellEditing();
        }
        if (table.getCellEditor() != null)     table.getCellEditor().stopCellEditing();
        table.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table.setPreferredScrollableViewportSize(new Dimension(100, 100));
        tab.add(table.getTableHeader(), BorderLayout.NORTH);
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
        tab.add(scrollPane, BorderLayout.CENTER);

        buttons.add(save);
        buttons.add(cancel);

        GroupListManager.read();
        myGroupList = GroupListManager.getGroupList();
        south2.setLayout(new BoxLayout(south2, BoxLayout.PAGE_AXIS));
        south2.add(add2);
        south2.add(none);
        none.setSelected(true);
        for (int i = 0; i < myGroupList.size(); i++) {
            String s = (String) myGroupList.get(i).getnom();
            JCheckBox S = new JCheckBox(myGroupList.get(i).getnom());
            S.setActionCommand(myGroupList.get(i).getnom());
            S.addItemListener(Listener);
            south2.add(S);
        }
        south2.add(buttons);

        add2.add(add);

        center2.setLayout(new BoxLayout(center2, BoxLayout.PAGE_AXIS));
        center2.add(pnumber);
        center2.add(tab);

        Fn = new JTextField(20);
        Ln = new JTextField(20);
        C = new JTextField(24);

        Firstname.add(Fname);
        Firstname.add(Fn);

        Lastname.add(Lname);
        Lastname.add(Ln);

        city.add(City);
        city.add(C);

        north.add(gestion);

        pnumber.add(phonenumber);

        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
        west.add(Box.createVerticalStrut(20));
        west.add(newC);

        bigpanel.setLayout(new BoxLayout(bigpanel, BoxLayout.PAGE_AXIS));
        bigpanel.add(Firstname);
        bigpanel.add(Lastname);
        bigpanel.add(city);

        east.setLayout(new BorderLayout());
        east.add(bigpanel, BorderLayout.NORTH);
        east.add(center2, BorderLayout.CENTER);
        east.add(south2, BorderLayout.SOUTH);

        center.setBackground(Color.cyan);

        New.add(north, BorderLayout.NORTH);
        New.add(west, BorderLayout.WEST);
        New.add(east, BorderLayout.EAST);
        New.add(center, BorderLayout.CENTER);
        New.pack();
        New.setVisible(true);
    }

    private class NewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object ob = e.getSource();

            if (ob == save) {
                String[] F = s.split(" ");
                List<Contact> newList = new ArrayList<Contact>();
                ArrayList<String> List = new ArrayList();
                String nom = (String) Fn.getText();
                String pre = (String) Ln.getText();
                String city = (String) C.getText();
                String[][] numero = Number.clone();
                Contact c = new Contact(nom, pre, city, numero);
                String li = "";

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
//                System.out.println(li);
                    li = "";
                }


                boolean TV = getTableValue(table);

                if (nom.equals("") || pre.equals("") || TV != false) {
                    JOptionPane.showMessageDialog(null, Save, "Error Message ", JOptionPane.ERROR_MESSAGE);
                } else {
                    ContactListManager.addContact(c);
                    for (int i = 0; i < F.length; i++) {
                        for (int j = 0; j < myGroupList.size(); j++) {
                            if (F[i].equals(myGroupList.get(j).getnom())) {
                                myGroupList.get(j).getListofContacts().add(c);
                                GroupListManager.save(myGroupList);
                            }
                        }
                    }
                }
            } else if (ob == cancel) {
                int dialogResult = JOptionPane.showOptionDialog(null, Cancel, "Confirm Message ", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    new ContactsWindow();
                } else if (dialogResult == JOptionPane.NO_OPTION) {
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
                    none.setSelected(false);
                } else if (myGroupList.get(i).getnom().equals(((JCheckBox) source).getActionCommand()) && !((JCheckBox) source).isSelected()) {
                    String no = myGroupList.get(i).getnom() + " ";
                    String news;
                    news = s.replace(no, "");
                    s = news ;
                }
            }
        }
    };
}
