package GUI;

import CONTROLLER.Controller;
import ENTITY.Salvadanaio;
import ENTITY.Transazione;
import EXCEPTIONS.MyExc;
import org.apache.pdfbox.cos.COSObjectKey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class TransazioniGUI extends JFrame {
    private Controller controller;
    //Dichiarazioni Variabili per i Font
    private Font fontRegular;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private Font fontRegularBold;
    private Font fontRegularXXL;

    public TransazioniGUI(Controller controller){
        this.controller = controller;
        setTitle("Le tue spese - S.M.U.");
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
        Object[] optionsAdd = {"Crea", "Annulla"};
        Object[] optionsFill = {"Invia", "Annulla"};
        Object[] optionsGet = {"Prendi", "Annulla"};
        Object[] options = {"Annulla", "Invia soldi", "Prendi soldi", "Elimina"};


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
        JPanel panelCenter = new JPanel(new GridBagLayout());
        panelCenter.setBackground(new Color(37, 89, 87));
        gbc.gridwidth = 1;
        gbc.weighty = 0.95;
        gbc.weightx = 0.7;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        contentPane.add(panelCenter, gbc);

        // Dichiarazione dei componenti per il pannello superiore
        JLabel speseLabel = new JLabel("Le tue spese");
        speseLabel.setForeground(new Color(246, 248, 255));
        JLabel titoloSmu = new JLabel("S.M.U.");
        titoloSmu.setForeground(Color.WHITE);
        if (fontExtraBold != null) {
            speseLabel.setFont(fontExtraBold);
            titoloSmu.setFont(fontRegular);
        }

        ImageIcon iconUnina = new ImageIcon(SalvadanaioGUI.class.getResource("/IMG/unina.png")); // Sostituisci con il percorso del tuo file icona
        JButton buttonLogo = new JButton();
        buttonLogo.setBackground(null);
        buttonLogo.setIcon(iconUnina);
        buttonLogo.setContentAreaFilled(false);
        buttonLogo.setOpaque(false);
        buttonLogo.setBorderPainted(false);
        buttonLogo.setBorder(null);
        buttonLogo.setFocusPainted(false);

        ImageIcon iconHome = new ImageIcon(SalvadanaioGUI.class.getResource("/IMG/home.png")); // Sostituisci con il percorso del tuo file icona
        JButton buttonHome = new JButton();
        buttonHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonHome.setBackground(null);
        buttonHome.setIcon(iconHome);
        buttonHome.setContentAreaFilled(false);
        buttonHome.setOpaque(false);
        buttonHome.setBorderPainted(false);
        buttonHome.setBorder(null);
        buttonHome.setFocusPainted(false);
        buttonHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.showHomePage(controller.contoScelto);
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
        panelTop.add(speseLabel, gbc);

        // Infine, aggiungi spazio di espansione a destra per mantenere homePageLabel centrata
        gbc.gridx = 5;
        gbc.weightx = 1.0; // Bilancia lo spazio extra a destra
        gbc.fill = GridBagConstraints.EAST;
        panelTop.add(Box.createHorizontalGlue(), gbc);

        gbc = new GridBagConstraints();
        // Configurazione per buttonUser e buttonLogout a destra della homePageLabel
        gbc.gridx = 6; // Posiziona buttonUser a destra della homePageLabel
        //panelTop.add(addPiggyBankButton, gbc);

        gbc.gridx = 7; // Posiziona buttonLogout a destra di buttonUser
        gbc.insets = new Insets(0, 20, 0, 15); // Aggiusta gli insetti se necessario
        panelTop.add(buttonHome, gbc);


        /**
         * Definizione della JTable con le informazioni di tutti i salvadanai del conto
         * */


        // Colonne della tabella
        String[] colonne = {"Importo", "Causale", "Data", "Orario", "Iban"};

        // Modello di tabella predefinito con celle non editabili
        DefaultTableModel modello = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rende tutte le celle non editabili
            }
        };
        // Aggiungere i dati del salvadanaio al modello
        for(Transazione transazione : controller.transazioni){
            String importo;
            if(transazione.getTipoTransazione().equals("Invia a")){
                importo = "-"+transazione.getImporto()+"€";
            }else {
                importo = "+"+transazione.getImporto()+"€";
            }
            Object[] riga = {importo,
                    transazione.getCausale(),
                    transazione.getDataTransazione(),
                    transazione.getOrarioTransazione(),
                    transazione.getIban()
            };
            modello.addRow(riga);
        }


        // Creare la tabella con il modello
        JTable tabella = new JTable(modello);
        JScrollPane scrollPane = new JScrollPane(tabella);
        tabella.getTableHeader().setFont(fontRegularBold);
        tabella.getTableHeader().setBackground(new Color(246, 248, 255));
        scrollPane.getViewport().setBackground(new Color(246, 248, 255)); // Sostituisci Color.LIGHT_GRAY con il colore desiderato
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));


        // Renderer centrato per le celle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabella.setFont(fontRegular);
        tabella.setRowHeight(70);
        tabella.setForeground(Color.WHITE);
        tabella.setBackground(new Color(37, 89, 87));

        // Applicare il renderer a tutte le celle per centrare il testo
        for (int i = 0; i < tabella.getColumnCount(); i++) {
            tabella.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Impostare la larghezza preferita per le colonne 0,1
        int smallWidth = 280;
        for (int i = 0; i <= 1; i++) {
            TableColumn column = tabella.getColumnModel().getColumn(i);
            column.setPreferredWidth(smallWidth);
            column.setMaxWidth(smallWidth);
            column.setMinWidth(smallWidth);
        }

        // Impostare la larghezza preferita per le colonne 2, 3, 4, 5
        smallWidth = 200;
        for (int i = 2; i <= 4; i++) {
            TableColumn column = tabella.getColumnModel().getColumn(i);
            column.setPreferredWidth(smallWidth);
            column.setMaxWidth(smallWidth);
            column.setMinWidth(smallWidth);
        }



//        tabella.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                Point point = e.getPoint();
//                int currentRow = tabella.rowAtPoint(point);
//
//                if (currentRow >= 0) { // Verifica che il clic sia su una riga valida
//                    // Costruisci il messaggio con i dati della riga cliccata
//                    StringBuilder infoSalvadanaio = new StringBuilder();
//                    infoSalvadanaio.append("Dettagli Salvadanaio:\n");
//                    for (int i = 0; i < tabella.getColumnCount(); i++) {
//                        infoSalvadanaio.append("<html><b>" +tabella.getColumnName(i) + ": </b>" + tabella.getValueAt(currentRow, i) + "\n</html>");
//                    }
//
//                    // Mostra il dialogo con le informazioni
//                    ImageIcon iconInformation = new ImageIcon(HomePageGUI.class.getResource("/IMG/information.png"));
//                    // Mostra il JOptionPane con i JTextField inseriti
//                    int result = JOptionPane.showOptionDialog(
//                            null, // Componente padre
//                            infoSalvadanaio.toString(), // Messaggio
//                            "Informazioni Salvadanaio", // Titolo
//                            JOptionPane.DEFAULT_OPTION,
//                            JOptionPane.QUESTION_MESSAGE, // Tipo di messaggio
//                            iconInformation, // Icona personalizzata, usa null per l'icona di default
//                            options, // Array contenente le etichette dei pulsanti
//                            options[0] // Opzione di default
//                    );
//
//                    switch (result){
//                        case 0: // annulla
//                            break;
//                        case 1: // invia soldi
//                            JPanel fillPiggyBankPanel = new JPanel(new GridBagLayout());
//                            JLabel soldiLabel = new JLabel("Inserisci una cifra da inviare al salvadanaio: ");
//                            JTextField soldiField = new JTextField();
//                            GridBagConstraints gbc = new GridBagConstraints();
//                            gbc.fill = GridBagConstraints.BOTH;
//                            gbc.gridy = 0;
//                            gbc.gridx = 0;
//                            fillPiggyBankPanel.add(soldiLabel, gbc);
//                            gbc.gridy = 1;
//                            gbc.gridx = 0;
//                            fillPiggyBankPanel.add(soldiField, gbc);
//                            int resultFill = JOptionPane.showOptionDialog(
//                                    null, // Componente padre
//                                    fillPiggyBankPanel, // Messaggio
//                                    "Invia soldi al salvadanaio", // Titolo
//                                    JOptionPane.DEFAULT_OPTION,
//                                    JOptionPane.QUESTION_MESSAGE, // Tipo di messaggio
//                                    iconInformation, // Icona personalizzata, usa null per l'icona di default
//                                    optionsFill, // Array contenente le etichette dei pulsanti
//                                    optionsFill[0] // Opzione di default
//                            );
//                            if (resultFill == JOptionPane.YES_OPTION){
//                                if( controller.contoScelto.getSaldo() >= Double.parseDouble(soldiField.getText())) {
//                                    controller.fillPiggyBank((String) tabella.getValueAt(currentRow, 0), Double.parseDouble(soldiField.getText()));
//                                    controller.updateBankAccount(controller.contoScelto);
//                                    controller.showSalvadanaioPage();
//                                }
//                                else {
//                                    JOptionPane.showMessageDialog(
//                                            null,
//                                            "Saldo conto corrente insufficiente!",
//                                            "Errore",
//                                            JOptionPane.ERROR_MESSAGE
//                                    );
//                                }
//
//                            }
//
//                            break;
//                        case 2: // prendi soldi
//                            JPanel getPiggyBankPanel = new JPanel(new GridBagLayout());
//                            JLabel getSoldiLabel = new JLabel("Inserisci una cifra da prendere dal salvadanaio: ");
//                            JTextField getSoldiField = new JTextField();
//                            gbc = new GridBagConstraints();
//                            gbc.fill = GridBagConstraints.BOTH;
//                            gbc.gridy = 0;
//                            gbc.gridx = 0;
//                            getPiggyBankPanel.add(getSoldiLabel, gbc);
//                            gbc.gridy = 1;
//                            gbc.gridx = 0;
//                            getPiggyBankPanel.add(getSoldiField, gbc);
//                            int resultGet = JOptionPane.showOptionDialog(
//                                    null, // Componente padre
//                                    getPiggyBankPanel, // Messaggio
//                                    "Prendi soldi dal salvadanaio", // Titolo
//                                    JOptionPane.DEFAULT_OPTION,
//                                    JOptionPane.QUESTION_MESSAGE, // Tipo di messaggio
//                                    iconInformation, // Icona personalizzata, usa null per l'icona di default
//                                    optionsGet, // Array contenente le etichette dei pulsanti
//                                    optionsGet[0] // Opzione di default
//                            );
//                            if (resultGet == JOptionPane.YES_OPTION){
//                                // Ottieni il valore dalla tabella e convertilo in Stringa
//                                String valueWithCurrency = (String) tabella.getValueAt(currentRow, 3);
//                                //  Rimuovi il simbolo della valuta '€' e qualsiasi altro carattere non numerico, mantenendo solo numeri e punto decimale
//                                String numericValue = valueWithCurrency.replaceAll("[^\\d.]", "");
//
//                                if(Double.parseDouble(numericValue) >= Double.parseDouble(getSoldiField.getText())) {
//                                    controller.getMoneyByPiggyBank((String) tabella.getValueAt(currentRow, 0), Double.parseDouble(getSoldiField.getText()));
//                                    controller.updateBankAccount(controller.contoScelto);
//                                    controller.showSalvadanaioPage();
//                                }
//                                else {
//                                    JOptionPane.showMessageDialog(
//                                            null,
//                                            "Saldo salvadanaio insufficiente!",
//                                            "Errore",
//                                            JOptionPane.ERROR_MESSAGE
//                                    );
//                                }
//                            }
//
//                            break;
//                        case 3: // elimina
//                            if (tabella.getValueAt(currentRow, 3).equals("0.0€")) {
//                                controller.deletePiggyBank((String) tabella.getValueAt(currentRow, 0));
//                                controller.showSalvadanaioPage();
//                            }
//                            else{
//                                JOptionPane.showMessageDialog(
//                                        null,
//                                        "Rimuovi prima i tuoi risparmi!",
//                                        "Errore",
//                                        JOptionPane.ERROR_MESSAGE
//                                );
//                            }
//                            break;
//
//                    }
//                }
//            }
//        });



        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.weightx = 1;
        panelCenter.add(scrollPane, gbc);

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
