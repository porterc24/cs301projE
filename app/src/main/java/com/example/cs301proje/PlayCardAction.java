package com.example.cs301proje;

/**
 * @author Max Woods
 *
 * This is used whenever the player plays some cards.
 */
public class PlayCardAction extends GameAction{

    private CardStack played_cards; // The cards the player has selected and decided to play

    public PlayCardAction(HumanPlayer sender, CardStack played_cards) {
        super(sender);
        this.played_cards = played_cards;
        sender.getGame().print("Player " + sender.getId() + " attempted to play " + played_cards.toString());
    }

    public CardStack getPlayedCards() {
        return played_cards;
    }
}
