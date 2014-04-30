package Rad;
//test
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class MyTableModel_1 extends AbstractTableModel {
    @SuppressWarnings("compatibility:-788613436047893303")
    private static final long serialVersionUID = 1L;

    public MyTableModel_1() {
        super();
    }
    static protected String[] columnNames = {"Affichage","Nom","Abréviation","A","Z","N","Demie-Vie","Type de désintégration","Population Initial", "Population Actuelle", "Activité"};
    private Object[][] data=Princip.gettabElem();
    
    
    public Object[][] fillIni(){
        Object[][] prov=new Object [40][11];
        for(int i=0; i<40; i++){
            for(int j=0; j<11; j++){
                prov[i][j]=0;
            }
            
        }
        return prov;
    }
    
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
            }else if (col == 8 & Princip.getStartSim()==false) {
                return true;
            }
            else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        
        public void tableChanged(TableModelEvent e) {
            /*
             * Les 2 colonnes modifiables sont la 1: affiche et la 8, popini, modifiable avant le démarrage.
             * dans 1, la valeur est boolean, dans 8 entière.
             * on met a jour les valeurs dans l'arraylist, puis on recrée le tableau.
             * TODO: voir si on ne peut pas changer les valeurs du tableau plutot que refaire le tableau
             */
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel)e.getSource();
                String columnName = model.getColumnName(column);
                Object data = model.getValueAt(row, column);
                
                switch(column){
                case 1: {
                            boolean value=(Boolean) data;
                            if(Princip.searchAfficheTrue()!=-1){
                                At prov=Princip.getElemListeElem(row);
                                prov.setaffiche(false);
                                Princip.setElemListeElem(row,prov);
                            }
                        At prov2=Princip.getElemListeElem(row);
                        prov2.setaffiche(true);
                        Princip.setElemListeElem(row,prov2);
                    }break;
                    case 8: {
                                int value= (Integer) data;
                            At prov=Princip.getElemListeElem(row);
                            prov.setpopIni(value);
                            Princip.setElemListeElem(row,prov);
                        }break;
                   
                }
                Princip.majTabElem();
                
            }
        
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
}
