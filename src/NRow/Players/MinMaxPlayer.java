package NRow.Players;

import NRow.Board;
import NRow.Game;
import NRow.Heuristics.Heuristic;
import NRow.Tree.Node;

import java.util.Arrays;
import java.util.ArrayList;

public class MinMaxPlayer extends PlayerController {
    protected int depth;
    // private Boolean isMaximumPlayer;

    public MinMaxPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
        //You can add functionality which runs when the player is first created (before the game starts)
    }

    /**
     * Implement this method yourself!
     *
     * @param board the current board
     * @return column integer the player chose
     */
    @Override
    public int makeMove(Board board) {
        int actualMove = 0;
        Node rootNode = new Node();
        rootNode.setBoard(board);
        constructTree(this.depth, board, rootNode, playerId);
        actualMove = miniMax(rootNode, this.depth, false).get(0);
        System.out.println("actucal move: " + actualMove + ", player: " + playerId + "/////////");
        return actualMove;
    }

    public ArrayList<Integer> miniMax(Node node, int depth, Boolean isMaximumPlayer) {
        ArrayList<Integer> outArrayList;
        if (depth == 0 || node.isTerminalNode()) {
            outArrayList = new ArrayList<>(Arrays.asList(node.getAction(), heuristic.evaluateBoard(playerId, node.getBoard())));
            return outArrayList;
        } if (isMaximumPlayer) {
            int maxEval = Integer.MIN_VALUE;
            int action = -1;
            for(Node child: node.getChildren()) {
                int eval = miniMax(child, depth - 1, false).get(1);
                if (eval > maxEval) {
                    maxEval = eval;
                    action = child.getAction();
                }
            }
            outArrayList = new ArrayList<>(Arrays.asList(action, maxEval));
        } else {
            int minEval = Integer.MAX_VALUE;
            int action = -1;
            for (Node child: node.getChildren()) {
                int eval = miniMax(child, depth - 1, true).get(1);
                if (eval < minEval) {
                    minEval = eval;
                    action = child.getAction();
                }
            }
            // switchPlayer();
            outArrayList = new ArrayList<>(Arrays.asList(action, minEval));
        }
        return outArrayList;
    }

    public void constructTree(Integer depth, Board board, Node rootNode, int currentId) {
        int childId;
        if (currentId == 1) {
            childId = 2;
        } else {
            childId = 1;
        }
        if (depth == 0 || Game.winning(board.getBoardState(),gameN)!=0) {
            return;
        }
        for (int i = 0; i < board.width; i++) {
            if (board.isValid(i)) {
                Board newBoard = board.getNewBoard(i, currentId);
                Node newChild = new Node();
                newChild.setBoard(newBoard);
                newChild.setAction(i);
                rootNode.addChild(newChild);
                constructTree(depth - 1, newBoard, newChild, childId);
            }
        }
    }
}




//        // TODO: implement minmax player!
//        // HINT: use the functions on the 'board' object to produce a new board given a specific move
//        // HINT: use the functions on the 'heuristic' object to produce evaluations for the different board states!
//        // Example:
//        int maxValue = Integer.MIN_VALUE;
//        int maxMove = 0;
//        for (int i = 0; i < board.width; i++) { //for each of the possible moves
//            if (board.isValid(i)) { //if the move is valid
//                Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
//                int value = heuristic.evaluateBoard(playerId, newBoard); //evaluate that new board to get a heuristic value from it
//                if (value > maxValue) {
//                    maxMove = i;
//                }
//            }
//        }
//        // This returns the same as:
//        heuristic.getBestAction(playerId, board); // Very useful helper function!
        /*
        This is obviously not enough (this is depth 1)
        Your assignment is to create a data structure (tree) to store the gameboards such that you can evaluate a higher depths.
        Then, use the minmax algorithm to search through this tree to find the best move/action to take!
        */
