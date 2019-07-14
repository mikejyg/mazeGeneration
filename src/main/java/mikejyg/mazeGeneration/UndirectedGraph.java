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
public class UndirectedGraph<IdType, NodeType extends NodeIntf<IdType> > extends GraphNodes<IdType, NodeType>
	implements GraphIntf< IdType, NodeType > {
	
	// Node ID -> connection index
	private Map<IdType, Collection< BidirectionalConnection<IdType, NodeType> > > nodeToConnectionsMap = new TreeMap<>();
	
	/////////////////////////////////////////////////

	public UndirectedGraph() {}
	
	/**
	 * get all the connections from a node.
	 * @param a collection of connections. It can be empty, but not null.
	 */
	@Override
	public Collection< BidirectionalConnection<IdType, NodeType> > getConnectionsFrom(NodeType node) {
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
	
	@Override
	public void addConnectionBothWays(NodeType node1, NodeType node2) {
		var newConn = new BidirectionalConnection<>(node1, node2);
		
		var conns = getConnectionsFrom(newConn.getFromNode());
		
		// check for duplication
		for (var conn : conns) {
			if ( conn.equals(newConn) )
				throw new Error("addConnection() connection already exists: " + conn);
		}
		
		conns.add(newConn);
		
		getConnectionsFrom(newConn.getToNode()).add(newConn);
	}
	
	@Override
	public void removeConnectionBothWays(NodeType node1, NodeType node2) {
		var conn = new BidirectionalConnection<>(node1, node2);
		var conns = getConnectionsFrom(conn.getFromNode());
		
		for (var c : conns) {
			if ( c.equals(conn) ) {
				conns.remove(c);
				break;
			}
		}

		conns = getConnectionsFrom(conn.getToNode());
		
		for (var c : conns) {
			if ( c.equals(conn) ) {
				conns.remove(c);
				break;
			}
		}
		
	}
	
	@Override
	public boolean isConnection(NodeType node1, NodeType node2) {
		var conn = new BidirectionalConnection<IdType, NodeType>(node1, node2);
		
		var conns = getConnectionsFrom(node1);
		
		for (var c : conns) {
			if ( c.equals(conn) )
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

	@Override
	public void addConnectionOneWay(NodeType fromNode, NodeType toNode) {
		throw new Error("addConnectionOneWay() not applicable.");
		
	}

	@Override
	public void removeConnectionOneWay(NodeType fromNode, NodeType toNode) {
		throw new Error("addConnectionOneWay() not applicable.");
	}
	

}
