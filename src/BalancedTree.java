import java.util.Iterator;

class BalancedTree<T extends Comparable<T>> extends Tree<T> {
    private int maxBalanceFactor;

    public BalancedTree(int maxBalanceFactor) {
        this.maxBalanceFactor = maxBalanceFactor;
    }

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
        if (parentNode != null && isBalanced(parentNode)) {
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

    private boolean isBalanced(Node node) {
        return calculateDepth(node) <= maxBalanceFactor;
    }

    private int calculateDepth(Node node) {
        if (node == null) return 0;
        int maxDepth = 0;
        for (Node child : node.children) {
            maxDepth = Math.max(maxDepth, calculateDepth(child));
        }
        return maxDepth + 1;
    }
}

