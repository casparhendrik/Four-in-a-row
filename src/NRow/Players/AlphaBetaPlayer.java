package NRow.Players;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Tree.Node;

import java.util.Arrays;
import java.util.ArrayList;

public class AlphaBetaPlayer extends MinMaxPlayer {

    public AlphaBetaPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, depth, heuristic);
    }

    @Override
    public int makeMove(Board board) {
        int actualMove = 0;

        Node rootNode = new Node();
        rootNode.setBoard(board);
        constructTree(this.depth, board, rootNode, playerId);
        System.out.println(rootNode.children);
        actualMove = alphaBeta(rootNode, this.depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE).get(0);
        System.out.println(rootNode.getAction());
        return actualMove;
    }

    public ArrayList<Integer> alphaBeta(Node node, int depth, Boolean isMaximumPlayer, int alpha, int beta) {
        if (depth == 0 || node.isTerminalNode()) {
            ArrayList<Integer> outArrayList = new ArrayList<>(Arrays.asList(node.getAction(), heuristic.evaluateBoard(playerId, node.getBoard())));
            return outArrayList;
        } if (isMaximumPlayer) {
            int maxEval = Integer.MIN_VALUE;
            int action = 6;
            for(Node child: node.getChildren()) {
                int eval = alphaBeta(child, depth - 1, false, alpha, beta).get(1);
                if (eval > maxEval) {
                    maxEval = eval;
                    action = child.getAction();
                }
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                   break;
                }
            }
            // switchPlayer();
            ArrayList<Integer> outArrayList = new ArrayList<>(Arrays.asList(action, maxEval));
            return outArrayList;
        } else {
            int minEval = Integer.MAX_VALUE;
            int action = 6;
            for (Node child: node.getChildren()) {
                int eval = alphaBeta(child, depth - 1, true, alpha, beta).get(1);
                if (eval < minEval) {
                    minEval = eval;
                    action = child.getAction();
                }
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                break;
                }
            }
            // switchPlayer();
            ArrayList<Integer> outArrayList = new ArrayList<>(Arrays.asList(action, minEval));
            return outArrayList;
        }
    }
}