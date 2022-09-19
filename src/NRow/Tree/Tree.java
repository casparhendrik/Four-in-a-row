package NRow.Tree;

public class Tree {

    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Tree() {
    }

    public void constructTree() {

    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void addChild(Node child) {
        if (root==null) {
            root = child;
        } else {
            root.addChild(child);
        }
    }
}
