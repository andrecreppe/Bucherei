package connections;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class Rented {
    //Fields Variables
    private String dateRented, dateReturned;
    private int idUser, idBook;

    //SQL Variables
    private String sql, msg;
    private ResultSet result;
    private ArrayList<String> search;
    private PreparedStatement querry;
    private Database localhost;

    public Rented() {
        dateRented = null;
        dateReturned = null;
        idBook = 0;
        idUser = 0;

        querry = null;

        localhost = new Database();
    }

    //SET's
    public void setIdUser(int u) {
        this.idUser = u;
    }

    public void setIdBook(int b) {
        this.idBook = b;
    }

    public void setDateRented(String d) {
        this.dateRented = d;
    }

    public void setDateReturned(String d) {
        this.dateReturned = d;
    }

    //SQL Operations
    public void Insert() {
        sql = "";
        msg = "";

        try {
            sql = "INSERT INTO rented VALUES(DEFAULT, ?, ?, ?, null)";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, idUser);
            querry.setInt(2, idBook);
            querry.setString(3, dateRented);

            querry.execute();

            querry.close();

            msg = "Aluguel inserido com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na inclusão: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
    }

    public void Delete(int id) {
        sql = "";

        try {
            sql = "DELETE FROM rented WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, id);

            querry.execute();

            querry.close();

            msg = "Aluguel deletado com sucesso!";
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
            sql = "SELECT * FROM rented ORDER BY id";

            querry = localhost.GetConnection().prepareStatement(sql);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("id"));
                search.add(result.getString("id_user"));
                search.add(result.getString("id_book"));
                search.add(result.getString("date_rented"));
                search.add(result.getString("date_returned"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public ArrayList<String> Select(int op) {
        sql = "";
        result = null;
        search = null;

        String field = "";

        try {
            if (op == 1) {
                field = "date_returned is NULL";
            } else if (op == 2) {
                field = "date_returned is NOT NULL";
            }
            sql = "SELECT * FROM rented WHERE date_returned=" + field + " ORDER BY id";

            querry = localhost.GetConnection().prepareStatement(sql);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
                search.add(result.getString("id"));
                search.add(result.getString("id_user"));
                search.add(result.getString("id_book"));
                search.add(result.getString("date_rented"));
                search.add(result.getString("date_returned"));
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public int GetRentID(int userID, int bookID) {
        sql = "";
        result = null;

        int search = -1;

        try {
            sql = "SELECT id FROM rented WHERE id_user=? AND id_book=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, userID);
            querry.setInt(2, bookID);
            result = querry.executeQuery();

            while (result.next()) {
                search = result.getInt("id");
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public int GetUserManyBooks(int user) {
        sql = "";
        result = null;

        int search = -1;

        try {
            sql = "SELECT COUNT(*) as qtd FROM rented WHERE id_user=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, user);
            result = querry.executeQuery();

            while (result.next()) {
                search = result.getInt("qtd");
            }

            querry.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na pesquisa: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        return search;
    }

    public void AddReturnDate(int id) {
        sql = "";
        msg = "";

        try {
            sql = "UPDATE rented SET " +
                    "date_returned=? " +
                    "WHERE id=?";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setString(1, dateReturned);
            querry.setInt(2, id);

            querry.execute();

            querry.close();

            msg = "Aluguel alterado com sucesso!";
        } catch (Exception e) {
            msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na alteração: " + e.getMessage();
        }

        JOptionPane.showMessageDialog(null, msg);
    }
}
