package passwordManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DBConnect {
	//store database connection info
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String URL = "jdbc:mysql://localhost:3306/passwordmanager";
    
    //authenticate login credentials
    public boolean checkCreds(String user, String pass) {
        boolean auth = false;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
        	//select user from database
            String sql1 = "SELECT * FROM users WHERE id = 1";
            ResultSet rs = statement.executeQuery(sql1);
            //check value of user credentials against what was entered
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columns = metaData.getColumnCount();
                boolean uservalid = false;
                boolean passwordvalid = false;

                for (int i = 1; i <= columns; i++) {
                    if (i > 1) {
                        String columnValue = rs.getString(i).replaceAll("\\s+", "");
                        if (columnValue.equals(user)) {
                            uservalid = true;
                        } else if (columnValue.equals(pass)) {
                            passwordvalid = true;
                        }
                    }
                }
                if (uservalid && passwordvalid) {
                    auth = true;
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auth;
    }
     
    //add password entered by user into database
    public void addPassword(String name, String username2, String password2, String url2, JPanel panel, JFrame frame) {
       //encrypt the data provided
       name = Encrypt.encrypt(name);
       username2 = Encrypt.encrypt(username2);
       password2 = Encrypt.encrypt(password2);
       url2 = Encrypt.encrypt(url2);
       //connect to database and add encrypted values
    	try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            String sql2 = "INSERT INTO passwords (name, username, password, url) VALUES ('" + name + "','" + username2 + "','" + password2 + "','" + url2 + "')";
            statement.execute(sql2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Manager man = new Manager();
        man.changeDisplay(panel, frame);
    }
    
    //retrieve encrypted values and decrypt them
    public String[][] retrievePasswords() {
        String[][] data = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            String sql = "SELECT * FROM passwords";
            ResultSet rs = statement.executeQuery(sql);

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            data = new String[rowCount][5];
            int x = 0;
            while (rs.next()) {
            	data[x][0] = rs.getString(1); //retrieve id which is not encrypted
            	//retrieve data and decrypt it
            	for(int i = 1;i<5;i++) {
                data[x][i] = Encrypt.decrypt(rs.getString(i+1)); 
                } 
                x++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    //remove password based on id
    public void removePassword(String id) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {

            String sql = "DELETE FROM passwords WHERE id ='" + id + "'";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
