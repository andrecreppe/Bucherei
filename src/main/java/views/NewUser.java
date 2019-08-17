package views;

import tools.*;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewUser extends JFrame implements ActionListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private AdminMenu adminMenu;

    //UI Objects
    private JLabel lblTitle, lblName, lblSurname, lblCPF, lblEmail, lblPassword, lblIcon, lblAdmin;
    private JTextField txtName, txtSurname, txtEmail;
    private JFormattedTextField txtCPF;
    private JPasswordField txtPassword;
    private JComboBox cmbIcons;
    private JRadioButton radAdmin, radUser;
    private JButton btnAdd, btnClear, btnCancel;

    public NewUser(AdminMenu menu) {
        //Window setup
        super("Bücherei: Novo Usuário");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        adminMenu = menu;

        InitializeUI();

        setVisible(true);
    }

    private void InitializeUI() {
        lblName = new JLabel("Nome");
        lblName.setBounds(300, 20, 50, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(350, 20, 200, 30);
        add(txtName);

        lblSurname = new JLabel("Sobrenome");
        lblSurname.setBounds(250, 70, 50, 30);
        add(lblSurname);

        txtSurname = new JTextField();
        txtSurname.setBounds(350, 70, 200, 30);
        add(txtSurname);
    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new NewUser(null);
    }
}
