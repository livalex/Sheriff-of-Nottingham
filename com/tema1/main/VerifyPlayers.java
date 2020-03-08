// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.main;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.players.Human;

import java.util.ArrayList;
import java.util.Queue;

public final class VerifyPlayers {
    private static VerifyPlayers verifyPlayers = null;

    private VerifyPlayers() {
    }

    public static VerifyPlayers getInstance() {
        if (verifyPlayers == null) {
            verifyPlayers = new VerifyPlayers();
        }
        return verifyPlayers;
    }

    public ArrayList<Human> verificationPost(final ArrayList<Human> players,
                                             final GameInput gameInput, final Queue<Integer> queue,
                                             final GoodsFactory goodsFactory) {
        int roundNumber = 0;
        // Iterate thorough all the rounds.
        for (int z = 0; z < gameInput.getRounds(); ++z) {
            ++roundNumber;
            for (int i = 0; i < gameInput.getPlayerNames().size(); ++i) {
                // One sheriff per round.
                Human sheriff = players.get(i);
                for (int j = 0; j < gameInput.getPlayerNames().size(); ++j) {
                    if (i != j) {
                        // In case the sheriff plays Bribe strategy
                        // We find the indexes of the players that we
                        // Must control and take the bribe from the
                        // Others in case they have.
                        if (sheriff.getPlayerType() == 2) {
                            if (players.size() == 2) {
                                int index;

                                if (i == 0) {
                                    index = 1;
                                } else {
                                    index = 0;
                                }
                                if (j == index) {
                                    Human player = players.get(index);

                                    player.commerciant(roundNumber, queue, goodsFactory);
                                    sheriff.sheriff(player, gameInput);
                                    player.getBag().clear();
                                    player.getCards().clear();
                                    player.getFreq().clear();
                                }
                            } else {
                                int index1, index2;

                                if (i == 0) {
                                    index1 = 1;
                                    index2 = players.size() - 1;
                                } else if (i == players.size() - 1) {
                                    index1 = 0;
                                    index2 = players.size() - 2;
                                } else {
                                    index1 = i - 1;
                                    index2 = i + 1;
                                }

                                if (j == index1) {
                                    Human player1 = players.get(index1);
                                    player1.commerciant(roundNumber, queue, goodsFactory);
                                    sheriff.sheriff(player1, gameInput);
                                    player1.getBag().clear();
                                    player1.getCards().clear();
                                    player1.getFreq().clear();
                                }

                                if (j == index2) {
                                    Human player2 = players.get(index2);
                                    player2.commerciant(roundNumber, queue, goodsFactory);
                                    sheriff.sheriff(player2, gameInput);
                                    player2.getBag().clear();
                                    player2.getCards().clear();
                                    player2.getFreq().clear();
                                }

                                // Take the bribe from the others.
                                if (i != j && j != index1 && j != index2) {
                                    Human commer = players.get(j);
                                    commer.commerciant(roundNumber, queue, goodsFactory);
                                    for (Goods g : commer.getBag()) {
                                        commer.getFinalBag().add(g);
                                    }
                                    if (commer.getBribe() != 0) {
                                        int money1 = sheriff.getMoney() + commer.getBribe();
                                        sheriff.setMoney(money1);

                                        int money2 = commer.getMoney() - commer.getBribe();
                                        commer.setMoney(money2);
                                    }
                                    commer.getBag().clear();
                                    commer.getCards().clear();
                                    commer.getFreq().clear();
                                    commer.setBribe(0);
                                }
                            }
                        } else {
                            // In case the sheriff is not Bribe.
                            Human commerciant = players.get(j);
                            commerciant.commerciant(roundNumber, queue, goodsFactory);
                            sheriff.sheriff(commerciant, gameInput);
                            // Free all his belongings to prepare for the next subround.
                            commerciant.getBag().clear();
                            commerciant.getCards().clear();
                            commerciant.getFreq().clear();
                        }
                    }
                }
            }
        }
        return players;
    }
}
