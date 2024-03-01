package GUI;

import CONTROLLER.Controller;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

public class BankTransferPageGUI extends JFrame {

    private Controller controller;
    //Dichiarazioni Variabili per i Font
    private Font fontRegular;
    private Font fontBold;
    private Font fontExtraBold;
    private Font fontRegularSmall;
    private Font fontRegularBold;
    private Font fontRegularXXL;

    public BankTransferPageGUI(Controller controller){
        this.controller = controller;
        setTitle("Invia Bonifico - S.M.U.");
        setVisible(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setResizable(false);
        setSize(700, 600);
        setLocationRelativeTo(null);
        fontBold();
        fontRegular();
        fontExtraBold();
        fontRegularSmall();
        fontRegularBold();
        fontRegularXXL();


        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBackground(new Color(246, 248, 255));

        JLabel inviaBonificoLabel = new JLabel();


        JTextField nomeDestinatarioField = new JTextField();


        JTextField cognomeDestinatarioField = new JTextField();




        JTextField ibanDestinatarioField = new JTextField();



        JTextField importoField = new JTextField();
        importoField.setForeground(Color.GRAY);



        JTextArea causaleArea = new JTextArea();

        causaleArea.setRows(7);
        causaleArea.setColumns(10);
        PlainDocument doc = (PlainDocument) causaleArea.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            private int maxChars = 400;

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offs, int length, String str, AttributeSet a)
                    throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength());
                int totalLength = text.length() - length + str.length();
                if (totalLength <= maxChars) {
                    super.replace(fb, offs, length, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offs, String str, AttributeSet a)
                    throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength());
                int totalLength = text.length() + str.length();
                if (totalLength <= maxChars) {
                    super.insertString(fb, offs, str, a);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

        //Funzioni che fanno andare accapo quando scrivi nella TextArea
        causaleArea.setLineWrap(true);
        causaleArea.setWrapStyleWord(true);

        JButton inviaButton = new JButton("Invia");
        inviaButton.setOpaque(true);
        inviaButton.setBackground(new Color(0, 0, 0, 255));
        inviaButton.setForeground(new Color(246, 248, 255));
        inviaButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        inviaButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cambia il cursore per indicare che è cliccabile
        inviaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.sendBankTransfer(ibanDestinatarioField.getText(), importoField.getText(), nomeDestinatarioField.getText(), cognomeDestinatarioField.getText(),causaleArea.getText());
            }
        });


        if(fontBold!=null){
            inviaButton.setFont(fontBold);
        }
        if(fontExtraBold!=null){
            inviaBonificoLabel.setFont(fontExtraBold);
        }
        if(fontRegular!=null){
            nomeDestinatarioField.setFont(fontRegular);
            cognomeDestinatarioField.setFont(fontRegular);
            importoField.setFont(fontRegular);
            ibanDestinatarioField.setFont(fontRegular);
            causaleArea.setFont(fontRegular);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 0.9;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        gbc.gridx = 0;
        contentPane.add(inviaBonificoLabel, gbc);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridx = 0;
        contentPane.add(nomeDestinatarioField,gbc);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridx = 1;
        contentPane.add(cognomeDestinatarioField,gbc);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 5, 5, 5);
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        gbc.gridx = 0;
        contentPane.add(ibanDestinatarioField,gbc);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 5, 5, 5);
        gbc.weighty = 0.1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 2;
        gbc.gridx = 1;
        contentPane.add(importoField,gbc);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 10, 5, 10);
        gbc.weighty = 0.2;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        contentPane.add(causaleArea,gbc);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weighty = 0.05;
        gbc.gridy = 4;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = 0;
        contentPane.add(inviaButton,gbc);

        // Utilizza SwingUtilities.invokeLater per richiedere il focus
        SwingUtilities.invokeLater(() -> inviaButton.requestFocusInWindow());
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
