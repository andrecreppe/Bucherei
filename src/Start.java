import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Start extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    //private ImageIcon image;

    //UI Objects
    private JProgressBar loading;
    private JLabel lblUser, lblPassword;
    private JTextField txtUser, txtPassword;
    private JButton btnClear, btnLogin;

    Start() {
        //Window setup
        super("Bücherei");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        //Window elements
        loading = new JProgressBar(1, 100);
        loading.setBounds(wConfig.getWidth() - 600, wConfig.getHeight() - 200, wConfig.getWidth() / 2, 30);
        add(loading);

        //Show
        setVisible(true);

        //Load the aplication
        //ProgressBar();
        LoginUI();
    }

    private void ProgressBar() {
        loading.setStringPainted(true);
        loading.setValue(0);
        loading.setString("0%");
        for (int i = 1; i <= 100; i++) {
            loading.setValue(i);
            loading.setString(i + "%");
            loading.repaint();

            try {
                if (i == 100)
                    Thread.sleep(1000);
                else
                    Thread.sleep(10);
            } catch (Exception e) {
                String msg = "Oops, aconteceu algum erro!";
                msg += "\n\nErro no carregamento: " + e.getMessage();

                JOptionPane.showMessageDialog(null, msg);
            }
        }
    }

    private void LoginUI() {
        remove(loading);

        lblUser = new JLabel("Usuário:");
        lblUser.setBounds(150, 120, 150, 30);
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

        txtPassword = new JTextField();
        txtPassword.setBounds(270, 200, 400, 30);
        txtPassword.setFont(wConfig.getFont(20, false));
        add(txtPassword);

        btnLogin = new JButton("Entrar");
        btnLogin.setBounds(150, 300, 160, 40);
        btnLogin.setFont(wConfig.getFont(25, true));
        btnLogin.addActionListener(this);
        btnLogin.setMnemonic('E');
        add(btnLogin);

        btnClear = new JButton("Limpar");
        btnClear.setBounds(350, 300, 160, 40);
        btnClear.setFont(wConfig.getFont(25, true));
        btnClear.addActionListener(this);
        btnClear.setMnemonic('L');
        add(btnClear);

        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin)
            JOptionPane.showMessageDialog(null, "Logando...");
        else if (e.getSource() == btnClear) {
            txtUser.setText("");
            txtPassword.setText("");

            txtUser.requestFocus();
        }
    }

    public static void main(String[] args) {
        new Start();
    }
}
