package GUI;

import CONTROLLER.ControllerLogin;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

// Classe della GUI di login
public class LoginViewGUI extends JFrame {

    //Dichiarazioni elementi Swing
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton loginButton;

    //Dichiarazioni Variabili per i Font
    private Font fontRegular;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private Font fontRegularBold;

    private ControllerLogin controller;
    private JTextField emailTextField;
    private JTextField passwordTextField;


    public LoginViewGUI(ControllerLogin controllerLogin) {
        this.controller = controllerLogin;
        setTitle("Login Page");
        setSize(1400, 800);
        setMinimumSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        fontBold();
//        fontRegular();
//        fontExtraBold();
//        fontRegularSmall();
//        fontRegularBold();


        //Inserimento immagine maialino
//        ImageIcon imageIcon = new ImageIcon(LoginPage.class.getResource("/noun-piggy-bank-55037.png")); // Sostituisci con il tuo percorso
//        JLabel imageLabel = new JLabel(imageIcon) {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                Image scaledImage = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
//                g.drawImage(scaledImage, 0, 0, null);
//            }
//        };


        // Creazione dei pannelli


        // Creazione del pannello di sfondo e setta il GridBagLayout
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setLayout(new GridBagLayout());


        //Creazione constraints per il GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();


        //Creazione di un JPanel con BoxLayout per contenere i componenti utili per il login
        JPanel panelLoginWhite = new JPanel(new GridBagLayout());
        panelLoginWhite.setBackground(new Color(255, 255, 255)); // Scegli il colore che preferisci
        panelLoginWhite.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, -200, 0, 0);
        gbc.weightx = 0.2;
        gbc.weighty = 1;
        contentPane.add(panelLoginWhite, gbc);//Aggiunge il panelLoginWhite al contentPane


        //Creazione di un JPanel 'PanelLoginRed' con BoxLayout
        JPanel panelLoginRed = new JPanel(new GridBagLayout());
        panelLoginRed.setBackground(new Color(133, 53, 53)); // Scegli il colore che preferisci
        panelLoginRed.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.6;
        gbc.weighty = 1;
        contentPane.add(panelLoginRed, gbc);//Aggiunge il panelLoginRed al contentPane


        // Creazione e aggiunta dei componenti sul pannello 'PanelLoginWhite'


        //Creazione della label 'Login'
        JLabel loginLabel = new JLabel("Login");
//        if (fontExtraBold != null)
//            loginLabel.setFont(fontExtraBold);
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(50, 5, 100, 5);
        panelLoginWhite.add(loginLabel, gbc); //aggiunge la loginLabel al panelLoginWhite


        //Creazione della label 'Email' e della textfield per il campo email.
        JLabel emailLabel = new JLabel("Email:");

        JTextField emailField = new JTextField(20);
//        if (fontRegularBold != null)
//            emailLabel.setFont(fontRegularBold);
//        if (fontRegular != null){
//            emailField.setFont(fontRegular);
//        }
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));
        emailField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(110, 110, 110)));
        emailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                emailField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(133, 53, 53)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                emailField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(110, 110, 110)));
            }
        });
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        panelLoginWhite.add(emailLabel, gbc); //Aggiunge la emailLabel al panelLoginWhite
        gbc.gridy = 3;
        panelLoginWhite.add(emailField, gbc); //Aggiunge la emailfield al panelLoginWhite


        //Creaione della label 'Password' e della textfield per il campo password.
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(110, 110, 110)));
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(133, 53, 53)));
            }
            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(110, 110, 110)));
            }
        });
        passwordField.setEchoChar('*');
//        if (fontRegularBold != null)
//            passwordLabel.setFont(fontRegularBold);
//        if (fontRegular != null){
//            passwordField.setFont(fontRegular);
//        }
        gbc.gridy = 4;
        panelLoginWhite.add(passwordLabel, gbc); //Aggiunge la passwordLabel al panelLoginWhite
        gbc.gridy = 5;
        panelLoginWhite.add(passwordField, gbc); //Aggiunge la passwordfield al panelLoginWhite


        // Inizializza il JCheckBox per mostrare/nascondere la password
        showPasswordCheckBox = new JCheckBox("Mostra Password");
//        if (fontRegularSmall != null)
//            showPasswordCheckBox.setFont(fontRegularSmall);
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Mostra la password
                } else {
                    passwordField.setEchoChar('*'); // Nasconde la password
                }
            }
        });
        showPasswordCheckBox.setIcon(new ImageIcon(LoginViewGUI.class.getResource("IMG/hiddenpassword.png")));
        showPasswordCheckBox.setSelectedIcon(new ImageIcon(LoginViewGUI.class.getResource()));
        showPasswordCheckBox.setPressedIcon(new ImageIcon(LoginViewGUI.class.getResource()));

        gbc.gridy = 6;
        panelLoginWhite.add(showPasswordCheckBox, gbc); //Aggiunge la showPasswordCheckBox al panelLoginWhite


        // Crazione della label 'CreaUtenteLabel'
        JLabel creaUtenteLabel = new JLabel("Crea Utente");
        creaUtenteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
//        if (fontRegularSmall != null)
//            creaUtenteLabel.setFont(fontRegularSmall);

        // Aggiungi un MouseListener alla label 'creaUtenteLabel'
//        creaUtenteLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                // Quando il mouse entra nella JLabel, applica la sottolineatura
//                creaUtenteLabel.setText("<html><u>Crea Utente</u></html>");
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                // Quando il mouse esce dalla JLabel, rimuovi la sottolineatura
//                creaUtenteLabel.setText("Crea Utente");
//            }
//
//            @Override
//            public void mouseClicked(MouseEvent e){
//                myGestore.newUser();
//            }
//        });
        gbc.gridy = 7;
        panelLoginWhite.add(creaUtenteLabel, gbc); //Aggiunge la creaUtenteLabel al panelLoginWhite


        // Crazione della label 'passwordDimenticataLabel'
        JLabel passwordDimenticataLabel = new JLabel("Password dimenticata?");
//        if (fontRegularSmall != null)
//            passwordDimenticataLabel.setFont(fontRegularSmall);
        passwordDimenticataLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
//        if (fontRegularSmall != null)
//            creaUtenteLabel.setFont(fontRegularSmall);

        // Aggiungi un MouseListener alla JLabel
        passwordDimenticataLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Quando il mouse entra nella JLabel, applica la sottolineatura
                passwordDimenticataLabel.setText("<html><u>Password dimenticata?</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Quando il mouse esce dalla JLabel, rimuovi la sottolineatura
                passwordDimenticataLabel.setText("Password dimenticata?");
            }
        });
        gbc.gridy = 8;
        panelLoginWhite.add(passwordDimenticataLabel, gbc); //Aggiunge la passwordDimenticatalabel al panelLoginWhite


        // Creazione del button 'loginButton'
        loginButton = new JButton("Accedi");
        if (fontBold != null)
            loginButton.setFont(fontBold);
        loginButton.setBackground(new Color(34, 40, 35, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 0;
        panelLoginWhite.add(loginButton, gbc); //Aggiunge il loginButton al panelLoginWhite
//        loginButton.addMouseListener(new MouseAdapter(){
//            @Override
//            public void mouseClicked(MouseEvent e){
//                // Quando con il mouse clicco sul pulsante
//                myGestore.CheckLogin(emailField.getText(), passwordField.getText());
//            }
//        });

        setContentPane(contentPane);
    }
}

//    private void fontExtraBold() {
//        try {
//            // Il percorso inizia con "/" e fa riferimento alla struttura della cartella sotto "src/main/resources"
//            InputStream is = getClass().getResourceAsStream("/Rubik/static/Rubik-ExtraBold.ttf");
//            fontExtraBold = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(52f); // Modifica la dimensione a piacimento
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            // Assicurati che il font sia disponibile nel tuo ambiente grafico
//            if (fontExtraBold != null) {
//                ge.registerFont(fontExtraBold);
//            }
//        } catch (IOException | FontFormatException e) {
//            e.printStackTrace();
//            fontExtraBold = null;
//        }
//    }
//
//
//    //Creazione del fontBold
//    private void fontBold() {
//        try {
//            InputStream is = LoginViewGUI.class.getResourceAsStream("static/Rubik-Bold.ttf"); // Sostituisci con il tuo percorso
//            fontBold = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(16f); // Modifica la dimensione a piacimento
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(fontBold);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fontBold = null;
//        }
//    }
//
//
//    //Creazione del fontRegular
//    private void fontRegular() {
//        try {
//            InputStream is = LoginViewGUI.class.getResourceAsStream("/main/java/FONT/static/Rubik-Regular.ttf"); // Sostituisci con il tuo percorso
//            fontRegular = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(20f); // Modifica la dimensione a piacimento
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(fontRegular);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fontRegular = null;
//        }
//    }
//
//
//    //Creazioned del fontRegularBold
//    private void fontRegularBold() {
//        try {
//            InputStream is = LoginViewGUI.class.getResourceAsStream("/main/java/FONT/static/Rubik-Bold.ttf"); // Sostituisci con il tuo percorso
//            fontRegularBold = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(22f); // Modifica la dimensione a piacimento
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(fontRegularBold);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fontRegularBold = null;
//        }
//    }
//
//
//    //Creazione del fontRegularSmall
//    private void fontRegularSmall() {
//        try {
//            InputStream is = LoginViewGUI.class.getResourceAsStream("/main/java/FONT/static/Rubik-Regular.ttf"); // Sostituisci con il tuo percorso
//            fontRegularSmall = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f); // Modifica la dimensione a piacimento
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            ge.registerFont(fontRegularSmall);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fontRegularSmall = null;
//        }
//    }
//    loginButton.addActionListener(new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            //Chiamiamo la funzione checkCredentials dal controller passandogli i dati inseriti
//            try {
//                controller.checkCredentials(emailTextField.getText(), passwordTextField.getText());
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    });
//}
