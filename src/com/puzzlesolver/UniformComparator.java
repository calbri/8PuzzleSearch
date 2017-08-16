package com.puzzlesolver;

import java.util.Comparator;

public class UniformComparator implements Comparator<PuzzleNode> {
    @Override
    public int compare(PuzzleNode o1, PuzzleNode o2) {
        if (o1.getCostToRoot() < o2.getCostToRoot()) {
            return -1;
        } else if (o1.getCostToRoot() == o2.getCostToRoot()) {
            return 0;
        }
        return 1;
    }
}
