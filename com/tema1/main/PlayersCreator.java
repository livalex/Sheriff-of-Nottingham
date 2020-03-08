// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.players.Human;
import com.tema1.players.BasicPlayer;
import com.tema1.players.GreedyPlayer;
import com.tema1.players.BribePlayer;
import java.util.ArrayList;

public final class PlayersCreator {
    private static PlayersCreator playersCreator = null;

    private PlayersCreator() {
    }

    public static PlayersCreator getInstance() {
        if (playersCreator == null) {
            playersCreator = new PlayersCreator();
        }
        return playersCreator;
    }

    // Here we create all the objects depending on the strategy.
    public ArrayList<Human> createPlayers(final GameInput gameInput) {
        ArrayList<Human> players = new ArrayList<>();

        int sizeOfInput = gameInput.getPlayerNames().size();
        for (int i = 0; i < sizeOfInput; ++i) {
            String strategy = gameInput.getPlayerNames().get(i);
            if (strategy.equals("basic")) {
                players.add(new BasicPlayer(0, i));
            }
            if (strategy.equals("greedy")) {
                players.add(new GreedyPlayer(1, i));
            }
            if (strategy.equals("bribed")) {
                players.add(new BribePlayer(2, i));
            }
        }
        return players;
    }
}
