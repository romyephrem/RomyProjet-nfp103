package contactlist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NewGroupWindow {

    private JFrame F = new JFrame("Project NFA035");
    private JPanel north, east, west, center, descrip, groupe, title, north2, buttons, tab;
    private JButton Save, Cancel;
    private String[] ColumnNames = {"Contact Name", "City", "Add to Group"};
    private JLabel gest, add, desc, grp, V, msg;
    private JTextField descr, group;
    private JTable table;
    private String[] options = {"yes", "no"};
    private List<Contact> myContactList;
    private JScrollPane scrollPane;

    public NewGroupWindow() {
        F.setPreferredSize(new Dimension(500, 450));
        F.setResizable(false);

        descr = new JTextField(20);
        group = new JTextField(20);

        Save = new JButton("Save Group");
        Save.addActionListener(new NGListener());
        Cancel = new JButton("Cancel");
        Cancel.addActionListener(new NGListener());

        gest = new JLabel("Gestion Des Contacts");
        gest.setForeground(Color.blue);
        add = new JLabel("Add New Group");
        add.setForeground(Color.red);
        desc = new JLabel("Description");
        grp = new JLabel("Group Name");
        V = new JLabel("Vous Voulez Quitter Cette Fenetre");
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
        for (Contact contact : myContactList) {
            if (contact != null) {
                String Name = contact.getnom() + " " + contact.getprenom();
                String City = contact.getville();
                Model.addRow(new Object[]{Name, City, false});
            }
        }
        table = new JTable(Model);
        if (table.getCellEditor() != null) {
            table.getCellEditor().stopCellEditing();
        }
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        tab.add(table.getTableHeader(), BorderLayout.NORTH);
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
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
        west.setPreferredSize(new Dimension(90, 200));
        north.add(title);
        center.setBackground(Color.cyan);

        F.add(north, BorderLayout.NORTH);
        F.add(west, BorderLayout.WEST);
        F.add(east, BorderLayout.EAST);
        F.add(center, BorderLayout.CENTER);
        F.pack();
        F.setVisible(true);

    }

    private class NGListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object ob = e.getSource();
            String g = (String) group.getText();
            String d = (String) descr.getText();
            ArrayList<Contact> Liste = new ArrayList<Contact>();
            Group S = new Group(g, d, Liste);
            if (ob == Cancel) {
                int dialogResult = JOptionPane.showOptionDialog(null, V, "Confirm Message ", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    new ContactsWindow();
                } else if (dialogResult == JOptionPane.NO_OPTION) {
                }
            } else if (ob == Save) {
                if (g.isEmpty()) {
                    JOptionPane.showMessageDialog(null, msg, "Error Message ", JOptionPane.ERROR_MESSAGE);
                } else {
                    for (int i = 0; i < table.getModel().getRowCount(); i++) {
                        for (int j = 0; j < 3; j++) {
                            for (Contact cont : myContactList) {
                                if (table.getModel().getValueAt(i, 2) == true && !Liste.contains(cont) && cont.getnom().equals(myContactList.get(i).getnom()) && cont.getprenom().equals(myContactList.get(i).getprenom())) {
                                    Liste.add(cont);
                                    System.out.println(cont.toString());
                                }
                            }
                        }
                    }
                    GroupListManager.addGroup(S);
                }
            }

        }
    }
}