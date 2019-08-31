package views.user;

import connections.*;
import tools.*;
import views.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.util.*;

public class ViewUser extends JFrame implements ActionListener, ItemListener, MouseListener {
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
    private JFormattedTextField mskCPF;
    private MaskFormatter mskMaker;
    private JButton btnEdit, btnExit, btnSearch, btnDelete;
    private JRadioButton radName, radSurname, radCPF, radAdmin, radAll, radIsAdmin, radIsUser;
    private ButtonGroup grpSearch, grpAdminSearch;

    public ViewUser(AdminMenu menu) {
        //Window setup
        super("Bücherei: Consulta de Usuários");
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
            model.addColumn("Sobrenome");
            model.addColumn("CPF");
            model.addColumn("Email");
            model.addColumn("Admin?");
            model.addColumn("QTD ALUGADOS");

            table = new JTable(model);
            table.addMouseListener(this);
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

            radIsAdmin = new JRadioButton("Admin");
            radIsAdmin.setBounds(630, 130, 80, 25);
            radIsAdmin.setVisible(false);
            add(radIsAdmin);

            radIsUser = new JRadioButton("Cliente");
            radIsUser.setBounds(710, 130, 100, 25);
            radIsUser.setVisible(false);
            add(radIsUser);

            grpAdminSearch = new ButtonGroup();
            grpAdminSearch.add(radIsAdmin);
            grpAdminSearch.add(radIsUser);

            mskCPF = new JFormattedTextField();
            mskCPF.setBounds(650, 130, 100, 25);
            mskCPF.setVisible(false);
            add(mskCPF);

            mskMaker = new MaskFormatter("###.###.###-##");
            mskMaker.setPlaceholderCharacter('_');
            mskMaker.install(mskCPF);

            lblSearch = new JLabel("Filtro por:");
            lblSearch.setBounds(665, 5, 100, 30);
            add(lblSearch);

            radName = new JRadioButton("Nome");
            radName.setBounds(620, 35, 80, 30);
            radName.addItemListener(this);
            add(radName);

            radSurname = new JRadioButton("Sobrenome");
            radSurname.setBounds(700, 35, 100, 30);
            radSurname.addItemListener(this);
            add(radSurname);

            radCPF = new JRadioButton("CPF");
            radCPF.setBounds(620, 65, 70, 30);
            radCPF.addItemListener(this);
            add(radCPF);

            radAdmin = new JRadioButton("Tipo");
            radAdmin.setBounds(700, 65, 100, 30);
            radAdmin.addItemListener(this);
            add(radAdmin);

            radAll = new JRadioButton("TODOS");
            radAll.setBounds(660, 95, 100, 30);
            radAll.addItemListener(this);
            radAll.setSelected(true);
            add(radAll);

            grpSearch = new ButtonGroup();
            grpSearch.add(radName);
            grpSearch.add(radSurname);
            grpSearch.add(radCPF);
            grpSearch.add(radAdmin);
            grpSearch.add(radAll);

            lblSelected = new JLabel("Nenhum usuário selecionado!");
            lblSelected.setBounds(620, 260, 200, 20);
            add(lblSelected);

            btnEdit = new JButton("Editar usuário");
            btnEdit.setBounds(620, 280, 160, 25);
            btnEdit.setMnemonic('E');
            btnEdit.setEnabled(false);
            btnEdit.addActionListener(this);
            add(btnEdit);

            btnDelete = new JButton("Deletar usuário");
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
        Users search = new Users();
        ArrayList<String> items;

        String searchText = txtSearch.getText();
        int searchOption = 0;

        if (radName.isSelected()) {
            searchOption = 1;
        } else if (radSurname.isSelected()) {
            searchOption = 2;
        } else if (radCPF.isSelected()) {
            searchOption = 3;

            searchText = mskCPF.getText().replace(".", "");
            searchText = searchText.replace("-", "");
        } else if (radAdmin.isSelected()) {
            searchOption = 4;

            if (radIsAdmin.isSelected()) {
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

        for (int i = 0; i < items.size(); i += 7) {
            Object[] data = {
                    items.get(i + 1),
                    items.get(i + 2),
                    items.get(i + 3),
                    items.get(i + 4),
                    items.get(i + 5).equals("1") ? "Sim" : "Não",
                    GetUserRentedBooks(Integer.parseInt(items.get(i))),
            };
            model.addRow(data);
        }
    }

    private int GetUserRentedBooks(int userID) {
        Rented userRentedBooks = new Rented();

        int qtd = userRentedBooks.GetUserManyBooks(userID);
        ;

        return qtd;
    }

    private void DeleteUser() {
        int option = JOptionPane.showConfirmDialog(null,
                "Deseja realmente deletar '" + table.getValueAt(selectedRow, 0) + "'?\nEssa ação não poderá ser desfeita.",
                "Bücherei", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            Users operation = new Users();

            int userId = operation.GetUserID(table.getValueAt(selectedRow, 2).toString());
            operation.Delete(userId);

            ClearSelectedUser();

            DoSearch();
        }
    }

    private void ClearSelectedUser() {
        selectedRow = -1;
        lblSelected.setText("Nenhum usuário selecionado!");
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            DoSearch();
            ClearSelectedUser();
        } else if (e.getSource() == btnEdit) {
            Users search = new Users();
            new EditUser(this, search.GetUserID(table.getValueAt(selectedRow, 2).toString()));
            dispose();
        } else if (e.getSource() == btnDelete) {
            DeleteUser();
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

        radIsAdmin.setVisible(false);
        radIsUser.setVisible(false);
        mskCPF.setVisible(false);
        txtSearch.setVisible(false);

        if (e.getSource() == radAdmin) {
            radIsAdmin.setVisible(true);
            radIsAdmin.setSelected(true);
            radIsUser.setVisible(true);
        } else if (e.getSource() == radCPF) {
            mskCPF.setVisible(true);
        } else {
            txtSearch.setVisible(true);
            txtSearch.setText("");
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());

        lblSelected.setText("Usuário '" + table.getValueAt(row, 0) + "' selecionado!");
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
