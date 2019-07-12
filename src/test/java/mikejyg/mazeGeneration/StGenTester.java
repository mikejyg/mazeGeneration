package mikejyg.mazeGeneration;

import java.util.Random;

/**
 * test the SpanningTreeGenerator
 * @author mikejyg
 *
 */
public class StGenTester {
	
	static public class Node implements NodeIntf<String> {
		public String id;
		
		public Node(String id) {
			this.id=id;
		}
		
		@Override
		public String getId() {
			return id;
		}
		
		@Override
		public String toString() {
			return id;
		}
	}

	///////////////////////////////////////////////
	
	private int cols = 3;
	private int rows = 3;

	private Random random;

	// working variables
	
	private Graph<String, Node> graph;
	
	private Node[][] nodes;
	
	///////////////////////////////////////////////
	
	public void test() {
		// construct a rectangular graph
		
		graph = new Graph<String, Node>();
		
		// create the nodes
		
		nodes = new Node[rows][cols];
		
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				var node = new Node( Integer.toString(row+1) + ", " + Integer.toString(col+1) );
				nodes[row][col] = node;
				graph.addNode(node);
			}
		}
		
		// make the connections
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				// right?
				if (col<cols-1) {
					graph.addBiDirConnection(nodes[row][col], nodes[row][col+1]);
				}
				
				// down?
				if (row<rows-1) {
					graph.addBiDirConnection(nodes[row][col], nodes[row+1][col]);
				}
				
			}
		}
		
//		System.out.println(graph.toString());
		
		//
		
		var stg = new SpanningTreeGenerator<String, Node>();
		stg.setRandom(random);
		
		var rowIdx = random.nextInt(rows);
		var colIdx = random.nextInt(cols);

		System.out.println("starting from: " + nodes[rowIdx][colIdx]);
		
		stg.genSpanningTree(graph, nodes[rowIdx][colIdx]);
		
		for ( var conn : stg.getTreeConnections() ) {
			graph.removeBiDirConnection(conn.getFromNode(), conn.getToNode());
		}
		
//		System.out.println(graph.toString());
		
		printMaze();
		
	}
	
	public void printMaze() {
		for (int row=0; row<rows; row++) {
			if (row==0) {
				System.out.print("+");
				for (int col=0; col<cols; col++) {
					System.out.print("-");
					
					if (col==cols-1)
						System.out.print("+");
					else
						System.out.print("-");
				}
				System.out.println();
			}
			
			System.out.print("|");
			for (int col=0; col<cols; col++) {
				System.out.print("o");
				if (col<cols-1) {
					if ( graph.isConnection(nodes[row][col], nodes[row][col+1]) ) {
						System.out.print("|");
					} else {
						System.out.print(" ");
					}
				}
			}
			System.out.println("|");
			
			System.out.print("+");
			for (int col=0; col<cols; col++) {
				if (row < rows-1) {
					if ( graph.isConnection(nodes[row][col], nodes[row+1][col]) ) {
						System.out.print("-+");
					} else {
						System.out.print(" +");
					}
				} else {
					System.out.print("-");
					if (col==cols-1)
						System.out.print("+");
					else
						System.out.print("-");
				}
			}
			System.out.println();
			
		}
	}
	
	public void parseArgs(String[] args) {
		int argIdx=0;
		
		if (args.length < 2) {
			System.out.println("usage: rows cols [random_seed]");
			System.exit(1);
		}
		
		rows = Integer.parseInt(args[argIdx++]);
		cols = Integer.parseInt(args[argIdx++]);
		
		if (args.length>argIdx)
			random = new Random(Integer.parseInt(args[argIdx++]));
		else
			random = new Random();
		
	}
	
	public static void main(String[] args) {
		StGenTester tester = new StGenTester();
		
		tester.parseArgs(args);
		
		tester.test();
	}
	

}

