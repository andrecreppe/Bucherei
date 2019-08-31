package views.sections;

import connections.*;
import tools.*;
import views.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;

public class ViewSection extends JFrame implements ActionListener, MouseListener, ItemListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;
    private int selectedRow;

    //UI Objects
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane dataPanel;
    private JLabel lblSearch, lblSelected;
    private JTextField txtSearch;
    private JButton btnEdit, btnExit, btnSearch, btnDelete;
    private JRadioButton radName, radIsActive, radActive, radIsUnactive, radAll;
    private ButtonGroup grpSearch, grpActive;

    public ViewSection(AdminMenu menu) {
        //Window setup
        super("Bücherei: Consulta de Seções");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        adminMenu = menu;
        selectedRow = -1;

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
            model.addColumn("Nome");
            model.addColumn("Descrição");
            model.addColumn("Ativo");
            model.addColumn("Qtd. de livros");

            table = new JTable(model);
            table.addMouseListener(this);
            table.getTableHeader().setReorderingAllowed(false);
            table.getColumnModel().getColumn(0).setMaxWidth(125);
            table.getColumnModel().getColumn(0).setMinWidth(125);
            table.getColumnModel().getColumn(1).setMaxWidth(250);
            table.getColumnModel().getColumn(1).setMinWidth(250);
            table.getColumnModel().getColumn(2).setMaxWidth(125);
            table.getColumnModel().getColumn(2).setMinWidth(125);

            dataPanel = new JScrollPane(table);
            dataPanel.setBounds(10, 10, 600, 450);
            add(dataPanel);

            txtSearch = new JTextField();
            txtSearch.setBounds(620, 110, 160, 25);
            txtSearch.setEnabled(false);
            add(txtSearch);

            btnSearch = new JButton("Pesquisar!");
            btnSearch.setBounds(620, 140, 160, 20);
            btnSearch.setMnemonic('P');
            btnSearch.addActionListener(this);
            add(btnSearch);

            radIsActive = new JRadioButton("Ativo");
            radIsActive.setBounds(620, 110, 80, 25);
            radIsActive.setVisible(false);
            add(radIsActive);

            radIsUnactive = new JRadioButton("Desativo");
            radIsUnactive.setBounds(700, 110, 120, 25);
            radIsUnactive.setVisible(false);
            add(radIsUnactive);

            grpActive = new ButtonGroup();
            grpActive.add(radIsActive);
            grpActive.add(radIsUnactive);

            lblSearch = new JLabel("Filtro por:");
            lblSearch.setBounds(665, 5, 100, 30);
            add(lblSearch);

            radName = new JRadioButton("Nome");
            radName.setBounds(620, 35, 80, 30);
            radName.addItemListener(this);
            add(radName);

            radActive = new JRadioButton("Ativo");
            radActive.setBounds(720, 35, 100, 30);
            radActive.addItemListener(this);
            add(radActive);

            radAll = new JRadioButton("TODOS");
            radAll.setBounds(660, 70, 100, 30);
            radAll.addItemListener(this);
            radAll.setSelected(true);
            add(radAll);

            grpSearch = new ButtonGroup();
            grpSearch.add(radName);
            grpSearch.add(radActive);
            grpSearch.add(radAll);

            lblSelected = new JLabel("Nenhuma seção selecionada!");
            lblSelected.setBounds(620, 260, 200, 20);
            add(lblSelected);

            btnEdit = new JButton("Editar seção");
            btnEdit.setBounds(620, 280, 160, 25);
            btnEdit.setMnemonic('E');
            btnEdit.setEnabled(false);
            btnEdit.addActionListener(this);
            add(btnEdit);

            btnDelete = new JButton("Deletar seção");
            btnDelete.setBounds(620, 310, 160, 25);
            btnDelete.setMnemonic('D');
            btnDelete.setEnabled(false);
            btnDelete.addActionListener(this);
            add(btnDelete);

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
        Sections search = new Sections();
        ArrayList<String> items;

        String searchText = txtSearch.getText();
        int searchOption = 0;

        if (radName.isSelected()) {
            searchOption = 1;
        } else if (radActive.isSelected()) {
            searchOption = 2;

            if (radIsActive.isSelected()) {
                searchText = "1";
            } else {
                searchText = "0";
            }
        }

        if (searchOption == 0) {
            items = search.Select();
        } else {
            items = search.Select(searchText, searchOption);
        }

        model.setRowCount(0);

        for (int i = 0; i < items.size(); i += 4) {
            Object[] data = {
                    items.get(i + 1),
                    items.get(i + 2),
                    items.get(i + 3).equals("1") ? "Sim" : "Não",
                    search.GetQuantity(items.get(i))
            };
            model.addRow(data);
        }
    }

    private void DeleteSection() {
        Sections operation = new Sections();

        String name = table.getValueAt(selectedRow, 0).toString();
        String description = table.getValueAt(selectedRow, 1).toString();

        int id = operation.GetSectionID(name, description);
        String qtd = operation.GetQuantity(id);

        if(Integer.parseInt(qtd) > 0) {
            JOptionPane.showMessageDialog(null,
                    "Ação bloqueada!\nNão é possivel deletar uma seção com livros nela!");
            return;
        }

        int option = JOptionPane.showConfirmDialog(null,
                "Deseja realmente deletar '" + table.getValueAt(selectedRow, 0) +
                        "'?\nEssa ação não poderá ser desfeita.",
                "Bücherei", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            operation.Delete(id);

            ClearSelectedSection();

            DoSearch();
        }
    }

    private void ClearSelectedSection() {
        selectedRow = -1;
        lblSelected.setText("Nenhuma seção selecionada!");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            DoSearch();
            ClearSelectedSection();
        } else if (e.getSource() == btnEdit) {
            Sections search = new Sections();

            String name = table.getValueAt(selectedRow, 0).toString();
            String description = table.getValueAt(selectedRow, 1).toString();

            new EditSection(this, search.GetSectionID(name, description));
            dispose();
        } else if (e.getSource() == btnDelete) {
            DeleteSection();
        } else if (e.getSource() == btnExit) {
            adminMenu.setVisible(true);
            dispose();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == radAll) {
            txtSearch.setEnabled(false);
        } else {
            txtSearch.setEnabled(true);
        }

        radIsActive.setVisible(false);
        radIsUnactive.setVisible(false);
        txtSearch.setVisible(false);

        if (e.getSource() == radActive) {
            radIsActive.setVisible(true);
            radIsActive.setSelected(true);
            radIsUnactive.setVisible(true);
        } else {
            txtSearch.setVisible(true);
            txtSearch.setText("");
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());

        lblSelected.setText("Seção '" + table.getValueAt(row, 0) + "' selecionada!");
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(true);
        selectedRow = row;
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
