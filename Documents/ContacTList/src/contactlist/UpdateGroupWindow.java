package contactlist;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class UpdateGroupWindow {

    private JFrame F = new JFrame("Project NFA035");
    private JPanel north, east, west, center, descrip, groupe, title, north2, buttons, tab;
    private JButton Save, Cancel;
    private JLabel gest, add, desc, grp, V, msg;
    private JTextField descr, group;
    String[][] Number = new String[14][3];
    String[] ColumnNames = {"Contact Name", "City", "Add To Group"};
    private JTable table;
    String[] options = {"yes", "no"};
    private JScrollPane scrollPane;
    private List<Contact> myContactList;
    private int leng = 0;
    private Group a;

    public UpdateGroupWindow(Group p) {
        F.setPreferredSize(new Dimension(600, 500));
        F.setResizable(false);


        a = p;

        group = new JTextField(a.getnom(), 20);
        descr = new JTextField(a.getdescription(), 21);

        Save = new JButton("Save Group");
        Save.addActionListener(new upgListener());
        Cancel = new JButton("Cancel");
        Cancel.addActionListener(new upgListener());

        gest = new JLabel("Gestion Des Contacts");
        gest.setForeground(Color.blue);
        add = new JLabel("Update Group");
        add.setForeground(Color.red);
        desc = new JLabel("Description");
        grp = new JLabel("Group Name");
        V = new JLabel("Vous Voulez Quitter Cette Fenetre ? ");
        msg = new JLabel("Un Group Name est obligatoire");

        north = new JPanel();
        east = new JPanel();
        west = new JPanel();
        center = new JPanel();
        descrip = new JPanel();
        groupe = new JPanel();
        title = new JPanel();
        north2 = new JPanel();
        buttons = new JPanel();
        tab = new JPanel();

        ContactListManager.read();
        myContactList = ContactListManager.getContactList();

        DefaultTableModel Model;
        Model = new DefaultTableModel(ColumnNames, 0) {
            public Class getColumnClass(int s) {
                switch (s) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }

            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        for (int i = 0; i < myContactList.size(); i++) {
            if (myContactList.get(i) != null) {
                int j = 0;
                String nom = myContactList.get(i).getnom();
                String prenom = myContactList.get(i).getprenom();
                String ville = myContactList.get(i).getville();
                String Name = myContactList.get(i).getnom() + " " + myContactList.get(i).getprenom();
                String City = myContactList.get(i).getville();
                Model.addRow(new Object[]{Name, City, false});
                for (j = 0; j < p.getListofContacts().size(); j++) {
                    String nomc = p.getListofContacts().get(j).getnom();
                    String prenomc = p.getListofContacts().get(j).getprenom();
                    String cityc = p.getListofContacts().get(j).getville();
                    if (nom.equals(nomc) && prenom.equals(prenomc) && ville.equals(cityc)) {
                        Model.addRow(new Object[]{Name, City, true});
                        j = p.getListofContacts().size();
                        Model.removeRow(i);
                        break;
                    }
                }
            }

        }
        table = new JTable(Model);
        if (table.getCellEditor() != null)     table.getCellEditor().stopCellEditing();
        tab.add(table.getTableHeader(), BorderLayout.NORTH);
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(350, 350));
        tab.add(scrollPane, BorderLayout.CENTER);

        buttons.add(Save);
        buttons.add(Cancel);

        descrip.add(desc);
        descrip.add(descr);

        groupe.add(grp);
        groupe.add(group);

        title.add(gest);

        north2.setLayout(new BoxLayout(north2, BoxLayout.PAGE_AXIS));
        north2.add(groupe);
        north2.add(descrip);

        east.setLayout(new BorderLayout());
        east.add(north2, BorderLayout.NORTH);
        east.add(tab, BorderLayout.CENTER);
        east.add(buttons, BorderLayout.SOUTH);

        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
        west.add(Box.createVerticalStrut(20));
        west.add(add);
        west.setPreferredSize(new Dimension(80, 200));
        north.add(title);
        center.setBackground(Color.cyan);

        F.add(north, BorderLayout.NORTH);
        F.add(west, BorderLayout.WEST);
        F.add(east, BorderLayout.EAST);
        F.add(center, BorderLayout.CENTER);
        F.pack();
        F.setVisible(true);


    }

    private class upgListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String g = (String) group.getText();
            String d = (String) descr.getText();
            ArrayList<Contact> cont = new ArrayList<Contact>();
            cont = (ArrayList<Contact>) a.getListofContacts();
            Group S = new Group(g, d, cont);
            Object ob = e.getSource();
            if (ob == Cancel) {

                int dialogResult = JOptionPane.showOptionDialog(null, V, "Confirm Message ", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    new ContactsWindow();
                } else if (dialogResult == JOptionPane.NO_OPTION) {
                }
            }
            if (ob == Save) {
                if (g.isEmpty()) {
                    JOptionPane.showMessageDialog(null, msg, "Error Message ", JOptionPane.ERROR_MESSAGE);
                } else {
                    GroupListManager.delGroup(a);
                    cont.clear();
                    for (int i = 0; i < table.getModel().getRowCount(); i++) {
//                        for (int j = 0; j < 3; j++) {
                            for (Contact contact : myContactList) {
                                
                                if (table.getModel().getValueAt(i, 2) == true  && contact.getnom().equals(myContactList.get(i).getnom()) && contact.getprenom().equals(myContactList.get(i).getprenom())) {
                                    cont.add(contact);
                                }
                            
                        }
                    }
                    GroupListManager.addGroup(S);
                }
            }
        }
    }
}
