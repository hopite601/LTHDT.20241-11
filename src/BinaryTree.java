import java.util.Iterator;

public class BinaryTree<T extends Comparable<T>> extends Tree<T> {
    @Override
    public void createTree() {
        root = null;
    }

    @Override
    public boolean insertNode(T parentValue, T newValue) {
        if (root == null) {
            root = new Node(newValue);
            return true;
        }

        Node parentNode = findNode(root, parentValue);
        if (parentNode != null && parentNode.children.size() < 2) {
            parentNode.children.add(new Node(newValue));
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteNode(T value) {
        if (root == null) return false;
        if (root.value.equals(value)) {
            root = null;
            return true;
        }

        return deleteRecursive(root, value);
    }

    private boolean deleteRecursive(Node current, T value) {
        Iterator<Node> iterator = current.children.iterator();
        while (iterator.hasNext()) {
            Node child = iterator.next();
            if (child.value.equals(value)) {
                iterator.remove();
                return true;
            }
            if (deleteRecursive(child, value)) return true;
        }
        return false;
    }
}
