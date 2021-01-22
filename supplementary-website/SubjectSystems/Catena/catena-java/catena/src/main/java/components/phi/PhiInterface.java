package main.java.components.phi;

import main.java.components.hash.HashInterface;

public interface PhiInterface {
	
	public byte[][] phi (int garlic, byte[][] b, byte[] m);
	
	public void setH (HashInterface h);
	public void setHPrime(HashInterface hPrime);
}
