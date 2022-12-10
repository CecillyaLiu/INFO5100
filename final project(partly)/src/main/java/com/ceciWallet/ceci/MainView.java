package com.ceciWallet.ceci;

import com.mysql.cj.jdbc.ConnectionImpl;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.router.Route;
import org.apache.catalina.User;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Pattern;

@Route("")
public class MainView {
    private JPanel contentPane;
    private MyPanel panel1;
    private MyPanel2 panel2;
    private MyPanel2 panel3;
    private homePagePanel homePage;
    private UserLogin userLoginPanel;
    private registerPanel register;
    private forgotPanel forgotPanel;
    private accountPanel accountPanel;
    private JComboBox choiceBox;
    private String[] choices = {
            "Panel 1",
            "Panel 2",
            "Panel 3"
    };


    void displayGUI() throws IOException, FontFormatException, SQLException, ClassNotFoundException {
        JFrame frame = new JFrame("MyWallet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());

        choiceBox = new JComboBox(choices);

        panel1 = new MyPanel(contentPane, this);
        panel2 = new MyPanel2(contentPane, Color.GREEN.darker().darker(), this);
        panel3 = new MyPanel2(contentPane, Color.DARK_GRAY, this);
        userLoginPanel = new UserLogin(contentPane, this);
        homePage = new homePagePanel(contentPane, this);
        register = new registerPanel(contentPane, this);
        forgotPanel = new forgotPanel(contentPane, this);
        accountPanel = new accountPanel(contentPane, this);

        contentPane.add(userLoginPanel, "User Login Panel");
        contentPane.add(homePage, "homePage 1");
        contentPane.add(panel1, "Panel 1");
        contentPane.add(panel2, "Panel 2");
        contentPane.add(panel3, "Panel 3");
        contentPane.add(register,"Register");
        contentPane.add(forgotPanel, "Forgot");
        contentPane.add(accountPanel, "Account Book 1");

        frame.getContentPane().add(choiceBox, BorderLayout.PAGE_START);
        frame.getContentPane().add(contentPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);


// var button = new Button("Click me!");
//        var textField = new TextField();
//
//        add(new HorizontalLayout(textField, button));
//
//        button.addClickListener(e -> {
//            add(new Paragraph("Hello," + textField.getValue()));
//            textField.clear();
//        });
    }
    public JComboBox getChoiceBox() {
        return choiceBox;
    }
}
class UserLogin extends JPanel {
    private static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private JLabel username = new JLabel();
    private JLabel password = new JLabel();
    private static JTextField username_ = new JTextField();
    private static JTextField password_ = new JTextField();
    private JLabel dontHaveAnAccount = new JLabel();
    private JButton sighUp = new JButton();
    private JButton forgotPassword = new JButton();
    private JButton signIn = new JButton();

    private JPanel contentPane;

    public static void setUsername_() {
        username_.setText("");
    }
    public static Connection getConnection() {
        return con;
    }

    public static void setPassword_() {
        password_.setText("");
    }

    public boolean ifUserSignIn () throws SQLException {
        if (username_.getText().isEmpty()|| password_.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
            username_.setText("");
            password_.setText("");
            return false;
        }
        String name = username_.getText();
        String pass = password_.getText();
        if (usernameExist(name)) {
            if (pass.equals(getPassword(name))) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Wrong password" ,"Try again", JOptionPane.ERROR_MESSAGE);
                username_.setText("");
                password_.setText("");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(this, "User doesn't exist, please sign up" ,"Try again", JOptionPane.ERROR_MESSAGE);
            username_.setText("");
            password_.setText("");
            return false;
        }

    }
    public String getPassword(String name) throws SQLException {
        Statement stmt = con.createStatement();
        String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
        ResultSet rs = stmt.executeQuery(SQL);
        if(rs.next()){
//            System.out.println(rs.getString("password"));
            return rs.getString("password");
        };
//        stmt.close();
        return "";
    }
    public boolean usernameExist(String name) {
        try {

            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
            ResultSet rs = stmt.executeQuery(SQL);
//            stmt.close();
            return rs.next();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public UserLogin(JPanel panel, MainView mv) throws IOException, SQLException {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("/Users/cecillya/Documents/22Fall/5100/FontsFree-Net-Modern1.ttf")).deriveFont(Font.PLAIN, 50);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        contentPane = panel;
        setOpaque(true);
        setBackground(new Color(249, 247, 225));
        this.setLayout(null);

        // construct components
        JLabel appName = new JLabel("Ceci's Wallet");
        appName.setFont(font);
        appName.setBounds(185, 120, 400, 100);
        add(appName);
//        BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
//        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
//        add(picLabel);
        username.setText("Username");
        Font myFont1 = new Font("Britannic", Font.BOLD, 13);
        username.setFont(myFont1);
        username.setBounds(230, 220, 100, 25);

        password.setText("Password");
        Font myFont2 = new Font("Britannic", Font.BOLD, 13);
        password.setFont(myFont2);
        password.setBounds(230, 285, 100, 25);

//        Font newLabelFont=new Font(username.getFont().getName(),Font.BOLD,username.getFont().getSize());
//        username.setFont(newLabelFont);
//
//        Font newLabelFont_=new Font(password.getFont().getName(),Font.BOLD, password.getFont().getSize());
//        password.setFont(newLabelFont_);

        username_.setBounds(230, 245, 200, 40);
        password_.setBounds(230, 305, 200, 40);
        add(username);
        add(password);
        add(username_);
        add(password_);


        signIn.setBounds(280, 365, 100, 35);
        signIn.setText("Sign In");
        Font myFont3 = new Font("Britannic", Font.BOLD, 15);
        signIn.setFont(myFont3);
        add(signIn);

//        signIn.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                super.keyPressed(e);
//                if (e.getKeyCode() == 10) {
//                    System.out.println("fffff");
//                }
//            }
//        });

        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ifUserSignIn()) {

//                        String changeToPanel = "homePage 1";
                        String changeToPanel = "Account Book 1";
                        CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                        cardLayout.show(contentPane, changeToPanel);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        dontHaveAnAccount.setText("Don't have an account?");
        Font myFont4 = new Font("Britannic", Font.PLAIN, 13);
        dontHaveAnAccount.setFont(myFont4);
        dontHaveAnAccount.setBounds(230, 420, 150, 25);
        add(dontHaveAnAccount);

        sighUp.setText("Sign up");
        sighUp.setBounds(360, 420, 100, 25);
        sighUp.setFont(myFont2);
        add(sighUp);

        sighUp.setOpaque(false);
        sighUp.setContentAreaFilled(false);
        sighUp.setBorderPainted(false);

        sighUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = "Register";
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
        });

        forgotPassword.setText("Forgot Password?");
        forgotPassword.setFont(myFont2);
        forgotPassword.setBounds(230, 440, 200, 35);

        forgotPassword.setOpaque(false);
        forgotPassword.setContentAreaFilled(false);
        forgotPassword.setBorderPainted(false);
        forgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = "Forgot";
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
        });
        add(forgotPassword);


    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}

class registerPanel extends JPanel {
//    private Connection con = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
    private JLabel signUp = new JLabel();
    private JLabel inputUsername = new JLabel();
    private JLabel inputPassword = new JLabel();
    private JTextArea inputUsername_ = new JTextArea();
    private JTextArea inputPassword_ = new JTextArea();
    private JPanel contentPane;
    private JButton signUpButton = new JButton();
    private JButton back = new JButton();
    public boolean usernameExist(String name) {
        try {

            Statement stmt = UserLogin.getConnection().createStatement();
            String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            stmt.close();
            return rs.next();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    public registerPanel(JPanel panel, MainView mv) throws ClassNotFoundException, SQLException {

        contentPane = panel;
        setOpaque(true);
//        setBackground(new Color(238, 242, 218));
        setBackground(new Color(249, 247, 225));
        this.setLayout(null);

        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("/Users/cecillya/Documents/22Fall/5100/FontsFree-Net-Modern1.ttf")).deriveFont(Font.PLAIN, 50);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // construct components
        JLabel appName = new JLabel("Ceci's Wallet");
        appName.setFont(font);
        appName.setBounds(185, 120, 400, 100);
        add(appName);

        Font myFont0 = new Font("Britannic", Font.BOLD, 20);
        Font myFont1 = new Font("Britannic", Font.BOLD, 13);
        JLabel signUp = new JLabel("Sign Up");

        signUp.setFont(myFont0);
        signUp.setBounds(290, 150, 400, 100);
        add(signUp);

        inputUsername.setText("Username");
        inputUsername.setFont(myFont1);
        inputUsername.setBounds(230, 220, 100, 25);

        inputPassword.setText("Password");
        Font myFont2 = new Font("Britannic", Font.BOLD, 13);
        inputPassword.setFont(myFont2);
        inputPassword.setBounds(230, 285, 100, 25);
        inputUsername_.setBounds(230, 250, 200, 30);
        inputPassword_.setBounds(230, 310, 200, 30);
        add(inputUsername);
        add(inputPassword);
        add(inputUsername_);
        add(inputPassword_);

        signUpButton.setBounds(280, 365, 100, 35);
        Font myFont3 = new Font("Britannic", Font.BOLD, 15);
        signUpButton.setFont(myFont3);
        signUpButton.setText("Confirm");
        add(signUpButton);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                        register();
                } catch (ClassNotFoundException | SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        back.setText("Go back to the login page");
        Font myFont4 = new Font("Britannic", Font.PLAIN, 13);
        back.setFont(myFont4);
        back.setBounds(390, 480, 300, 25);

        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = "User Login Panel";
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
        });
        add(back);

    }

    public void register() throws ClassNotFoundException, SQLException {
        if (inputPassword_.getText().isEmpty() || inputUsername_.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
            inputUsername_.setText("");
            inputPassword_.setText("");
        } else if (usernameExist(inputUsername_.getText())) {
            int response = JOptionPane.showConfirmDialog(this,
                    "User already exists, do you want to register again? \nChoose NO to go back to login",
                    "User exists",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                String changeToPanel = "User Login Panel";
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
            inputUsername_.setText("");
            inputPassword_.setText("");
        } else{
            String insert = "insert into userInformation(username, password) values(?,?)" ;
            PreparedStatement ps = UserLogin.getConnection().prepareStatement(insert);
            ps.setString(1,inputUsername_.getText());
            ps.setString(2,inputPassword_.getText());
            int result1 =  ps.executeUpdate();
            // int result1 = st.executeUpdate(insert);
            String changeToPanel = "homePage 1";
            CardLayout cardLayout = (CardLayout) contentPane.getLayout();
            cardLayout.show(contentPane, changeToPanel);
            ps.close();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}

class forgotPanel extends JPanel {
//    private Connection con = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
    private JLabel forgot = new JLabel();
    private JButton confirm = new JButton();
    private JButton back = new JButton();
    private JPanel contentPane;
    private JLabel inputUsername = new JLabel();
    private JLabel inputPassword = new JLabel();
    private JTextArea inputUsername_ = new JTextArea();
    private JLabel inputPassword_ = new JLabel();

    public boolean usernameExist(String name) {
        try {

            Statement stmt = UserLogin.getConnection().createStatement();
            String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            stmt.close();
            return rs.next();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public String getPassword(String name) throws SQLException {
        Statement stmt = UserLogin.getConnection().createStatement();
        String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
        ResultSet rs = stmt.executeQuery(SQL);
        if(rs.next()){
//            System.out.println(rs.getString("password"));
            return rs.getString("password");
        };
        stmt.close();
        return "";
    }

    public void confirm() throws SQLException {
        if (usernameExist(inputUsername_.getText())) {
            inputPassword_.setText(getPassword(inputUsername_.getText()));
        } else {
            JOptionPane.showMessageDialog(this, "User doesn't exist, please sign up", "No Such User", JOptionPane.INFORMATION_MESSAGE);
            inputUsername_.setText("");
            inputPassword_.setText("");
        }
    }
    public forgotPanel(JPanel panel, MainView mv) throws SQLException {

        contentPane = panel;
        setOpaque(true);
        setBackground(new Color(249, 247, 225));
        this.setLayout(null);

        Font myFont0 = new Font("Britannic", Font.BOLD, 20);
        Font myFont1 = new Font("Britannic", Font.BOLD, 13);
        forgot.setText("Retrieve Password");
        forgot.setFont(myFont0);
        forgot.setBounds(230, 120, 400, 100);
        add(forgot);

        inputUsername.setText("Your username is:");
        inputUsername.setFont(myFont1);
        inputUsername.setBounds(230, 220, 300, 25);

        inputPassword.setText("Your Password is:");
        Font myFont2 = new Font("Britannic", Font.BOLD, 13);
        inputPassword.setFont(myFont2);
        inputPassword.setBounds(230, 285, 300, 25);
        inputUsername_.setBounds(230, 245, 200, 30);
        inputPassword_.setBounds(230, 305, 200, 30);

        add(inputUsername);
        add(inputPassword);
        add(inputUsername_);
        add(inputPassword_);

        confirm.setBounds(280, 365, 100, 35);
        Font myFont3 = new Font("Britannic", Font.BOLD, 15);
        confirm.setFont(myFont3);
        confirm.setText("Confirm");
        add(confirm);
        confirm.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                confirm();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        });

        back.setText("Go back to the login page");
        Font myFont4 = new Font("Britannic", Font.PLAIN, 13);
        back.setFont(myFont4);
        back.setBounds(390, 480, 300, 25);

        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = "User Login Panel";
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
        });
        add(back);

    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}



class homePagePanel extends JPanel {
    private JPanel contentPane;
//    private Connection con = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
    private JButton back =  new JButton();
    private JPanel panel1_r = new JPanel();
    private String chosenAccountBook = "";
    private int numberAccountBook = 0;
    private JRadioButton accountBook1 = new JRadioButton();
    private JRadioButton accountBook2 = new JRadioButton();
    private JRadioButton accountBook3 = new JRadioButton();
    private Boolean accountBook1_status = false;
    private Boolean accountBook2_status = false;
    private Boolean accountBook3_status = false;

//    public void setAccountBook1 (Boolean status) {
//        accountBook1_status = status;
//    }
//
//    public void setAccountBook2 (Boolean status) {
//        accountBook2_status = status;
//    }
//
//    public void setAccountBook3 (Boolean status) {
//        accountBook3_status = status;
//    }

//    public void setAccountBook (JRadioButton accountBook, Boolean status) {
//        if (accountBook.equals(accountBook1)) {
//            setAccountBook1(status);
//        } else if (accountBook.equals(accountBook2)) {
//            setAccountBook2(status);
//        } else {
//            setAccountBook3(status);
//        }
//    }
//
//    public boolean getAccountBook1 () {
//        return(accountBook1_status);
//    }
//
//    public boolean getAccountBook2 () {
//        return(accountBook2_status);
//    }
//
//    public boolean getAccountBook3 () {
//        return(accountBook3_status);
//    }

    public JRadioButton getChosenButton(){
        if (accountBook1.isSelected()) {
            return accountBook1;
        } else if (accountBook2.isSelected()) {
            return accountBook2;
        } else {
            return accountBook3;
        }
    }

    public String getChosenAccountBook() {
        return getChosenButton().getText();
    }
//    public int showConfirmDialog() {
//        int response = JOptionPane.showConfirmDialog(this, "Do you want to delete the chosen account book?", "Delete or not", JOptionPane.YES_NO_OPTION);
//        return response;
//    }
//
//    public void showCantDelete() {
//        JOptionPane.showMessageDialog(this, "No account book can be deleted");
//    }
    public void setInitialization() throws SQLException {
        Statement stmt3 = UserLogin.getConnection().createStatement();
        String sql3 = "SELECT name FROM accountBookName";
        ResultSet rs3 = stmt3.executeQuery(sql3);
        String[] name = new String[3];
        int j = 0;
        while(rs3.next()) {
            numberAccountBook++;
            System.out.println(numberAccountBook);
            name[j] = rs3.getString("name");
            j++;
        }
        accountBook1.setText(name[0]);
//        setAccountBook1(true);
        accountBook2.setText(name[1]);
//        setAccountBook2(true);
        accountBook3.setText(name[2]);
//        setAccountBook3(true);
    }
//    public void showAddFailure(){
//        JOptionPane.showMessageDialog(this, "Maximal number of account book is 3");
//    }
    public void showNameNotProper() {
        JOptionPane.showMessageDialog(this, "Name can only contain letters, please try again");
    }

    public homePagePanel(JPanel panel, MainView mv) throws SQLException {
        contentPane = panel;
        setOpaque(true);
        setBackground(new Color(249, 247, 225));
        this.setLayout(null);

        // split the panel
        this.setBorder(new TitledBorder("Home Page"));
        this.setLayout(new GridLayout(2, 2));

        setInitialization();

        // upper left
        JPanel panel1 = new JPanel();
//        panel1.setBorder(new TitledBorder("Content Bar"));
        panel1.setLayout(new GridLayout(1, 2));
        panel1.setOpaque(true);
        panel1.setBackground(new Color(249, 247, 225));

        JPanel panel1_l = new JPanel();
        panel1_l.setBorder(new TitledBorder("Tool Content"));
        panel1_l.setBackground(new Color(249, 247, 225));
        panel1_l.setLayout(null);
//        panel1_l.setLayout(new BoxLayout(panel1_l, BoxLayout.Y_AXIS));
//        panel1_l.setLayout(new BorderLayout());
        JButton summary_button = new JButton("Summary");
        JButton accountBook_button = new JButton("Account Book");
        JButton budget_button = new JButton("Budget");
        JButton converter_button = new JButton("Converter");
        JButton authorPage_button = new JButton("Author Page");

        summary_button.setBounds(5, 20, 150, 25);
        accountBook_button.setBounds(5, 50, 150, 25);
        budget_button.setBounds(5, 80, 150, 25);
        converter_button.setBounds(5, 110, 150, 25);
        authorPage_button.setBounds(5, 140, 150, 25);

        back.setText("Sign Out");
        Font myFont4 = new Font("Britannic", Font.PLAIN, 13);
        back.setFont(myFont4);
        back.setBounds(5, 200, 150, 25);

        back.setOpaque(false);
        back.setContentAreaFilled(false);
        back.setBorderPainted(false);
        back.setBorder(new LineBorder(Color.BLACK, 2));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = "User Login Panel";
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
                UserLogin.setUsername_();
                UserLogin.setPassword_();
            }
        });

        panel1_l.add(summary_button);
        panel1_l.add(accountBook_button);
        panel1_l.add(budget_button);
        panel1_l.add(converter_button);
        panel1_l.add(authorPage_button);
        panel1_l.add(back);

        //summary panel
        JPanel summary_Panel = new JPanel();
        JPanel panelUP_summary = new JPanel();
        JPanel panelDown_summary = new JPanel();

        ButtonGroup group1_summary = new ButtonGroup();
        ButtonGroup group2_summary = new ButtonGroup();

        summary_Panel.setOpaque(true);
        summary_Panel.setBackground(new Color(249, 247, 225));
        summary_Panel.setLayout(new BorderLayout());
        summary_Panel.setBorder(new TitledBorder("Summary"));


        JRadioButton radioButton1 = new JRadioButton("Expenditure");
        JRadioButton radioButton2 = new JRadioButton("Income");
        group1_summary.add(radioButton1);
        group1_summary.add(radioButton2);

        JRadioButton radioButton3 = new JRadioButton("Account Book 1");
        JRadioButton radioButton4 = new JRadioButton("Account Book 2");
        JRadioButton radioButton5 = new JRadioButton("Account Book 3");
        group2_summary.add(radioButton3);
        group2_summary.add(radioButton4);
        group2_summary.add(radioButton5);

        radioButton1.setSelected(true);
        radioButton3.setSelected(true);

        panelUP_summary.setLayout(new BoxLayout(panelUP_summary, BoxLayout.Y_AXIS));
        panelUP_summary.add(radioButton3);
        panelUP_summary.add(radioButton4);
        panelUP_summary.add(radioButton5);
        panelUP_summary.setOpaque(true);
        panelUP_summary.setBackground(new Color(249, 247, 225));

        panelDown_summary.setLayout(new BoxLayout(panelDown_summary, BoxLayout.Y_AXIS));
        panelDown_summary.add(radioButton1);
        panelDown_summary.add(radioButton2);
        panelDown_summary.setOpaque(true);
        panelDown_summary.setBackground(new Color(249, 247, 225));

        summary_Panel.setLayout(new BorderLayout());
        summary_Panel.add(panelUP_summary, BorderLayout.NORTH);
        summary_Panel.add(panelDown_summary, BorderLayout.CENTER);

        //

        panel1.add(panel1_l);
        panel1_r = summary_Panel;
        panel1.add(panel1_r);

        add(panel1);

        //account panel
        JPanel account_Panel = new JPanel();
        JPanel panelUP_account = new JPanel();
        JPanel panelDown_account = new JPanel();

        ButtonGroup group1_account = new ButtonGroup();
        ButtonGroup group2_account = new ButtonGroup();

        account_Panel.setOpaque(true);
        account_Panel.setBackground(new Color(249, 247, 225));
        account_Panel.setLayout(new BorderLayout());
        account_Panel.setBorder(new TitledBorder("Account Book"));
        panelUP_account.setLayout(new BoxLayout(panelUP_account, BoxLayout.Y_AXIS));
        panelDown_account.setLayout(new BoxLayout(panelDown_account, BoxLayout.Y_AXIS));

//        JLabel tip = new JLabel();
//        if (numberAccountBook == 0) {
//            tip.setBounds(170, 20, 100, 50);
//            tip.setText("Create a new one!");
//            panelUP_account.add(tip);
//        }
        JRadioButton radioButton6 = new JRadioButton("Account Book 1");
        JRadioButton radioButton7 = new JRadioButton("Account Book 2");
        JRadioButton radioButton8 = new JRadioButton("Account Book 3");
        group1_account.add(radioButton6);
        group1_account.add(radioButton7);
        group1_account.add(radioButton8);

//        JButton addAccountBook = new JButton("Add");
//        JButton deleteAccountBook = new JButton("Delete");
//        JButton renameAccountBook = new JButton("Rename");
        JButton gotoDetailedPage = new JButton("Go to Check");

        group2_account.add(gotoDetailedPage);
//        group2_account.add(addAccountBook);
//        group2_account.add(deleteAccountBook);
//        group2_account.add(renameAccountBook);


        panelUP_account.setOpaque(true);
        panelUP_account.setBackground(new Color(249, 247, 225));

        panelDown_account.add(gotoDetailedPage);
//        panelDown_account.add(addAccountBook);
//        panelDown_account.add(deleteAccountBook);

        panelUP_account.add(radioButton6);
        panelUP_account.add(radioButton7);
        panelUP_account.add(radioButton8);

//        panelDown_account.add(renameAccountBook);
        panelDown_account.setOpaque(true);
        panelDown_account.setBackground(new Color(249, 247, 225));

        account_Panel.setLayout(new BorderLayout());
        account_Panel.add(panelUP_account, BorderLayout.NORTH);
        account_Panel.add(panelDown_account, BorderLayout.CENTER);



        summary_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1_r.removeAll();
                panel1_r.revalidate();
                panel1_r.repaint();
                panel1_r.setLayout(new BorderLayout());
                panel1_r.add(panelUP_summary, BorderLayout.NORTH);
                panel1_r.add(panelDown_summary, BorderLayout.CENTER);
                panel1_r.setBorder(new TitledBorder("Summary"));

            }
        });

        accountBook_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonGroup group1_account = new ButtonGroup();
                panel1_r.removeAll();
                panel1_r.revalidate();
                panel1_r.repaint();
                panel1_r.setLayout(new BorderLayout());
//                if (getAccountBook1()) {
//                    group1_account.add(accountBook1);
//                    panelUP_account.add(accountBook1);
//                }
//                if (getAccountBook2()) {
//                    group1_account.add(accountBook2);
//                    panelUP_account.add(accountBook2);
//                }
//                if (getAccountBook3()) {
//                    group1_account.add(accountBook3);
//                    panelUP_account.add(accountBook3);
//                }
                panel1_r.add(panelUP_account, BorderLayout.NORTH);
                panel1_r.add(panelDown_account, BorderLayout.CENTER);
                panel1_r.setBorder(new TitledBorder("Account Book"));

            }
        });

        budget_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1_r.removeAll();
                panel1_r.revalidate();
                panel1_r.repaint();
                panel1_r.setBorder(new TitledBorder("Budget Settings"));

            }
        });

        converter_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1_r.removeAll();
                panel1_r.revalidate();
                panel1_r.repaint();
                panel1_r.setBorder(new TitledBorder("Currency Converter"));

            }
        });

        authorPage_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel1_r.removeAll();
                panel1_r.revalidate();
                panel1_r.repaint();
                panel1_r.setBorder(new TitledBorder("Author Information"));

            }
        });


//        addAccountBook.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (numberAccountBook < 3) {
//                    String name = JOptionPane.showInputDialog("Input your new account book name:");
//
//                    if (name == null ||!Pattern.matches("[a-zA-Z]+",name)) {
//                        showNameNotProper();
//                    }else{
//                        try {
//                            Statement stmt4 = UserLogin.getConnection().createStatement();
//                            String sql4 = "CREATE TABLE " + name + " (item VARCHAR(255) NOT NULL, price DECIMAL(2), thedate DATE, inOrOUT int, category VARCHAR(255))";
//                            stmt4.executeQuery(sql4);
//                            stmt4.close();
//                            Statement stmt5 = UserLogin.getConnection().createStatement();
//                            String sql5 = "INSERT INTO accountBookName (name)" + " VALUES (" + "'" + name + "'" + ")";
//                            stmt5.executeQuery(sql5);
//                            stmt5.close();
//                            if (!getAccountBook1()) {
//                                accountBook1.setText(name);
//                                setAccountBook1(true);
//                            } else if (getAccountBook1() && !getAccountBook2()) {
//                                accountBook2.setText(name);
//                                setAccountBook2(true);
//                            } else {
//                                accountBook3.setText(name);
//                                setAccountBook3(true);
//                            }
//                        } catch (SQLException ef) {
//                            ef.printStackTrace();
//                            System.out.println("fail to connect to database");
//                        }
//
//                    }
//
//
//                    numberAccountBook++;
//                } else {
//                    showAddFailure();
//                }
//            }
//        });

//        deleteAccountBook.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!Objects.equals(getChosenAccountBook(), "")) {
//
//                    int response = showConfirmDialog();
//                    if (response == JOptionPane.YES_OPTION) {
//                        try {
////                            dropTable();
//                            Statement stmt = UserLogin.getConnection().createStatement();
//                            System.out.println(getChosenAccountBook());
//                            String sql = "DROP TABLE " + getChosenAccountBook();
//                            stmt.executeUpdate(sql);
//                            stmt.close();
//                            Statement stmt2 = UserLogin.getConnection().createStatement();
//                            String sql2 = "DELETE FROM accountBookName WHERE name =" + getChosenAccountBook();
//                            stmt2.executeQuery(sql2);
//                            stmt2.close();
//                            panelUP_account.remove(getChosenButton());
//                            setAccountBook(getChosenButton(), false);
//                        } catch (SQLException ef) {
//                            ef.printStackTrace();
//                            System.out.println("fail to connect to database");
//                        }
//                        numberAccountBook--;
//                        if (numberAccountBook == 0) {
//                            chosenAccountBook = "";
//                        }
//                    }
//                } else {
//                    showCantDelete();
//                }
//
//            }
//        });


        // upper right
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel tabPanel1 = new JPanel();
        tabPanel1.setLayout(new BorderLayout());

        JPanel tabPanelCheckboxPanel = new JPanel();
        tabPanelCheckboxPanel.setLayout(new BoxLayout(tabPanelCheckboxPanel, BoxLayout.Y_AXIS));
        tabPanelCheckboxPanel.add(new JCheckBox("UncheckedCheckBox"));
        JCheckBox cb = new JCheckBox("CheckedCheckBox");
        cb.setSelected(true);
        tabPanelCheckboxPanel.add(cb);
        JCheckBox cb2 = new JCheckBox("InactiveCheckBox");
        cb2.setEnabled(false);
        tabPanelCheckboxPanel.add(cb2);
        tabPanel1.add(tabPanelCheckboxPanel, BorderLayout.CENTER);
//        tabPanel1.add(new JSlider(0, 100, 70), BorderLayout.SOUTH);

        tabbedPane.addTab("SelectedTab", tabPanel1);
        tabbedPane.addTab("OtherTab", new JPanel());
        add(tabbedPane);

        // bottom left
        JPanel panel3 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel3.add(new JTextField("TextField"));
        panel3.add(new JPasswordField("PasswordField"));
        JComboBox<String> combo = new JComboBox<String>(new String[] { "Item1", "Item2", "Item3"});
        panel3.add(combo);

        // bottom right
        JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());
        panel4.add(panel3, BorderLayout.NORTH);
        add(panel4);

        add(new JTextArea("TextArea"));


    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}


class accountPanel extends JPanel {
    private JPanel contentPane;

    public accountPanel(JPanel panel, MainView mv) throws SQLException { //  account book 1
        contentPane = panel;
        setOpaque(true);
        setBackground(new Color(249, 247, 225));
        this.setLayout(null);

        JPanel upPanel = new JPanel();
        upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
        upPanel.setOpaque(true);
        upPanel.setBackground(new Color(249, 247, 225));

        JPanel downPanel = new JPanel();
        downPanel.setOpaque(true);
        downPanel.setBackground(new Color(249, 247, 225));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(new Color(249, 247, 225));


        GridLayout grid = new GridLayout(3, 0); // name, amount, budget, chose date, choose in or out
        JPanel grid1 = new JPanel();
        JPanel grid2 = new JPanel();
        JPanel grid3 = new JPanel();

        upPanel.setLayout(grid);
        upPanel.add(grid1);
        upPanel.add(grid2);
        upPanel.add(grid3);

        grid1.setOpaque(true);
        grid1.setBackground(new Color(249, 247, 225));
        grid2.setOpaque(true);
        grid2.setBackground(new Color(249, 247, 225));
        grid3.setOpaque(true);
        grid3.setBackground(new Color(249, 247, 225));

        Font myFont2 = new Font("Britannic", Font.BOLD, 20);
        JLabel name = new JLabel("Account Book:");
        JLabel name_ = new JLabel("Account Book 1");

        grid1.setLayout(new BoxLayout(grid1, BoxLayout.X_AXIS));
        grid1.add(name);
        grid1.add(name_);
        name_.setFont(myFont2);

        grid2.setLayout(new GridLayout(1,2));
        JPanel gridIn2_1 = new JPanel();
        JPanel gridIn2_2 = new JPanel();

        gridIn2_1.setOpaque(true);
        gridIn2_1.setBackground(new Color(249, 247, 225));
        gridIn2_2.setOpaque(true);
        gridIn2_2.setBackground(new Color(249, 247, 225));

        grid2.add(gridIn2_1);
        grid2.add(gridIn2_2);

        JLabel amount = new JLabel("Amount: ");
        JLabel amount_ = new JLabel("0");
        gridIn2_1.setLayout(new BoxLayout(gridIn2_1, BoxLayout.X_AXIS));
        gridIn2_1.add(amount);
        gridIn2_1.add(amount_);


        JLabel budget = new JLabel("Remaining budget： ");
        JLabel budget_ = new JLabel("0");
        gridIn2_2.setLayout(new BoxLayout(gridIn2_2, BoxLayout.X_AXIS));
        gridIn2_2.add(budget);
        gridIn2_2.add(budget_);


        grid3.setLayout(new GridLayout(1, 2));
        JPanel gridIn3_1 = new JPanel();
        JPanel gridIn3_2 = new JPanel();
        gridIn3_1.setOpaque(true);
        gridIn3_1.setBackground(new Color(249, 247, 225));
        gridIn3_2.setOpaque(true);
        gridIn3_2.setBackground(new Color(249, 247, 225));
        gridIn3_1.setLayout(new BoxLayout(gridIn3_1, BoxLayout.X_AXIS));
        gridIn3_2.setLayout(new BoxLayout(gridIn3_2, BoxLayout.X_AXIS));


        grid3.add(gridIn3_1);
        grid3.add(gridIn3_2);

        String[] year = {"2022", "2023", "2024", "2025"};
        String[] month = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        JLabel year_ = new JLabel("Choose Year:");
        JLabel month_ = new JLabel("Choose Month:");
        JComboBox comboBox1 = new JComboBox(year);
        JComboBox comboBox2 = new JComboBox(month);
        gridIn3_1.add(year_);
        gridIn3_1.add(comboBox1);
        gridIn3_2.add(month_);
        gridIn3_2.add(comboBox2);



        Statement st = UserLogin.getConnection().createStatement();
        String query = "SELECT * from AccountBook1 WHERE InorOut = 0";
        ResultSet rs = st.executeQuery(query);
        int row = 0;
        while(rs.next()) {
            row++;
        }
        st.close();
        System.out.println("row=" + row);

        Statement st1 = UserLogin.getConnection().createStatement();
        String query1 = "SELECT * from AccountBook1 WHERE InorOut = 0";
        ResultSet rs1 = st1.executeQuery(query1);
        ResultSetMetaData rsmd = rs1.getMetaData();
        int column = rsmd.getColumnCount();
        System.out.println("column=" + column);

        String[][] strings = new String[row][column];
        int i = 0;
        while(rs1.next()) {
            System.out.println("ffffffffff");
            for (int j = 1; j <= column; j++) {
                strings[i][j-1] = rs1.getString(j);
                System.out.println(rs1.getString(j));
            }
            i++;
        }
        String[] header = {"Item", "Price", "Category", "Date", "Income/Expenditure"};
        JTable jTable1 = new JTable(strings, header);
        Font myFont1 = new Font("Britannic", Font.BOLD, 14);
        jTable1.getTableHeader().setFont(myFont1);
        jTable1.getTableHeader().setOpaque(false);
        jTable1.getTableHeader().setBackground(new Color(242,237,189));
        jTable1.getTableHeader().setForeground(new Color(125,69,5));
        jTable1.setBounds(10, 200, 400, 300);
        jTable1.setRowHeight(25);
        jTable1.setRowHeight(0, 35);



        JScrollPane scrollPane = new JScrollPane(jTable1);
        scrollPane.setPreferredSize(new Dimension(450, 300));
        downPanel.add(scrollPane);

        this.setLayout(new BorderLayout());
        this.add(upPanel, BorderLayout.NORTH);
        this.add(downPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }
    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}
class MyPanel extends JPanel {
    private JButton jcomp4;
    private JPanel contentPane;
    private JComboBox choiceBox;

    public MyPanel(JPanel panel, MainView mv) {
        choiceBox = mv.getChoiceBox();
        contentPane = panel;
        setOpaque(true);
        setBackground(Color.RED.darker().darker());
        // construct components
        jcomp4 = new JButton("show new panel");
        jcomp4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = (String) choiceBox.getSelectedItem();
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
        });
        add(jcomp4);
    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}

class MyPanel2 extends JPanel {
    private JButton jcomp1;
    private JPanel contentPane;
    private Color backgroundColour;
    private JComboBox choiceBox;
    public MyPanel2 (JPanel panel, Color c, MainView mv){
        contentPane = panel;
        backgroundColour = c;
        choiceBox = mv.getChoiceBox();

        setOpaque(true);
        setBackground(backgroundColour);

        // construct components
        jcomp1 = new JButton("show more panel");
        jcomp1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String changeToPanel = (String) choiceBox.getSelectedItem();
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, changeToPanel);
            }
        });
        add(jcomp1);
    }

    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}