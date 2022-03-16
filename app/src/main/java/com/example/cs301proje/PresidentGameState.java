package com.example.cs301proje;

import android.util.Log;
import java.util.ArrayList;

public class PresidentGameState {
    // TODO
    // implement a method that deals with invalid moves (when a player cannot play a card)

    // IMPORTANT: If you add a new instance variable, make sure you update the
    // deep copy ctor!!!!!
    int maxPlayers;
    int currentStage;

    ArrayList<HumanPlayer> players;
    TurnCounter currTurn;
    CardStack inPlayPile;
    Deck discardPile;
    CurrentState state;
    PresidentGame game;

    /**
     * Default setup for a game.
     * @param players The players to be added to the game
     */
    public PresidentGameState(ArrayList<HumanPlayer> players, PresidentGame game) {

        this.players = players;
        this.currentStage = 0;
        this.maxPlayers = players.size();
        this.discardPile = new Deck();
        this.game = game;
        this.currTurn = new TurnCounter(this.maxPlayers);

        // At the start of the game, the first player is the first person to put down a card.
        // The first card is set to null so that the game knows it's valid for that player to
        // put down a card on the first turn.
        this.inPlayPile = new CardStack();

        state = CurrentState.INIT_ARRAYS;

    }

    public PresidentGameState() {
        this.currentStage = 0;
        this.discardPile = new Deck();
        this.currTurn = new TurnCounter(this.maxPlayers);
        this.inPlayPile = new CardStack();

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
        this.inPlayPile = new CardStack(orig.inPlayPile);
        this.game = orig.game;

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
    public void dealCards() {
        state = CurrentState.GAME_SETUP;
        Deck masterDeck = new Deck(new ArrayList<Card>());
        masterDeck.generateMasterDeck();


        for (HumanPlayer player : this.players) {
            for (int i = 0; i < (52 / players.size()); i++) {
                //selects a random card of the 52 in masterDeck
                Card randomCard = (masterDeck.cards.get((int) Math.random() * masterDeck.MAX_CARDS));

                this.game.sendInfo(new DealCardAction(
                        null,
                        randomCard
                ), player);
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
     * Instead of generating a master deck, this method takes a pre-made deck and deals it out
     * to the players. Useful for testing.
     */
    public void dealRiggedCards(Deck rigged_deck) {
        state = CurrentState.GAME_SETUP;

        for (HumanPlayer player : this.players) {
            for (int i = 0; i < (52 / players.size()); i++) {
                Card riggedCard = (rigged_deck.cards.get(i));
                this.game.sendInfo(new DealCardAction(
                        null,
                        riggedCard
                ), player);
                //Log.i("DECKS", "Value of i: " + i);
            }
        }

        // THIS IS FOR TESTING/DEBUG
        this.players.forEach(p -> {
            Log.i("RIGGED","PLAYER: " + p.getDeck().toString());
        });

        state = CurrentState.MAIN_PLAY;
    }

    public CardStack getPlayPile() {
        return inPlayPile;
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
        if (getPlayerFromTurn().getId().equals(player.getId())) {
            return true;
        }
        return false;
    }

    /**
     * Returns the HumanPlayer who's turn it is.
     * @return
     */
    public HumanPlayer getPlayerFromTurn() {
        return this.players.get(currTurn.turn-1); // Turn counter starts from 1
    }

    /**
     * checks that the first index of the passed deck (selectedCards)'s rank is higher than the
     * first index of the inPlayPile's rank, and whether the length of the (selectedCards) is longer
     * than or equal to the inPlayPile's length
     *
     * inPlayPile is a max length 4, so
     * @param deck
     * @return
     */

    // potential bug - currently initializing inPlayPile with a length of 0, may mess up when

    public boolean isValidMove(Deck deck) {
        //TODO
        if (deck.cards.get(0).getRank() > inPlayPile.getStackRank()) {
            if (deck.cards.size() >= inPlayPile.getStackSize()) {
                return true;
            }
        }
        // cannot play cards, illegal move
        // with this implementation shouldn't be running
        return false;
    }

    /**
     * "plays" the card to the top of the inPlayPile, returns true if successful, else returns false
     * @param player
     * @return boolean
     */
    public boolean playCards(HumanPlayer player) {
        if (isPlayerTurn(player)) {
            inPlayPile.set(player.getSelectedCardStack().getCards());
            inPlayPile.print();
            this.currTurn.nextTurn();
            return true;
        }
        return false;
    }

    public boolean pass(HumanPlayer player) {
        if (isPlayerTurn(player)) {
            this.currTurn.nextTurn();
            return true;
        }
        return false;
    }

    public void clearDecks() {
        this.players.forEach(p -> {
            this.game.sendInfo(new ClearDeckAction(null), p);
        });
    }

    /**
     * loops through the players hand and finds the valid cards, then selects an equal amount of a
     * valid card rank (i.e. two 9s, three Kings etc.)
     * @param player
     * @return Deck
     */

    // function is edited for Proj. E, and isn't functional for gameplay

    public CardStack selectCards(HumanPlayer player) {
        player.getSelectedCardStack().clear();
        ArrayList<Card> card_buffer = new ArrayList<>();

        // checking if the player is the first to play cards
        if (inPlayPile.getStackSize() == 0) {
            // pick a random card from the player's deck
            Card card = player.deck.cards.get((int) Math.random() * player.deck.cards.size());

            // add all instances of that card to the selectedCards deck
            for (Card c: player.deck.cards) {
                if (c.getRank() == card.getRank()) {
                    card_buffer.add(c);
                }
            }
        }
        else {
            // looping through the player's cards and adding the valid ones to the validCards deck
            for (Card card: player.deck.cards) {
                if (card.getRank() >= inPlayPile.getStackRank()) {
                    player.validCards.addCard(card);
                }
            }
        }

        // repeat while the player's selectedCards doesn't equal the inPlayPile's cards
        // i.e. the player only selects one higher card when a pair of cards is in play

        while (player.selectedCards.getStackSize() < inPlayPile.getStackSize()) {
            // clear the deck from any past iterations of the loop
            player.getSelectedCardStack().clear();

            // picking a random card from the validCards deck to play
            Card selectedMoveCard = player.validCards.cards.get((int) (Math.random() * player.validCards.cards.size()));

            // looping through the player's valid cards and adding all of the same rank cards to the
            // selectedCards deck
            for (Card card: player.validCards.cards) {
                if (card.getRank() == selectedMoveCard.getRank()) {
                    player.selectedCards.add(card);
                }
            }
        }
        return player.selectedCards;
    }

    /**
     * This is how the GameState receieves information from other Player objects.
     * PlayCardAction and PassAction are basically the only two things a player can do
     * in this game.
     * @param action The GameAction the player has made.
     * @return Whether or not the move was valid or not
     */
    public boolean receiveInfo(GameAction action) {

        // If the player has tried to play a card...
        // NOT TESTED! Have no idea if this will work correctly!
        if (action instanceof PlayCardAction) {
            HumanPlayer player = action.getSender();
            PlayCardAction pca = (PlayCardAction) action;

            if (isPlayerTurn(player)) {
                if (this.inPlayPile.getStackSize() == 0) { // If it's the very first turn...
                    this.game.print("Playing first card.");
                    playCards(player);
                    return true;
                }

                CardStack selected_cards = pca.getPlayedCards();
                // Rules for playing cards
                if (this.inPlayPile.getStackSize() <= selected_cards.getStackSize()) {
                    if (this.inPlayPile.getStackRank() < selected_cards.getStackRank()) {
                        this.game.print("Card rank is greater. Playing card.");
                        playCards(player);
                        return true;
                    }
                }

                return false;
            } else {
                return false;
            }
        } // PlayCardAction

        // If the player passess, go to the next turn
        else if (action instanceof PassAction) {
            HumanPlayer player = action.getSender();
            return pass(player);
        } // PassAction

        return false;
    } // receiveInfo

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

    public void setPlayers(ArrayList<HumanPlayer> players) {
        this.players = players;
        this.maxPlayers = players.size();
        this.currTurn = new TurnCounter(this.maxPlayers);
    }

    public void setGame(PresidentGame game) {
        this.game = game;
    }
}
