package mikejyg.mazeGeneration;

/**
 * Comparable is need to enforce the order of a node pair.
 * 
 * @author mikejyg
 *
 * @param <IdType>
 */
public interface NodeIntf<IdType> extends Comparable< NodeIntf<IdType> > {
	
	// ID is need for the data structures, such as compare, indexing...
	public IdType getId();
	
}
