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

        // if (boardTree==null) {
        //     constructTree(this.depth, board);
        // }   
        Node rootNode = new Node();
        rootNode.setBoard(board);
        constructTree(this.depth, board, rootNode, playerId);
        // System.out.println(rootNode.children);
        actualMove = miniMax(rootNode, this.depth, true).get(0);
        System.out.println("actucal move: " + actualMove + ", player: " + playerId + "/////////");
        return actualMove;
    }
   
    /*
   Switch the state of the current player. Method is used when traversing to a different depth in the tree.
    */
    // public void switchPlayer() {
    //     if (this.isMaximumPlayer == true) {
    //         this.isMaximumPlayer = false;
    //     } else {
    //         this.isMaximumPlayer = true;
    //     }
    // }

    public ArrayList<Integer> miniMax(Node node, int depth, Boolean isMaximumPlayer) {
        if (depth == 0 || node.isTerminalNode()) {
            ArrayList<Integer> outArrayList = new ArrayList<>(Arrays.asList(node.getAction(), heuristic.evaluateBoard(playerId, node.getBoard())));
            // System.out.println("Torch the buttom! Return: " + outArrayList);
            return outArrayList;
        } if (isMaximumPlayer) {
            int maxEval = Integer.MIN_VALUE;
            int action = -1;
            for(Node child: node.getChildren()) {
                int eval = miniMax(child, depth - 1, false).get(1);
                // System.out.println("Evaluating column " + child.getAction() + ", evaluation: " + eval);
                if (eval > maxEval) {
                    maxEval = eval;
                    action = child.getAction();
                    // System.out.println("Updated!!!!");
                }
            }
            // switchPlayer();
            ArrayList<Integer> outArrayList = new ArrayList<>(Arrays.asList(action, maxEval));
            // System.out.println("Returned results action + maxEval " + outArrayList);
            return outArrayList;
        } else {
            int minEval = Integer.MAX_VALUE;
            int action = -1;
            for (Node child: node.getChildren()) {
                int eval = miniMax(child, depth - 1, true).get(1);
                // System.out.println("Evaluating column " + child.getAction() + ", evaluation: " + eval);
                if (eval < minEval) {
                    minEval = eval;
                    action = child.getAction();
                    // System.out.println("Updated!!!!");
                }
            }
            // switchPlayer();
            ArrayList<Integer> outArrayList = new ArrayList<>(Arrays.asList(action, minEval));
            // System.out.println("Returned results action + minEval " + outArrayList);
            return outArrayList;
        }
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
        // // boardTree = new Tree();
        for (int i = 0; i < board.width; i++) {
            if (board.isValid(i)) {
                Board newBoard = board.getNewBoard(i, currentId);
                Node newChild = new Node();
                // newChild.setValue(heuristic.evaluateBoard(childId, newBoard));
                newChild.setBoard(newBoard);
                newChild.setAction(i);
                rootNode.addChild(newChild);
                constructTree(depth - 1, newBoard, newChild, childId);
            }
        }
        }
    }

//    public Integer alphaBeta(Node node, int depth, Player currentPlayer, int alpha, int beta) {
//        if (depth == 0 || node.isTerminalNode()) {
//            return 0;
//        } if (currentPlayer==Player.MAX) {
//            int value = Integer.MIN_VALUE;
//            for(Node child: node.getChildren()) {
//                switchPlayer();
//                int newValue = alphaBeta(child, depth - 1, currentPlayer, alpha, beta);
//                if (newValue > value) {
//                    value = newValue;
//                }
//                alpha = Math.max(alpha, value);
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//            return value;
//        } else { // if the currentPlayer is not MAX it has to MIN
//            int value = Integer.MAX_VALUE;
//            for(Node child: node.getChildren()) {
//                switchPlayer();
//                int newValue = alphaBeta(child, depth - 1, currentPlayer, alpha, beta);
//                if (newValue < value) {
//                    value = newValue;
//                }
//                beta = Math.min(beta, value);
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//            return value;
//        }
//    }
//


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
