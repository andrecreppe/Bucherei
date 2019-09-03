package views.user;

import tools.*;
import views.*;
import views.user.books.ViewBook;
import views.user.rents.ViewMyRents;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class UserMenu extends JFrame implements ActionListener, MenuListener {
    //Control Variables
    private WindowConfiguration wConfig;
    private BufferedImage imageLoader;
    private Start startMenu;

    //UI Objects
    private JLabel creppe, dea, bucherei;
    private JMenuBar bar;
    private JMenu books, rented, about, logout;
    private JMenuItem viewBooks, viewRented;
    private JPanel developers;
    private JLabel lblCreppe, lblDea;

    public UserMenu(String name, Start login) {
        //Window setup
        super("Bücherei: " + name + " (user)");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        startMenu = login;

        //Window size
        wConfig = new WindowConfiguration();
        setBounds(wConfig.getCoordinateX(), wConfig.getCoordinateY(), wConfig.getWidth(), wConfig.getHeight());

        NavigationBar();

        setVisible(true);
    }

    private void NavigationBar() {
        try {
            //MenuBar setup
            bar = new JMenuBar();
            setJMenuBar(bar);
            getContentPane().setBackground(new Color(226, 255, 217));

            //Menu 'livros'
            books = new JMenu("Livros");
            books.setMnemonic('L');
            bar.add(books);
            //Add menu items
            viewBooks = new JMenuItem("Pesquisa");
            viewBooks.addActionListener(this);
            books.add(viewBooks);

            //Menu 'aluguel'
            rented = new JMenu("Meus aluguéis");
            rented.setMnemonic('A');
            bar.add(rented);
            //Add menu items
            viewRented = new JMenuItem("Pesquisa");
            viewRented.addActionListener(this);
            rented.add(viewRented);

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

            //Logo
            imageLoader = null;
            imageLoader = ImageIO.read(getClass().getResource("/images/logo.png"));
            bucherei = new JLabel(new ImageIcon(imageLoader));
            bucherei.setBounds(50, 90, 707, 240);
            add(bucherei);

            DevFrame();
        } catch (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no carregamento dos gráficos: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    private void DevFrame() {
        try {
            developers = new JPanel();
            developers.setSize(getSize());
            developers.setLayout(null);
            developers.setVisible(false);
            add(developers);

            imageLoader = null;
            imageLoader = ImageIO.read(getClass().getResource("/images/creppe.png"));
            creppe = new JLabel(new ImageIcon(imageLoader));
            creppe.setBounds(40, 10, 300, 300);
            developers.add(creppe);

            imageLoader = null;
            imageLoader = ImageIO.read(getClass().getResource("/images/dea.png"));
            dea = new JLabel(new ImageIcon(imageLoader));
            dea.setBounds(440, 10, 300, 300);
            developers.add(dea);

            lblCreppe = new JLabel("01 - André Zanardi Creppe");
            lblCreppe.setBounds(125, 290, 300, 100);
            developers.add(lblCreppe);

            lblDea = new JLabel("32 - Thiago Prado Dalla Dea");
            lblDea.setBounds(530, 290, 300, 100);
            developers.add(lblDea);

            repaint();
        } /* Ash */ catch /* um */ (Exception e) {
            String msg = "Oops, aconteceu algum erro!";
            msg += "\n\nErro no carregamento da imagem: " + e.getMessage();

            JOptionPane.showMessageDialog(null, msg);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewRented) {
            new ViewMyRents(this);
        } else if (e.getSource() == viewBooks) {
            new ViewBook(this);
        }

        setVisible(false);
    }

    public void menuSelected(MenuEvent e) {
        if (e.getSource() == about) {
            developers.setVisible(true);
            bucherei.setVisible(false);
        } else if (e.getSource() == logout) {
            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?",
                    "Bücherei", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                startMenu.setVisible(true);
                dispose();
            }
        }
    }

    public void menuDeselected(MenuEvent e) {
        developers.setVisible(false);
        bucherei.setVisible(true);
    }

    public void menuCanceled(MenuEvent e) {
    }

    public static void main(String[] args) {
        new UserMenu("Falsiano Fakeson", null);
    }
}
