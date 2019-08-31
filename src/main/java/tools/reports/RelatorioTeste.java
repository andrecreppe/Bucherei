package tools.reports;

import connections.Database;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.extensions.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.io.*;

public class RelatorioTeste
{
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public RelatorioTeste()
    {

        JasperReport jasperR;
        JasperPrint jasperP;
        JRResultSetDataSource jrRS;
        Map parametros;

        try
        {
            Database banco = new Database();
            con = banco.GetConnection();

            stmt=con.createStatement();
            rs=stmt.executeQuery("Select * from books order by id");
            jrRS = new JRResultSetDataSource( rs );
            parametros = new HashMap();

            JasperPrint impressao = JasperFillManager.fillReport( "Coffee.jasper" , parametros,    jrRS );
            //exibe o resultado
            JasperViewer viewer = new JasperViewer( impressao , false );
            viewer.show();

            //fechando o banco
            con.close();
            stmt.close();


        }//try
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,"ERRO relatorio="+e.getMessage());

        }//catch

    }    //main

}//class