package mikejyg.mazeGeneration;

import java.util.Collection;

/**
 * an interface common to both directed graph and undirected graph.
 * @author mikejyg
 *
 */
public interface GraphIntf< IdType, NodeType extends NodeIntf<IdType> > extends GraphNodesIntf<NodeType> {
	
	public void addConnectionOneWay(NodeType fromNode, NodeType toNode);
	
	public void addConnectionBothWays(NodeType fromNode, NodeType toNode);
	
	public Collection< ? extends ConnectionIntf<NodeType> > getConnectionsFrom(NodeType fromNode);
	
	public boolean isConnection(NodeType fromNode, NodeType toNode);
	
	public void removeConnectionOneWay(NodeType fromNode, NodeType toNode);
	
	public void removeConnectionBothWays(NodeType fromNode, NodeType toNode);
	
	
}
