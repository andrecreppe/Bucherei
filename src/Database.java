import java.sql.*;

public class Database {
    private String url, user, passwd;
    private Connection con;

    Database() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/castro";
            user = "root";
            passwd = "";

            con = DriverManager.getConnection(url, user, passwd);

            System.out.println("foi");
        } catch (Exception e) {
            System.out.println("Erro na conexão!");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Database();
    }
}