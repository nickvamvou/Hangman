import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Words {

    /**
     * Refers to the categories that the user can select
     */
    public static final int COUNTIES = 1;
    public static final int COUNTRIES = 2;
    public static final int CITIES = 3;


    /**
     * Refers to the word in each category that will be generated
     * as the targetWord
     */
    static String[] counties = { "Argyll and Bute", "Caithness",  "Kingdom of Fife",
            "East Lothian", "Highland", "Dumfries and Galloway",
            "Renfrewshire", "Scottish Borders", "Perth and Kinross" };
    static String[] countries = { "Scotland", "England", "Wales", "Northern Ireland", "Ireland",
            "France", "Germany", "Netherlands", "Spain", "Portugal",
            "Belgium", "Luxembourg", "Switzerland", "Italy", "Greece" };
    static String[] cities = { "St Andrews", "Edinburgh", "Glasgow", "Kirkcaldy", "Perth",
            "Dundee", "Stirling", "Inverness", "Aberdeen", "Falkirk" };

    static ArrayList<String> customwords;

    /**
     * Gets a random word based on the category provided
     * by the user
     * @param category integer representing the category supplied to the user
     * @throws CategoryNotFoundException when the category integer supplied by the user is not correct
     */
    public static String generateRandomWord(int category) throws CategoryNotFoundException  {
        if (category == 1)
            return counties[(int)(Math.random()*9)];
        else if (category == 2)
            return countries[(int)(Math.random()*15)];
        else if (category == 3) {
            return cities[(int) (Math.random() * 10)];
        }
        else {
            throw new CategoryNotFoundException();
        }
    }

    /**
     * Gets a random word based on the category provided
     * by the user
     * @param wordsource String that contains the path to the file
     * @return a random string that is parsed - from the file
     * @throws IOException When could not read the file
     * @throws IndexOutOfBoundsException When the file cannot be found based on the path
     */
    public static String randomWordBasedOnFile(String wordsource) throws IOException, FileNotFoundException {
        String line;
        customwords = new ArrayList<String>();
        try {
            FileReader file = new FileReader(wordsource);
            BufferedReader reader = new BufferedReader(file);

            while((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) {
                    throw new IndexOutOfBoundsException();
                }
                customwords.add(line);
            }
            reader.close();
            return customwords.get((int)(Math.random()*customwords.size()));
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException();

        } catch(IOException e) {
            throw new IOException();
        }
    }

    public static String[] getCounties() {
        return counties;
    }

    public static void setCounties(String[] counties) {
        Words.counties = counties;
    }

    public static String[] getCountries() {
        return countries;
    }

    public static void setCountries(String[] countries) {
        Words.countries = countries;
    }

    public static String[] getCities() {
        return cities;
    }

    public static void setCities(String[] cities) {
        Words.cities = cities;
    }
}
