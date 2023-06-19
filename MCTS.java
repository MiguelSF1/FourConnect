import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class MCTS {

    private static final int SIMULATION_COUNT = 70000;
    private static final double EXPLORATION_CONSTANT = Math.sqrt(2);

    public static int mctsSearch(Game game) {
        Node rootNode = new Node(game, null, -1);

        for (int i = 0; i < SIMULATION_COUNT; i++) {
            Node selectedNode = select(rootNode);
            Node expandedNode = expand(selectedNode);
            int simulationResult = simulate(expandedNode);
            backPropagate(expandedNode, simulationResult);
        }

        Node bestChild = Collections.max(rootNode.getChildren());
        return bestChild.getMove();
    }

    private static Node select(Node node) {
        while (!node.getGame().isGameOver() && !node.getChildren().isEmpty()) {
            double bestUCB = -Double.MAX_VALUE;
            Node bestChild = null;

            for (Node child : node.getChildren()) {
                double ucb = child.getUCB()
                        + EXPLORATION_CONSTANT
                                * Math.sqrt(Math.log(node.getTimesWon() + 1e-10) / (child.getTimesWon() + 1e-10));
                if (ucb > bestUCB) {
                    bestUCB = ucb;
                    bestChild = child;
                }
            }
            node = bestChild;
        }

        return node;
    }

    private static Node expand(Node node) {
        Game game = node.getGame();

        if (game.isGameOver()) {
            return node;
        }

        List<Integer> possibleMoves = game.getPossibleMoves();
        for (int move : possibleMoves) {
            Game childGame = game.play(move);
            Node childNode = new Node(childGame, node, move);
            node.addChild(childNode);
        }

        return node.getChildren().get(0);
    }

    private static int simulate(Node node) {
        Game game = node.getGame();
        int moveCount = 0;

        while (!game.isGameOver()) {
            List<Integer> possibleMoves = game.getPossibleMoves();
            int randomMove = possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
            game = game.play(randomMove);
            moveCount++;

            if (moveCount >= 30) {
                break;
            }
        }

        return game.value();
    }

    private static void backPropagate(Node node, int value) {
        while (node != null) {
            node.updateTimesWon();
            node.setValue(node.getValue() + value);
            node = node.getParent();
        }
    }
}