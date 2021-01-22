package main.java.components.graph.algorithms.index;

import main.java.components.graph.algorithms.IdxInterface;

public class IndexSBRG implements IdxInterface {
	
	int _c;
	public IndexSBRG(int c) {
		_c = c;
	}

	public int getIndex(int i, int g) {
		IndexBRG brg = new IndexBRG();
		return ((brg.getIndex(i, g)+_c)&((1<<g)-1));
	}

	public long getIndex(long i, int g) {
		IndexBRG brg = new IndexBRG();
		return ((brg.getIndex(i, g)+_c)&((1<<g)-1));
	}

	public int getIndex(int i, int j, int g) {
		return 0;
	}

	public long getIndex(long i, long j, int g) {
		return 0;
	}
	
	
}
