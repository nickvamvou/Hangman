package hangman;

import java.util.ArrayList;
import java.util.Scanner;

// The game state
public class GameState {

    /**
     * Representation of user input, categorized weather the user typed a word (ex 'hello'), a letter ('l'),
     * asked for a hint (?) or inputted a wrong input ('!')
     */
    public static final int WORD = 0;
    public static final int LETTER = 1;
    public static final int HINT = 2;
    public static final int WRONG_INPUT = 3;

    /**
     * Word that the user is looking for
     */
    public String word; // letters

    /**
     * Keeps track of the guesses made so far.
     */
    public int guessesMadeSoFar;

    /**
     * Keeps track of the guesses remaining.
     */
    public int guessesRemaining;

    /**
     * Keeps track of hints remaining
     */
    public int hintsRemaining;

    /**
     * Contains the characters that the user has guessed based on the word the user is searching (word)
     */
    ArrayList<Character> guessedLetters;

    /**
     * Contains the characters that the user has not guessed based on the word the user is searching (word)
     */
    ArrayList<Character> unguessedLetters;

    /**
     * Contains the characters that the user has guessed based on the word the user is searching (word)
     */
    //scanner for getting the input from the user via the terminal
    public Scanner sc;



    /**
     * Constructor of GameState which initializes the game fields
     * @param targetWord Represents the word that the player will be looking for
     * @param maximumGuesses Represents the maximum number of guesses the player can make
     * @param maximumHints Represents the maximun hints that the player can have throughout the game
     */
    public GameState(String targetWord, int maximumGuesses, int maximumHints) {
        sc = new Scanner(System.in).useDelimiter("\n");
        //initialize word
        this.word = targetWord;
        //initialize guessed and unguessed letters
        unguessedLetters = new ArrayList<Character>();
        guessedLetters = new ArrayList<Character>();
        this.guessesMadeSoFar = 0; // guesses made
        guessesRemaining = maximumGuesses; // guesses remaining
        this.hintsRemaining = maximumHints; //hints made
    }

    /**
     * Receives the targetWord that the user is trying to guess
     * and populates the unguessedLetters char array with the characters
     * of the word (no duplicate characters)
     * @param targetWord Word that the user is trying to guess (target word)
     *
     */
    public void addCharactersOfWordToUnguessed(String targetWord){
        //add the letters to the word (all the letters should go to unguessed)
        for(int i = 0; i < targetWord.length(); ++i) {
            if (!unguessedLetters.contains(Character.toLowerCase(targetWord.charAt(i))) && Character.toLowerCase(targetWord.charAt(i)) != ' ')
                unguessedLetters.add(Character.toLowerCase(targetWord.charAt(i)));
        }
    }


    /**
     * Shows the word that the player has guessed so far
     */
    public void showWord() {
        String wordCurrent = createCurrentStateOfWord();
        System.out.println(wordCurrent);

    }

    /**
     * Based on the guessed letters array, the function constructs
     * the necessary string to display to the user
     */
    public String createCurrentStateOfWord(){
        StringBuilder returnString = new StringBuilder();
        for (int i = 0; i < word.length(); ++i) {
            Character letter = getChar(word,i);
            if ( word.charAt(i) == ' ') {
                returnString.append(" ");
            } else if (guessedLetters.contains(letter)) {
                returnString.append(word.charAt(i));
            } else {
                returnString.append("-");
            }
        }
        return returnString.toString();
    }


    /**
     * @param input String that contains the input of the user
     * @return an integer which shows what type of input the user has passed to the application
     * Returns :
     * 0 for word
     * 1 for letter
     * 2 for hint
     * 3 for wrong input
     */
    public int getInput(String input){

        if(input == null || input.length() == 0){
            return 3;
        }
        else if(input.length() > 1){
            return 0;
        }
        //if its a character
        Character charInput = input.charAt(0);
        if(charInput == '?'){
            return 2;
        }
        else if(charInput >= 'A' && charInput <= 'Z'
                || charInput >= 'a' && charInput <= 'z'){
            return 1;
        }
        else {
            return 3;
        }
    }

    public Character getChar(String word, int i){
        return Character.toLowerCase(word.charAt(i));
    }


    /**
     * Function that returns a String based on the input received
     * inputGuess can be categorized (Word, Letter, Hint, Wrong Input)
     * @param inputGuess A string that shows the input of the user.
     * @return A String containing the result based on the action of the input.
     */
    public String outputGuessMessage(String inputGuess) {

        //get the type of user input
        int typeOfInputGuess = getInput(inputGuess);

        //word
        if(typeOfInputGuess == 0){
            return handleGuessedWord(inputGuess);
        }
        //letter
        else if(typeOfInputGuess == 1){
            return handleGuessedLetter(inputGuess.charAt(0));
        }
        //hint
        else if(typeOfInputGuess == 2){
            return handleGuessedHint();
        }
        //wrong input
        else if(typeOfInputGuess == 3){
            return handleWrongInput();
        }
        //wrong input
        else{
            return handleWrongInput();
        }

    }

    /**
     * Function that filters the input by receiving it with the scanner
     * and following using the outputGuessMessage function in order to return
     * a result based on the input
     * @return A String containing the result based on the action of the input.
     */
    public String userGuess(){
        System.out.print("Guess a letter or word (? for a hint): ");
        String inputGuess = sc.next().toLowerCase();
        return outputGuessMessage(inputGuess);
    }


    /**
     * Function that handles the situation where
     * the input passed from the user is a word ('hello')
     * @param guessedWord is the word guessed by the user
     * @return A String which indicates whether the word guessed is correct or incorrect
     * (belongs to the targetWord)
     */
    public String handleGuessedWord(String guessedWord){
        guessesMadeSoFar++;
        //checks if the word was correctly guessed
        if (guessedWord.equalsIgnoreCase(word)) {
            // clear the unguessed letters array
            unguessedLetters.clear();
            return "You guessed the word correctly";
        } else {
            // Subtract the number of remaining guesses
            guessesRemaining--;
            return "The word guessed is incorrect";
        }
    }

    /**
     * Function that handles the situation where
     * the input passed from the user is a letter ('l')
     *@param guessedLetter is the word guessed by the user
     *@return A String which indicates whether the letter guessed is correct or incorrect
     * (belong to the taregWord)
     */
    public String handleGuessedLetter(Character guessedLetter){
        if(guessedLetter == null){
            return "The character you guessed is incorrect";
        }
        guessesMadeSoFar++;
        for (Character characterNotGuessedSoFar : unguessedLetters) {
            //checks whether the guess was correct
            if (characterNotGuessedSoFar == Character.toLowerCase(guessedLetter)) {
                // add the removed letter to the guessed
                guessedLetters.add(characterNotGuessedSoFar);
                // remove the guessed letter from unguessed
                unguessedLetters.remove(characterNotGuessedSoFar);
                return "You guessed the character correctly";
            }
        }
        // if this statement reaches the guess was wrong
        guessesRemaining--;
        return "The character you guessed is incorrect";
    }

    /**
     * Function that handles the situation where
     * the input passed from the user is a hint ('?')
     *
     *@return A String which indicates whether the letter guessed is correct or incorrect
     * (belong to the taregWord)
     */
    public String handleGuessedHint(){
        Character hint = provideHint();
        if (hint == '0') {
            return "No hints left";
        } else {
            return "Try: " + hint;
        }
    }

    /**
     * Provides a random letter hint to the player
     * only if the player has enough remaining hints
     *@return Hint char or 0 if the user does not have any other
     * hints
     */
    public Character provideHint() {
        if (hintsRemaining == 0) {
            return '0';
        }
        hintsRemaining--;
        return unguessedLetters.get((int)(Math.random()* unguessedLetters.size()));

    }

    /**
     * Is called, when a wrong input character is passed to the user
     * @return The String indicating that the word is wrong
     * hints
     */
    public String handleWrongInput() {
        return "The input you provided is not correct, please try again";
    }


    /**
     * Is called, to check if the user won the game
     * which is the case if in the unguessed char array
     * there are no characters left
     * @return Boolean which indicates whether the game is won
     */
    public boolean won() {
        //all chars have been guessed
        return unguessedLetters.size() == 0;
    }

    /**
     * Is called, to check if the user lost the game
     * which is the case if the user has no guesses
     * and there are still elements in the unguessed letters
     * @return Boolean which indicates whether the game is lost
     */
    public boolean lost() {
        return (unguessedLetters.size() > 0 && guessesRemaining == 0);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getGuessesMadeSoFar() {
        return guessesMadeSoFar;
    }

    public void setGuessesMadeSoFar(int guessesMadeSoFar) {
        this.guessesMadeSoFar = guessesMadeSoFar;
    }

    public int getGuessesRemaining() {
        return guessesRemaining;
    }

    public void setGuessesRemaining(int guessesRemaining) {
        this.guessesRemaining = guessesRemaining;
    }

    public int getHintsRemaining() {
        return hintsRemaining;
    }

    public void setHintsRemaining(int hintsRemaining) {
        this.hintsRemaining = hintsRemaining;
    }

    public ArrayList<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(ArrayList<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public ArrayList<Character> getUnguessedLetters() {
        return unguessedLetters;
    }

    public void setUnguessedLetters(ArrayList<Character> unguessedLetters) {
        this.unguessedLetters = unguessedLetters;
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }
}