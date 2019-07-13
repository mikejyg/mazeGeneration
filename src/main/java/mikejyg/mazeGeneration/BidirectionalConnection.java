package mikejyg.mazeGeneration;

public class BidirectionalConnection< IdType, NodeType extends NodeIntf<IdType> > {

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

	//////////////////////////////////////////////
	
	public BidirectionalConnection(NodeType node1, NodeType node2) {
		nodePair = new Pair<>();
		setNodes(node1, node2);
	}
	
	public Pair<NodeType> getNodePair() {
		return nodePair;
	}

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
	
	//////////////////////////////////////////////
	
	@Override
	public String toString() {
		return "{ " + nodePair.v1.toString() + " - " + nodePair.v2.toString() + " }";
	}
	

}
