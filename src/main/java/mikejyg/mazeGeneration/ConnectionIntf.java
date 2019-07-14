package mikejyg.mazeGeneration;

public interface ConnectionIntf<NodeType> {

	public NodeType getFromNode();

	public NodeType getToNode();

	public NodeType getTheOtherNode(NodeType node1);
	
}
