package mikejyg.mazeGeneration;

import java.util.Collection;

/**
 * 
 * @author mikejyg
 *
 * @param <NodeType>
 */
public interface GraphNodesIntf<NodeType> {
	
	public void addNode(NodeType node);

	public Collection<NodeType> getNodeSet();
	
	public int size();
	
}
