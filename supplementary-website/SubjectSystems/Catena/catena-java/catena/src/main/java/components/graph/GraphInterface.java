package main.java.components.graph;

import main.java.components.graph.algorithms.IdxInterface;
import main.java.components.hash.HashInterface;

/**
 * Interface for graphs
 * @author max
 *
 */
public interface GraphInterface {
	
	public byte[][] graph (int g, byte[][] v, int lambda);
	
	public void setH (HashInterface h);
	public void setHPrime(HashInterface hPrime);
	public void setIndexing(IdxInterface idx);
}
