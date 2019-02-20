

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class WordsTest {

    //check that the category counties actually returns word from counties
    @Test
    public void testRandomWordReturnCounties() throws CategoryNotFoundException {
        Words words = new Words();
        String wordGenerated = words.generateRandomWord(words.COUNTIES);
        assertThat(Arrays.asList(Words.getCounties()), hasItem(wordGenerated));
    }

    //check that the category countries actually returns word from countries
    @Test
    public void testRandomWordReturnCountry() throws CategoryNotFoundException {
        Words words = new Words();
        String wordGenerated = words.generateRandomWord(words.COUNTRIES);
        assertThat(Arrays.asList(Words.getCountries()), hasItem(wordGenerated));
    }

    //check that the category cities actually returns word from cities
    @Test
    public void testRandomWordReturnCity() throws CategoryNotFoundException {
        Words words = new Words();
        String wordGenerated = words.generateRandomWord(words.CITIES);
        assertThat(Arrays.asList(Words.getCities()), hasItem(wordGenerated));
    }

    //test invalid input
    //check that the category cities actually returns word from cities
    @Test(expected = CategoryNotFoundException.class)
    public void testRandomWordInvalid() throws CategoryNotFoundException {
        Words words = new Words();
        String wordGenerated = words.generateRandomWord(5);
    }

    //test that the program can get the word from the file
    @Test
    public void testRandomWordFileInput() {
        String fileWord = "Hello";
        try {
            File file = File.createTempFile("file", ".txt", new File("."));
            file.deleteOnExit();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(fileWord);
            bufferedWriter.close();
            String wordGenerated = Words.randomWordBasedOnFile(file.getAbsolutePath());
            assertEquals(fileWord, wordGenerated);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //what happens if the file has no words
    //test that the program can get the word from the file
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRandomWordFileNoInput() {
        String fileWord = "";
        try {
            File file = File.createTempFile("file", ".txt", new File("."));
            file.deleteOnExit();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(fileWord);
            bufferedWriter.close();
            String wordGenerated = Words.randomWordBasedOnFile(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testRandomWordFileDoesNotExist() throws IOException {
        String returnedWord = Words.randomWordBasedOnFile("checker.txt");
    }


}
