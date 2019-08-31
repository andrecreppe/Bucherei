package views.rents;

import com.toedter.calendar.*;
import connections.*;
import tools.*;
import views.*;

import javax.swing.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

public class NewRent extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;
    private int incY, firstY;
    private String bookName;

    //UI Objects
    private JLabel lblUser, lblBook, lblPickDay;
    private JComboBox cmbUsers, cmbUsersID, cmbBooks, cmbBooksID;
    private JDateChooser dateRented;
    private JButton btnAdd, btnClear, btnCancel;

    public NewRent(AdminMenu menu, String name) {
        //Window setup
        super("Bücherei: Alugar um livro");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        adminMenu = menu;
        bookName = name;

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
            int index = -1;
            for (int i = 0; i < books.size(); i += 7) {
                cmbBooks.addItem(books.get(1 + i));

                if (books.get(i + 1).equals(bookName + "")) {
                    index = i / 7;
                }
            }
            cmbBooks.setSelectedIndex(index);
            cmbBooks.addActionListener(this);
            add(cmbBooks);

            firstY += incY;

            lblUser = new JLabel("*Usuário:");
            lblUser.setBounds(290, firstY, 100, 30);
            add(lblUser);

            cmbUsers = new JComboBox();
            cmbUsers.setBounds(350, firstY, 200, 30);
            for (int i = 0; i < users.size(); i += 7) {
                cmbUsers.addItem(users.get(i + 1));
            }
            cmbUsers.addActionListener(this);
            cmbUsers.setSelectedIndex(-1);
            add(cmbUsers);

            cmbUsersID = new JComboBox();
            cmbUsersID.setVisible(false);
            for (int i = 0; i < users.size(); i += 7) {
                cmbUsersID.addItem(users.get(i));
            }
            add(cmbUsersID);

            firstY += incY;

            lblPickDay = new JLabel("*Dia do aluguel:");
            lblPickDay.setBounds(255, firstY, 100, 30);
            add(lblPickDay);

            dateRented = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
            dateRented.setDate(new Date());
            dateRented.setBounds(370, firstY, 110, 30);
            dateRented.getDateEditor().getUiComponent().addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    ((JTextFieldDateEditor) e.getSource()).selectAll();
                }
            });
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
    }

    private void ClearFields() {
        cmbBooks.setSelectedIndex(-1);
        cmbUsers.setSelectedIndex(-1);
        dateRented.setDate(new Date());
    }

    private void Exit() {
        adminMenu.setVisible(true);
        dispose();
    }

    private void AddNewRentedBook() {
        int validation = AllFieldsOK();

        if (validation == 0) {
            Rented inclusion = new Rented();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = "";

            inclusion.setIdBook(Integer.parseInt(cmbBooksID.getSelectedItem().toString()));
            inclusion.setIdUser(Integer.parseInt(cmbUsersID.getSelectedItem().toString()));
            inclusion.setDateRented(dateFormat.format(dateRented.getDate()));

            inclusion.Insert();

            Exit();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Existem campos sem preencher!\nComeça no Campo " + validation + ". Preencha-o.");
        }
    }

    private int AllFieldsOK() {
        int fieldNumber = 0;

        if (cmbBooks.getSelectedIndex() < 0) {
            fieldNumber = 1;
        } else if (cmbUsers.getSelectedIndex() < 0) {
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
