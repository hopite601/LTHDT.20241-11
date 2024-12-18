package tree;

public class BalancedBinaryTree extends BinaryTree {
    private int maxHeightDifference;

    public BalancedBinaryTree(int maxHeightDifference) {
        super();
        this.maxHeightDifference = maxHeightDifference;
    }

    @Override
    public void insertNode(Node parent, int value) {
        if (parent != null) {
            if (parent.getChildren().size() < 2) {
                Node newNode = new Node(value);
                parent.addChild(newNode);

                if (!isBalancedBinary(this.root)) {
                    parent.getChildren().remove(newNode); // Undo insertion
                    System.out.println("Insertion would unbalance the binary tree. Operation aborted.");
                }
            } else {
                System.out.println("A binary node can have only two children.");
            }
        }
    }

    public boolean isBalancedBinary(Node node) {
        if (node == null) {
            return true;
        }
        int leftHeight = getHeight(node.getChildren().isEmpty() ? null : node.getChildren().get(0));
        int rightHeight = node.getChildren().size() < 2 ? 0 : getHeight(node.getChildren().get(1));
        return Math.abs(leftHeight - rightHeight) <= maxHeightDifference &&
               isBalancedBinary(node.getChildren().isEmpty() ? null : node.getChildren().get(0)) &&
               isBalancedBinary(node.getChildren().size() < 2 ? null : node.getChildren().get(1));
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
