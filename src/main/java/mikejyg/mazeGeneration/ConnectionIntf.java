package mikejyg.mazeGeneration;

/**
 * a common interface for both directed and undirected connections.
 * @author mikejyg
 *
 * @param <NodeType>
 */
public interface ConnectionIntf<NodeType> {

	public NodeType getFromNode();

	public NodeType getToNode();

	public NodeType getTheOtherNode(NodeType node1);
	
	// this is here, so that the usage of it can be found by Eclipse.
	@Override
	boolean equals(Object obj);
	
}
