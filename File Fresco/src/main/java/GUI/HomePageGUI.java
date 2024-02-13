package GUI;

import CONTROLLER.Controller;

import javax.naming.event.ObjectChangeListener;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

public class HomePageGUI extends JFrame {
    private Controller controller;

    //Dichiarazioni Variabili per i Font
    private Font fontRegular;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private Font fontRegularBold;
    private Font fontRegularXXL;



    public HomePageGUI(Controller controller){
        this.controller = controller;
        setTitle("HomePage - S.M.U.");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        fontBold();
        fontRegular();
        fontExtraBold();
        fontRegularSmall();
        fontRegularBold();
        fontRegularXXL();

        // Creazione panello principale che contiene il tutto
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(246, 248, 255));

        // Dichiarazione dei constraints per posizionare i pannelli
        GridBagConstraints gbc = new GridBagConstraints();

        // Dichiarazione del pannello superiore con aggiunta dei constraints per posizionarlo
        JPanel panelTop = new JPanel(new GridBagLayout());
        panelTop.setBackground(new Color(37, 89, 87));
        gbc.gridwidth = 4;
        gbc.weighty = 0.1;
        gbc.weightx = 1;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(panelTop, gbc);

        // Dichiarazione del pannello laterale sinistro con aggiunta dei constraints per posizionarlo
        RoundedPanel panelLeft = new RoundedPanel(50, new Color(37, 89, 87));
        panelLeft.setLayout(new GridBagLayout());
        gbc.gridwidth = 1;
        gbc.weighty = 0.95;
        gbc.weightx = 0.04;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        contentPane.add(panelLeft, gbc);

        // Dichiarazione del pannello laterale destro con aggiunta dei constraints per posizionarlo
        JPanel panelGhost = new JPanel(new GridBagLayout());
        panelGhost.setBackground(new Color(65, 157, 120));

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0;
        gbc.weightx = 0.35;
        gbc.gridy = 1;
        gbc.gridx = 2;
        contentPane.add(panelGhost, gbc);

        JPanel userPanel = new JPanel();
        userPanel.setVisible(false);
        userPanel.setBackground(new Color(217, 217, 217));
        userPanel.setBorder(new MatteBorder(0, 3, 0, 0, new Color(37, 89, 87)));
        userPanel.setLayout(new GridBagLayout());
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 0;
        gbc.weightx = 0.08;
        gbc.gridy = 1;
        gbc.gridx = 3;
        contentPane.add(userPanel, gbc);



        // Dichiarazione dei componenti per il pannello superiore
        JLabel homePageLabel = new JLabel("Home Page");
        homePageLabel.setForeground(new Color(246, 248, 255));
        JLabel titoloSmu = new JLabel("S.M.U.");
        titoloSmu.setForeground(Color.WHITE);
        if (fontExtraBold != null) {
            homePageLabel.setFont(fontExtraBold);
            titoloSmu.setFont(fontRegular);
        }

        ImageIcon iconUnina = new ImageIcon(HomePageGUI.class.getResource("/IMG/unina.png")); // Sostituisci con il percorso del tuo file icona
        JButton buttonLogo = new JButton();
        buttonLogo.setBackground(null);
        buttonLogo.setIcon(iconUnina);
        buttonLogo.setContentAreaFilled(false);
        buttonLogo.setOpaque(false);
        buttonLogo.setBorderPainted(false);
        buttonLogo.setBorder(null);
        buttonLogo.setFocusPainted(false);

        ImageIcon iconLogOut = new ImageIcon(HomePageGUI.class.getResource("/IMG/logout.png")); // Sostituisci con il percorso del tuo file icona
        JButton buttonLogout = new JButton();
        buttonLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonLogout.setBackground(null);
        buttonLogout.setIcon(iconLogOut);
        buttonLogout.setContentAreaFilled(false);
        buttonLogout.setOpaque(false);
        buttonLogout.setBorderPainted(false);
        buttonLogout.setBorder(null);
        buttonLogout.setFocusPainted(false);
        buttonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int risposta = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire?", "Conferma Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (risposta == JOptionPane.YES_OPTION)
                    controller.backLoginPage();
            }
        });



        JLabel accountLabel = new JLabel("Account");
        accountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        accountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                accountLabel.setText("<html><u>Account</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                accountLabel.setText("Account");
            }
        });
        JLabel bankAccountLabel = new JLabel("Seleziona Conto");
        bankAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bankAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bankAccountLabel.setText("<html><u>Seleziona Conto</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bankAccountLabel.setText("Seleziona Conto");
            }

            @Override
            public void mouseClicked(MouseEvent e){
                controller.backFramePick();
            }
        });
        JLabel notificheLabel = new JLabel("Notifiche");
        notificheLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        notificheLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                notificheLabel.setText("<html><u>Notifiche</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                notificheLabel.setText("Notifiche");
            }
        });
        JLabel settingsLabel = new JLabel("Elimina Conto Corrente");
        settingsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                settingsLabel.setText("<html><u>Elimina Conto Corrente</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settingsLabel.setText("Elimina Conto Corrente");
            }

            @Override
            public void mouseClicked(MouseEvent e){
                controller.deleteBankAccount(controller.contoScelto.getIban());
            }
        });

        if (fontRegular != null){
            accountLabel.setFont(fontRegular);
            bankAccountLabel.setFont(fontRegular);
            notificheLabel.setFont(fontRegular);
            settingsLabel.setFont(fontRegular);
        }

        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 10);
        userPanel.add(accountLabel, gbc);
        gbc.gridy = 1;
        userPanel.add(bankAccountLabel, gbc);
        gbc.gridy = 2;
        userPanel.add(notificheLabel, gbc);
        gbc.gridy = 3;
        userPanel.add(settingsLabel, gbc);




        ImageIcon iconUser = new ImageIcon(HomePageGUI.class.getResource("/IMG/user.png")); // Sostituisci con il percorso del tuo file icona
        JButton buttonUser = new JButton();
        buttonUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonUser.setBackground(null);
        buttonUser.setIcon(iconUser);
        buttonUser.setContentAreaFilled(false);
        buttonUser.setOpaque(false);
        buttonUser.setBorderPainted(false);
        buttonUser.setBorder(null);
        buttonUser.setFocusPainted(false);
        buttonUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!userPanel.isVisible())
                    userPanel.setVisible(true);
                else
                    userPanel.setVisible(false);
            }
        });

        gbc = new GridBagConstraints();


        // Configurazione per buttonLogo a sinistra di titoloSmu
        gbc.gridx = 1; // Posizione immediatamente a sinistra di titoloSmu
        gbc.weightx = 0; // Non assegna spazio extra, mantiene la posizione
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 15, 0, 0); // Aggiusta gli insetti se necessario
        panelTop.add(buttonLogo, gbc);

        // Configurazione per il titoloSmu a sinistra di homePageLabel
        gbc.gridx = 2; // Posiziona titoloSmu accanto a buttonLogo
        panelTop.add(titoloSmu, gbc);

        gbc = new GridBagConstraints();
        // Infine, aggiungi spazio di espansione a destra per mantenere homePageLabel centrata
        gbc.gridx = 3;
        gbc.weightx = 1.0; // Bilancia lo spazio extra a destra
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelTop.add(Box.createHorizontalGlue(), gbc);

        gbc = new GridBagConstraints();
        // Configurazione per la homePageLabel al centro
        gbc.gridx = 4;
        panelTop.add(homePageLabel, gbc);

        // Infine, aggiungi spazio di espansione a destra per mantenere homePageLabel centrata
        gbc.gridx = 5;
        gbc.weightx = 1.0; // Bilancia lo spazio extra a destra
        gbc.fill = GridBagConstraints.EAST;
        panelTop.add(Box.createHorizontalGlue(), gbc);

        gbc = new GridBagConstraints();
        // Configurazione per buttonUser e buttonLogout a destra della homePageLabel
        gbc.gridx = 6; // Posiziona buttonUser a destra della homePageLabel
        panelTop.add(buttonUser, gbc);

        gbc.gridx = 7; // Posiziona buttonLogout a destra di buttonUser
        gbc.insets = new Insets(0, 20, 0, 15); // Aggiusta gli insetti se necessario
        panelTop.add(buttonLogout, gbc);



        /**
         * Aggiungiamo ora i componenti all'interno del panello di sinistra
         * */
        RoundedPanel saldoPanel = new RoundedPanel(50, new Color(72, 173, 169) );
        saldoPanel.setLayout(new GridBagLayout());

        RoundedPanel spesePanel = new RoundedPanel(50, new Color(111, 195, 192));
        spesePanel.setLayout(new GridBagLayout());

        RoundedPanel salvadanaioPanel = new RoundedPanel(50, new Color(154, 213, 211));
        salvadanaioPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.33;
        gbc.insets = new Insets(20, 20, 20, 20);
        panelLeft.add(saldoPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.33;
        panelLeft.add(spesePanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.33;
        panelLeft.add(salvadanaioPanel, gbc);

        /**
         * Aggiungiamo i componenti ad ognuno dei 3 rounded panel all'interno del panel di sx
         * */


        JLabel cartaLabel = new JLabel("CARTA");
        cartaLabel.setForeground(new Color(8, 76, 97));
        JLabel saldoLabel = new JLabel(String.valueOf(controller.contoScelto.getSaldo())+"€");
        saldoLabel.setForeground(new Color(246, 248, 255));
        JButton buttonSaldo = new JButton();
        ImageIcon iconSaldo = new ImageIcon(HomePageGUI.class.getResource("/IMG/credit_resized.png"));
        buttonSaldo.setBackground(null);
        buttonSaldo.setIcon(iconSaldo);
        buttonSaldo.setContentAreaFilled(false);
        buttonSaldo.setOpaque(false);
        buttonSaldo.setBorderPainted(false);
        buttonSaldo.setBorder(null);
        buttonSaldo.setFocusPainted(false);


        JLabel speseLabel = new JLabel("<html><b>LE TUE<br>SPESE</b></html>");
        speseLabel.setForeground(new Color(8, 76, 97));
        JButton buttonSpese = new JButton();
        ImageIcon iconSpese = new ImageIcon(HomePageGUI.class.getResource("/IMG/time-count_resized_flipped.png"));
        buttonSpese.setBackground(null);
        buttonSpese.setIcon(iconSpese);
        buttonSpese.setContentAreaFilled(false);
        buttonSpese.setOpaque(false);
        buttonSpese.setBorderPainted(false);
        buttonSpese.setBorder(null);
        buttonSpese.setFocusPainted(false);

        JLabel salvadanaioLabel = new JLabel("<html><b>PIGGY<br>BANK</b></html>");
        salvadanaioLabel.setForeground(new Color(8, 76, 97));
        JButton buttonSalvadanaio = new JButton();
        ImageIcon iconSalvadanaio = new ImageIcon(HomePageGUI.class.getResource("/IMG/saving_resized.png"));
        buttonSalvadanaio.setBackground(null);
        buttonSalvadanaio.setIcon(iconSalvadanaio);
        buttonSalvadanaio.setContentAreaFilled(false);
        buttonSalvadanaio.setOpaque(false);
        buttonSalvadanaio.setBorderPainted(false);
        buttonSalvadanaio.setBorder(null);
        buttonSalvadanaio.setFocusPainted(false);

        if (fontRegular != null){
            saldoLabel.setFont(fontRegularXXL);
            cartaLabel.setFont(fontRegularXXL);
            speseLabel.setFont(fontRegularXXL);
            salvadanaioLabel.setFont(fontRegularXXL);
        }


        gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.5;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(-40, 0, 0, 0);
        saldoPanel.add(cartaLabel, gbc);


        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(-50, 0, 0, 0);
        saldoPanel.add(saldoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        saldoPanel.add(buttonSaldo, gbc);

        gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.5;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        spesePanel.add(speseLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        spesePanel.add(buttonSpese, gbc);

        gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        salvadanaioPanel.add(salvadanaioLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 50, 0, 0);
        salvadanaioPanel.add(buttonSalvadanaio, gbc);


        /**
         * Aggiungiamo ora i componenti all'interno del panello trasparente centrale
         * */
        RoundedPanel inviaSoldiPanel = new RoundedPanel(200, new Color(246, 248, 255) );
        inviaSoldiPanel.setLayout(new GridBagLayout());

        RoundedPanel raccoltePanel = new RoundedPanel(200, new Color(246, 248, 255));
        raccoltePanel.setLayout(new GridBagLayout());

        RoundedPanel riceviSoldiPanel = new RoundedPanel(200, new Color(246, 248, 255));
        riceviSoldiPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.33;
        gbc.insets = new Insets(20, 20, 20, 20);
        panelGhost.add(inviaSoldiPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.33;
        panelGhost.add(raccoltePanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.33;
        panelGhost.add(riceviSoldiPanel, gbc);

        /**
         * Aggiungiamo i componenti ad ognuno dei 3 rounded panel all'interno del panel trasparente
         * */


        JButton buttonInviaSoldi = new JButton();
        ImageIcon iconInviaSoldi = new ImageIcon(HomePageGUI.class.getResource("/IMG/kapp-05.png"));
        buttonInviaSoldi.setBackground(null);
        buttonInviaSoldi.setIcon(iconInviaSoldi);
        buttonInviaSoldi.setContentAreaFilled(false);
        buttonInviaSoldi.setOpaque(false);
        buttonInviaSoldi.setBorderPainted(false);
        buttonInviaSoldi.setBorder(null);
        buttonInviaSoldi.setFocusPainted(false);

        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        inviaSoldiPanel.add(buttonInviaSoldi, gbc);


        JButton buttonRaccolte = new JButton();
        ImageIcon iconRaccolte = new ImageIcon(HomePageGUI.class.getResource("/IMG/kapp-03.png"));
        buttonRaccolte.setBackground(null);
        buttonRaccolte.setIcon(iconRaccolte);
        buttonRaccolte.setContentAreaFilled(false);
        buttonRaccolte.setOpaque(false);
        buttonRaccolte.setBorderPainted(false);
        buttonRaccolte.setBorder(null);
        buttonRaccolte.setFocusPainted(false);

        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        raccoltePanel.add(buttonRaccolte, gbc);


        JButton buttonNotifiche = new JButton();
        ImageIcon iconNotifiche = new ImageIcon(HomePageGUI.class.getResource("/IMG/kapp-01.png"));
        buttonNotifiche.setBackground(null);
        buttonNotifiche.setIcon(iconNotifiche);
        buttonNotifiche.setContentAreaFilled(false);
        buttonNotifiche.setOpaque(false);
        buttonNotifiche.setBorderPainted(false);
        buttonNotifiche.setBorder(null);
        buttonNotifiche.setFocusPainted(false);

        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        riceviSoldiPanel.add(buttonNotifiche, gbc);



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

    //Creazione del fontRegular
    private void fontRegularXXL() {
        try {
            InputStream is = LoginViewGUI.class.getResourceAsStream("/FONT/Rubik-Bold.ttf"); // Sostituisci con il tuo percorso
            fontRegularXXL = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f); // Modifica la dimensione a piacimento
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fontRegularXXL);
        } catch (Exception e) {
            e.printStackTrace();
            fontRegularXXL = null;
        }
    }
}