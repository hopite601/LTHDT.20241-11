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
    public boolean insertNode(Node parent, int value) {
        if (parent != null) {
            Node newNode = new Node(value);
            parent.addChild(newNode);

            if (!isBalanced(this.root)) {
                parent.getChildren().remove(newNode); // Undo insertion
                System.out.println("Insertion would unbalance the tree. Operation aborted.");
                return false;
            }
            
            return true;
        }
        return false;
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