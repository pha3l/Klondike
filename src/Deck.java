/** A deck or pile of cards. */
public class Deck {

    /** Stores the cards in this deck. */
    private Card[] cards;

    /** The number of cards currently in this deck. */
    private int numCards;

    /** A new deck is initially empty, but has the capacity to hold
     * all the cards in a standard deck. */
    public Deck() {
        // TODO You have to write this.
        numCards = 0;
        cards = new Card[52];
    }

    /** Adds card to the top of this deck. */
    public void add(Card card) {
        // TODO You have to write this.
        cards[numCards]=card;
        numCards++;
    }

    /** Adds all cards in a standard deck. */
    public void fill() {
        // TODO You have to write this.
        numCards = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[numCards] = new Card(rank,suit);
                numCards++;
            }
        }
    }

    /**
     * Returns the ith card in this deck, where card 0 is the one on the bottom.
     * Assumes the deck is not empty.
     */
    public Card get(int i) {
        // TODO You have to write this.
        if (numCards > i)
            return cards[i];
        else
            return null;
    }

    /** Moves one card from the top of this deck to the top of that deck. */
    public void move(Deck that) {
        // TODO You have to write this.
        that.add(this.cards[numCards-1]);
        this.cards[numCards-1] = null;
        numCards--;
    }

    /**
     * Moves the top n cards from the top of this deck to the top of that deck.
     * They maintain their order, so that the card that used to be on top of
     * this becomes the top of that.
     */
    public void move(Deck that, int n) {
        // TODO You have to write this.
        Deck temp = new Deck();
        for (int i=0; i<n; i++) {
            temp.add(this.cards[numCards-1]);
            this.cards[numCards-1]=null;
            numCards--;
        }
        for (int i=0; i<n; i++) {
            that.add(temp.cards[temp.numCards-1]);
            temp.cards[temp.numCards-1] = null;
            temp.numCards--;
        }
    }

    /**
     * Randomly reorders the cards in this deck.
     */
    public void shuffle() {
        // TODO You have to write this.

        // The TAs will not help you on this method. You must research
        // shuffling on your own.

        // Mention in a comment what source(s) you used when
        // researching shuffling algorithms. Include URLs for web
        // pages, title and authors for books, names for people, etc.
//        for i from n - 1 downto 1 do
//            j = random integer with 0 <= j <= i
//            exchange a[j] and a[i]
        for (int i = numCards-1;i > 0; i--) {
            int j = (int)(Math.random()*(i+1));
            Card temp = cards[j];
            cards[j] = cards[i];
            cards[i] = temp;
        }
        }
    

    /** Returns the number of cards in this deck. */
    public int size() {
        // TODO You have to write this.
        return numCards;
    }

    /**
     * Returns the top card on this deck (but does not modify the deck).
     * 
     * @return null if this deck is empty.
     */
    public Card top() {
        // TODO You have to write this.
        if (numCards > 0)
            return cards[(numCards-1)];
        else
            return null;
    }
    
}


