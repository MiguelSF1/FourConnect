
public class NodeMC implements Comparable<NodeMC> {
    private Game board;
    private NodeMC parent;
    private int col;
    private char winner;
    private int timeswon;
    private float ucb;
    private int timespassed;
    private NodeMC c1 = null, c2 = null, c3 = null, c4 = null, c5 = null, c6 = null, c0 = null;

    NodeMC(Game board, NodeMC parent, int col) {
        this.board = board;
        this.parent = parent;
        this.col = col;
        this.timeswon = 0;
        this.winner = 'A';
        this.ucb = 0;
        this.timespassed = 0;
    }

    public NodeMC getChild(int c) {
        switch (c) {
            case 0:
                return c0;
            case 1:
                return c1;
            case 2:
                return c2;
            case 3:
                return c3;
            case 4:
                return c4;
            case 5:
                return c5;
            case 6:
                return c6;
        }
        return null;
    }

    public void addChild(NodeMC child, int c) {
        switch (c) {
            case 0:
                c0 = child;
                break;
            case 1:
                c1 = child;
                break;
            case 2:
                c2 = child;
                break;
            case 3:
                c3 = child;
                break;
            case 4:
                c4 = child;
                break;
            case 5:
                c5 = child;
                break;
            case 6:
                c6 = child;
                break;
        }
    }

    public Game getGame() {
        return board;
    }

    public NodeMC getParent() {
        return parent;
    }

    public char getWinner() {
        return winner;
    }

    public int getMove() {
        return col;
    }

    public void setMove(int col) {
        this.col = col;
    }

    public double getUCB() {
        return this.ucb;
    }

    public int getTimesWon() {
        return this.timeswon;
    }

    public int getTimesPassed() {
        return this.timespassed;
    }

    public void updateTimesWon() {
        this.timeswon++;
        updateUCB();
    }

    public void updateTimesLost() {
        this.timeswon--;
        updateUCB();
    }

    private void updateUCB() {
        this.ucb = (float) timeswon / (float) timespassed;
    }

    public void updateTimesPassed() {
        this.timespassed++;
        updateUCB();
    }

    @Override
    public int compareTo(NodeMC o) {
        if (ucb < o.getUCB()) {
            return -1;
        }
        return 1;
    }

}
