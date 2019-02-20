public class CommandOpts {

    /**
     * Represents the maximum guesses that a player can make
     */
    public int maxGuesses;
    /**
     * Represents the maximum hints that a player can have
     */
    public int maxHints;
    /**
     * Represents the word that has been selected that the user needs to buy
     */
    String wordSource;

    /**
     * Representes the default hints that a user can have if no number is provided
     */
    public static final int DEFAULT_HINTS = 3;

    /**
     * Represents the default guesses that a user can have if no number is provided
     */
    public static final int DEFAULT_GUESSES = 8;

    /**
     * Constructor of CommandOpts that initializes maxGuesses, maxHints
     * and wordSource to their default values.
     */
    public CommandOpts(){
        maxGuesses = DEFAULT_GUESSES;
        maxHints = DEFAULT_HINTS;
        wordSource = "";
    }



    /**
     * Hanldles the parsing of arguments (guesses, hints, wordsource) when provided through a file
     * @param args String array containing flags values for variables together with the path to the file
     * @throws NumberFormatException when value of the variables (guesses, hints) is not the correct type (int)
     */
    public void getArguementsForGame(String[] args) throws NumberFormatException{
        //sets the nuber of max guesses and max hints
        for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("--guesses")) {
                maxGuesses = Integer.parseInt(args[i+1]);
                i++;
            }
            else if (args[i].equals("--hints")) {
                maxHints = Integer.parseInt(args[i+1]);
                i++;
            }
            else if(args[i].equals("--source")){
                wordSource = args[i+1];
                i++;
            }
            else{
                System.out.println("Arguement passed is not correct");
            }
        }
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }

    public void setMaxGuesses(int maxGuesses) {
        this.maxGuesses = maxGuesses;
    }

    public int getMaxHints() {
        return maxHints;
    }

    public void setMaxHints(int maxHints) {
        this.maxHints = maxHints;
    }

    public String getWordSource() {
        return wordSource;
    }

    public void setWordSource(String wordSource) {
        this.wordSource = wordSource;
    }
}