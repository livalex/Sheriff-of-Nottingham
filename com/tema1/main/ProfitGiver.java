// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.players.Human;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import java.util.ArrayList;

public final class ProfitGiver {
    private static ProfitGiver profitGiver = null;

    private ProfitGiver() {
    }

    public static ProfitGiver getInstance() {
        if (profitGiver == null) {
            profitGiver = new ProfitGiver();
        }
        return profitGiver;
    }

    // We take a look at all the goods that made it past the sheriff
    // And we give the profit and the bonuses from the illegal goods
    // To the players.
    public ArrayList<Human> getProfit(final ArrayList<Human> players) {
        // Dam banii de pe bunurile de pe taraba la sfarsitul jocului
        // pt fiecare jucator
        for (Human human : players) {
            for (Goods goods : human.getFinalBag()) {
                if (goods.getType() == GoodsType.Legal) {
                    int moneyToGive = human.getMoney() + goods.getProfit();
                    human.setMoney(moneyToGive);
                } else {
                    int moneyToGive = human.getMoney() + goods.getProfit();
                    for (Goods good : goods.getIllegalBonus().keySet()) {
                        moneyToGive += goods.getIllegalBonus().get(good) * good.getProfit();
                        human.setMoney(moneyToGive);
                    }
                }
            }
        }
        return players;
    }
}
