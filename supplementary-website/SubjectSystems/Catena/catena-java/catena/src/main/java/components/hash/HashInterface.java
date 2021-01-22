package main.java.components.hash;

/**
 * Interface for hashing functions
 * @author max
 *
 */
public interface HashInterface {
	
	public void update(byte[] message);
	public void update(byte b);
	public void update(byte[] message, int offset, int len);
	
	public byte[] doFinal();
	
	public void reset();
	
	public void setVertexIndex(int r);
	
	public String getName();
	public int getOutputSize();
}
