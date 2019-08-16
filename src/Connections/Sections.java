package Connections;

import java.util.*;
import javax.swing.*;
import java.sql.*;

public class Sections {
    //Fields Variables
    private String name, description;
    private boolean active;

    //SQL Variables
    private String sql;
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

    //GET's
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getActive() {
        return this.active;
    }

    //SQL Operations
    public void Insert() {

    }

    public void Update(int id) {

    }

    public void Delete(int id) {

    }

    public ArrayList<String> Select(String name) {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM sections WHERE name LIKE ? AND active=true ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, '%' + name);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("description"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public ArrayList<String> Select(boolean active) {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM sections WHERE active=? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, "" + active);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("name"));
                search.add(result.getString("description"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }
}
