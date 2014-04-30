package Rad;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/*
 * CREDITS
 * getters et setters de At par Kexin Zong
 * Objet At et base de donnée des atomes par Daphné Guibert
 * Jmathplot, courbes etc... par Alexandre Lamartine
 * Reste par Enzo Vironda
 * ReadCSV inspiré de Nagesh Chauhan @http://www.beingjavaguys.com/2013/09/read-and-parse-csv-file-in-java.html
 * Relecture, optimisation, correction par tous
 */

public class Princip {

    //pas du chronométre

    protected static int T = 1000; //pas du timer
    protected static double delay = 1; // une seconde  de temps réel = delay secondes de temps
    static Timer timer;
    protected double temps;
    protected static ArrayList<At> ListeElem =
        new ArrayList(); //passage d'une linked list à Array list car Linkedlist.get o(n) et ArrayList.get o(1) d'oé une surchage de parcours
    //protected static JTable jtabElem;
    protected static Object[][] tabElem;
    protected static boolean finSim = false;
    protected int posElec;
    protected int posHel;
    protected int posProt;
    protected static boolean StartSim = false;
    InterfGraph fen;
    public static Object[][] test2 = MyTableModel_1.fillIni();


    public Princip() {

        //rempli la liste des atomes
        Origin(); // met en place timer

        //jtabElem = new JTable(tabElem, MyTableModel_1.getColumnNames()); //inutile ?
        InterfGraph.setjTable1();
        // TODO créer fonction qui remplit les conditions initiales: a priori, tableau éditable avant le play


    }

    public void desint(At atome) {
        /*
        *Prend un atome et en réalise la désintégration :
        détemine en quel élément il se désintégre selon le type de désintégration
        parcourt la liste pour trouver l'atome crée, met à  jour les populations
        Si une particule est émise, sa position est connue et sa population mise Ã  jour.
        */
        At atomeprov;
        At atomedes;
        int posAtome; //position
        int des = 0;
        switch (atome.gettype()) {
        case 0:
            break;
        case 1:
            { //alpha
                for (int i = 0; i < ListeElem.size(); i++) {
                    atomeprov = ListeElem.get(i);
                    if (atomeprov.getA() == (atome.getA() - 4) & atomeprov.getZ() == (atome.getZ() - 2)) {
                        atomedes = atomeprov;
                        atome.setpop1(atome.pop2);
                        atome.setpop2((int) (atome.popIni *
                                             Math.exp(atome.getdVie() * temps))); // loi decroissance radio
                        atomedes.setpop1(atome.pop2);
                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop2(des);
                        ListeElem.set(i, atomedes);
                        At He = ListeElem.get(posHel); // trouve élém Helium, pop1 pop précédente, pop2 nouvelle pop
                        He.setpop1(He.getpop2());
                        He.setpop2(He.getpop2() + des);
                        ListeElem.set(posHel, He);
                    }
                }

            }
            break;
        case 2:
            { //Beta moins
                for (int i = 0; i < ListeElem.size(); i++) {
                    atomeprov = ListeElem.get(i);
                    if (atomeprov.getA() == (atome.getA()) & atomeprov.getZ() == (atome.getZ() + 1)) {
                        atomedes = atomeprov;
                        atome.setpop1(atome.pop2);
                        atome.setpop2((int) (atome.popIni *
                                             Math.exp(atome.getdVie() * temps))); // loi decroissance radio
                        atomedes.setpop1(atome.pop2);
                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop2(des);
                        ListeElem.set(i, atomedes);
                        At El = ListeElem.get(posElec); // trouve élém Elec, pop1 pop précédente, pop2 nouvelle pop
                        El.setpop1(El.getpop2());
                        El.setpop2(El.getpop2() + des);
                        ListeElem.set(posElec, El);
                    }
                }

            }
            break;
        case 3:
            { //Beta plus
                for (int i = 0; i < ListeElem.size(); i++) {
                    atomeprov = ListeElem.get(i);
                    if (atomeprov.getA() == (atome.getA()) & atomeprov.getZ() == (atome.getZ() - 1)) {
                        atomedes = atomeprov;
                        atome.setpop1(atome.pop2);
                        atome.setpop2((int) (atome.popIni *
                                             Math.exp(atome.getdVie() * temps))); // loi decroissance radio
                        atomedes.setpop1(atome.pop2);
                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop2(des);
                        ListeElem.set(i, atomedes);
                        At Pro = ListeElem.get(posProt); // trouve élém Proton, pop1 pop précédente, pop2 nouvelle pop
                        Pro.setpop1(Pro.getpop2());
                        Pro.setpop2(Pro.getpop2() + des);
                        ListeElem.set(posProt, Pro);
                    }
                }

            }
            break;
        case 4:
            {
            }
            break;

        }


    }

    private void Origin() {
        /*
         *Lis CSV, Crée liste, Crée tableau, le  remplit, crée le jTable, crée l'interface et la rend visible
         */
        // readCSV.convertCsvToJavaAt(ListeElem);
        readCSV.convertCsvToJavaAt2(ListeElem);
        searchPosParticule();
        majTabElem(); //rempli un tableau à partir de l'ArrayList
        //jtabElem = new JTable(tabElem, MyTableModel_1.getColumnNames()); //inutile ?

        fen = new InterfGraph();
        InterfGraph.setjTable1();
        InterfGraph.refreshTab();

        fen.setVisible(true);
        fen.setResizable(false);
        fen.revalidate();
        fen.repaint();
        SwingUtilities.updateComponentTreeUI(fen);

        timer = new Timer(T, new TimerAction()); //implémenter timeraction Timer(T, new TimerAction())
        temps = 0;
        InterfGraph.textsetjTextField1("Timer");


    }

    public void testTimer() {
        At test = getElemListeElem(0);
        test.setA(test.getA() + 1);
        setElemListeElem(0, test);
        majTabElem();
        InterfGraph.setjTable1();
        InterfGraph.refreshTab();

    }

    public void boucle_principale() {
        /*appel&e à chaque instant t
        * réalise la dégradation de chaque élément
        * met a jour l'activité
        * MAJ tableau et jTable
        * rafraichit l'interface
        */
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            desint(atome);
            atome.activite();
        }
        majTabElem();
        //jtabElem = new JTable(tabElem, MyTableModel_1.getColumnNames());
        InterfGraph.setjTable1();
        InterfGraph.refreshTab();
        fen.repaint();

    }

    public static void main(String[] args) {
        Princip Rad = new Princip();
    }

    private class TimerAction implements ActionListener {

        // ActionListener appProee tous les interval millisecondes

        public void actionPerformed(ActionEvent e) {
            //boucle_principale();
            testTimer();


            temps = temps + getdelay();
            System.out.println(temps);
            InterfGraph.textsetjTextField1(String.valueOf(temps));
        }

    }


    public static Object[][] fillData() {
        /*
         * rempli un tableau 2D à partir de ListeElem
         */
        ArrayList<At> Liste = Princip.getList();
        int numCol = 11;
        int numLign = Liste.size();
        Object[][] data = new Object[numLign][numCol];
        for (int i = 0; i < numLign; i++) {
            At atome = Liste.get(i);
            data[i][0] = atome.getaffiche();
            data[i][1] = atome.getnom();
            data[i][2] = atome.getabr();
            data[i][3] = atome.getA();
            data[i][4] = atome.getZ();
            data[i][5] = atome.getN();
            data[i][6] = atome.getdVie();
            data[i][7] = atome.gettype();
            data[i][8] = atome.getpopIni();
            data[i][9] = atome.getpop2();
            data[i][10] = atome.getactivite();

        }
        return data;
    }


    private void searchPosParticule() {
        /*
         * cherche et initialise la position des particules importantes dans la désintégration, la taille de l'ArrayList ne changeant pas.
         */
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            if (atome.getA() == 4 & atome.getZ() == 2) {
                posHel = i;
            } else if (atome.getA() == 0 & atome.getZ() == 1) {
                posProt = i;
            } else if (atome.getA() == 0 & atome.getZ() == -1) {
                posElec = i;
            }
        }
    }

    /*
     * Implémentation Boutons
     */
    public static void testButton() {
        At test = getElemListeElem(0);
        test.setnom("coucou");
        setElemListeElem(0, test);
        test.toPrint();
        At newat = test;
        ListeElem.add(newat);
        majTabElem();
        tabToPrint(tabElem);
        InterfGraph.setjTable1();
        jtabToPrint(InterfGraph.getjTable1());
        InterfGraph.refreshTab();
    }

    public static void stopButton() {
        if (timer.isRunning()) {
            timer.stop();
        }
        setfinSim(true);
        setStartSim(false);
    }

    public static void pauseButton() {
        if (timer.isRunning() & getStartSim()) {
            timer.stop();
        }

        else if (timer.isRunning() == false & getStartSim()) {
            timer.start();
        } else {
        }

    }

    public static void playButton() {
        if (getStartSim() == false & getfinSim() == false) {
            timer.start();
            setStartSim(true);
        } else {
        }

    }
    /*
     * Getters et setters
     */
    public static ArrayList getList() {
        return ListeElem;
    }

    public static void setList(ArrayList L) {
        ListeElem = L;
    }

    public static void setElemListeElem(int i, At at) {
        ListeElem.set(i, at);
    }

    public static At getElemListeElem(int i) {
        At at = ListeElem.get(i);
        return at;
    }

    /*    public static JTable getjtabElem() {
        return jtabElem;
    }

 public void setjtabElem(JTable jt) {
        jtabElem = jt;
    }
*/
    public static Object[][] gettabElem() {
        return tabElem;
    }

    public static void settabElem(Object[][] t) {
        tabElem = t;
    }

    public static boolean getStartSim() {
        return StartSim;
    }

    public static void setStartSim(boolean S) {
        StartSim = S;
    }

    public static boolean getfinSim() {
        return finSim;
    }

    public static void setfinSim(boolean f) {
        finSim = f;
    }

    public static int getT() {
        return T;
    }

    public static void setT(int t) {
        T = t;
    }

    public static double getdelay() {
        return delay;
    }

    public static void setdelay(double d) {
        delay = d;
    }

    public static int searchAfficheTrue() {
        int pos = -1;
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            if (atome.getaffiche() == true) {
                pos = i;
            }
        }
        return pos;
    }

    public static void majTabElem() {
        tabElem = fillData();
    }

    public static void tabToPrint(Object[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void jtabToPrint(JTable tab) {

        for (int i = 0; i < tab.getRowCount(); i++) {
            for (int j = 0; j < tab.getColumnCount(); j++) {
                System.out.print(tab.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("c'était un jtab");
    }
}


