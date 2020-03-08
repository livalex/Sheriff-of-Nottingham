// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.Goods;
import com.tema1.players.Human;
import java.util.ArrayList;

public final class BonusGiver {
    private static BonusGiver bonusGiver = null;

    private BonusGiver() {
    }

    public static BonusGiver getInstance() {
        if (bonusGiver == null) {
            bonusGiver = new BonusGiver();
        }
        return bonusGiver;
    }

    public ArrayList<Human> giveBonus(final ArrayList<Human> players,
                                      final GoodsFactory goodsFactory, final GameInput gameInput) {
        int k;
        for (k = 0; k < Constants.CARDS_DRAWING; ++k) {
            int kingFreq = 0;
            int kingFreqPlayer = -1;
            int queenFreq = 0;
            int queenFreqPlayer = -1;
            Goods card = goodsFactory.getGoodsById(k);
            // Using this arraylist we store the players with
            // The same frequency of a card.
            ArrayList<Human> kingFreqPlayers = new ArrayList<Human>(2);
            for (int j = 0; j < gameInput.getPlayerNames().size(); ++j) {
                Human player = players.get(j);
                if (player.getHumanGoodsFreq().get(k) != 0
                        && player.getHumanGoodsFreq().get(k) > kingFreq) {
                    // Clear the vector in case we find a bigger frequency
                    // Than the others.
                    kingFreqPlayers.clear();
                    kingFreqPlayers.add(player);
                    // Interchange the king frequency value and
                    // Id with thq queen in case we find a new king
                    queenFreq = kingFreq;
                    kingFreq = player.getHumanGoodsFreq().get(k);
                    queenFreqPlayer = kingFreqPlayer;
                    kingFreqPlayer = j;
                } else if (player.getHumanGoodsFreq().get(k) != 0
                        && (player.getHumanGoodsFreq().get(k).equals(kingFreq)
                        || (player.getHumanGoodsFreq().get(k) > queenFreq
                        && !player.getHumanGoodsFreq().get(k).equals(kingFreq)))) {
                    // Update the queen in case we find a frequency lower
                    // The king's frequency and higher than the queen's
                    // Frequcny
                    if (player.getHumanGoodsFreq().get(k) > queenFreq
                            && !player.getHumanGoodsFreq().get(k).equals(kingFreq)
                            && player.getHumanGoodsFreq().get(k) != 0) {
                        queenFreq = player.getHumanGoodsFreq().get(k);
                        queenFreqPlayer = j;
                    }

                    // In case two players have the same frequency on a card
                    // We put the player in this vector for further examination
                    if (player.getHumanGoodsFreq().get(k).equals(kingFreq)
                            && player.getHumanGoodsFreq().get(k) != 0) {
                        if (kingFreqPlayers.size() <= 2) {
                            kingFreqPlayers.add(player);
                        }
                    }
                }
            }
            // If a player has a lower Id than the other
            // He will be the king. In this case the Id's
            // Of the player are sorted.
            if (kingFreqPlayers.size() >= Constants.MAX_BONUS_VECTOR_SIZE) {
                kingFreqPlayer = kingFreqPlayers.get(0).getPlayerId();
                queenFreqPlayer = kingFreqPlayers.get(1).getPlayerId();
            }

            // After a harhs selection, here we finally give
            // The bonuses to the right players.
            for (Human p : players) {
                if (p.getPlayerId() == kingFreqPlayer) {
                    int money = p.getMoney() + card.getKingBonus();
                    p.setMoney(money);
                }

                if (p.getPlayerId() == queenFreqPlayer) {
                    int money = p.getMoney() + card.getQueenBonus();
                    p.setMoney(money);
                }
            }
        }
        return players;
    }
}
