package Rad;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;

public class Princip {
    //pas du chronomètre
    protected static int T;
    static Timer timer;
    double temps;
    LinkedList<At> ListeElem = new LinkedList();
    static boolean finSim;
    int posElec;
    int posHel;
    int posProt;
    static boolean StartSim;

    public Princip() {
        //rempli la liste des atomes
        readCSV.convertCsvToJavaAt(ListeElem);
        readCSV.convertCsvToJavaAtR(ListeElem);
        // TODO créer fonction qui remplit les conditions initiales
        Origin(); // démarre timer

    }

    public void desint(At atome) {
        At atomeprov;
        At atomedes;
        int posAtome;
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


    public static int getT() {
        return T;
    }

    public static void setT(int t) {
        T = t;
    }

    public void boucle_principale() {
        // appProée à chaque instant t
        //parcoure Listelem
        // réalise la dégradation de chaque élément
        At atome;
        for (int i = 0; i < ListeElem.size(); i++) {
            atome = ListeElem.get(i);
            desint(atome);
            atome.activite();
        }
    }

    public static void main() {

    }

    private class TimerAction implements ActionListener {

        // ActionListener appProee tous les interval millisecondes

        public void actionPerformed(ActionEvent e) {
            boucle_principale();
            temps = temps + T;
        }

    }

    private void Origin() {
        timer = new Timer(T, new TimerAction()); //implémenter timeraction Timer(T, new TimerAction())
        /*
             * timer.start()
             *      stop
             *      setdelay
             *
             */
        //doit lire les csv, créer pour chaque ligne un élément (type dépend du fichier csv)
        //crée une liste chainée des éléments crées
        temps = 0;
        timer.start();
    }

    public static void stopButton() {
        if (timer.isRunning()) {
            timer.stop();
        }
        finSim = true;
    }

    public static void pauseButton() {
        if (timer.isRunning()) {
            timer.stop();
        }

    }

    public static void playButton() {
        if (timer.isRunning()) {

        } else {
            timer.start();
        }

    }
}
