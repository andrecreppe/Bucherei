package views;

import tools.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

//SET FONTTTTTTTTTTTTT

public class AdminMenu extends JFrame implements ActionListener, MenuListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private String userName;
    private BufferedImage imageLoader;

    //UI Objects
    private JLabel creppe, dea;
    private JMenuBar bar;
    private JMenu books, sections, users, rented, about, logout;
    private JMenuItem viewBooks, viewSections, viewUsers, viewRented;
    private JMenuItem newBook, newSection, newUser, newRent;

    public AdminMenu(String name) {
        //Window setup
        super("Bücherei: " + name + " (admin)");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        userName = name;

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

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

        //Menu 'sobre'
        about = new JMenu("Desenvolvedores");
        about.setMnemonic('D');
        about.addMenuListener(this);
        bar.add(about);

//        //Menu 'logout'
//        logout = new JMenu("Logout");
//        logout.setMnemonic('O');
//        logout.addMenuListener(this);
//        bar.add(logout);

        setVisible(true);
    }

    public void Dashboard() {
        //livros alugados
        //usuarios picaretas
    }

    public void DeveloperInfo() {
        try {
            imageLoader = ImageIO.read(getClass().getResource("/images/creppe.png"));
            creppe = new JLabel(new ImageIcon(imageLoader));
            creppe.setBounds(40, 10, 300, 300);
            add(creppe);

            imageLoader = null;

            imageLoader = ImageIO.read(getClass().getResource("/images/dea.png"));
            dea = new JLabel(new ImageIcon(imageLoader));
            dea.setBounds(440, 10, 300, 300);
            add(dea);
        } /* Ash */ catch /* um */ (Exception e) {
            System.err.println("Gotta catch 'em all");
        }
    }

    public void menuSelected(MenuEvent e) {
        if (e.getSource() == about) {
            System.out.println("os brabo");
            DeveloperInfo();
        }
//        else if(e.getSource() == logout) {
//            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
//            if(option == JOptionPane.YES_OPTION) {
//                dispose();
//            }
//        }
    }

    public void menuDeselected(MenuEvent e) {
    }

    public void menuCanceled(MenuEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) {
        new AdminMenu("TESTE FAKE");
    }
}
