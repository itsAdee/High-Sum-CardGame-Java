
// Purpose: CardDeck class that creates a deck of cards and allows the user to shuffle and pass cards.
import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
    // Instance Variables
    ArrayList<String> deck;

    // Constructor
    public CardDeck() {
        this.deck = generateDeck();
    }

    // Methods
    // Purpose: Generates a deck of cards.
    private ArrayList<String> generateDeck() {
        String[] suits = { "Spade", "Heart", "Club", "Diamond" };
        String[] values = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" };

        ArrayList<String> tempDeck = new ArrayList<String>();
        for (String suit : suits) {
            for (String value : values) {
                tempDeck.add(suit + " " + value);
            }
        }
        return tempDeck;
    }

    // Purpose: Shuffles the deck of cards.
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
        Collections.shuffle(this.deck);
    }

    // Purpose: Passes a card from the deck.
    public String passCard() {
        String card = this.deck.get(0);
        this.deck.remove(0);
        return card;
    }

    // Purpose: Resets the deck of cards.
    public void resetDeck() {
        this.deck.clear();
        this.deck = generateDeck();
    }

    // Purpose: Displays the deck of cards.
    public void displayDeck() {
        for (String card : this.deck) {
            System.out.println(card);
        }
    }
}
