package mikejyg.mazeGeneration;

/**
 * a one-way connection from node0 to node1
 * 
 * @author jgu
 *
 * @param <NodeType>
 */
public class Connection<NodeType> implements ConnectionIntf<NodeType> {
	private NodeType fromNode, toNode;

	//////////////////////////////////////////////
	
	public Connection(NodeType fromNode, NodeType toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
	
	public boolean equals(NodeType fromNode, NodeType toNode) {
		return this.fromNode.equals(fromNode) && this.toNode.equals(toNode);
	}

	@Override
	public String toString() {
		return "{ " + fromNode.toString() + " - " + toNode.toString() + " }";
	}
	
	@Override
	public NodeType getTheOtherNode(NodeType node1) {
		if (fromNode.equals(node1)) {
			return toNode;
		} else {
			assert(toNode.equals(node1));
			return fromNode;
		}
	}
	
	//////////////////////////////////////////////
	
	@Override
	public NodeType getFromNode() {
		return fromNode;
	}

//	public void setFromNode(NodeType fromNode) {
//		this.fromNode = fromNode;
//	}

	@Override
	public NodeType getToNode() {
		return toNode;
	}

//	public void setToNode(NodeType toNode) {
//		this.toNode = toNode;
//	}

	
}
