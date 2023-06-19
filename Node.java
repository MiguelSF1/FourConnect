import java.util.*;

public class Node implements Comparable<Node> {
    private Game board;
    private Node parent;
    private int depth;
    private int value;
    private int col;
    private char winner;
    private List<Node> children = new ArrayList<>();
    private int timeswon;
    private double ucb;

    Node(Game board, Node parent, int col) {
        this.board = board;
        this.parent = parent;
        this.depth = 0;
        this.value = 0;
        this.col = col;
        this.timeswon = 0;
        this.winner = 'A';
        this.ucb = 0;

    }

    Node(Game board, Node parent, int depth, int col) {
        this.board = board;
        this.parent = parent;
        this.depth = depth;
        this.value = 0;
        this.col = col;
        this.winner = 'A';
        this.ucb = 0;
    }

    public Game getGame() {
        return board;
    }

    public Node getParent() {
        return parent;
    }

    public char getWinner() {
        return winner;
    }

    public int getDepth() {
        return depth;
    }

    public int getMove() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public void updateTimesWon() {
        this.timeswon++;
    }

    public void updateTimesLost() {
        this.timeswon--;
    }

    public void setUCB(double f) {
        this.ucb = f;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public int compareTo(Node o) {
        if(ucb < o.getUCB()){
            return -1;
        }
        return 1;
    }

}
    