package CONTROLLER;

import GUI.LoginView;
import GUI.SignInView;

import javax.swing.*;

public class ControllerSignIn {
    // Metodo per avviare la GUI di signIn
    public static void startSignIn() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignInView();
            }
        });
    }
}
