package Rad;

/*TODO
 * Gestion des modifications pour le tableau : affichage et popIni
 * /////
 * Graphiques: buffer ?
 * Graphiques: data de traçage :arraylist ou linkedlist des coordonnées, attribut de At: ArrayList<double[2]> graph
 * getters et setters, fonction addPoint(pop2) qui prend pop2 et temps, les met dans un tableau et l'ajoute
 * La fonction de traçage utilisera searchAfficheTrue() qui renvoie la position de l'élément à afficher, et utilisera
 * /////
 * Amélioration du Design
 * affichage et stockagetemps: pb avec millisecondes
 *(fait) Affichage du temps: 2 jtextfields: 1 valeur 2 unité
 *(fait) changement de format selon temps
 *(à re tester de plus près) URGENT : problème d'arrondis dans desint, d'où une population croissante d'un atome radioactif qui ne peut que se désintégrer
 * GAMMA !
 */

/*DONE
 * Se passer du tableau intermédiaire : correspondance directe Jtab et liste
 *
 *
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/*CREDITS
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
    protected static double temps; //temps écoulé
    protected static ArrayList<At> ListeElem =
        new ArrayList(); //passage d'une linked list à Array list car Linkedlist.get o(n) et ArrayList.get o(1) d'où une surchage de parcours


    //position dans les diverses tables des 3 particules les plus utilisées, afin de ne pas avoir à les rechercher à chaque fois.
    protected int posElec;
    protected int posHel;
    protected int posProt;
    //booléennes qui indiquent l'état du programme
    protected static boolean startSim = false; //false= attend conditions initiales
    protected static boolean finSim = false; //true= appuyé sur fin ou pop nulle pour tous les atomes radio
    protected static boolean finInit = false;
    static InterfGraph fen;
    public static Princip Rad;


    public Princip() {

        //rempli la liste des atomes
        Origin(); // met en place timer

        // TODO créer fonction qui remplit les conditions initiales: a priori, tableau éditable avant le play
    }

    public void desintAt(At atome) {

        /*Prend un atome et en réalise la désintégration :
        détemine en quel élément il se désintégre selon le type de désintégration
        parcourt la liste pour trouver l'atome crée, met à  jour les populations
        Si une particule est émise, sa position est connue et sa population mise Ã  jour.
        */
        At atomeprov; //atome considéré
        At atomedes; //atome fruit de la désintégration
        int posAtome; //position
        int des = 0; //population desintégrée


        switch (atome.gettype()) {
        case 0:
            break;
        case 1:
            { //alpha
                for (int i = 0; i < ListeElem.size(); i++) {
                    atomeprov = ListeElem.get(i);
                    if (atomeprov.getA() == (atome.getA() - 4) & atomeprov.getZ() == (atome.getZ() - 2)) { //trouve le nouvel élément
                        atomedes = atomeprov;
                        //atome.setpop1(atome.pop2);  //pop1=pop2
                        int newPop = ((int) Math.ceil((atome.getpopIni() * Math.exp(-(temps / atome.getdVie()))))); // loi decroissance radio N(t)=No*Exp(-At) A=1/dVie
                        atome.setajoutPop(newPop - atome.getpop1());


                        atomedes.setajoutPop(atomedes.getajoutPop() - atome.getajoutPop());
                        ListeElem.set(i, atomedes);
                        At He = ListeElem.get(posHel); // trouve élém Helium, pop1 pop précédente, pop2 nouvelle pop
                        He.setajoutPop(He.getajoutPop() - atome.getajoutPop());
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
                        //atome.setpop1(atome.pop2);  //pop1=pop2
                        int newPop =
                            ((int) Math.ceil((atome.getpopIni() *
                                              Math.exp(-(temps /
                                                         atome.getdVie()))))); // loi decroissance radio N(t)=No*Exp(-At) A=1/dVie
                        atome.setajoutPop(newPop - atome.getpop1());


                        atomedes.setajoutPop(atomedes.getajoutPop() - atome.getajoutPop());
                        ListeElem.set(i, atomedes);
                        At Elec = ListeElem.get(posElec); // trouve élém Electron
                        Elec.setajoutPop(Elec.getajoutPop() - atome.getajoutPop());
                        ListeElem.set(posElec, Elec);
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
                        //atome.setpop1(atome.pop2);  //pop1=pop2
                        int newPop =
                            ((int) Math.ceil((atome.getpopIni() *
                                              Math.exp(-(temps /
                                                         atome.getdVie()))))); // loi decroissance radio N(t)=No*Exp(-At) A=1/dVie
                        atome.setajoutPop(newPop - atome.getpop1());


                        atomedes.setajoutPop(atomedes.getajoutPop() - atome.getajoutPop());
                        ListeElem.set(i, atomedes);
                        At Proton = ListeElem.get(posProt); // trouve élém Proton
                        Proton.setajoutPop(Proton.getajoutPop() - atome.getajoutPop());
                        ListeElem.set(posProt, Proton);
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

    public void desintAtOld(At atome) {

        /*Prend un atome et en réalise la désintégration :
        détemine en quel élément il se désintégre selon le type de désintégration
        parcourt la liste pour trouver l'atome crée, met à  jour les populations
        Si une particule est émise, sa position est connue et sa population mise Ã  jour.
        */
        At atomeprov; //atome considéré
        At atomedes; //atome fruit de la désintégration
        int posAtome; //position
        int des = 0; //population desintégrée


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
                        atome.setpop2((int) Math.ceil((atome.popIni *
                                                       Math.exp(-(temps /
                                                                  atome.getdVie()))))); // loi decroissance radio N(t)=No*Exp(-At)

                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop1(atomedes.pop2);
                        atomedes.setpop2(atomedes.getpop1() + des);
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
                        atome.setpop2((int) Math.ceil((atome.popIni *
                                                       Math.exp(-(temps /
                                                                  atome.getdVie()))))); // loi decroissance radio N(t)=No*Exp(-At)

                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop1(atomedes.pop2);
                        atomedes.setpop2(atomedes.getpop1() + des);
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
                        atome.setpop2((int) Math.ceil((atome.popIni *
                                                       Math.exp(-(temps /
                                                                  atome.getdVie()))))); // loi decroissance radio N(t)=No*Exp(-At)

                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop1(atomedes.pop2);
                        atomedes.setpop2(atomedes.getpop1() + des);
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

    public void desint() {
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            desintAt(atome);
            ListeElem.set(i, atome);
        }
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            atome.setpop2(atome.getpop1() + atome.getajoutPop());
            atome.activite();
            atome.setajoutPop(0);
            atome.addPointAct();
            atome.setpop1(atome.getpop2());
            atome.addPointPop();
            atome.setpop2(0);
            ListeElem.set(i, atome);
        }
    }

    private void Origin() {
        /*
         *Lis CSV, Crée liste, Crée tableau, le  remplit, crée le jTable, crée l'interface et la rend visible
         */
        // readCSV.convertCsvToJavaAt(ListeElem);
        readCSV.convertCsvToJavaAt2(ListeElem);
        searchPosParticule();

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
        InterfGraph.setjTable1();
        setfinInit(true);

    }

    public void testTimer() {
        At test = getElemListeElem(0);
        test.setA(test.getA() + 1);
        setElemListeElem(0, test);
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
        desint();
        displayGraph();
        InterfGraph.setjTable1();
        InterfGraph.refreshTab();
        fen.repaint();

    }

    public static void main(String[] args) {
        Rad = new Princip();
    }


    private class TimerAction implements ActionListener {

        // ActionListener appProee tous les interval millisecondes

        public void actionPerformed(ActionEvent e) {
            boucle_principale();
            //testTimer();
            temps = temps + getdelay();
            afficheTemps();
        }

    }

    public static Object[][] listTo2dTab() {
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
            data[i][6] = secToTime(atome.getdVie());
            data[i][7] = typeintToTypeString(atome.gettype());
            data[i][8] = atome.getpopIni();
            data[i][9] = atome.getpop1();
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
        /*At test = getElemListeElem(0);
        test.setnom("coucou");
        setElemListeElem(0, test);
        test.toPrint();
        At newat = test;
        ListeElem.add(newat);
        InterfGraph.setjTable1();
        */

        displayGraph();
    }

    public static void resetButton() {
        fen.dispose();
        stopButton();

        Rad = new Princip();
        InterfGraph.setjTable1();
    }

    public static void stopButton() {
        if (timer.isRunning()) {
            timer.stop();
        }
        setfinSim(true);
        setstartSim(false);
    }

    public static void pauseButton() {
        if (timer.isRunning() & getstartSim()) {
            timer.stop();
        }

        else if (timer.isRunning() == false & getstartSim()) {
            timer.start();
        } else {
        }

    }

    public static void playButton() {
        if (getstartSim() == false & getfinSim() == false) {
            timer.start();
            setstartSim(true);
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

    public static boolean getstartSim() {
        return startSim;
    }

    public static void setstartSim(boolean S) {
        startSim = S;
    }

    public static boolean getfinSim() {
        return finSim;
    }

    public static void setfinSim(boolean f) {
        finSim = f;
    }

    public static boolean getfinInit() {
        return finInit;
    }

    public static void setfinInit(boolean S) {
        finInit = S;
    }

    public static int getT() {
        return T;
    }

    public static void setT(int t) {
        T = t;
    }

    public static double gettemps() {
        return temps;
    }

    public void settemps(double t) {
        temps = t;
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

    public void afficheTemps() {
        //nécessité de double car valmax int (2 octet) est 2147483647 et que l'on travaille en plusieurs miliards d'années
        double c;
        double prov = temps;
        double an = (Math.floor(prov / (365 * 24 * 60)));
        prov = prov - an * 365 * 24 * 60;
        double jours = (Math.floor(prov / (24 * 60)));
        ;
        prov = prov - jours * 24 * 60;
        double min = (Math.floor(prov / (60)));
        ;
        prov = prov - min * 60;
        double sec = (Math.floor(prov));


        InterfGraph.textsetjTextField1(an + " années " + jours + " jours " + min + " minutes " + sec + " secondes");
    }

    public static String secToTime(double s) {
        //nécessité de double car valmax int (2 octet) est 2147483647 et que l'on travaille en plusieurs miliards d'années
        String S = "";
        double prov = s;
        double an = (Math.floor(prov / (365 * 24 * 60)));
        prov = prov - an * 365 * 24 * 60;
        double jours = (Math.floor(prov / (24 * 60)));
        ;
        prov = prov - jours * 24 * 60;
        double min = (Math.floor(prov / (60)));
        ;
        prov = prov - min * 60;
        double sec = (Math.floor(prov));
        if (an != 0) {
            S = S + an + " an ";
        }
        if (jours != 0) {
            S = S + jours + " jours ";
        }
        if (min != 0) {
            S = S + min + " min ";
        }
        if (sec != 0) {
            S = S + sec + " sec ";
        }
        return S;
    }

    public static String typeintToTypeString(int t) {
        String Type = "";
        switch (t) {
        case 0:
            Type = "Stable";
            break;
        case 1:
            Type = "Alpha";
            break;
        case 2:
            Type = "Beta-";
            break;
        case 3:
            Type = "Beta+";
            break;
        case 4:
            Type = "gamma";
            break;
        }
        return Type;
    }

    public static void displayGraph() {
        int pos = searchAfficheTrue();
        if (pos != -1) {
            At atome = ListeElem.get(pos);
            fen.setjPPop(atome.getnom(), atome.tabXPop(), atome.tabYPop());
            fen.setjPAct(atome.getnom(), atome.tabXAct(), atome.tabYAct());
            fen.revalidate();
        }
    }


    //fonctions de test de bon remplissage des tableaux
    public static void listToPrint() {
        for (int i = 0; i < ListeElem.size(); i++) {
            ListeElem.get(i).toPrint();
        }

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
                System.out.print(tab.getValueAt(i, 0));
            }
            System.out.println();
        }
        System.out.println("c'était un jtab");
    }
    
    public static int countAfficheTrue(){
        int count=0;
        for (int i = 0; i < ListeElem.size(); i++) {
            if(ListeElem.get(i).getaffiche()==true){count++;}
        }
        return count;
    }
    
    public static int countAfficheTruejT(){
        int count=0;
            for (int i = 0; i < InterfGraph.getjTable1().getRowCount(); i++) {
            if(InterfGraph.getjTable1().getValueAt(i, 0)==true){count++;}
        }
        return count;
    }
}


