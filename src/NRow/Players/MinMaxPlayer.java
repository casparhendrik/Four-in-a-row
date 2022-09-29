package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Tree.Node;
import NRow.Tree.Tree;

import java.util.List;

public class MinMaxPlayer extends PlayerController {
    private int depth;
    private Tree boardTree;

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

        constructTree(this.depth, board);

        // TODO: implement minmax player!
        // HINT: use the functions on the 'board' object to produce a new board given a specific move
        // HINT: use the functions on the 'heuristic' object to produce evaluations for the different board states!
        // Example: 
        int maxValue = Integer.MIN_VALUE;
        int maxMove = 0;
        for (int i = 0; i < board.width; i++) { //for each of the possible moves
            if (board.isValid(i)) { //if the move is valid
                Board newBoard = board.getNewBoard(i, playerId); // Get a new board resulting from that move
                int value = heuristic.evaluateBoard(playerId, newBoard); //evaluate that new board to get a heuristic value from it
                if (value > maxValue) {
                    maxMove = i;
                }
            }
        }
        // This returns the same as:
        heuristic.getBestAction(playerId, board); // Very useful helper function!
        /*
        This is obviously not enough (this is depth 1)
        Your assignment is to create a data structure (tree) to store the gameboards such that you can evaluate a higher depths.
        Then, use the minmax algorithm to search through this tree to find the best move/action to take!
        */

        return actualMove;
    }

    public void constructTree(Integer depth, Board board) {
        if (depth == 0) {
            return;
        }
        for (int i = 0; i < board.width; i++) {
            if (board.isValid(i)) {
                Board newBoard = board.getNewBoard(i, playerId);
                Node newChild = new Node();
                newChild.setBoardState(newBoard.getBoardState());
                boardTree.addChild(newChild);
                constructTree(depth - 1, board);
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
//    /*
//    Switch the state of the current player. Method is used when traversing to a different depth in the tree.
//     */
//    public void switchPlayer() {
//        if (this.currentPlayer==Player.MAX) {
//            this.currentPlayer = Player.MIN;
//        } else {
//            this.currentPlayer = Player.MAX;
//        }
//    }
    }
}
