import java.util.*;

public class Fourconnect {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Game board = new Game();
        System.out.println("1 player(1) or 2 players(2)");
        int numPlayers = in.nextInt();

        switch (numPlayers) {
            case 2:
                printBoard(board);
                while (true) {
                    board = board.play(in.nextInt() - 1);
                    printBoard(board);
                    if (board.isGameOver()) {
                        verifyVictory(board);
                        break;
                    }
                }
                break;

            case 1:
                System.out.println("First player(1) or second player(2)");
                int fstmove = in.nextInt();
                if (fstmove == 2) board.player = 'X';
                System.out.println("Play VS: ");
                System.out.println("MinMax(1) | AlphaBeta(2) | MonteCarlo(3)");
                int ai = in.nextInt();
                printBoard(board);

                while (true) {
                    if (board.player == 'X') {
                        switch (ai) {
                            case 1:
                                board = board.play(MinMax.MinMaxSearch(board));
                                break;
                            case 2:
                                board = board.play(AlphaBeta.AlphaBetaSearch(board));
                                break;
                            case 3:
                                board = board.play(MonteCarlo.mctsSearch(board));
                                break;
                            default:
                                break;
                        }
                    }
                    else board = board.play(in.nextInt() - 1);

                    printBoard(board);

                    if (board.isGameOver()) {
                        verifyVictory(board);
                        break;
                    }
                }
                break;
        }
        in.close();
    }

    public static void printBoard(Game board) {
        System.out.println();
        board.myToString();
        System.out.println("  | 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
        System.out.println("Pontos: " + board.value());
        System.out.println();
    }

    public static void verifyVictory(Game board) {
        char winner = board.getWinner();
        if (winner == 'X')
            System.out.println("X wins with: " + board.value());
        else if (winner == 'O')
            System.out.println("O wins with: " + board.value());
        else
            System.out.println("Draw with: " + board.value());
    }
}