// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;
import com.tema1.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public abstract class Human {
    // The fields needed for every player.
    private int money = Constants.STARTING_SUM;
    private ArrayList<Goods> bag = new ArrayList<>();
    private ArrayList<Goods> finalBag = new ArrayList<>();
    private ArrayList<Goods> cards = new ArrayList<>();
    private int bagContent;
    private int bribe = Constants.STARTING_BRIBE;
    private int playerId = Constants.STARTING_ID;
    private int playerType;
    private HashMap<Goods, Integer> freq = new HashMap<>();
    private ArrayList<Integer> humanGoodsFreq = new ArrayList<>();

    public final int getMoney() {
        return money;
    }

    public final void setMoney(final int profit) {
        this.money = profit;
    }

    public final ArrayList<Goods> getBag() {
        return bag;
    }

    public final void setBag(final ArrayList<Goods> bag) {
        this.bag = bag;
    }

    public final ArrayList<Integer> getHumanGoodsFreq() {
        return humanGoodsFreq;
    }

    public final void setHumanGoodsFreq(final ArrayList<Integer> humanGoodsFreq) {
        this.humanGoodsFreq = humanGoodsFreq;
    }

    public final ArrayList<Goods> getCards() {
        return cards;
    }

    public final void setCards(final ArrayList<Goods> cards) {
        this.cards = cards;
    }

    public final int getBagContent() {
        return bagContent;
    }

    public final void setBagContent(final int bagContent) {
        this.bagContent = bagContent;
    }

    public final int getBribe() {
        return bribe;
    }

    public final void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    public final HashMap<Goods, Integer> getFreq() {
        return freq;
    }

    public final void setFreq(final HashMap<Goods, Integer> freq) {
        this.freq = freq;
    }

    public final int getPlayerId() {
        return playerId;
    }

    public final void setPlayerId(final int playerId) {
        this.playerId = playerId;
    }

    public final int getPlayerType() {
        return playerType;
    }

    public final void setPlayerType(final int playerType) {
        this.playerType = playerType;
    }

    public final ArrayList<Goods> getFinalBag() {
        return finalBag;
    }

    public final void setFinalBag(final ArrayList<Goods> finalBag) {
        this.finalBag = finalBag;
    }

    public abstract void commerciant(int roundNumber, Queue<Integer> queue,
                                     GoodsFactory goodsFactory);

    public abstract void sheriff(Human human, GameInput gameInput);
}
