package views.admin.sections;

import connections.*;
import tools.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EditSection extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private ViewSection viewSection;
    private int incY, firstY, sectionID;

    //UI Objects
    private JLabel lblName, lblDescription, lblActive;
    private JTextField txtName;
    private JTextArea txaDescription;
    private JRadioButton radActive, radUnactive;
    private ButtonGroup radioGroup;
    private JButton btnAdd, btnClear, btnCancel;

    public EditSection(ViewSection view, int id) {
        //Window setup
        super("Bücherei: Editar Seção");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        viewSection = view;
        sectionID = id;

        firstY = 60;
        incY = 100;

        InitializeUI();

        SetFields();

        setVisible(true);
    }

    private void InitializeUI() {
        try {
            lblName = new JLabel("*Nome:");
            lblName.setBounds(300, firstY, 100, 30);
            add(lblName);

            txtName = new JTextField();
            txtName.setBounds(350, firstY, 200, 30);
            add(txtName);

            firstY += incY;

            lblDescription = new JLabel("*Descrição:");
            lblDescription.setBounds(270, firstY, 100, 30);
            add(lblDescription);

            txaDescription = new JTextArea();
            txaDescription.setBounds(350, firstY, 250, 50);
            add(txaDescription);

            firstY += incY;

            lblActive = new JLabel("*Ativo:");
            lblActive.setBounds(300, firstY, 100, 30);
            add(lblActive);

            radActive = new JRadioButton("Ativo");
            radActive.setBounds(370, firstY, 70, 30);
            add(radActive);
            radUnactive = new JRadioButton("Inativo");
            radUnactive.setBounds(450, firstY, 100, 30);
            add(radUnactive);

            radioGroup = new ButtonGroup();
            radioGroup.add(radActive);
            radioGroup.add(radUnactive);

            firstY += incY + 25;

            btnAdd = new JButton("Alterar");
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

    private void SetFields() {
        Sections section = new Sections();

        ArrayList<String> sectionData = section.Select(sectionID);

        txtName.setText(sectionData.get(1));
        txaDescription.setText(sectionData.get(2));

        if(sectionData.get(3).equals("1")) {
            radActive.setSelected(true);
        } else {
            radUnactive.setSelected(true);
        }
    }

    private void ClearFields() {
        txtName.setText("");
        txaDescription.setText("");
        radioGroup.clearSelection();
    }

    private void Exit() {
        viewSection.setVisible(true);
        dispose();
    }

    private void EditThisSection() {
        int validation = AllFieldsOK();

        if (validation == 0) {
            Sections inclusion = new Sections();
            MD5 crypto = new MD5();

            inclusion.setName(txtName.getText());
            inclusion.setDescription(txaDescription.getText());

            if (radActive.isSelected()) {
                inclusion.setActive(true);
            } else {
                inclusion.setActive(false);
            }

            inclusion.Update(sectionID);

            Exit();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Existem campos sem preencher!\nComeça no Campo " + validation + ". Preencha-o.");
        }
    }

    private int AllFieldsOK() {
        int fieldNumber = 0;

        if (txtName.getText().length() < 1) {
            fieldNumber = 1;
        } else if (txaDescription.getText().length() < 1) {
            fieldNumber = 2;
        } else if (!radActive.isSelected() && !radUnactive.isSelected()) {
            fieldNumber = 3;
        }

        return fieldNumber;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            EditThisSection();
        } else if (e.getSource() == btnClear) {
            ClearFields();
        } else if (e.getSource() == btnCancel) {
            Exit();
        }
    }
}
