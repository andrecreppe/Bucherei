package views.user.books;

import connections.*;
import tools.*;
import views.user.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class ViewBook extends JFrame implements ActionListener, ItemListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private UserMenu userMenu;

    //UI Objects
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane dataPanel;
    private JLabel lblSearch;
    private JTextField txtSearch;
    private JComboBox cmbSections;
    private JSpinner numSearch;
    private JRadioButton radTitle, radAuthor, radPublisher, radYear, radSection, radAll;
    private ButtonGroup grpSearch;
    private JButton btnExit, btnSearch;

    public ViewBook(UserMenu menu) {
        //Window setup
        super("Bücherei: Consulta de Livros");
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
        Sections sectionsSearch = new Sections();
        ArrayList<String> sections = sectionsSearch.Select();

        try {
            model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.addColumn("Título");
            model.addColumn("Autor");
            model.addColumn("Editora");
            model.addColumn("Ano");
            model.addColumn("Páginas");
            model.addColumn("Seção");

            table = new JTable(model);
            table.getTableHeader().setReorderingAllowed(false);
            table.getColumnModel().getColumn(0).setMaxWidth(100);
            table.getColumnModel().getColumn(0).setMinWidth(100);
            table.getColumnModel().getColumn(1).setMaxWidth(100);
            table.getColumnModel().getColumn(1).setMinWidth(100);
            table.getColumnModel().getColumn(2).setMaxWidth(100);
            table.getColumnModel().getColumn(2).setMinWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(100);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(4).setMaxWidth(100);
            table.getColumnModel().getColumn(4).setMinWidth(100);

            dataPanel = new JScrollPane(table);
            dataPanel.setBounds(10, 10, 600, 450);
            add(dataPanel);

            txtSearch = new JTextField();
            txtSearch.setBounds(620, 130, 160, 25);
            txtSearch.setEnabled(false);
            add(txtSearch);

            btnSearch = new JButton("Pesquisar!");
            btnSearch.setBounds(620, 160, 160, 20);
            btnSearch.setMnemonic('P');
            btnSearch.addActionListener(this);
            add(btnSearch);

            SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 2019, 1);
            numSearch = new JSpinner();
            numSearch.setBounds(660, 130, 80, 25);
            numSearch.setVisible(false);
            add(numSearch);

            cmbSections = new JComboBox();
            cmbSections.setBounds(620, 130, 160, 25);
            for (int i = 0; i < sections.size(); i += 4) {
                cmbSections.addItem(sections.get(i + 1));
            }
            cmbSections.setVisible(false);
            add(cmbSections);

            lblSearch = new JLabel("Filtro por:");
            lblSearch.setBounds(665, 5, 100, 30);
            add(lblSearch);

            radTitle = new JRadioButton("Título");
            radTitle.setBounds(620, 35, 80, 30);
            radTitle.addItemListener(this);
            add(radTitle);

            radAuthor = new JRadioButton("Autor");
            radAuthor.setBounds(700, 35, 100, 30);
            radAuthor.addItemListener(this);
            add(radAuthor);

            radPublisher = new JRadioButton("Editora");
            radPublisher.setBounds(620, 65, 70, 30);
            radPublisher.addItemListener(this);
            add(radPublisher);

            radSection = new JRadioButton("Seção");
            radSection.setBounds(700, 65, 100, 30);
            radSection.addItemListener(this);
            add(radSection);

            radYear = new JRadioButton("Ano");
            radYear.setBounds(620, 95, 70, 30);
            radYear.addItemListener(this);
            add(radYear);

            radAll = new JRadioButton("TODOS");
            radAll.setBounds(700, 95, 100, 30);
            radAll.addItemListener(this);
            radAll.setSelected(true);
            add(radAll);

            grpSearch = new ButtonGroup();
            grpSearch.add(radTitle);
            grpSearch.add(radAuthor);
            grpSearch.add(radPublisher);
            grpSearch.add(radSection);
            grpSearch.add(radYear);
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
        Books search = new Books();
        Sections section = new Sections();
        ArrayList<String> items;

        String searchText = txtSearch.getText();
        int searchOption = 0;

        if (radTitle.isSelected()) {
            searchOption = 1;
        } else if (radAuthor.isSelected()) {
            searchOption = 2;
        } else if (radYear.isSelected()) {
            searchOption = 3;

            int val = Integer.parseInt(numSearch.getValue().toString());
            searchText = val + "";
        } else if (radPublisher.isSelected()) {
            searchOption = 4;
        } else if (radSection.isSelected() && cmbSections.getSelectedIndex() > -1) {
            searchOption = 5;

            items = section.Select(cmbSections.getSelectedItem().toString(), 1);
            searchText = items.get(0);

            System.out.println(searchText);
        }

        items = null;

        if (searchOption == 0) {
            items = search.Select();
        } else {
            items = search.Select(searchText, searchOption);
        }

        model.setRowCount(0);

        for (int i = 0; i < items.size(); i += 8) {
            Object[] data = {
                    items.get(i + 1),
                    items.get(i + 2),
                    items.get(i + 3),
                    items.get(i + 4),
                    items.get(i + 5),
                    section.Select(Integer.parseInt(items.get(i + 7))).get(1)
            };
            model.addRow(data);
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

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == radAll) {
            txtSearch.setEnabled(false);
        } else {
            txtSearch.setEnabled(true);
        }

        cmbSections.setVisible(false);
        numSearch.setVisible(false);
        txtSearch.setVisible(false);

        if (e.getSource() == radSection) {
            cmbSections.setVisible(true);
            cmbSections.setSelectedIndex(-1);
        } else if (e.getSource() == radYear) {
            numSearch.setVisible(true);
            numSearch.setValue(0);
        } else {
            txtSearch.setVisible(true);
            txtSearch.setText("");
        }
    }
}
