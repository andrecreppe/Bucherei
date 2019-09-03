package views.user.rents;

import connections.*;
import tools.*;
import views.user.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class ViewMyRents extends JFrame implements ActionListener{
    //Control Variables
    private WindowConfiguration wConfig;
    private UserMenu userMenu;

    //UI Objects
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane dataPanel;
    private JLabel lblSearch;
    private JButton btnExit, btnSearch;
    private JRadioButton radRented, radReturned, radAll;
    private ButtonGroup grpSearch;

    public ViewMyRents(UserMenu menu) {
        //Window setup
        super("Bücherei: Consulta dos meus Aluguéis");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        userMenu = menu;

        InitializeUI();

        DoSearch();

        setVisible(true);
    }

    private void InitializeUI() {
        try {
            model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.addColumn("Livro");
            model.addColumn("Autor");
            model.addColumn("Data retirada");
            model.addColumn("Data devolução");

            table = new JTable(model);
            table.getTableHeader().setReorderingAllowed(false);
            table.getColumnModel().getColumn(0).setMaxWidth(180);
            table.getColumnModel().getColumn(0).setMinWidth(180);
            table.getColumnModel().getColumn(1).setMaxWidth(180);
            table.getColumnModel().getColumn(1).setMinWidth(180);
            table.getColumnModel().getColumn(2).setMaxWidth(120);
            table.getColumnModel().getColumn(2).setMinWidth(120);

            dataPanel = new JScrollPane(table);
            dataPanel.setBounds(10, 10, 600, 450);
            add(dataPanel);

            btnSearch = new JButton("Pesquisar!");
            btnSearch.setBounds(620, 100, 160, 20);
            btnSearch.setMnemonic('P');
            btnSearch.addActionListener(this);
            add(btnSearch);

            lblSearch = new JLabel("Filtro por:");
            lblSearch.setBounds(670, 5, 100, 30);
            add(lblSearch);

            radRented = new JRadioButton("Alugados");
            radRented.setBounds(615, 35, 80, 30);
            add(radRented);

            radReturned = new JRadioButton("Devolvidos");
            radReturned.setBounds(700, 35, 100, 30);
            add(radReturned);

            radAll = new JRadioButton("TODOS");
            radAll.setBounds(665, 65, 100, 30);
            radAll.setSelected(true);
            add(radAll);

            grpSearch = new ButtonGroup();
            grpSearch.add(radRented);
            grpSearch.add(radReturned);
            grpSearch.add(radAll);

            btnExit = new JButton("Sair");
            btnExit.setBounds(620, 428, 160, 30);
            btnExit.setMnemonic('S');
            btnExit.addActionListener(this);
            add(btnExit);
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no setup dos campos de vizualização: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void DoSearch() {
        try {
            Rented search = new Rented();
            Books books = new Books();
            ArrayList<String> items;

            int searchOption = 0;

            if (radReturned.isSelected()) {
                searchOption = 2;
            } else if (radRented.isSelected()) {
                searchOption = 1;
            }

            if (searchOption == 0) {
                items = search.Select();
            } else {
                items = search.Select(searchOption);
            }

            model.setRowCount(0);

            for (int i = 0; i < items.size(); i += 5) {
                Object[] data = {
                        books.Select(Integer.parseInt(items.get(i + 2))).get(1),
                        books.Select(Integer.parseInt(items.get(i + 2))).get(2),
                        items.get(i + 3),
                        items.get(i + 4) == null ? "NÃO DEVOLVIDO" : items.get(i + 4),
                };
                model.addRow(data);
            }
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no carregamento dos dados: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            DoSearch();
        } else if (e.getSource() == btnExit) {
            userMenu.setVisible(true);
            dispose();
        }
    }
}
