
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandOptsTest {

    //tests whether the parsing arguements function works
    @Test
    public void optionsTest() {
        String[] args = { "--guesses", "2", "--hints", "4","--source", "words.txt" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.maxGuesses, 2);
        assertEquals(opts.maxHints, 4);
        assertEquals(opts.getWordSource(), "words.txt");
    }

    //tests whether the default constructor for command opts works
    @Test
    public void constructorTest() {
        String[] args = { "--guesses", "2", "--hints", "4","--source", "words.txt" };
        CommandOpts opts = new CommandOpts();

        assertEquals(opts.maxGuesses, 8);
        assertEquals(opts.maxHints, 3);
        assertEquals(opts.getWordSource(), "");
    }

    //test different order of the arguements
    @Test
    public void optionsTestOrder() {
        String[] args = { "--hints", "4", "--guesses", "2","--source", "words.txt" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.maxGuesses, 2);
        assertEquals(opts.maxHints, 4);
        assertEquals(opts.getWordSource(), "words.txt");
    }

    //test with different order 2
    //test different order of the arguements
    @Test
    public void optionsTestOrder2() {
        String[] args = { "--hints", "4", "--source", "words.txt","--guesses", "2" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.maxGuesses, 2);
        assertEquals(opts.maxHints, 4);
        assertEquals(opts.getWordSource(), "words.txt");
    }

    //test with different order 3
    @Test
    public void optionsTestOrder3() {
        String[] args = { "--source", "words.txt", "--guesses", "2","--hints", "4" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.maxGuesses, 2);
        assertEquals(opts.maxHints, 4);
        assertEquals(opts.getWordSource(), "words.txt");
    }

    //testing separate inputs with get arguments function
    @Test
    public void optionsTest1InputSource() {
        String[] args = { "--source", "words.txt" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.getWordSource(), "words.txt");
    }

    //testing separate inputs with get arguments function
    @Test
    public void optionsTest1InputGuesses() {
        String[] args = {  "--guesses", "2" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.maxGuesses, 2);
    }

    //testing separate inputs with get arguments function
    @Test
    public void optionsTest1InputHints() {
        String[] args = { "--hints", "4" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.maxHints, 4);

    }

    //testing with no arguements
    @Test
    public void optionTestWithNoArguements() {
        String[] args = {};
        CommandOpts opts = new CommandOpts();
        assertEquals(opts.getMaxGuesses(), CommandOpts.DEFAULT_GUESSES);
        assertEquals(opts.getMaxHints(), CommandOpts.DEFAULT_HINTS);
        assertEquals(opts.getWordSource(), "");
    }

    //testing with no arguements
    @Test
    public void optionTestWithNullArguements() {
        String[] args = null;
        CommandOpts opts = new CommandOpts();
        assertEquals(opts.getMaxGuesses(), CommandOpts.DEFAULT_GUESSES);
        assertEquals(opts.getMaxHints(), CommandOpts.DEFAULT_HINTS);
        assertEquals(opts.getWordSource(), "");
    }

    //check with wrong arguments

    //check with wrong arguments guess
    @Test
    public void optionTestWithWrongArguementsSource() {
        String[] args = { "--guesses", "2", "--hints", "4","--source", "2" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
        assertEquals(opts.getMaxGuesses(), 2);
        assertEquals(opts.getMaxHints(), 4);
        assertEquals(opts.getWordSource(), "2");
    }

    //check with wrong arguments hints
    @Test(expected=NumberFormatException.class)
    public void optionTestWithWrongArguementsHint()  {
        String[] args = { "--guesses", "2", "--hints", "word.txt","--source", "2" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
    }

    //check with wrong arguments source
    @Test(expected=NumberFormatException.class)
    public void optionTestWithWrongArguementsGuess() {
        String[] args = { "--guesses", "word.txt", "--hints", "4","--source", "2" };
        CommandOpts opts = new CommandOpts();
        opts.getArguementsForGame(args);
    }

}