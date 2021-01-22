package main.java.components.phi.algorithms;

import main.java.Helper;
import main.java.components.hash.HashInterface;
import main.java.components.phi.PhiInterface;

public class CatenaPhi implements PhiInterface {

	Helper helper = new Helper();
	
	IdxStateInterface _idx;
	HashInterface _h;
	HashInterface _hPrime;
	
	public CatenaPhi(IdxStateInterface idx){
		_idx = idx; 
	}
	
	public byte[][] phi(int garlic, byte[][] b, byte[] m) {
		int j = _idx.getLsbIndex(m, garlic);
		byte[][] tmp = new byte[b.length][_h.getOutputSize()];
		
		System.arraycopy(b, 0, tmp, 0, b.length);
		
		
		
		_hPrime.update(helper.concateByteArrays(b[b.length-1], b[j]));
		tmp[0] = _hPrime.doFinal();
		_hPrime.reset();
		
		for (int i = 1; i < b.length; ++i){
			j = _idx.getLsbIndex(tmp[i-1], garlic);
			
			_hPrime.update(helper.concateByteArrays(tmp[i-1], tmp[j]));
			tmp[i] = _hPrime.doFinal();
			_hPrime.reset(); 
		}
		
		return tmp;
	}

	public void setH(HashInterface h) {
		_h = h;
	}

	public void setHPrime(HashInterface hPrime) {
		_hPrime = hPrime;
	}

}
