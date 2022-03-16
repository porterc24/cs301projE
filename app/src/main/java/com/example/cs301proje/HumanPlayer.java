package com.example.cs301proje;

import java.util.List;
import java.util.UUID;

/**
 * @author Margo Brown
 * @author Claire Porter
 * @author Renn Torigoe
 * @author Max Woods
 *
 * Manages information about HumanPlayers. Contains methods for playing cards, passing, etc.
 * Information is received from the GameState/Game via the receiveInfo() method
 */
// TODO: Make a 'Player' parent/abstract class
public class HumanPlayer {

    private Deck deck;
    private Deck validCards;
    private CardStack selectedCards;

    private PresidentGame game;

    int score;

    // This UUID is automatically generated each time a new HumanPlayer is made.
    // It's useful for comparing two player objects.
    private final UUID id; // TODO Maybe make a PlayerIDFactory for assigning simple integer IDs instead of UUID?
    boolean isOut;

    public HumanPlayer(Deck deck) {
        this.id = UUID.randomUUID();
        this.deck = deck;
        this.score = 0;
        this.validCards = new Deck();
        this.selectedCards = new CardStack();
    }

    public HumanPlayer(PresidentGame game) {
        this.game = game;
        this.id = UUID.randomUUID();
        this.deck = new Deck();
        this.score = 0;
        this.validCards = new Deck();
        this.selectedCards = new CardStack();
    }

    /**
     * Called whenever the GameState class sends information to the player.
     * @param action the information sent
     */
    public void receiveInfo(GameAction action) {
        if (action instanceof DealCardAction) {
            this.deck.addCard(((DealCardAction) action).getCard());
        }

        if (action instanceof ClearDeckAction) {
            this.deck = new Deck();
        }
    }
        /**
         * Executes a pass action on this turn. TODO: better message
         */
    public boolean pass() {
        return this.game.sendInfo(new PassAction(this));
    }

    public void selectCard(Card card) {
        this.selectedCards.add(card);
    }

    public void selectCards(List<Card> cards) {
        this.selectedCards.set(cards);
    }

    /**
     * Attempts to play the current hand. Resets the currently selected cards if successful. If
     * the play was successful, the game progresses to the next turn.
     * TODO If attempt was valid, remove the cards that were just played from the player's hand.
     * @return whether or not the play was successful
     */
    public boolean playCards() {

        boolean flag = this.game.sendInfo(new PlayCardAction(this, this.selectedCards));

        // If the card was successfully played...
        if (flag) {
            this.selectedCards.clear();
        }

        return flag;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public UUID getId() {return this.id; }

    public int getScore() {return this.score;}

    public boolean getIsOut() {return this.isOut;}

    public CardStack getSelectedCardStack() { return this.selectedCards; }

    public Deck getValidCards() { return validCards; }

    public void printCards() {
        this.game.print(this.deck.toString());
    }

    public PresidentGame getGame() {
        return this.game;
    }
}
