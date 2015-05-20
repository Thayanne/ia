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
        if (row == 0 && column == 0)
            result.setBackground(new Color(153, 50, 204));
        else if (row == 1 && column == lastColumn)
            result.setBackground(new Color(225, 20, 147));
        else if ((row == 0 && column == lastColumn) || (row == 1 && column == 0))
            result.setBackground(new Color(255, 255, 255));
        else if (row == 0)
            result.setBackground(new Color(255, 105, 180));
        else
            result.setBackground(new Color(218, 112, 214));
        
        return result;
    }
}
