/** A standard playing card. */
public class Card {

    /** True if this card is face-up. */
    private boolean faceUp;

    /** Rank of the card. */
    private Rank rank;

    /** Suit of the card. */
    private Suit suit;

    /**
     * Newly-created cards are face-up by default.
     */
    public Card(Rank rank, Suit suit) {
        // TODO You have to write this.
        this.rank = rank;
        this.suit = suit;
        this.faceUp = true;
    }

    /**
     * Returns the rank of this card: one of ACE through KING.
     */
    public Rank getRank() {
        // TODO You have to write this.
        return this.rank;
    }

    /**
     * Returns the suit of this card, one of CLUBS, SPADES, HEARTS, or DIAMONDS.
     */
    public Suit getSuit() {
        // TODO You have to write this.
        return this.suit;
    }

    /** Returns true if this card is face-up. */
    public boolean isFaceUp() {
        // TODO You have to write this.
        return faceUp;
    }

    /** Returns true if this card is red. */
    public boolean isRed() {
        // TODO You have to write this.
        if (suit == Suit.HEARTS || suit == Suit.DIAMONDS)
            return true;
        else
            return false;
    }

    /** Sets the face-up status of this card. */
    public void setFaceUp(boolean faceUp) {
        // TODO You have to write this.
        this.faceUp = faceUp;
    }

}
