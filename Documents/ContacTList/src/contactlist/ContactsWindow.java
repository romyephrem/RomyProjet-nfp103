package contactlist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ContactsWindow {

    private DefaultListSelectionModel cont;
    private JFrame F = new JFrame("Project NFA035");
    private JPanel east, west, north, center, search, options;
    private JButton sortbyfirstname, sortbylastname, sortbycity, add, view, update, delete;
    private JLabel title, C, se;
    private JTextField S;
    private JList Liste;
    private List<Contact> myContactList;
    private Contact D;
    private List<Group> myGroupList;

    public ContactsWindow() {

        F.setPreferredSize(new Dimension(550, 500));
        F.setLayout(new BorderLayout());
        F.setResizable(false);

        title = new JLabel("Gestion Des Contacts");
        title.setForeground(Color.blue);
        C = new JLabel("Contacts");
        C.setForeground(Color.red);
        se = new JLabel("Search : ");

        sortbyfirstname = new JButton("sort by first name");
        sortbylastname = new JButton("sort by last name");
        sortbycity = new JButton("sort by city");
        add = new JButton("Add new Contact");
        view = new JButton("view");
        update = new JButton("update");
        delete = new JButton("delete");
        sortbyfirstname.addActionListener(new CListener());
        sortbylastname.addActionListener(new CListener());
        sortbycity.addActionListener(new CListener());
        add.addActionListener(new CListener());
        view.addActionListener(new CListener());
        update.addActionListener(new CListener());
        delete.addActionListener(new CListener());

        east = new JPanel();
        west = new JPanel();
        north = new JPanel();
        center = new JPanel();
        search = new JPanel();
        options = new JPanel();
        north.add(title);

        GroupListManager.read();
        myGroupList = (ArrayList<Group>) GroupListManager.getGroupList();
        ContactListManager.read();
        myContactList = ContactListManager.getContactList();
        sortbyfirstname(myContactList);
        cont = new DefaultListSelectionModel();
        Liste = new JList(myContactList.toArray());
        cont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cont.setLeadAnchorNotificationEnabled(false);
        Liste.setSelectionModel(cont);
        Liste.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        Liste.setLayoutOrientation(JList.VERTICAL);
        Liste.setVisibleRowCount(-1);
        Liste.setPreferredSize(new Dimension(150, 400));
        JScrollPane scrollpane = new JScrollPane(Liste);
        scrollpane.setPreferredSize(new Dimension(150, 200));
        scrollpane.setMaximumSize(new Dimension(200, 200));
        Liste.addMouseListener(mouseListener);

        west.setPreferredSize(new Dimension(140, 600));
        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
        west.add(Box.createVerticalStrut(30));
        west.add(C);
        west.add(Box.createVerticalStrut(20));
        west.add(sortbyfirstname);
        west.add(Box.createVerticalStrut(20));
        west.add(sortbylastname);
        west.add(Box.createVerticalStrut(20));
        west.add(sortbycity);
        west.add(Box.createVerticalStrut(20));
        west.add(add);

        center.setBackground(Color.cyan);

        search.add(se);
        S = new JTextField(17);
        S.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                ArrayList<Contact> original = new ArrayList<>();
                ArrayList<Contact> newList = new ArrayList<>();
                String content = S.getText().toString().toLowerCase();
                try {
                    ContactListManager.read();
                    original = (ArrayList<Contact>) ContactListManager.getContactList();
                    for (Contact d : original) {
                        if (d != null && d.getnom().toLowerCase().startsWith(content)) {
                            newList.add(d);
                        }
                    }
                    Liste.setListData(newList.toArray());
                } catch (Exception E) {
                    System.out.println(E);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                ArrayList<Contact> original = new ArrayList<>();
                ArrayList<Contact> newList = new ArrayList<>();
                String content = S.getText().toString().toLowerCase();
                try {
                    ContactListManager.read();
                    original = (ArrayList<Contact>) ContactListManager.getContactList();
                    if (S.getText().isEmpty()) {
                        Liste.setListData(original.toArray());
                    }
                } catch (Exception E) {
                    System.out.println(E);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                ArrayList<Contact> original = new ArrayList<>();
                ArrayList<Contact> newList = new ArrayList<>();
                String content = S.getText().toString().toLowerCase();
                try {
                    ContactListManager.read();
                    original = (ArrayList<Contact>) ContactListManager.getContactList();
                    for (Contact d : original) {
                        if (d != null && d.getnom().toLowerCase().startsWith(content)) {
                            newList.add(d);
                        }
                    }
                } catch (Exception E) {
                    System.out.println(E);
                }
            }
        });
        search.add(S);

        options.add(view);
        options.add(update);
        options.add(delete);

        east.setLayout(new BorderLayout());
        east.add(search, BorderLayout.NORTH);
        east.add(scrollpane, BorderLayout.CENTER);
        east.add(options, BorderLayout.SOUTH);

        F.add(north, BorderLayout.NORTH);
        F.add(west, BorderLayout.WEST);
        F.add(center, BorderLayout.CENTER);
        F.add(east, BorderLayout.EAST);
        F.pack();
        F.setVisible(true);
    }
//    Configurator.enableAutoCompletion(Liste , S);

    private class CListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final Object ob = e.getSource();
            if (ob == add) {
                new NewContactWindow();
            } else if (ob == sortbyfirstname) {
                List sortedList = sortbyfirstname(ContactListManager.getContactList());
                Liste.removeAll();
                Liste.setListData(sortedList.toArray());
            } else if (ob == sortbylastname) {
                List sortedList = sortbylastname(ContactListManager.getContactList());
                Liste.removeAll();
                Liste.setListData(sortedList.toArray());
            } else if (ob == sortbycity) {
                List sortedList = sortbycity(ContactListManager.getContactList());
                Liste.removeAll();
                Liste.setListData(sortedList.toArray());
            } else if (ob == delete) {
                if (D != null) {
                    for (int i = 0; i < myGroupList.size(); i++) {
                        List<Contact> liste = new ArrayList<Contact>();
                        for (int j = 0; j < myGroupList.get(i).getListofContacts().size(); j++) {
                            String name = myGroupList.get(i).getListofContacts().get(j).getnom();
                            String lastname = myGroupList.get(i).getListofContacts().get(j).getprenom();
                            String city = myGroupList.get(i).getListofContacts().get(j).getville();
                            String[][] number = myGroupList.get(i).getListofContacts().get(j).getphonenumber();
                            if (name.equals(D.getnom()) && lastname.equals(D.getprenom()) && city.equals(D.getville())) {
                                myGroupList.get(i).getListofContacts().remove(j);
                                GroupListManager.save(myGroupList);
                            }
                        }
                    }
                    ContactListManager.deletecontact(D);
                }
                List sortedList = sortbyfirstname(ContactListManager.getContactList());
                Liste.removeAll();
                Liste.setListData(sortedList.toArray());
            } else if (ob == view) {
                if (D != null) {
                    new ViewContactWindow(D.getnom(), D.getprenom(), D.getville(), D.getphonenumber());
                }
            } else if (ob == update) {
                if (D != null) {
                    new UpdateContact(D.getnom(), D.getprenom(), D.getville(), D.getphonenumber(), D);
                }
            }
        }
    }

    public List<Contact> sortbyfirstname(List<Contact> liste) {
        Collections.sort(liste, new Comparator<Contact>() {
            @Override
            public int compare(Contact t, Contact t1) {
                return t.getnom().toLowerCase().compareTo(t1.getnom().toLowerCase());
                // -1 if t < t1
                // 0  if equal
                //1 if t > 1
            }
        });
        return liste;
    }

    public List<Contact> sortbylastname(List<Contact> List) {
        Collections.sort(List, new Comparator<Contact>() {
            @Override
            public int compare(Contact t, Contact t1) {
                return t.getprenom().toLowerCase().compareTo(t1.getprenom().toLowerCase());
            }
        });
        return List;
    }

    public List<Contact> sortbycity(List<Contact> List) {
        Collections.sort(List, new Comparator<Contact>() {
            @Override
            public int compare(Contact t, Contact t1) {
                return t.getville().toLowerCase().compareTo(t1.getville().toLowerCase());
            }
        });
        return List;
    }
    MouseListener mouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent event) {
            final Contact c;
            try {
                c = (Contact) Liste.getSelectedValue();
                D = c;
            } catch (Exception E) {
            }
        }
    };
}
