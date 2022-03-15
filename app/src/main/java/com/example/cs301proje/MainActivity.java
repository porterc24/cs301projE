package com.example.cs301proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

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
        // PLAYERS MUST BE > 2
        testDealCards(game_state.maxPlayers);
        testTurnRotation(game_state.maxPlayers);
    }

    /**
     * This test ensures that players are actually receiving cards from the GameState, and that
     * the sendInfo and receiveInfo methods on GameState and HumanPlayer are working respectively.
     */
    public void testDealCards(int players) {

        this.game.print("*****BEGINNING TEST 1*****");
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
     */
    public void testTurnRotation(int players) {

        this.game.print("*****TEST 2 BEGIN*****");

        HumanPlayer player1 = this.game.getPlayers().get(0);
        HumanPlayer player2 = this.game.getPlayers().get(1);

        PresidentGameState game_state = this.game.getGameState();

        boolean success = true;

        for (int i = 0; i < players; i++) {
            this.game.print("Current turn: " + game_state.getPlayerFromTurn().getId());
            HumanPlayer player = game_state.getPlayers().get(i);
            player.pass();
            if (game_state.getPlayerFromTurn().getId().equals(player.getId())) {
                success = false;
            }
        }

        // Now try passing when it's not their turn:
        if (game_state.getPlayers().get(1).pass()) {
            success = false;
        }

        if (success) {
            this.game.print("*****TEST 2 SUCCEEDED*****");
        } else {
            System.err.println("Test 2 failed.");
            System.exit(-10234);
        }

    }

    @Override
    public void onClick(View view) {
        this.editText.setText(game.getEditText());
    }
}
