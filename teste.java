import java.lang.Math;

public class teste {
    private static final int SIMULATIONS = 1000;

    public static int mctsSearch(Game board) {

        Node rootNode = new Node(board, null, -1);
        Node nGen = null;
        Node bestMove = null;

        for (int i = 0; i < 7; i++) {
            nGen = new Node(rootNode.getGame().play(i), rootNode, i);
            for (int j = 0; j < SIMULATIONS; j++) {

                simulation(nGen);

            }
            calculateUCB(nGen);

            if ((bestMove == null || bestMove.getUCB() <= nGen.getUCB())
                    && !rootNode.getGame().isColumnFull(nGen.getMove())) {
                bestMove = nGen;
            }
        }

        return bestMove.getMove();
    }

    private static void simulation(Node board) {
        Game game = board.getGame();
        while (!game.isGameOver()) {
            int randomMove = (int) (Math.random() * 7);

            if (!game.isColumnFull(randomMove)) {

                game = game.play(randomMove);
            }
        }

        char winner = game.getWinner();
        if (winner == 'X') {
            board.updateTimesWon();
        } else if (winner == 'O') {
            board.updateTimesLost();
        }
    }

    private static void calculateUCB(Node board) {
        double ucb = (double) board.getTimesWon() / SIMULATIONS;
        board.setUCB(ucb);

    }

}