package NRow.Tree;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private List<Node> children;
    private int[][] boardState;
    public Node() {
        this.children = new ArrayList<>();
    }
    public List<Node> getChildren() {
        return children;
    }
    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public void setBoardState(int[][] state) {
        this.boardState = state;
    }

    public Boolean isTerminalNode() {
        if (children == null || children.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
