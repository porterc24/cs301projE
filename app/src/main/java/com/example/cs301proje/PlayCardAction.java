package com.example.cs301proje;

/**
 * This is used whenever the player plays some cards.
 */
public class PlayCardAction extends GameAction{

    private Deck played_cards; // The cards the player has selected and decided to play

    public PlayCardAction(HumanPlayer sender, Deck played_cards) {
        super(sender);
        this.played_cards = played_cards;
    }

    public Deck getPlayedCards() {
        return played_cards;
    }
}
