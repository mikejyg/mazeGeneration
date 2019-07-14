package mikejyg.mazeGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mikejyg.tree.TreeNode;
import mikejyg.tree.TreeTraversal;

/**
 * turns a graph into a spanning tree
 * 
 * @author jgu
 *
 * TODO: use a more sophisticated graph traverse algorithm, than DFS.    
 * 
 */
public class SpanningTreeGenerator< IdType, NodeType extends NodeIntf<IdType> > {
	static final Logger logger = LoggerFactory.getLogger(SpanningTreeGenerator.class);
	
	private TreeNode<NodeType> treeRoot;
	
	// working variables
	
//	private UndirectedGraph<IdType, NodeType> graph;
	
	private GraphIntf<IdType, NodeType> graph;
	
	private Set<IdType> visitedNodeSet;
	
	private Random random;
	
	/**
	 * Remove connections from the graph while traversing the graph.
	 * This is faster, than calling removeTreeConnectionsFromGraph() later. 
	 * However, it changes the graph during tree generation.
	 */
	private boolean removeConnectionsOnTheGo;
	
	////////////////////////////////////////////////
	
	/**
	 * generate a list of available connections for the next move
	 * @return
	 */
	private List< ConnectionIntf<NodeType> >  getValidConnections(NodeType startingNode) {
		var availConns = new ArrayList< ConnectionIntf<NodeType> >();
		
		var conns = graph.getConnectionsFrom(startingNode);
		var it = conns.iterator();
		while (it.hasNext()) {
			var conn = it.next();
			
			if ( visitedNodeSet.contains( conn.getTheOtherNode(startingNode).getId() ) ) {
				continue;
			}
			
			availConns.add(conn);
		}
		
		return availConns;
	}
	
	////////////////////////////////////////////////

	public void genSpanningTree(GraphIntf< IdType, NodeType > graph, NodeType startingNode) {
		this.graph = graph;
		
		if (random==null)
			random = new Random();
		
		visitedNodeSet = new TreeSet<>();
		treeRoot = new TreeNode<>(startingNode);
		
		var treeNode = treeRoot;
		
		while ( treeNode != null ) {
			var nodeData = treeNode.getNodeData();
			visitedNodeSet.add(nodeData.getId());
			
			var availConns = getValidConnections(nodeData);
			if (availConns.isEmpty()) {
				// backtrack
				treeNode = treeNode.getParent();
				continue;
			}
			
			var connIdx = random.nextInt(availConns.size());
				
			var conn = availConns.get(connIdx);
			
			// take the move
			
			var newTreeNode = new TreeNode<>(conn.getTheOtherNode(nodeData), treeNode);

			treeNode.addChild(newTreeNode);
			
			if (removeConnectionsOnTheGo)
				graph.removeConnectionBothWays(conn.getFromNode(), conn.getToNode());
			
			treeNode = newTreeNode;
			
			// early termination criteria
			if ( visitedNodeSet.size() == graph.size() )
				break;
		}
		
	}
	
	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * remove all connections (walls) of the graph, and the resulting graph will be the maze
	 */
	public void removeTreeConnectionsFromGraph() {
		var treeTraversal = new TreeTraversal();
		
		treeTraversal.traverse(treeRoot, node->{
			if (node.getParent()!=null) {
				@SuppressWarnings("unchecked")
				var treeNode = (TreeNode<NodeType>)node;
				graph.removeConnectionBothWays( treeNode.getParent().getNodeData(), treeNode.getNodeData());
			}
		});
	}
	
	public void setRemoveConnectionsOnTheGo(boolean removeConnectionsOnTheGo) {
		this.removeConnectionsOnTheGo = removeConnectionsOnTheGo;
	}

	
}
