package GUI;

import CONTROLLER.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInViewGUI extends JFrame {


    private Controller controller;

    public SignInViewGUI(Controller controller){


        // Impostazioni della finestra principale
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel nomeLabel = new JLabel("Nome:");
        JTextField nomeField = new JTextField();

        JLabel cognomeLabel = new JLabel("Cognome:");
        JTextField cognomeField = new JTextField();

        JLabel dataLabel = new JLabel("Data di nascita:");
        JTextField dataField = new JTextField();

        JLabel telefonoLabel = new JLabel("Telefono:");
        JTextField telefonoField = new JTextField();

        JLabel cittaLabel = new JLabel("Città:");
        JTextField cittaField = new JTextField();

        JLabel viaLabel = new JLabel("Via:");
        JTextField viaField = new JTextField();

        JLabel nCivicoLabel = new JLabel("Civico:");
        JTextField nCivicoField = new JTextField();

        JLabel capLabel = new JLabel("Cap:");
        JTextField capField = new JTextField();

        JLabel codiceFiscaleLabel = new JLabel("Codice fiscale:");
        JTextField codiceFiscaleField = new JTextField();


        // Etichette e campi di testo
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        // Etichette e campi di testo


        // Bottone di SignIn
        JButton signInButton = new JButton("Sign In");
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    controller.insertAccount(emailField.getText(), passwordField.getText(), usernameField.getText(), codiceFiscaleField.getText());
                    controller.frameLogin(true);
                    setVisible(false);

                    emailField.setText("");
                passwordField.setText("");
                usernameField.setText("");
                codiceFiscaleField.setText("");

//                controller.insertUser(nomeField.getText(), cognomeField.getText(), dataField.getText(),
//                                telefonoField.getText(), cittaField.getText(), viaField.getText(), nCivicoField.getText(),
//                                capField.getText(), codiceFiscaleField.getText());


            }
        });

        // Bottone "Cancel"
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> System.exit(0));

        // Aggiungi componenti al pannello principale
        mainPanel.add(nomeLabel);
        mainPanel.add(nomeField);
        mainPanel.add(cognomeLabel);
        mainPanel.add(cognomeField);
        mainPanel.add(dataLabel);
        mainPanel.add(dataField);
        mainPanel.add(telefonoLabel);
        mainPanel.add(telefonoField);
        mainPanel.add(cittaLabel);
        mainPanel.add(cittaField);
        mainPanel.add(viaLabel);
        mainPanel.add(viaField);
        mainPanel.add(nCivicoLabel);
        mainPanel.add(nCivicoField);
        mainPanel.add(capLabel);
        mainPanel.add(capField);
        mainPanel.add(codiceFiscaleLabel);
        mainPanel.add(codiceFiscaleField);

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


    }

}

