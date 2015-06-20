package views;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;
import models.Board;
import models.Observer;
import models.Pot;

public class KalahInterface extends JFrame implements Observer {
    
    private Board board;
    private JPanel topPanel;
    private JTable table;
    private JScrollPane scrollPane;

    public KalahInterface(Board b) {
        board = b;
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Kalah");
        setSize(500, 291);
        
        setBackground(Color.BLUE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setFocusableWindowState(false);
        
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        Pot[] pots = board.getPots();
        String columnNames[] = new String[pots.length/2 + 1];
        for (int i = 0; i < columnNames.length; i++) {
            columnNames[i] = "";
        }

        String dataValues[][] = new String[5][pots.length/2 + 1];
        Pot[] firstHalf = Arrays.copyOfRange(pots, 0, pots.length/2);
        Pot[] secondHalf = Arrays.copyOfRange(pots, pots.length/2, pots.length);
        for (int i = 0; i < firstHalf.length; i++) {
            dataValues[0][i] = pots[i].getDiamonds()+"";
        }
        dataValues[0][firstHalf.length] = "";
        for (int i = 0; i < secondHalf.length; i++) {
            dataValues[1][secondHalf.length - i] = secondHalf[i].getDiamonds()+"";
        }
        dataValues[1][0] = "";
        KalahInterfaceData model = new KalahInterfaceData(dataValues, columnNames);
        table = new JTable(model);
        table.setTableHeader(null);
        table.setRowHeight(50);
        KalahCellRenderer kalahRenderer = new KalahCellRenderer();
        kalahRenderer.setHorizontalAlignment(JLabel.CENTER);
        int columns = table.getColumnCount();
        for (int i = 0; i < columns; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(kalahRenderer);
        }
        scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void updateData() {
        clonePreviousContent();
        Pot[] pots = board.getPots();
        Pot[] firstHalf = Arrays.copyOfRange(pots, 0, pots.length/2);
        Pot[] secondHalf = Arrays.copyOfRange(pots, pots.length/2, pots.length);
        for (int i = 0; i < firstHalf.length; i++) {
            updateCell(firstHalf[i].getDiamonds()+"", 0, i);
        }
        for (int i = 0; i < secondHalf.length; i++) {
            updateCell(secondHalf[i].getDiamonds()+"", 1, secondHalf.length - i);
        }
    }
    
    private void clonePreviousContent() {
        int rows = table.getModel().getRowCount();
        int columns = table.getModel().getColumnCount();
        String value;
        //copia as duas primeiras linhas para as duas últimas
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < columns; j++) {
                value = table.getModel().getValueAt(i, j)+"";
                updateCell(value, (rows + i - 2), j);
            }
        }
    }
    
    private void updateCell(String value, int row, int column) {
        table.setValueAt(value, row, column);
    }
    
}