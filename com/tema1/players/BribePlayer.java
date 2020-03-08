// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.players;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ArrayList;
import com.tema1.comparators.GoodsComparator;


public class BribePlayer extends BasicPlayer {
    public BribePlayer() {
    }

    public BribePlayer(final int type, final int playerId) {
        setPlayerType(type);
        setPlayerId(playerId);
    }

    @Override
    public final void commerciant(final int roundNumber, final Queue<Integer> queue,
                                  final GoodsFactory goodsFactory) {
        // Made a copy of the queue in order to find out
        // If he will play base strategy for commerciant or bribe.
        Queue<Integer> copyQueue = new LinkedList<Integer>();
        for (Integer i : queue) {
            copyQueue.add(i);
        }

        int flag = 0;
        for (int j = 0; j < Constants.CARDS_DRAWING; ++j) {
            int card = copyQueue.remove();
            if (card > Constants.MAX_LEGAL_CARD_ID) {
                flag = 1;
            }
        }

        if (getMoney() <= Constants.MIN_BRIBE || flag == 0) {
            super.commerciant(roundNumber, queue, goodsFactory);
        } else {
            for (int j = 0; j < Constants.CARDS_DRAWING; ++j) {
                int card = queue.remove();
                getCards().add(goodsFactory.getGoodsById(card));
            }
            GoodsComparator goodsComparator = new GoodsComparator();

            // A copy of the cards used to sort the cards.
            ArrayList<Goods> copyCard = new ArrayList<>();
            for (Goods g : getCards()) {
                copyCard.add(g);
            }

            Collections.sort(copyCard, goodsComparator);
            int bribeFlag = -1;
            int illegalCounter = 0;
            int possiblePenalty = 0;
            for (int i = 0; i < copyCard.size(); ++i) {
                Goods g = copyCard.get(i);
                if (g.getType() == GoodsType.Illegal) {
                    // If he would have money to pay the penalty of
                    // The card in case he is caught, he puts the card
                    // In the bag and we find out the total number of illegal cards.
                    if (illegalCounter <= 2) {
                        if (getMoney() - g.getPenalty() - possiblePenalty > 0
                                && getBag().size() < Constants.CARDS_IN_HAND) {

                            ++illegalCounter;
                            possiblePenalty += g.getPenalty();
                            getBag().add(g);
                        }
                    } else {
                        if (getMoney() - g.getPenalty() - possiblePenalty > 0
                                && getBag().size() < Constants.CARDS_IN_HAND) {
                            ++illegalCounter;
                            possiblePenalty += g.getPenalty();
                            getBag().add(g);
                        }
                    }
                } else {
                    // Just as above but with legal cards.
                    if (getMoney() - g.getPenalty() - possiblePenalty > 0
                            && getBag().size() < Constants.CARDS_IN_HAND) {
                        possiblePenalty += g.getPenalty();
                        getBag().add(g);
                    }
                }
            }
            setBagContent(0);
            // Decide the bribe.
            if (illegalCounter <= 2 && illegalCounter != 0) {
                setBribe(Constants.MIN_BRIBE);
            } else {
                setBribe(Constants.MAX_BRIBE);
            }
        }
    }



    @Override
    public final void sheriff(final Human human, final GameInput gameInput) {
        super.sheriff(human, gameInput);
    }
}


