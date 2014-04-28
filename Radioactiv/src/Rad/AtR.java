package Rad;

import Rad.Princip.*;

public class AtR extends At{
    protected double dVie;
    double activite;
    
    
    public AtR() {
        super();
    }
  
    public double getdVie(){
       return dVie;
    }
    public  double getactivite(){
     return activite;
    }
    public void setdVie(double d){
     dVie=d;
    } 
    public void setactivite(double c){
     activite=c;
    } 
  
    public void activite(){
       double t=Princip.getT();
        activite=Math.abs(pop1-pop2)/(t);
    }
}
