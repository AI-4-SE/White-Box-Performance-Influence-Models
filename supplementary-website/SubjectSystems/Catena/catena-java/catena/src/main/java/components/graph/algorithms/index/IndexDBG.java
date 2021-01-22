package main.java.components.graph.algorithms.index;

import main.java.Helper;
import main.java.components.graph.algorithms.IdxInterface;

public class IndexDBG implements IdxInterface{
	
	static Helper helper = new Helper();

	public int getIndex(int g, int j, int i) {
		return (int)getIndex((long)g, (long)j, i);
	}

	public long getIndex(long g, long j, int i) {
		if (j <= g-1){
			long tmp =  (long)1 << (g-1-j);
			return tmp ^ (long)i;
		} else {
			long tmp = (long)1 << (j-g+1);
			return tmp ^ (long)i;
		}
	}

	@Override
	public int getIndex(int i, int g) {
		return -1;
	}

	@Override
	public long getIndex(long i, int g) {
		return -1;
	}
}
