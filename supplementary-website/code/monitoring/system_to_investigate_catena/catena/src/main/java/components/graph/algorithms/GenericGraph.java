package main.java.components.graph.algorithms;

import main.java.Helper;
import main.java.components.graph.GraphInterface;
import main.java.components.hash.HashInterface;

public class GenericGraph implements GraphInterface {

	HashInterface _h;
	HashInterface _hPrime;
	Helper helper = new Helper();
	IdxInterface indexing;
	
	public byte[][] graph (int g, byte[][] v, int lambda){
		
		int dim1 = (int)Math.pow(2, g);
		int dim2 = _hPrime.getOutputSize();
		byte[][] r = new byte[dim1][dim2];
		
		for (int k = 0; k < lambda; ++k){
			r[0] = hFirst(helper.concateByteArrays(v[dim1-1],
					v[indexing.getIndex(0, g)]));
			
			int loop = (int)Math.pow(2,g);
			
			for (int i = 1; i < loop; ++i){
				_hPrime.update(helper.concateByteArrays(r[i-1],v[indexing.getIndex(i, g)]));
				r[i] = _hPrime.doFinal();
			}
			
			System.arraycopy(r, 0, v, 0, r.length);
		}
		return v;
	}
	
	private byte[] hFirst(byte[] in) {
		int n = _h.getOutputSize();
		int k = _hPrime.getOutputSize();
		int l = k/n;
		
		byte[][] w = new byte[l][n];
		byte[] iByte = new byte[1];
		
		_h.update(in);
		w[0] = _h.doFinal();
		_h.reset();
		
		for (int i = 1; i < l; ++i) {
			iByte[0] = (byte) i;
			_h.update(helper.concateByteArrays(iByte,w[0]));
			w[i] = _h.doFinal();
			_h.reset();
		}
		return helper.twoDimByteArrayToOne(w);
	}
	
	@Override
	public void setH(HashInterface h) {
		_h = h;
	}

	@Override
	public void setHPrime(HashInterface hPrime) {
		_hPrime = hPrime;
	}
	
	public void setIndexing(IdxInterface idx){
		indexing = idx;
	}
}
