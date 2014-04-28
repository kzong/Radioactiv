package Rad;


import java.util.LinkedList;
import java.util.List;

public class Princip {
    //pas du chronomètre
   protected static double T;
   
   public LinkedList<At> ListeElem = new LinkedList();
   
   public static double getT(){
       return T;
   }
   
   public static void setT(double t){
       T=t;
   }
    
    public static void main(){
        
    }
    
    public void Origin(){
        //doit lire les csv, créer pour chaque ligne un élément (type dépend du fichier csv)
        //crée une liste chainée des éléments crées
        readCSV.convertCsvToJavaAt(ListeElem);
        readCSV.convertCsvToJavaAtR(ListeElem);
    }



}
