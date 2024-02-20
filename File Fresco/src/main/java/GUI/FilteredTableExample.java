import javax.swing.*;
import javax.swing.table.*;

public class FilteredTableExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Filtered JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Dati della tabella (esempio)
        String[][] data = {
                {"John", "25", "Male"},
                {"Sara", "22", "Female"},
                {"Paul", "30", "Male"},
                {"Laura", "28", "Female"}
        };
        String[] columnNames = {"Name", "Age", "Gender"};

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Impostazione del TableRowSorter
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Campo di testo per l'input di ricerca
        JTextField filterText = new JTextField();
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            String text = filterText.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                // Filtra la colonna in base al testo inserito
                sorter.setRowFilter(RowFilter.regexFilter(text));
            }
        });

        // Aggiungi componenti al frame
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(new JScrollPane(table));
        frame.add(filterText);
        frame.add(filterButton);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
