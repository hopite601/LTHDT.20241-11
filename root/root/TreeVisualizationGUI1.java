package root;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TreeVisualizationGUI1 {

    // Node class representing a single node in the tree
    static class Node {
        int value;
        Node left, right;

        public Node(int value) {
            this.value = value;
            this.left = this.right = null;
        }
    }

    // Balanced Binary Tree class
    static class BalancedBinaryTree {
        Node root;

        // Insert a node with balancing
        public void insert(int value) {
            root = insertRecursive(root, value);
        }

        private Node insertRecursive(Node node, int value) {
            if (node == null) {
                return new Node(value);
            }

            if (value < node.value) {
                node.left = insertRecursive(node.left, value);
            } else if (value > node.value) {
                node.right = insertRecursive(node.right, value);
            } else {
                return node; // Duplicate values not allowed
            }

            return balance(node);
        }

        public void delete(int value) {
            root = deleteRecursive(root, value);
        }
        
        private Node deleteRecursive(Node node, int value) {
            if (node == null) {
                return null; // Node không tồn tại
            }
        
            // Tìm vị trí node cần xóa
            if (value < node.value) {
                node.left = deleteRecursive(node.left, value);
            } else if (value > node.value) {
                node.right = deleteRecursive(node.right, value);
            } else {
                // Node cần xóa được tìm thấy
                if (node.left == null || node.right == null) {
                    // Node có 0 hoặc 1 con
                    Node temp = (node.left != null) ? node.left : node.right;
        
                    // Trường hợp node không có con
                    if (temp == null) {
                        node = null;
                    } else {
                        node = temp; // Trường hợp node có 1 con
                    }
                } else {
                    // Node có 2 con: Thay thế bằng giá trị nhỏ nhất từ cây con phải
                    int minValue = findMin(node.right);
                    node.value = minValue;
                    node.right = deleteRecursive(node.right, minValue);
                }
            }
        
            if (node == null) {
                return null; // Trường hợp cây rỗng sau khi xóa
            }
        
            // Cân bằng lại cây sau khi xóa
            return balance(node);
        }
        

        private int findMin(Node node) {
            while (node.left != null) {
                node = node.left;
            }
            return node.value;
        }

        private int findMax(Node node) {
            while (node.right != null) {
                node = node.right;
            }
            return node.value;
        }

        public boolean search(int value) {
            return searchRecursive(root, value);
        }

        private boolean searchRecursive(Node node, int value) {
            if (node == null) {
                return false;
            }

            if (value == node.value) {
                return true;
            } else if (value < node.value) {
                return searchRecursive(node.left, value);
            } else {
                return searchRecursive(node.right, value);
            }
        }

        // Balance the node using AVL rotation rules
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
            if (node == null) {
                return 0;
            }
            return 1 + Math.max(height(node.left), height(node.right));
        }

        public Node getRoot() {
            return root;
        }

        public int getMin() {
            if (root == null) throw new IllegalStateException("Tree is empty");
            return findMin(root);
        }

        public int getMax() {
            if (root == null) throw new IllegalStateException("Tree is empty");
            return findMax(root);
        }

        public int getHeight() {
            return height(root);
        }
    }

    // Panel to draw the tree
    static class TreePanel extends JPanel {
        private BalancedBinaryTree tree;
        private Node highlightedNode;

        public TreePanel(BalancedBinaryTree tree) {
            this.tree = tree;
        }

        public void highlightNode(Node node) {
            highlightedNode = node;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (tree.getRoot() != null) {
                drawTree(g, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
            }

            // Display the tree height on the side
            g.setColor(Color.BLACK);
            g.drawString("Tree Height: " + tree.getHeight(), 10, 20);
        }

        private void drawTree(Graphics g, Node node, int x, int y, int xOffset) {
            if (node == null) {
                return;
            }

            if (node == highlightedNode) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.BLACK);
            }

            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(node.value), x - 7, y + 5);
            g.setColor(Color.BLACK);

            if (node.left != null) {
                g.drawLine(x, y, x - xOffset, y + 50);
                drawTree(g, node.left, x - xOffset, y + 50, xOffset / 2);
            }

            if (node.right != null) {
                g.drawLine(x, y, x + xOffset, y + 50);
                drawTree(g, node.right, x + xOffset, y + 50, xOffset / 2);
            }
        }
    }

    // Main GUI class
    private JFrame frame;
    private BalancedBinaryTree balancedBinaryTree;
    private TreePanel treePanel;
    private JSlider speedSlider;
    private ScheduledExecutorService executor;

    public TreeVisualizationGUI1() {
        balancedBinaryTree = new BalancedBinaryTree();
        treePanel = new TreePanel(balancedBinaryTree);
        executor = Executors.newSingleThreadScheduledExecutor();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Tree Visualization GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Left panel with buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));
        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");
        JButton findMinButton = new JButton("Find Min");
        JButton findMaxButton = new JButton("Find Max");
        JButton searchButton = new JButton("Search");
        JButton traverseButton = new JButton("Traverse");

        leftPanel.add(insertButton);
        leftPanel.add(deleteButton);
        leftPanel.add(findMinButton);
        leftPanel.add(findMaxButton);
        leftPanel.add(searchButton);
        leftPanel.add(traverseButton);

        // Add slider for speed control
        speedSlider = new JSlider(1, 1000, 500);
        speedSlider.setMajorTickSpacing(200);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        leftPanel.add(new JLabel("Traversal Speed"));
        leftPanel.add(speedSlider);

        // Add components
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(treePanel, BorderLayout.CENTER);

        // Button actions
        insertButton.addActionListener(e -> {
            String value = JOptionPane.showInputDialog(frame, "Enter value to insert:");
            if (value != null) {
                balancedBinaryTree.insert(Integer.parseInt(value));
                treePanel.repaint();
            }
        });

        deleteButton.addActionListener(e -> {
            String value = JOptionPane.showInputDialog(frame, "Enter value to delete:");
            if (value != null) {
                balancedBinaryTree.delete(Integer.parseInt(value));
                treePanel.repaint();
            }
        });

        findMinButton.addActionListener(e -> performFindMin());
        findMaxButton.addActionListener(e -> performFindMax());
        searchButton.addActionListener(e -> performSearch());
        traverseButton.addActionListener(e -> performTraversal());

        frame.setVisible(true);
    }

    private void performTraversal() {
        if (balancedBinaryTree.getRoot() == null) {
            JOptionPane.showMessageDialog(frame, "The tree is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        Queue<Node> queue = new LinkedList<>();
        queue.add(balancedBinaryTree.getRoot());

        executor.scheduleAtFixedRate(() -> {
            if (!queue.isEmpty()) {
                Node current = queue.poll();
                treePanel.highlightNode(current);

                if (current.left != null) queue.add(current.left);
                if (current.right != null) queue.add(current.right);
            } else {
                executor.shutdown();
                treePanel.highlightNode(null);
            }
        }, 0, speedSlider.getValue(), TimeUnit.MILLISECONDS);
    }

    private void performFindMin() {
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        Node root = balancedBinaryTree.getRoot();
        if (root == null) {
            JOptionPane.showMessageDialog(frame, "The tree is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        executor.execute(() -> {
            Node current = root;
            while (current.left != null) {
                treePanel.highlightNode(current);
                try {
                    Thread.sleep(speedSlider.getValue());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                current = current.left;
            }

            treePanel.highlightNode(current);
            JOptionPane.showMessageDialog(frame, "Minimum value: " + current.value);
        });
    }

    private void performFindMax() {
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        Node root = balancedBinaryTree.getRoot();
        if (root == null) {
            JOptionPane.showMessageDialog(frame, "The tree is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        executor.execute(() -> {
            Node current = root;
            while (current.right != null) {
                treePanel.highlightNode(current);
                try {
                    Thread.sleep(speedSlider.getValue());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                current = current.right;
            }

            treePanel.highlightNode(current);
            JOptionPane.showMessageDialog(frame, "Maximum value: " + current.value);
        });
    }

    private void performSearch() {
        String value = JOptionPane.showInputDialog(frame, "Enter value to search:");
        if (value == null || value.isEmpty()) {
            return;
        }

        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        int searchValue = Integer.parseInt(value);

        executor.execute(() -> {
            Node current = balancedBinaryTree.getRoot();
            while (current != null) {
                treePanel.highlightNode(current);
                try {
                    Thread.sleep(speedSlider.getValue());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                if (searchValue == current.value) {
                    JOptionPane.showMessageDialog(frame, "Value found: " + searchValue);
                    treePanel.highlightNode(null);
                    return;
                } else if (searchValue < current.value) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            JOptionPane.showMessageDialog(frame, "Value not found: " + searchValue);
            treePanel.highlightNode(null);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TreeVisualizationGUI::new);
    }
}
