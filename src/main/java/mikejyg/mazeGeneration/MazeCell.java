package mikejyg.mazeGeneration;

/**
 * a maze cell with position information.
 * 
 * @author mikejyg
 *
 */
public class MazeCell implements NodeIntf<Integer> {
	// the coordinate of the cell
	public int x, y;
	
	public int id;
	
	public MazeCell(int x, int y, int id) {
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

	@Override
	public int compareTo(NodeIntf<Integer> o) {
		if ( id == o.getId() )
			return 0;
		
		if ( id < o.getId() )
			return -1;
		else
			return 1;
	}

	
}
