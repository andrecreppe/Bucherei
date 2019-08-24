package views.books;

import connections.*;
import tools.*;
import views.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NewBook extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;
    private int incY, firstY;

    //UI Objects
    private JLabel lblTitle, lblAuthor, lblYear, lblPublisher, lblPages, lblSection;
    private JTextField txtTitle, txtAuthor, txtPublisher;
    private JSpinner numYear, numPages;
    private SpinnerModel yearModel, pagesModel;
    private JComboBox cmbSections;
    private JButton btnAdd, btnClear, btnCancel;

    public NewBook(AdminMenu menu) {
        //Window setup
        super("Bücherei: Novo Livro");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        adminMenu = menu;

        firstY = 30;
        incY = 60;

        InitializeUI();

        setVisible(true);
    }

    private void InitializeUI() {
        try {
            Sections sectionsSearch = new Sections();
            ArrayList<String> sections = sectionsSearch.Select();

            lblTitle = new JLabel("*Título:");
            lblTitle.setBounds(300, firstY, 100, 30);
            add(lblTitle);

            txtTitle = new JTextField();
            txtTitle.setBounds(350, firstY, 200, 30);
            add(txtTitle);

            firstY += incY;

            lblAuthor = new JLabel("*Autor:");
            lblAuthor.setBounds(300, firstY, 100, 30);
            add(lblAuthor);

            txtAuthor = new JTextField();
            txtAuthor.setBounds(350, firstY, 200, 30);
            add(txtAuthor);

            firstY += incY;

            lblPublisher = new JLabel("*Editora:");
            lblPublisher.setBounds(290, firstY, 100, 30);
            add(lblPublisher);

            txtPublisher = new JTextField();
            txtPublisher.setBounds(350, firstY, 200, 30);
            add(txtPublisher);

            firstY += incY;

            lblYear = new JLabel("*Ano de publicação:");
            lblYear.setBounds(230, firstY, 150, 30);
            add(lblYear);

            yearModel = new SpinnerNumberModel(0, 0, 2019, 1);
            numYear = new JSpinner(yearModel);
            numYear.setBounds(350, firstY, 100, 30);
            add(numYear);

            firstY += incY;

            lblPages = new JLabel("*Total de páginas:");
            lblPages.setBounds(235, firstY, 150, 30);
            add(lblPages);

            pagesModel = new SpinnerNumberModel(0, 0, 9999, 1);
            numPages = new JSpinner(pagesModel);
            numPages.setBounds(350, firstY, 100, 30);
            add(numPages);

            firstY += incY;

            lblSection = new JLabel("*Seção pertencente:");
            lblSection.setBounds(220, firstY, 150, 30);
            add(lblSection);

            cmbSections = new JComboBox();
            cmbSections.setBounds(350, firstY, 200, 30);
            for (int i = 0; i < sections.size(); i += 4) {
                cmbSections.addItem(sections.get(i + 1));
            }
            add(cmbSections);

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
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPublisher.setText("");
        numYear.setValue(0);
        numPages.setValue(0);
        cmbSections.setSelectedIndex(-1);
    }

    private void Exit() {
        adminMenu.setVisible(true);
        dispose();
    }

    private void AddNewUser() {
        int validation = AllFieldsOK();

        if (validation == 0) {
            Books inclusion = new Books();
            Sections section = new Sections();
            ArrayList<String> sct = section.Select(cmbSections.getSelectedItem().toString(), 1);

            int year = 0, pages = 0, id = 0;

            try {
                year = Integer.parseInt(numYear.getValue().toString());
                pages = Integer.parseInt(numPages.getValue().toString());
                id = Integer.parseInt(sct.get(0));
            } catch (Exception e) {
                String msg = "Oops, aconteceu algum erro!";
                msg += "\n\nErro na conversão numérica: " + e.getMessage();

                JOptionPane.showMessageDialog(null, msg);
            }

            inclusion.setName(txtTitle.getText());
            inclusion.setAuthor(txtAuthor.getText());
            inclusion.setPublisher(txtPublisher.getText());
            inclusion.setPages(pages);
            inclusion.setYear(year);
            inclusion.setSection(id);

            System.out.println(id);

            inclusion.Insert();

            Exit();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Existem campos sem preencher!\nComeça no Campo " + validation + ". Preencha-o.");
        }
    }

    private int AllFieldsOK() {
        int fieldNumber = 0;

        int year = 0, pages = 0;

        try {
            year = Integer.parseInt(numYear.getValue().toString());
            pages = Integer.parseInt(numPages.getValue().toString());
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro na conversão numérica: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        if (txtTitle.getText().length() < 1) {
            fieldNumber = 1;
        } else if (txtAuthor.getText().length() < 1) {
            fieldNumber = 2;
        } else if (txtPublisher.getText().length() < 1) {
            fieldNumber = 3;
        } else if (year <= 0) {
            fieldNumber = 4;
        } else if (pages <= 0) {
            fieldNumber = 5;
        } else if (cmbSections.getSelectedIndex() < 0) {
            fieldNumber = 6;
        }

        return fieldNumber;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            AddNewUser();
        } else if (e.getSource() == btnClear) {
            ClearFields();
        } else if (e.getSource() == btnCancel) {
            Exit();
        }
    }
}
