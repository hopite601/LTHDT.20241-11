package GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import tree.BalancedBinaryTree;
import tree.BalancedTree;
import tree.BinaryTree;
import tree.GenericTree;
import tree.Node;
import tree.Tree;

public class TreeVisualizerController {
    @FXML
    private Pane treeVisualizer;  // man hinh chinh hien thi
    
    private Tree currentTree; // luu tru cay hien tai
    

    private Dialog<ButtonType> dialog;
    
    @FXML
    private Button btnGenericTree, btnBinaryTree, btnBalancedTree, btnBalancedBinaryTree; // nut chon loai cay
    
    @FXML
    private Button btnCreate, btnInsert, btnDelete, btnUpdate, btnTraverse, btnSearch ; // cac nut thao tac tren cay

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
    	int maxHeightDif = TreeDialog.maxHeightDifDialog(); // nhap maxHeight
    	currentTree = new BalancedBinaryTree(maxHeightDif);
    	updateTreeVisualizer("BalancedBinary Tree selected.");
    }

    @FXML
    void selectBalancedTree(ActionEvent event) {
    	int maxHeightDif = TreeDialog.maxHeightDifDialog(); // nhap maxHeight
    	currentTree = new BalancedTree(maxHeightDif);
    	updateTreeVisualizer("Balanced Tree selected.");
    }

    @FXML
    void selectBinaryTree(ActionEvent event) {
    	currentTree = new BinaryTree();
    	updateTreeVisualizer("Binary Tree selected.");
    }
    
    // Het xu li chon cay
    
    
    // Thao tac tren cay
    @FXML
    void btnCreatePressed(ActionEvent event) {
    	 if (currentTree != null ) {
    		 int value = TreeDialog.showCreateDialog(); // hien thi dialog de nhap value
             if(value != - 1) {
            	 currentTree.createTree(value);  
            	 updateTreeVisualizer("Tree created with root = " + value);
             }
          }

    }
    
    @FXML
    void btnInsertPressed(ActionEvent event) {
        if (currentTree != null) {
        	int[] values = TreeDialog.showInsertDialog(); // hien thi dialog insert
        	if(values[0] != -1 && values[1] != -1) {
        		Node parent = currentTree.findNodeByValue(currentTree.getRoot(), values[0]);
        		
        		// kiem tra xem them node dc co dc ko
        		if(currentTree.insertNode(parent, values[1])) {
        			updateTreeVisualizer("Parent: " + values[0] + " and child: " + values[1] + " are inserted.");
        		} else {
        			updateTreeVisualizer("Invalid input or insertion canceled.");
        		}
        	} else {
        		updateTreeVisualizer("Invalid input or insertion canceled.");
        	}
        }
    }
    @FXML
    void btnDeletePressed(ActionEvent event) {
        if (currentTree != null) {
        	int value = TreeDialog.showDeleteDialog();
        	if(currentTree.deleteNode(value)) {
        		updateTreeVisualizer("Node with value " + value + " deleted.");
        	} else {
        		updateTreeVisualizer("Invalid input or insertion canceled.");
        	}
        }else {
        	updateTreeVisualizer("Invalid input or insertion canceled.");
        }
    }
    
    @FXML
    void btnUpdatePressed(ActionEvent event) {
        if (currentTree != null) {
        	int[] values = TreeDialog.showUpdateDialog();
        	if(values[0] != -1 && values[1] != -1) {
        		if(currentTree.updateNode(values[0], values[1])) {
        			updateTreeVisualizer("Node updated from " + values[0]  + " to " + values[1]);
        		}else {
        			updateTreeVisualizer("old value not exists OR new value already exists");
        		}
 
        	} else {
        		updateTreeVisualizer("Invalid input or insertion canceled.");
        	}
    
        }else {
        	updateTreeVisualizer("Invalid input or insertion canceled.");
        }
    }
    
    @FXML
    void btnTraversePressed(ActionEvent event) {

    }
    
    @FXML
    void btnSearchPressed(ActionEvent event) {

    }
    
    // Het thao tac tren cay

    
    // tro giup
    @FXML
    void btnHelpPressed(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("User Guide");
    	alert.setHeaderText("Instructions for Tree Operations");
    	alert.setContentText("1. Select a tree type to get started.\n" +
    		    "2. Use the operation buttons like Create, Insert, Delete, and Update to manipulate the tree.\n" +
    		    "3. Each operation will update the visualization on the screen.\n" +
    		    "4. To exit the application, click the Quit button.\n" );
    	alert.showAndWait();
    }
    
    @FXML
    void btnQuitPressed(ActionEvent event) {
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
         alert.setTitle("Xác nhận thoát");
         alert.setHeaderText("Bạn có chắc chắn muốn thoát không?");
         alert.setContentText("Nhấn 'OK' để thoát hoặc 'Cancel' để quay lại.");

         // Hiển thị và xử lý kết quả
         alert.showAndWait().ifPresent(response -> {
             if (response == ButtonType.OK) {
                 Platform.exit(); // Thoát ứng dụng
             }
         });
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
            drawTree(currentTree.getRoot(), 300, 50, 100);  // Tọa độ (300, 50) là gốc, và khoảng cách giua 2 nut con là 100
        }

        // Thêm một Label hiển thị thông báo vào cây
        Text label = new Text(message);
        label.setStyle("-fx-font-size: 14px;");
        label.setX(10);
        label.setY(30);
        treeVisualizer.getChildren().add(label);
    }
    
    private void drawTree(Node node, double x, double y, double dx) {
        if (node == null) return;

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
            double startY = y + radius;  // Điểm nối ở rìa dưới của node cha
            double endX = childX;
            double endY = y + 100 - radius;  // Điểm nối ở rìa trên của node con

            // ve duong noi tu (startX, startY) -> (endX, endY)
            Line line = new Line(startX, startY, endX, endY);
            treeVisualizer.getChildren().add(line);

            // Vẽ tiếp các node con
            drawTree(child, childX, y + 100, dx / 2);
            childX += dx;  // Di chuyển tọa độ cho các node con tiếp theo
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
    

    

}
