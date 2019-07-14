package mikejyg.mazeGeneration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;

/**
 * a rectangular maze
 * @author mikejyg
 *
 */
public class Maze {

	private int cols = 3;

	private int rows = 3;

	private Random random;

	// TODO: this option is not really needed. It is used to test the TreeTraversal for the moment.
	private boolean removeConnectionsOnTheGo;
	
	// working variables
	
	private UndirectedGraph<Integer, MazeCell> graph;
	
	private MazeCell[][] nodes;

	private double drawScale = 50;

	private int maxCanvasX = 1024;
	
	private int maxCanvasY = 768;
	
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
		
		graph = new UndirectedGraph<Integer, MazeCell>();
		
		// create the nodes
		
		nodes = new MazeCell[rows][cols];
		
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				var node = new MazeCell( col+1, row+1, row * cols + col );
				nodes[row][col] = node;
				graph.addNode(node);
			}
		}
		
		// make the connections
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				// right?
				if (col<cols-1) {
					graph.addConnection(nodes[row][col], nodes[row][col+1]);
				}
				
				// down?
				if (row<rows-1) {
					graph.addConnection(nodes[row][col], nodes[row+1][col]);
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
		
		var stg = new SpanningTreeGenerator<Integer, MazeCell>();
		stg.setRandom(random);
		
		var rowIdx = random.nextInt(rows);
		var colIdx = random.nextInt(cols);

		System.out.println("starting from: " + nodes[rowIdx][colIdx]);
		
		if (removeConnectionsOnTheGo)
			stg.setRemoveConnectionsOnTheGo(true);
		
		stg.genSpanningTree(graph, nodes[rowIdx][colIdx]);
		
		if (!removeConnectionsOnTheGo)
			stg.removeTreeConnectionsFromGraph();
	
//		System.out.println(graph.toString());
		
	}
	
	public void drawMaze() {
		StdDraw.enableDoubleBuffering();
		
		// reduce draw scale to fit in max canvas size
		if ( cols*drawScale > maxCanvasX)
			drawScale = (double) maxCanvasX / cols;
		
		if ( rows*drawScale > maxCanvasY )
			drawScale = (double) maxCanvasY / rows;
		
		StdDraw.setCanvasSize( (int)Math.ceil(cols * drawScale)
				, (int)Math.ceil(rows * drawScale) );
		
		StdDraw.setXscale(1./2, cols + 1./2);
        StdDraw.setYscale(1./2, rows + 1./2);
        
		StdDraw.setPenColor(StdDraw.BLACK);
		
		for (var node : graph.getNodeSet()) {
			var conns = graph.getConnectionsOf(node);
			
			for (var conn: conns) {
				var node2 = conn.getTheOtherNode(node);
				
				// calculate the line segment of the wall between the 2 nodes
				
				// since all lines in a rectangular maze are either horizontal or vertical,
				// so the end points of a cross line are the original end points with each of their x and y swapped.
				
				var centerX = (node.x + node2.x)/2.;
				var centerY = (node.y + node2.y)/2.;
				
				// move the line segment to the center
				
				var nodeXRel = node.x - centerX;
				var nodeYRel = node.y - centerY;

				var node2XRel = node2.x - centerX;
				var node2YRel = node2.y - centerY;

				// swap x, y, and move back to the original center
				
				StdDraw.line( centerX + nodeYRel, centerY + nodeXRel
						, centerX + node2YRel, centerY + node2XRel );
				
			}	
		}
		
		StdDraw.show();
		
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
					if ( graph.isConnected(nodes[row][col], nodes[row][col+1]) ) {
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
					if ( graph.isConnected(nodes[row][col], nodes[row+1][col]) ) {
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

	public void setRemoveConnectionsOnTheGo(boolean removeConnectionsOnTheGo) {
		this.removeConnectionsOnTheGo = removeConnectionsOnTheGo;
	}

	/////////////////////////////////////////////////////////////////
	
	@Override
	public String toString() {
		try (var baos = new ByteArrayOutputStream();
				var ps = new PrintStream(baos, true, StandardCharsets.UTF_8); ) {
			
			printMaze(ps);

			return new String(baos.toByteArray(), StandardCharsets.UTF_8);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}
	

}
