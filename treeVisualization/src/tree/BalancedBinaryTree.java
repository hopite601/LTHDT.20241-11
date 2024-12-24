/**
 * 
 */
package tree;

/**
 * 
 */
public class BalancedBinaryTree extends BinaryTree {
    private int maxHeightDifference;

    public BalancedBinaryTree(int maxHeightDifference) {
        super();
        this.maxHeightDifference = maxHeightDifference;
    }

    @Override
    public boolean insertNode(Node parent, int value) {
    	if (parent != null) {
            if (parent.getChildren().size() < 2) {
                Node newNode = new Node(value);
                parent.addChild(newNode);

                if (!isBalancedBinary(this.root)) {
                    parent.getChildren().remove(newNode); // Undo insertion
                    System.out.println("Insertion would unbalance the binary tree. Operation aborted.");
                    return false;
                }

                // Rebalance the tree after insertion
                balanceTree(this.root);
                return true;
            } else {
                System.out.println("A binary node can have only two children.");
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean updateNode(int oldValue, int newValue) {
        // Kiểm tra nếu giá trị mới đã tồn tại trong cây
        if (search(newValue) != null) {
            System.out.println("Cannot update. Node with value " + newValue + " already exists.");
            return false;
        }

        // Tìm node cần cập nhật
        Node nodeToUpdate = search(oldValue);
        if (nodeToUpdate != null) {
            nodeToUpdate.setValue(newValue); // Cập nhật giá trị

            // Kiểm tra nếu cây không còn cân bằng sau khi cập nhật
            if (!isBalancedBinary(root)) {
            	balanceTree(root);
            }

            System.out.println("Updated node value from " + oldValue + " to " + newValue);
            return true;
        } else {
            System.out.println("Node with value " + oldValue + " not found.");
            return false;
        }
    }
    
    @Override
	public boolean deleteNode(int value) {
    	Node deleteNode = search(value);
        if (deleteNode != null) {
            deleteNode.removeSelf();  // Remove node from the tree
            // Rebalance the tree after deletion
            balanceTree(this.root);
            return true;
        } else {
            System.out.println("Node with value " + value + " not found.");
            return false;
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
    
    private void balanceTree(Node node) {
        if (node == null) {
            return;
        }

        // Lấy chiều cao của các nhánh con (nếu có)
        int leftHeight = node.getChildren().size() > 0 ? getHeight(node.getChildren().get(0)) : 0;
        int rightHeight = node.getChildren().size() > 1 ? getHeight(node.getChildren().get(1)) : 0;

        // Nếu mất cân bằng, thực hiện xoay
        if (Math.abs(leftHeight - rightHeight) > maxHeightDifference) {
            if (leftHeight > rightHeight) {
                node = rotateRight(node);
            } else {
                node = rotateLeft(node);
            }
        }

        // Đệ quy cân bằng các node con
        for (Node child : node.getChildren()) {
            balanceTree(child);
        }
    }

    private Node rotateLeft(Node node) {
        if (node.getChildren().size() < 2) {
            return node; // Không thể xoay nếu không có đủ con
        }

        Node newRoot = node.getChildren().get(1); // Con phải
        node.getChildren().remove(1);

        // Chuyển node hiện tại thành con trái của newRoot
        newRoot.addChild(node);

        // Di chuyển các node con khác (nếu có) để giữ đúng cấu trúc
        /*for (Node child : newRoot.getChildren()) {
            if (!child.equals(node)) {
                node.addChild(child);
                newRoot.getChildren().remove(child);
            }
        }*/

        return newRoot;
    }

    private Node rotateRight(Node node) {
        if (node.getChildren().isEmpty()) {
            return node; // Không thể xoay nếu không có con trái
        }

        Node newRoot = node.getChildren().get(0); // Con trái
        node.getChildren().remove(0);

        // Chuyển node hiện tại thành con phải của newRoot
        newRoot.addChild(node);

        // Di chuyển các node con khác (nếu có) để giữ đúng cấu trúc
        	/*for (Node child : newRoot.getChildren()) {
            if (!child.equals(node)) {
                node.addChild(child);
                newRoot.getChildren().remove(child);
            } 
        }*/

        return newRoot;
    }
}