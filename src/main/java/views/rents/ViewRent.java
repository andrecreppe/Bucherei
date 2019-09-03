package views.rents;

import connections.*;
import tools.*;
import views.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

public class ViewRent extends JFrame implements ActionListener, MouseListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;
    private int selectedRow;

    //UI Objects
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane dataPanel;
    private JLabel lblSearch, lblSelected;
    private JButton btnEndRent, btnExit, btnSearch, btnDelete;
    private JRadioButton radRented, radReturned, radAll;
    private JComboBox cmbUserID, cmbBookID;
    private ButtonGroup grpSearch;

    public ViewRent(AdminMenu menu) {
        //Window setup
        super("Bücherei: Consulta de Aluguéis");
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
            model.addColumn("Usuário");
            model.addColumn("Livro");
            model.addColumn("Data retirada");
            model.addColumn("Data devolução");

            table = new JTable(model);
            table.addMouseListener(this);
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

            cmbBookID = new JComboBox();
            cmbBookID.setVisible(false);
            add(cmbBookID);

            cmbUserID = new JComboBox();
            cmbUserID.setVisible(false);
            add(cmbUserID);

            lblSelected = new JLabel("Nenhum aluguel selecionado!");
            lblSelected.setBounds(620, 260, 200, 20);
            add(lblSelected);

            btnEndRent = new JButton("Registar devolução");
            btnEndRent.setBounds(620, 280, 160, 25);
            btnEndRent.setMnemonic('R');
            btnEndRent.setEnabled(false);
            btnEndRent.addActionListener(this);
            add(btnEndRent);

            btnDelete = new JButton("Deletar aluguel");
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

    private void ClearSelectedRent() {
        selectedRow = -1;
        lblSelected.setText("Nenhum aluguel selecionado!");
        btnEndRent.setEnabled(false);
        btnDelete.setEnabled(false);

        cmbBookID.setSelectedIndex(-1);
        cmbUserID.setSelectedIndex(-1);
    }

    public void DoSearch() {
        try {
            Rented search = new Rented();
            Books books = new Books();
            Users users = new Users();
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
            cmbUserID.removeAllItems();
            cmbBookID.removeAllItems();

            for (int i = 0; i < items.size(); i += 5) {
                cmbUserID.addItem(items.get(i + 1));
                cmbBookID.addItem(items.get(i + 2));

                Object[] data = {
                        users.Select(Integer.parseInt(items.get(i + 1))).get(1),
                        books.Select(Integer.parseInt(items.get(i + 2))).get(1),
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

    private void DeleteRent() {
        Rented operation = new Rented();

        int user = Integer.parseInt(cmbUserID.getSelectedItem().toString());
        int book = Integer.parseInt(cmbBookID.getSelectedItem().toString());

        int id = operation.GetRentID(user, book);

        int option = JOptionPane.showConfirmDialog(null,
                "Deseja realmente deletar '" + table.getValueAt(selectedRow, 1) +
                        "' de " + table.getValueAt(selectedRow, 0) +
                        "?\nEssa ação não poderá ser desfeita.",
                "Bücherei", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            operation.Delete(id);

            ClearSelectedRent();

            DoSearch();
        }
    }

    private void EndRent() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());

        int option = JOptionPane.showConfirmDialog(null, "Registrar devolução do livro '" +
                        table.getValueAt(selectedRow, 0) + "' para o dia de hoje (" + dateString + ")?",
                "Bücherei", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            Rented operation = new Rented();

            int user = Integer.parseInt(cmbUserID.getSelectedItem().toString());
            int book = Integer.parseInt(cmbBookID.getSelectedItem().toString());

            int id = operation.GetRentID(user, book);

            operation.setDateReturned(dateString);
            operation.AddReturnDate(id);

            DoSearch();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            DoSearch();
            ClearSelectedRent();
        } else if (e.getSource() == btnEndRent) {
            EndRent();
        } else if (e.getSource() == btnDelete) {
            DeleteRent();
        } else if (e.getSource() == btnExit) {
            adminMenu.setVisible(true);
            dispose();
        }
    }

    public void mouseClicked(MouseEvent e) {
        int row = table.rowAtPoint(e.getPoint());

        lblSelected.setText("Aluguel de '" + table.getValueAt(row, 0) + "' selecionado!");
        btnDelete.setEnabled(true);

        if (table.getValueAt(row, 3).equals("NÃO DEVOLVIDO")) {
            btnEndRent.setEnabled(true);
        } else {
            btnEndRent.setEnabled(false);
        }

        cmbBookID.setSelectedIndex(row);
        cmbUserID.setSelectedIndex(row);

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
