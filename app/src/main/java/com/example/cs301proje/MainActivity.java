package com.example.cs301proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
        runTest1();
    }

    /**
     * This test ensures that players are actually receiving cards from the GameState, and that
     * the sendInfo and receiveInfo methods on GameState and HumanPlayer are working respectively.
     */
    public void runTest1() {

        this.game.print("*****BEGINNING TEST 1*****");

        HumanPlayer player1 = this.game.getPlayers().get(0);
        HumanPlayer player2 = this.game.getPlayers().get(1);

        this.game.print("*****PLAYER 1 HAND*****");
        player1.printCards();
        this.game.print("*****PLAYER 2 HAND*****");
        player2.printCards();
    }

    @Override
    public void onClick(View view) {
        this.editText.setText(game.getEditText());
    }
}
