// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.comparators;

import com.tema1.players.Human;
import java.util.Comparator;

// Sorts players by their money for final display.
public final class PlayersComparator implements Comparator<Human> {
    @Override
    public int compare(final Human h1, final Human h2) {
        if (h1.getMoney() != h2.getMoney()) {
            return h2.getMoney() - h1.getMoney();
        }

        if (h1.getMoney() == h2.getMoney()) {
            return h1.getPlayerId() - h2.getPlayerId();
        }

        return 0;
    }
}
