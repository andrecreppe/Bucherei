package connections;

import java.util.*;
import javax.swing.*;
import java.sql.*;

public class Sections {
    //Fields Variables
    private String name, description;
    private boolean active;

    //SQL Variables
    private String sql, msg;
    private ResultSet result;
    private ArrayList<String> search;
    private PreparedStatement querry;
    private Database localhost;

    public Sections() {
        name = "";
        description = "";
        active = true;

        querry = null;

        localhost = new Database();
    }

    //SET's
    public void setName(String n) {
        this.name = n;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public void setActive(boolean a) {
        this.active = a;
    }

    //SQL Operations
    public void Insert() {
        sql = "";
        msg = "";

        try {
            sql = "INSERT INTO sections VALUES(DEFAULT, ?, ?, ?)";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, description);
            querry.setBoolean(3, active);

            querry.execute();

            querry.close();

            msg = "Seção inserida com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na inclusão: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
    }

    public void Update(int id) {
        sql = "";
        msg = "";

        try {
            sql = "UPDATE sections SET " +
                    "name=?, description=?, active=? " +
                    "WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, description);
            querry.setBoolean(3, active);
            querry.setInt(4, id);

            querry.execute();

            querry.close();

            msg = "Seção alterada com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na alteração: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
    }

    public void Delete(int id) {
        sql = "";

        try {
            sql = "DELETE FROM sections WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);

            querry.execute();

            querry.close();

            msg = "Seção deletada com sucesso!";
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
            sql = "SELECT * FROM sections ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("description"));
                search.add(result.getString("active"));
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

            if (op == 1) {
                field = "name LIKE ";
            } else if (op == 2) {
                field = "active=";
            }

            sql = "SELECT * FROM sections WHERE " + field + "? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);

            if (op == 1) {
                querry.setString(1, "%" + toFind + "%");
            } else if (op == 2) {
                querry.setString(1, toFind);
            }

            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("description"));
                search.add(result.getString("active"));
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
            sql = "SELECT * FROM sections WHERE id=? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);

            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("description"));
                search.add(result.getString("active"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public int GetSectionID(String name, String description) {
        int id = -1;

        sql = "";
        result = null;

        try {
            sql = "SELECT id FROM sections WHERE name=? AND description=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, description);
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
