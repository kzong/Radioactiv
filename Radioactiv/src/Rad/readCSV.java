package Rad;
//change2
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
//test

/**
 * inspired from Nagesh Chauhan
 *
 */
public class readCSV {

    
    public static void convertCsvToJavaAt2(ArrayList ListeElem) {
        String csvFileToRead = "csvFiles/Elements.csv";
        BufferedReader br = null;
        String line = "";
        String splitBy = "/";
        // doit utiliser une liste déjà crée


        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {

                // split on comma(',')
                String[] elements = line.split(splitBy);

                // create  object to store values
                At atRObjet = new At();

                // add values from csv to ATR object
                atRObjet.setnom(elements[0]);
                atRObjet.setabr(elements[1]);
                atRObjet.setZ(Integer.parseInt(elements[2]));
                atRObjet.setN(Integer.parseInt(elements[3]));
                atRObjet.setA(Integer.parseInt(elements[4]));
                String type = elements[5];
                switch (type) {
                case "Alpha":
                    {
                        atRObjet.settype(1);
                    }
                    break;
                case "Beta-":
                    {
                        atRObjet.settype(2);
                    }
                    break;
                case "Beta+":
                    {
                        atRObjet.settype(3);
                    }
                    break;
                case "Gamma":
                    {
                        atRObjet.settype(4);
                    }
                    break;
                case "":
                    {
                        atRObjet.settype(0);
                    }
                    break;
                case "STABLE":
                    {
                        atRObjet.settype(0);
                    }
                    break;

                }
                String vie= elements[6];
                atRObjet.setdVie(Double.parseDouble(vie));
                atRObjet.setaffiche(false);
                atRObjet.setpopIni(30); //test
                //System.out.println(vie);
               // atRObjet.toPrint();


                // adding car objects to a list
                ListeElem.add(atRObjet);

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


