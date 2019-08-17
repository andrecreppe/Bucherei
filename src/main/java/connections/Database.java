package connections;

import java.sql.*;
import javax.swing.*;

public class Database {
    private String url, user, passwd, database;
    private Connection con;

    Database() {
        try {
            database = "castro";
            url = "jdbc:mysql://localhost:3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            user = "root";
            passwd = "guerra123";

            con = null;
            con = DriverManager.getConnection(url, user, passwd);
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na coneção: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void Logoff() {
        try {
            con.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na desconexão: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public Connection GetConnection() {
        return this.con;
    }
}