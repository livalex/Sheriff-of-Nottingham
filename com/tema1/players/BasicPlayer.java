// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.players;

import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;
import com.tema1.goods.Goods;
import com.tema1.common.Constants;

import java.util.Queue;
import java.util.ArrayList;

public class BasicPlayer extends Human {

    public BasicPlayer() {
    }

    public BasicPlayer(final int type, final int playerId) {
        setPlayerType(type);
        setPlayerId(playerId);
    }

    /**
     * The commerciant strategy for the Bribe player.
     * @param roundNumber the round of the game.
     * @param queue the queue to take the cards from.
     * @param goodsFactory goods factory to generate cards.
     */
    @Override
    public void commerciant(final int roundNumber, final Queue<Integer> queue,
                            final GoodsFactory goodsFactory) {
        setBribe(0);

        // extract 10 cards from the queue
        for (int i = 0; i < Constants.CARDS_DRAWING; ++i) {
            int temp = queue.remove();
            getCards().add(goodsFactory.getGoodsById(temp));
        }

        // check if there are any legal goods
        int ok = 0;
        for (Goods goods : getCards()) {
            if (goods.getType().equals(GoodsType.Legal)) {
                ok = 1;
            }
        }

        // If there are any illegal goods put in the bag
        // The card with the biggest profit.
        int illegalValue = -1;
        int toDelete = -1;
        // If there are only illegal goods.
        if (ok == 0) {
            for (Goods goods : getCards()) {
                if (goods.getProfit() > illegalValue) {
                    illegalValue = goods.getProfit();
                    // Make sure to get just one card.
                    if (!getBag().isEmpty()) {
                        getBag().clear();
                    }
                    getBag().add(goods);
                    toDelete = goods.getId();
                }
            }

            setBagContent(0);

            // We delete the card from the first 10.
            for (int i = 0; i < getCards().size(); ++i) {
                Goods variable = getCards().get(i);
                if (variable.getId() == toDelete) {
                    getCards().remove(variable);
                    break;
                }
            }
            // If there are llegal goods.
        } else if (ok == 1) {
            // Put the frequency of the legal goods in the map.
            for (Goods goods : getCards()) {
                if (goods.getType().equals(GoodsType.Legal)) {
                    if (getFreq().containsKey(goods)) {
                        getFreq().put(goods, getFreq().get(goods) + 1);
                    } else {
                        getFreq().put(goods, 1);
                    }
                }
            }

            // Find out the item with the biggest frequency.
            int maxFreq = -1;
            for (Goods goods : getCards()) {
                if (goods.getType().equals(GoodsType.Legal)) {
                    if (getFreq().get(goods) > maxFreq) {
                        maxFreq = getFreq().get(goods);
                    }
                }
            }

            // We put the biggest frequency cards in this vector
            // in order to find out the biggest profit.
            ArrayList<Goods> maxFreqGoods = new ArrayList<>();
            for (Goods goods : getCards()) {
                if (goods.getType().equals(GoodsType.Legal)) {
                    if (getFreq().get(goods) == maxFreq) {
                        maxFreqGoods.add(goods);
                    }
                }
            }

            // Find out the biggest profit card with the biggest
            // Frequency.
            int maxProfit = -1;
            for (Goods goods : maxFreqGoods) {
                if (goods.getProfit() > maxProfit) {
                    maxProfit = goods.getProfit();
                }
            }

            // Put the cards with the biggest frequency and the biggest
            // Profit in this vector.
            ArrayList<Goods> maxFreqProfitGoods = new ArrayList<>();
            for (Goods goods : maxFreqGoods) {
                if (goods.getProfit() == maxProfit) {
                    maxFreqProfitGoods.add(goods);
                }
            }

            // Find out the biggest Id.
            int maxID = -1;
            for (Goods goods : maxFreqProfitGoods) {
                if (goods.getId() > maxID) {
                    maxID = goods.getId();
                }
            }

            // Put the final cards in the bag.
            for (Goods goods : maxFreqProfitGoods) {
                if (goods.getId() == maxID) {
                    getBag().add(goods);
                    setBagContent(goods.getId());
                }
            }
        }
    }

    /**
     * The sheriff strategy for the Bribe player.
     * @param human takes a trader to verify him.
     * @param gameInput to put the confiscated goods in the back
     *                  of the deck of cards.
     */
    @Override
    public void sheriff(final Human human, final GameInput gameInput) {
        if (getMoney() >= Constants.MIN_SUM) {
            ArrayList<Integer> itemsToBeTrashed = new ArrayList<>();
            int honestyFlag = 1;
            int penalty = 0;
            for (int i = 0; i < human.getBag().size(); ++i) {
                Goods g = human.getBag().get(i);
                if (g.getId() != human.getBagContent()) {
                    // The trader lies.
                    honestyFlag = 0;
                    // Calculate the penalty.
                    penalty += g.getPenalty();
                    int specificId = g.getId();
                    // Put the card int the back of the deck and
                    // Put it in the vector to be removed.
                    gameInput.getAssetIds().add(specificId);
                    itemsToBeTrashed.add(g.getId());
                }
            }

            // Remove the cards from the bag.
            for (int i = 0; i < itemsToBeTrashed.size(); ++i) {
                Integer id = itemsToBeTrashed.get(i);
                for (int j = 0; j < human.getBag().size(); ++j) {
                    Goods g = human.getBag().get(j);
                    if (id == g.getId()) {
                        human.getBag().remove(g);
                    }
                }
            }
            // Give the penalty to the trader if he's lying.
            // Or else give him the reward for not lying.
            if (honestyFlag == 0) {
                setMoney(getMoney() + penalty);
                human.setMoney(human.getMoney() - penalty);
            } else {
                int specificPenalty = -1;
                for (Goods goods : human.getBag()) {
                    specificPenalty = goods.getPenalty();
                }
                human.setMoney(human.getMoney() + human.getBag().size() * specificPenalty);
                this.setMoney(this.getMoney() - human.getBag().size() * specificPenalty);
            }
        }

        // Put the cards that made it past the sheriff in the
        // Final bag(stand).
        for (Goods goods : human.getBag()) {
            human.getFinalBag().add(goods);
        }
    }
}


