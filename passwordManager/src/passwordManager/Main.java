package passwordManager;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class Main extends JFrame {
    public static void main(String[] args) {
        //create new swing panel and frame
    	JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        //start the login screen
        new Login(panel,frame);

    }
}