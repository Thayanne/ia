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

        setTitle("Thayanne's Kalah");
        setSize(500, 140);
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

        String dataValues[][] = new String[2][pots.length/2 + 1];
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
        Pot[] pots = board.getPots();
        Pot[] firstHalf = Arrays.copyOfRange(pots, 0, pots.length/2);
        Pot[] secondHalf = Arrays.copyOfRange(pots, pots.length/2, pots.length);
        for (int i = 0; i < firstHalf.length; i++) {
            table.setValueAt(firstHalf[i].getDiamonds()+"", 0, i);
        }
        for (int i = 0; i < secondHalf.length; i++) {
            table.setValueAt(secondHalf[i].getDiamonds()+"", 1, secondHalf.length - i);
        }
    }
    
}