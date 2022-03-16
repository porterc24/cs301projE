package com.example.cs301proje;

/**
 * @author Max Woods
 *
 * Sent by the GameState whenever cards are dealt. Tells the player what card to add to their deck.
 */
public class DealCardAction extends GameAction{

    private Card card;

    public DealCardAction(HumanPlayer sender, Card card) {
        super(sender);
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

}
