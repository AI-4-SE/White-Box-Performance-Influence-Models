package main.java.components.gamma.algorithms;

import main.java.components.gamma.GammaInterface;
import main.java.components.hash.HashInterface;

public class IdentityGamma implements GammaInterface {

	HashInterface _h;
	HashInterface _hPrime;
	
	public void setH(HashInterface h) {
		_h = h;
	}

	public void setHPrime(HashInterface hPrime) {
		_hPrime = hPrime;
	}

	public byte[][] gamma(int g, byte[][] x, byte[] gamma) {
		return x;
	}
}
