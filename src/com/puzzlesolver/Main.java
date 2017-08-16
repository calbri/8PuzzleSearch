package com.puzzlesolver;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Main {

    protected static PriorityQueue<PuzzleNode> BFSQueue = new PriorityQueue<>();
    protected static PriorityQueue<PuzzleNode> UCSQueue = new PriorityQueue<>(11, new UniformComparator());
    protected static Stack<PuzzleNode> DFSStack = new Stack<>();
    protected static HashSet<String> History = new HashSet<>();

    protected static String goal;
    protected static String start;
    protected static boolean BFSearch;
    protected static boolean UCSearch = false;

    static final char space = '_';
    protected static boolean solved = false;

    public static void main(String[] args) {
        if (args.length != 3) {
            return;
        }
        start = args[1];
        goal = args[2];

        //check parity
        if (!validParityCheck(start, goal)) {
            System.out.println("Invalid goal.");
            return;
        }

        BFSearch = args[0].equals("BFS");
        UCSearch = args[0].equals("UCS");

        //start calculation
        long timeStart = System.nanoTime();

        PuzzleNode first = new PuzzleNode(start);
        History.add(start);
        checkState(first);



        if (BFSearch) {
            System.out.println("Searching with Breadth First Search...");
            while (!(BFSQueue.isEmpty())) {
                if (solved) {
                    break;
                }
                checkState(BFSQueue.poll());
            }
        } else if (UCSearch) {
            System.out.println("Searching with Uniform Cost Search...");
            while (!(UCSQueue.isEmpty())) {
                if (solved) {
                    break;
                }
                checkState(UCSQueue.poll());
            }
        } else {
            System.out.println("Searching with Depth First Search...");
            while (!(DFSStack.isEmpty())) {
                if (solved) {
                    break;
                }
                checkState(DFSStack.pop());
            }
        }

        if (solved) {
            System.out.println("Solution found successfully.");
            long timeFinish = System.nanoTime();
            System.out.println("Time taken = " + TimeUnit.NANOSECONDS.toMillis(timeFinish - timeStart) + " milliseconds.");
        } else {
            System.out.println("Solution not found after exhausting all possibilities from initial state.");
        }
    }

    protected static boolean validParityCheck(String startState, String endState) {
        int startParity = getParity(startState);
        int endParity = getParity(endState);

        return (startParity % 2 == endParity % 2);
    }

    protected static int getParity(String state) {
        int parityValue = 0;
        int i = 0;
        while (i < 8) {
            char value = state.charAt(i);
            if (value == '_') {
                i++;
                continue;
            }
            int numericValue = value - '0';
            int j = i + 1;
            while (j < 8) {
                if (state.charAt(j) == '_') {
                    j++;
                    continue;
                }
                if (numericValue > (state.charAt(j) - '0')) {
                    parityValue++;
                }
                j++;
            }

            i++;
        }
        return parityValue;
    }

    protected static void addToQueue(String state, PuzzleNode node, String move) {
        if (!History.contains(state)) {
            PuzzleNode newNode = new PuzzleNode(state, node);
            newNode.setMovePrevious(move);
            if (state.equals(goal)) {
                newNode.setMoveCurrent("SOLVED");
                newNode.printSolution();
                solved = true;
            }
            if (BFSearch) {
                BFSQueue.add(newNode);
            } else if (UCSearch) {
                UCSQueue.add(newNode);
            } else {
                DFSStack.push(newNode);
            }
            History.add(state);
        }
    }

    protected static void checkState(PuzzleNode node) {
        String state = node.getState();

        int spaceLocation = state.indexOf(space);

        //left
        if (spaceLocation != 0 && spaceLocation != 3 && spaceLocation != 6) {
            char swapChar = state.charAt(spaceLocation - 1);
            char[] firstChar = state.toCharArray();
            firstChar[spaceLocation - 1] = space;
            firstChar[spaceLocation] = swapChar;
            String newPos = String.valueOf(firstChar);
            addToQueue(newPos, node, "LEFT");
        }

        if (solved) { return; }

        //right
        if (spaceLocation != 2 && spaceLocation != 5 && spaceLocation != 8) {
            char swapChar = state.charAt(spaceLocation + 1);
            char[] firstChar = state.toCharArray();
            firstChar[spaceLocation + 1] = space;
            firstChar[spaceLocation] = swapChar;
            String newPos = String.valueOf(firstChar);
            addToQueue(newPos, node, "RIGHT");
        }

        if (solved) { return; }

        //up
        if (spaceLocation != 0 && spaceLocation != 1 && spaceLocation != 2) {
            char swapChar = state.charAt(spaceLocation - 3);
            char[] firstChar = state.toCharArray();
            firstChar[spaceLocation - 3] = space;
            firstChar[spaceLocation] = swapChar;
            String newPos = String.valueOf(firstChar);
            addToQueue(newPos, node, "UP");
        }

        if (solved) { return; }

        //down
        if (spaceLocation != 6 && spaceLocation != 7 && spaceLocation != 8) {
            char swapChar = state.charAt(spaceLocation + 3);
            char[] firstChar = state.toCharArray();
            firstChar[spaceLocation + 3] = space;
            firstChar[spaceLocation] = swapChar;
            String newPos = String.valueOf(firstChar);
            addToQueue(newPos, node, "DOWN");
        }
    }
}
