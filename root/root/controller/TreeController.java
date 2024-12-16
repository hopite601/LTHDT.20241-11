package root.controller;

import root.model.BalancedBinaryTree;
import root.model.Node;
import root.view.TreePanel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class TreeController {
    private BalancedBinaryTree tree;
    private TreePanel treePanel;
    private int speed; // Speed in milliseconds
    private ScheduledExecutorService executor;

    public TreeController(BalancedBinaryTree tree, TreePanel treePanel) {
        this.tree = tree;
        this.treePanel = treePanel;
        this.speed = 1000; // Default speed
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void insertValue(JFrame frame) {
        String value = JOptionPane.showInputDialog(frame, "Enter value to insert:");
        if (value == null || value.isEmpty()) return;
    
        treePanel.clearHighlights();
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();
    
        executor.execute(() -> {
            try {
                int intValue = Integer.parseInt(value);
                treePanel.clearHighlights();
                Node root = tree.getRoot();
                tree.setRoot(insertWithHighlight(root, intValue));
                JOptionPane.showMessageDialog(frame, "Value inserted: " + intValue);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                treePanel.clearHighlights();
            }
        });
    }
    
    private Node insertWithHighlight(Node node, int value) throws InterruptedException {
        if (node == null) {
            Node newNode = new Node(value);
            treePanel.markVisited(newNode); // Highlight node inserted
            Thread.sleep(speed);
            return newNode;
        }
    
        treePanel.highlightCurrentNode(node);
        Thread.sleep(speed);
    
        if (value < node.value) {
            node.left = insertWithHighlight(node.left, value);
        } else if (value > node.value) {
            node.right = insertWithHighlight(node.right, value);
        }
    
        return balanceAndHighlight(node);
    }
    

    public void deleteValue(JFrame frame) {
        String value = JOptionPane.showInputDialog(frame, "Enter value to delete:");
        if (value == null || value.isEmpty()) return;
    
        treePanel.clearHighlights();
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();
    
        executor.execute(() -> {
            try {
                int intValue = Integer.parseInt(value);
                Node root = tree.getRoot();
                tree.setRoot(deleteWithHighlight(root, intValue));
                JOptionPane.showMessageDialog(frame, "Value deleted: " + intValue);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                treePanel.clearHighlights();
            }
        });
    }
    
    private Node deleteWithHighlight(Node node, int value) throws InterruptedException {
        if (node == null) return null;
    
        treePanel.highlightCurrentNode(node);
        Thread.sleep(speed);
    
        if (value < node.value) {
            node.left = deleteWithHighlight(node.left, value);
        } else if (value > node.value) {
            node.right = deleteWithHighlight(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                treePanel.markVisited(node);
                Thread.sleep(speed);
                return temp;
            }
    
            // Node có 2 con: Lấy giá trị nhỏ nhất từ cây con phải
            int minValue = tree.findMin(node.right); // Gọi hàm công khai
            treePanel.highlightCurrentNode(node);
            Thread.sleep(speed);
            node.value = minValue;
            node.right = deleteWithHighlight(node.right, minValue);
        }
    
        return balanceAndHighlight(node);
    }
    private Node balanceAndHighlight(Node node) throws InterruptedException {
        node = tree.balanceTree(node); // Gọi phương thức công khai
        treePanel.highlightCurrentNode(node); // Highlight node sau khi cân bằng
        Thread.sleep(speed);
        return node;
    }
    
    public void findMin(JFrame frame) {
        treePanel.clearHighlights();
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.execute(() -> {
            try {
                Node current = tree.getRoot();
                if (current == null) {
                    JOptionPane.showMessageDialog(frame, "The tree is empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                while (current != null) {
                    treePanel.highlightCurrentNode(current);
                    Thread.sleep(speed); // Pause for visualization
                    if (current.left == null) {
                        treePanel.markVisited(current); // Mark final node
                        JOptionPane.showMessageDialog(frame, "Minimum value: " + current.value);
                        return;
                    }
                    current = current.left;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                treePanel.clearHighlights();
            }
        });
    }

    public void findMax(JFrame frame) {
        treePanel.clearHighlights();
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.execute(() -> {
            try {
                Node current = tree.getRoot();
                if (current == null) {
                    JOptionPane.showMessageDialog(frame, "The tree is empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                while (current != null) {
                    treePanel.highlightCurrentNode(current);
                    Thread.sleep(speed); // Pause for visualization
                    if (current.right == null) {
                        treePanel.markVisited(current); // Mark final node
                        JOptionPane.showMessageDialog(frame, "Maximum value: " + current.value);
                        return;
                    }
                    current = current.right;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                treePanel.clearHighlights();
            }
        });
    }
    public void searchValue(JFrame frame) {
        String value = JOptionPane.showInputDialog(frame, "Enter value to search:");
        if (value != null) {
            boolean found = tree.search(Integer.parseInt(value));
            String message = found ? "Value found in the tree." : "Value not found in the tree.";
            JOptionPane.showMessageDialog(frame, message);
        }
    }

    public void traverseTree(JFrame frame) {
        Node root = tree.getRoot();
        if (root == null) {
            JOptionPane.showMessageDialog(frame, "The tree is empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        treePanel.clearHighlights();
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.execute(() -> {
            try {
                inOrderTraversalWithHighlight(root);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                treePanel.clearHighlights();
            }
        });
    }


    private void inOrderTraversalWithHighlight(Node node) throws InterruptedException {
        if (node == null) return;

        inOrderTraversalWithHighlight(node.left);

        treePanel.highlightCurrentNode(node);
        Thread.sleep(speed); // Pause for the specified speed
        treePanel.markVisited(node);

        inOrderTraversalWithHighlight(node.right);
    }

    public void searchValue(JFrame frame, int value) {
        treePanel.clearHighlights();
        executor.shutdownNow();
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.execute(() -> {
            try {
                Node current = tree.getRoot();
                while (current != null) {
                    treePanel.highlightCurrentNode(current);
                    Thread.sleep(speed); // Pause for the specified speed

                    if (value == current.value) {
                        JOptionPane.showMessageDialog(frame, "Value found: " + value);
                        return;
                    } else if (value < current.value) {
                        current = current.left;
                    } else {
                        current = current.right;
                    }
                }

                JOptionPane.showMessageDialog(frame, "Value not found: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                treePanel.clearHighlights();
            }
        });
    }
}