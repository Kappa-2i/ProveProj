package GUI;

import CONTROLLER.ControllerLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

// Classe della GUI di login
public class LoginView extends JFrame {

    private ControllerLogin controller;
    private JTextField emailTextField;
    private JTextField passwordField;


    public LoginView(ControllerLogin controllerLogin) {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        this.controller = controllerLogin;
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(20);

        JButton loginButton = new JButton("Avanti");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                // Otteniamo i valori dalla GUI
//                String email = emailTextField.getText();
//                String password = passwordField.getText();
//
//                // Creiamo un account con i valori inseriti
//                Account account = new Account(email, password);
//
//                // Verifichiamo l'esistenza dell'account nel database
//                if (controller.checkCredentials(account)) {
//                    JOptionPane.showMessageDialog(LoginView.this, "Accesso consentito!");
//                    setVisible(false);
//                    ControllerSignIn.startSignIn();
//
//                } else {
//                    JOptionPane.showMessageDialog(LoginView.this, "Credenziali errate. Riprova.");
//                }

                //Chiamiamo la funzione checkCredentials dal controller passandogli i dati inseriti
                try {
                    controller.checkCredentials(emailTextField.getText(), passwordField.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        getContentPane().add(panel);

        setSize(300, 150);
        setVisible(true);
    }
}
