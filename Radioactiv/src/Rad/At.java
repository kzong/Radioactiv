package Rad;
//change2
import java.util.ArrayList;
import java.util.LinkedList;
//test
public class At {
    protected String nom;
    protected String abr;
    protected int A;
    protected int Z;
    protected int N;
    protected int type;
    protected int pop2;
    protected int popAct;
    protected int ajoutPop;
    protected int pop1;
    protected int popIni;
    protected boolean affiche;
    protected double activite;
   
    ArrayList<double[]> graphPop = new ArrayList();
    ArrayList<double[]> graphAct = new ArrayList();

    protected double dVie;

    public At() {
        
        super();
        
    }

    public String getnom() {
        return nom;
    }

    public String getabr() {
        return abr;
    }

    public int getA() {
        return A;
    }

    public int getZ() {
        return Z;
    }

    public int getN() {
        return N;
    }

    public int gettype() {
        return type;
    }

    public int getpop2() {
        return pop2;
    }
    
    public int getpopAct() {
        return popAct;
    }
    
    public int getajoutPop() {
        return ajoutPop;
    }

    public int getpop1() {
        return pop1;
    }

    public int getpopIni() {
        return popIni;
    }

    public boolean getaffiche() {
        return affiche;
    }

    public void setnom(String s) {
        nom = s;
    }

    public void setabr(String t) {
        abr = t;
    }

    public void setA(int a) {
        A = a;
    }

    public void setnom(int z) {
        Z = z;
    }

    public void setN(int n) {
        N = n;
    }

    public void setZ(int z) {
        Z = z;
    }

    public void settype(int p) {
        type = p;
    }

    public void setpop2(int p2) {
        pop2 = p2;
    }
    
    public void setpopAct(int pa) {
        popAct = pa;
    }

    public void setajoutPop(int aj) {
        ajoutPop = aj;
    }

    public void setpop1(int p1) {
        pop1 = p1;
    }

    public void setpopIni(int pi) {
        popIni = pi;
    }

    public void setaffiche(boolean a) {
        affiche = a;
    }

    /*   public abstract double getdVie();

    public abstract void setdVie(double d);
*/

    public double getactivite() {
        return activite;
    }

    public void activite() {
        double t = Princip.gettemps()-Princip.getTempsPrec();
        
        if(ajoutPop==0 ){activite =0;} // sert � avoir un vrai 0
        else{activite = ((double)ajoutPop) / t;}
    }


    public double getdVie() {
        return dVie;
    }
    
   


    public void setdVie(double d) {
        dVie =d;
    }
    
    
    
    public void toPrint(){
       System.out.println(this.nom+" abr:"+this.abr+" A:"+this.A+" Type:"+this.type+" Demie Vie:"+this.dVie+" popIni"+this.popIni+"pop1 "+this.pop1+"pop2 "+this.pop2+ "pop2 "+this.popAct);
       
    }
    
    public void addPointPop(){
        double[] coord={ Princip.gettemps(),this.getpopAct()};
        this.graphPop.add(coord);
    }
    
    public void addPointAct(){
        double[] coord={ Princip.gettemps(),this.getactivite()};
        this.graphAct.add(coord);
    }
    
    public double[] tabXAct(){
        double[] tab= new double[graphAct.size()];                       
        for (int i = 0; i < graphAct.size(); i++) {
            tab[i]=graphAct.get(i)[0];
        }
        return tab;
    }
    
    public double[] tabYAct(){
        double[] tab1= new double[graphAct.size()];                       
        for (int i = 0; i < graphAct.size(); i++) {
            tab1[i]=graphAct.get(i)[1];
        }
        return tab1;
    }
    
    public double[] tabXPop(){
        double[] tab2= new double[graphPop.size()];                       
        for (int i = 0; i < graphPop.size(); i++) {
            tab2[i]=graphPop.get(i)[0];
        }
        return tab2;
    }
    
    public double[] tabYPop(){
        double[] tab3= new double[graphPop.size()];                       
        for (int i = 0; i < graphPop.size(); i++) {
            tab3[i]=graphPop.get(i)[1];
        }
        return tab3;
    }
    
}
