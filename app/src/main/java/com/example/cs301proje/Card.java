package com.example.cs301proje;

public class Card {
    private final int rank;
    private final int suite;

    private boolean isSelected; // Instead of deleting selected cards from the original deck,
                                // we can just add a flag to the cards

    public Card(int rank, int suite) {
        this.rank = rank;
        this.suite = suite;
        this.isSelected = false;
    }

    // Copy ctor for Card class
    public Card(Card orig) {
        this.rank = orig.rank;
        this.suite = orig.suite;
        this.isSelected = false;
    }

    public int getRank() {
        return rank;
    }

    public int getSuite() { return suite; }

    public boolean cardEquals(Card that) {
        return that.getRank() == this.rank && that.getSuite() == this.suite;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
