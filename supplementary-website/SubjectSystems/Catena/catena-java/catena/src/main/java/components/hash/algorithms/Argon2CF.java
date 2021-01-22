package main.java.components.hash.algorithms;

import java.util.Arrays;

import main.java.Helper;
import main.java.components.hash.HashInterface;

public class Argon2CF implements HashInterface {

	Helper helper = new Helper();
	
	private String name = "Argon2 hash function.";
	private int anzByte = 1024;
	// use GL if 1 else use GB
	private boolean _useGL;
	
	private long[] _v = new long[128];
	
	private byte[] _z;
	
	public Argon2CF (boolean useGL){
		_useGL = useGL;
	}
	
	public void update(byte[] message) {

		if (message.length != 2048){
			return;
		}
		
		byte [] x = new byte[anzByte];
		byte [] y = new byte[anzByte];
		
		System.arraycopy(message, 0, x, 0, anzByte);
		System.arraycopy(message, 1024, y, 0, anzByte);
		
		byte [] R = helper.xor(x, y);
		byte [] Q = new byte[R.length];
		
		// updating rows
		pWrapper(Arrays.copyOfRange(R, 0,   128), 0);
		pWrapper(Arrays.copyOfRange(R, 128, 256), 1);
		pWrapper(Arrays.copyOfRange(R, 256, 384), 2);
		pWrapper(Arrays.copyOfRange(R, 384, 512), 3);
		pWrapper(Arrays.copyOfRange(R, 512, 640), 4);
		pWrapper(Arrays.copyOfRange(R, 640, 768), 5);
		pWrapper(Arrays.copyOfRange(R, 768, 896), 6);
		pWrapper(Arrays.copyOfRange(R, 896, 1024), 7);
		
		
		// inverting elements of array _v
//		for (int i = 0; i<_v.length; ++i){
//			byte[] tmp = long2bytes(_v[i]);
//			
//			for (int k = 0; k < tmp.length/2; k++) {
//			    byte temp = tmp[k];
//			    tmp[k] = tmp[tmp.length-(1+k)];
//			    tmp[tmp.length-(1+k)] = temp;
//			}
//			_v[i] = bytes2long(tmp, 0);
//		}
		
		// updating columns
		for (int i = 0; i<=7; ++i){
			int i0 = i*2;
			int i1 = i0+16;
			int i2 = i1+16;
			int i3 = i2+16;
			int i4 = i3+16;
			int i5 = i4+16;
			int i6 = i5+16;
			int i7 = i6+16;
			
			p(i0, i1, i2, i3, i4, i5, i6, i7);
		}
		
		// inverting elements of array _v
//		for (int i = 0; i<_v.length; ++i){
//			byte[] tmp = long2bytes(_v[i]);
//			
//			for (int k = 0; k < tmp.length/2; k++) {
//			    byte temp = tmp[k];
//			    tmp[k] = tmp[tmp.length-(1+k)];
//			    tmp[tmp.length-(1+k)] = temp;
//			}
//			_v[i] = bytes2long(tmp, 0);
//		}
		
		for (int i=0; i<_v.length; ++i){
			byte[] tmp = long2bytes(_v[i]);
			System.arraycopy(tmp, 0, Q, i*8, 8);
		}
				
		_z = helper.xor(R, Q);
	}

	private void p(int i0, int i1, int i2, int i3, int i4, int i5, int i6, int i7){
		
		if(_useGL){
			GL(i0,   i2,   i4,   i6);
			GL(i0+1, i2+1, i4+1, i6+1);
			GL(i1,   i3,   i5,   i7);
			GL(i1+1, i3+1, i5+1, i7+1);
			GL(i0,   i2+1, i5,   i7+1);
			GL(i0+1, i3,   i5+1, i6);
			GL(i1,   i3+1, i4,   i6+1);
			GL(i1+1, i2,   i4+1, i7);
		} else {
			GB(i0,   i2,   i4,   i6);
			GB(i0+1, i2+1, i4+1, i6+1);
			GB(i1,   i3,   i5,   i7);
			GB(i1+1, i3+1, i5+1, i7+1);
			GB(i0,   i2+1, i5,   i7+1);
			GB(i0+1, i3,   i5+1, i6);
			GB(i1,   i3+1, i4,   i6+1);
			GB(i1+1, i2,   i4+1, i7);
		}
		
	}

	private void pWrapper(byte[] vIn, int idx){
		pAndInit(Arrays.copyOfRange(vIn, 0, 16), Arrays.copyOfRange(vIn, 16, 32), 
				Arrays.copyOfRange(vIn, 32, 48), Arrays.copyOfRange(vIn, 48, 64), 
				Arrays.copyOfRange(vIn, 64, 80), Arrays.copyOfRange(vIn, 80, 96), 
				Arrays.copyOfRange(vIn, 96, 112), Arrays.copyOfRange(vIn, 112, 128), 
				idx);
	}
	
	private void pAndInit(byte[] p1, byte[] p2, byte[] p3, byte[] p4, byte[] p5, byte[] p6, byte[] p7, byte[] p8,
			int idx){
		
		// initialize v (global array)
		_v[0+(idx*16)] = bytes2long(Arrays.copyOfRange(p1, 0, 8),0);
		_v[1+(idx*16)] = bytes2long(Arrays.copyOfRange(p1, 8, 16),0);
		
		_v[2+(idx*16)] = bytes2long(Arrays.copyOfRange(p2, 0, 8),0);
		_v[3+(idx*16)] = bytes2long(Arrays.copyOfRange(p2, 8, 16),0);
		
		_v[4+(idx*16)] = bytes2long(Arrays.copyOfRange(p3, 0, 8),0);
		_v[5+(idx*16)] = bytes2long(Arrays.copyOfRange(p3, 8, 16),0);
		
		_v[6+(idx*16)] = bytes2long(Arrays.copyOfRange(p4, 0, 8),0);
		_v[7+(idx*16)] = bytes2long(Arrays.copyOfRange(p4, 8, 16),0);
		
		_v[8+(idx*16)] = bytes2long(Arrays.copyOfRange(p5, 0, 8),0);
		_v[9+(idx*16)] = bytes2long(Arrays.copyOfRange(p5, 8, 16),0);
		
		_v[10+(idx*16)] = bytes2long(Arrays.copyOfRange(p6, 0, 8),0);
		_v[11+(idx*16)] = bytes2long(Arrays.copyOfRange(p6, 8, 16),0);
		
		_v[12+(idx*16)] = bytes2long(Arrays.copyOfRange(p7, 0, 8),0);
		_v[13+(idx*16)] = bytes2long(Arrays.copyOfRange(p7, 8, 16),0);
		
		_v[14+(idx*16)] = bytes2long(Arrays.copyOfRange(p8, 0, 8),0);
		_v[15+(idx*16)] = bytes2long(Arrays.copyOfRange(p8, 8, 16),0);
			
		if(_useGL){
			GL(0+(idx*16), 4+(idx*16), 8+(idx*16), 12+(idx*16));
			GL(1+(idx*16), 5+(idx*16), 9+(idx*16), 13+(idx*16));
			GL(2+(idx*16), 6+(idx*16), 10+(idx*16), 14+(idx*16));
			GL(3+(idx*16), 7+(idx*16), 11+(idx*16), 15+(idx*16));
			GL(0+(idx*16), 5+(idx*16), 10+(idx*16), 15+(idx*16));
			GL(1+(idx*16), 6+(idx*16), 11+(idx*16), 12+(idx*16));
			GL(2+(idx*16), 7+(idx*16), 8+(idx*16), 13+(idx*16));
			GL(3+(idx*16), 4+(idx*16), 9+(idx*16), 14+(idx*16));
		} else {
			GB(0+(idx*16), 4+(idx*16), 8+(idx*16), 12+(idx*16));
			GB(1+(idx*16), 5+(idx*16), 9+(idx*16), 13+(idx*16));
			GB(2+(idx*16), 6+(idx*16), 10+(idx*16), 14+(idx*16));
			GB(3+(idx*16), 7+(idx*16), 11+(idx*16), 15+(idx*16));
			GB(0+(idx*16), 5+(idx*16), 10+(idx*16), 15+(idx*16));
			GB(1+(idx*16), 6+(idx*16), 11+(idx*16), 12+(idx*16));
			GB(2+(idx*16), 7+(idx*16), 8+(idx*16), 13+(idx*16));
			GB(3+(idx*16), 4+(idx*16), 9+(idx*16), 14+(idx*16));
		}
	}

	public byte[] doFinal() {
		return _z;
	}
	
	private void GL(int posA, int posB, int posC, int posD) {

		_v[posA] = _v[posA] + _v[posB];
	    _v[posD] = rotr64(_v[posD] ^ _v[posA], 32); 
	    _v[posC] = _v[posC] + _v[posD]; 
	    _v[posB] = rotr64(_v[posB] ^ _v[posC], 24); // replaces 25 of BLAKE
	    _v[posA] = _v[posA] + _v[posB]; 
	    _v[posD] = rotr64(_v[posD] ^ _v[posA], 16); 
	    _v[posC] = _v[posC] + _v[posD]; 
	    _v[posB] = rotr64(_v[posB] ^ _v[posC], 63); // replaces 11 of BLAKE
	}

	private void GB(int posA, int posB, int posC, int posD) {

		_v[posA] = _v[posA] + _v[posB] + 
				2 * (_v[posA] & 0x00000000FFFFFFFFL) 
				* (_v[posB] & 0x00000000FFFFFFFFL);
	    _v[posD] = rotr64(_v[posD] ^ _v[posA], 32); 
	    _v[posC] = _v[posC] + _v[posD] + 
				2 * (_v[posC] & 0x00000000FFFFFFFFL) 
				* (_v[posD] & 0x00000000FFFFFFFFL); 
	    _v[posB] = rotr64(_v[posB] ^ _v[posC], 24); // replaces 25 of BLAKE
	    _v[posA] = _v[posA] + _v[posB] + 
				2 * (_v[posA] & 0x00000000FFFFFFFFL) 
				* (_v[posB] & 0x00000000FFFFFFFFL); 
	    _v[posD] = rotr64(_v[posD] ^ _v[posA], 16); 
	    _v[posC] = _v[posC] + _v[posD] + 
				2 * (_v[posC] & 0x00000000FFFFFFFFL) 
				* (_v[posD] & 0x00000000FFFFFFFFL); 
	    _v[posB] = rotr64(_v[posB] ^ _v[posC], 63); // replaces 11 of BLAKE
	}
	
	private long rotr64(long x, int rot) {
		return x >>> rot | (x << (64 - rot));
	}
	
	// convert one long value in byte array
	// little-endian byte order!
	private final static byte[] long2bytes(long longValue) {
	    return new byte[] {	        
		    (byte) longValue,
	        (byte) (longValue >> 8),
	        (byte) (longValue >> 16),
	        (byte) (longValue >> 24),
	        (byte) (longValue >> 32),
	        (byte) (longValue >> 40),
	        (byte) (longValue >> 48),
	        (byte) (longValue >> 56)};
	}
	
	// little-endian byte order!
	private final static long bytes2long(byte[] byteArray, int offset) {
	      
		return (
				((long) byteArray[offset] & 0xFF ) |
				(((long) byteArray[offset + 1] & 0xFF ) << 8) |
				(((long) byteArray[offset + 2] & 0xFF ) << 16) |
				(((long) byteArray[offset + 3] & 0xFF ) << 24) |
				(((long) byteArray[offset + 4] & 0xFF ) << 32) |
				(((long) byteArray[offset + 5] & 0xFF ) << 40) |
				(((long) byteArray[offset + 6] & 0xFF ) << 48) |
				(((long) byteArray[offset + 7] & 0xFF ) << 56) ) ;
	}

	public void reset() {
		_v = new long[128];
	}
	
	public void update(byte b) {
		return;
	}

	public void update(byte[] message, int offset, int len) {
		return;
	}

	public void setVertexIndex(int r) {
		return;
	}

	public String getName() {
		return name;
	}

	public int getOutputSize() {
		return anzByte;
	}

}
