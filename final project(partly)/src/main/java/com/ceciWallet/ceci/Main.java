package com.ceciWallet.ceci;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    public static void main(String... args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainView().displayGUI();
            } catch (IOException | FontFormatException | ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
