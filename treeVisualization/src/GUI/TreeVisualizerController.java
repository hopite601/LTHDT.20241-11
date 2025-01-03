package GUI;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Map<Node, Double> nodePositionX = new HashMap<>();
    private Map<Node, Double> nodePositionY = new HashMap<>();

    private volatile boolean isPaused = false;
    private Thread traverseThread;

    private List<Node> traverseNodes;
    private List<Integer> pseudoSteps;
    private int currentStep;
    private String traversalMethod;

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
        currentTree = new BalancedBinaryTree(1);
        updateTreeVisualizer("Binary Tree selected.");
    }

    // Het xu li chon cay

    // Thao tac tren cay
    @FXML
    void btnCreatePressed(ActionEvent event) {
        if (currentTree != null) {
            int value = TreeDialog.showCreateDialog(); // hien thi dialog de nhap value
            if (value != -1) {
                currentTree.createTree(value);
                updateTreeVisualizer("Tree created with root = " + value);
            }
        } else {
            updateTreeVisualizer("Please select a tree type first.");
        }
    }

    @FXML
    void btnInsertPressed(ActionEvent event) {
        if (currentTree != null) {
            int[] values = TreeDialog.showInsertDialog(); // hien thi dialog insert
            if (values[0] != -1 && values[1] != -1) {
                updateInsertPseudoCode(values[0], values[1]); // Hiển thị pseudo code cho hành động Insert
                new Thread(() -> {
                    String[] codeLines = getInsertPseudoCodeLines(values[0], values[1]);
                    for (int i = 0; i < codeLines.length; i++) {
                        final int step = i;
                        Platform.runLater(() -> highlightPseudoCodeLine(codeLines, step));
                        try {
                            Thread.sleep((long) (1000 / sliderSpeed.getValue())); // Điều chỉnh tốc độ dựa trên
                                                                                  // sliderSpeed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        
                        // node con da ton tai thi dung lai
                        if(i == 7) {
                        	updateTreeVisualizer("Node already exists with value: " + values[1]);
                        	return;
                        }
                        
                        // kiem tra parent co ton tai ko, neu ko thi nhay highlight xuong cuoi
                        if(step == 2 && currentTree.search(values[0]) == null) {
                        	i = 8;
                        	continue;
                        } else {
                        	// kiem tra nut con da ton tai hay chua
                        	if(step == 3 && currentTree.search(values[1]) == null) {
                        		// chay tiep
                        	} else if (step == 3 && currentTree.search(values[1]) != null) {
                        		i = 6;
                        		continue;
                        	}
                        }
                        
                        // da thoa man node cha, node con
                        if(step == 5) {
                        	Node parent = currentTree.search(values[0]);
                        	 Platform.runLater(() -> {
                                 if (currentTree.insertNode(parent, values[1])) {
                                     updateTreeVisualizer(
                                             "Parent: " + values[0] + " and child: " + values[1] + " are inserted.");
                                 }
                        	 });
                        	return;
                        }
                    
                    }
                }).start();
            } else {
                updateTreeVisualizer("Invalid input or insertion canceled.");
            }
        } else {
            updateTreeVisualizer("Please select a tree type first.");
        }
    }

    @FXML
    void btnDeletePressed(ActionEvent event) {
        if (currentTree != null) {
            int value = TreeDialog.showDeleteDialog();
            updateDeletePseudoCode(value); // Hiển thị pseudo code cho hành động Delete
            new Thread(() -> {
                String[] codeLines = getDeletePseudoCodeLines(value);
                boolean[] nodeFound = { false };
                for (int i = 0; i < codeLines.length; i++) {
                    final int step = i;
                    Platform.runLater(() -> highlightPseudoCodeLine(codeLines, step));
                    try {
                        Thread.sleep((long) (1000 / sliderSpeed.getValue())); // Điều chỉnh tốc độ dựa trên sliderSpeed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i == 3) return;
                    
                    if (step == 2 && currentTree.search(value) != null) { // Dòng lệnh xoá node khỏi tree
                        Platform.runLater(() -> {
                        	currentTree.deleteNode(value);
                            updateTreeVisualizer("Node with value " + value + " deleted.");
                            nodeFound[0] = true;
                        });
                    } else if(step == 2 && currentTree.search(value) == null) {
                    	i = 4;
                    	continue;
                    }
                }
                if (!nodeFound[0]) {
                    Platform.runLater(() -> updateTreeVisualizer("Node with value " + value + " not found."));
                }
            }).start();
        } else {
            updateTreeVisualizer("Please select a tree type first.");
        }
    }

    @FXML
    void btnUpdatePressed(ActionEvent event) {
        if (currentTree != null) {
            int[] values = TreeDialog.showUpdateDialog();
            if (values[0] != -1 && values[1] != -1) {
                updateUpdatePseudoCode(values[0], values[1]); // Hiển thị pseudo code cho hành động Update
                new Thread(() -> {
                    String[] codeLines = getUpdatePseudoCodeLines(values[0], values[1]);
                    for (int i = 0; i < codeLines.length; i++) {
                        final int step = i;
                        Platform.runLater(() -> highlightPseudoCodeLine(codeLines, step));
                        try {
                            Thread.sleep((long) (1000 / sliderSpeed.getValue())); // Điều chỉnh tốc độ dựa trên
                                                                                  // sliderSpeed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(i == 6) return;
                        
                        // kiem tra oldValue co ton tai ko
                        if(i == 2 && currentTree.search(values[0]) != null) {
                        	// kiem tra newValue co ton tai ko
                        	if( i == 3 && currentTree.search(values[1]) != null) {
                        		i = 5;
                        		continue;
                        	} else if (i == 3 && currentTree.search(values[1]) == null) {
                        		i = 4;
                        	}
                        } else if ( i == 2 && currentTree.search(values[0]) == null) {
                        	i = 7;
                        	continue;
                        }
                        
                        
                        if (step == 4) { 
                        	 Platform.runLater(() -> {
                             	currentTree.updateNode(values[0], values[1]);
                            	updateTreeVisualizer("Node updated from " + values[0] + " to " + values[1]);
                        	 });
                            return;
                        }
                    }
                }).start();
            } else {
                updateTreeVisualizer("Invalid input or update canceled.");
            }
        } else {
            updateTreeVisualizer("Please select a tree type first.");
        }
    }

    private String[] getInsertPseudoCodeLines(int parentValue, int childValue) {
        return new String[] {
                "Insert(" + parentValue + ", " + childValue + "):",
                "  parent = findNodeByValue(root, " + parentValue + ")",
                "  if parent is not null:",
                "    if child does not exist:",
                "      create new node with value " + childValue,
                "      add child to parent",
                "    else:",
                "      print 'Node already exists with value: " + childValue + "'",
                "  else:",
                "    print 'Parent node not found'"
        };
    }

    private String[] getDeletePseudoCodeLines(int value) {
        return new String[] {
                "Delete(" + value + "):",
                "  node = findNodeByValue(root, " + value + ")",
                "  if node is not null:",
                "    remove node from tree",
                "  else:",
                "    print 'Node with value " + value + " not found'"
        };
    }

    private String[] getUpdatePseudoCodeLines(int oldValue, int newValue) {
        return new String[] {
                "Update(" + oldValue + ", " + newValue + "):",
                "  node = findNodeByValue(root, " + oldValue + ")",
                "  if node is not null:",
                "    if " + newValue + " does not exist:",
                "      update node value to " + newValue,
                "    else:",
                "      print 'Node with value " + newValue + " already exists'",
                "  else:",
                "    print 'Node with value " + oldValue + " not found'"
        };
    }

    private void updateInsertPseudoCode(int parentValue, int childValue) {
        pseudoCode.getChildren().clear();
        VBox vbox = new VBox();
        String[] codeLines = new String[] {
                "Insert(" + parentValue + ", " + childValue + "):",
                "  parent = findNodeByValue(root, " + parentValue + ")",
                "  if parent is not null:",
                "    if child does not exist:",
                "      create new node with value " + childValue,
                "      add child to parent",
                "    else:",
                "      print 'Node already exists with value: " + childValue + "'",
                "  else:",
                "    print 'Parent node not found'"
        };
        for (String line : codeLines) {
            Text text = new Text(line);
            vbox.getChildren().add(text);
        }
        pseudoCode.getChildren().add(vbox);
    }

    private void updateDeletePseudoCode(int value) {
        pseudoCode.getChildren().clear();
        VBox vbox = new VBox();
        String[] codeLines = new String[] {
                "Delete(" + value + "):",
                "  node = findNodeByValue(root, " + value + ")",
                "  if node is not null:",
                "    remove node from tree",
                "  else:",
                "    print 'Node with value " + value + " not found'"
        };
        for (String line : codeLines) {
            Text text = new Text(line);
            vbox.getChildren().add(text);
        }
        pseudoCode.getChildren().add(vbox);
    }

    private void updateUpdatePseudoCode(int oldValue, int newValue) {
        pseudoCode.getChildren().clear();
        VBox vbox = new VBox();
        String[] codeLines = new String[] {
                "Update(" + oldValue + ", " + newValue + "):",
                "  node = findNodeByValue(root, " + oldValue + ")",
                "  if node is not null:",
                "    if " + newValue + " does not exist:",
                "      update node value to " + newValue,
                "    else:",
                "      print 'Node with value " + newValue + " already exists'",
                "  else:",
                "    print 'Node with value " + oldValue + " not found'"
        };
        for (String line : codeLines) {
            Text text = new Text(line);
            vbox.getChildren().add(text);
        }
        pseudoCode.getChildren().add(vbox);
    }

    @FXML
    void btnTraversePressed(ActionEvent event) {
        if (currentTree != null) {
            traversalMethod = TreeDialog.showTraversalDialog(); // Hiển thị dialog chọn phương pháp traverse
            if (traversalMethod != null) {
                updatePseudoCode(traversalMethod); // Hiển thị mã giả của phương pháp traverse
                traverseTree(traversalMethod); // Bắt đầu quá trình traverse
            }
        } else {
            updateTreeVisualizer("Please select a tree type first.");
        }
    }

    private void updatePseudoCode(String method) {
        pseudoCode.getChildren().clear();
        VBox vbox = new VBox();
        String[] codeLines;
        if ("DFS".equalsIgnoreCase(method)) {
            codeLines = new String[] {
                    "DFS(node):",
                    "  if node is null:",
                    "    return",
                    "  visit(node)",
                    "  for each child in node.children:",
                    "    DFS(child)"
            };
        } else {
            codeLines = new String[] {
                    "BFS(node):",
                    "  queue = new Queue()",
                    "  queue.enqueue(node)",
                    "  while not queue.isEmpty():",
                    "    current = queue.dequeue()",
                    "    visit(current)",
                    "    for each child in current.children:",
                    "      queue.enqueue(child)"
            };
        }
        for (String line : codeLines) {
            Text text = new Text(line);
            vbox.getChildren().add(text);
        }
        pseudoCode.getChildren().add(vbox);
    }

    private void traverseTree(String method) {
        traverseNodes = currentTree.traverse(method);
        pseudoSteps = new ArrayList<>();
        currentStep = 0;

        traverseThread = new Thread(() -> {
            for (int i = 0; i < traverseNodes.size(); i++) {
                Node node = traverseNodes.get(i);
                for (int j = 0; j < getPseudoCodeLines(method).length; j++) {
                    final int step = j;
                    Platform.runLater(() -> {
                        highlightPseudoCodeLine(getPseudoCodeLines(method), step);
                        if (isVisitLine(method, step)) {
                            highlightNode(node);
                        }
                    });
                    pseudoSteps.add(step);
                    try {
                        Thread.sleep((long) (1000 / sliderSpeed.getValue())); // Điều chỉnh tốc độ dựa trên sliderSpeed
                        synchronized (traverseThread) {
                            while (isPaused) {
                                traverseThread.wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        traverseThread.start();
    }

    private boolean isVisitLine(String method, int step) {
        if ("DFS".equalsIgnoreCase(method)) {
            return step == 3; // Dòng visit(node) trong DFS
        } else {
            return step == 5; // Dòng visit(current) trong BFS
        }
    }

    private String[] getPseudoCodeLines(String method) {
        if ("DFS".equalsIgnoreCase(method)) {
            return new String[] {
                    "DFS(node):",
                    "  if node is null:",
                    "    return",
                    "  visit(node)",
                    "  for each child in node.children:",
                    "    DFS(child)"
            };
        } else {
            return new String[] {
                    "BFS(node):",
                    "  queue = new Queue()",
                    "  queue.enqueue(node)",
                    "  while not queue.isEmpty():",
                    "    current = queue.dequeue()",
                    "    visit(current)",
                    "    for each child in current.children:",
                    "      queue.enqueue(child)"
            };
        }
    }

    private void highlightNode(Node node) {
        updateTreeVisualizer(""); // Cập nhật lại cây
        drawTree(currentTree.getRoot(), 300, 50, 100); // Vẽ lại toàn bộ cây
        Circle highlight = new Circle(300, 50, 20); // Tạo highlight cho node
        highlight.setStyle("-fx-fill: yellow;");
        highlight.setCenterX(findNodePositionX(node)); // Đặt vị trí X của highlight
        highlight.setCenterY(findNodePositionY(node)); // Đặt vị trí Y của highlight
        treeVisualizer.getChildren().add(highlight);
    }

    private double findNodePositionX(Node node) {
        return nodePositionX.getOrDefault(node, 0.0);
    }

    private double findNodePositionY(Node node) {
        return nodePositionY.getOrDefault(node, 0.0);
    }

    private void highlightPseudoCodeLine(String[] codeLines, int step) {
        pseudoCode.getChildren().clear();
        VBox vbox = new VBox();
        for (int i = 0; i < codeLines.length; i++) {
            Text text = new Text(codeLines[i]);
            if (i == step) {
                text.setStyle("-fx-font-weight: bold; -fx-fill: red;");
            }
            vbox.getChildren().add(text);
        }
        pseudoCode.getChildren().add(vbox);
    }

    @FXML
    void btnSearchPressed(ActionEvent event) {
        if (currentTree != null) {
            int value = TreeDialog.showSearchDialog(); // Hiển thị dialog nhập giá trị node muốn tìm
            traversalMethod = TreeDialog.showTraversalDialog(); // Hiển thị dialog chọn phương pháp tìm kiếm
            if (value != -1 && traversalMethod != null) {
                updatePseudoCode(traversalMethod); // Hiển thị mã giả của phương pháp tìm kiếm
                searchTree(value, traversalMethod); // Bắt đầu quá trình tìm kiếm
            }
        } else {
            updateTreeVisualizer("Please select a tree type first.");
        }
    }

    private void searchTree(int value, String method) {
        traverseNodes = currentTree.traverse(method);
        pseudoSteps = new ArrayList<>();
        currentStep = 0;

        traverseThread = new Thread(() -> {
            boolean[] found = { false };
            Node[] foundNode = { null };
            for (int i = 0; i < traverseNodes.size(); i++) {
                Node node = traverseNodes.get(i);
                for (int j = 0; j < getPseudoCodeLines(method).length; j++) {
                    final int step = j;
                    Platform.runLater(() -> {
                        highlightPseudoCodeLine(getPseudoCodeLines(method), step);
                        if (isVisitLine(method, step)) {
                            highlightNode(node);
                            if (node.getValue() == value) {
                                found[0] = true;
                                foundNode[0] = node;
                            }
                        }
                    });
                    pseudoSteps.add(step);
                    try {
                        Thread.sleep((long) (1000 / sliderSpeed.getValue())); // Điều chỉnh tốc độ dựa trên sliderSpeed
                        synchronized (traverseThread) {
                            while (isPaused) {
                                traverseThread.wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (found[0]) {
                        break;
                    }
                }
                if (found[0]) {
                    break;
                }
            }
            if (!found[0]) {
                Platform.runLater(() -> updateTreeVisualizer("Node with value " + value + " not found."));
            } else {
                final Node finalFoundNode = foundNode[0];
                Platform.runLater(() -> {
                    updateTreeVisualizer("Node with value " + value + " found.");
                    highlightNode(finalFoundNode); // Giữ highlight trên node được tìm thấy
                });
            }
        });
        traverseThread.start();
    }

    // Het thao tac tren cay

    // tro giup
    @FXML
    void btnHelpPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User Guide");
        alert.setHeaderText("Instructions for Tree Operations");
        alert.setContentText("""
                1. Select a tree type to get started.
                2. Use the operation buttons like Create, Insert, Delete, and Update to manipulate the tree.
                3. Each operation will update the visualization on the screen.
                4. To exit the application, click the Quit button.
                """);
        alert.showAndWait();
    }

    @FXML
    void btnQuitPressed(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click 'OK' to exit or 'Cancel' to go back.");

        // Hiển thị và xử lý kết quả
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit(); // Thoát ứng dụng
            }
        });
    }

    @FXML
    void stepBackward(ActionEvent event) {
        if (isPaused && currentStep > 0) {
            currentStep--;
            updateNodeHighlight();
            clearPseudoCodeHighlight();
        }
    }

    @FXML
    void btnPlayPressed(ActionEvent event) {
        isPaused = false;
        synchronized (traverseThread) {
            traverseThread.notify();
        }
    }

    @FXML
    void btnPausePressed(ActionEvent event) {
        isPaused = true;
    }

    @FXML
    void stepForward(ActionEvent event) {
        if (isPaused && currentStep < traverseNodes.size() - 1) {
            currentStep++;
            updateNodeHighlight();
            clearPseudoCodeHighlight();
        }
    }

    private void updateNodeHighlight() {
        Node currentNode = traverseNodes.get(currentStep);
        highlightNode(currentNode);
    }

    private void clearPseudoCodeHighlight() {
        pseudoCode.getChildren().clear();
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
        label.setStyle("-fx-font-size: 14px;");
        label.setX(10);
        label.setY(30);
        treeVisualizer.getChildren().add(label);
    }

    private void drawTree(Node node, double x, double y, double dx) {
        if (node == null)
            return;

        double radius = 20; // ban kinh hinh tron

        // Lưu vị trí của node
        nodePositionX.put(node, x);
        nodePositionY.put(node, y);

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
// test