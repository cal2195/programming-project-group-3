/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programmingproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cal
 */
public class PreprocessData
{

    String dataFile = "";
    ArrayList<String> medallions = new ArrayList<>();
    ArrayList<String> hacks = new ArrayList<>();
    PrintWriter out;

    public PreprocessData(String file)
    {
        dataFile = file;
        try
        {
            out = new PrintWriter("data.csv");
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(PreprocessData.class.getName()).log(Level.SEVERE, null, ex);
        }
        processData();
        writeAuxDataFiles();
    }

    public void processData()
    {
        BufferedReader buff = null;
        try
        {
            buff = new BufferedReader(new FileReader(dataFile));
            String current = "";
            int count = 0;
            buff.readLine(); //Skip the headers!
            while ((current = buff.readLine()) != null)
            {
                count++;
                if (count % 10000 == 0)
                {
                    System.out.println("Loading Line " + count + "...");
                }
                String[] currentLine = current.split(",");
                boolean found = false;
                for (int i = 0; i < medallions.size(); i++)
                {
                    if (medallions.get(i).equals(currentLine[0]))
                    {
                        currentLine[0] = "" + i;
                        found = true;
                    }
                }
                if (!found)
                {
                    medallions.add(currentLine[0]);
                    currentLine[0] = "" + medallions.size();
                }
                found = false;
                for (int i = 0; i < hacks.size(); i++)
                {
                    if (hacks.get(i).equals(currentLine[1]))
                    {
                        currentLine[1] = "" + i;
                        found = true;
                    }
                }
                if (!found)
                {
                    hacks.add(currentLine[1]);
                    currentLine[1] = "" + hacks.size();
                }
                currentLine[5] = "" + DateTime.dateTimeToSecs(currentLine[5]);

                appendData(currentLine);
            }
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                buff.close();
                out.close();
            } catch (IOException ex)
            {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeAuxDataFiles()
    {
        try
        {
            PrintWriter newData = new PrintWriter("meds.txt");
            for (String med : medallions)
            {
                newData.println(med);
            }
            newData.close();
            newData = new PrintWriter("hacks.txt");
            for (String hack : hacks)
            {
                newData.println(hack);
            }
            newData.close();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(PreprocessData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void appendData(String[] data)
    {
        String line = "";
        for (int i = 0; i < data.length; i++)
        {
            if (i != 6)
            {
                line += (i == 0 ? "" : ",") + data[i];
            }
        }
        out.println(line);
    }
}
