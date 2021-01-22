package main.java.components.phi.algorithms;

import main.java.components.hash.HashInterface;
import main.java.components.phi.PhiInterface;

public class IdentityPhi implements PhiInterface {

	HashInterface _h;
	HashInterface _hPrime;
	
	public byte[][] phi(int garlic, byte[][] b, byte[] m) {
		return b;
	}

	public void setH(HashInterface h) {
		_h = h;
	}

	public void setHPrime(HashInterface hPrime) {
		_hPrime = hPrime;
	}
	
}

