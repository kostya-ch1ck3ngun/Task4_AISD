package org.algandsd;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class TableUpdater {
    private final JTable table;
    private final JLabel label;
    private Timer timer;

    private List<SortState> sortStates;
    private int updateIntervalMs = 0;
    private boolean isPlayerMode = false;
    
    private int currentIndex = 0;

    public TableUpdater(JTable table, JLabel label) {
        this.table = table;
        this.label = label;
    }

    public void setInterval(int interval) {
        updateIntervalMs = interval;
    }

    public void setMode(boolean usePlayer) {
        isPlayerMode = usePlayer;
    }

    public void startSortView (List<SortState> list) {
        currentIndex = 0;
        sortStates = list;
        stopTimer();
        
        if (isPlayerMode) {
            updateTable();
        } else {
            timer = new Timer(updateIntervalMs, e -> {
                updateTable();
                currentIndex++;
            });
            timer.start();
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void nextState() {
        if (isPlayerMode && !sortStates.isEmpty() && currentIndex < sortStates.size() - 1) {
            currentIndex++;
            updateTable();
        }
    }

    public void previousState() {
        if (isPlayerMode && !sortStates.isEmpty() && currentIndex > 0) {
            currentIndex--;
            updateTable();
        }
    }

    private void updateTable() {
        if (sortStates.isEmpty()) return;

        if (currentIndex > sortStates.size() - 1) {
            stopTimer();
            return;
        }

        SortState currentState = sortStates.get(currentIndex);
        int[] array = currentState.getArray();

        DefaultTableModel model = new DefaultTableModel();
        
        Object[] res = new Object[array.length];
        String[] columnNames = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            columnNames[i] = "[" + i + "]";
            res[i] = array[i];
        }

        label.setText("Итерация цикла: " + currentState.getCycleIteration());

        colorCells(table, currentState.getElementIndex(), currentState.isSorted());

        model.setColumnIdentifiers(columnNames);
        model.addRow(res);

        table.setModel(model);
        table.revalidate();
        table.repaint();
    }

    private void colorCells(JTable table, int elementIndex, boolean isSorted) {
        TableCellRenderer renderer = table.getDefaultRenderer(Object.class);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int rowIndex, int columnIndex) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, rowIndex, columnIndex);

                if (isSorted) {
                    c.setBackground(Color.GREEN);
                } else if (columnIndex == elementIndex) {
                    c.setBackground(Color.YELLOW);
                } else {
                    c.setBackground(table.getBackground());
                }

                return c;
            }
        });

        table.repaint();
    }
}
