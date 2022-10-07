package NRow.Players;

import NRow.Board;
import NRow.Game;
import NRow.Heuristics.Heuristic;
import NRow.Tree.Node;

import java.util.Arrays;
import java.util.ArrayList;

public class MinMaxPlayer extends PlayerController {
    protected int depth;

    public MinMaxPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
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