package main.java.components.graph.algorithms.index;

import main.java.Helper;
import main.java.components.graph.algorithms.IdxInterface;

public class IndexGRG implements IdxInterface {
	
	int _l;
	Helper h = new Helper();
	
	public IndexGRG (int l){
		_l = l;
	}
	
	
	public int getIndex(int i, int g) {
		IndexBRG brg = new IndexBRG();
		return (brg.getIndex(i, g))^
				(brg.getIndex((1 << g) - 1 - i, g)
				>>(int)Math.ceil((float)g/(float)_l));
	}

	public long getIndex(long i, int g) {
		IndexBRG brg = new IndexBRG();
		return (brg.getIndex(i, g))^
				(brg.getIndex((1 << g) - 1 - i, g)
				>>(int)Math.ceil((float)g/(float)_l));
	}

	public int getIndex(int i, int j, int g) {
		return -1;
	}

	public long getIndex(long i, long j, int g) {
		return -1;
	}

}
