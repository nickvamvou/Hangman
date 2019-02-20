import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Class containing the main method.
 */
public final class Hangman {
    private Hangman(){}
    public static void main(String[] args) {
        try{
            //initialize the scanner
            Scanner scanner = new Scanner(System.in, "UTF-8");
            //initialize the game state
            hangman.GameState gameState = null;
            //declare the arguement parser
            CommandOpts arguementParser = new CommandOpts();
            arguementParser.getArguementsForGame(args);
            //check whether to print the menu or parse the file
            boolean terminal = printMenu(arguementParser.getWordSource());
            //show the menu to the user if its a menu
            if(terminal){
                printPlayerMenu();
            }
            //initialize the game based on if the parsing is done from the console or from a file
            //pass the parser object to determine category, maximum guesses, maximum hints
            gameState = initializeGame(terminal, gameState, arguementParser, scanner);

            //run the game
            while (!gameState.won() && !gameState.lost()){
                //show the word to the user
                gameState.showWord();
                printInformationAboutGameRound(gameState.userGuess(), gameState.getGuessesRemaining(), gameState.getHintsRemaining());
            }
            //checks whether the player won or lost after the game ended
            checkFinishedState(gameState.won(), gameState);
            //close the scanner at the end
            scanner.close();

        }
        catch (CategoryNotFoundException e){
            System.out.println("Category selected does not exist please restart the game");
            System.exit(1);
        }
        catch (NumberFormatException e){
            System.out.println("Argument passed is incorrect please restart the game!");
            System.exit(1);

        } catch (IllegalArgumentException e){
            System.out.println("Illegal argument passed please restart the game!");
            System.exit(1);
        }

        catch (InputMismatchException e){
            System.out.println("Illegal argument passed please restart the game!");
            System.exit(1);
        }
    }

    //decides whether the input for the arguement parser should be taken from the terminal or from a file
    public static boolean printMenu(String source){
        if(source == ""){
            return true;
        }
        return false;
    }
    /**
     * Initializes the game based on input or file provided
     * @param fromInput boolean which indicates if the program should parse from terminal or file
     * @param gameState the gamestate object initialized from the main method
     * @param arguementsPassed the arguments passed in order to initialize the game
     * @param sc Scanner object to scan input from the terminal
     * @return The gameState object which now contains the information needed to start the game
     */
    public static hangman.GameState initializeGame(boolean fromInput, hangman.GameState gameState, CommandOpts arguementsPassed, Scanner sc) throws CategoryNotFoundException {
        //if the input is from terminal initialize game state based on category selected by the user
        if(fromInput){
            int category = sc.nextInt();
            String word = Words.generateRandomWord(category);

            gameState = new hangman.GameState(word, arguementsPassed.getMaxGuesses(), 3);
            //create the array needed for unguessed
            gameState.addCharactersOfWordToUnguessed(word);
            return gameState;
        }
        else{
            //initialize game based on file
            gameState = new hangman.GameState(arguementsPassed.wordSource, arguementsPassed.getMaxGuesses(), arguementsPassed.getMaxHints());
            gameState.addCharactersOfWordToUnguessed(arguementsPassed.wordSource);
            return gameState;
        }
    }

    /**
     * Prints to the console the menu in which the user can
     * select the category to play with
     */
    public static void printPlayerMenu(){
        System.out.println("  1. Counties");
        System.out.println("  2. Countries");
        System.out.println("  3. Cities");
        System.out.print("Pick a category:");
    }

    /**
     * Prints to the console the necessary information
     * for the user to play the game
     * @param guessOutcome The state of the target word after the gueess
     * @param guessRemaining The guess that are remaining
     * @param hintsRemaining The remaining hints that the user has
     */
    public static void printInformationAboutGameRound(String guessOutcome, int guessRemaining, int hintsRemaining){
        System.out.println("Guesses remaining: " + guessRemaining);
        System.out.println("Hints remaining: " + hintsRemaining);
        System.out.println(guessOutcome);
    }

    /**
     * Prints at the console whether the game is won or lost
     * and prints to the console based on the GameState logic
     * object
     * @param won Whether the user won or lost the game
     * @param gameState The logic object of the game
     */
    public static void checkFinishedState(boolean won, hangman.GameState gameState){
        if (won) {
            System.out.println("Well done! You found the word!");
            System.out.println("You took " + gameState.getGuessesMadeSoFar() + " guesses");
        } else {
            System.out.println("You lost! The word was " + gameState.getWord());
        }
    }
}
