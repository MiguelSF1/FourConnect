import java.lang.Math;
import java.util.*;

public class MonteCarlo {
    private static final int SIMULATIONS = 4000;

    private static PriorityQueue<NodeMC> q = new PriorityQueue<>();

    public static int mctsSearch(Game board) {

        if (q.size() == 0) {

            NodeMC rootNode = new NodeMC(board, null, -1);
            q.add(rootNode);
        }
        NodeMC nGen = null;
        NodeMC bestMove = null;
        NodeMC explored = selection();

        for (int i = 0; i < 7; i++) {

            if (explored.getChild(i) == null) {

                nGen = new NodeMC(explored.getGame().play(i), explored, i);
                explored.addChild(nGen, i);
            } else {
                nGen = explored.getChild(i);
            }
            for (int j = 0; j < SIMULATIONS; j++) {

                simulation(nGen);

            }

            if ((bestMove == null || bestMove.getUCB() <= nGen.getUCB())
                    && !explored.getGame().isColumnFull(nGen.getMove())) {
                bestMove = nGen;
            }
        }

        return bestMove.getMove();
    }

    private static NodeMC selection() {
        return q.peek();
    }

    private static void simulation(NodeMC board) {
        NodeMC cur = board;
        while (!cur.getGame().isGameOver()) {
            cur.updateTimesPassed();
            int randomMove = (int) (Math.random() * 7);

            if (!cur.getGame().isColumnFull(randomMove)) {

                if (cur.getChild(randomMove) == null) {
                    NodeMC next = new NodeMC(cur.getGame().play(randomMove), cur, randomMove);
                    cur.addChild(next, randomMove);
                    cur = next;
                } else {
                    NodeMC next = cur.getChild(randomMove);
                    cur = next;
                }
                if (!q.contains(cur)) {
                    q.add(cur);
                }
            }
        }

        char winner = cur.getGame().getWinner();
        if (winner == 'X') {
            backtrack(cur, winner);
        } else if (winner == 'O') {
            backtrack(cur, winner);
        }
    }

    private static void backtrack(NodeMC board, char winner) {
        NodeMC cur = board;
        while (cur.getParent() != null) {
            if (winner == 'X') {
                cur.updateTimesWon();
            } else {
                cur.updateTimesLost();
            }
            NodeMC parent = cur.getParent();
            cur = parent;
        }
    }

}