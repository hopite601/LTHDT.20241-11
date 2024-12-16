
class BalancedBinaryTree<T extends Comparable<T>> extends BinaryTree<T> {
    private int maxBalanceFactor;

    public BalancedBinaryTree(int maxBalanceFactor) {
        this.maxBalanceFactor = maxBalanceFactor;
    }

    @Override
    public boolean insertNode(T parentValue, T newValue) {
        if (root == null) {
            root = new Node(newValue);
            return true;
        }

        Node parentNode = findNode(root, parentValue);
        if (parentNode != null && parentNode.children.size() < 2 && isBalanced(parentNode)) {
            parentNode.children.add(new Node(newValue));
            return true;
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
