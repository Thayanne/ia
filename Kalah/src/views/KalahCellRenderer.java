package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class KalahCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        Component result = super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column);

        result.setFont(new Font("arial", Font.BOLD, 16));
        
        int lastColumn = table.getColumnCount() - 1;
        boolean isPlayerKalah = (
            (row == 0 || row == 3) && column == 0
        );
        boolean isPlayerRow = (
            row == 0 || row == 3
        );
        boolean isAIKalah = (
            (row == 1 || row == 4) && column == lastColumn
        );
        boolean isAIRow = (
            row == 1 || row == 4
        );
        boolean isWhiteSquare = (
            ((row == 0 || row == 3) && column == lastColumn)
            || ((row == 1 || row == 4) && column == 0)
            || row == 2
        );
        if (isPlayerKalah)
            result.setBackground(new Color(153, 50, 204));
        else if (isAIKalah)
            result.setBackground(new Color(225, 20, 147));
        else if (isWhiteSquare)
            result.setBackground(new Color(255, 255, 255));
        else if (isPlayerRow)
            result.setBackground(new Color(255, 105, 180));
        else if (isAIRow)
            result.setBackground(new Color(218, 112, 214));
        
        return result;
    }
}
