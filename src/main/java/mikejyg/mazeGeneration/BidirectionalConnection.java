package mikejyg.mazeGeneration;

/**
 * connections of an undirected graph.
 * 
 * @author mikejyg
 *
 * @param <IdType>
 * @param <NodeType>
 */
public class BidirectionalConnection< IdType, NodeType extends NodeIntf<IdType> > implements ConnectionIntf<NodeType> {

	/**
	 *  a pair of nodes. 
	 *  
	 *  node1 is forced to be less then node2, to make the equal test of 2 connections easier. 
	 */
	private Pair<NodeType> nodePair;

	//////////////////////////////////////////////
	
	private void setNodes(NodeType node1, NodeType node2) {
		var c = node1.compareTo(node2);
		
		assert(c!=0);
//		if (c==0)
//			throw new Error("setNodes() nodes are equal.");
		
		if ( c > 0 ) {
			// swap node1 and node2
			nodePair.v1 = node2;
			nodePair.v2 = node1;
			
		} else {
			nodePair.v1 = node1;
			nodePair.v2 = node2;
		}
	}

	private Pair<NodeType> getNodePair() {
		return nodePair;
	}

	//////////////////////////////////////////////
	
	public BidirectionalConnection(NodeType node1, NodeType node2) {
		nodePair = new Pair<>();
		setNodes(node1, node2);
	}
	
	@Override
	public NodeType getTheOtherNode(NodeType node1) {
		if ( nodePair.v1.equals(node1) ) {
			return nodePair.v2;
			
		} else {
			assert(node1.equals(nodePair.v2));
			return nodePair.v1;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		var c2 = (BidirectionalConnection<IdType, NodeType>)obj;
		
		return nodePair.equals(c2.nodePair);
	}
	
	@Override
	public NodeType getFromNode() {
		return getNodePair().v1;
	}

	@Override
	public NodeType getToNode() {
		return getNodePair().v2;
	}
	
	//////////////////////////////////////////////
	
	@Override
	public String toString() {
		return "{ " + nodePair.v1.toString() + " - " + nodePair.v2.toString() + " }";
	}


}
