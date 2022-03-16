package com.example.cs301proje;

/**
 * This should be used whenever a player passes. Called by the PresidentGame.sendInfo() method
 * inside of the Player class.
 */
public class PassAction extends GameAction {

    public PassAction(HumanPlayer sender) {
        super(sender);
        sender.getGame().print("Player " + sender.getId() + " attempted to pass");
    }

}
