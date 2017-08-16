package com.puzzlesolver;

import java.util.Stack;

public class PuzzleNode implements Comparable {

    private PuzzleNode previous;
    private String move;
    private String state;

    public PuzzleNode(String state, PuzzleNode previous) {
        this.state = state;
        this.previous = previous;
    }

    public PuzzleNode(String state) {
        this.state = state;
        this.previous = null;
    }

    public PuzzleNode getPrevious() {
        return previous;
    }
    public String getState() {
        return state;
    }

    public void setMovePrevious(String move) {
        previous.move = move;
    }

    public void setMoveCurrent(String move) {
        this.move = move;
    }

    public int getCostToRoot() {
        PuzzleNode parent = previous;
        int count = 0;
        while (parent != null) {
            count++;
            parent = parent.getPrevious();
        }

        return count;
    }

    public void printSolution() {
        Stack<String> solution = new Stack<>();
        solution.push(state + " " + move);
        PuzzleNode parent = previous;
        int count = 0;
        while (parent != null) {
            count++;
            solution.push(parent.getState() + " " + parent.move);
            parent = parent.getPrevious();
        }

        System.out.println("Path to solution: ");
        while (!solution.empty()) {
            System.out.println(solution.pop());
        }

        System.out.println("\nNumber of moves = " + count);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PuzzleNode)) {
            return false;
        }

        if (this.state.equals(((PuzzleNode) other).getState())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
