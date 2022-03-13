package com.example.cs301proje;

import android.util.Log;
import java.util.ArrayList;

public class PresidentGameState {

    // IMPORTANT: If you add a new instance variable, make sure you update the
    // deep copy ctor!!!!!
    int maxPlayers;
    int currentStage;

    ArrayList<HumanPlayer> players;
    TurnCounter currTurn;
    Deck inPlayPile;
    Deck discardPile;
    CurrentState state;

    /**
     * Default setup for a game.
     * @param players The players to be added to the game
     */
    public PresidentGameState(ArrayList<HumanPlayer> players) {
        this.players = players;
        this.currentStage = 0;
        this.maxPlayers = players.size();
        this.discardPile = new Deck();
        this.inPlayPile = new Deck(4);

        this.currTurn = new TurnCounter(this.maxPlayers);

        dealCards();

        state = CurrentState.INIT_ARRAYS;

    }

    // Deep copy ctor for PresidentGameState
    public PresidentGameState(PresidentGameState orig) {

        this.players = orig.players; // Players should not be deep copied (passed by ref)

        // Mutable primitives
        this.maxPlayers = orig.maxPlayers;
        this.currentStage = orig.currentStage;

        // Mutable class types
        this.discardPile = new Deck(orig.discardPile);
        this.currTurn = new TurnCounter(orig.currTurn);

        state = CurrentState.INIT_OBJECTS;
    }

    /**
     * This method prunes out all players except the given parameter from this GameState.
     * Useful for when you want to hide information.
     * @param player the player to be kept in the GameState. All other players are removed.
     */
    public void prunePlayers(HumanPlayer player) {
        this.players.forEach(p -> {
            if (p.getId() != player.getId()) {
                this.players.remove(p);
            }
        });
    }

    /**
     * This method is used to deal the cards at the start of the game.
     * First, it generates the full "master deck" of 52 cards. Then,
     * it takes out slices of that master deck and deals them
     * out to the players.
     */
    private void dealCards() {
        state = CurrentState.GAME_SETUP;
        Deck masterDeck = new Deck(new ArrayList<Card>());
        masterDeck.generateMasterDeck();


        for (HumanPlayer player: this.players) {
            for (int i = 0; i < (52 / players.size()); i++) {
                //selects a random card of the 52 in masterDeck
                Card randomCard = (masterDeck.cards.get((int) Math.random() * masterDeck.MAX_CARDS));

                //adds the card to the players deck/hand and removes it from masterDeck
                player.deck.cards.add(randomCard);
                masterDeck.cards.remove(randomCard);
                //Log.i("DECKS", "Value of i: " + i);
            }
        }

        // THIS IS FOR TESTING/DEBUG
        this.players.forEach(p -> {
            Log.i("DECKS","PLAYER: " + p.getDeck().toString());
        });

        state = CurrentState.MAIN_PLAY;
    }

    /**
     * This method prints out returns a String with the attributes of the current PresidentGameState
     * @return info.toString() returns the StringBuilder with the info
     */

    @Override
    public String toString() {
        int playerNo = 1;
        StringBuilder info = new StringBuilder("{GameState Info: maxPlayers = " + maxPlayers + ", currTurn = " + currTurn +
                ", Players [ ");
        for (HumanPlayer player: players) {
            info.append("(Player " + playerNo + ", ID: " + player.getId() + ", Cards: ");
            for (Card card: player.deck.cards) {
                info.append("{ " + CardValues.getCardValue(card.getRank()) + " of " + CardSuites.getSuiteName(card.getSuite()) + " } ");
            }
            info.append(", " + "Points: " + player.getScore() + " ) \n");
            playerNo++;
        }

        info.append(" ]}");
        return info.toString();
    }

    /**
     * This method checks if it's a player's turn.
     * @param player the player's turn to be checked
     * @return TRUE if it's the player's turn, FALSE if not
     */
    public boolean isPlayerTurn(HumanPlayer player) {
        for (int i = 0; i < maxPlayers; i++) {
            if ((i+1) == currTurn.turn) { // Need to add '1' to 'i' because turns start from 1
                if (player.getId() == players.get(i).getId()) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isValidMove(Deck deck) {
        //TODO
        return true;
    }



    public boolean playCard(HumanPlayer player) {
        if (isPlayerTurn(player)) {
            return true;
        }
        return false;
    }

    public boolean pass(HumanPlayer player) {
        if (isPlayerTurn(player)) {
            return true;
        }
        return false;
    }

    public Deck selectCards(HumanPlayer player) {
        // looping through the player's cards and adding the valid ones to the validCards deck
        for (Card card: player.deck.cards) {
            if (card.getRank() >= inPlayPile.returnCards().get(0).getRank()) {
                player.validCards.addCard(card);
            }
        }

        // repeat while the player's selectedCards doesn't equal the inPlayPile's cards
        // i.e. the player only selects one higher card when a pair of cards is in play
        while (player.selectedCards.cards.size() < inPlayPile.cards.size()) {
            // clear the deck from any past iterations of the loop
            player.clearDeck(player.selectedCards);

            // picking a random card from the validCards deck to play
            Card selectedMoveCard = player.validCards.cards.get((int) (Math.random() * player.validCards.cards.size()));

            // looping through the player's valid cards and adding all of the same rank cards to the
            // selectedCards deck
            for (Card card: player.validCards.cards) {
                if (card.getRank() == selectedMoveCard.getRank()) {
                    player.selectedCards.addCard(card);
                }
            }
        }
        return player.selectedCards;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public ArrayList<HumanPlayer> getPlayers() {
        return players;
    }

    public TurnCounter getCurrTurn() {
        return currTurn;
    }

    public Deck getDiscardPile() {
        return discardPile;
    }

    public boolean endGame(ArrayList<HumanPlayer> players){
        int out = 0;
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getIsOut() == false) {
                out++;
            }
        }
        if(out == players.size() - 1){
            state = CurrentState.GAME_END;
            return true;
        }
        return false;
    }
}
