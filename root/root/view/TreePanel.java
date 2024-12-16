package root.view;

import root.model.Node;
import root.model.BalancedBinaryTree;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class TreePanel extends JPanel {
    private BalancedBinaryTree tree;
    private Node currentNode;
    private Set<Node> visitedNodes;

    public TreePanel(BalancedBinaryTree tree) {
        this.tree = tree;
        this.visitedNodes = new HashSet<>();
    }

    public void highlightCurrentNode(Node node) {
        currentNode = node;
        repaint();
    }

    public void markVisited(Node node) {
        visitedNodes.add(node);
        repaint();
    }

    public void clearHighlights() {
        currentNode = null;
        visitedNodes.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int xOffset) {
        if (node == null) return;

        if (node == currentNode) {
            g.setColor(Color.RED); // Node hiện tại
        } else if (visitedNodes.contains(node)) {
            g.setColor(Color.GREEN); // Node đã duyệt
        } else {
            g.setColor(Color.BLACK); // Node bình thường
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
