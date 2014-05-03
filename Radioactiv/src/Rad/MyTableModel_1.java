package Rad;
//change2
/*CREDITS
 * Enzo Vironda
 * utilisation des exemples Oracle
 */
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class MyTableModel_1 extends AbstractTableModel implements TableModel {
    @SuppressWarnings("compatibility:-788613436047893303")
    private static final long serialVersionUID = 1L;

    public MyTableModel_1() {
        super();
    }
    static protected String[] columnNames = {"Affichage","Nom","Abréviation","A","Z","N","Demie-Vie","Type de désintégration","Population Initial", "Population Actuelle", "Activité"};
    private Object[][] data=Princip.listTo2dTab();
    
    
    public String getColumnName(int col) {
           return columnNames[col].toString();
       }
    
    public static String[] getColumnNames() {
           return columnNames;
       }


        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col>=1 & col != 8) {
                return false;
            }else if (col == 8 & Princip.getstartSim()==true) {
                return false;
            }
            else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        
       
        
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
}
