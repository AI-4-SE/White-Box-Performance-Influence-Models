package main.java;

import java.util.Arrays;
import java.util.Random;

import main.java.components.gamma.GammaInterface;
import main.java.components.graph.GraphInterface;
import main.java.components.graph.algorithms.IdxInterface;
import main.java.components.hash.HashInterface;
import main.java.components.phi.PhiInterface;

public class Catena {
	
	private Helper helper = new Helper();
	
	/**
	 * versionID decodes the version of catena
	 * ossible: "Dragonfly", "Dragonfly-Full", Butterfly", Butterfly-Full"
	 */
	private String _vId;
	
	private HashInterface 	_h;
	private HashInterface 	_hPrime;
	private GammaInterface 	_gamma;
    private GraphInterface 	_f;
    private PhiInterface 	_phi;
    
    private int _d = 0;			// 0 = Password Scrambling (Default)
    private int _n;				// H output size
	private int _k;				// H' output size
	private int _gLow;			
	private int _gHigh;
	private int _lambda;
	
	/**
	 * Main function of catena to hash a password
	 * 
	 * @param pwd			Password to be hashed
	 * @param salt			Salt of arbitrary length
	 * @param publicInput	User choosen public input
	 * @param gamma			Input for graph size
	 * @param m				User desired output length of hash
	 * 
	 * @return xTrun		Hash of pwd
	 */
	public byte[] catena(byte[] pwd, byte[] salt, byte[] publicInput,
		     byte[] gamma, int m){
		
		byte[] t = compTweak(_vId, _d, _lambda, m, salt.length, publicInput);
		
		_h.update(helper.concateByteArrays(t, pwd, salt));
		byte[] x = _h.doFinal();
		
		erasePwd(pwd);
		
		x = flap(((_gLow+1)/2), x, gamma);
		
		_h.update(x);
		x = _h.doFinal();
		
		byte[] gByte = new byte[1];
		
		for (int g = _gLow; g <= _gHigh; ++g){
			if (x.length < _n){
				x = helper.paddWithZero(x, _n);
			}
			
			x = flap(g, x, gamma);
			
			gByte[0] = (byte)g;
			_h.update(helper.concateByteArrays(gByte, x));
			x = _h.doFinal();
			x = helper.truncate(x, m);
		}
		return x;
	}
	
	/**
	 * flap function from catena specification
	 * 
	 * @param g
	 * @param xIn
	 * @param gamma
	 * @return
	 */
	private byte[] flap(int g, byte[] xIn, byte[] gamma){
		
		_hPrime.reset();
		
		byte[] xHinit;
		int iterations = (int)Math.pow(2, g);
		
		byte[][] v = new byte[iterations+2][_k];
		
		xHinit = hInit(xIn);
		
		System.arraycopy(xHinit, 0, v[0], 0, _k);
		System.arraycopy(xHinit, _k, v[1], 0, _k);
		
		for (int i=2; i<iterations+2; ++i){
			_hPrime.update(helper.concateByteArrays(v[i-1], v[i-2]));
			v[i] = _hPrime.doFinal();
		}
		
		byte[][] v2 = new byte[iterations][_k];
		System.arraycopy( v, 2, v2, 0, v2.length );
		
		_hPrime.reset();
		
		v2 = gamma(g, v2, gamma);
		
		_hPrime.reset();
		
		v2 = f(g, v2, _lambda);
		
		_hPrime.reset();
		v2 = phi(g, v2, v2[v2.length-1]);
		
		return v2[v2.length-1];
	}
	
	public byte[] flapPub(int g, byte[] xIn, byte[] gamma){
		return flap(g, xIn, gamma);
	}
	
	
	/**
	 * Initialisation of the 2 values for flap rounds
	 * 
	 * @param x		Input Array
	 * @return 		2 hashed values v_-1, V_-2 in one byte array
	 * 				(output is already splitted in the middle and swapped)
	 */
	private byte[] hInit(byte[] x){
		int l = 2*_k/_n;
		byte[][] xLoop = new byte[l][_n];
		byte[] iByte = new byte[1];
		
		for (int i=0; i<= l-1; ++i){
			iByte[0] = (byte) i;
			_h.update(helper.concateByteArrays(iByte, x));
			xLoop[i] = _h.doFinal();
			_h.reset();
		}
		return helper.twoDimByteArrayToOne(xLoop);
	}
	
	/**
	 * No clue how to test private functions so this wrapper exists
	 * 
	 * @param x		Initial value to instantiate v-2 and v-1
	 * @return		v-2 and v-1 combined in one array
	 */
	public byte[] testHInit(byte[] x){
		return hInit(x);
	}
	
	/**
	 * salt dependent update with random access
	 * 
	 * @param g		garlic
	 * @param x		hash array
	 * @param gamma	gamma
	 * @return		hash array
	 */
	private byte[][] gamma(int g, byte[][] x, byte[] gamma){
		return _gamma.gamma(g, x, gamma);
	}
	
	/**
	 * Memory expensive graph Layer
	 * 
	 * @param x 	hash input
	 * @return		hash output
	 */
	private byte[][] f(int g, byte[][] x, int lambda){
		return _f.graph(g, x, lambda);
	}
	
	/**
	 * phi function from catena specification
	 * 
	 * @param x		hash input
	 * @return		hash output
	 */
	private byte[][] phi(int garlic, byte[][] x, byte[] m){
		return _phi.phi(garlic, x, m);
	}
	
	/**
	 * Combine Tweak Array
	 * 
	 * @param vId		Version ID
	 * @param mode		Mode of catena
	 * @param lambda	Lambda
	 * @param outLen	Output Length
	 * @param sLen		Salt Length
	 * @param aData		Additional Data
	 * @return			Combined Tweak
	 */
	private byte[] compTweak(String vId, int mode, 
			int lambda, int outLen, int sLen, byte[] aData) {
		
		byte[] modeByte = new byte[1];
		byte[] lambdaByte = new byte[1];
		byte[] outLenByte = helper.intToByteArrayLittleEndian(outLen, 2);
		byte[] sLenByte = helper.intToByteArrayLittleEndian(sLen, 2);
		
		_h.update((helper.string2Bytes(vId)));
		byte[] vIdH = _h.doFinal();
		_h.reset();

		_h.update(aData);
		byte[] aDataH = _h.doFinal();
		_h.reset();
		
		modeByte[0] = (byte) mode;
		lambdaByte[0] = (byte) lambda;

		return helper.concateByteArrays(vIdH, modeByte, 
				lambdaByte, outLenByte, sLenByte, aDataH);
	}
	
	/**
	 * public interface for testing tweak computation
	 * 
	 * @param vId		String, VersionID
	 * @param mode		Integer, Mode of Catena
	 * @param lambda	Integer, The depth of the graph structure.
	 * @param outLen	Integer, Output length.
	 * @param sLen		Integer, Salt length.
	 * @param aData		byte[], Associated data of the user and/or the host.
	 * @return tweak	byte[], The calculatetd tweak.
	 */
	public byte[] testCompTweak (String vId, int mode, 
			int lambda, int outLen, int sLen, byte[] aData){
		return compTweak(vId, mode, lambda, outLen, sLen, aData);
	}
	
	/**
	 * Clear the password
	 * 
	 * @param pwd	the password to be cleared
	 */
	private final void erasePwd(byte[] pwd) {
		Arrays.fill(pwd, (byte) 0);
	}

	/**
	 * Initializes Catena
	 * 
	 * initializrs all needed variables and functions with default values
	 * 
	 * @param h			main hash function
	 * @param hPrime	reduced hash function
	 * @param gamma		gamma function (e.g. SaltMix)
	 * @param f			graph
	 * @param idx		index function for graph
	 * @param phi		phi function
	 * @param gLow		minimum Garlic
	 * @param gHigh		maximum Garlic
	 * @param lambda	depth of graphs
	 * @param vID		version ID
	 */
	public void init(HashInterface h, HashInterface hPrime, 
			GammaInterface gamma, GraphInterface f, 
			IdxInterface idx, PhiInterface phi, int gLow, int gHigh,
			int lambda, String vID){
		
		_h = h;
		_hPrime = hPrime;
		
		_gamma = gamma;
		_gamma.setH(_h);
		_gamma.setHPrime(_hPrime);
	    
		_f = f;
	    _f.setH(_h);
	    _f.setHPrime(_hPrime);
	    _f.setIndexing(idx);
	    
	    _phi = phi;
	    _phi.setH(_h);
	    _phi.setHPrime(_hPrime);
		
		_gLow = gLow;
		_gHigh = gHigh;
		
		_lambda = lambda;
		
		_n = _h.getOutputSize();
		_k = _hPrime.getOutputSize();
		
		_vId = vID;
	}
	
	public String getVID(){
		return _vId;
	}
	
	public int getLambda(){
		return _lambda;
	}
	
	public void setGHigh(int gHigh){
		_gHigh = gHigh;
	}
	
	public void setGLow(int gLow){
		_gLow = gLow;
	}
	
	public void setD(int d){
		_d = d;
	}
	
	public byte[] keyedClientIndependentUpdate(byte[] hashOld, int gHighOld,
			int gHighNew, byte[] gamma, int outputLenth, byte[] serverKey, byte[] userID) throws Exception{
		
		if (gHighOld >= gHighNew){
			throw new Exception("New gHigh value should be bigger as the old one.");
		}
		
		byte[] keystream = computeKeyStream(serverKey, userID, gHighOld, outputLenth);
		byte[] oldHash = helper.xor(hashOld, keystream);
		byte[] newHash = clientIndependentUpdate(oldHash, gHighOld, gHighNew, gamma, outputLenth);
		byte[] newKeystream = computeKeyStream(serverKey, userID, gHighNew, outputLenth);
		
		return helper.xor(newHash, newKeystream);
	}
	
	private byte[] computeKeyStream(byte[] serverKey, byte[] userID, int gHigh, int outLen){
		byte[] gByte = new byte[1];
		gByte[0] = (byte) gHigh;
		_h.update(helper.concateByteArrays(serverKey, userID, gByte, serverKey));
		
		byte[] output = _h.doFinal();
		output = helper.truncate(output, outLen);
		return output;
	}
	
	public byte[] clientIndependentUpdate(byte[] hashOld, int gHighOld,
			int gHighNew, byte[] gamma, int outputLenth) throws Exception{
		if (gHighOld >= gHighNew){
			throw new Exception("New gHigh value should be bigger as the old one.");
		}
		
		int n = _h.getOutputSize();
		
		byte[] newHash = new byte[n];
		byte[] gByte = new byte[1];
		
		System.arraycopy(hashOld, 0, newHash, 0, hashOld.length);
		
		for (int i = gHighOld + 1; i<gHighNew+1; ++i){
			if (newHash.length < _n){
				newHash = helper.paddWithZero(newHash, n);
			}
			newHash = flap(i, newHash, gamma);
			
			gByte[0] = (byte) i;
			
			_h.update(helper.concateByteArrays(gByte, newHash));
			newHash = _h.doFinal();
			_h.reset();
			
			newHash = helper.truncate(newHash, outputLenth);
		}
		return newHash;
	}
	
	public byte[] keyedPasswordHashing(byte[] pwd, byte[] key, 
			byte[] salt, byte[] gamma, byte[] a_data, int out_len,
            byte[] userID){
		byte[] gHighBytes = new byte[1];
		gHighBytes[0] = (byte)_gHigh;
		
		_h.update(helper.concateByteArrays(key, userID, gHighBytes,  key));
		byte[] z = helper.truncate(_h.doFinal(), out_len);		
		byte[] hash = catena(pwd, salt, a_data, gamma, out_len);
		
		return helper.xor(z, hash);
	}

	public byte[] keyDerivation(byte[] pwd, byte[] salt, 
			byte[] publicInput, byte[] gamma, int outLen, 
			int keySize, byte[] keyIdentifier){
		
		// Domain: 1 = Key Derivation
		int d = 1;
		byte[] tweak = compTweak(_vId, d, _lambda, outLen, 
				salt.length, publicInput);
		
		_h.update(helper.concateByteArrays(tweak, pwd, salt));
		byte[] x = _h.doFinal();
		
		x = flap(((_gLow+1)/2), x, gamma);
		
		erasePwd(pwd);
		
		_h.update(x);
		x = _h.doFinal();
		
		byte[] gByte = new byte[1];
		
		for (int g = _gLow; g <= _gHigh; ++g){
			if (x.length < _n){
				x = helper.paddWithZero(x, _n);
			}
			
			x = flap(g, x, gamma);
			
			gByte[0] = (byte)g;
			_h.update(helper.concateByteArrays(gByte, x));
			x = _h.doFinal();
			
			x = helper.truncate(x, outLen);
		}
		
		int limit = (int) Math.ceil((double)((double)keySize/_h.getOutputSize()));
		byte[] outputKey = new byte[0];
		
		for (int i = 1; i < limit+1; ++i){
			byte[] iByte = helper.intToByteArrayLittleEndian(i, 2);
			byte[] keySizeByte = helper.intToByteArrayLittleEndian(keySize, 2);
			
			_h.update(helper.concateByteArrays(iByte, keyIdentifier, keySizeByte, x));
			byte[] tmp = _h.doFinal();
			
			outputKey = helper.concateByteArrays(outputKey, tmp);
		}
		
		return helper.truncate(outputKey, keySize);
	}
	
	public byte[] serverReliefClient(byte[] pwd, byte[] salt, byte[] aData, 
			int outLen, byte[] gamma){
		
		// Domain = 0 Password Scrambling
		int d = 0;
		
		byte[] t = compTweak(_vId, d, _lambda, outLen, salt.length, aData);
		
		_h.update(helper.concateByteArrays(t, pwd, salt));
		byte[] x = _h.doFinal();
		
		x = flap(((_gLow+1)/2), x, gamma);
		
		erasePwd(pwd);
		
		_h.update(x);
		x = _h.doFinal();
		
		if (_gHigh > _gLow){
			byte[] gByte = new byte[1];
			for (int g = _gLow; g < _gHigh; ++g){
				
				if (x.length < _n){
					x = helper.paddWithZero(x, outLen);
				}
				
				x = flap(g, x, gamma);
				
				gByte[0] = (byte) g;
				_h.update(helper.concateByteArrays(gByte, x));
				x = _h.doFinal();
				
				x = helper.truncate(x, outLen);
			}
		}
		
		if (x.length < _n){
			x = helper.paddWithZero(x, _n);
		}
		x = flap(_gHigh, x, gamma);
		
		return x;
	}
	
	public byte[] serverReliefServer(int outLen, byte[] lastHash){
		byte[] gByte = new byte[1];
		gByte[0] = (byte) _gHigh;
		_h.update(helper.concateByteArrays(gByte, lastHash));
		return helper.truncate(_h.doFinal(), outLen);
	}
	
	public class POWstruct{
		public byte[] 	pwd;
		public byte[] 	salt;
		public byte[] 	aData;
		public int 		gLow;
		public int 		gHigh;
		public int 		outLen;
		public byte[] 	gamma;
		public int 		p;
		public byte[] 	rHash;
		public int 		mode;
	}
	
	public POWstruct proofOfWorkServer(byte[] pwd, byte[] salt, byte[] aData, 
			int outLen, byte[] gamma, int p, int mode){
		
		// Prove of Work
		setD(2);
		
		POWstruct output = new POWstruct();
		
		byte[] inpPWD = new byte[pwd.length];
		System.arraycopy(pwd, 0, inpPWD, 0, pwd.length);
		
		output.pwd = inpPWD;
		output.salt = salt;
		output.aData = aData;
		output.gLow = _gLow;
		output.gHigh = _gHigh;
		output.outLen = outLen;
		output.gamma = gamma;
		output.p = p;
		output.rHash = catena(pwd, salt, aData, gamma, outLen);
		output.mode = mode;
		
		if (mode == 0){
			byte[] inpSalt = new byte[salt.length];
			System.arraycopy(salt, 0, inpSalt, 0, salt.length);
			
			int a = (1 << (8*(int)(Math.ceil(p/8.0))))-(1 << p);
			byte[] mask = helper.intToBytes(a);
			
			int saltLength = inpSalt.length;
			int maskLength = mask.length;
			
			byte[] newMask = new byte[saltLength];
			
			if (maskLength > saltLength){
				System.arraycopy(mask, maskLength-saltLength, newMask, 0, saltLength);
			} else {
				System.arraycopy(mask, 0, newMask, 0, maskLength);
			}
			int newMaskLength = newMask.length;
			for (int i = 0; i < newMaskLength; ++i){
				if (newMask[i] == 0){
					newMask[i] = (byte) 0xff; 
				} else {
					break;
				}
			}
			
			for (int i = 0; i < newMaskLength; ++i){
				inpSalt[saltLength-newMaskLength+i] = (byte) (inpSalt[saltLength-newMaskLength+i] & newMask[i]); 
			}
			
			output.salt = inpSalt;
			return output;
		} else if (mode == 1){
			output.pwd = new byte[0];
			return output;
		} else {
			return new POWstruct();
		}
	}

	public byte[] proofOfWorkClient(POWstruct input){
		setD(2);
		if (input.mode == 0){
			
			int numBytes = (int) Math.ceil(input.p/8.0);
			int upperBound = (1 << input.p);
			Random rand = new Random();
			int randomOffset = rand.nextInt(upperBound);
			
			// guessing secret bits in salt (pepper)
			for(int i = 0; i < upperBound; ++i){
				
				byte[] pwd = new byte[input.pwd.length];
				System.arraycopy(input.pwd, 0, pwd, 0, input.pwd.length);
				
				byte[] saltInput = new byte[input.salt.length];
				System.arraycopy(input.salt, 0, saltInput, 0, input.salt.length);
				
				byte[] pepperPre = helper.intToBytes((i+randomOffset) % upperBound);
				byte[] pepper = new byte[numBytes];
				System.arraycopy(pepperPre, (pepperPre.length-numBytes), pepper, 0, numBytes);
				
				
				if (saltInput.length == pepper.length){
					saltInput = pepper;
				} else {
					for (int j = 1; j == numBytes; ++j){
						saltInput[saltInput.length-j] = (byte) (saltInput[saltInput.length-j] + pepper[pepper.length-j]);
					}
				}
				
				byte[] actualHash = catena(pwd, saltInput, input.aData, input.gamma, input.outLen);
				if (helper.bytes2hex(actualHash).equals(helper.bytes2hex(input.rHash))){
					return saltInput;
				}
			}
			return new byte[0];
		} else if (input.mode == 1){
			
			int numBytes = (int) Math.ceil(input.p/8.0);
			int upperBound = (1 << input.p);
			Random rand = new Random();
			int randomOffset = rand.nextInt(upperBound);
			
			// guessing secret password (length of p)
			for(int i = 0; i < upperBound; ++i){
				
				byte[] pepperPwd = helper.intToBytes((i+randomOffset) % upperBound);
				byte[] sectretPwd = new byte[numBytes];
				System.arraycopy(pepperPwd, (pepperPwd.length-numBytes), sectretPwd, 0, numBytes);
				
				byte[] saveForReturn = new byte[sectretPwd.length];
				System.arraycopy(sectretPwd, 0, saveForReturn, 0, sectretPwd.length);
				
				byte[] actual = catena(sectretPwd, input.salt, input.aData, input.gamma, input.outLen);
				
				if (helper.bytes2hex(actual).equals(helper.bytes2hex(input.rHash))){
					return saveForReturn;
				}
			}
			// could not guess pwd
			return new byte[0];
		} else {
			// mode is not 0 or 1
			return new byte[0];
		}
	}
	
}
