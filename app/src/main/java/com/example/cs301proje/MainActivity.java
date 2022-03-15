package com.example.cs301proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button runTest = findViewById(R.id.button);
    EditText editText = findViewById(R.id.edittext);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTest.setOnClickListener(this);

        // Initializing the game
        PresidentGame game = new PresidentGame();

        ArrayList<HumanPlayer> players = new ArrayList<>();
        HumanPlayer player1 = new HumanPlayer(game);
        HumanPlayer player2 = new HumanPlayer(game);

        players.add(player1);
        players.add(player2);

        PresidentGameState game_state = new PresidentGameState(players);

        game.setPlayers(players);
        game.setGameState(game_state);
    }

    @Override
    public void onClick(View view) {
        editText.setText("");
        
       }
    }
