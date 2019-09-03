package views.user;

import connections.*;
import tools.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

public class EditUser extends JFrame implements ActionListener, ItemListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private ViewUser userTableMenu;
    private BufferedImage imageLoader;
    private int incY, firstY, userID;

    //UI Objects
    private JLabel lblName, lblSurname, lblCPF, lblEmail, lblPassword, lblIcon, lblAdmin, lblImage, lblImageText;
    private JTextField txtName, txtSurname, txtEmail;
    private JFormattedTextField mskCPF;
    private MaskFormatter mskMaker;
    private JComboBox cmbIcons;
    private JRadioButton radAdmin, radUser;
    private ButtonGroup radioGroup;
    private JButton btnAdd, btnClear, btnCancel;

    public EditUser(ViewUser view, int id){
        //Window setup
        super("Bücherei: Editar Usuário");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        userTableMenu = view;
        userID = id;

        firstY = 30;
        incY = 50;

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

            lblSurname = new JLabel("*Sobrenome:");
            lblSurname.setBounds(270, firstY, 100, 30);
            add(lblSurname);

            txtSurname = new JTextField();
            txtSurname.setBounds(350, firstY, 200, 30);
            add(txtSurname);

            firstY += incY;

            lblCPF = new JLabel("*CPF:");
            lblCPF.setBounds(310, firstY, 100, 30);
            add(lblCPF);

            mskCPF = new JFormattedTextField();
            mskCPF.setBounds(350, firstY, 200, 30);
            add(mskCPF);

            mskMaker = new MaskFormatter("###.###.###-##");
            mskMaker.setPlaceholderCharacter('_');
            mskMaker.install(mskCPF);

            firstY += incY;

            lblEmail = new JLabel("*Email:");
            lblEmail.setBounds(300, firstY, 100, 30);
            add(lblEmail);

            txtEmail = new JTextField();
            txtEmail.setBounds(350, firstY, 200, 30);
            add(txtEmail);

            firstY += incY;

            lblPassword = new JLabel("*Senha:    A SENHÁ SÓ PODE SER ALTERADA PELO PRÓRPIO USUÁRIO!");
            lblPassword.setBounds(295, firstY, 400, 30);
            add(lblPassword);

            firstY += incY;

            lblIcon = new JLabel("*Ícone:");
            lblIcon.setBounds(300, firstY, 100, 30);
            add(lblIcon);

            cmbIcons = new JComboBox();
            cmbIcons.setBounds(350, firstY, 200, 30);
            cmbIcons.addItemListener(this);
            cmbIcons.addItem("Nenhum");
            cmbIcons.addItem("Unicórnio");
            cmbIcons.addItem("Mago Mágico");
            cmbIcons.addItem("Fantasminha");
            cmbIcons.addItem("Dino");
            cmbIcons.addItem("Zumbi");
            cmbIcons.addItem("Capitão Pirata");
            cmbIcons.setSelectedIndex(0);
            add(cmbIcons);

            lblImageText = new JLabel("Preview do ícone");
            lblImageText.setBounds(75, 120, 120, 30);
            add(lblImageText);

            firstY += incY;

            lblAdmin = new JLabel("*Admin?");
            lblAdmin.setBounds(295, firstY, 100, 30);
            add(lblAdmin);

            radAdmin = new JRadioButton("Admin");
            radAdmin.setBounds(370, firstY, 70, 30);
            add(radAdmin);
            radUser = new JRadioButton("User");
            radUser.setBounds(450, firstY, 100, 30);
            add(radUser);

            radioGroup = new ButtonGroup();
            radioGroup.add(radAdmin);
            radioGroup.add(radUser);

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
        Users user = new Users();

        ArrayList<String> userData = user.Select(userID);

        txtName.setText(userData.get(0));
        txtSurname.setText(userData.get(1));
        mskCPF.setText(userData.get(2));
        txtEmail.setText(userData.get(3));

        cmbIcons.setSelectedIndex(Integer.parseInt(userData.get(4)));

        if(userData.get(5).equals("1")) {
            radAdmin.setSelected(true);
        } else {
            radUser.setSelected(true);
        }
    }

    private void ClearFields() {
        txtName.setText("");
        txtSurname.setText("");
        mskCPF.setText("");
        txtEmail.setText("");
        cmbIcons.setSelectedIndex(0);
        radioGroup.clearSelection();
    }

    private void Exit() {
        userTableMenu.setVisible(true);
        userTableMenu.DoSearch();
        dispose();
    }

    private void EditThisUser() {
        int validation = AllFieldsOK();

        if (validation == 0) {
            Users inclusion = new Users();
            MD5 crypto = new MD5();
            String cpf;

            inclusion.setName(txtName.getText());
            inclusion.setSurname(txtSurname.getText());
            inclusion.setEmail(txtEmail.getText());
            inclusion.setIcon(cmbIcons.getSelectedIndex());

            cpf = mskCPF.getText().replace(".", "");
            cpf = cpf.replace("-", "");
            inclusion.setCPF(cpf);

            if (radAdmin.isSelected()) {
                inclusion.setAdmin(true);
            } else {
                inclusion.setAdmin(false);
            }

            inclusion.Update(userID);

            Exit();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Existem campos sem preencher!\nComeça no Campo " + validation + ". Preencha-o.");
        }
    }

    private int AllFieldsOK() {
        int fieldNumber = 0;
        String cpf;

        if(txtName.getText().length() < 1) {
            fieldNumber = 1;
        } else if (txtSurname.getText().length() < 1) {
            fieldNumber = 2;
        } else if (txtEmail.getText().length() < 1) {
            fieldNumber = 4;
        } else if (cmbIcons.getSelectedIndex() < 0) {
            fieldNumber = 5;
        } else if (!radAdmin.isSelected() && !radUser.isSelected()) {
            fieldNumber = 6;
        }

        cpf = mskCPF.getText().replace(".", "");
        cpf = cpf.replace("-", "");

        if(cpf.length() != 11) {
            fieldNumber = 3;
        }

        return fieldNumber;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            EditThisUser();
        } else if (e.getSource() == btnClear) {
            ClearFields();
        } else if (e.getSource() == btnCancel) {
            Exit();
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == cmbIcons) {
            try {
                String imageNumber = cmbIcons.getSelectedIndex() + "";
                imageLoader = null;
                imageLoader = ImageIO.read(getClass().getResource("/images/" + imageNumber + ".jpg"));

                if(lblImage != null) {
                    lblImage.setIcon(null);
                }

                lblImage = new JLabel(new ImageIcon(imageLoader));
                lblImage.setBounds(80, 150, 100, 100);
                add(lblImage);
            } catch (Exception ee) {
            }
        }
    }
}
