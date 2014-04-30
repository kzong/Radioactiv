package Rad;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.Timer;
/*
 * CREDITS
 * getters et setters de At par Kexin Zong
 * Objet At et base de donn�e des atomes par Daphn� Guibert
 * Jmathplot, courbes etc... par Alexandre Lamartine
 * Reste par Enzo Vironda
 * ReadCSV inspir� de Nagesh Chauhan @http://www.beingjavaguys.com/2013/09/read-and-parse-csv-file-in-java.html
 * Relecture, optimisation, correction par tous
 */

public class Princip {
    
    //pas du chronom�tre
    protected static int T;
    static Timer timer;
    protected double temps;
    protected static ArrayList<At> ListeElem =
        new ArrayList(); //passage d'une lined list � Array list car Linkedlist.get o(n) et ArrayList.get o(1) d'o� une surchage de parcours
    protected static JTable jtabElem;
    protected static Object[][] tabElem;
    protected static boolean finSim=false;
    protected int posElec;
    protected int posHel;
    protected int posProt;
    protected static boolean StartSim=false;
    InterfGraph fen;
   

    public Princip() {
        //rempli la liste des atomes
        Origin(); // met en place timer
        // TODO cr�er fonction qui remplit les conditions initiales: a priori, tableau �ditable avant le play
        

    }

    public void desint(At atome) {
        /*
        *Prend un atome et en réalise la désintégration :
        détemine en quel élément il se désintègre selon le type de désintégration
        parcourt la liste pour trouver l'atome crée, met à jours les populations
        Si une particule est émise, sa position est connue et sa population mise à jour.
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
                        atome.setpop2((int) (atome.popIni * Math.exp(atome.getdVie() * temps))); // loi decroissance radio
                        atomedes.setpop1(atome.pop2);
                        des = Math.abs(atome.getpop2() - atome.getpop1());
                        atomedes.setpop2(des);
                        ListeElem.set(i, atomedes);
                        At He = ListeElem.get(posHel); // trouve �l�m Helium, pop1 pop pr�c�dente, pop2 nouvelle pop
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
                        At El = ListeElem.get(posElec); // trouve �l�m Elec, pop1 pop pr�c�dente, pop2 nouvelle pop
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
                        At Pro = ListeElem.get(posProt); // trouve �l�m Proton, pop1 pop pr�c�dente, pop2 nouvelle pop
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
       // readCSV.convertCsvToJavaAt(ListeElem);
        readCSV.convertCsvToJavaAt2(ListeElem);
        tabElem=fillData();
        jtabElem=new JTable(tabElem,MyTableModel_1.getColumnNames());
        fen=new InterfGraph();
        fen.setjTable1(jtabElem);
        
        fen.setVisible(true);
        fen.setResizable(false);
        fen.repaint();
        timer = new Timer(T, new TimerAction()); //impl�menter timeraction Timer(T, new TimerAction())
        temps = 0;
        
        
        
    }
    
    public void boucle_principale() {
        // appPro�e � chaque instant t
        //parcoure Listelem
        // r�alise la d�gradation de chaque �l�ment
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            desint(atome);
            atome.activite();
        }
        tabElem=fillData();
        jtabElem=new JTable(tabElem,MyTableModel_1.getColumnNames());
        fen.setjTable1(jtabElem);
        fen.repaint();
        
    }

    public static void main(String[] args) {
        Princip Rad= new Princip();
    }

    private class TimerAction implements ActionListener {

        // ActionListener appProee tous les interval millisecondes

        public void actionPerformed(ActionEvent e) {
            boucle_principale();
            temps = temps + T;
        }

    }

  
    
    public static Object[][] fillData(){
        ArrayList<At> Liste=Princip.getList();
        int numCol=11;
        int numLign=Liste.size();
        Object[][] data=new Object[numLign][numCol];
        for (int i = 0; i < numLign; i++) {
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
    
    private void searchPosParticule(){
        /*
         * cherche et initialise la position des particules importantes dans la d�sint�gration, la taille de l'ArrayList ne changeant pas.
         */
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            if(atome.getA()==4 & atome.getZ()==2){posHel=i;}
            else if(atome.getA()==0 & atome.getZ()==1){posProt=i;}
            else if(atome.getA()==0 & atome.getZ()==-1){posElec=i;}
        }
    }
    
    /*
     * Impl�mentation Boutons
     */

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
        
        else if (timer.isRunning()==false & getStartSim()) {
            timer.start();
        }
        else{}

    }

    public static void playButton() {
        if (timer.isRunning()==false & getStartSim()==false & getfinSim()==false) {
            timer.start();
            setStartSim(true);
        } else {}

    }
    /*
     * Getters et setters
     */
    public static ArrayList getList() {
        return ListeElem;
    }
    
    public static void setList(ArrayList L) {
        ListeElem=L;
    }
    
    public static void setElemListeElem(int i,At at){
        ListeElem.set(i, at);
    }
    
    public static At getElemListeElem(int i){
        At at=ListeElem.get(i);
       return at;
    }
    
    public static JTable getjtabElem() {
        return jtabElem;
    }
    
    public void setjtabElem(JTable jt) {
        jtabElem=jt;
    }
    
    public static Object[][] gettabElem() {
        return tabElem;
    }
    
    public static void settabElem(Object[][] t) {
        tabElem=t;
    }
    
    public static boolean getStartSim(){
        return StartSim;
    }
    
    public static void setStartSim(boolean S){
        StartSim=S;
    }
    
    public static boolean getfinSim(){
        return finSim;
    }
    
    public static void setfinSim(boolean f){
        finSim=f;
    }
    
    public static int getT() {
        return T;
    }

    public static void setT(int t) {
        T = t;
    }
    
    public static int searchAfficheTrue(){
        int pos=-1;
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            if(atome.getaffiche()==true){pos=i;}
        }
        return pos;
    }
    
    public static void majTabElem(){
        tabElem=fillData();
    }
}


