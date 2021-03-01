package com.mark;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class Main extends JFrame {
    JLabel label ;
    JTextField userText;
    JPasswordField userTextp;
    JLabel labelp;
    JButton button1;
    Boolean auth = false;


    //changes display from authentication screen
    public void changeDisplay(JPanel panel,JFrame frame){
        panel.removeAll();
        frame.setSize(1275,750);
        JLabel newInfo = new JLabel("Add Password:");
        newInfo.setBounds(595, 20, 100, 25);
        JLabel newName = new JLabel("Name: ");
        newName.setBounds(25, 50, 50, 25);
        JTextField newNameField = new JTextField();
        newNameField.setBounds(80,50,165,25);
        JLabel newUsername = new JLabel("Username: ");
        newUsername.setBounds(255, 50, 75, 25);
        JTextField newUsernameField = new JTextField();
        newUsernameField.setBounds(340,50,160,25);
        JLabel newPassword = new JLabel("Password: ");
        newPassword.setBounds(510, 50, 75, 25);
        JTextField newPasswordField = new JTextField();
        newPasswordField.setBounds(595,50,165,25);
        JLabel newUrl = new JLabel("Url: ");
        newUrl.setBounds(770, 50, 45, 25);
        JTextField newUrlField = new JTextField();
        newUrlField.setBounds(805,50,400,25);
        JButton submitPassword = new JButton("Submit");
        submitPassword.setBounds(595,90,150,25);
        submitPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = newNameField.getText();
                String password = newPasswordField.getText();
                String url = newUrlField.getText();
                String username= newUsernameField.getText();
                addPassword(name,username,password,url,panel,frame);
            }
        });
        JSeparator seperator = new JSeparator(SwingConstants.HORIZONTAL);
        seperator.setBounds(0, 125, 1275, 2);

        panel.add(newUsername);
        panel.add(newUsernameField);
        panel.add(newInfo);
        panel.add(newName);
        panel.add(newPassword);
        panel.add(newUrl);
        panel.add(seperator);
        panel.add(newNameField);
        panel.add(newPasswordField);
        panel.add(newUrlField);
        panel.add(submitPassword);

        String value = null;
        String value2 = null;
        String value3 = null;
        String value4 = null;
        String value5 = null;

        String username = "root";
        String url = "jdbc:mysql://localhost:3306/passwordmanager";
        String password = "password";
        try {

            //connects to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //sql query
            String sql1 = "select* from passwords";
            ResultSet rs = statement.executeQuery(sql1);
            int i = 0 ;
            int x = 0 ;

            while (rs.next()) {
                value = rs.getString(1);

                i++;
            }
            String[] sqlValuesID = new String[i];
            String[] sqlValuesName = new String[i];
            String[] sqlValuesUser = new String[i];
            String[] sqlValuesPass = new String[i];
            String[] sqlValuesURL = new String[i];
            rs.beforeFirst();
            while (rs.next()) {
                value = rs.getString(1);

                sqlValuesID[x] = value;

                value2 = rs.getString(2);

                sqlValuesName[x] = value2;

                value3 = rs.getString(3);

                sqlValuesUser[x] = value3;

                value4 = rs.getString(4);

                sqlValuesPass[x] = value4;

                value5 = rs.getString(5);

                sqlValuesURL[x] = value5;
                x++;
            }
            String[] columns = {"id", "Name", "Username", "Password", "URL"};
            String[][] data = new String[sqlValuesID.length][5];
            int w = 0;

            while(w < sqlValuesID.length){
                data[w][0] = sqlValuesID[w];
                data[w][1] = sqlValuesName[w];
                data[w][2] = sqlValuesUser[w];
                data[w][3] = sqlValuesPass[w];
                data[w][4] = sqlValuesURL[w];
                w++;
            }
            table=new JTable(data,columns){
                public boolean isCellEditable(int data,int columns){
                    return false;
                }
                public  Component prepareRenderer(TableCellRenderer r,int data,int columns){
                    Component c = super.prepareRenderer(r,data,columns);
                    if(data%2 == 0){
                        c.setBackground(Color.white);
                    }
                    else
                        c.setBackground(Color.LIGHT_GRAY);
                    return c;
                }
            };
            table.getActionMap().put("copy", new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
                    StringSelection stringSelection = new StringSelection(cellValue);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
                }
            });
            table.setPreferredScrollableViewportSize(new Dimension(745,415));
            table.setFillsViewportHeight(true);

            JScrollPane jps = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jps.setPreferredSize(new Dimension(800, 415));
            jps.setBounds(240,135,800,415);



            panel.add(jps);


            statement.close();
            connection.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        JSeparator sep = new JSeparator();
        sep.setBounds(0,570,1250,2);
        JLabel removePas = new JLabel("Remove a Password:");
        removePas.setBounds(595,572,200,25);
        JLabel removeid = new JLabel("Id: ");
        removeid.setBounds(510,607,50,25);
        JTextField removeFlied= new JTextField();
        removeFlied.setBounds(560,607,165,25);
        JButton remove = new JButton("Remove");
        remove.setBounds(735,607,150,25);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String removingValue = removeFlied.getText();
                String username = "root";
                String url = "jdbc:mysql://localhost:3306/passwordmanager";
                String password = "password";
                try {

                    //connects to the database
                    Connection connection = DriverManager.getConnection(url, username, password);

                    Statement statement = connection.createStatement();

                    //sql query
                    String sql2 = "DELETE FROM passwords WHERE id ='"+removingValue+"'";
                    boolean rs = statement.execute(sql2);


                    statement.close();
                    connection.close();

                } catch (SQLException throwables) {

                    throwables.printStackTrace();
                }
                changeDisplay(panel,frame);
            }


        });
        panel.add(removeFlied);
        panel.add(sep);
        panel.add(removePas);
        panel.add(remove);
        panel.add(removeid);
        panel.revalidate();
        panel.repaint();
    }
    JTable table;


    //first panel seen by user
    public Main(JPanel panel,JFrame frame) {


        label = new JLabel("Username");
        label.setBounds(10, 20, 80, 25);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);

        labelp = new JLabel("Password");
        labelp.setBounds(10, 50, 80, 25);

        userTextp = new JPasswordField();
        userTextp.setEchoChar('*');
        userTextp.setBounds(100, 50, 165, 25);

        // the clickable button
        button1 = new JButton("Submit");
        button1.setBounds(100,85,165,25);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String pass = new String(userTextp.getPassword());
                sqlFunction(user,pass);
                if (auth == true ){
                    changeDisplay(panel,frame);
                }


            }
        });

        // the panel with the button and text
        panel.add(label);
        panel.add(userText);
        panel.add(labelp);
        panel.add(userTextp);
        panel.add(button1);
        panel.add(userTextp);
        panel.add(button1);




    }

    //database connection and login authentication
    public boolean sqlFunction(String user,String pass){
        String username = "root";
        String url = "jdbc:mysql://localhost:3306/passwordmanager";
        String password = "password";


        try {

            //connects to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            //sql query
            String sql1 = "select* from users where id = 1";
            ResultSet rs = statement.executeQuery(sql1);

            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();
                boolean uservalid = false;
                boolean passwordvalid = false;

                //this reads column by column
                for (int i = 1; i <= columns; i++) {
                    if (i > 1) {

                        String columnValue = rs.getString(i);

                        try {
                            pass = pass.replaceAll("\\s+", "");
                        } finally {
                            user = user.replaceAll("\\s+","");
                        }

                        if (columnValue.equals(user)){

                            uservalid = true;
                        }else if (columnValue.equals(pass)){

                            passwordvalid = true;
                        } else {

                            JOptionPane.showMessageDialog(this,"inncorrect username or password please try again");
                        }
                    }//closes for loop

                }//closes while loop
                if(uservalid == true && passwordvalid == true){

                    auth = true;
                    }
            }
            statement.close();
            connection.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        return auth;
    }
    public void addPassword(String name,String username2,String password2,String url2,JPanel panel,JFrame frame){
        String username = "root";
        String url = "jdbc:mysql://localhost:3306/passwordmanager";
        String password = "password";
        try {

            //connects to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();

            //sql query
            String sql2 = "INSERT INTO `passwords`(name,username,password,url)VALUES ('"+name+"','"+username2+"','"+password2+"','"+url2+"')";
            boolean rs = statement.execute(sql2);


            statement.close();
            connection.close();

        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        changeDisplay(panel,frame);
    }

    // create one Frame
    public static void main(String[] args) {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(285,180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Password Manager");
        frame.setVisible(true);
        frame.add(panel);

        panel.setLayout(null);
        new Main(panel,frame);

    }
}


