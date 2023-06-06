import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

// Purpose: HighSumCardGame class that allows the user to play a game of High Sum.

public class HighSumCardGame {
    // Instance Variables
    BufferedReader bf;
    ArrayList<String> dealerCards;
    ArrayList<String> playerCards;
    CardDeck cardDeck;
    String username, password;
    int playerChips, dealerChips;
    int total_bet, player_bet, dealer_bet;
    Boolean is_game_cancelled;

    // Constructor
    public HighSumCardGame() {
        this.bf = new BufferedReader(new InputStreamReader(System.in));
        this.dealerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
        this.cardDeck = new CardDeck();
        this.username = "IcePeak";
        this.password = "password";
        this.playerChips = this.dealerChips = 100;
        this.total_bet = this.player_bet = this.dealer_bet = 0;
        this.is_game_cancelled = false;
    }
    // Methods

    // Purpose: Method that authenticates the user.
    // the username must be "IcePeak" and the password must be "password".
    public void login() throws IOException {
        System.out.print("Enter Login Name> ");
        String name = this.bf.readLine();
        System.out.print("Enter Password> ");
        String pass = this.bf.readLine();

        if (!name.equals(this.username) || !pass.equals(this.password)) {
            System.out.println("Invalid Username or Password! Please try again...");
            this.login();
        }
    }

    // Purpose: Method that allows the passing of cards to the player and dealer.
    public void makePass() {
        this.playerCards.add(this.cardDeck.passCard());
        this.dealerCards.add(this.cardDeck.passCard());
    }

    // Purpose: Method that returns the suit value of a card.
    public int returnSuitValue(String suit) {
        return switch (suit) {
            case "Spade" -> 3;
            case "Heart" -> 2;
            case "Club" -> 1;
            default -> 0;
        };
    }

    // Purpose: Method that returns the card value of a card.
    public int returnCardValue(String val) {
        if (val.equals("Ace"))
            return 1;
        else if (val.equals("King") || val.equals("Queen") || val.equals("Jack") || val.equals("10"))
            return 10;
        else {
            return Integer.parseInt(val);
        }
    }

    // Purpose: Method that calculates the value of a hand.
    public int calcValue(ArrayList<String> cards) {
        int value = 0;
        for (String card : cards) {
            value += returnCardValue((card.split("\\W+"))[1]);
        }
        return value;
    }

    // Purpose: Method to view the player's cards.
    public void viewUserCards() {
        System.out.println(this.username);
        for (String card : this.playerCards) {
            System.out.print("<" + card + "> ");
        }
        System.out.println();
        System.out.println("Value: " + calcValue(this.playerCards));
    }

    // Purpose: Method to view the dealer's cards. First card is hidden.
    public void viewDealerCards() {
        System.out.println("Dealer");
        System.out.print("<HIDDEN CARD> ");
        for (int i = 1; i < this.dealerCards.size(); i++) {
            System.out.print("<" + this.dealerCards.get(i) + "> ");
        }
        System.out.println();
    }

    // Purpose: Method to view the dealer's cards at the end of the game.
    public void viewDealerCardsEnd() {
        System.out.println("Dealer");
        for (String card : this.dealerCards) {
            System.out.print("<" + card + "> ");
        }
        System.out.println();
        System.out.println("Value: " + calcValue(this.dealerCards));
    }

    // Purpose: Method that checks if the bet is valid or not.
    public void verifyBet(int bet) {
        if (bet > this.playerChips) {
            System.out.println("Insufficient Chips! You have " + this.playerChips + " chips.\nPlease try again...");
            this.playerCall();
        } else if (bet <= 0) {
            System.out.println("Invalid Input! Please enter a positive whole number...");
            this.playerCall();
        } else {
            this.playerChips -= bet;
            this.dealerChips -= bet;
            this.total_bet += 2 * bet;
            System.out.println("IcePeak you are left with:" + this.playerChips + " chips.");
            System.out.println("Bet on the Table:" + this.total_bet + " chips.");
        }
    }

    // Purpose: Method that allows the player to call or quit.
    public void playerCall() {
        while (true) {
            try {
                System.out.println("Do you want to [C]all or [Q]uit? > ");
                String choice = bf.readLine();
                if (!choice.toLowerCase(Locale.ROOT).equals("c") && !choice.toLowerCase(Locale.ROOT).equals("call")) {
                    dealerChips += total_bet;
                    System.out.println("Dealer WINS!");
                    System.out.println("Dealer Chips: " + dealerChips);
                    System.out.println();

                    is_game_cancelled = true;
                    return;
                }
            } catch (Exception e) {
                continue;
            }
            break;
        }

        while (true) {
            try {
                System.out.println("Player call , Enter bet amount: ");
                this.player_bet = Integer.parseInt(this.bf.readLine());
                this.verifyBet(this.player_bet);
            } catch (Exception e) {
                System.out.println("Invalid Input! Please try again.. (Enter a positive whole number))");
                continue;
            }
            break;
        }
    }

    // Purpose: Method that allows the dealer to place a random bet.
    public void dealerCall() {
        int min = 10;
        int max = 30;
        if (this.dealerChips < 30) {
            max = this.dealerChips;
            min = 3;
        }
        Random rand = new Random();
        int dealer_bet = rand.nextInt(max - min + 1) + min;

        while (true) {
            try {
                System.out.println("Dealer call , bet amount: " + dealer_bet);
                System.out.println("Do you want to follow? (Y/N)");
                String choice = this.bf.readLine();
                if (choice.equals("y") || choice.equals("Y") || choice.equals("yes") || choice.equals("Yes")) {
                    this.dealerChips -= dealer_bet;
                    this.playerChips -= dealer_bet;
                    this.total_bet += 2 * dealer_bet;
                    System.out.println("IcePeak you are left with: " + this.playerChips + " chips.");
                    System.out.println("Bet on the Table: " + this.total_bet + " chips.");
                } else {
                    this.dealerChips += this.total_bet;
                    System.out.println("Dealer WINS!");
                    System.out.println("Dealer Chips: " + this.dealerChips);
                    System.out.println();
                    this.is_game_cancelled = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input! Please try again...");
                continue;
            }
            break;
        }

    }

    // Purpose: Method that selects the caller based on the cards. in each round.

    public void selectCaller(int round) {
        if (returnSuitValue((playerCards.get(round).split("\\W+"))[0]) > returnSuitValue(
                (dealerCards.get(round).split("\\W+"))[0])) {
            playerCall();
        } else if (returnSuitValue((playerCards.get(round).split("\\W+"))[0]) < returnSuitValue(
                (dealerCards.get(round).split("\\W+"))[0])) {
            dealerCall();
        } else {
            if (returnCardValue((playerCards.get(round).split("\\W+"))[1]) > returnCardValue(
                    (dealerCards.get(round).split("\\W+"))[1])) {
                playerCall();
            } else {
                dealerCall();
            }
        }
    }

    // Purpose: Method that runs a round of the game.
    public void runRound(int round) {
        System.out.println(("-").repeat(100));
        System.out.println("Dealer dealing cards - ROUND " + round);
        System.out.println(("-").repeat(100));

        this.makePass();
        if (round == 1)
            this.makePass();

        viewDealerCards();
        System.out.println();
        viewUserCards();

        System.out.println();
        selectCaller(round);
    }

    // Purpose: Method that resets the game.
    public void resetGame() {
        dealerCards.clear();
        playerCards.clear();
        playerChips = dealerChips = 100;
        cardDeck.resetDeck();
        this.total_bet = this.player_bet = this.dealer_bet = 0;
        this.is_game_cancelled = false;
    }
}
