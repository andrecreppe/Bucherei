package tools.report;

import connections.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;

public class ReportGenerator {
    public void GenerateReport(String table) {
        Connection con;
        Statement stmt;
        ResultSet rs;
        JRResultSetDataSource jrRS;
        Map parametros;

        try {
            Database db = new Database();
            con = db.GetConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM " + table + " ORDER BY id");
            jrRS = new JRResultSetDataSource(rs);
            parametros = new HashMap<String, String>();

            String url = System.getProperty("user.dir") + "/src/main/java/tools/report/" + table + ".jasper";
            JasperPrint impressao = JasperFillManager.fillReport(url, parametros, jrRS);

            JasperViewer viewer = new JasperViewer(impressao, false);
            viewer.show();

            stmt.close();
            con.close();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro ao gerar o relatório: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }
}
