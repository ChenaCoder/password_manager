package passwordManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login {
	JLabel label ;
	JTextField userText;
	JPasswordField userTextp;
	JLabel labelp;
	JButton button1;
	

	public Login(JPanel panel,JFrame frame) {
		//create a frame and add fields
		frame.setSize(400,180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Password Manager");
        label = new JLabel("Username");
        label.setBounds(10, 20, 80, 25);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);

        labelp = new JLabel("Password");
        labelp.setBounds(10, 50, 80, 25);

        userTextp = new JPasswordField();
        userTextp.setEchoChar('*');
        userTextp.setBounds(100, 50, 165, 25);

       
        button1 = new JButton("Submit");
        button1.setBounds(100,85,165,25);
        // on the press of submit authenticate password
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String pass = new String(userTextp.getPassword());
                DBConnect db = new DBConnect();
                
                // has the values entered by user
                Hash hash = new Hash();
                String userHash = hash.hashString(user);
                String passHash = hash.hashString(pass);
                //authenticate login credentials
                boolean auth = db.checkCreds(userHash,passHash);
                if (auth == true ){
                    Manager man = new Manager();
                	man.changeDisplay(panel,frame);
                }else {
                	JOptionPane.showMessageDialog(null, this,"inncorrect username or password please try again", 0);
                }


            }
        });

        // add elements to panel and frame
        panel.add(label);
        panel.add(userText);
        panel.add(labelp);
        panel.add(userTextp);
        panel.add(button1);
        panel.add(userTextp);
        panel.add(button1);
        frame.setVisible(true);
        frame.add(panel);
        panel.setLayout(null);



    }
}
