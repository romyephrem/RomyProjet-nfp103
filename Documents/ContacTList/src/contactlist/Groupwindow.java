package contactlist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class Groupwindow {

    private JFrame frame = new JFrame("Project NFA035");
    private JButton up, del, add;
    private JLabel gest, grp, list, delete;
    String[] s = {"Group Name", "Nb. De Contact"};
    String[] s2 = {"Contact Name", "Contact City"};
    String[][] ss = new String[7][2];
    String[][] ss2 = new String[7][2];
    private JTable table;
    private JTable table2;
    private DefaultTableModel tableModel, table2Model;
    private JPanel north, west, east, center, liste, buttons, tab, tab2, bigtab;
    String[] options = {"yes", "no"};
    private ArrayList<Group> myGroupList;
    private ArrayList<Contact> myContactList;
    private int numberofcontacts = 0;
    private JScrollPane scrollPane, scrollPane2;
    private int row, column;
    private Group D;
    private Contact S;

    public Groupwindow() {
        frame.setPreferredSize(new Dimension(500, 450));
        frame.setResizable(false);

        table = new JTable(ss, s);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        table.setModel(tableModel);
        for (String col : s) {
            tableModel.addColumn(col);
        }

        table2 = new JTable(ss2, s2);
        table2.setModel(table2Model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        });
        for (String col : s2) {
            table2Model.addColumn(col);
        }
        table2.addMouseListener(mouseListener);
        up = new JButton("Update Group");
        up.addActionListener(new GListener());
        del = new JButton("Delete");
        del.addActionListener(new GListener());
        add = new JButton("Add New Group");
        add.addActionListener(new GListener());

        gest = new JLabel("Gestion des Contacts");
        gest.setForeground(Color.blue);
        grp = new JLabel("Groups");
        grp.setForeground(Color.red);
        list = new JLabel("List Des Groups");
        delete = new JLabel("Vous Voulez Supprimer ce Grp");

        north = new JPanel();
        east = new JPanel();
        west = new JPanel();
        center = new JPanel();
        liste = new JPanel();
        buttons = new JPanel();
        tab = new JPanel();
        tab2 = new JPanel();
        bigtab = new JPanel();

        GroupListManager.read();
        myGroupList = (ArrayList) GroupListManager.getGroupList();
        ContactListManager.read();
        myContactList = (ArrayList) ContactListManager.getContactList();
        
        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        table.getSelectionModel().addListSelectionListener(LListener);
        tab.add(table.getTableHeader(), BorderLayout.NORTH);
        tab.setLayout(new BoxLayout(tab, BoxLayout.PAGE_AXIS));
        tab.add(scrollPane, BorderLayout.CENTER);
        
        scrollPane2 = new JScrollPane(table2);
        scrollPane2.setPreferredSize(new Dimension(200, 200));
        tab2.add(table2.getTableHeader(), BorderLayout.NORTH);
        tab2.setLayout(new BoxLayout(tab2, BoxLayout.PAGE_AXIS));
        tab2.add(scrollPane2, BorderLayout.CENTER);

        bigtab.setLayout(new BoxLayout(bigtab, BoxLayout.PAGE_AXIS));
        bigtab.add(tab);
        bigtab.add(Box.createVerticalStrut(20));
        bigtab.add(tab2);

        buttons.add(up);
        buttons.add(del);
        liste.add(list);

        east.setLayout(new BorderLayout());
        east.add(liste, BorderLayout.NORTH);
        east.add(bigtab, BorderLayout.CENTER);
        east.add(buttons, BorderLayout.SOUTH);

        west.setPreferredSize(new Dimension(130, 200));
        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
        west.add(Box.createVerticalStrut(20));
        west.add(grp);
        west.add(Box.createVerticalStrut(20));
        west.add(add);

        north.add(gest);

        center.setBackground(Color.cyan);

        frame.add(north, BorderLayout.NORTH);
        frame.add(east, BorderLayout.EAST);
        frame.add(west, BorderLayout.WEST);
        frame.add(center, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        refreshLists();
    }

    private void refreshLists() {

        tableModel.setRowCount(0);
        for (int i = 0; i < myGroupList.size(); i++) {
            ss[i][0] = myGroupList.get(i).getnom();
            numberofcontacts = myGroupList.get(i).getListofContacts().size();
            ss[i][1] = String.valueOf(numberofcontacts);
            tableModel.addRow(ss[i]);
        }

        int count = tableModel.getRowCount();
        while (count < 7) {
            count++;
            tableModel.addRow(new Object[]{});
        }

        table2Model.setRowCount(0);
        for (int i = 0; i < 7; i++) {
            table2Model.addRow(new Object[]{});
        }
    }

    private class GListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object ob = e.getSource();
            if (ob == add) {
                new NewGroupWindow();
            } else if (ob == up) {
                if (D!=null)
                new UpdateGroupWindow(D);
            } else if (ob == del) {
                int dialogResult = JOptionPane.showOptionDialog(null, delete, "Confirm Message ", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    GroupListManager.delGroup(D);
                    refreshLists();
                }
            }
        }
    }

    ListSelectionListener LListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            final Group c;
            int selectedRow = table.getSelectedRow();
            try {
                c = (Group) myGroupList.get(selectedRow);
                int lengt = 0;
                lengt = myGroupList.get(selectedRow).getListofContacts().size();
                List<Contact> Liste = new ArrayList<Contact>();
                Liste = myGroupList.get(selectedRow).getListofContacts();
                table2Model.setRowCount(0);
                for (int i = 0; i < lengt; i++) {
                    ss2[i][0] = Liste.get(i).getnom() + " " + Liste.get(i).getprenom();
                    ss2[i][1] = Liste.get(i).getville();
                    table2Model.addRow(ss2[i]);
                }
                int count = table2Model.getRowCount();
                while (count < 7) {
                    count++;
                    table2Model.addRow(new Object[]{});
                }
                D = c;
                row = selectedRow;
            } catch (Exception E){}
        }
    };
    MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent event) {
            final Contact c;
            int selectedRow = table2.getSelectedRow();
            try {
            c = (Contact) myGroupList.get(row).getListofContacts().get(selectedRow);
            S = c;
            if (S!=null)new ViewContactWindow(S.getnom(), S.getprenom(), S.getville(), S.getphonenumber());
        }catch (Exception E ) {} }
    };
}
