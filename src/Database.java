import java.sql.*;

class Database
{
    private String url, user, passwd;
    private Connection con;

    Database()
    {
        try 
        {
            //Class.forName("com.mysql.jdbc.Driver");

            url ="jdbc:mysql://localhost:3306/castro";
            user = "root";
            passwd = "";

            con = DriverManager.getConnection(url, user, passwd);
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        new Database();
        System.out.println("foi");
    }
}