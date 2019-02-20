
import hangman.GameState;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GameStateTest {

    @Test
    public void testGameStateConstructor() {
        String word = "Ireland";
        GameState gameState = new GameState(word, 5, 2);

        assertEquals(gameState.getWord(), word);
        assertEquals(gameState.getGuessesRemaining(), 5);
        assertEquals(gameState.getGuessesMadeSoFar(), 0);
        assertEquals(gameState.getHintsRemaining(), 2);
    }

    @Test
    public void testUnguessedArrayNormal() {
        String word = "Ireland";
        GameState gameState = new GameState(word, 10, 4);

        ArrayList<Character> expected = new ArrayList<>();

        expected.add('i');
        expected.add('r');
        expected.add('e');
        expected.add('l');
        expected.add('a');
        expected.add('n');
        expected.add('d');

        gameState.addCharactersOfWordToUnguessed(word);

        assertEquals(gameState.getUnguessedLetters(), expected);
    }

    @Test
    public void testInitialisingUnGuessedArrayRepeate() {
        String word = "Stirling";
        GameState gameState = new GameState(word, 5, 2);

        ArrayList<Character> expected = new ArrayList<>();

        expected.add('s');
        expected.add('t');
        expected.add('i');
        expected.add('r');
        expected.add('l');
        expected.add('n');
        expected.add('g');

        expected.size();

        gameState.addCharactersOfWordToUnguessed(word);

        assertEquals(gameState.getUnguessedLetters(), expected);
    }

    //testt the initialization of array with empty space
    @Test
    public void testInitialisingUnGuessedWithSpace() {
        String word = "Test space";
        GameState gameState = new GameState(word, 5, 2);

        ArrayList<Character> expected = new ArrayList<>();

        expected.add('t');
        expected.add('e');
        expected.add('s');
        expected.add('p');
        expected.add('a');
        expected.add('c');


        gameState.addCharactersOfWordToUnguessed(word);

        assertEquals(gameState.getUnguessedLetters(), expected);
    }

    @Test
    public void testShowingWordWithWordsGuessedNoneGuessed() {
        String word = "Test Space";
        GameState gameState = new GameState(word, 5, 2);

        ArrayList<Character> expected = new ArrayList<>();

        String guessed = gameState.createCurrentStateOfWord();
        assertEquals("---- -----", guessed);

    }

    @Test
    public void testShowingWordWithWordsGuessedAllGuessed() {
        String word = "Test Space";
        GameState gameState = new GameState(word, 5, 2);
        gameState.getGuessedLetters().add('t');
        gameState.getGuessedLetters().add('e');
        gameState.getGuessedLetters().add('s');
        gameState.getGuessedLetters().add('t');
        gameState.getGuessedLetters().add('s');
        gameState.getGuessedLetters().add('p');
        gameState.getGuessedLetters().add('a');
        gameState.getGuessedLetters().add('c');
        gameState.getGuessedLetters().add('e');

        String guessed = gameState.createCurrentStateOfWord();
        assertEquals(word, guessed);

    }

    //checks if word is parsed
    @Test
    public void parseWord() {
        String word = "Testspace";
        GameState gameState = new GameState(word, 20, 20);
        // Trying out different lengths.
        int result1 = gameState.getInput("Test");
        int result2 = gameState.getInput("testpace");
        int result3 = gameState.getInput("la");

        assertEquals(result1, gameState.WORD);
        assertEquals(result2, gameState.WORD);
        assertEquals(result3, gameState.WORD);
    }


    @Test
    public void parseLetter() {
        String word = "Testspace";
        GameState gameState = new GameState(word, 20, 20);
        // Trying out different lengths.
        int result = gameState.getInput("l");
        int result2 = gameState.getInput("t");

        assertEquals(result, gameState.LETTER);
        assertEquals(result2, gameState.LETTER);
    }

    @Test
    public void parseHint() {
        String word = "Testspace";
        GameState gameState = new GameState(word, 20, 20);

        int result = gameState.getInput("?");
        assertEquals(result, gameState.HINT);

    }

    @Test
    public void parseWrongInput() {
        String word = "Testspace";
        GameState gameState = new GameState(word, 20, 20);

        int result = gameState.getInput("");
        int result1 = gameState.getInput("{");
        int result2 = gameState.getInput(" ");
        int result3 = gameState.getInput("[");
        int result4 = gameState.getInput("!");

        assertEquals(result, gameState.WRONG_INPUT);
        assertEquals(result, gameState.WRONG_INPUT);
        assertEquals(result, gameState.WRONG_INPUT);
        assertEquals(result, gameState.WRONG_INPUT);
        assertEquals(result, gameState.WRONG_INPUT);
    }

    @Test
    public void parseWrongInputNull() {
        String word = "Testspace";
        GameState gameState = new GameState(word, 20, 20);

        int result = gameState.getInput(null);


        assertEquals(result, gameState.WRONG_INPUT);

    }

    //tests if a correct hint is provided and also that hints remaining are reduced
    @Test
    public void testProvideHint() {

        String word = "By";
        GameState gameState = new GameState(word, 20, 20);
        gameState.addCharactersOfWordToUnguessed(word);
        Character hint = gameState.provideHint();

        assertTrue(gameState.getUnguessedLetters().contains(hint));
        assertEquals(gameState.getHintsRemaining(), 19);



    }


    //tests that the system does not allow the user to get more than the required hints
    @Test
    public void testRemainingHints() {

        String word = "By";
        GameState gameState = new GameState(word, 20, 1);
        gameState.addCharactersOfWordToUnguessed(word);

        Character validProvision = gameState.provideHint();
        //returns 0 if invalid
        Character unvalidProvision = gameState.provideHint();

        assertEquals(0, gameState.getHintsRemaining());
        assertTrue(unvalidProvision == '0');


    }
    //tests that the system sends correct message when more hints are asked but not allowed
    @Test
    public void testRemainingHintsMessageNoHintsLeft() {

        String word = "By";
        GameState gameState = new GameState(word, 20, 1);
        gameState.addCharactersOfWordToUnguessed(word);

        String validProvision = gameState.handleGuessedHint();
        //returns 0 if invalid
        String unvalidProvision = gameState.handleGuessedHint();

        assertEquals(0, gameState.getHintsRemaining());
        assertTrue(unvalidProvision.equals("No hints left"));


    }



    //tests that the program recognizes when a letter inputted does not belong to the word
    @Test
    public void testWrongLetterIsDetected() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        String message = gameState.handleGuessedLetter('i');
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 9);
        assertEquals(message, "The character you guessed is incorrect");



    }

    //tests that the program recognizes when a letter inputted does not belong to the word
    @Test
    public void testNullLetterIsDetected() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        String message = gameState.handleGuessedLetter(null);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 10);
        assertEquals(message, "The character you guessed is incorrect");

    }



    //tests that the program recognizes when a letter inputted does belongs to the word
    @Test
    public void testCorrectLetterIsDetected() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        Character charGuessed = 'b';
        String message = gameState.handleGuessedLetter(charGuessed);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 10);
        assertEquals(message, "You guessed the character correctly");
        assertTrue(!gameState.getUnguessedLetters().contains(charGuessed));


    }

    //tests that the program recognizes when a letter inputted belongs to the word even if its a capital letter
    @Test
    public void testCorrectLetterIsDetectedCapital() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        Character charGuessed = 'B';
        String message = gameState.handleGuessedLetter(charGuessed);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 10);
        assertEquals(message, "You guessed the character correctly");
        assertTrue(!gameState.getUnguessedLetters().contains(charGuessed));


    }


    //tests that the program recognizes when a letter inputted is not the word
    @Test
    public void testWrongWordIsDetected() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        String wordGuessed = "balando";
        String message = gameState.handleGuessedWord(wordGuessed);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 9);
        assertEquals(message, "The word guessed is incorrect");

    }


    //tests that the program recognizes when a letter inputted is not the word (CAPITAL)
    @Test
    public void testWrongWordIsDetectedCapital() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        String wordGuessed = "BALANDO";
        String message = gameState.handleGuessedWord(wordGuessed);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 9);
        assertEquals(message, "The word guessed is incorrect");

    }

    //tests that the program recognizes when a correct word (matching) is inputted
    @Test
    public void testCorrectWordIsDetected() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        String wordGuessed = "Bylando";
        String message = gameState.handleGuessedWord(wordGuessed);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 10);
        assertEquals(message, "You guessed the word correctly");

    }

    //tests that the program recognizes when a word (In CAPS) is a matching word
    @Test
    public void testCorrectWordIsDetectedCapital() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);


        String wordGuessed = "BYLANDO";
        String message = gameState.handleGuessedWord(wordGuessed);
        int remainingGuesses = gameState.getGuessesRemaining();

        //shows that the guesses remaining was decremented
        assertEquals(remainingGuesses, 10);
        assertEquals(message, "You guessed the word correctly");
    }

    //state game checking
    //tests that the program recognizes when a word (In CAPS) is a matching word
    @Test
    public void testPlayerWon() {
        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);

        gameState.outputGuessMessage("Bylando");
        assertTrue(gameState.won());
    }

    //test player not won
    @Test
    public void testPlayerNotWon() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 10, 5);
        gameState.addCharactersOfWordToUnguessed(word);

        gameState.outputGuessMessage("Balando");
        assertTrue(!gameState.won());
    }


    //test player not won
    @Test
    public void testPlayerLost() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 1, 5);
        gameState.addCharactersOfWordToUnguessed(word);

        gameState.outputGuessMessage("Balando");
        assertTrue(gameState.lost());
    }

    //test player not won
    @Test
    public void testPlayerNotLost() {

        String word = "Bylando";
        GameState gameState = new GameState(word, 2, 5);
        gameState.addCharactersOfWordToUnguessed(word);

        gameState.outputGuessMessage("Balando");
        assertTrue(!gameState.lost());
    }


}