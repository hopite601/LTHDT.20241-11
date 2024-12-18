package tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private int value;
	private List<Node> children;
	private Node parent;
	
	// constructor
	public Node(int value) {
		this.value = value;
		this.children = new ArrayList<>();
		this.parent = null;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public List<Node> getChildren() {
		return children;
	}
	// them nut con
	public void addChild(Node child) {
		this.children.add(child);
		child.setParent(this);
	}
	public void removeChild(Node child) {
		this.children.remove(child);
		child.setParent(null);
	}
	
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    
    // xoa node khoi cay
    public void removeSelf() {
        if (this.parent != null) {
            this.parent.removeChild(this);
        }
        for (Node child : children) {
            child.parent = null;
        }
    } 
	
}
