package com.example.cs301proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button runTest;
    EditText editText;
    PresidentGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.runTest = findViewById(R.id.button);
        this.editText = findViewById(R.id.edittext);


        runTest.setOnClickListener(this);
        // Initializing the game
        this.game = new PresidentGame();
        game.setEditText(this.editText);

        ArrayList<HumanPlayer> players = new ArrayList<>();

        HumanPlayer player1 = new HumanPlayer(game);
        HumanPlayer player2 = new HumanPlayer(game);
        //HumanPlayer player3 = new HumanPlayer(game);
        //HumanPlayer player4 = new HumanPlayer(game);

        players.add(player1);
        players.add(player2);
        //players.add(player3);
        //players.add(player4);

        PresidentGameState game_state = new PresidentGameState(players,game);

        game.setPlayers(players);
        game.setGameState(game_state);

        // Proj E testing:
        // PLAYERS MUST BE >= 2
        // TODO: Meta-tester method that tests all of these for different numbers of players
        // TODO: Use deep copy for testing and have all these be called by Run Test button
        testDealCards(game_state.maxPlayers);
        testTurnRotation(game_state.maxPlayers);
        testClearDecks(game_state.maxPlayers);
        testCardStack();
        testPlayCards(game_state.maxPlayers);
    }

    /**
     * This test ensures that players are actually receiving cards from the GameState, and that
     * the sendInfo and receiveInfo methods on GameState and HumanPlayer are working respectively.
     */
    public void testDealCards(int players) {

        this.game.print("*****TEST 1 BEGIN*****");
        this.game.getGameState().dealCards();

        int flag = 0;

        for (int i = 0; i < players; i++) {
            HumanPlayer player = this.game.getPlayers().get(i);
            if (player.getDeck().getNumCards() == (52 / players)) {
                flag++;
            }
        }
        if (flag == players) {
            this.game.print("*****TEST 1 SUCCEEDED*****");
        } else {
            System.err.println("Test 1 failed.");
            System.exit(-10234);
        }
    }

    /**
     * This test ensures that the TurnCounter, pass() method in the Player class, and the
     * isPlayerTurn() method in the GameState class are functioning.
     *
     * Makes sure that a player cannot pass when it's not their turn.
     */
    public void testTurnRotation(int players) {

        this.game.print("*****TEST 2 BEGIN*****");

        HumanPlayer player1 = this.game.getPlayers().get(0);
        HumanPlayer player2 = this.game.getPlayers().get(1);

        PresidentGameState game_state = this.game.getGameState();

        boolean success = true;

        // Testing passing functionality:
        for (int i = 0; i < players; i++) {
            this.game.print("Current turn: " + game_state.getPlayerFromTurn().getId());
            HumanPlayer player = game_state.getPlayers().get(i);
            player.pass();
            if (game_state.getPlayerFromTurn().getId().equals(player.getId())) {
                success = false;
            }
        }

        // Testing to see if players are unable to pass when it's not their turn:
        for (int i = 0; i < players; i++) {
            HumanPlayer player = game_state.getPlayers().get(i);

            if (!game_state.isPlayerTurn(player)) {
                if (player.pass()) {
                    success = false;
                }
            }
        }


        if (success) {
            this.game.print("*****TEST 2 SUCCEEDED*****");
        } else {
            System.err.println("Test 2 failed.");
            System.exit(-10234);
        }

    }

    /**
     * This test ensures that the clearDecks() method in the GameState works. It clears the decks,
     * then checks to make sure that there are no cards in any of the player's decks.
     * @param players
     */
    public void testClearDecks(int players) {
        this.game.print("*****TEST 3 BEGIN*****");
        PresidentGameState game_state = this.game.getGameState();
        game_state.clearDecks();

        boolean success = true;

        // Checking that there are no cards in any player decks...
        for (int i = 0; i < players; i++) {
            HumanPlayer player = game_state.getPlayers().get(i);
            if (player.getDeck().getNumCards() != 0) {
                success = false;
            }
        }

        if (success) {
            this.game.print("*****TEST 3 SUCCESS*****");
        } else {
            System.err.println("Test 3 failed.");
            System.exit(-10234);
        }
    }

    /**
     * For ensuring that the CardStack's functions work properly. TODO This test is unfinished.
     */
    public void testCardStack() {

        this.game.print("*****TEST 4 BEGIN*****");
        CardStack card_stack = new CardStack();
        card_stack.set(new Card(1,1));
        card_stack.print();

        for (int i = 0; i < card_stack.getStackSize(); i++) {
            this.game.print("Card Stack: " + card_stack.getCard(i).getRank());
        }

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(2, 3));
        cards.add(new Card(2, 3));
        cards.add(new Card(2, 3));

        card_stack.set(cards);

        for (int i = 0; i < card_stack.getStackSize(); i++) {
            this.game.print("Card Stack: " + card_stack.getCard(i).getRank());
        }

        this.game.print("*****TEST 4 SUCCESS*****");

    }

    /**
     * This method tests the playing card functionality. It's pretty verbose at the moment, but it's
     * the bare minimum and it works.
     * TODO make this better!
     * @param players
     */
    public void testPlayCards(int players) {

        this.game.print("*****TEST 5 BEGIN*****");
        boolean success = true;
        Deck rigged_deck = new Deck();
        rigged_deck.generateRiggedDeck();

        PresidentGameState game_state = this.game.getGameState();
        game_state.dealRiggedCards(rigged_deck);

        HumanPlayer player1 = game_state.getPlayerFromTurn();
        // Play a rank 2 card...
        player1.selectCard(player1.getDeck().cards.get(1));

        boolean flag = player1.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
        }

        HumanPlayer player2 = game_state.getPlayerFromTurn();
        // Play a rank 3 card...
        player2.selectCard(player2.getDeck().cards.get(3));

        Log.i("Deck: ", player1.getDeck().toString());

        flag = player2.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
        }

        // Illegal move...
        player2.selectCard(player2.getDeck().cards.get(5));
        flag = player2.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
        }

        // Playing multiple cards at once
        ArrayList<Card> two_cards = new ArrayList<Card>();
        player1.selectCard(player1.getDeck().cards.get(6));
        player1.selectCard(player1.getDeck().cards.get(7));
        player1.selectCard(player1.getDeck().cards.get(8));

        flag = player1.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
        } else {
            this.game.print("Invalid move!");
        }


        // Attempting to play a single card
        player2.selectedCards.clear();
        player2.selectCard(player2.getDeck().cards.get(9));
        flag = player2.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
            success = false;
        } else {
            this.game.print("Invalid move!");
        }

        // Attempting to play 2 cards
        player2.selectCard(player2.getDeck().cards.get(9));
        flag = player2.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
            success = false;
        } else {
            this.game.print("Invalid move!");
        }


        // Attempting to play 3 cards
        player2.selectCard(player2.getDeck().cards.get(9));
        flag = player2.playCards();
        if (flag) {
            this.game.print(game_state.getPlayPile().toString());
        } else {
            this.game.print("Invalid move!");
        }


        Log.i("RIGGED SIZE:", "" + rigged_deck.getNumCards());

        if (success) {
            this.game.print("*****TEST 5 SUCCESS*****");
        } else {
            System.err.println("Test 5 failed.");
            System.exit(-10234);
        }

    }

    @Override
    public void onClick(View view) {
        this.editText.setText(game.getEditText());
    }
}
