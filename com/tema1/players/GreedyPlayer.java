// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

import java.util.ArrayList;
import java.util.Queue;

public class GreedyPlayer extends BasicPlayer {

    public GreedyPlayer() {
    }

    public GreedyPlayer(final int type, final int playerId) {
        setPlayerType(type);
        setPlayerId(playerId);
    }

    @Override
    public final void commerciant(final int roundNumber, final Queue<Integer> queue,
                                  final GoodsFactory goodsFactory) {
        super.commerciant(roundNumber, queue, goodsFactory);
        // In case the round is even.
        if (roundNumber % 2 == 0) {

            // Find out if he has illegal cards.
            int ok = 0;
            for (Goods goods : getCards()) {
                if (goods.getType().equals(GoodsType.Illegal)) {
                    ok = 1;
                }
            }

            // Takes the illegal card with the biggest profit and
            // Puts it in his bag.
            ArrayList<Goods> maxIllegalProfitGoods = new ArrayList<>();
            int maxIllegalProfit = -1;
            if (ok == 1) {
                if (getBag().size() < Constants.CARDS_IN_HAND) {
                    for (Goods goods : getCards()) {
                        if (goods.getType() == GoodsType.Illegal) {
                            if (goods.getProfit() > maxIllegalProfit) {
                                maxIllegalProfit = goods.getProfit();
                                // Make sure we only have one card.
                                if (!maxIllegalProfitGoods.isEmpty()) {
                                    maxIllegalProfitGoods.clear();
                                }
                                maxIllegalProfitGoods.add(goods);
                            }
                        }
                    }
                    // Put the card in the bag after selection.
                    if (!maxIllegalProfitGoods.isEmpty()) {
                        getBag().add(maxIllegalProfitGoods.get(0));
                    }
                }
            }

        }
    }

    @Override
    public final void sheriff(final Human human, final GameInput gameInput) {
        // If the trader gives bribe, the sheriff takes it
        // And lets the trader go.
        if (human.getBribe() > 0) {
            int money1 = this.getMoney() + human.getBribe();
            this.setMoney(money1);

            int money2 = human.getMoney() - human.getBribe();
            human.setMoney(money2);

            for (Goods goods : human.getBag()) {
                human.getFinalBag().add(goods);
            }
        } else {
            super.sheriff(human, gameInput);
        }
    }
}
