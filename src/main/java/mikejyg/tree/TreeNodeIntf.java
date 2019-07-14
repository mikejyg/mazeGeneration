package mikejyg.tree;

import java.io.PrintStream;
import java.util.Collection;

/**
 * An node of a tree.
 * 
 * Since it has references to both the parent and children, an object of this holds the entire tree. 
 */
public interface TreeNodeIntf {

	public void addChild(TreeNodeIntf child);
	
	/**
	 * @return null if root node.
	 */
	public TreeNodeIntf getParent();
	
	/**
	 * get the existing children.
	 * @return both null or an empty collection can be returned.
	 */
	public Collection< ? extends TreeNodeIntf > getChildren();

	/**
	 * print children recursively
	 * @param strm
	 */
	default public void printChildren(PrintStream strm) {
		if (getChildren() == null)
			return;

		// print the list of children first,
		for (TreeNodeIntf child: getChildren()) {
			strm.println(child.toString());
		}
		
		// then print the chilren of each child.
		for (TreeNodeIntf child: getChildren()) {
			child.printChildren(strm);
		}
	}
	
	default public void printTree(PrintStream strm) {
		strm.println(toString());
		
		printChildren(strm);
	}


}
