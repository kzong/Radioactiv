package Rad;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MyTableModel_1 extends AbstractTableModel {
    @SuppressWarnings("compatibility:-788613436047893303")
    private static final long serialVersionUID = 1L;

    public MyTableModel_1() {
        super();
    }
    private String[] columnNames = {"Affichage","Nom","Abréviation","A","Z","N","Demie-Vie","Type de désintégration","Population Initial", "Population Actuelle", "Activité"};
    private Object[][] data =fillData();
    
    
    public Object[][] fillData(){
        ArrayList<At> Liste=Princip.getList();
        int numCol=10;
        int numLign=Liste.size();
        Object[][] data=new Object[numCol][numLign];
        for (int i = 0; i < Liste.size(); i++) {
            At atome = Liste.get(i);
            data[i][0]=atome.getaffiche();
            data[i][1]=atome.getnom();
            data[i][2]=atome.getabr();
            data[i][3]=atome.getA();
            data[i][4]=atome.getZ();
            data[i][5]=atome.getN();
            data[i][6]=atome.getdVie();
            data[i][7]=atome.gettype();
            data[i][8]=atome.getpopIni();
            data[i][9]=atome.getpop2();
            data[i][10]=atome.getactivite();
            
        }
        return data;
    }
    
    public String getColumnName(int col) {
           return columnNames[col].toString();
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
            if (col > 1) {
                return false;
            }else if (col == 9 & Princip.getStartSim()==true) {
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
