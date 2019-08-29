package views.rents;

import connections.*;
import org.jdatepicker.impl.*;
import tools.*;
import views.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//CAMPO DE DATA BUGADOOOOOO

public class NewRent extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;
    private int incY, firstY, bookID;

    //UI Objects
    private JLabel lblUser, lblBook, lblPickDay;
    private JComboBox cmbUsers, cmbUsersID, cmbBooks, cmbBooksID;
    private JDatePickerImpl dateRented;
    private JButton btnAdd, btnClear, btnCancel;

    public NewRent(AdminMenu menu, int id) {
        //Window setup
        super("Bücherei: Alugar um livro");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        adminMenu = menu;
        bookID = id;

        firstY = 120;
        incY = 80;

        InitializeUI();

        setVisible(true);
    }

    private void InitializeUI() {
        try {
            Books bookDatabase = new Books();
            Users userDatabase = new Users();

            ArrayList<String> books = bookDatabase.Select();
            ArrayList<String> users = userDatabase.Select("0", 4);

            lblBook = new JLabel("*Livro:");
            lblBook.setBounds(300, firstY, 100, 30);
            add(lblBook);

            cmbBooksID = new JComboBox();
            cmbBooksID.setVisible(false);
            for (int i = 0; i < books.size(); i += 7) {
                cmbBooksID.addItem(books.get(i));
            }
            add(cmbBooksID);

            cmbBooks = new JComboBox();
            cmbBooks.setBounds(350, firstY, 200, 30);
            for (int i = 0; i < books.size(); i += 7) {
                cmbBooks.addItem(books.get(1 + i));

                if (books.get(i).equals(bookID + "")) {
                    cmbBooks.setSelectedIndex(i / 7);
                }
            }
            cmbBooks.addActionListener(this);
            add(cmbBooks);

            firstY += incY;

            lblUser = new JLabel("*Usuário:");
            lblUser.setBounds(290, firstY, 100, 30);
            add(lblUser);

            cmbUsers = new JComboBox();
            cmbUsers.setBounds(350, firstY, 200, 30);
            for (int i = 0; i < users.size(); i += 6) {
                cmbUsers.addItem(users.get(i));
            }
            cmbUsers.addActionListener(this);
            add(cmbUsers);

            cmbUsersID = new JComboBox();
            cmbUsersID.setVisible(false);
            for (int i = 0; i < users.size(); i += 6) {
                cmbUsers.addItem(users.get(i));
            }
            add(cmbUsersID);

            firstY += incY;

            lblPickDay = new JLabel("*Dia do aluguel:");
            lblPickDay.setBounds(255, firstY, 100, 30);
            add(lblPickDay);

            Date today = new Date();
            UtilDateModel model = new UtilDateModel();
            model.setDate(today.getYear(), today.getMonth(), today.getDay());
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            dateRented = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            dateRented.setBounds(370, firstY, 110, 30);
            add(dateRented);

            firstY += incY + 25;

            btnAdd = new JButton("Adicionar");
            btnAdd.setBounds(220, firstY, 100, 30);
            btnAdd.setMnemonic('A');
            btnAdd.addActionListener(this);
            add(btnAdd);

            btnClear = new JButton("Limpar");
            btnClear.setBounds(340, firstY, 100, 30);
            btnClear.setMnemonic('L');
            btnClear.addActionListener(this);
            add(btnClear);

            btnCancel = new JButton("Cancelar");
            btnCancel.setBounds(460, firstY, 100, 30);
            btnCancel.setMnemonic('C');
            btnCancel.addActionListener(this);
            add(btnCancel);
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no setup dos campos de cadasrtro: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        ClearFields();
    }

    private void ClearFields() {
        cmbBooks.setSelectedIndex(-1);
        cmbUsers.setSelectedIndex(-1);
        //dateRented.set
    }

    private void Exit() {
        adminMenu.setVisible(true);
        dispose();
    }

    private void AddNewRentedBook() {
//        int validation = AllFieldsOK();
//
//        if (validation == 0) {
//            Sections inclusion = new Sections();
//
//            inclusion.setName(txtName.getText());
//            inclusion.setDescription(txaDescription.getText());
//
//            if (radActive.isSelected()) {
//                inclusion.setActive(true);
//            } else {
//                inclusion.setActive(false);
//            }
//
//            inclusion.Insert();
//
//            Exit();
//        } else {
//            JOptionPane.showMessageDialog(null,
//                    "Existem campos sem preencher!\nComeça no Campo " + validation + ". Preencha-o.");
//        }
    }

    private int AllFieldsOK() {
        int fieldNumber = 0;
        String cpf;

        if (cmbBooks.getSelectedIndex() < 0) {
            fieldNumber = 1;
        } else if (cmbBooks.getSelectedIndex() < 0) {
            fieldNumber = 2;
        }

        return fieldNumber;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            AddNewRentedBook();
        } else if (e.getSource() == btnClear) {
            ClearFields();
        } else if (e.getSource() == btnCancel) {
            Exit();
        }
    }
}
