// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.players.Human;

import java.util.ArrayList;

public final class PlayersDisplay {
    private static PlayersDisplay playersDisplay = null;

    private PlayersDisplay() {
    }

    public static PlayersDisplay getInstance() {
        if (playersDisplay == null) {
            playersDisplay = new PlayersDisplay();
        }
        return playersDisplay;
    }

    // Display the output.
    public void displayPlayers(final ArrayList<Human> players) {
        for (Human player : players) {
            if (player.getPlayerType() == 0) {
                System.out.println(player.getPlayerId() + " " + "BASIC" + " " + player.getMoney());
            }

            if (player.getPlayerType() == 1) {
                System.out.println(player.getPlayerId() + " " + "GREEDY" + " " + player.getMoney());
            }

            if (player.getPlayerType() == 2) {
                System.out.println(player.getPlayerId() + " " + "BRIBED" + " " + player.getMoney());
            }
        }
    }
}
