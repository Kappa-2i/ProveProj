package GUI;

import CONTROLLER.Controller;
import EXCEPTIONS.MyExc;

import com.toedter.calendar.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class SignInViewGUI extends JFrame {

    //Dichiarazioni Variabili per i Font
    private Font fontRegular;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private Font fontRegularBold;


    private Controller controller;
    private String dataFormattata;


    public SignInViewGUI(Controller controller){


        // Impostazioni della finestra principale
        setTitle("Sign In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        fontBold();
        fontRegular();
        fontExtraBold();
        fontRegularSmall();
        fontRegularBold();

        // Pannello principale
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(255, 255, 255)); // Scegli il colore che preferisci

        JPanel panelSignInWhite = new JPanel(new GridBagLayout());
        panelSignInWhite.setBackground(new Color(255, 255, 255)); // Scegli il colore che preferisci
        panelSignInWhite.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, -200, 0, 0);
        gbc.weightx = 0.2;
        gbc.weighty = 1;
        contentPane.add(panelSignInWhite, gbc);//Aggiunge il panelLoginWhite al contentPane


        //Creazione di un JPanel 'PanelLoginRed' con BoxLayout
        JPanel panelSignInRed = new JPanel();
        panelSignInRed.setBackground(new Color(37, 89, 87)); // Scegli il colore che preferisci
        panelSignInRed.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        contentPane.add(panelSignInRed, gbc);//Aggiunge il panelLoginRed al contentPane


        // Creazione e aggiunta dei componenti sul pannello 'PanelLoginWhite'
        //Creazione della label 'Login'
        JLabel signInLabel = new JLabel("Crea Account");
        if (fontExtraBold != null)
            signInLabel.setFont(fontExtraBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.1;
        gbc.weighty = 0.0;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 20, 20, 5);
        panelSignInWhite.add(signInLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        //Creazione della label 'Login'
        JLabel creaPersona = new JLabel("Inserisci i dati personali");
        if (fontRegularBold != null)
            creaPersona.setFont(fontRegularBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 15, 5);
        panelSignInWhite.add(creaPersona, gbc); //aggiunge la loginLabel al panelLoginWhite

        //Creazione della label e field 'Nome'
        JLabel nomeLabel = new JLabel("Nome");
        if (fontBold != null)
            nomeLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(nomeLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField nomeField = new JTextField();
        if (fontRegular != null)
            nomeField.setFont(fontRegular);
        nomeField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        nomeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                nomeField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                nomeField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 3;
        panelSignInWhite.add(nomeField, gbc); //Aggiunge la emailfield al panelLoginWhite


        //Creazione della label e field 'Cognome'
        JLabel cognomeLabel = new JLabel("Cognome");
        if (fontBold != null)
            cognomeLabel.setFont(fontBold);
        gbc.gridwidth = 1; // Occupa una sola colonna
        gbc.gridx = 1; // Colonna successiva a Nome
        gbc.gridy = 2; // Stessa riga della label Nome
        gbc.insets = new Insets(10, 250, 5, 150);
        panelSignInWhite.add(cognomeLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField cognomeField = new JTextField();
        if (fontRegular != null)
            cognomeField.setFont(fontRegular);
        cognomeField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        cognomeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                cognomeField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                cognomeField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelSignInWhite.add(cognomeField, gbc); //Aggiunge la emailfield al panelLoginWhite


        //Creazione della label e field 'Nome'
        JLabel dataLabel = new JLabel("Data di nascita");
        if (fontBold != null)
            dataLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(dataLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JDateChooser dataField = new JDateChooser();
        if (fontRegular != null)
            dataField.setFont(fontRegular);

        dataField.setDateFormatString("yyyy-MM-dd"); // Imposta il formato della data

        dataField.setDate(new Date());

        dataField.getJCalendar().getYearChooser().setStartYear(1900);//anno minimo del calendario: 1900
        dataField.getJCalendar().getYearChooser().setEndYear(Calendar.getInstance().get(Calendar.YEAR));//anno massimo del calendario: anno corrente
        //java.sql.Date dataNascita = null;
        dataField.getDateEditor().setEnabled(false);
        dataField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        dataField.setOpaque(false);
        dataField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                dataField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                dataField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 5;
        panelSignInWhite.add(dataField, gbc); //Aggiunge la emailfield al panelLoginWhite

        //Creazione della label e field 'Cognome'
        JLabel telefonoLabel = new JLabel("Telefono");
        if (fontBold != null)
            telefonoLabel.setFont(fontBold);
        gbc.gridwidth = 1; // Occupa una sola colonna
        gbc.gridx = 1; // Colonna successiva a Nome
        gbc.gridy = 4; // Stessa riga della label Nome
        gbc.insets = new Insets(10, 250, 5, 150);
        panelSignInWhite.add(telefonoLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField telefonoField = new JTextField();
        if (fontRegular != null)
            telefonoField.setFont(fontRegular);
        telefonoField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        telefonoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                telefonoField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                telefonoField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        panelSignInWhite.add(telefonoField, gbc); //Aggiunge la emailfield al panelLoginWhite



        //Creazione della label e field 'Nome'
        JLabel cittaLabel = new JLabel("Città");
        if (fontBold != null)
            cittaLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(cittaLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField cittaField = new JTextField();
        if (fontRegular != null)
            cittaField.setFont(fontRegular);
        cittaField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        cittaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                cittaField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                cittaField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 7;
        panelSignInWhite.add(cittaField, gbc); //Aggiunge la emailfield al panelLoginWhite

        //Creazione della label e field 'Cognome'
        JLabel viaLabel = new JLabel("Via");
        if (fontBold != null)
            viaLabel.setFont(fontBold);
        gbc.gridwidth = 1; // Occupa una sola colonna
        gbc.gridx = 1; // Colonna successiva a Nome
        gbc.gridy = 6; // Stessa riga della label Nome
        gbc.insets = new Insets(10, 250, 5, 150);
        panelSignInWhite.add(viaLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField viaField= new JTextField();
        if (fontRegular != null)
            viaField.setFont(fontRegular);
        viaField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        viaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                viaField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                viaField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 7;
        panelSignInWhite.add(viaField, gbc); //Aggiunge la emailfield al panelLoginWhite


        //Creazione della label e field 'Nome'
        JLabel nCivicoLabel = new JLabel("Numero Civico");
        if (fontBold != null)
            nCivicoLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(nCivicoLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField nCivicoField = new JTextField();
        if (fontRegular != null)
            nCivicoField.setFont(fontRegular);
        nCivicoField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        nCivicoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                nCivicoField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                nCivicoField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 11;
        panelSignInWhite.add(nCivicoField, gbc); //Aggiunge la emailfield al panelLoginWhite

        //Creazione della label e field 'Cognome'
        JLabel capLabel = new JLabel("Cap");
        if (fontBold != null)
            capLabel.setFont(fontBold);
        gbc.gridwidth = 1; // Occupa una sola colonna
        gbc.gridx = 1; // Colonna successiva a Nome
        gbc.gridy = 10; // Stessa riga della label Nome
        gbc.insets = new Insets(10, 250, 5, 150);
        panelSignInWhite.add(capLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField capField= new JTextField();
        if (fontRegular != null)
            capField.setFont(fontRegular);
        capField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        capField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                capField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                capField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 11;
        panelSignInWhite.add(capField, gbc); //Aggiunge la emailfield al panelLoginWhite

        //Creazione della label e field 'Nome'
        JLabel codiceFiscaleLabel = new JLabel("Codice Fiscale");
        if (fontBold != null)
            codiceFiscaleLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 12;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(codiceFiscaleLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField codiceFiscaleField = new JTextField();
        if (fontRegular != null)
            codiceFiscaleField.setFont(fontRegular);
        codiceFiscaleField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        codiceFiscaleField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                codiceFiscaleField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                codiceFiscaleField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 13;
        panelSignInWhite.add(codiceFiscaleField, gbc); //Aggiunge la emailfield al panelLoginWhite


        // Creazione del button 'loginButton'
        JButton ghostButton = new JButton("");
        if (fontBold != null)
            ghostButton.setFont(fontBold);
        ghostButton.setOpaque(false);
        //continueButton.setBackground(new Color(0, 0, 0, 255));
        ghostButton.setForeground(Color.WHITE);
        ghostButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        ghostButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        ghostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Chiamiamo la funzione checkCredentials dal controller passandogli i dati inseriti
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 0;
        gbc.insets = new Insets(10, 350, 5, 150);
        panelSignInWhite.add(ghostButton, gbc); //Aggiunge il loginButton al panelLoginWhit

        //Creazione della label 'Login'
        JLabel creaAccount = new JLabel("Inserisci i dati dell'account");
        if (fontRegularBold != null)
            creaAccount.setFont(fontRegularBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 14;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(20, 20, 20, 5);
        panelSignInWhite.add(creaAccount, gbc); //aggiunge la loginLabel al panelLoginWhite

        //Creazione della label e field 'Nome'
        JLabel emailLabel = new JLabel("Email");
        if (fontBold != null)
            emailLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 15;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(emailLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField emailField = new JTextField();
        if (fontRegular != null)
            emailField.setFont(fontRegular);
        emailField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                emailField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                emailField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 16;
        panelSignInWhite.add(emailField, gbc); //Aggiunge la emailfield al panelLoginWhite

        //Creazione della label e field 'Cognome'
        JLabel nomeUtenteLabel = new JLabel("Nome Utente");
        if (fontBold != null)
            nomeUtenteLabel.setFont(fontBold);
        gbc.gridwidth = 1; // Occupa una sola colonna
        gbc.gridx = 1; // Colonna successiva a Nome
        gbc.gridy = 15; // Stessa riga della label Nome
        gbc.insets = new Insets(10, 250, 5, 150);
        panelSignInWhite.add(nomeUtenteLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JTextField nomeUtenteField= new JTextField();
        if (fontRegular != null)
            nomeUtenteField.setFont(fontRegular);
        nomeUtenteField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        nomeUtenteField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                nomeUtenteField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                nomeUtenteField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 16;
        panelSignInWhite.add(nomeUtenteField, gbc); //Aggiunge la emailfield al panelLoginWhite


        //Creazione della label e field 'Nome'
        JLabel passwordLabel = new JLabel("Password");
        if (fontBold != null)
            passwordLabel.setFont(fontBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 17;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 500);
        panelSignInWhite.add(passwordLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        if (fontRegular != null)
            passwordField.setFont(fontRegular);
        passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridy = 18;
        panelSignInWhite.add(passwordField, gbc); //Aggiunge la emailfield al panelLoginWhite

        //Creazione della label e field 'Cognome'
        JLabel confermaPasswordLabel = new JLabel("Conferma Password");
        if (fontBold != null)
            confermaPasswordLabel.setFont(fontBold);
        gbc.gridwidth = 1; // Occupa una sola colonna
        gbc.gridx = 1; // Colonna successiva a Nome
        gbc.gridy = 17; // Stessa riga della label Nome
        gbc.insets = new Insets(10, 250, 5, 150);
        panelSignInWhite.add(confermaPasswordLabel, gbc); //aggiunge la loginLabel al panelLoginWhite

        JPasswordField confermaPasswordField= new JPasswordField();
        confermaPasswordField.setEchoChar('*');
        if (fontRegular != null)
            confermaPasswordField.setFont(fontRegular);
        confermaPasswordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        confermaPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                confermaPasswordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                confermaPasswordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 18;
        panelSignInWhite.add(confermaPasswordField, gbc); //Aggiunge la emailfield al panelLoginWhite



        // Creazione del button 'loginButton'
        JButton continueButton = new JButton("Continua");
        if (fontBold != null)
            continueButton.setFont(fontBold);
        continueButton.setOpaque(true);
        continueButton.setBackground(new Color(0, 0, 0, 255));
        continueButton.setForeground(Color.WHITE);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        continueButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Chiamiamo la funzione checkCredentials dal controller passandogli i dati inseriti
            }
        });


        gbc.gridx = 1;
        gbc.gridy = 20;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 0;
        gbc.insets = new Insets(10, 250, 20, 150);
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date dataSelezionata = dataField.getDate();
                dataFormattata = new SimpleDateFormat("yyyy-MM-dd").format(dataSelezionata);
                if(controller.confirmedPassword(passwordField.getText(), confermaPasswordField.getText())){
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
                                nomeUtenteField.getText(),
                                passwordField.getText());
                    } catch (MyExc ex) {
                        throw new RuntimeException(ex);
                    }

                    setVisible(false);
                    controller.frameLogin(true);

                    emailField.setText("");
                    nomeUtenteField.setText("");
                    passwordField.setText("");
                    codiceFiscaleField.setText("");
                    nomeField.setText("");
                    cognomeField.setText("");
                    dataField.setDate(new Date());
                    dataFormattata = null;
                    nCivicoField.setText("");
                    cittaField.setText("");
                    viaField.setText("");
                    capField.setText("");
                    telefonoField.setText("");
                    confermaPasswordField.setText("");

                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Le password non corrispondono",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        panelSignInWhite.add(continueButton, gbc); //Aggiunge il loginButton al panelLoginWhit

        // Creazione del button 'loginButton'
        JButton backButton = new JButton("Indietro");
        if (fontBold != null)
            backButton.setFont(fontBold);
        backButton.setOpaque(true);
        backButton.setBackground(new Color(0, 0, 0, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Chiamiamo la funzione checkCredentials dal controller passandogli i dati inseriti
                setVisible(false);
                controller.frameLogin(true);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 20, 10);
        panelSignInWhite.add(backButton, gbc); //Aggiunge il loginButton al panelLoginWhit

        setContentPane(contentPane);
    }

    //Creazione del fontExtraBold
    private void fontExtraBold() {
        try {
            InputStream is = LoginViewGUI.class.getResourceAsStream("/FONT/Rubik-ExtraBold.ttf"); // Sostituisci con il tuo percorso
            fontExtraBold = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(52f); // Modifica la dimensione a piacimento
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontExtraBold);
        } catch (Exception e) {
            e.printStackTrace();
            fontExtraBold = null;
        }
    }

    //Creazione del fontBold
    private void fontBold() {
        try {
            InputStream is = LoginViewGUI.class.getResourceAsStream("/FONT/Rubik-Bold.ttf"); // Sostituisci con il tuo percorso
            fontBold = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(16f); // Modifica la dimensione a piacimento
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontBold);
        } catch (Exception e) {
            e.printStackTrace();
            fontBold = null;
        }
    }

    //Creazione del fontRegular
    private void fontRegular() {
        try {
            InputStream is = LoginViewGUI.class.getResourceAsStream("/FONT/Rubik-Regular.ttf"); // Sostituisci con il tuo percorso
            fontRegular = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f); // Modifica la dimensione a piacimento
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontRegular);
        } catch (Exception e) {
            e.printStackTrace();
            fontRegular = null;
        }
    }

    //Creazioned del fontRegularBold
    private void fontRegularBold() {
        try {
            InputStream is = LoginViewGUI.class.getResourceAsStream("/FONT/Rubik-Bold.ttf"); // Sostituisci con il tuo percorso
            fontRegularBold = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(22f); // Modifica la dimensione a piacimento
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontRegularBold);
        } catch (Exception e) {
            e.printStackTrace();
            fontRegularBold = null;
        }
    }

    //Creazione del fontRegularSmall
    private void fontRegularSmall() {
        try {
            InputStream is = LoginViewGUI.class.getResourceAsStream("/FONT/Rubik-Regular.ttf"); // Sostituisci con il tuo percorso
            fontRegularSmall = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f); // Modifica la dimensione a piacimento
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontRegularSmall);
        } catch (Exception e) {
            e.printStackTrace();
            fontRegularSmall = null;
        }
    }

}


