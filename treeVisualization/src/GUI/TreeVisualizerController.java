package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import tree.BalancedBinaryTree;
import tree.BalancedTree;
import tree.BinaryTree;
import tree.GenericTree;
import tree.Node;
import tree.Tree;

import java.util.List;

public class TreeVisualizerController {
    @FXML
    private Pane treeVisualizer; // man hinh chinh hien thi

    private Tree currentTree; // luu tru cay hien tai

    private Dialog<ButtonType> dialog;

    @FXML
    private Button btnGenericTree, btnBinaryTree, btnBalancedTree, btnBalancedBinaryTree; // nut chon loai cay

    @FXML
    private Button btnCreate, btnInsert, btnDelete, btnUpdate, btnTraverse, btnSearch; // cac nut thao tac tren cay

    @FXML
    private Button btnBackward, btnPlay, btnPause, btnForward; // cac nut dieu khien

    @FXML
    private Button btnHelp, btnQuit; // 1 so nut khac

    @FXML
    private Pane pseudoCode; // man hinh hien thi ma gia

    @FXML
    private Slider sliderSpeed; // thanh truot toc do

    // Xu li chon cay
    @FXML
    void selectGenericTree(ActionEvent event) {
        currentTree = new GenericTree();
        updateTreeVisualizer("Generic Tree selected.");
    }

    @FXML
    void selectBalancedBinaryTree(ActionEvent event) {
        currentTree = new BinaryTree();
        updateTreeVisualizer("Binary Tree selected.");
    }

    @FXML
    void selectBalancedTree(ActionEvent event) {
        currentTree = new BalancedTree(1);
        updateTreeVisualizer("Balanced Tree selected.");
    }

    @FXML
    void selectBinaryTree(ActionEvent event) {
        currentTree = new BalancedBinaryTree(1);
        updateTreeVisualizer("Balanced Binary Tree selected.");
    }

    // Het xu li chon cay

    // Thao tac tren cay
    @FXML
    void btnCreatePressed(ActionEvent event) {
        if (currentTree != null && currentTree instanceof GenericTree) {
            int value = TreeDialog.showCreateDialog(); // hien thi dialog de nhap value
            currentTree.createTree(value);
            updateTreeVisualizer("Tree created with root = " + value);
        } else if (true) {

        } else if (true) {

        } else if (true) {

        }
    }

    @FXML
    void btnInsertPressed(ActionEvent event) {
        if (currentTree != null && currentTree instanceof GenericTree) {
            int[] values = TreeDialog.showInsertDialog(); // hien thi dialog insert
            if (values[0] != -1 && values[1] != -1) {
                Node parent = currentTree.findNodeByValue(currentTree.getRoot(), values[0]);

                // kiem tra xem them node dc co dc ko
                if (currentTree.insertNode(parent, values[1])) {
                    updateTreeVisualizer("Parent: " + values[0] + " and child: " + values[1] + " are inserted.");
                } else {
                    updateTreeVisualizer("Invalid input or insertion canceled.");
                }
            } else {
                updateTreeVisualizer("Invalid input or insertion canceled.");
            }
        } else if (true) {

        } else if (true) {

        } else if (true) {

        }

    }

    @FXML
    void btnDeletePressed(ActionEvent event) {
        if (currentTree != null) {
            currentTree.deleteNode(5);
            updateTreeVisualizer("Node with value 5 deleted.");
        }
    }

    @FXML
    void btnUpdatePressed(ActionEvent event) {
        if (currentTree != null) {
            currentTree.updateNode(5, 10); // Cập nhật giá trị của nút từ 5 thành 10
            updateTreeVisualizer("Node updated from 5 to 10.");
        }
    }

    @FXML
    void btnTraversePressed(ActionEvent event) {
        if (currentTree != null) {
            String traversalMethod = TreeDialog.showTraversalDialog();
            if (traversalMethod != null) {
                pseudoCode.getChildren().clear();
                pseudoCode.getChildren().add(new Label("Traversal using " + traversalMethod));
                pseudoCode.getChildren().add(new Label("1. Start at the root node."));
                pseudoCode.getChildren().add(new Label("2. Visit the current node."));
                pseudoCode.getChildren()
                        .add(new Label("3. Move to the next node according to " + traversalMethod + " rules."));
                pseudoCode.getChildren().add(new Label("4. Repeat steps 2-3 until all nodes are visited."));

                List<Node> traversalResult = currentTree.traverse(traversalMethod);

                // Clear the visualizer
                treeVisualizer.getChildren().clear();

                // Display the traversal result with highlighting
                double x = 50;
                double y = 50;
                double dx = 50;
                for (Node node : traversalResult) {
                    Circle circle = new Circle(x, y, 20);
                    circle.setStyle("-fx-fill: yellow;"); // Highlight the current node
                    Text text = new Text(x - 5, y + 5, String.valueOf(node.getValue()));
                    treeVisualizer.getChildren().addAll(circle, text);
                    x += dx;

                    // Pause to show the highlighting effect
                    try {
                        Thread.sleep(500); // Adjust the delay as needed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Reset the node color after highlighting
                    circle.setStyle("-fx-fill: lightblue;");
                }
            } else {
                updateTreeVisualizer("Traversal canceled.");
            }
        } else {
            updateTreeVisualizer("No tree selected.");
        }
    }

    @FXML
    void btnSearchPressed(ActionEvent event) {
        if (currentTree != null) {
            int value = TreeDialog.showSearchDialog();
            if (value != -1) {
                pseudoCode.getChildren().clear();
                pseudoCode.getChildren().add(new Label("Searching for node with value: " + value));
                pseudoCode.getChildren().add(new Label("1. Start at the root node."));
                pseudoCode.getChildren().add(new Label("2. Compare the current node's value with the target value."));
                pseudoCode.getChildren().add(new Label("3. If they are equal, the node is found."));
                pseudoCode.getChildren().add(new Label("4. If not, move to the next child and repeat step 2."));
                pseudoCode.getChildren().add(new Label("5. If no children left, the node is not in the tree."));

                searchAndHighlight(currentTree.getRoot(), value);
            } else {
                updateTreeVisualizer("Search canceled.");
            }
        } else {
            updateTreeVisualizer("No tree selected.");
        }
    }

    private void searchAndHighlight(Node node, int value) {
        if (node == null)
            return;

        // Highlight the current node
        treeVisualizer.getChildren().clear();
        drawTree(currentTree.getRoot(), 300, 50, 100);
        Circle highlight = new Circle(300, 50, 20);
        highlight.setStyle("-fx-fill: yellow;");
        treeVisualizer.getChildren().add(highlight);

        if (node.getValue() == value) {
            updateTreeVisualizer("Node with value " + value + " found.");
            return;
        }

        for (Node child : node.getChildren()) {
            searchAndHighlight(child, value);
        }
    }

    private void searchAndHighlight(Node node, int value) {
        if (node == null)
            return;

        // Highlight the current node
        treeVisualizer.getChildren().clear();
        drawTree(currentTree.getRoot(), 300, 50, 100);
        Circle highlight = new Circle(300, 50, 20);
        highlight.setStyle("-fx-fill: yellow;");
        treeVisualizer.getChildren().add(highlight);

        if (node.getValue() == value) {
            updateTreeVisualizer("Node with value " + value + " found.");
            return;
        }

        for (Node child : node.getChildren()) {
            searchAndHighlight(child, value);
        }
    }

    // Het thao tac tren cay

    // tro giup
    @FXML
    void btnHelpPressed(ActionEvent event) {

    }

    @FXML
    void btnQuitPressed(ActionEvent event) {

    }

    // Het tro giup

    // bottom bar
    @FXML
    void stepBackward(ActionEvent event) {

    }

    @FXML
    void btnPlayPressed(ActionEvent event) {

    }

    @FXML
    void btnPausePressed(ActionEvent event) {

    }

    @FXML
    void stepForward(ActionEvent event) {

    }
    // het bottom bar

    private void updateTreeVisualizer(String message) {
        // Xóa tất cả các phần tử cũ
        treeVisualizer.getChildren().clear();

        if (currentTree != null) {
            // Vẽ cây sau mỗi thao tác
            drawTree(currentTree.getRoot(), 300, 50, 100); // Tọa độ (300, 50) là gốc, và khoảng cách giua 2 nut con là
                                                           // 100
        }

        // Thêm một Label hiển thị thông báo vào cây
        Text label = new Text(message);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: blue;");
        label.setX(10);
        label.setY(30);
        treeVisualizer.getChildren().add(label);
    }

    private void drawTree(Node node, double x, double y, double dx) {
        if (node == null)
            return;

        double radius = 20; // ban kinh hinh tron

        // Vẽ node hiện tại
        Circle circle = new Circle(x, y, radius);
        circle.setStyle("-fx-fill: lightblue;");
        Text text = new Text(x - 5, y + 5, String.valueOf(node.getValue()));
        treeVisualizer.getChildren().addAll(circle, text);

        // Ve ket noi tu nut cha den nut con
        double childX = x - (node.getChildren().size() - 1) * dx / 2; // Đảm bảo các node con phân bố đều
        for (Node child : node.getChildren()) {
            // Tính toán điểm nối tại rìa của node cha và node con
            double startX = x;
            double startY = y + radius; // Điểm nối ở rìa dưới của node cha
            double endX = childX;
            double endY = y + 100 - radius; // Điểm nối ở rìa trên của node con

            // ve duong noi tu (startX, startY) -> (endX, endY)
            Line line = new Line(startX, startY, endX, endY);
            treeVisualizer.getChildren().add(line);

            // Vẽ tiếp các node con
            drawTree(child, childX, y + 100, dx / 2);
            childX += dx; // Di chuyển tọa độ cho các node con tiếp theo
        }
    }
}
