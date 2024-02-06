package GUI;

import CONTROLLER.Controller;
import EXCEPTIONS.MyExc;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class SignInViewGUI extends JFrame {


    private Controller controller;
    private String dataFormattata;


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

        JDateChooser dataField = new JDateChooser();
        dataField.setDateFormatString("yyyy-MM-dd"); // Imposta il formato della data
        dataField.getJCalendar().getYearChooser().setStartYear(1900);//anno minimo del calendario: 1900
        dataField.getJCalendar().getYearChooser().setEndYear(Calendar.getInstance().get(Calendar.YEAR));//anno massimo del calendario: anno corrente


        java.sql.Date dataNascita = null;




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

                java.util.Date dataSelezionata = dataField.getDate();
                if (dataSelezionata != null) {
                    // Viene formattata la data Selezionata dall'utente e passata ad una stringa
                    dataFormattata = new SimpleDateFormat("yyyy-MM-dd").format(dataSelezionata);
                }

                try {
                    controller.insertUser(nomeField.getText(),
                            cognomeField.getText(),
                            telefonoField.getText(),
                            dataFormattata,
                            cittaField.getText(),
                            viaField.getText(),
                            nCivicoField.getText(),
                            capField.getText(),
                            codiceFiscaleField.getText(),
                            emailField.getText(),
                            usernameField.getText(),
                            passwordField.getText());


                } catch (MyExc ex) {
                    throw new RuntimeException(ex);
                }

                setVisible(false);
                controller.frameLogin(true);

                emailField.setText("");
                usernameField.setText("");
                passwordField.setText("");
                codiceFiscaleField.setText("");
                nomeField.setText("");
                cognomeField.setText("");
                dataField.setDate(null);
                nCivicoField.setText("");
                cittaField.setText("");
                viaField.setText("");
                capField.setText("");
                telefonoField.setText("");




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

