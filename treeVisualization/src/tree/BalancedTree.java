/**
 * 
 */
package tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 
 */
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
        if (algorithm.equalsIgnoreCase("DFS")) {
            return traverseDFS();
        } else if (algorithm.equalsIgnoreCase("BFS")) {
            return traverseBFS();
        }
        return new LinkedList<>(); // Return an empty list if no matching algorithm
    }

    // DFS traversal
    public List<Node> traverseDFS() {
        List<Node> result = new LinkedList<>();
        if (root != null) {
            traverseDFSRecursive(root, result);
        }
        return result;
    }

    private void traverseDFSRecursive(Node current, List<Node> result) {
        result.add(current); // Add the current node to the result

        for (Node child : current.getChildren()) {
            traverseDFSRecursive(child, result); // Recur for all children
        }
    }

    // BFS traversal
    public List<Node> traverseBFS() {
        List<Node> result = new LinkedList<>();
        if (root != null) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                result.add(current); // Add the current node to the result

                // Add all children to the queue
                for (Node child : current.getChildren()) {
                    queue.add(child);
                }
            }
        }
        return result;
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