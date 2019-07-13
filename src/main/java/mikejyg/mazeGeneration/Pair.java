package mikejyg.mazeGeneration;

public class Pair<T> {
	public T v1, v2;
	
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		var p2 = (Pair<T>)obj;
		return v1.equals(p2.v1) && v2.equals(p2.v2);
	}
	
}
