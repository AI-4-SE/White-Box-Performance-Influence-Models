package main.java.components.gamma;

import main.java.components.hash.HashInterface;

public interface GammaInterface {
	
	public void setH(HashInterface h);
	public void setHPrime(HashInterface hPrime);
	
	public byte[][] gamma (int g, byte[][] x, byte[] gamma);
}