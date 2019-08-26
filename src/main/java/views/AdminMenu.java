package views;

import tools.*;
import views.books.*;
import views.sections.*;
import views.user.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class AdminMenu extends JFrame implements ActionListener, MenuListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private String userName;
    private BufferedImage imageLoader;
    private Start startMenu;

    //UI Objects
    private JLabel creppe, dea;
    private JMenuBar bar;
    private JMenu books, sections, users, rented, about, logout, report;
    private JMenuItem viewBooks, viewSections, viewUsers, viewRented;
    private JMenuItem newBook, newSection, newUser, newRent;
    private JPanel developers;
    private JLabel lblCreppe, lblDea;

    public AdminMenu(String name, Start login) {
        //Window setup
        super("Bücherei: " + name + " (admin)");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        userName = name;
        startMenu = login;

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        NavigationBar();

        setVisible(true);
    }

    public void NavigationBar() {
        //MenuBar setup
        bar = new JMenuBar();
        setJMenuBar(bar);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        //Menu 'livros'
        books = new JMenu("Livros");
        books.setMnemonic('L');
        bar.add(books);
        //Add menu items
        viewBooks = new JMenuItem("Pesquisa");
        viewBooks.addActionListener(this);
        books.add(viewBooks);
        newBook = new JMenuItem("Cadastrar");
        newBook.addActionListener(this);
        books.add(newBook);

        //Menu 'secao'
        sections = new JMenu("Seção");
        sections.setMnemonic('S');
        bar.add(sections);
        //Add menu items
        viewSections = new JMenuItem("Pesquisa");
        viewSections.addActionListener(this);
        sections.add(viewSections);
        newSection = new JMenuItem("Cadastrar");
        newSection.addActionListener(this);
        sections.add(newSection);

        //Menu 'usuarios'
        users = new JMenu("Usuários");
        users.setMnemonic('U');
        bar.add(users);
        //Add menu items
        viewUsers = new JMenuItem("Pesquisa");
        viewUsers.addActionListener(this);
        users.add(viewUsers);
        newUser = new JMenuItem("Cadastrar");
        newUser.addActionListener(this);
        users.add(newUser);

        //Menu 'aluguel'
        rented = new JMenu("Aluguel");
        rented.setMnemonic('A');
        bar.add(rented);
        //Add menu items
        viewRented = new JMenuItem("Pesquisa");
        viewRented.addActionListener(this);
        rented.add(viewRented);
        newRent = new JMenuItem("Cadastrar");
        newRent.addActionListener(this);
        rented.add(newRent);

        //Menu 'relatorio'
        report = new JMenu("Relatório");
        report.setMnemonic('R');
        report.addMenuListener(this);
        bar.add(report);

        //Menu 'sobre'
        about = new JMenu("Desenvolvedores");
        about.setMnemonic('D');
        about.addMenuListener(this);
        bar.add(about);

        //Menu 'logout'
        logout = new JMenu("Logout");
        logout.setMnemonic('O');
        logout.addMenuListener(this);
        bar.add(logout);

        DevFrame();
    }

    public void DevFrame() {
        try {
            developers = new JPanel();
            developers.setSize(getSize());
            developers.setLayout(null);
            developers.setVisible(false);
            add(developers);

            imageLoader = ImageIO.read(getClass().getResource("/images/creppe.png"));
            creppe = new JLabel(new ImageIcon(imageLoader));
            creppe.setBounds(40, 10, 300, 300);
            developers.add(creppe);

            imageLoader = null;

            imageLoader = ImageIO.read(getClass().getResource("/images/dea.png"));
            dea = new JLabel(new ImageIcon(imageLoader));
            dea.setBounds(440, 10, 300, 300);
            developers.add(dea);

            lblCreppe = new JLabel("<html>André Zanardi Creppe<br/><br/>Número 01</html>");
            lblCreppe.setBounds(125, 290, 300, 100);
            developers.add(lblCreppe);

            lblDea = new JLabel("<html>Thiago Prado Dalla Dea<br/><br/>Número 32</html>");
            lblDea.setBounds(530, 290, 300, 100);
            developers.add(lblDea);

            repaint();
        } /* Ash */ catch /* um */ (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no carregamento da imagem: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void Dashboard() {
        //livros alugados
        //usuarios picaretas
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewBooks) {
            new ViewBook(this);
        } else if (e.getSource() == newBook) {
            new NewBook(this);
        } else if (e.getSource() == viewSections) {
            new ViewSection(this);
        } else if (e.getSource() == newSection) {
            new NewSection(this);
        } else if (e.getSource() == viewUsers) {
            new ViewUser(this);
        } else if (e.getSource() == newUser) {
            new NewUser(this);
        } else if (e.getSource() == viewRented) {
            //new ViewRent(this);
        } else if (e.getSource() == newRent) {
            //new NewRent(this);
        }

        setVisible(false);
    }

    public void menuSelected(MenuEvent e) {
        if (e.getSource() == about) {
            developers.setVisible(true);
        } else if (e.getSource() == logout) {
            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
            if (option == JOptionPane.YES_OPTION) {
                startMenu.setVisible(true);
                dispose();
            }
        } else if (e.getSource() == report) {
            System.out.println("fazer relatoriooooo");
        }
    }

    public void menuDeselected(MenuEvent e) {
        developers.setVisible(false);
    }

    public void menuCanceled(MenuEvent e) {
    }

    public static void main(String[] args) {
        new AdminMenu("Falsiano Fakeson", null);
    }
}
