package com.example.cs301proje;

import java.util.UUID;

// TODO: Make a 'Player' parent/abstract class
public class HumanPlayer {
    Deck deck;
    Deck validCards;
    Deck selectedCards;

    PresidentGame game;

    int score;

    // This UUID is automatically generated each time a new HumanPlayer is made.
    // It's useful for comparing two player objects.
    UUID id;
    boolean isOut;

    public HumanPlayer(Deck deck) {
        this.id = UUID.randomUUID();
        this.deck = deck;
        this.score = 0;
        this.validCards = new Deck();
        this.selectedCards = new Deck();
    }

    public HumanPlayer(PresidentGame game) {
        this.game = game;
        this.id = UUID.randomUUID();
        this.deck = new Deck();
        this.score = 0;
        this.validCards = new Deck();
        this.selectedCards = new Deck();
    }

    /**
     * This method sends the currently selected cards to the GameState.
     * @return whether or not the action was accepted
     */
    public boolean playCards() {
        return this.game.sendInfo(new PlayCardAction(this, this.selectedCards));
    }

    /**
     * Executes a pass action on this turn.
     * @return whether or not the pass action was accepted
     */
    public boolean pass() {
        return this.game.sendInfo(new PassAction(this));
    }

    public void selectCard(Card card) {
        card.setSelected(true);
        this.selectedCards.addCard(card);
    }

    public void deselectCard(Card card) {
        card.setSelected(false);
        this.selectedCards.removeCard(card);
    }

    public Deck getDeck() {
        return this.deck;
    }

    public UUID getId() {return this.id; }

    public int getScore() {return this.score;}

    public boolean getIsOut() {return this.isOut;}

    public Deck getSelectedCards() { return this.selectedCards; }

    public void clearDeck(Deck deck) {
        for (int i = 0; i < deck.cards.size(); i++) {
            deck.cards.remove(i);
        }
    }

    public Deck getValidCards() { return validCards; }
}
