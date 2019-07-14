package mikejyg.mazeGeneration;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

/**
 * test the SpanningTreeGenerator
 * @author mikejyg
 *
 */
public class MazeTest {

	Maze maze;
	
	boolean drawFlag;
	
	///////////////////////////////////////////////////
	
	/**
	 * parse arguments, construct and customize a maze object.
	 * 
	 * @param args
	 */
	public void parseArgs(String[] args) {
		int argIdx=0;
		
		if (args.length < 2) {
			System.out.println("usage: [-d] cols rows [random_seed]");
			System.out.println("\t-d draw the generated maze and wait for user to press ENTER.");
			System.exit(1);
		}
		
		if (args[0].contentEquals("-d")) {
			argIdx++;
			drawFlag = true;
		}
		
		maze=new Maze();

		maze.setCols( Integer.parseInt(args[argIdx++]) );
		maze.setRows( Integer.parseInt(args[argIdx++]) );
		
		if (args.length>argIdx)
			maze.setRandom(new Random(Integer.parseInt(args[argIdx++])) );
		else
			maze.setRandom(new Random());
		
	}
	
	public void generateMaze() throws IOException {
		maze.buildGraph();
		
		maze.toMaze();
		
		maze.printMaze(System.out);
		
		if (drawFlag) {
			maze.drawMaze();

			// pause, until enter is pressed
			System.out.println("press ENTER, or close the draw Window to exit.");
			System.in.read();
		}
		
	}
	
	/**
	 * a unit test
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		var goldenStr = 
				"+-----+" + System.lineSeparator() + 
				"|o|o o|" + System.lineSeparator() + 
				"+ +-+ +" + System.lineSeparator() + 
				"|o o o|" + System.lineSeparator() + 
				"+ +-+-+" + System.lineSeparator() + 
				"|o o o|" + System.lineSeparator() + 
				"+-----+" + System.lineSeparator();
				
		maze=new Maze();
		maze.setRandom(new Random(1));
		
		maze.buildGraph();
		maze.toMaze();
		
		var mazeStr = maze.toString();
		System.out.println(mazeStr);			
		assert( mazeStr.contentEquals(goldenStr) );

		// do another test with removeConnectionsOnTheGo enabled
		maze=new Maze();
		maze.setRandom(new Random(1));
		maze.setRemoveConnectionsOnTheGo(true);
		
		maze.buildGraph();
		maze.toMaze();
		
		mazeStr = maze.toString();
		System.out.println(mazeStr);			
		assert( mazeStr.contentEquals(goldenStr) );

		System.out.println("test() successful.");

	}

	//////////////////////////////////////////////////////////
	
	public static void main(String[] args) throws InterruptedException, IOException {
		MazeTest tester = new MazeTest();
		
		tester.parseArgs(args);
		
		tester.generateMaze();
		
		System.exit(0);
		
	}
	
	
}


