package mikejyg.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * a simple tree node.
 * 
 * @author mikejyg
 *
 */
public class TreeNode<NodeDataType> implements TreeNodeIntf {
	private TreeNode<NodeDataType> parent;
	private List< TreeNode<NodeDataType> > children;
	
	private NodeDataType nodeData;
	
	///////////////////////////////////////////////////
	
	public TreeNode(NodeDataType nodeData) {
		this.nodeData = nodeData;
	}
	
	public TreeNode(NodeDataType nodeData, TreeNode<NodeDataType> parent) {
		this(nodeData);
		this.parent = parent;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addChild(TreeNodeIntf child) {
		if (children==null)
			children = new ArrayList<>();
		
		children.add((TreeNode<NodeDataType>) child);
	}

	@Override
	public TreeNode<NodeDataType> getParent() {
		return parent;
	}
	
	@Override
	public Collection<? extends TreeNodeIntf> getChildren() {
		return children;
	}

	public NodeDataType getNodeData() {
		return nodeData;
	}

	
}
