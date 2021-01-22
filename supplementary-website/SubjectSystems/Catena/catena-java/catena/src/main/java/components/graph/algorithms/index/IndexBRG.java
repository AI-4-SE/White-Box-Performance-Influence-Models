package main.java.components.graph.algorithms.index;

import main.java.components.graph.algorithms.IdxInterface;

public class IndexBRG implements IdxInterface {

	@Override
	public int getIndex(int x, int g) {
		boolean[] bool1 = new boolean[g];
		boolean[] bool2 = new boolean[g];
		
		String tmp = Integer.toBinaryString(x);
		for (int i = 0; i < tmp.length(); ++i ) {
			if(tmp.charAt(i)=='1'){
				bool1[i+g-tmp.length()] = true;
			}
		}
		int b1size = bool1.length;
		for (int i = 0; i < b1size; ++i){
			boolean b = bool1[i];
			if(b){
				bool2[g-1-i] = true;
			}
		}
		
		int n = 0;
		int l = bool2.length;
		for (int i = 0; i < l; ++i) {
		    n = (n << 1) + (bool2[i] ? 1 : 0);
		}
		return n;
	}
	
	@Override
	public long getIndex(long x, int g) {
		boolean[] bool1 = new boolean[g];
		boolean[] bool2 = new boolean[g];
		
		String tmp = Long.toBinaryString(x);
		for (int i = 0; i < tmp.length(); ++i ) {
			if(tmp.charAt(i)=='1'){
				bool1[i+g-tmp.length()] = true;
			}
		}
		int b1size = bool1.length;
		for (int i = 0; i < b1size; ++i){
			boolean b = bool1[i];
			if(b){
				bool2[g-1-i] = true;
			}
		}
		
		long n = 0;
		long l = bool2.length;
		for (int i = 0; i < l; ++i) {
		    n = (n << 1) + (bool2[i] ? 1 : 0);
		}
		return n;
	}

	@Override
	public int getIndex(int i, int j, int g) {
		return -1;
	}

	@Override
	public long getIndex(long i, long j, int g) {
		return -1;
	}
}