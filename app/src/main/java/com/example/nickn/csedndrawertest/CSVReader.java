package com.example.nickn.csedndrawertest;

import java.io.*;
import java.util.ArrayList;

/**
 * A class that reads from .csv files.
 * @author ajb258
 * @version 1.0
 * @release N/A
 */

public class CSVReader
{
    String path = "/sdcard/CSV/";

    /*TODO: implement readers for the users.csv file*/
    String csvFile = path + "entries.csv";
    String line = "";
    String csvSplit = ",";

    /**
     * Default constructor.
     */

    public CSVReader()
    {

    }


    /**
     *
     * @param toRead the path of the file to be read
     * @returnan arraylist of all entries contained in the file
     */
    public ArrayList<Entry> readEntries(String toRead)
    {

        ArrayList<Entry> allEntries = new ArrayList<Entry>();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(toRead));
            int iteration = 0;
            while ((line = br.readLine()) != null)
            {
                // If statement prevents headers of csv file from being displayed.
                if (iteration == 0)
                {
                    iteration++;
                }
                else
                {
                    String[] fields = line.split(csvSplit);
                    int entryType = Integer.parseInt(fields[0]);
                    Entry currentEntry = new Entry(entryType,fields[1],fields[2]);
                    allEntries.add(currentEntry);
                }
            }

            br.close();
            return allEntries;


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    /**
     *
     * @param field: the name of the field to be searched for
     * @param value: the value of the field to be searched for
     * @return: an arraylist of all entries which contain the correct values for the field
     */
    public ArrayList<Entry> SearchFor(String field, String value){
        ArrayList<Entry> allFound = new ArrayList<Entry>();
        ArrayList<Entry> allEntries = readEntries(path + "entries.csv");
        for(int i = 0; i<allEntries.size();i++){
            Entry current = allEntries.get(i);
            switch(field){
                case "TYPE" :
                    Integer Type = current.getType();
                    if(Type.toString().equals(value)){
                        allFound.add(current);
                    }
                    break;
                case "VALUE" :
                    if(current.getValue().equals(value)){
                        allFound.add(current);
                    }
                    break;
                case "DATE" :
                    if(current.getDate().equals(value)){
                        allFound.add(current);
                    }
                    break;
            }

        }

        return allFound;
    }

    public User readUsers(String toRead)
{

    User currentUser = new User();


    try
    {
        BufferedReader br = new BufferedReader(new FileReader(toRead));
        int iteration = 0;
        while ((line = br.readLine()) != null)
        {
            // If statement prevents headers of csv file from being displayed.
            if (iteration == 0)
            {
                iteration++;
            }
            else if(!line.isEmpty())
            {
                String[] fields = line.split(csvSplit);
                int userHeight = Integer.parseInt(fields[2]);
                int userWeight = Integer.parseInt(fields[3]);
                int targetWeight = Integer.parseInt(fields[5]);
                currentUser = new User(fields[0],userHeight, fields[4],userWeight, fields[1], targetWeight);
            }
        }

        br.close();
        return currentUser;


    }
    catch (FileNotFoundException e)
    {
        e.printStackTrace();
        return null;
    }
    catch (IOException e)
    {
        e.printStackTrace();
        return null;
    }
}

    public ArrayList<Weight> readWeight(String toRead)
    {
        ArrayList<Weight> allWeights = new ArrayList<Weight>();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(toRead));
            int iteration = 0;
            while ((line = br.readLine()) != null)
            {
                // If statement prevents headers of csv file from being displayed.
                if (iteration == 0)
                {
                    iteration++;
                }
                else
                {
                    String[] fields = line.split(csvSplit);
                    int weight = Integer.parseInt(fields[0]);

                    allWeights.add(new Weight(weight, fields[1]));
                }
            }

            br.close();
            return allWeights;


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public int weightAtDate(String toRead, String Date){
        ArrayList<Weight> allWeights = readWeight(toRead);
        int weight = 0;
        for (int i=0; i<allWeights.size(); i++){
            if (allWeights.get(i).getDate().equals(Date)) {
                weight = allWeights.get(i).getWeight();
            }
        }
        return weight;
    }


    //used for reading meal or exercise
    public ArrayList<Meal> readMeals(String toRead)
    {
        ArrayList<Meal> allMeals = new ArrayList<Meal>();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(toRead));
            int iteration = 0;
            while ((line = br.readLine()) != null)
            {
                // If statement prevents headers of csv file from being displayed.
                if (iteration == 0)
                {
                    iteration++;
                }
                else
                {
                    String[] fields = line.split(csvSplit);
                    int calories = Integer.parseInt(fields[1]);

                    allMeals.add(new Meal(fields[0], calories));
                }
            }

            br.close();
            return allMeals;


        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args)
    {
        new CSVReader();
    }
}
