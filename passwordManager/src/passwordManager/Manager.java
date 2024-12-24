package passwordManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class Manager {
	//creates new panel displaying all passwords
    public void changeDisplay(JPanel panel, JFrame frame) {
    	//get rid of login screen
        panel.removeAll();
        
        //resize and add elements
        frame.setSize(1750, 750);
        JLabel newInfo = new JLabel("Add Password:");
        newInfo.setBounds(595, 20, 100, 25);
        JLabel newName = new JLabel("Name: ");
        newName.setBounds(25, 50, 50, 25);
        JTextField newNameField = new JTextField();
        newNameField.setBounds(80, 50, 165, 25);
        JLabel newUsername = new JLabel("Username: ");
        newUsername.setBounds(255, 50, 75, 25);
        JTextField newUsernameField = new JTextField();
        newUsernameField.setBounds(340, 50, 160, 25);
        JLabel newPassword = new JLabel("Password: ");
        newPassword.setBounds(510, 50, 75, 25);
        JTextField newPasswordField = new JTextField();
        newPasswordField.setBounds(595, 50, 165, 25);
        JLabel newUrl = new JLabel("Url: ");
        newUrl.setBounds(770, 50, 45, 25);
        JTextField newUrlField = new JTextField();
        newUrlField.setBounds(805, 50, 400, 25);
        JButton submitPassword = new JButton("Submit");
        submitPassword.setBounds(595, 90, 150, 25);
        
        //add password on button press
        submitPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = newNameField.getText();
                String password = newPasswordField.getText();
                String url = newUrlField.getText();
                String username = newUsernameField.getText();
                DBConnect db = new DBConnect();
                db.addPassword(name, username, password, url, panel, frame);
            }
        });
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(0, 125, 1275, 2);

        panel.add(newUsername);
        panel.add(newUsernameField);
        panel.add(newInfo);
        panel.add(newName);
        panel.add(newPassword);
        panel.add(newUrl);
        panel.add(separator);
        panel.add(newNameField);
        panel.add(newPasswordField);
        panel.add(newUrlField);
        panel.add(submitPassword);
        
        //retrieve existing passwords
        DBConnect db = new DBConnect();
        String[][] data = db.retrievePasswords();
        String[] columns = {"id", "Name", "Username", "Password", "URL"};
        
        //display passwords in a formatted table
        JTable table = new JTable(data, columns) {
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
            //alternate cell row colors
            public Component prepareRenderer(TableCellRenderer r, int data, int columns) {
                Component c = super.prepareRenderer(r, data, columns);
                if (data % 2 == 0) {
                    c.setBackground(Color.white);
                } else {
                    c.setBackground(Color.LIGHT_GRAY);
                }
                return c;
            }
        };
        //copy the JTable into the displayed table
        table.getActionMap().put("copy", new AbstractAction() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                StringSelection stringSelection = new StringSelection(cellValue);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
            }
        });
        table.setPreferredScrollableViewportSize(new Dimension(745, 415));
        table.setFillsViewportHeight(true);
        
        //make password manager scrollable in case it fills
        JScrollPane jps = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jps.setPreferredSize(new Dimension(800, 415));
        jps.setBounds(240, 135, 800, 415);

        panel.add(jps);
        
        //remove passwords by id
        JSeparator sep = new JSeparator();
        sep.setBounds(0, 570, 1250, 2);
        JLabel removePas = new JLabel("Remove a Password:");
        removePas.setBounds(595, 572, 200, 25);
        JLabel removeId = new JLabel("Id: ");
        removeId.setBounds(510, 607, 50, 25);
        JTextField removeField = new JTextField();
        removeField.setBounds(560, 607, 165, 25);
        JButton remove = new JButton("Remove");
        remove.setBounds(735, 607, 150, 25);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String removingValue = removeField.getText();
                DBConnect db = new DBConnect();
                db.removePassword(removingValue);
                changeDisplay(panel, frame);
            }
        });
        //add elements to panel
        panel.add(removeField);
        panel.add(sep);
        panel.add(removePas);
        panel.add(remove);
        panel.add(removeId);
        panel.revalidate();
        //refresh panel
        panel.repaint();
    }
}
