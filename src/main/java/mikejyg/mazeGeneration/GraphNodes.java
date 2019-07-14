package mikejyg.mazeGeneration;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * a common base class for a graph, that holds nodes.
 * 
 * @author mikejyg
 *
 */
public class GraphNodes<IdType, NodeType extends NodeIntf<IdType> > implements GraphNodesIntf<NodeType> {
	// the node set
	protected Map<IdType, NodeType> nodeSet = new TreeMap<>();
	
	@Override
	public Collection<NodeType> getNodeSet() {
		return nodeSet.values();
	}
	
	@Override
	public void addNode(NodeType node) {
		nodeSet.put(node.getId(), node);
	}
	
	@Override
	public int size() {
		return nodeSet.size();
	}
	

}
