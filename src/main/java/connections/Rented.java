package connections;

import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Rented {
    //Fields Variables
    private Date dateRented, dateReturned;
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

    public void setDateRented(Date d) {
        this.dateRented = d;
    }

    public void setDateReturned(Date d) {
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
            querry.setDate(3, dateRented);

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

    public ArrayList<String> Select(int user) {
        sql = "";
        result = null;
        search = null;

        try {
            sql = "SELECT * FROM rented WHERE user_id=? ORDER BY name";

            querry = localhost.GetConnection().prepareStatement(sql);
            querry.setInt(1, user);
            result = querry.executeQuery();

            search = new ArrayList<String>();
            while (result.next()) {
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
}
