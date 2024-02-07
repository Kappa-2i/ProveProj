package GUI;

import CONTROLLER.Controller;
import DAO.ContoCorrenteDAO;
import ENTITY.ContoCorrente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private JPanel panelSignIn;


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
        fontRegularBold();

        // Aggiungo il content Panel
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(255, 255, 255));

        // Constraints
        GridBagConstraints gbc = new GridBagConstraints();


        // Panel 3 in alto a tutto
        JPanel panelSignIn3 = new JPanel(new GridBagLayout());
        panelSignIn3.setBackground(new Color(37, 89, 87)); // Scegli il colore che preferisci
        panelSignIn3.setOpaque(true);
        gbc.gridx = 0; // Inizia dalla prima colonna
        gbc.gridy = 0; // Prima riga
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Si estende su tutte le colonne
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.06;

        JLabel titoloFrame = new JLabel("Seleziona il tuo conto");
        if (fontExtraBold != null)
            titoloFrame.setFont(fontExtraBold);
        titoloFrame.setForeground(new Color(234, 242, 239));
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelSignIn3.add(titoloFrame, gbc);

        //Crea componenti
        ImageIcon icon = new ImageIcon(BankAccountPickViewGUI.class.getResource("/IMG/logOut.png")); // Sostituisci con il percorso del tuo file icona
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
        gbc.gridx = 3; // Imposta la posizione x nel layout della griglia
        gbc.gridy = 0; // Imposta la posizione y nel layout della griglia
        //gbc.insets = new Insets(10, 10, 10, 10); // Imposta i margini intorno al pulsante
        gbc.gridwidth = 0;
        gbc.fill = GridBagConstraints.EAST;
        gbc.weightx = 0.1;
        //gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.EAST;
        // Aggiungi il pulsante al panelSignIn3 utilizzando GridBagLayout
        //panelSignIn3.add(logOutButton, gbc);

        gbc.gridx = 0; // Inizia dalla prima colonna
        gbc.gridy = 0; // Prima riga
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Si estende su tutte le colonne
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.06;
        contentPane.add(panelSignIn3, gbc);

        // Crea un JPanel a sx
        panelSignIn = new JPanel(new GridBagLayout());
        panelSignIn.setBackground(new Color(234, 242, 239)); // Scegli il colore che preferisci
        panelSignIn.setOpaque(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.7;
        //panelSignIn.setPreferredSize(new Dimension(200, 500));
        showBankAccount();

        contentPane.add(panelSignIn, gbc);


        //Crea un JPanel a dx
        JPanel panelSignIn2 = new JPanel(new GridBagLayout());
        panelSignIn2.setBackground(new Color(234, 242, 239)); // Scegli il colore che preferisci
        panelSignIn2.setOpaque(true); // Imposta come trasparente per mostrare il gradiente
        //panelSignIn2.setPreferredSize(new Dimension(500, 500));
//        gbc.gridx = 1;
//        gbc.anchor = GridBagConstraints.CENTER;
//        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.8;
        gbc.weighty = 0.7;
        //contentPane.add(panelSignIn2, gbc);

        JLabel labelNomeUtente = new JLabel("");
        labelNomeUtente.setText("Benvenuto " +controller.account.getNomeutente() + ", seleziona il tuo conto.");

        //controller.selectBankAccount(controller.account.getEmail());


        //panelSignIn2.add(labelNomeUtente, gbc);
        setContentPane(contentPane);
    }

    public void showBankAccount(){

        ArrayList<ContoCorrente> conti = controller.selectBankAccount(controller.account.getEmail());
        if (!conti.isEmpty()){
            for (ContoCorrente conto : conti){
                int i=0;

                JPanel cardBank = new JPanel();
                cardBank.setBackground(new Color(234, 242, 239));
                cardBank.setBorder(new MatteBorder(0, 0, 2, 0, new Color(37, 89, 87)));

                JLabel ibanLabel = new JLabel("Iban: ");
                if (fontBold != null)
                    ibanLabel.setFont(fontBold);

                JLabel numberIbanLabel = new JLabel(conto.getIban());
                if (fontRegular != null)
                    numberIbanLabel.setFont(fontRegular);


                JLabel saldoLabel = new JLabel("Saldo: ");
                if (fontBold != null)
                    saldoLabel.setFont(fontBold);
                JLabel numberSaldoLabel = new JLabel(String.valueOf(conto.getSaldo())+"€");
                if (fontRegular != null)
                    numberSaldoLabel.setFont(fontRegular);


                GroupLayout glBankAccount = new GroupLayout(cardBank);
                cardBank.setLayout(glBankAccount);

                glBankAccount.setAutoCreateGaps(true);
                glBankAccount.setAutoCreateContainerGaps(true);

                GroupLayout.SequentialGroup hGroup = glBankAccount.createSequentialGroup();

                hGroup.addGroup(glBankAccount.createParallelGroup().
                        addComponent(ibanLabel).addComponent(saldoLabel));
                hGroup.addGroup(glBankAccount.createParallelGroup().
                        addComponent(numberIbanLabel).addComponent(numberSaldoLabel));
                glBankAccount.setHorizontalGroup(hGroup);

                GroupLayout.SequentialGroup vGroup = glBankAccount.createSequentialGroup();

                vGroup.addGroup(glBankAccount.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(ibanLabel).addComponent(numberIbanLabel));
                vGroup.addGroup(glBankAccount.createParallelGroup(GroupLayout.Alignment.BASELINE).
                        addComponent(saldoLabel).addComponent(numberSaldoLabel));
                glBankAccount.setVerticalGroup(vGroup);

                cardBank.setCursor(new Cursor(Cursor.HAND_CURSOR));
                cardBank.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        controller.showHomePage(numberIbanLabel.getText());
                    }
                });


                GridBagConstraints gbc = new GridBagConstraints();

                gbc.insets = new Insets(20, 20, 20, 20);
                panelSignIn.add(cardBank, gbc);
            }
        }
        else
        {

            JLabel creaContoLabel = new JLabel("Crea Conto Corrente");
            if (fontRegularBold != null)
                creaContoLabel.setFont(fontRegularBold);


            creaContoLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                        if(controller.insertBankAccount(controller.account.getEmail())) {
                            try {
                                controller.checkCredentials(controller.account.getEmail(), controller.account.getPassword());
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    creaContoLabel.setText("<html><b><u>Crea Conto Corrente</u></b></html>");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    creaContoLabel.setText("Crea Conto Corrente");
                }
            });
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(20, 20, 20, 20);
            panelSignIn.add(creaContoLabel, gbc);
        }
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


