package NRow.Tree;

import java.util.ArrayList;
import java.util.List;

import NRow.Board;

public class Node {

    public List<Node> children;
    private int value;
    private Board board;
    private int action;
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

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard(){
        return this.board;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return this.action;
    }

    public Boolean isTerminalNode() {
        if (children == null || children.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
