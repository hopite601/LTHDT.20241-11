import java.util.*;

//base Tree class
abstract class Tree<T extends Comparable<T>> {
    protected class Node {
        T value;
        List<Node> children;

        Node(T value) {
            this.value = value;
            this.children = new ArrayList<>();
        }
    }

    protected Node root;

    public abstract void createTree();

    public abstract boolean insertNode(T parentValue, T newValue);

    public abstract boolean deleteNode(T value);

    protected Node findNode(Node current, T value) {
        if (current == null) return null;
        if (current.value.equals(value)) return current;
        for (Node child : current.children) {
            Node result = findNode(child, value);
            if (result != null) return result;
        }
        return null;
    }
}
