package root.model;

public class BalancedBinaryTree {
    private Node root;
    public void setRoot(Node root) {
        this.root = root;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.value) node.left = insertRecursive(node.left, value);
        else if (value > node.value) node.right = insertRecursive(node.right, value);
        return balance(node);
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) node.left = deleteRecursive(node.left, value);
        else if (value > node.value) node.right = deleteRecursive(node.right, value);
        else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                return temp == null ? null : temp;
            } else {
                int minValue = findMin(node.right);
                node.value = minValue;
                node.right = deleteRecursive(node.right, minValue);
            }
        }

        return balance(node);
    }
    public int findMin(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.value;
    }
    
    public int findMax(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.value;
    }
    public boolean search(int value) {
        return searchRecursive(root, value);
    }

    private boolean searchRecursive(Node node, int value) {
        if (node == null) return false;
        if (value == node.value) return true;
        return value < node.value ? searchRecursive(node.left, value) : searchRecursive(node.right, value);
    }

    public Node balanceTree(Node node) {
        return balance(node);
    }
    
    private Node balance(Node node) {
        int balanceFactor = height(node.left) - height(node.right);
    
        if (balanceFactor > 1) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        } else if (balanceFactor < -1) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }
    
        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        return y;
    }

    private int height(Node node) {
        return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
    }

    public Node getRoot() {
        return root;
    }
}
