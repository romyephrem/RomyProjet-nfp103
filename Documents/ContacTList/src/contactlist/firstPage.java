
package contactlist;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class firstPage {
    private JFrame NFA = new JFrame ("Project NFA035");
    private JPanel  title , centre  , Westpanel , empty , emptyEast;
    private JButton contact ;
    private JButton group ;
    private JLabel label ;
    public firstPage () 
    {
        NFA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NFA.setPreferredSize(new Dimension (500,400));
        NFA.setLayout(new BorderLayout());
        NFA.setResizable(false);
        title = new JPanel ();
        centre = new JPanel ();
        Westpanel = new JPanel ();
        empty = new JPanel();
        emptyEast = new JPanel();
        Westpanel.setLayout(new GridLayout (6,1,40,40));
        contact = new JButton ("Contacts");
        group = new JButton ("Groups");
        Westpanel.add(empty);
        Westpanel.add(contact);
        Westpanel.add(group );
        contact.addActionListener(new TempListener ());
        group.addActionListener(new TempListener ());
        label = new JLabel ("Gestion Des Bouttons");
        label.setForeground(Color.blue);
        title.add(label);
        centre.setBackground(Color.cyan);
        NFA.add(title , BorderLayout.NORTH);
        NFA.add(centre , BorderLayout.CENTER);
        NFA.add(Westpanel , BorderLayout.WEST);
        NFA.add(emptyEast , BorderLayout.EAST);
        NFA.pack();
        NFA.setVisible(true);

    }
    private class TempListener implements ActionListener 
    {
        @Override
        public void actionPerformed (ActionEvent e) 
        {
            Object ob = e.getSource();
             
            if (ob == contact ) 
            { 
                new ContactsWindow (); 
            }
           
            else if (ob == group) 
            {
                new Groupwindow () ;
            }
        } 
    }
    public static void main (String [] args)
    {
        new firstPage () ;
    }
}
