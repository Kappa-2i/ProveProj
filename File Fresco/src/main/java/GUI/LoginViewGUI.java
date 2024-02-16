package GUI;

import CONTROLLER.Controller;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

import java.io.InputStream;
import java.net.URL;
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

    private Controller controller;



    public LoginViewGUI(Controller controllerLogin) {
        this.controller = controllerLogin;
        setTitle("Login Page");
        setSize(1400, 800);
        setMinimumSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        fontBold();
        fontRegular();
        fontExtraBold();
        fontRegularSmall();
        fontRegularBold();


        // Creazione dei pannelli
        // Creazione del pannello di sfondo e setta il GridBagLayout
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(246, 248, 255));
        contentPane.setLayout(new GridBagLayout());


        //Creazione constraints per il GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();


        //Creazione di un JPanel con BoxLayout per contenere i componenti utili per il login
        JPanel panelLoginWhite = new JPanel(new GridBagLayout());
        panelLoginWhite.setBackground(new Color(246, 248, 255)); // Scegli il colore che preferisci
        panelLoginWhite.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, -200, 0, 0);
        gbc.weightx = 0.2;
        gbc.weighty = 1;
        contentPane.add(panelLoginWhite, gbc);//Aggiunge il panelLoginWhite al contentPane


        //Creazione di un JPanel 'PanelLoginRed' con BoxLayout
        JPanel panelLoginRed = new JPanel(new BorderLayout());
        panelLoginRed.setBackground(new Color(37, 89, 87)); // Scegli il colore che preferisci
        panelLoginRed.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.45;
        gbc.weighty = 1;
        contentPane.add(panelLoginRed, gbc);//Aggiunge il panelLoginRed al contentPane




        // Creazione e aggiunta dei componenti sul pannello 'PanelLoginWhite'
        //Creazione della label 'Login'
        JLabel loginLabel = new JLabel("Login");
        if (fontExtraBold != null)
            loginLabel.setFont(fontExtraBold);
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
        emailField.setBackground(new Color(246, 248, 255));
        if (fontRegularBold != null)
            emailLabel.setFont(fontRegularBold);
        if (fontRegular != null){
            emailField.setFont(fontRegular);
        }

        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailField.getPreferredSize().height));
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
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        panelLoginWhite.add(emailLabel, gbc); //Aggiunge la emailLabel al panelLoginWhite
        gbc.gridy = 3;
        panelLoginWhite.add(emailField, gbc); //Aggiunge la emailfield al panelLoginWhite


        //Creaione della label 'Password' e della textfield per il campo password.
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(new Color(246, 248, 255));
        passwordField.setBorder(new MatteBorder(0, 0, 2, 0, new Color(185, 185, 185)));
        if (fontRegularBold != null)
            passwordLabel.setFont(fontRegularBold);
        if (fontRegular != null){
            passwordField.setFont(fontRegular);
        }
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
        passwordField.setEchoChar('*');
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // Occupa una colonna
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelLoginWhite.add(passwordLabel, gbc);

        gbc.gridy = 5;
        panelLoginWhite.add(passwordField, gbc);


        // Inizializza il JCheckBox per mostrare/nascondere la password
        showPasswordCheckBox = new JCheckBox("");
        if (fontRegularSmall != null)
            showPasswordCheckBox.setFont(fontRegularSmall);
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

        showPasswordCheckBox.setIcon(resizeIcon(LoginViewGUI.class.getResource("/IMG/hiddenpassword.png"), 35, 35));
        showPasswordCheckBox.setSelectedIcon(resizeIcon(LoginViewGUI.class.getResource("/IMG/showedpassword.png"), 35, 35));
        showPasswordCheckBox.setPressedIcon(resizeIcon(LoginViewGUI.class.getResource("/IMG/showedpassword.png"), 35, 35));
        showPasswordCheckBox.setFocusPainted(false);
        gbc.gridx = 1; // Posiziona il checkbox nella colonna successiva
        gbc.gridy = 5; // Stessa riga del campo password
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Occupa il resto della riga
        gbc.fill = GridBagConstraints.NONE; // Non allargare il componente
        gbc.anchor = GridBagConstraints.WEST; // Allinea a sinistra nella cella
        panelLoginWhite.add(showPasswordCheckBox, gbc);


        // Crazione della label 'CreaUtenteLabel'
        JLabel creaUtenteLabel = new JLabel("Crea Utente");
        if (fontRegularSmall != null)
            creaUtenteLabel.setFont(fontRegularSmall);
        creaUtenteLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile

        //Aggiungi un MouseListener alla label 'creaUtenteLabel'
        creaUtenteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Quando il mouse entra nella JLabel, applica la sottolineatura
                creaUtenteLabel.setText("<html><u>Crea Utente</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Quando il mouse esce dalla JLabel, rimuovi la sottolineatura
                creaUtenteLabel.setText("Crea Utente");
            }

            @Override
            public void mouseClicked(MouseEvent e){
                controller.showFrameSignIn();
            }
        });

        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 5, 5);
        panelLoginWhite.add(creaUtenteLabel, gbc); //Aggiunge la creaUtenteLabel al panelLoginWhite


        // Crazione della label 'passwordDimenticataLabel'
        JLabel passwordDimenticataLabel = new JLabel("Password dimenticata?");
        if (fontRegularSmall != null)
           passwordDimenticataLabel.setFont(fontRegularSmall);
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
        gbc.gridx = 0;
        gbc.gridy = 8;
        panelLoginWhite.add(passwordDimenticataLabel, gbc); //Aggiunge la passwordDimenticatalabel al panelLoginWhite


        // Creazione del button 'loginButton'
        loginButton = new JButton("Accedi");
        if (fontBold != null)
            loginButton.setFont(fontBold);
        loginButton.setOpaque(true);
        loginButton.setBackground(new Color(0, 0, 0, 255));
        loginButton.setForeground(new Color(246, 248, 255));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Chiamiamo la funzione checkCredentials dal controller passandogli i dati inseriti
            try {
                controller.checkCredentials(emailField.getText(), passwordField.getText());
                emailField.setText("");
                passwordField.setText("");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 0;
        panelLoginWhite.add(loginButton, gbc); //Aggiunge il loginButton al panelLoginWhit

        setContentPane(contentPane);
    }

    private static ImageIcon resizeIcon(URL url, int width, int height) {
        // Crea l'icona dall'URL
        ImageIcon icon = new ImageIcon(url);
        // Ridimensiona l'immagine
        Image resizedImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        // Ritorna la nuova icona ridimensionata
        return new ImageIcon(resizedImage);
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


