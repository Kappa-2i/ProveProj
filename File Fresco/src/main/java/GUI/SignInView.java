package GUI;

import CONTROLLER.ControllerLogin;

import javax.swing.*;
import java.awt.*;

public class SignInView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;

    public SignInView(ControllerLogin controller) {
        // Impostazioni della finestra principale
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        // Etichette e campi di testo
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        // Bottone di SignIn
        JButton signInButton = new JButton("Sign In");

        // Bottone "Cancel"
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));

        // Aggiungi componenti al pannello principale
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(signInButton);
        mainPanel.add(cancelButton);

        // Aggiungi il pannello principale alla finestra
        add(mainPanel);

        // Rendi la finestra visibile
        setVisible(true);
    }

}

