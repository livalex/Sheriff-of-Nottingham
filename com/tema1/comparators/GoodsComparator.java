// Copyright 2019: Livadaru Alexandru-Valentin
package com.tema1.comparators;

import com.tema1.goods.Goods;
import java.util.Comparator;

// Sorts the Cards by their profit in order
// To give Bribe a sorted array.
public final class GoodsComparator implements Comparator<Goods> {
    @Override
    public int compare(final Goods g1, final Goods g2) {
        if (g1.getProfit() != g2.getProfit()) {
            return g2.getProfit() - g1.getProfit();
        }

        if (g1.getProfit() == g2.getProfit()) {
            return g2.getId() - g1.getId();
        }
        return 0;
    }
}
