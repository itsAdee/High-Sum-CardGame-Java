import java.io.IOException;

// Purpose: Menu class that allows the user to play a game of High Sum.
class Menu {
    // Instance Variables
    HighSumCardGame highSum;
    boolean exit;

    // Constructor
    public Menu() {
        this.highSum = new HighSumCardGame();
        this.exit = false;
    }

    // Methods
    // Purpose: Method that displays the options menu.
    public void newGamePrompt() {
        while (true) {
            System.out.println("Do you want to play again? (Y/N)");
            try {
                String choice = highSum.bf.readLine();
                if (choice.equals("Y") || choice.equals("y") || choice.equals("yes") || choice.equals("Yes")) {
                    highSum.resetGame();
                    this.newGame();
                } else {
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Invalid Input! Please try again...");
                continue;
            }
            break;
        }
    }

    // Purpose: Method that displays the results of the game.
    public void displayResults() {
        System.out.println(("-").repeat(100));
        System.out.println(" Game Ends -Dealer reveals cards.");
        System.out.println(("-").repeat(100));
        highSum.viewDealerCardsEnd();
        System.out.println();
        highSum.viewUserCards();
        System.out.println();
        // check the winner
        if (highSum.calcValue(highSum.playerCards) == highSum.calcValue(highSum.dealerCards)) {
            System.out.println("Draw!");
            System.out.println("Original bet returned to both dealer and IcePeak.");
            highSum.playerChips = 100;
            highSum.dealerChips = 100;
        }

        else if (highSum.calcValue(highSum.playerCards) > highSum.calcValue(highSum.dealerCards)) {
            System.out.println("You Win!");
            highSum.playerChips += highSum.total_bet;
            System.out.println("You have " + highSum.playerChips + " chips.");
        } else {
            System.out.println("Dealer Wins!");
            highSum.dealerChips += highSum.total_bet;
            System.out.println("The dealer has " + highSum.dealerChips + " chips.");
        }

        System.out.println();
    }

    // Purpose: Method that starts a new game.
    public void newGame() {
        this.exit = false;
        System.out.println(("=").repeat(100));
        System.out.println("HighSum GAME");
        System.out.println(("=").repeat(100));
        System.out.println(highSum.username + ", You have " + highSum.playerChips + " chips.");
        System.out.println(("-").repeat(100));

        System.out.println("Game Starts - Dealer shuffles deck.");
        highSum.cardDeck.shuffleDeck();

        for (int i = 0; i < 4; i++) {
            highSum.runRound(i + 1);
            if (highSum.is_game_cancelled) {
                System.out.println("Game Cancelled!");
                this.exit = true;
                break;
            }
        }

        if (!exit)
            this.displayResults();
        newGamePrompt();
    }

    // Purpose: Method that starts the game.
    public void Starter() {
        this.highSum = new HighSumCardGame();

        while (true) {
            System.out.println("HighSum GAME");
            System.out.println(("=").repeat(100));
            try {
                highSum.login();

            } catch (IOException e) {
                System.out.println("Invalid Input! Please try again...");
                continue;
            }
            break;
        }

        this.newGame();
    }

    // Purpose: Main method that starts the program.
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.Starter();
    }
}
