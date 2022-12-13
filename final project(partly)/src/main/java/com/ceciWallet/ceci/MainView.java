package com.ceciWallet.ceci;


import com.vaadin.flow.router.Route;

import java.sql.*;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;

import java.util.regex.Pattern;

@Route("")
public class MainView {
    private JPanel contentPane;

    private UserLogin userLoginPanel;
    private registerPanel register;
    private forgotPanel forgotPanel;
    private accountPanel accountPanel;

    void displayGUI() throws IOException, FontFormatException, SQLException, ClassNotFoundException {
        JFrame frame = new JFrame("Ceci's Wallet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());

        /*
        switch panel in one frame, below are the panels
         */
        userLoginPanel = new UserLogin(contentPane, this);
        register = new registerPanel(contentPane, this);
        forgotPanel = new forgotPanel(contentPane, this);
        accountPanel = new accountPanel(contentPane, this);

        contentPane.add(userLoginPanel, "User Login Panel");
        contentPane.add(register,"Register");
        contentPane.add(forgotPanel, "Forgot");
        contentPane.add(accountPanel, "Account Book 1");

        frame.getContentPane().add(contentPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

}
class UserLogin extends JPanel {
    /*
    this class is for user login, will check whether user exists or not, password matches with username, and if all the blanks are filled.
    Also, the connection with sqlite is built in this class.
     */
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
        /*
        check whether user meets all the conditions to login
         */
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
        /*
        get the password according to the username, return name if user doesn't exist
         */
        Statement stmt = con.createStatement();
        String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
        ResultSet rs = stmt.executeQuery(SQL);
        if(rs.next()){
            return rs.getString("password");
        };
        return "";
    }
    public boolean usernameExist(String name) {
        /*
        check whether user exists or not
         */
        try {
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM userInformation WHERE username = '" + name + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            return rs.next();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public UserLogin(JPanel panel, MainView mv) throws IOException, SQLException {
        Font font = null;
        /*
        use beautiful font as title
         */
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
/*
this class is for user register, need to check whether user exists or not
 */
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
            JOptionPane.showMessageDialog(null,"Register Successful!");
            String changeToPanel = "Account Book 1";
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
/*
Below are some efforts that have been abandoned since it's hard to realize
 */

//
//class homePagePanel extends JPanel {
//    private JPanel contentPane;
////    private Connection con = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
//    private JButton back =  new JButton();
//    private JPanel panel1_r = new JPanel();
//    private String chosenAccountBook = "";
//    private int numberAccountBook = 0;
//    private JRadioButton accountBook1 = new JRadioButton();
//    private JRadioButton accountBook2 = new JRadioButton();
//    private JRadioButton accountBook3 = new JRadioButton();
//    private Boolean accountBook1_status = false;
//    private Boolean accountBook2_status = false;
//    private Boolean accountBook3_status = false;

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

//    public JRadioButton getChosenButton(){
//        if (accountBook1.isSelected()) {
//            return accountBook1;
//        } else if (accountBook2.isSelected()) {
//            return accountBook2;
//        } else {
//            return accountBook3;
//        }
//    }
//
//    public String getChosenAccountBook() {
//        return getChosenButton().getText();
//    }
//    public int showConfirmDialog() {
//        int response = JOptionPane.showConfirmDialog(this, "Do you want to delete the chosen account book?", "Delete or not", JOptionPane.YES_NO_OPTION);
//        return response;
//    }
//
//    public void showCantDelete() {
//        JOptionPane.showMessageDialog(this, "No account book can be deleted");
//    }
//    public void setInitialization() throws SQLException {
//        Statement stmt3 = UserLogin.getConnection().createStatement();
//        String sql3 = "SELECT name FROM accountBookName";
//        ResultSet rs3 = stmt3.executeQuery(sql3);
//        String[] name = new String[3];
//        int j = 0;
//        while(rs3.next()) {
//            numberAccountBook++;
//            System.out.println(numberAccountBook);
//            name[j] = rs3.getString("name");
//            j++;
//        }
//        accountBook1.setText(name[0]);
////        setAccountBook1(true);
//        accountBook2.setText(name[1]);
////        setAccountBook2(true);
//        accountBook3.setText(name[2]);
////        setAccountBook3(true);
//    }
////    public void showAddFailure(){
////        JOptionPane.showMessageDialog(this, "Maximal number of account book is 3");
////    }
//    public void showNameNotProper() {
//        JOptionPane.showMessageDialog(this, "Name can only contain letters, please try again");
//    }
//
//    public homePagePanel(JPanel panel, MainView mv) throws SQLException {
//        contentPane = panel;
//        setOpaque(true);
//        setBackground(new Color(249, 247, 225));
//        this.setLayout(null);
//
//        // split the panel
//        this.setBorder(new TitledBorder("Home Page"));
//        this.setLayout(new GridLayout(2, 2));
//
//        setInitialization();
//
//        // upper left
//        JPanel panel1 = new JPanel();
////        panel1.setBorder(new TitledBorder("Content Bar"));
//        panel1.setLayout(new GridLayout(1, 2));
//        panel1.setOpaque(true);
//        panel1.setBackground(new Color(249, 247, 225));
//
//        JPanel panel1_l = new JPanel();
//        panel1_l.setBorder(new TitledBorder("Tool Content"));
//        panel1_l.setBackground(new Color(249, 247, 225));
//        panel1_l.setLayout(null);
////        panel1_l.setLayout(new BoxLayout(panel1_l, BoxLayout.Y_AXIS));
////        panel1_l.setLayout(new BorderLayout());
//        JButton summary_button = new JButton("Summary");
//        JButton accountBook_button = new JButton("Account Book");
//        JButton budget_button = new JButton("Budget");
//        JButton converter_button = new JButton("Converter");
//        JButton authorPage_button = new JButton("Author Page");
//
//        summary_button.setBounds(5, 20, 150, 25);
//        accountBook_button.setBounds(5, 50, 150, 25);
//        budget_button.setBounds(5, 80, 150, 25);
//        converter_button.setBounds(5, 110, 150, 25);
//        authorPage_button.setBounds(5, 140, 150, 25);
//
//        back.setText("Sign Out");
//        Font myFont4 = new Font("Britannic", Font.PLAIN, 13);
//        back.setFont(myFont4);
//        back.setBounds(5, 200, 150, 25);
//
//        back.setOpaque(false);
//        back.setContentAreaFilled(false);
//        back.setBorderPainted(false);
//        back.setBorder(new LineBorder(Color.BLACK, 2));
//        back.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String changeToPanel = "User Login Panel";
//                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
//                cardLayout.show(contentPane, changeToPanel);
//                UserLogin.setUsername_();
//                UserLogin.setPassword_();
//            }
//        });
//
//        panel1_l.add(summary_button);
//        panel1_l.add(accountBook_button);
//        panel1_l.add(budget_button);
//        panel1_l.add(converter_button);
//        panel1_l.add(authorPage_button);
//        panel1_l.add(back);
//
//        //summary panel
//        JPanel summary_Panel = new JPanel();
//        JPanel panelUP_summary = new JPanel();
//        JPanel panelDown_summary = new JPanel();
//
//        ButtonGroup group1_summary = new ButtonGroup();
//        ButtonGroup group2_summary = new ButtonGroup();
//
//        summary_Panel.setOpaque(true);
//        summary_Panel.setBackground(new Color(249, 247, 225));
//        summary_Panel.setLayout(new BorderLayout());
//        summary_Panel.setBorder(new TitledBorder("Summary"));
//
//
//        JRadioButton radioButton1 = new JRadioButton("Expenditure");
//        JRadioButton radioButton2 = new JRadioButton("Income");
//        group1_summary.add(radioButton1);
//        group1_summary.add(radioButton2);
//
//        JRadioButton radioButton3 = new JRadioButton("Account Book 1");
//        JRadioButton radioButton4 = new JRadioButton("Account Book 2");
//        JRadioButton radioButton5 = new JRadioButton("Account Book 3");
//        group2_summary.add(radioButton3);
//        group2_summary.add(radioButton4);
//        group2_summary.add(radioButton5);
//
//        radioButton1.setSelected(true);
//        radioButton3.setSelected(true);
//
//        panelUP_summary.setLayout(new BoxLayout(panelUP_summary, BoxLayout.Y_AXIS));
//        panelUP_summary.add(radioButton3);
//        panelUP_summary.add(radioButton4);
//        panelUP_summary.add(radioButton5);
//        panelUP_summary.setOpaque(true);
//        panelUP_summary.setBackground(new Color(249, 247, 225));
//
//        panelDown_summary.setLayout(new BoxLayout(panelDown_summary, BoxLayout.Y_AXIS));
//        panelDown_summary.add(radioButton1);
//        panelDown_summary.add(radioButton2);
//        panelDown_summary.setOpaque(true);
//        panelDown_summary.setBackground(new Color(249, 247, 225));
//
//        summary_Panel.setLayout(new BorderLayout());
//        summary_Panel.add(panelUP_summary, BorderLayout.NORTH);
//        summary_Panel.add(panelDown_summary, BorderLayout.CENTER);
//
//        //
//
//        panel1.add(panel1_l);
//        panel1_r = summary_Panel;
//        panel1.add(panel1_r);
//
//        add(panel1);
//
//        //account panel
//        JPanel account_Panel = new JPanel();
//        JPanel panelUP_account = new JPanel();
//        JPanel panelDown_account = new JPanel();
//
//        ButtonGroup group1_account = new ButtonGroup();
//        ButtonGroup group2_account = new ButtonGroup();
//
//        account_Panel.setOpaque(true);
//        account_Panel.setBackground(new Color(249, 247, 225));
//        account_Panel.setLayout(new BorderLayout());
//        account_Panel.setBorder(new TitledBorder("Account Book"));
//        panelUP_account.setLayout(new BoxLayout(panelUP_account, BoxLayout.Y_AXIS));
//        panelDown_account.setLayout(new BoxLayout(panelDown_account, BoxLayout.Y_AXIS));
//
////        JLabel tip = new JLabel();
////        if (numberAccountBook == 0) {
////            tip.setBounds(170, 20, 100, 50);
////            tip.setText("Create a new one!");
////            panelUP_account.add(tip);
////        }
//        JRadioButton radioButton6 = new JRadioButton("Account Book 1");
//        JRadioButton radioButton7 = new JRadioButton("Account Book 2");
//        JRadioButton radioButton8 = new JRadioButton("Account Book 3");
//        group1_account.add(radioButton6);
//        group1_account.add(radioButton7);
//        group1_account.add(radioButton8);
//
////        JButton addAccountBook = new JButton("Add");
////        JButton deleteAccountBook = new JButton("Delete");
////        JButton renameAccountBook = new JButton("Rename");
//        JButton gotoDetailedPage = new JButton("Go to Check");
//
//        group2_account.add(gotoDetailedPage);
////        group2_account.add(addAccountBook);
////        group2_account.add(deleteAccountBook);
////        group2_account.add(renameAccountBook);
//
//
//        panelUP_account.setOpaque(true);
//        panelUP_account.setBackground(new Color(249, 247, 225));
//
//        panelDown_account.add(gotoDetailedPage);
//
//        panelUP_account.add(radioButton6);
//        panelUP_account.add(radioButton7);
//        panelUP_account.add(radioButton8);
//
//        panelDown_account.setOpaque(true);
//        panelDown_account.setBackground(new Color(249, 247, 225));
//
//        account_Panel.setLayout(new BorderLayout());
//        account_Panel.add(panelUP_account, BorderLayout.NORTH);
//        account_Panel.add(panelDown_account, BorderLayout.CENTER);
//
//
//
//        summary_button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panel1_r.removeAll();
//                panel1_r.revalidate();
//                panel1_r.repaint();
//                panel1_r.setLayout(new BorderLayout());
//                panel1_r.add(panelUP_summary, BorderLayout.NORTH);
//                panel1_r.add(panelDown_summary, BorderLayout.CENTER);
//                panel1_r.setBorder(new TitledBorder("Summary"));
//
//            }
//        });
//
//        accountBook_button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ButtonGroup group1_account = new ButtonGroup();
//                panel1_r.removeAll();
//                panel1_r.revalidate();
//                panel1_r.repaint();
//                panel1_r.setLayout(new BorderLayout());
////                if (getAccountBook1()) {
////                    group1_account.add(accountBook1);
////                    panelUP_account.add(accountBook1);
////                }
////                if (getAccountBook2()) {
////                    group1_account.add(accountBook2);
////                    panelUP_account.add(accountBook2);
////                }
////                if (getAccountBook3()) {
////                    group1_account.add(accountBook3);
////                    panelUP_account.add(accountBook3);
////                }
//                panel1_r.add(panelUP_account, BorderLayout.NORTH);
//                panel1_r.add(panelDown_account, BorderLayout.CENTER);
//                panel1_r.setBorder(new TitledBorder("Account Book"));
//
//            }
//        });
//
//        budget_button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panel1_r.removeAll();
//                panel1_r.revalidate();
//                panel1_r.repaint();
//                panel1_r.setBorder(new TitledBorder("Budget Settings"));
//
//            }
//        });
//
//        converter_button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panel1_r.removeAll();
//                panel1_r.revalidate();
//                panel1_r.repaint();
//                panel1_r.setBorder(new TitledBorder("Currency Converter"));
//
//            }
//        });
//
//        authorPage_button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panel1_r.removeAll();
//                panel1_r.revalidate();
//                panel1_r.repaint();
//                panel1_r.setBorder(new TitledBorder("Author Information"));
//
//            }
//        });
//
//
////        addAccountBook.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                if (numberAccountBook < 3) {
////                    String name = JOptionPane.showInputDialog("Input your new account book name:");
////
////                    if (name == null ||!Pattern.matches("[a-zA-Z]+",name)) {
////                        showNameNotProper();
////                    }else{
////                        try {
////                            Statement stmt4 = UserLogin.getConnection().createStatement();
////                            String sql4 = "CREATE TABLE " + name + " (item VARCHAR(255) NOT NULL, price DECIMAL(2), thedate DATE, inOrOUT int, category VARCHAR(255))";
////                            stmt4.executeQuery(sql4);
////                            stmt4.close();
////                            Statement stmt5 = UserLogin.getConnection().createStatement();
////                            String sql5 = "INSERT INTO accountBookName (name)" + " VALUES (" + "'" + name + "'" + ")";
////                            stmt5.executeQuery(sql5);
////                            stmt5.close();
////                            if (!getAccountBook1()) {
////                                accountBook1.setText(name);
////                                setAccountBook1(true);
////                            } else if (getAccountBook1() && !getAccountBook2()) {
////                                accountBook2.setText(name);
////                                setAccountBook2(true);
////                            } else {
////                                accountBook3.setText(name);
////                                setAccountBook3(true);
////                            }
////                        } catch (SQLException ef) {
////                            ef.printStackTrace();
////                            System.out.println("fail to connect to database");
////                        }
////
////                    }
////
////
////                    numberAccountBook++;
////                } else {
////                    showAddFailure();
////                }
////            }
////        });
//
////        deleteAccountBook.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                if (!Objects.equals(getChosenAccountBook(), "")) {
////
////                    int response = showConfirmDialog();
////                    if (response == JOptionPane.YES_OPTION) {
////                        try {
//////                            dropTable();
////                            Statement stmt = UserLogin.getConnection().createStatement();
////                            System.out.println(getChosenAccountBook());
////                            String sql = "DROP TABLE " + getChosenAccountBook();
////                            stmt.executeUpdate(sql);
////                            stmt.close();
////                            Statement stmt2 = UserLogin.getConnection().createStatement();
////                            String sql2 = "DELETE FROM accountBookName WHERE name =" + getChosenAccountBook();
////                            stmt2.executeQuery(sql2);
////                            stmt2.close();
////                            panelUP_account.remove(getChosenButton());
////                            setAccountBook(getChosenButton(), false);
////                        } catch (SQLException ef) {
////                            ef.printStackTrace();
////                            System.out.println("fail to connect to database");
////                        }
////                        numberAccountBook--;
////                        if (numberAccountBook == 0) {
////                            chosenAccountBook = "";
////                        }
////                    }
////                } else {
////                    showCantDelete();
////                }
////
////            }
////        });
//
//
//        // upper right
//        JTabbedPane tabbedPane = new JTabbedPane();
//        JPanel tabPanel1 = new JPanel();
//        tabPanel1.setLayout(new BorderLayout());
//
//        JPanel tabPanelCheckboxPanel = new JPanel();
//        tabPanelCheckboxPanel.setLayout(new BoxLayout(tabPanelCheckboxPanel, BoxLayout.Y_AXIS));
//        tabPanelCheckboxPanel.add(new JCheckBox("UncheckedCheckBox"));
//        JCheckBox cb = new JCheckBox("CheckedCheckBox");
//        cb.setSelected(true);
//        tabPanelCheckboxPanel.add(cb);
//        JCheckBox cb2 = new JCheckBox("InactiveCheckBox");
//        cb2.setEnabled(false);
//        tabPanelCheckboxPanel.add(cb2);
//        tabPanel1.add(tabPanelCheckboxPanel, BorderLayout.CENTER);
////        tabPanel1.add(new JSlider(0, 100, 70), BorderLayout.SOUTH);
//
//        tabbedPane.addTab("SelectedTab", tabPanel1);
//        tabbedPane.addTab("OtherTab", new JPanel());
//        add(tabbedPane);
//
//        // bottom left
//        JPanel panel3 = new JPanel();
//        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
//        panel3.add(new JTextField("TextField"));
//        panel3.add(new JPasswordField("PasswordField"));
//        JComboBox<String> combo = new JComboBox<String>(new String[] { "Item1", "Item2", "Item3"});
//        panel3.add(combo);
//
//        // bottom right
//        JPanel panel4 = new JPanel();
//        panel4.setLayout(new BorderLayout());
//        panel4.add(panel3, BorderLayout.NORTH);
//        add(panel4);
//
//        add(new JTextArea("TextArea"));
//
//
//    }
//
//    @Override
//    public Dimension getPreferredSize() {
//        return (new Dimension(650, 510));
//    }
//}
//

class accountPanel extends JPanel {
    /*
    this class is for the account book
     */
    private JPanel contentPane;
    private JTextField itemName_ = new JTextField();
    private JTextField itemPrice_ = new JTextField();
    private JTextField itemDate_ = new JTextField();
    private ButtonGroup group;
    private ButtonGroup group1;
    private Double budgetMoney = 0.00;
    private JTable jTable1 = new JTable();
    private DefaultTableModel tModel;
    Double remainingBudget = 0.00;
    /*
    check if the date matches the pattern YYYY-MM-DD
     */
    private Pattern date_pattern = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
    public boolean ifMatches(String date) {
        return date_pattern.matcher(date).matches();
    }

    public JTable createTable(String year, String month, String InOrOt) throws SQLException {
        /*
        create the corresponding table given many conditions chosen by the user in the panel
         */
        Statement st = UserLogin.getConnection().createStatement();
        String query;
        if (InOrOt.equals("Expenses")) {
            if (year.equals("All") && month.equals("All") && month.equals("All")) {
                query = "SELECT * from AccountBook1 WHERE InOrOut = 0";
            } else if (year.equals("All") && !month.equals("All")) {
                query = "SELECT * from AccountBook1 WHERE Month = " + Integer.parseInt(month) + " AND InOrOut = 0";
            } else if (!year.equals("All") && month.equals("All")) {
                query = "SELECT * from AccountBook1 WHERE Year = " + Integer.parseInt(year) + " AND InOrOut = 0";
            } else {
                query = "SELECT * from AccountBook1 WHERE Year = " + Integer.parseInt(year) + " AND Month = " + Integer.parseInt(month) + " AND InOrOut = 0";
            }
        } else {
            if (year.equals("All") && month.equals("All")) {
                query = "SELECT * from AccountBook1 WHERE InOrOut = 1";
            } else if (year.equals("All") && !month.equals("All")) {
                query = "SELECT * from AccountBook1 WHERE Month = " + Integer.parseInt(month) + " AND InOrOut = 1";
            } else if (!year.equals("All") && month.equals("All")) {
                query = "SELECT * from AccountBook1 WHERE Year = " + Integer.parseInt(year) + " AND InOrOut = 1";
            } else {
                query = "SELECT * from AccountBook1 WHERE Year = " + Integer.parseInt(year) + " AND Month = " + Integer.parseInt(month) + " AND InOrOut = 1";
            }        }
        ResultSet rs = st.executeQuery(query);
        int row = 0;
        while(rs.next()) { // count the number of rows first to build a string[] []
            row++;
        }
        st.close();

        Statement st1 = UserLogin.getConnection().createStatement();
        ResultSet rs1 = st1.executeQuery(query);
        ResultSetMetaData rsmd = rs1.getMetaData();
        int column = rsmd.getColumnCount();

        String[][] strings = new String[row][column];
        int i = 0;
        while(rs1.next()) {
            for (int j = 1; j <= column; j++) {
                strings[i][j-1] = rs1.getString(j);
            }
            i++;
        }
        String[] header = {"Item", "Price", "Category", "Year", "Month", "Day"};
        tModel = (DefaultTableModel)jTable1.getModel();
        tModel.setDataVector(strings,header);
//        jTable1 = new JTable(strings, header);
        jTable1 = new JTable(tModel);
        Font myFont1 = new Font("Britannic", Font.BOLD, 14);
        jTable1.getTableHeader().setFont(myFont1);
        jTable1.getTableHeader().setOpaque(false);
        jTable1.getTableHeader().setBackground(new Color(242,237,189));
        jTable1.getTableHeader().setForeground(new Color(171, 139, 189));
        jTable1.setBounds(10, 200, 400, 300);
        jTable1.setRowHeight(25);

        return jTable1;
    }


    public accountPanel(JPanel panel, MainView mv) throws SQLException { //  account book 1
        /*
        I used many subPanel and GridLayout and BoxLayout and Border Layout to get a better organization
         */
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
        downPanel.setPreferredSize(new Dimension(450, 300));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(new Color(249, 247, 225));
        bottomPanel.setBorder(new TitledBorder("Control Center"));


        GridLayout grid = new GridLayout(3, 0);
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
        JLabel name_ = new JLabel();
        Statement stmt = UserLogin.getConnection().createStatement();
        String SQL = "SELECT * FROM accountBookName";
        ResultSet rs = stmt.executeQuery(SQL);
        if(rs.next()){ // check if user renamed the account book, if not, name it as Your Account Book Name
            name_.setText(rs.getString("BookName"));
            stmt.close();
        } else {
            name_.setText("Your Account Book Name");
            stmt = UserLogin.getConnection().createStatement();
            String SQL2 = "INSERT INTO accountBookName (BookName) VALUES (" + "'Your Account Book Name')";
            stmt.executeUpdate(SQL2);
            stmt.close();
        }


        name_.setForeground(new Color(171,139,189));

        grid1.setLayout(new BoxLayout(grid1, BoxLayout.X_AXIS));
        grid1.add(name_);
        name_.setFont(myFont2);

        grid2.setLayout(new GridLayout(1,3));
        JPanel gridIn2_1 = new JPanel();
        JPanel gridIn2_2 = new JPanel();
        JPanel gridIn2_3 = new JPanel();

        gridIn2_1.setOpaque(true);
        gridIn2_1.setBackground(new Color(249, 247, 225));
        gridIn2_2.setOpaque(true);
        gridIn2_2.setBackground(new Color(249, 247, 225));
        gridIn2_3.setOpaque(true);
        gridIn2_3.setBackground(new Color(249, 247, 225));

        grid2.add(gridIn2_1);
        grid2.add(gridIn2_2);
        grid2.add(gridIn2_3);

        JLabel amount = new JLabel("Amount: $");
        JLabel amount_ = new JLabel("0");
        gridIn2_1.setLayout(new BoxLayout(gridIn2_1, BoxLayout.X_AXIS));
        gridIn2_1.add(amount);
        gridIn2_1.add(amount_);



        JLabel budget = new JLabel("Remaining Budget： $");
        JLabel budget_ = new JLabel("0");
        gridIn2_2.setLayout(new BoxLayout(gridIn2_2, BoxLayout.X_AXIS));
        gridIn2_2.add(budget);
        gridIn2_2.add(budget_);

        JLabel budgetShould = new JLabel("Monthly Budget: $");
        JLabel budgetShould_ = new JLabel();
        budgetShould_.setText(Double.toString(budgetMoney));
        gridIn2_3.setLayout(new BoxLayout(gridIn2_3, BoxLayout.X_AXIS));
        gridIn2_3.add(budgetShould);
        gridIn2_3.add(budgetShould_);

        grid3.setLayout(new GridLayout(1, 3));
        JPanel gridIn3_1 = new JPanel();
        JPanel gridIn3_2 = new JPanel();
        JPanel gridIn3_3 = new JPanel();
        gridIn3_1.setOpaque(true);
        gridIn3_1.setBackground(new Color(249, 247, 225));
        gridIn3_2.setOpaque(true);
        gridIn3_2.setBackground(new Color(249, 247, 225));
        gridIn3_3.setOpaque(true);
        gridIn3_3.setBackground(new Color(249, 247, 225));
        gridIn3_1.setLayout(new BoxLayout(gridIn3_1, BoxLayout.X_AXIS));
        gridIn3_2.setLayout(new BoxLayout(gridIn3_2, BoxLayout.X_AXIS));
        gridIn3_3.setLayout(new BoxLayout(gridIn3_3, BoxLayout.X_AXIS));



        grid3.add(gridIn3_1);
        grid3.add(gridIn3_2);
        grid3.add(gridIn3_3);

        String[] year = {"All", "2022", "2023", "2024", "2025"};
        String[] month = {"All", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String[] inOrOut = {"Expenses", "Income"};
        JLabel year_ = new JLabel("Choose Year: ");
        JLabel month_ = new JLabel("Choose Month: ");
        JLabel inOrOut_ = new JLabel("Exp/Inc: ");
        JComboBox comboBox1 = new JComboBox(year);
        JComboBox comboBox2 = new JComboBox(month);
        JComboBox comboBox3 = new JComboBox(inOrOut);

        gridIn3_2.add(year_);
        gridIn3_2.add(comboBox1);
        gridIn3_3.add(month_);
        gridIn3_3.add(comboBox2);
        gridIn3_1.add(inOrOut_);
        gridIn3_1.add(comboBox3);
        jTable1 = createTable((String) comboBox1.getSelectedItem(),(String) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(jTable1);
        scrollPane.setPreferredSize(new Dimension(450, 200));
        downPanel.add(scrollPane);

        String sql4 = null;
        if (comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
            sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
        } else if (comboBox1.getSelectedItem().equals("All") && !comboBox2.getSelectedItem().equals("All")) {
            sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
        } else if (!comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
            sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
        } else {
            sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
        }
        Statement stmt4 = UserLogin.getConnection().createStatement();
        ResultSet rs4 = stmt4.executeQuery(sql4);
        if (rs4.next()) {
            String sum = rs4.getString("sumPrice"); // calculate the sum of bills
            amount_.setText(sum);
        }
        stmt4.close();

        Statement stmt5 = UserLogin.getConnection().createStatement();
        String sql5 = "Select * FROM Budget";
        ResultSet rs5 = stmt5.executeQuery(sql5);

        if (rs5.next()) {
            budgetMoney = Double.parseDouble(rs5.getString("monthly"));
            budgetShould_.setText(Double.toString(budgetMoney)); //refresh the monthly budget
        }

        remainingBudget = budgetMoney - Double.parseDouble(amount_.getText());
        budget_.setText(Double.toString(remainingBudget));
        stmt5.close();

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    jTable1 = createTable((String) comboBox1.getSelectedItem(), (String) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                jTable1.repaint(); // to refreash the table
//                scrollPane.removeAll();
//                scrollPane.revalidate();
//                scrollPane.repaint();
//                scrollPane.getViewport().add(jTable1);
//                scrollPane.setPreferredSize(new Dimension(450, 200));
                String sql4 = null;
                if (comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (comboBox1.getSelectedItem().equals("All") && !comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (!comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                }
                Statement stmt4 = null;
                try {
                    stmt4 = UserLogin.getConnection().createStatement();
                    ResultSet rs4 = stmt4.executeQuery(sql4);
                    if (rs4.next()) {
                        String sum = rs4.getString("sumPrice");
                        amount_.setText(sum);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                remainingBudget = budgetMoney - Double.parseDouble(amount_.getText());
                budget_.setText(Double.toString(remainingBudget)); // to refresh the amount money and remaining budget
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jTable1 = createTable((String) comboBox1.getSelectedItem(), (String) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String sql4 = null;
                if (comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (comboBox1.getSelectedItem().equals("All") && !comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (!comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                }
                Statement stmt4 = null;
                try {
                    stmt4 = UserLogin.getConnection().createStatement();
                    ResultSet rs4 = stmt4.executeQuery(sql4);
                    if (rs4.next()) {
                        String sum = rs4.getString("sumPrice");
                        amount_.setText(sum);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                remainingBudget = budgetMoney - Double.parseDouble(amount_.getText());
                budget_.setText(Double.toString(remainingBudget));
            }
        });

        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jTable1 = createTable((String) comboBox1.getSelectedItem(), (String) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                String sql4 = null;
                if (comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (comboBox1.getSelectedItem().equals("All") && !comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (!comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                }
                Statement stmt4 = null;
                try {
                    stmt4 = UserLogin.getConnection().createStatement();
                    ResultSet rs4 = stmt4.executeQuery(sql4);
                    if (rs4.next()) {
                        String sum = rs4.getString("sumPrice");
                        amount_.setText(sum);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                remainingBudget = budgetMoney - Double.parseDouble(amount_.getText());
                budget_.setText(Double.toString(remainingBudget));
            }
        });

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        JPanel rename_panel = new JPanel();
        rename_panel.setOpaque(true);
        rename_panel.setBackground(new Color(249, 247, 225));
        rename_panel.setLayout(new BoxLayout(rename_panel, BoxLayout.X_AXIS));

        JButton rename = new JButton("Rename account book");
        rename_panel.add(rename);
        JButton setBudget_button = new JButton("Set Budget");
        rename_panel.add(setBudget_button);
        JButton add_button = new JButton("Add Item");
        rename_panel.add(add_button);
        JButton clear_button = new JButton("Clear");
        rename_panel.add(clear_button);
        JButton delete_button = new JButton("Delete Item");
        rename_panel.add(delete_button);
        bottomPanel.add(rename_panel);

        setBudget_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String money = JOptionPane.showInputDialog(null, "Your monthly budget:");
                try {
                    Statement stmt6 = UserLogin.getConnection().createStatement();
                    String SQL6 = "DELETE FROM (Budget)";
                    stmt6.executeUpdate(SQL6);
                    stmt6.close();

                    Statement stmt = UserLogin.getConnection().createStatement();
                    String SQL2 = "INSERT INTO Budget (monthly) VALUES (" + "'" + money + "'" + ")";
                    stmt.executeUpdate(SQL2);
                    stmt.close();
                    JOptionPane.showMessageDialog(null, "Successfully set!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Set failure!");
                    throw new RuntimeException(ex);
                }
                budgetMoney = Double.parseDouble(money);
                remainingBudget = budgetMoney - Double.parseDouble(amount_.getText());
                budget_.setText(Double.toString(remainingBudget));
                budgetShould_.setText(Double.toString(budgetMoney));
            }
        });

        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "Input your new Account Book name, don't use any symbols:");

                Statement stmt = null;
                try {
                    stmt = UserLogin.getConnection().createStatement();
                    String SQL1 = "DELETE FROM accountBookName";
                    stmt.executeUpdate(SQL1);
                    stmt.close();
                    stmt = UserLogin.getConnection().createStatement();
                    String SQL2 = "INSERT INTO accountBookName (BookName) VALUES (" + "'" + name + "'" + ")";
                    stmt.executeUpdate(SQL2);
                    stmt.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                name_.setText(name);
            }
        });



        GridLayout grid_add = new GridLayout(1, 3);
        JPanel addPanel = new JPanel();
        addPanel.setOpaque(true);
        addPanel.setBackground(new Color(249, 247, 225));
        addPanel.setLayout(grid_add);

        JPanel addPanel_1 = new JPanel();
        addPanel_1.setBackground(new Color(249, 247, 225));
        addPanel_1.setOpaque(true);
        JPanel addPanel_2 = new JPanel();
        addPanel_2.setBackground(new Color(249, 247, 225));
        addPanel_2.setOpaque(true);
        JPanel addPanel_3 = new JPanel();
        addPanel_3.setBackground(new Color(249, 247, 225));
        addPanel_3.setOpaque(true);

        addPanel.add(addPanel_1);
        addPanel.add(addPanel_2);
        addPanel.add(addPanel_3);

        JLabel itemName = new JLabel("Name: ");
        JLabel itemPrice = new JLabel("Price: ");
        JLabel itemDate = new JLabel("Date: ");
        itemName_ = new JTextField();
        itemName_.setPreferredSize(new Dimension(150, 25));
        itemPrice_ = new JTextField();
        itemPrice_.setPreferredSize(new Dimension(150, 25));
        itemDate_ = new JTextField();
        itemDate_.setPreferredSize(new Dimension(150, 25));

        addPanel_1.add(itemName);
        addPanel_1.add(itemName_);
        addPanel_2.add(itemPrice);
        addPanel_2.add(itemPrice_);
        addPanel_3.add(itemDate);
        addPanel_3.add(itemDate_);

        bottomPanel.add(addPanel);
        JPanel notePanel = new JPanel();
        notePanel.setBackground(new Color(249, 247, 225));
        notePanel.setOpaque(true);

        JLabel note = new JLabel("NOTE: Please enter DATE as pattern: YYYY-MM-DD, for example, 2022-03-10");
        note.setAlignmentX(Component.LEFT_ALIGNMENT);
        notePanel.add(note);
        bottomPanel.add(notePanel);


        JPanel chooseInOrOur = new JPanel();
        chooseInOrOur.setBackground(new Color(249, 247, 225));
        chooseInOrOur.setOpaque(true);
        chooseInOrOur.setLayout(new BoxLayout(chooseInOrOur, BoxLayout.X_AXIS));
        Font myFont1 = new Font("Britannic", Font.BOLD, 13);
        JLabel inOrOur_label = new JLabel("Chooses Expenses or Income: ");
        inOrOur_label.setFont(myFont1);

        group1 = new ButtonGroup();
        JRadioButton expenses_button = new JRadioButton("Expenses");
        JRadioButton income_button = new JRadioButton("Income");
        group1.add(expenses_button);
        group1.add(income_button);

        chooseInOrOur.add(inOrOur_label);
        chooseInOrOur.add(expenses_button);
        chooseInOrOur.add(income_button);

        bottomPanel.add(chooseInOrOur);

        JPanel chooseCategory1 = new JPanel();
        group = new ButtonGroup();
        chooseCategory1.setLayout(new BoxLayout(chooseCategory1, BoxLayout.X_AXIS));
        chooseCategory1.setBackground(new Color(249, 247, 225));
        chooseCategory1.setOpaque(true);
        JRadioButton clothes_button = new JRadioButton("Clothes");
        JRadioButton travel_button = new JRadioButton("Travel");
        JRadioButton entertainment_button = new JRadioButton("Entertainment");
        JRadioButton groceries_button = new JRadioButton("Groceries");
        JRadioButton snacks_button = new JRadioButton("Snacks");
        JRadioButton fuel_button = new JRadioButton("Fuel");
        JRadioButton insurance_button = new JRadioButton("Insurance");
        JRadioButton restaurantCafe_button = new JRadioButton("Restaurant & Cafes");
        JRadioButton utilities_button = new JRadioButton("Utilities Bills");
        JRadioButton healthy_button = new JRadioButton("Healthy");
        JRadioButton sports_button = new JRadioButton("Sports");
        JRadioButton academics_button = new JRadioButton("Academics");
        JRadioButton others = new JRadioButton("Others");
        JRadioButton pet_button = new JRadioButton("Pet");
        JRadioButton life_button = new JRadioButton("Life");


        JLabel chooseCategory_label = new JLabel("Choose Category: ");
        chooseCategory_label.setFont(myFont1);
        chooseCategory1.add(chooseCategory_label);
        chooseCategory1.add(groceries_button);
        chooseCategory1.add(snacks_button);
        chooseCategory1.add(restaurantCafe_button);
        chooseCategory1.add(utilities_button);


        JPanel chooseCategory2 = new JPanel();

        chooseCategory2.setLayout(new BoxLayout(chooseCategory2, BoxLayout.X_AXIS));
        chooseCategory2.setBackground(new Color(249, 247, 225));
        chooseCategory2.setOpaque(true);

        chooseCategory2.add(fuel_button);
        chooseCategory2.add(entertainment_button);
        chooseCategory2.add(travel_button);
        chooseCategory2.add(clothes_button);
        chooseCategory2.add(insurance_button);
        chooseCategory2.add(healthy_button);

        JPanel chooseCategory3 = new JPanel();

        chooseCategory3.setLayout(new BoxLayout(chooseCategory3, BoxLayout.X_AXIS));
        chooseCategory3.setBackground(new Color(249, 247, 225));
        chooseCategory3.setOpaque(true);


        chooseCategory3.add(pet_button);
        chooseCategory3.add(life_button);
        chooseCategory3.add(academics_button);
        chooseCategory3.add(sports_button);
        chooseCategory3.add(others);

        group.add(groceries_button);
        group.add(snacks_button);
        group.add(restaurantCafe_button);
        group.add(utilities_button);
        group.add(fuel_button);
        group.add(entertainment_button);
        group.add(travel_button);
        group.add(clothes_button);
        group.add(insurance_button);
        group.add(healthy_button);
        group.add(academics_button);
        group.add(sports_button);
        group.add(others);
        group.add(pet_button);
        group.add(life_button);

        bottomPanel.add(chooseCategory1);
        bottomPanel.add(chooseCategory2);
        bottomPanel.add(chooseCategory3);

        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ifAddMeet()) {
                    try {
                        Statement stmt = UserLogin.getConnection().createStatement();
                        String name = itemName_.getText();
                        String price = itemPrice_.getText();
                        String date = itemDate_.getText();
                        String year = date.substring(0, 4);
                        String month = date.substring(5, 7);
                        String day = date.substring(8,10);
                        String category = getSelectedButtonText(group);
                        String inOut = getSelectedButtonText(group1);
                        String SQL1 = null;
                        if (inOut.equals("Expenses")) {
                            SQL1 = "INSERT INTO AccountBook1 (ItemName, Price, Category, Year, Month, Day, InOrOut)" + " VALUES (" + "'" + name + "', '" + price + "', '" + category + "', " + year + ", " + month + ", " + day + ", " + "0" + ")";
                        } else {
                            SQL1 = "INSERT INTO AccountBook1 (ItemName, Price, Category, Year, Month, Day, InOrOut)" + " VALUES (" + "'" + name + "', '" + price + "', '" + category + "', " + year + ", " + month + ", " + day + ", " + "1" + ")";
                        }
                        stmt.executeUpdate(SQL1);
                        stmt.close();
                        jTable1.revalidate();
                        JOptionPane.showMessageDialog(null, "Add successfully!");
                        itemName_.setText("");
                        itemPrice_.setText("");
                        itemDate_.setText("");
//                        jTable1 = createTable((String) comboBox1.getSelectedItem(), (String) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Errors appear. Please don't use symbol in item name and check others' pattern");
//                    throw new RuntimeException(ex);
                    }
                }

                String sql4 = null;
                if (comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (comboBox1.getSelectedItem().equals("All") && !comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else if (!comboBox1.getSelectedItem().equals("All") && comboBox2.getSelectedItem().equals("All")) {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                } else {
                    sql4 = "Select Sum(Price) as sumPrice from AccountBook1 WHERE Year = " + comboBox1.getSelectedItem() + " AND Month = " + comboBox2.getSelectedItem() + " AND InOrOut = " + (comboBox3.getSelectedItem().equals("Expenses")? 0 : 1);
                }

                try {
                    Statement stmt4 = UserLogin.getConnection().createStatement();
                    ResultSet rs4 = stmt4.executeQuery(sql4);
                    if (rs4.next()) {
                        String sum = rs4.getString("sumPrice");
                        amount_.setText(sum);
                    }
                    stmt4.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                remainingBudget = budgetMoney - Double.parseDouble(amount_.getText());
                budget_.setText(Double.toString(remainingBudget));
                try {
                    jTable1 = createTable((String) comboBox1.getSelectedItem(), (String) comboBox2.getSelectedItem(), (String) comboBox3.getSelectedItem());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                jTable1.repaint();
            }
        });

        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  int row = jTable1.getSelectedRow();
                  if (row == -1) {
                      JOptionPane.showMessageDialog(null, "Select a row first, please");
                  } else {
                      DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                      int modelRow = jTable1.convertRowIndexToModel(row);
                      String _item = (String) jTable1.getModel().getValueAt(row, 0);
                      String _price = (String) jTable1.getModel().getValueAt(row, 1);
                      String _category = (String) jTable1.getModel().getValueAt(row, 2);
                      String _year = (String) jTable1.getModel().getValueAt(row, 3);
                      String _month = (String) jTable1.getModel().getValueAt(row, 4);
                      String _day = (String) jTable1.getModel().getValueAt(row, 5);
                      int _inOrOut =  comboBox3.getSelectedItem().equals("Expenses")? 0 : 1;
                      try {
                          Statement stmt = UserLogin.getConnection().createStatement();
                          String SQL = "DELETE FROM AccountBook1 WHERE ItemName = '" + _item + "' AND Price = "
                                  + _price + " AND Category = '" + _category + "' AND Year = " + _year + " AND Month = " + _month
                                  + " AND Day = " + _day + " AND InOrOut = " + _inOrOut;

                          stmt.executeUpdate(SQL);
                          JOptionPane.showMessageDialog(null, "Successfully delete!");

                          stmt.close();

                      } catch (SQLException ex) {
                          JOptionPane.showMessageDialog(null, "Failed to delete, please check again");
                          throw new RuntimeException();

                      }
                      model.removeRow(modelRow);
                      jTable1.revalidate();
                  }
            }
        });

        clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemName_.setText("");
                itemPrice_.setText("");
                itemDate_.setText("");
            }
        });

        add(bottomPanel);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(upPanel);
        this.add(scrollPane);
        this.add(bottomPanel);

    }
    public boolean ifAddMeet(){
        if (itemName_.getText().isEmpty() || itemPrice_.getText().isEmpty() || itemDate_.getText().isEmpty() || getSelectedButtonText(group).equals("") || getSelectedButtonText(group1).equals("")) {
            JOptionPane.showMessageDialog(null, "Please complete all options");
            return false;
        }
        if ( !ifMatches(itemDate_.getText())) {
            JOptionPane.showMessageDialog(null, "Date entered doesn't meet the requirement");
            return false;
        }
        return true;
    }
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return "";
    }
    @Override
    public Dimension getPreferredSize() {
        return (new Dimension(650, 510));
    }
}