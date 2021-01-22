package main.java.components.graph.algorithms;

import kieker.monitoring.annotation.OperationExecutionMonitoringProbe;
import main.java.Helper;
import main.java.components.graph.GraphInterface;
import main.java.components.hash.HashInterface;

public class DoubleButterflyGraph implements GraphInterface {

	HashInterface _h;
	HashInterface _hPrime;
	Helper helper = new Helper();
	IdxInterface indexing;
	
	@Override
	@OperationExecutionMonitoringProbe
	public byte[][] graph(int g, byte[][] v, int lambda) {
		
		int dim1 = (int)Math.pow(2, g);;
		int dim2 = _hPrime.getOutputSize();
		byte [][] r = new byte[dim1][dim2];
		
		int jLimit = 2*g - 1;
		int iLimit = dim1-1;
		
		_hPrime.reset();
		
		for (int k = 1; k <= lambda; k++) {
			for (int j = 1; j <= jLimit; j++) {
	
				r[0] = hFirst(helper.concateByteArrays(
						helper.xor(v[dim1-1],v[0]),
						v[(int)indexing.getIndex((long)g, (long)j-1, 0)]));
				
				for (int i = 1; i <= iLimit; i++) {
					_hPrime.update(helper.concateByteArrays(
							helper.xor(r[i-1],v[i]),
							v[(int)indexing.getIndex((long)g, (long)j-1, i)]));
					r[i] = _hPrime.doFinal();
				}
				System.arraycopy(r, 0, v, 0, r.length);
			}
		}
		return v;
	}

	@Override
	public void setH(HashInterface h) {
		_h = h;
	}

	@Override
	public void setHPrime(HashInterface hPrime) {
		_hPrime = hPrime;
	}

	@Override
	public void setIndexing(IdxInterface idx) {
		indexing = idx;
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
		
		for (int i = 1; i <= l-1; ++i) {
			iByte[0] = (byte) i;
			_h.update(helper.concateByteArrays(iByte,w[0]));
			w[i] = _h.doFinal();
			_h.reset();
		}
		return helper.twoDimByteArrayToOne(w);
	}
}
