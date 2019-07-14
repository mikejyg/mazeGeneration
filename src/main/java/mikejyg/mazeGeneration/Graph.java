package mikejyg.mazeGeneration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * A graph contains a set of nodes, and a set of connections.
 * A connection is defined as a path from one node to another.
 * @author jgu
 *
 */
public class Graph<IdType, NodeType extends NodeIntf<IdType> > extends GraphNodes<IdType, NodeType>
	implements GraphIntf< IdType, NodeType > {
	
	// fromNode -> connection index
	private Map<IdType, Collection< Connection<NodeType> > > nodeToConnectionsMap = new TreeMap<>();
	
	/////////////////////////////////////////////////

	public Graph() {}
	
	/**
	 * get all the connections from a node.
	 * @param a collection of connections. It can be empty, but not null.
	 */
	@Override
	public Collection< Connection<NodeType> > getConnectionsFrom(NodeType fromNode) {
		var conns = nodeToConnectionsMap.get(fromNode.getId());
		if (conns==null) {
			if ( ! nodeSet.containsKey(fromNode.getId()) ) {
				throw new Error("getConnectionsFrom() node does not exist: " + fromNode.toString());
			}
			
			conns = new ArrayList<>();
			nodeToConnectionsMap.put(fromNode.getId(), conns);
		}
		return conns;
	}
	
	@Override
	public void addConnectionOneWay(NodeType fromNode, NodeType toNode) {
		var conns = getConnectionsFrom(fromNode);
		
		// check for duplication
		for (var conn : conns) {
			if ( conn.getToNode().equals(toNode) )
				throw new Error("addConnection() connection already exists: " + conn);
		}
		
		var newConn = new Connection<>(fromNode, toNode);
		conns.add(newConn);
	}
	
	@Override
	public void addConnectionBothWays(NodeType node1, NodeType node2) {
		addConnectionOneWay(node1, node2);
		addConnectionOneWay(node2, node1);
	}
	
	@Override
	public void removeConnectionOneWay(NodeType fromNode, NodeType toNode) {
		var conns = getConnectionsFrom(fromNode);
		
		for (var conn : conns) {
			if ( conn.getToNode().equals(toNode) ) {
				conns.remove(conn);
				break;
			}
		}
	}
	
	/**
	 * remove the 2 on-way connections between node1 and node2
	 * @param fromNode
	 * @param toNode
	 */
	@Override
	public void removeConnectionBothWays(NodeType node1, NodeType node2) {
		removeConnectionOneWay(node1, node2);
		removeConnectionOneWay(node2, node1);
	}
	
	@Override
	public boolean isConnection(NodeType fromNode, NodeType toNode) {
		var conns = getConnectionsFrom(fromNode);
		
		for (var conn : conns) {
			if (conn.getToNode().equals(toNode))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String str="";
		for (var nodeId : nodeToConnectionsMap.keySet()) {
			str += "node: " + nodeId + "[ ";

			for (var conn : nodeToConnectionsMap.get(nodeId)) {
				str += conn.toString() + " ";
			}
			
			str += " ]" + System.lineSeparator();
			
		}
		return str;
		
	}
	
	
}
