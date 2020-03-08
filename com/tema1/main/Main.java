// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.goods.GoodsFactory;
import com.tema1.players.Human;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import com.tema1.comparators.PlayersComparator;

public final class Main {
    private Main() {
        // just to trick checkstyle
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        GoodsFactory goodsFactory = GoodsFactory.getInstance();

        PlayersComparator playersComparator = new PlayersComparator();

        // Made this classes singleton because we use them just once.
        PlayersCreator playersCreator = PlayersCreator.getInstance();
        VerifyPlayers verifyPlayers = VerifyPlayers.getInstance();
        ProfitGiver profitGiver = ProfitGiver.getInstance();
        FrequencyGiver frequencyGiver = FrequencyGiver.getInstance();
        BonusGiver bonusGiver = BonusGiver.getInstance();
        PlayersDisplay playersDisplay = PlayersDisplay.getInstance();

        ArrayList<Human> players;

        // Populate the queue with the input cards.
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < gameInput.getAssetIds().size(); ++i) {
            queue.add(gameInput.getAssetIds().get(i));
        }

        players = playersCreator.createPlayers(gameInput);
        players = verifyPlayers.verificationPost(players, gameInput, queue, goodsFactory);
        players = profitGiver.getProfit(players);
        players = frequencyGiver.giveFrequncy(players, goodsFactory);
        players = bonusGiver.giveBonus(players, goodsFactory, gameInput);

        Collections.sort(players, playersComparator);

        playersDisplay.displayPlayers(players);
    }
}
