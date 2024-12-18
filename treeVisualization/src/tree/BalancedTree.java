package tree;

public class BalancedTree extends Tree {
    private int maxHeightDifference;

    public BalancedTree(int maxHeightDifference) {
        super();
        this.maxHeightDifference = maxHeightDifference;
    }

    @Override
    public void createTree(int value) {
        this.root = new Node(value);
    }

    @Override
    public void insertNode(Node parent, int value) {
        if (parent != null) {
            Node newNode = new Node(value);
            parent.addChild(newNode);

            if (!isBalanced(this.root)) {
                parent.getChildren().remove(newNode); // Undo insertion
                System.out.println("Insertion would unbalance the tree. Operation aborted.");
            }
        }
    }

    @Override
    public void deleteNode(int value) {
        Node deleteNode = search(value);
        if (deleteNode != null) {
            deleteNode.removeSelf();
        }
    }

    @Override
    public void updateNode(int oldValue, int newValue) {
        Node nodeToUpdate = search(oldValue);
        if (nodeToUpdate != null) {
            nodeToUpdate.setValue(newValue);
            if (!isBalanced(this.root)) {
                nodeToUpdate.setValue(oldValue); // Undo update
                System.out.println("Update would unbalance the tree. Operation aborted.");
            }
        }
    }

    @Override
    public Node search(int value) {
        return new GenericTree().search(value); // Reuse search logic
    }

    @Override
    public List<Node> traverse(String algorithm) {
        List<Node> result = new ArrayList<>();
        
        if ("DFS".equalsIgnoreCase(algorithm)) {
            dfsTraversal(root, result);
        } else if ("BFS".equalsIgnoreCase(algorithm)) {
            bfsTraversal(root, result);
        } else {
            System.out.println("Unsupported traversal algorithm: " + algorithm);
        }
        
        return result;
    }

    private void dfsTraversal(Node node, List<Node> result) {
        if (node != null) {
            result.add(node); // Add the current node to the result
            for (Node child : node.getChildren()) {
                dfsTraversal(child, result); // Recursive call for each child
            }
        }
    }

    private void bfsTraversal(Node node, List<Node> result) {
        if (node == null) return;
        
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node); // Start with the root
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current); // Add the node to the result list
            
            // Add all children of the current node to the queue
            for (Node child : current.getChildren()) {
                queue.offer(child);
            }
        }
    }

    private boolean isBalanced(Node node) {
        if (node == null) {
            return true;
        }
        int leftHeight = getHeight(node.getChildren().isEmpty() ? null : node.getChildren().get(0));
        int rightHeight = node.getChildren().size() < 2 ? 0 : getHeight(node.getChildren().get(1));
        return Math.abs(leftHeight - rightHeight) <= maxHeightDifference &&
               isBalanced(node.getChildren().isEmpty() ? null : node.getChildren().get(0)) &&
               isBalanced(node.getChildren().size() < 2 ? null : node.getChildren().get(1));
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        int maxHeight = 0;
        for (Node child : node.getChildren()) {
            maxHeight = Math.max(maxHeight, getHeight(child));
        }
        return 1 + maxHeight;
    }
}
