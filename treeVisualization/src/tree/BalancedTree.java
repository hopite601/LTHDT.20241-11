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
    public boolean insertNode(Node parent, int value) {
        if (parent == null) {
            System.out.println("Parent node is null, cannot insert");
            return false;
        }

        // Kiểm tra nếu node đã tồn tại
        if (search(value) != null) {
            System.out.println("Node already exists with value: " + value);
            return false;
        }

        // Tạo node mới
        Node newNode = new Node(value);

        // Thêm node vào danh sách con của parent
        parent.addChild(newNode);

        // Kiểm tra nếu cây không còn cân bằng sau khi thêm node
        if (!isBalanced(root)) {
            balanceTree(root);
        }

        return true; // Trả về true nếu node được thêm vào thành công
    }

    @Override
    public void deleteNode(int value) {
        Node deleteNode = search(value);
        if (deleteNode != null) {
            deleteNode.removeSelf();
        }
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
            if (!isBalanced(root)) {
            	balanceTree(root);
            }

            System.out.println("Updated node value from " + oldValue + " to " + newValue);
            return true;
        } else {
            System.out.println("Node with value " + oldValue + " not found.");
            return false;
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
    
    private void balanceTree(Node node) {
        // Phân phối lại các node con để đảm bảo độ cao giữa các nhánh gần nhau
        List<Node> children = node.getChildren();
        while (!isBalanced(node)) {
            // Lấy các node cao nhất và thấp nhất để phân phối lại
            Node highest = children.get(0);
            Node lowest = children.get(children.size() - 1);
            // Di chuyển node từ highest sang lowest (logic cần được cụ thể hóa hơn)
            if (highest.getChildren().size() > 0) {
                Node moveNode = highest.getChildren().remove(0);
                lowest.addChild(moveNode);
            }
        }
    }
}