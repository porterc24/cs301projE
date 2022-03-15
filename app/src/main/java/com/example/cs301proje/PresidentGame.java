package com.example.cs301proje;

import java.util.ArrayList;

/**
 * This game class manages basically everything. It handles the sending of information between
 * GameStates and players.
 */
public class PresidentGame {

    private ArrayList<HumanPlayer> players;
    private PresidentGameState game_state;


    // For sending GameState info to players:
    public void sendInfo(PresidentGameState game_state, ArrayList<HumanPlayer> players) {

    }

    // For sending GameAction info to GameState:
    public boolean sendInfo(GameAction action) {
        return this.game_state.receiveInfo(action);
    }

    public ArrayList<HumanPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<HumanPlayer> players) {
        this.players = players;
    }

    public PresidentGameState getGameState() {
        return game_state;
    }

    public void setGameState(PresidentGameState game_state) {
        this.game_state = game_state;
    }
}
