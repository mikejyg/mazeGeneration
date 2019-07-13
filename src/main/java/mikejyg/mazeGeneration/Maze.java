package mikejyg.mazeGeneration;

import java.io.PrintStream;
import java.util.Random;

/**
 * a rectangular maze
 * @author mikejyg
 *
 */
public class Maze {
	
	static public class Node implements NodeIntf<Integer> {
		// the coordinate of the cell
		public int x, y;
		
		public int id;
		
		public Node(int x, int y, int id) {
			this.x=x;
			this.y=y;
			this.id=id;
		}
		
		@Override
		public Integer getId() {
			return id;
		}
		
		@Override
		public String toString() {
			return "{ " 
					+ Integer.toString(id) + ": "
					+ Integer.toString(x) + ", " + Integer.toString(y) + 
					" }";
		}
	}

	///////////////////////////////////////////////
	
	private int cols = 3;

	private int rows = 3;

	private Random random;

	// working variables
	
	private Graph<Integer, Node> graph;
	
	private Node[][] nodes;
	
	///////////////////////////////////////////////
	
	/**
	 * construct a graph for the initial structure, with all interior (non-boundary) walls present.
	 * the nodes represent cells, and the connections represent walls.
	 * 
	 * the lower corner cell of the maze is at (1,1), and the distance between two adjacent cells is 1. 
	 */
	public void buildGraph() {
		// construct a graph for the initial maze, with all interior (non-boundary) walls present.
		// the nodes represent cells, and the connections represent walls.
		
		graph = new Graph<Integer, Node>();
		
		// create the nodes
		
		nodes = new Node[rows][cols];
		
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				var node = new Node( row+1, col+1, row * cols + col );
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
	}
	
	/**
	 * turn the graph into a maze
	 */
	public void toMaze() {
		// generate a spanning tree within the graph
		
		var stg = new SpanningTreeGenerator<Integer, Node>();
		stg.setRandom(random);
		
		var rowIdx = random.nextInt(rows);
		var colIdx = random.nextInt(cols);

		System.out.println("starting from: " + nodes[rowIdx][colIdx]);
		
		stg.genSpanningTree(graph, nodes[rowIdx][colIdx]);
		
		// remove all connections (walls) of the graph, and the resulting graph will be the maze
		
		for ( var conn : stg.getTreeConnections() ) {
			graph.removeBiDirConnection(conn.getFromNode(), conn.getToNode());
		}
		
//		System.out.println(graph.toString());
		
	}
	
	public void printMaze(PrintStream printStream) {
		for (int row=0; row<rows; row++) {
			if (row==0) {
				printStream.print("+");
				for (int col=0; col<cols; col++) {
					printStream.print("-");
					
					if (col==cols-1)
						printStream.print("+");
					else
						printStream.print("-");
				}
				printStream.println();
			}
			
			printStream.print("|");
			for (int col=0; col<cols; col++) {
				printStream.print("o");
				if (col<cols-1) {
					if ( graph.isConnection(nodes[row][col], nodes[row][col+1]) ) {
						printStream.print("|");
					} else {
						printStream.print(" ");
					}
				}
			}
			printStream.println("|");
			
			printStream.print("+");
			for (int col=0; col<cols; col++) {
				if (row < rows-1) {
					if ( graph.isConnection(nodes[row][col], nodes[row+1][col]) ) {
						printStream.print("-+");
					} else {
						printStream.print(" +");
					}
				} else {
					printStream.print("-");
					if (col==cols-1)
						printStream.print("+");
					else
						printStream.print("-");
				}
			}
			printStream.println();
			
		}
	}
	
	public void setRandom(Random random) {
		this.random = random;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
