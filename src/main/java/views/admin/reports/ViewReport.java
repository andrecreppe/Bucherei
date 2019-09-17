package views.admin.reports;

import tools.*;
import tools.report.*;
import views.admin.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

public class ViewReport extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;
    private BufferedImage img1, img2;

    //UI Objects
    private JLabel lblTable, lblJaspersoft, lblReport;
    private JComboBox cmbTable;
    private JButton btnShow, btnCancel;

    public ViewReport(AdminMenu menu) {
        //Window setup
        super("Bücherei: Gerar relatório");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        adminMenu = menu;

        InitializeUI();

        setVisible(true);
    }

    private void InitializeUI() {
        try {
            img1 = ImageIO.read(getClass().getResource("/images/report.png"));
            img2 = ImageIO.read(getClass().getResource("/images/jaspersoft.png"));

            lblReport = new JLabel(new ImageIcon(img1));
            lblReport.setBounds(50, 30, 120, 150);
            add(lblReport);

            lblJaspersoft = new JLabel(new ImageIcon(img2));
            lblJaspersoft.setBounds(550, 50, 200, 100);
            add(lblJaspersoft);

            lblTable = new JLabel("Tabela:");
            lblTable.setBounds(270, 220, 100, 30);
            add(lblTable);

            cmbTable = new JComboBox();
            cmbTable.setBounds(320, 220, 200, 30);
            cmbTable.addItem("Livros");
            cmbTable.addItem("Seções");
            cmbTable.addItem("Usuários");
            cmbTable.addItem("Aluguéis");
            cmbTable.setSelectedIndex(-1);
            add(cmbTable);

            btnShow = new JButton("Gerar relatório");
            btnShow.setBounds(220, 360, 200, 30);
            btnShow.setMnemonic('G');
            btnShow.addActionListener(this);
            add(btnShow);

            btnCancel = new JButton("Cancelar");
            btnCancel.setBounds(460, 360, 100, 30);
            btnCancel.setMnemonic('C');
            btnCancel.addActionListener(this);
            add(btnCancel);
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no setup dos campos de seleção: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    private void GenerateReport() {
        int selectedField = cmbTable.getSelectedIndex();

        if(selectedField < 0) {
            JOptionPane.showMessageDialog(null, "Selecione uma tabela para gerar um relatório!");
            return;
        }

        ReportGenerator report = new ReportGenerator();

        if(selectedField == 0) { //Books
            report.GenerateReport("books");
        } else if(selectedField == 1) { //Sections
            report.GenerateReport("sections");
        } else if(selectedField == 2) { //Sections
            report.GenerateReport("users");
        } else if(selectedField == 3) { //Sections
            report.GenerateReport("rented");
        }
    }

    private void Exit() {
        adminMenu.setVisible(true);
        dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnShow) {
            GenerateReport();
        } else if (e.getSource() == btnCancel) {
            Exit();
        }
    }
}
