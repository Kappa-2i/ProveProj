package CONTROLLER;

import GUI.SignInViewGUI;

import javax.swing.*;

public class ControllerSignIn {
    // Metodo per avviare la GUI di signIn
    public static void startSignIn() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SignInViewGUI(new ControllerLogin());
            }
        });
    }
}
