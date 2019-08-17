package connections;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Users {
    //Fields Variables
    private String name, surname, cpf, email, password;
    private int icon;
    private boolean admin;

    //SQL Variables
    private String sql;
    private ResultSet result;
    private ArrayList<String> search;
    private PreparedStatement querry;
    private Database localhost;

    public Users() {
        name = "";
        surname = "";
        cpf = "";
        email = "";
        password = "";
        icon = 0;
        admin = true;

        querry = null;

        localhost = new Database();
    }

    //SET's
    public void setName(String n) {
        this.name = n;
    }

    public void setSurname(String s) {
        this.surname = s;
    }

    public void setCPF(String c) {
        this.cpf = c;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public void setAdmin(boolean a) {
        this.admin = a;
    }

    //SQL Operations
    public void Insert() {
        sql = "";

        try {
            sql = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, surname);
            querry.setString(3, cpf);
            querry.setString(4, email);
            querry.setString(5, password);
            querry.setInt(6, icon);
            querry.setBoolean(7, admin);

            querry.execute();

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na inserção: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void Update(int id) {
        sql = "";

        try {
            sql = "UPDATE users SET " +
                    "name=?, surname=?, cpf=?, email=?, password=?, icon=?, admin=?" +
                    "WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, surname);
            querry.setString(3, cpf);
            querry.setString(4, email);
            querry.setString(5, password);
            querry.setInt(6, icon);
            querry.setBoolean(7, admin);
            querry.setInt(8, id);

            querry.execute();

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na alteração: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void Delete(int id) {
        sql = "";

        try {
            sql = "DELETE FROM users WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);

            querry.execute();

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na exclusão: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public ArrayList<String> Select() {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM users ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("surname"));
                search.add(result.getString("cpf"));
                search.add(result.getString("email"));
                search.add(result.getString("icon"));
                search.add(result.getString("admin"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public ArrayList<String> Select(String nome) {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM users WHERE name=? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, nome);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("surname"));
                search.add(result.getString("cpf"));
                search.add(result.getString("email"));
                search.add(result.getString("icon"));
                search.add(result.getString("admin"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public ArrayList<String> Login() {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM users WHERE email=? AND password=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, email);
            querry.setString(2, password);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("admin"));
                search.add(result.getString("name"));
                search.add(result.getString("surname"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no login: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }
}
