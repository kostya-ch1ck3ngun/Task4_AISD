package org.algandsd;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    private JTable arrayTable;
    private JTable sortTable;
    private JTextField inputTxtTextField;
    private JButton inputButton;
    private JCheckBox playerCheckBox;
    private JButton beforeButton;
    private JButton nextButton;
    private JButton sortButton;
    private JSpinner timeSpinner;
    private JLabel cycleField;
    private JButton randomButton;
    private JSpinner columnsSpinner;
    private JSpinner boundSpinner;
    private JButton fastSortButton;

    final int TIME = 100;
    final int RANDOM_BOUND = 256;
    final Object[] ARRAY = {8, 7, 6, 5, 4, 3, 2, 1};

    public MainForm() {
        super("Таск 4");
        setVisible(true);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(1488, 666);

        TableUpdater tableUpdater = new TableUpdater(sortTable, cycleField);

        inputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importFileToJTable(inputTxtTextField.getText(), arrayTable);
            }
        });

        arrayTable.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateTableToInt(arrayTable);
                columnsSpinner.setValue(arrayTable.getColumnCount());
            }
        });

        beforeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdater.previousState();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdater.nextState();
            }
        });

        playerCheckBox.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tableUpdater.setMode(playerCheckBox.isSelected());
            }
        });

        timeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tableUpdater.setInterval((int) timeSpinner.getValue());
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdater.startSortView(GnomeSort.sort(getIntArrayFromTable(arrayTable)));
            }
        });

        columnsSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                resizeTableColumns(arrayTable, (int) columnsSpinner.getValue());
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomTable(arrayTable, (int) boundSpinner.getValue());
            }
        });

        fastSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableUpdater.startSortView(GnomeSort.fastSort(getIntArrayFromTable(arrayTable)));
            }
        });

        timeSpinner.setValue(TIME);
        boundSpinner.setValue(RANDOM_BOUND);

        String[] columnNames = new String[ARRAY.length];
        for (int i = 0; i < ARRAY.length; i++) {
            columnNames[i] = "[" + i + "]";
        }

        DefaultTableModel newModel = new DefaultTableModel();
        newModel.setColumnIdentifiers(columnNames);
        newModel.addRow(ARRAY);

        arrayTable.setModel(newModel);
    }

    public void resizeTableColumns(JTable table, int newColumnCount) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int currentRowCount = model.getRowCount();
        int currentColumnCount = model.getColumnCount();

        if (newColumnCount == currentColumnCount) {
            return;
        }

        Object[][] newData = new Object[currentRowCount][newColumnCount];
        Object[] newColumnNames = new Object[newColumnCount];

        int minColumns = Math.min(currentColumnCount, newColumnCount);

        for (int row = 0; row < currentRowCount; row++) {
            for (int col = 0; col < minColumns; col++) {
                newData[row][col] = model.getValueAt(row, col);
            }
        }

        for (int col = 0; col < minColumns; col++) {
            newColumnNames[col] = model.getColumnName(col);
        }

        for (int col = minColumns; col < newColumnCount; col++) {
            newColumnNames[col] = "[" + col + "]";
        }

        model.setDataVector(newData, newColumnNames);
    }

    public static void randomTable (JTable table, int bound) {
        if (bound <= 0) {
            JOptionPane.showMessageDialog(null,
                    "Граница должна быть положительным числом",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int size = table.getColumnCount();

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < bound; i++) {
            numbers.add(i);
        }

        Object[] result = new Object[size];

        for (int i = 0; i < size; i++) {
            if (i % (bound) == 0 && i > 0 || i == 0) {
                Collections.shuffle(numbers);
            }

            int indexInCycle = i % (bound);
            result[i] = numbers.get(indexInCycle);
        }

        String[] columnNames = new String[size];
        for (int i = 0; i < size; i++) {
            columnNames[i] = "[" + i + "]";
        }

        DefaultTableModel newModel = new DefaultTableModel();
        newModel.setColumnIdentifiers(columnNames);
        newModel.addRow(result);

        table.setModel(newModel);
    }

    public static void importFileToJTable(String filename, JTable table) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/org/algandsd/" + filename))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split("\\s+");

                String[] columnNames = new String[data.length];
                for (int i = 0; i < data.length; i++) {
                    columnNames[i] = "[" + i + "]";
                }

                DefaultTableModel newModel = new DefaultTableModel();
                newModel.setColumnIdentifiers(columnNames);
                newModel.addRow(data);

                table.setModel(newModel);
            } else {
                table.setModel(new DefaultTableModel());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка чтения файла: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void updateTableToInt(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                Object value = model.getValueAt(row, col);

                if (value == null) {
                    continue;
                }

                try {
                    int intValue = Integer.parseInt(value.toString().trim());
                    model.setValueAt(intValue, row, col);
                } catch (NumberFormatException e) {
                    model.setValueAt(null, row, col);
                }
            }
        }

        table.revalidate();
        table.repaint();
    }

    public static int[] getIntArrayFromTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int columnCount = model.getColumnCount();
        int[] result = new int[columnCount];

        for (int col = 0; col < columnCount; col++) {
            result[col] = (Integer) model.getValueAt(0, col);
        }
        return result;
    }
}