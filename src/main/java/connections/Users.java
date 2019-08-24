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
    private String sql, msg;
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
        msg = "";
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

            msg = "Usuário inserido com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na inserção: " + e.getMessage();

        }

        JOptionPane.showMessageDialog(null, msg);
    }

    public void Update(int id) {
        msg = "";
        sql = "";

        try {
            sql = "UPDATE users SET " +
                    "name=?, surname=?, cpf=?, email=?, icon=?, admin=? " +
                    "WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, surname);
            querry.setString(3, cpf);
            querry.setString(4, email);
            querry.setInt(5, icon);
            querry.setBoolean(6, admin);
            querry.setInt(7, id);

            querry.execute();

            querry.close();

            msg = "Usuário alterado com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na alteração: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
    }

    public void Delete(int id) {
        msg = "";
        sql = "";

        try {
            sql = "DELETE FROM users WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);

            querry.execute();

            querry.close();

            msg = "Usuário excluído com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na exclusão: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
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

    public ArrayList<String> Select(int id) {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM users WHERE id=? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);
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

    public ArrayList<String> Select(String toFind, int op) {
        sql = "";
        result = null;
        search = null;

        try {
            String field = "";

            if(op == 1) {
                field = "name";
            } else if(op == 2) {
                field = "surname";
            } else if(op == 3) {
                field = "cpf";
            } else if(op == 4) {
                field = "admin";
            }

            sql = "SELECT * FROM users WHERE " + field + " LIKE ? ORDER BY id";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, "%" + toFind + "%");
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

    public int GetUserID(String cpf) {
        int id = -1;

        sql = "";
        result = null;

        try {
            sql = "SELECT id FROM users WHERE cpf=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, cpf);
            result = querry.executeQuery();

            while (result.next()) {
                id = result.getInt("id");
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return id;
    }
}
