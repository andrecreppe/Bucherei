package connections;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Books {
    //Fields Variables
    private String name, author, publisher;
    private int year, pages, section;

    //SQL Variables
    private String sql, msg;
    private ResultSet result;
    private ArrayList<String> search;
    private PreparedStatement querry;
    private Database localhost;

    public Books() {
        name = "";
        author = "";
        publisher = "";
        year = 0;
        pages = 0;
        section = 0;

        querry = null;

        localhost = new Database();
    }

    //SET's
    public void setName(String n) {
        this.name = n;
    }

    public void setAuthor(String a) {
        this.author = a;
    }

    public void setPublisher(String p) {
        this.publisher = p;
    }

    public void setYear(int y) {
        this.year = y;
    }

    public void setPages(int p) {
        this.pages = p;
    }

    public void setSection(int s) {
        this.section = s;
    }

    //SQL Operations
    public void Insert() {
        sql = "";
        msg = "";

        try {
            sql = "INSERT INTO books VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, author);
            querry.setInt(3, year);
            querry.setString(4, publisher);
            querry.setInt(5, pages);
            querry.setInt(6, section);

            querry.execute();

            querry.close();

            msg = "Livro inserido com sucesso!";
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
                    "name=?, author=?, publisher=?, year=?, pages=?, id_section=?" +
                    "WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, name);
            querry.setString(2, author);
            querry.setString(3, publisher);
            querry.setInt(4, year);
            querry.setInt(5, pages);
            querry.setInt(6, section);
            querry.setInt(7, id);

            querry.execute();

            querry.close();

            msg = "Livro alterado com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na alteração: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
    }

    public void Delete(int id) {
        sql = "";

        try {
            sql = "DELETE FROM books WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);

            querry.execute();

            querry.close();

            msg = "Livro deletado com sucesso!";
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
            sql = "SELECT * FROM books ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("id"));
                search.add(result.getString("name"));
                search.add(result.getString("author"));
                search.add(result.getString("publisher"));
                search.add(result.getString("year"));
                search.add(result.getString("pages"));
                search.add(result.getString("id_section"));
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
                field = "author LIKE ";
            } else if (op == 3) {
                field = "year=";
            } else if (op == 4) {
                field = "publisher LIKE ";
            } else if (op == 5) {
                field = "id_section=";
            }

            sql = "SELECT * FROM books WHERE " + field + "? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);

            if (op == 1 || op == 2 || op == 4) {
                querry.setString(1, "%" + toFind + "%");
            } else if (op == 3 || op == 5) {
                querry.setString(1, toFind);
            }

            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("id"));
                search.add(result.getString("name"));
                search.add(result.getString("author"));
                search.add(result.getString("publisher"));
                search.add(result.getString("year"));
                search.add(result.getString("pages"));
                search.add(result.getString("id_section"));
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
            sql = "SELECT * FROM books WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("id"));
                search.add(result.getString("name"));
                search.add(result.getString("author"));
                search.add(result.getString("publisher"));
                search.add(result.getString("year"));
                search.add(result.getString("pages"));
                search.add(result.getString("id_section"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

//    public int GetBookID(String name, String author) {
//
//    }
}
