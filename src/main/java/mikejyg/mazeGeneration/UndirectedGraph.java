package mikejyg.mazeGeneration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * an undirected graph.
 * 
 * @author mikejyg
 *
 * @param <IdType>
 * @param <NodeType>
 */
public class UndirectedGraph<IdType, NodeType extends NodeIntf<IdType> > {
	// the node set
	private Map<IdType, NodeType> nodeSet = new TreeMap<>();
		
	// Node ID -> connection index
	private Map<IdType, Collection< BidirectionalConnection<IdType, NodeType> > > nodeToConnectionsMap = new TreeMap<>();
	
	/////////////////////////////////////////////////
	

	/////////////////////////////////////////////////

	public Collection<NodeType> getNodeSet() {
		return nodeSet.values();
	}
	
	public void addNode(NodeType node) {
		nodeSet.put(node.getId(), node);
	}
	
	/**
	 * get all the connections from a node.
	 * @param a collection of connections. It can be empty, but not null.
	 */
	public Collection< BidirectionalConnection<IdType, NodeType> > getConnectionsOf(NodeType node) {
		var conns = nodeToConnectionsMap.get(node.getId());
		if (conns==null) {
			if ( ! nodeSet.containsKey(node.getId()) ) {
				throw new Error("getConnectionsFrom() node does not exist: " + node.toString());
			}
			
			conns = new ArrayList<>();
			nodeToConnectionsMap.put(node.getId(), conns);
		}
		return conns;
	}
	
	public void addConnection(BidirectionalConnection<IdType, NodeType> newConn) {
		var conns = getConnectionsOf(newConn.getNodePair().v1);
		
		// check for duplication
		for (var conn : conns) {
			if ( conn.equals(newConn) )
				throw new Error("addConnection() connection already exists: " + conn);
		}
		
		conns.add(newConn);
		
		getConnectionsOf(newConn.getNodePair().v2).add(newConn);
	}
	
	public void addConnection(NodeType node1, NodeType node2) {
		addConnection(new BidirectionalConnection<>(node1, node2));
	}
	
	public void removeConnection(BidirectionalConnection<IdType, NodeType> conn) {
		var conns = getConnectionsOf(conn.getNodePair().v1);
		
		for (var c : conns) {
			if ( c.equals(conn) ) {
				conns.remove(c);
				break;
			}
		}

		conns = getConnectionsOf(conn.getNodePair().v2);
		
		for (var c : conns) {
			if ( c.equals(conn) ) {
				conns.remove(c);
				break;
			}
		}
		
	}
	
	public void removeConnection(NodeType node1, NodeType node2) {
		removeConnection(new BidirectionalConnection<>(node1, node2));
	}
	
	public boolean isConnected(NodeType node1, NodeType node2) {
		var conn = new BidirectionalConnection<IdType, NodeType>(node1, node2);
		
		var conns = getConnectionsOf(node1);
		
		// TODO right now it is a linear search, may need to use a more scalable way.
		
		for (var c : conns) {
			if ( c.equals(conn) )
				return true;
		}
		
		return false;
	}
	
	public int size() {
		return nodeSet.size();
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
