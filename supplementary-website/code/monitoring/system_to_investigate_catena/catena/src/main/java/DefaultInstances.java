package main.java;

import main.java.components.gamma.GammaInterface;
import main.java.components.gamma.algorithms.IdentityGamma;
import main.java.components.gamma.algorithms.SaltMix;
import main.java.components.graph.GraphInterface;
import main.java.components.graph.algorithms.DoubleButterflyGraph;
import main.java.components.graph.algorithms.GenericGraph;
import main.java.components.graph.algorithms.IdxInterface;
import main.java.components.graph.algorithms.index.IndexBRG;
import main.java.components.graph.algorithms.index.IndexDBG;
import main.java.components.graph.algorithms.index.IndexGRG;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Argon2CF;
import main.java.components.hash.algorithms.Blake2b;
import main.java.components.hash.algorithms.Blake2b_1;
import main.java.components.phi.PhiInterface;
import main.java.components.phi.algorithms.CatenaPhi;
import main.java.components.phi.algorithms.IdentityPhi;
import main.java.components.phi.algorithms.IdxStateInterface;
import main.java.components.phi.algorithms.index.LSBIndex;

public class DefaultInstances extends Catena {

	/**
	 * initalizes Butterfly default instance of catena
	 * 
	 * @return	Catena Butterfly instance
	 */
	public Catena initButterfly (){
		Catena catena = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b_1();
		
		GammaInterface gamma = new SaltMix();
		GraphInterface f = new DoubleButterflyGraph();
		IdxInterface idx = new IndexDBG();
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 16;
		int gHigh = 16;
		int lambda = 4;
		
		String vID = "Butterfly";
		
		catena.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return catena;
	}
	
	/**
	 * initalizes Butterfly Full default instance of catena
	 * 
	 * @return	Catena Butterfly Full instance
	 */
	public Catena initButterflyFull(){
		Catena catena = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		GammaInterface gamma = new SaltMix();
		GraphInterface f = new DoubleButterflyGraph();
		IdxInterface idx = new IndexDBG();
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 17;
		int gHigh = 17;
		int lambda = 4;
		
		String vID = "Butterfly-Full";
		
		catena.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return catena;
	}
	
	/**
	 * initalizes Dragonfly default instance of catena
	 * 
	 * @return	Catena Dragonfly instance
	 */
	public Catena initDragonfly(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b_1();
		
		GammaInterface gamma = new SaltMix();
		
		GraphInterface f = new GenericGraph();

		IdxInterface idx = new IndexBRG();
		PhiInterface phi = new IdentityPhi();		
		
		int gLow = 21;
		int gHigh = 21;
		int lambda = 2;
		
		String vID = "Dragonfly";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Dragonfly Full default instance of catena
	 * 
	 * @return	Catena Dragonfly Full instance
	 */
	public Catena initDragonflyFull(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		GammaInterface gamma = new SaltMix();
		
		GraphInterface f = new GenericGraph();

		IdxInterface idx = new IndexBRG();
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 22;
		int gHigh = 22;
		
		int lambda = 2;
		
		String vID = "Dragonfly-Full";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Horsefly default instance of catena
	 * 
	 * @return	Catena Horsefly instance
	 */
	public Catena initHorsefly(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		boolean useGL = true;
		HashInterface hPrime = new Argon2CF(useGL);
		
		GammaInterface gamma = new IdentityGamma();
		
		GraphInterface f = new GenericGraph();
		IdxInterface idx = new IndexBRG();
		
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 19;
		int gHigh = 19;
		
		int lambda = 1;
		
		String vID = "Horsefly";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Horsefly Full default instance of catena 
	 * 
	 * @return	Catena Horsefly Full instance
	 */
	public Catena initHorseflyFull(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		GammaInterface gamma = new IdentityGamma();
		
		GraphInterface f = new GenericGraph();
		IdxInterface idx = new IndexBRG();
		
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 23;
		int gHigh = 23;
		
		int lambda = 1;
		
		String vID = "Horsefly-Full";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Lanternfly default instance of catena
	 * 
	 * @return	Catena Lanternfly instance
	 */
	public Catena initLanternfly(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		boolean useGL = false;
		HashInterface hPrime = new Argon2CF(useGL);
		
		GammaInterface gamma = new SaltMix();
		
		GraphInterface f = new GenericGraph();
		int l = 3;
		IdxInterface idx = new IndexGRG(l);
		
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 17;
		int gHigh = 17;
		
		int lambda = 2;
		
		String vID = "Lanternfly";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Lanternfly Full default instance of catena
	 * 
	 * @return	Catena Lanternfly Full instance
	 */
	public Catena initLanternflyFull(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		GammaInterface gamma = new SaltMix();
		
		GraphInterface f = new GenericGraph();
		int l = 3;
		IdxInterface idx = new IndexGRG(l);
		
		PhiInterface phi = new IdentityPhi();
		
		int gLow = 22;
		int gHigh = 22;
		
		int lambda = 2;
		
		String vID = "Lanternfly-Full";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Mydasfly default instance of catena
	 * 
	 * @return	Catena Mydasfly instance
	 */
	public Catena initMydasfly(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		boolean useGL = true;
		HashInterface hPrime = new Argon2CF(useGL);
		
		GammaInterface gamma = new IdentityGamma();
		
		GraphInterface f = new DoubleButterflyGraph();
		IdxInterface idx = new IndexDBG();
		
		IdxStateInterface idxState = new LSBIndex();
		PhiInterface phi = new CatenaPhi(idxState);
		
		int gLow = 14;
		int gHigh = 14;
		
		int lambda = 2;
		
		String vID = "Mydasfly";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Mydasfly Full default instance of catena
	 * 
	 * @return	Catena Mydasfly Full instance
	 */
	public Catena initMydasflyFull(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		GammaInterface gamma = new IdentityGamma();
		
		GraphInterface f = new DoubleButterflyGraph();
		IdxInterface idx = new IndexDBG();
		
		IdxStateInterface idxState = new LSBIndex();
		PhiInterface phi = new CatenaPhi(idxState);
		
		int gLow = 18;
		int gHigh = 18;
		
		int lambda = 2;
		
		String vID = "Mydasfly-Full";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Stonefly default instance of catena
	 * 
	 * @return Catena Stonefly instance
	 */
	public Catena initStonefly(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		boolean useGL = false;
		HashInterface hPrime = new Argon2CF(useGL);
		
		GammaInterface gamma = new SaltMix();
		
		GraphInterface f = new GenericGraph();
		IdxInterface idx = new IndexBRG();
		
		IdxStateInterface idxState = new LSBIndex();
		PhiInterface phi = new CatenaPhi(idxState);
		
		int gLow = 18;
		int gHigh = 18;
		
		int lambda = 2;
		
		String vID = "Stonefly";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
	
	/**
	 * initalizes Stonefly Full default instance of catena
	 * 
	 * @return Catena Stonefly Full instance
	 */
	public Catena initStoneflyFull(){
		Catena c = new Catena();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		GammaInterface gamma = new SaltMix();
		
		GraphInterface f = new GenericGraph();
		IdxInterface idx = new IndexBRG();
		
		IdxStateInterface idxState = new LSBIndex();
		PhiInterface phi = new CatenaPhi(idxState);
		
		int gLow = 22;
		int gHigh = 22;
		
		int lambda = 2;
		
		String vID = "Stonefly-Full";
		
		c.init(h, hPrime, gamma, f, idx, phi, gLow, gHigh, lambda, vID);
		
		return c;
	}
}
