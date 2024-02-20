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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.*;


public class TransazioniGUI extends JFrame {
    private Controller controller;
    //Dichiarazioni Variabili per i Font
    private Font fontRegular;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private Font fontRegularBold;
    private Font fontRegularXXL;

    private static final Map<String, String> monthMap = new HashMap<>();
    static {
        monthMap.put("Gennaio", "01");
        monthMap.put("Febbraio", "02");
        monthMap.put("Marzo", "03");
        monthMap.put("Aprile", "04");
        monthMap.put("Maggio", "05");
        monthMap.put("Giugno", "06");
        monthMap.put("Luglio", "07");
        monthMap.put("Agosto", "08");
        monthMap.put("Settembre", "09");
        monthMap.put("Ottobre", "10");
        monthMap.put("Novembre", "11");
        monthMap.put("Dicembre", "12");
    }

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

        Object[] optionsView = {"Visualizza", "Annulla"};



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
                setVisible(false);
                controller.showHomePage(controller.contoScelto);
            }
        });

        ImageIcon iconStats = new ImageIcon(SalvadanaioGUI.class.getResource("/IMG/statistics.png")); // Sostituisci con il percorso del tuo file icona
        JButton statsButton = new JButton();
        statsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statsButton.setBackground(null);
        statsButton.setIcon(iconStats);
        statsButton.setContentAreaFilled(false);
        statsButton.setOpaque(false);
        statsButton.setBorderPainted(false);
        statsButton.setBorder(null);
        statsButton.setFocusPainted(false);
        statsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Creazione del JPanel che conterrà i JTextField
                JPanel statsPanel = new JPanel(new GridBagLayout());

                JLabel selezionaLabel = new JLabel("Seleziona mese: ");
                // Array contenente i nomi dei mesi


                // Crea la JList utilizzando l'array dei mesi
                JComboBox<String> monthsComboBox = new JComboBox<>(monthMap.keySet().toArray(new String[0]));

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 0.3;
                gbc.gridy = 0;
                gbc.gridx = 0;
                statsPanel.add(selezionaLabel, gbc);
                gbc.gridwidth = 2;
                gbc.weightx = 0.6;
                gbc.gridy = 1;
                gbc.gridx = 0;
                statsPanel.add(monthsComboBox, gbc);


                // Mostra il JOptionPane con i JTextField inseriti
                int result = JOptionPane.showOptionDialog(
                        null, // Componente padre
                        statsPanel, // Messaggio
                        "Visualizza Report", // Titolo
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, // Tipo di messaggio
                        null, // Icona personalizzata, usa null per l'icona di default
                        optionsView, // Array contenente le etichette dei pulsanti
                        optionsView[0] // Opzione di default
                );
                if (result == JOptionPane.YES_OPTION){
                    String selectedMonthName = (String) monthsComboBox.getSelectedItem();
                    String monthValue = monthMap.get(selectedMonthName);

                    // Ottieni l'anno corrente
                    String currentYear = String.valueOf(LocalDate.now().getYear());

                    // Combina l'anno e il mese nel formato YYYY-MM
                    String yearMonth = currentYear + "-" + monthValue;
                    controller.viewReport(controller.contoScelto, yearMonth);


                    JPanel reportPanel = new JPanel(new GridBagLayout());

                    JLabel entrataMax = new JLabel("Entrata Massima: ");
                    JLabel entraMaxValue = new JLabel(String.valueOf(controller.report[0]) + "€");
                    JLabel entrataMin = new JLabel("Entrata Minima: ");
                    JLabel entraMinValue = new JLabel(String.valueOf(controller.report[1])+ "€");
                    JLabel entrataMed = new JLabel("Entrata Media: ");
                    JLabel entraMedValue = new JLabel(String.valueOf(controller.report[2])+ "€");
                    JLabel uscitaMax = new JLabel("Uscita Massima: ");
                    JLabel uscitaMaxValue = new JLabel(String.valueOf(controller.report[3])+ "€");
                    JLabel uscitaMin = new JLabel("Uscita Minima: ");
                    JLabel uscitaMinValue = new JLabel(String.valueOf(controller.report[4])+ "€");
                    JLabel uscitaMed = new JLabel("Uscita Media: ");
                    JLabel uscitaMedValue = new JLabel(String.valueOf(controller.report[5])+ "€");

                    gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.3;
                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    reportPanel.add(entrataMax, gbc);
                    gbc.gridwidth = 2;
                    gbc.weightx = 0.6;
                    gbc.gridy = 0;
                    gbc.gridx = 1;
                    reportPanel.add(entraMaxValue, gbc);
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.3;
                    gbc.gridy = 1;
                    gbc.gridx = 0;
                    reportPanel.add(entrataMin, gbc);
                    gbc.gridwidth = 2;
                    gbc.weightx = 0.6;
                    gbc.gridy = 1;
                    gbc.gridx = 1;
                    reportPanel.add(entraMinValue, gbc);
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.3;
                    gbc.gridy = 2;
                    gbc.gridx = 0;
                    reportPanel.add(entrataMed, gbc);
                    gbc.gridwidth = 2;
                    gbc.weightx = 0.6;
                    gbc.gridy = 2;
                    gbc.gridx = 1;
                    reportPanel.add(entraMedValue, gbc);

                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.insets = new Insets(10, 0, 0, 0);
                    gbc.weightx = 0.3;
                    gbc.gridy = 3;
                    gbc.gridx = 0;
                    reportPanel.add(uscitaMax, gbc);
                    gbc.gridwidth = 2;
                    gbc.weightx = 0.6;
                    gbc.gridy = 3;
                    gbc.gridx = 1;
                    reportPanel.add(uscitaMaxValue, gbc);
                    gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.3;
                    gbc.gridy = 4;
                    gbc.gridx = 0;
                    reportPanel.add(uscitaMin, gbc);
                    gbc.gridwidth = 2;
                    gbc.weightx = 0.6;
                    gbc.gridy = 4;
                    gbc.gridx = 1;
                    reportPanel.add(uscitaMinValue, gbc);
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.weightx = 0.3;
                    gbc.gridy = 5;
                    gbc.gridx = 0;
                    reportPanel.add(uscitaMed, gbc);
                    gbc.gridwidth = 2;
                    gbc.weightx = 0.6;
                    gbc.gridy = 5;
                    gbc.gridx = 1;
                    reportPanel.add(uscitaMedValue, gbc);



                    JOptionPane.showMessageDialog(
                            null,
                            reportPanel,
                            "Dio",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                }
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
        panelTop.add(statsButton, gbc);

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
        tabella.getTableHeader().setFont(fontRegularBold);
        tabella.getTableHeader().setBackground(new Color(246, 248, 255));
        tabella.setFont(fontRegular);
        tabella.setRowHeight(70);
        tabella.setForeground(Color.WHITE);
        tabella.setBackground(new Color(37, 89, 87));
        tabella.setTableHeader(null);

        // Creiamo lo scrollPane che contiene la tabella
        JScrollPane scrollPane = new JScrollPane(tabella);
        scrollPane.getViewport().setBackground(new Color(246, 248, 255)); // Sostituisci Color.LIGHT_GRAY con il colore desiderato
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabella.getModel());
        tabella.setRowSorter(sorter);

        // Assumendo che "DataOra" sia nella quarta colonna (indice 3) del modello
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING)); // Ordina per "Data" in ordine decrescente
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.DESCENDING)); // Ordina per "Ora" in ordine decrescente
        sorter.setSortKeys(sortKeys);


        // Renderer centrato per le celle
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Applicare il renderer a tutte le celle per centrare il testo
        for (int i = 0; i < tabella.getColumnCount(); i++) {
            tabella.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumn column = tabella.getColumnModel().getColumn(0);
        column.setPreferredWidth(200);
        column.setMaxWidth(200);
        column.setMinWidth(200);

        column = tabella.getColumnModel().getColumn(1);
        column.setPreferredWidth(400);
        column.setMaxWidth(400);
        column.setMinWidth(400);

        column = tabella.getColumnModel().getColumn(2);
        column.setPreferredWidth(200);
        column.setMaxWidth(200);
        column.setMinWidth(200);

        column = tabella.getColumnModel().getColumn(3);
        column.setPreferredWidth(200);
        column.setMaxWidth(200);
        column.setMinWidth(200);

        column = tabella.getColumnModel().getColumn(4);
        column.setPreferredWidth(400);
        column.setMaxWidth(400);
        column.setMinWidth(400);

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
