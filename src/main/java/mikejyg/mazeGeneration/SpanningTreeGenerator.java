package mikejyg.mazeGeneration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * turns a graph into a spanning tree
 * 
 * assuming the graph only contains bi-directional connections, 
 *   that is, for each connection, there exists a reverse connection.
 *    
 * @author jgu
 *
 */
public class SpanningTreeGenerator< IdType, NodeType extends NodeIntf<IdType> > {
	static final Logger logger = LoggerFactory.getLogger(SpanningTreeGenerator.class);
	
	/**
	 * the connections of the generated spanning tree.
	 * NOTE: only one one-way connection is recorded, for every bi-directional connection of the tree.
	 */
	private Collection< Connection<NodeType> > treeConnections;
	
	// working variables
	
	private Graph<IdType, NodeType> graph;
	
	private Set<IdType> visitedNodeSet;
	
	private Random random;
	
	////////////////////////////////////////////////
	
	/**
	 * generate a list of available connections for the next move
	 * @return
	 */
	private List< Connection<NodeType> >  getValidConnections(NodeType startingNode) {
		var availConns = new ArrayList<Connection<NodeType>>();
		
		var conns = graph.getConnectionsFrom(startingNode);
		var it = conns.iterator();
		while (it.hasNext()) {
			var conn = it.next();
			
			if ( visitedNodeSet.contains( conn.getToNode().getId() ) ) {
				continue;
			}
			
			availConns.add(conn);
		}
		
		return availConns;
	}
	
	////////////////////////////////////////////////
	
	private void genSpanningTree(NodeType startingNode) {
		logger.debug("genSpanningTree() " + startingNode);
		
		visitedNodeSet.add(startingNode.getId());

		while (true) {
			var availConns = getValidConnections(startingNode);
			if (availConns.isEmpty())
				break;
			
			var connIdx = random.nextInt(availConns.size());
				
			var conn = availConns.get(connIdx);
			
			// take the move
			
			treeConnections.add( conn );
			
			genSpanningTree(conn.getToNode());
			
			// early termination criteria
			if ( visitedNodeSet.size() == graph.size() )
				break;
			
		}		
		
	}

	////////////////////////////////////////////////

	public void genSpanningTree(Graph<IdType, NodeType> graph, NodeType startingNode) {
		this.graph = graph;
		
		visitedNodeSet = new TreeSet<>();
		treeConnections = new ArrayList<>();
		
		if (random==null)
			random = new Random();
		
		genSpanningTree(startingNode);

	}
	
	public Collection< Connection<NodeType> > getTreeConnections() {
		return treeConnections;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	
}
