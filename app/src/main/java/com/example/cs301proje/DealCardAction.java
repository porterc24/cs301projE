package com.example.cs301proje;

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
