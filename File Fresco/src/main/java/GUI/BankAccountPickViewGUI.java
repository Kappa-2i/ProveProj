package GUI;

import CONTROLLER.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class BankAccountPickViewGUI extends JFrame {
    private Controller controller;
    private Font fontRegular;
    private Font fontRegularBold;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private JButton logOutButton;


    public BankAccountPickViewGUI(Controller controller){
        this.controller = controller;
        setTitle("Seleziona conto");
        setSize(1920, 800);
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fontBold();
        fontRegular();
        fontExtraBold();
        fontRegularSmall();

        // Aggiungo il content Panel
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(255, 255, 255));

        // Constraints
        GridBagConstraints gbc = new GridBagConstraints();


        // Panel 3 in alto a tutto
        JPanel panelSignIn3 = new JPanel(new GridBagLayout());
        panelSignIn3.setBackground(new Color(224, 164, 88, 191)); // Scegli il colore che preferisci
        panelSignIn3.setOpaque(true);
        gbc.gridx = 0; // Inizia dalla prima colonna
        gbc.gridy = 0; // Prima riga
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Si estende su tutte le colonne
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.07;
        contentPane.add(panelSignIn3, gbc);

        // Crea un JPanel a sx
        JPanel panelSignIn = new JPanel(new GridBagLayout());
        panelSignIn.setBackground(new Color(0, 126, 167)); // Scegli il colore che preferisci
        panelSignIn.setOpaque(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 0.7;
        //panelSignIn.setPreferredSize(new Dimension(200, 500));
        contentPane.add(panelSignIn, gbc);


        //Crea un JPanel a dx
        JPanel panelSignIn2 = new JPanel(new GridBagLayout());
        panelSignIn2.setBackground(new Color(95, 94, 65)); // Scegli il colore che preferisci
        panelSignIn2.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        //panelSignIn2.setPreferredSize(new Dimension(500, 500));
//        gbc.gridx = 1;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7;
        gbc.weighty = 0.7;
        contentPane.add(panelSignIn2, gbc);







        //Crea componenti
        ImageIcon icon = new ImageIcon("/IMG/logOut.png"); // Sostituisci con il percorso del tuo file icona
        logOutButton = new JButton("");
        logOutButton.setIcon(icon);
        if (fontBold != null)
            logOutButton.setFont(fontBold);
        logOutButton.setOpaque(true);
        logOutButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        logOutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Quando premo il bottone
                controller.account = null;
                setVisible(false);
                controller.frameLogin(true);
            }
        });
        // Crea un oggetto GridBagConstraints per il pulsante
        gbc.gridx = 0; // Imposta la posizione x nel layout della griglia
        gbc.gridy = 0; // Imposta la posizione y nel layout della griglia
        gbc.insets = new Insets(10, 10, 10, 10); // Imposta i margini intorno al pulsante
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.EAST;
        // Aggiungi il pulsante al panelSignIn3 utilizzando GridBagLayout

        JLabel labelNomeUtente = new JLabel("");
        labelNomeUtente.setText("Benvenuto " +controller.account.getNomeutente() + ", seleziona il tuo conto.");

        controller.selectBankAccount(controller.account.getEmail());

        panelSignIn3.add(logOutButton, gbc);
        panelSignIn2.add(labelNomeUtente, gbc);




//        JLabel creaAccountLabel = new JLabel("Crea Account");
//        creaAccountLabel.setForeground(Color.WHITE);
//        if(fontExtraBold != null)
//            creaAccountLabel.setFont(fontExtraBold);
//        gbc.gridy = 0;
//        gbc.gridx = 0;
//        gbc.insets = new Insets(5, 5, 20, 5);
//        panelSignIn.add(creaAccountLabel, gbc);
//
//        JLabel nomeLabel = new JLabel("Nome");
//        nomeLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            nomeLabel.setFont(fontBold);
//        RoundedTextField nomeField = new RoundedTextField(20);
//        if(fontRegular != null)
//            nomeField.setFont(fontRegular);
//        gbc.gridy = 1;
//        gbc.insets = new Insets(5, 5, 5, 5);
//        panelSignIn.add(nomeLabel, gbc);
//        gbc.gridy = 2;
//        panelSignIn.add(nomeField, gbc);
//
//
//
//        JLabel cognomeLabel = new JLabel("Cognome");
//        cognomeLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            cognomeLabel.setFont(fontBold);
//        RoundedTextField cognomeField = new RoundedTextField(20);
//        if(fontRegular != null)
//            cognomeField.setFont(fontRegular);
//        gbc.insets = new Insets(5, 20, 5, 5);
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        panelSignIn.add(cognomeLabel, gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 2;
//        panelSignIn.add(cognomeField, gbc);
//
//
//
//
//        JLabel dataLabel = new JLabel("Data di Nascita");
//        dataLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            dataLabel.setFont(fontBold);;
//        RoundedTextField dataField = new RoundedTextField(20);
//        if(fontRegular != null)
//            dataField.setFont(fontRegular);
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        panelSignIn.add(dataLabel, gbc);
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        panelSignIn.add(dataField, gbc);
//
//
//        JLabel luogoNascitaLabel = new JLabel("Luogo di Nascita");
//        luogoNascitaLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            luogoNascitaLabel.setFont(fontBold);
//        RoundedTextField luogoNascitaField = new RoundedTextField(20);
//        if(fontRegular != null)
//            luogoNascitaField.setFont(fontRegular);
//        gbc.insets = new Insets(5, 20, 5, 5);
//        gbc.gridx = 1;
//        gbc.gridy = 3;
//        panelSignIn.add(luogoNascitaLabel, gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 4;
//        panelSignIn.add(luogoNascitaField, gbc);
//
//
//        JLabel codiceFiscaleLabel = new JLabel("Codice Fiscale");
//        codiceFiscaleLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            codiceFiscaleLabel.setFont(fontBold);
//        RoundedTextField codiceFiscaleField = new RoundedTextField(16);
//        if(fontRegular != null)
//            codiceFiscaleField.setFont(fontRegular);
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.gridx = 0;
//        gbc.gridy = 5;
//        panelSignIn.add(codiceFiscaleLabel, gbc);
//        gbc.gridx = 0;
//        gbc.gridy = 6;
//        panelSignIn.add(codiceFiscaleField, gbc);
//
//
//        JLabel telefonoLabel = new JLabel("Telefono");
//        telefonoLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            telefonoLabel.setFont(fontBold);
//        RoundedTextField telefonoField = new RoundedTextField(10);
//        if(fontRegular != null)
//            telefonoField.setFont(fontRegular);
//        gbc.insets = new Insets(5, 20, 5, 5);
//        gbc.gridx = 1;
//        gbc.gridy = 5;
//        panelSignIn.add(telefonoLabel, gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 6;
//        panelSignIn.add(telefonoField, gbc);
//
//
//        JLabel emailLabel = new JLabel("Email");
//        emailLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            emailLabel.setFont(fontBold);
//        emailField = new RoundedTextField(20);
//        if (fontRegular != null)
//            emailField.setFont(fontRegular);
//        emailField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusLost(FocusEvent e) {
//                super.focusLost(e);
//                validateEmail(emailField.getText());
//            }
//        });
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.gridx = 0;
//        gbc.gridy = 7;
//        panelSignIn.add(emailLabel, gbc);
//        gbc.gridx = 0;
//        gbc.gridy = 8;
//        panelSignIn.add(emailField, gbc);
//
//
//
//        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setForeground(Color.WHITE);
//        if(fontBold != null)
//            passwordLabel.setFont(fontBold);
//        RoundedTextField passwordField = new RoundedTextField(20);
//        if(fontRegular != null)
//            passwordField.setFont(fontRegular);
//        passwordField.setEchoChar('*');
//        gbc.insets = new Insets(5, 20, 5, 5);
//        gbc.gridx = 1;
//        gbc.gridy = 7;
//        panelSignIn.add(passwordLabel, gbc);
//        gbc.gridx = 1;
//        gbc.gridy = 8;
//        panelSignIn.add(passwordField, gbc);
//
//        JButton signInButton = new JButton("Registrati");
//        signInButton.setForeground(Color.WHITE);
//        if(fontRegular != null)
//            signInButton.setFont(fontRegular);
//        signInButton.setBackground(new Color(255, 138, 91));
//        signInButton.setForeground(Color.WHITE);
//        signInButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
//        gbc.gridx = 1;
//        gbc.gridy = 9;
//        gbc.anchor = GridBagConstraints.EAST;
//        gbc.fill = 0;
//        gbc.insets = new Insets(15, 5, 5, 5);
//        panelSignIn.add(signInButton, gbc);
//
//        JButton backButton = new JButton("Indietro");
//        backButton.setForeground(Color.WHITE);
//        if(fontRegular != null)
//            backButton.setFont(fontRegular);
//        backButton.setBackground(new Color(255, 138, 91));
//        backButton.setForeground(Color.WHITE);
//        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
//        backButton.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e){
//                // Quando con il mouse clicco sul pulsante
//                myGestore.backToLogin();
//            }
//        });
//        gbc.gridx = 1;
//        gbc.gridy = 9;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = 0;
//        panelSignIn.add(backButton, gbc);
//
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


