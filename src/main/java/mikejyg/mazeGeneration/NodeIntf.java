package mikejyg.mazeGeneration;

public interface NodeIntf<IdType> {
	
	// ID is need for the data structures, such as compare, indexing...
	public IdType getId();
	
}
