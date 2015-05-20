package views;

import javax.swing.table.DefaultTableModel;

public class KalahInterfaceData extends DefaultTableModel {

   public KalahInterfaceData(Object[][] tableData, Object[] colNames) {
      super(tableData, colNames);
   }

    @Override
   public boolean isCellEditable(int row, int column) {
      return false;
   }
}