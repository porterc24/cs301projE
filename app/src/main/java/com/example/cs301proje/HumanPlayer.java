package com.example.cs301proje;

import java.util.UUID;

public class HumanPlayer {
    Deck deck;
    int score;
    Deck validCards;
    Deck selectedCards;

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

    public HumanPlayer() {
        this.id = UUID.randomUUID();
        this.deck = new Deck();
        this.score = 0;
        this.validCards = new Deck();
        this.selectedCards = new Deck();
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
