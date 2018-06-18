import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Desktop;
import java.net.*;
import java.io.IOException;
import java.io.File;

/**
 * Creates methods to utilize external files.
 *
 * @author Beth Fineberg
 * @version 1.0
 */
public class WordProcessor
{
    //array to be loaded with words in file
    ArrayList<String> words = new ArrayList<String>();
    
    boolean isURL = false;
    
    String[] happy = {"happy", "excitement", "excited", "alive", "joy", "win", "laugh", "joyful",
        "good", "great", "wonderful", "best", "sunny", "laughing", "love", "like", "enjoy", "enjoyed",
        "full", "pretty", "beautiful", "peace", "pleasure", "relaxed", "better", "cure", "recover",
        "succeed", "create", "fix", "repair", "hug"};
    String[] sad = {"sad", "angry", "dead", "death", "died", "loss", "deadly", "bad", "mad", "hurt",
        "loss", "lose", "cry", "cried", "crying", "worst", "sucks", "terrible", "hate", "despise",
        "empty", "depressed", "ugly", "war", "pain", "tense", "tension", "kill", "killed", "worse",
        "dark", "hurt", "fail", "suicide", "destroy", "break", "punch"};
        
    double happyCount = 0;
    double sadCount = 0;

    double emotionPercent;
    
    /**
     * Reads external file and adds words to an ArrayList.
     * 
     * @param fileName name of external file
     */
    public void read(String fileName)
    {
        try
        {
            Scanner scan = new Scanner(new FileReader(fileName));
            
            while(scan.hasNext())
            {
                String currentWord = scan.next();
                
                //checks if string is a URL or link
                if(currentWord.contains("www.") || currentWord.contains("bit.ly") || currentWord.contains("http") 
                    || currentWord.contains(".com") || currentWord.contains(".co"))
                {
                    //when true, word isn't added
                    isURL = true;
                }
                
                //replaces all white space in the read in word
                currentWord = currentWord.replaceAll("\\s+", "");
                //removes all non-letter character in wordi
                currentWord = currentWord.replaceAll("[^a-zA-Z]", "");
                
                //replaces extraneous words found typically in Trump's Tweets
                currentWord = currentWord.replaceAll("realDonaldTrump", "");
                currentWord = currentWord.replaceAll("TextDateFavoritesRetweetsTweet", "");
                currentWord = currentWord.replaceAll("ID", "");
                
                //if string isn't empty and is a read word, it's added to an ArrayList
                if(currentWord.length() > 0 && !isURL) words.add(currentWord);
                
                //makes it so the next word won't be counted as a URL if it is not
                isURL = false;
            }
            scan.close();
        }
        catch (FileNotFoundException e)
        {
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Get Three returns 3 random words from the file in a string.
     * 
     * @return String of 3 words
     */
    public String getThree()
    {
        String word1 = words.get(randomIndex());
        String word2 = words.get(randomIndex());
        String word3 = words.get(randomIndex());

        return word1 + " " + word2 + " " + word3;
    }

    /**
     * Random Index gets a random index within the length of the ArrayList.
     * 
     * @return int random number
     */
    public int randomIndex()
    {
        return ((int)(Math.random() * (words.size() - 1)));
    }

    /**
     * Google Search takes in 11 strings (maximum) and adds them to an ArrayList.
     * The contents of the ArrayList are then added to a String with the format
     * in order to make a Google Search using the I'm Feeling Lucky feature.
     * 
     * @param String a, b, c, d, e, f, g, h, i, j, k words to google
     */
    public void googleSearch(String a, String b, String c, String d, String e, String f,
    String g, String h, String i, String j, String k)
    {
        Desktop desktop = Desktop.getDesktop();
        String add = "";

        ArrayList<String> terms = new ArrayList<String>();
        terms.add(a);
        terms.add(b);
        terms.add(c);
        terms.add(d);
        terms.add(e);
        terms.add(f);
        terms.add(g);
        terms.add(h);
        terms.add(i);
        terms.add(j);
        terms.add(k);

        //adds content of ArrayList in order to work for google
        for (int q = terms.size() - 1; q > 0; q--)
        {
            //makes sure string isn't empty
            if(!terms.get(q).equals(""))
            {
                add += terms.get(q) + "%20";
            }
        }

        try
        {
            URI myNewLocation = new URI("http://www.google.com/search?q=" 
                    + add + "&btnI");
            desktop.browse(myNewLocation);
        }
        catch (IOException ioe) 
        {
        }
        catch(URISyntaxException urie)
        {
        }
    }

    /**
     * getWords returns the ArrayList of read words from external file.
     * 
     * @return ArrayList of words
     */
    public ArrayList<String> getWords()
    {
        return words;
    }
    
    /**
     * getWord returns a word from the certain index of the ArrayList
     * 
     * @param int l the index wanted
     * @return String the word at the desired index
     */
    public String getWord(int l)
    {
        return words.get(l);
    }

    /**
     * clear removes all contents of the ArrayList. The counters for the emotions
     * are reset to zero.
     */
    public void clear()
    {
        words.clear();
        happyCount = 0;
        sadCount = 0;
    }
    
    /**
     * getMood goes through the ArrayList of words from the file and the emotional
     * words in the Arrays. The amount of the same words are counted and that number
     * is divided by the total number of words in the file. That is turned into a
     * percentage and given back to the user in a String.
     * 
     * @return String explaining what percentage of the text is a certain mood
     */
    public String getMood()
    {
        for(String m: words)
        {
            for(int i = 0; i < happy.length; i++)
            {
                if(m.toLowerCase().equals(happy[i]))
                {
                    happyCount++;
                }
            }
            for(int i = 0; i < sad.length; i++)
            {
                if(m.toLowerCase().equals(sad[i]))
                {
                    sadCount++;
                }
            }
        }
        
        //returns a String based upon the mood
        if (happyCount > sadCount)
        {
            emotionPercent = (happyCount/words.size()) * 100;
            return emotionPercent + "% Happy";
        }
        else if(happyCount < sadCount)
        {
            emotionPercent = (sadCount/words.size()) * 100;
            return emotionPercent + "% Sad";
        }
        else if(happyCount == 0 && sadCount == 0)
        {
            return "Neutral";
        }
        else
        {
            return "error";
        }
    }
}