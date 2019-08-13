import java.sql.*;
import javax.swing.*;

public class Database {
    private String url, user, passwd;
    private Connection con;

    Database() {
        try {
            url = "jdbc:mysql://localhost:3306/castro?useTimezone=true&serverTimezone=UTC";
            user = "root";
            passwd = "060802";

            con = DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na coneção: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

//    public static void main(String args[]) {
//        new Database();
//    }
}