// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.common.Constants;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.players.Human;

import java.util.ArrayList;

public final class FrequencyGiver {
    private static FrequencyGiver frequencyGiver = null;

    private FrequencyGiver() {
    }

    public static FrequencyGiver getInstance() {
        if (frequencyGiver == null) {
            frequencyGiver = new FrequencyGiver();
        }
        return frequencyGiver;
    }

    public ArrayList<Human> giveFrequncy(final ArrayList<Human> players,
                                         final GoodsFactory goodsFactory) {
        // We add the frequency of each Card in the frequency array
        // Of the goods. Every players has it's own array
        // Depending on it's bag.
        for (Human human : players) {
            for (int i = 0; i < Constants.CARDS_DRAWING; ++i) {
                int goodFreq = 0;
                Goods card = goodsFactory.getGoodsById(i);
                for (Goods good : human.getFinalBag()) {
                    if (good.getType().equals(GoodsType.Legal)) {
                        if (card.getId() == good.getId()) {
                            ++goodFreq;
                        }
                    } else {
                        for (Goods g : good.getIllegalBonus().keySet()) {
                            if (card.getId() == g.getId()) {
                                goodFreq += good.getIllegalBonus().get(g);
                            }
                        }
                    }
                }
                human.getHumanGoodsFreq().add(i, goodFreq);
            }
        }
        return players;
    }
}
