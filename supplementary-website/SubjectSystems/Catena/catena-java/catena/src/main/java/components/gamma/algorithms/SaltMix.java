package main.java.components.gamma.algorithms;

import main.java.Helper;
import main.java.components.gamma.GammaInterface;
import main.java.components.hash.HashInterface;

/**
 * Salt dependent pudate using xorShif1024* RNG
 * @author max
 *
 */
public class SaltMix implements GammaInterface {
	
	private HashInterface _h;
	private HashInterface _hPrime;
	private Helper helper = new Helper();
	
	private long[] r ;	// state of the Xorshift RNG
	private int p; 		// position in state vector s
	
	/**
	 * implements SaltMix with xorShift1024*
	 * 
	 * @param g			garlic
	 * @param x			state
	 * @param gamma		gamma
	 * @return			updated state
	 */
	public byte[][] gamma (int g, byte[][] x, byte[] gamma){
		
		byte[] gammaByte = gamma;
		byte[] tmp1;
		byte[] tmp2;
		
		_h.update(gammaByte);
		tmp1 = _h.doFinal();
		_h.reset();
		_h.update(tmp1);
		tmp2 = _h.doFinal();
		_h.reset();
		
		transformBytesToLong(tmp1, tmp2);
		
		p=0;
		long j1 = 0;
		long j2 = 0;
		int loopLimit = (int)Math.pow(2, Math.ceil(3.0*g/4.0));
		
//		System.out.println("Looplimit: " + loopLimit);
		
		_hPrime.reset();
		
		for (int i = 0; i < loopLimit; ++i){
			j1 = xorshift1024star() >>> (64 - g);
			j2 = xorshift1024star() >>> (64 - g);
			
			_hPrime.update(helper.concateByteArrays(x[(int)j1], x[(int)j2]));
			x[(int)j1] = _hPrime.doFinal();
			
		}
		return x;
	}
	
	public long xorshift1024star(){	
		// computes random g-bit value j1 / j2
		// in each iteration of the for-loop of saltMix 
		long s0 = r[p];
		p = (p+1) & 15;
		long s1 = r[ p];
		s1 ^= s1 << 31; // a
		s1 ^= s1 >>> 11; // b
		s0 ^= s0 >>> 30; // c
		r[p] = s0 ^ s1;
		return r[p] * 1181783497276652981L;
	}
	
	void transformBytesToLong(byte[] a, byte[] b){ // seed the state with two hash values
		r = new long[16];

		p = 0;
		int sIndex = 0;
		for ( int i = 0; i < a.length; i+=8) {
			r[sIndex++] = bytes2long(a, i);
		}
		for ( int i = 0; i < b.length; i+=8) {
			r[sIndex++] = bytes2long(b, i);
		}		
	}
	
	public final static long bytes2long(byte[] byteArray, int offset) {
	      
	      return (	    		  
				  (((long) byteArray[offset + 0] & 0xFF )	   ) |
				  (((long) byteArray[offset + 1] & 0xFF ) << 8 ) |
				  (((long) byteArray[offset + 2] & 0xFF ) << 16) |
				  (((long) byteArray[offset + 3] & 0xFF ) << 24) |
			      (((long) byteArray[offset + 4] & 0xFF ) << 32) |
			      (((long) byteArray[offset + 5] & 0xFF ) << 40) |
			      (((long) byteArray[offset + 6] & 0xFF ) << 48) |
			      (((long) byteArray[offset + 7] & 0xFF ) << 56) ) ;  	    			    		  
	}
	
	@Override
	public void setH(HashInterface h) {
		_h = h;
	}

	@Override
	public void setHPrime(HashInterface hPrime) {
		_hPrime = hPrime;
	}

}
