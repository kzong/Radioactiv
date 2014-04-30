package Rad;

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

    public static void convertCsvToJavaAt(ArrayList ListeElem) {
        String csvFileToRead = "csvFiles/Elements.csv";
        BufferedReader br = null;
        String line = ";";
        String splitBy = ",";
        // doit utiliser une liste déjà crée


        try {

            br = new BufferedReader(new FileReader(csvFileToRead));
            while ((line = br.readLine()) != null) {

                // split on comma(',')
                String[] elements = line.split(splitBy);

                // create  object to store values
                At atObjet = new At();

                // add values from csv to At object
                //nom/abr/Z/N/type
                atObjet.setnom(elements[0]);
                atObjet.setabr(elements[1]);
                atObjet.setZ(Integer.parseInt(elements[2]));
                atObjet.setN(Integer.parseInt(elements[3]));
                atObjet.setA(Integer.parseInt(elements[3]) + Integer.parseInt(elements[2]));
                String type = elements[4];
                switch (type) {
                case "alpha":
                    {
                        atObjet.settype(1);
                    }
                    break;
                case "Beta -":
                    {
                        atObjet.settype(2);
                    }
                    break;
                case "Beta +":
                    {
                        atObjet.settype(3);
                    }
                    break;
                case "Gamma":
                    {
                        atObjet.settype(4);
                    }
                    break;
                case "":
                    {
                        atObjet.settype(0);
                    }
                    break;


                }


                // adding objects to a list
                ListeElem.add(atObjet);

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
                atRObjet.setdVie(Double.parseDouble(elements[6]));
                atRObjet.setaffiche(false);
                atRObjet.toPrint();


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


