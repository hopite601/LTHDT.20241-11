package root;

import root.controller.TreeController;
import root.model.BalancedBinaryTree;
import root.view.TreePanel;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizationGUI {
    public TreeVisualizationGUI() {
        BalancedBinaryTree tree = new BalancedBinaryTree();
        TreePanel treePanel = new TreePanel(tree);
        TreeController controller = new TreeController(tree, treePanel);

        JFrame frame = new JFrame("Tree Visualization GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(8, 1));
        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");
        JButton findMinButton = new JButton("Find Min");
        JButton findMaxButton = new JButton("Find Max");
        JButton searchButton = new JButton("Search");
        JButton traverseButton = new JButton("Traverse");
        JLabel speedLabel = new JLabel("Traversal Speed:");
        JSlider speedSlider = new JSlider(200, 2000, 1000);

        speedSlider.setMajorTickSpacing(400);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        leftPanel.add(insertButton);
        leftPanel.add(deleteButton);
        leftPanel.add(findMinButton);
        leftPanel.add(findMaxButton);
        leftPanel.add(searchButton);
        leftPanel.add(traverseButton);
        leftPanel.add(speedLabel);
        leftPanel.add(speedSlider);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(treePanel, BorderLayout.CENTER);

        insertButton.addActionListener(e -> controller.insertValue(frame));
        deleteButton.addActionListener(e -> controller.deleteValue(frame));
        findMinButton.addActionListener(e -> controller.findMin(frame));
        findMaxButton.addActionListener(e -> controller.findMax(frame));
        searchButton.addActionListener(e -> {
            String value = JOptionPane.showInputDialog(frame, "Enter value to search:");
            if (value != null) {
                controller.searchValue(frame, Integer.parseInt(value));
            }
        });
        traverseButton.addActionListener(e -> controller.traverseTree(frame));
        speedSlider.addChangeListener(e -> controller.setSpeed(speedSlider.getValue()));

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TreeVisualizationGUI::new);
    }
}
