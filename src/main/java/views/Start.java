package views;

import connections.*;
import tools.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

//set font to the entire program
//tool tips texts
//comment the code
//alterar senha em uma aba separada
//deletar????

public class Start extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private BufferedImage image;

    //UI Objects
    private JProgressBar loading;
    private JLabel lblUser, lblPassword, logo, lblAuthors;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnClear, btnLogin;

    public Start() {
        //Window setup
        super("Bücherei");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        //Window elements
        loading = new JProgressBar(1, 100);
        loading.setBounds(wConfig.getWidth() - 600, wConfig.getHeight() - 150, wConfig.getWidth() / 2, 30);
        add(loading);

        lblAuthors = new JLabel("By: André Creppe & Thiago Dalla Dea - (CTI@2019)");
        lblAuthors.setBounds(wConfig.getWidth() - 550, wConfig.getHeight() - 120, 350, 50);
        lblAuthors.setFont(wConfig.getFont(15, true));
        add(lblAuthors);

        try {
            image = ImageIO.read(getClass().getResource("/images/logo.png"));
            logo = new JLabel(new ImageIcon(image));
            logo.setBounds(40, 10, 720, 240);
            add(logo);
        } /* Ash */ catch /* um */ (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no carregamento da imagem: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }

        //Show
        setVisible(true);

        //Load the aplication
        ProgressBar();
        LoginUI();
    }

    private void ProgressBar() {
        loading.setStringPainted(true);
        loading.setValue(0);
        loading.setString("Carregando... (0%)");
        for (int i = 1; i <= 100; i++) {
            loading.setValue(i);
            loading.setString("Carregando... (" + i + "%)");
            loading.repaint();

            try {
                if (i == 100) {
                    loading.setString("Carregado! (100%)");
                    loading.repaint();
                    Thread.sleep(1500);
                } else {
                    Thread.sleep(20);
                }
            } catch (Exception e) {
                String msg = "Oops, aconteceu algum erro!";
                msg += "\n\nErro no carregamento: " + e.getMessage();

                JOptionPane.showMessageDialog(null, msg);
            }
        }
    }

    private void LoginUI() {
        remove(loading);
        remove(logo);
        remove(lblAuthors);

        lblUser = new JLabel("Email:");
        lblUser.setBounds(170, 120, 150, 30);
        lblUser.setFont(wConfig.getFont(30, true));
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(270, 120, 400, 30);
        txtUser.setFont(wConfig.getFont(20, false));
        add(txtUser);

        lblPassword = new JLabel("Senha:");
        lblPassword.setBounds(170, 200, 150, 30);
        lblPassword.setFont(wConfig.getFont(30, true));
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(270, 200, 400, 30);
        txtPassword.setFont(wConfig.getFont(20, false));
        txtPassword.setEchoChar('*');
        add(txtPassword);

        btnLogin = new JButton("Entrar");
        btnLogin.setBounds(170, 300, 160, 40);
        btnLogin.setFont(wConfig.getFont(25, true));
        btnLogin.addActionListener(this);
        btnLogin.setMnemonic('E');
        add(btnLogin);

        btnClear = new JButton("Limpar");
        btnClear.setBounds(430, 300, 160, 40);
        btnClear.setFont(wConfig.getFont(25, true));
        btnClear.addActionListener(this);
        btnClear.setMnemonic('L');
        add(btnClear);

        revalidate();
        repaint();
    }

    private void ClearFields() {
        txtUser.setText("");
        txtPassword.setText("");

        txtUser.requestFocus();
    }

    private void Login() {
        Users user = new Users();
        MD5 crypto = new MD5();

        user.setEmail(txtUser.getText());
        user.setPassword(crypto.GenerateHash(txtPassword.getText()));

        ArrayList<String> resp = user.Login();

        if (resp.size() <= 0) {
            JOptionPane.showMessageDialog(null, "Senha ou usuário incorreto!\nRedigite.");
            ClearFields();
            return;
        }

        ClearFields();

        if (resp.get(0).equals("1")) {//Admin
            new AdminMenu(resp.get(1) + " " + resp.get(2), this);
        } else { //Normal user
            System.out.println("Normal guy");
        }

        setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin)
            Login();
        else if (e.getSource() == btnClear)
            ClearFields();
    }

    public static void main(String[] args) {
        new Start();
    }
}
