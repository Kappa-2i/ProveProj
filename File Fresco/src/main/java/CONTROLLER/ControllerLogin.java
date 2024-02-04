package CONTROLLER;

import GUI.LoginView;
import javax.swing.*;

// Classe del controller
public class ControllerLogin {
    // Metodo per avviare la GUI di login
    public static void startLogin() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView();
            }
        });
    }
}

