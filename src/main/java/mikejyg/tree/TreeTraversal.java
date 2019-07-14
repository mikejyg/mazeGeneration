package mikejyg.tree;

import java.util.function.Consumer;

/**
 * traverse the TreeNodeIntf.
 * 
 * @author mikejyg
 *
 */
public class TreeTraversal {

	// working variable
	private Consumer<TreeNodeIntf> consumer;

	/////////////////////////////////////////////////

	private void traverseRecursive(TreeNodeIntf node) {
		consumer.accept(node);
			
		var children = node.getChildren();
		if (children==null)
			return;
		
		for (var child : children) {
			traverseRecursive(child);
		}
		
	}

	/////////////////////////////////////////////////
	
	public void traverse(TreeNodeIntf root, Consumer<TreeNodeIntf> consumer) {
		this.consumer = consumer;

		if (root!=null)
			traverseRecursive(root);
	}
	
	
}
