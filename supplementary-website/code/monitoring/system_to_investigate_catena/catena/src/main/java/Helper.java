package main.java;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class Helper {
	
	
	private final static char[] hexArray = 
			"0123456789ABCDEF".toCharArray();

	/**
	 * Helper-function
	 * from "github.com/Beloumi/Crypto-Eck"
	 * transfers string to byte array
	 * @param  hexString 	input string
	 * @return 				byte array
	 */
	public final byte[] hex2bytes(String hexString) {

		// 2 Character = 1 Byte
		byte[] byteArray = new byte[hexString.length() / 2];
		int len = hexString.length();
		if ( (len & 1) == 1){ 
			System.err.println("Illegal Argument (Function "
					+ "hexStringToBytes): HexString is not even");
			System.exit(1);
		}
		final char [] hexCharArray = hexString.toCharArray ();
		for (int i = 0; i < hexString.length(); i+=2) {
			// 1. char in hex <<4, 2. char in hex
			byteArray[i / 2] = 
					(byte) ((Character.digit (hexCharArray[i], 16) << 4) 
					+ Character.digit (hexCharArray[i + 1], 16));
		}		
		return byteArray;
	}
	
	public final byte[] string2Bytes (String hexString){
		return hexString.getBytes();
	}

	/**
	 * Helper-function
	 * from "github.com/Beloumi/Crypto-Eck"
	 * transfers byte array to string
	 * @param bytes 	array of bytes
	 * @return 			hexString
	 */
	public final String bytes2hex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars).toLowerCase();
	}
	
	/**
	 * Helper-function
	 * Concatenation function to combine all elements of the
	 * tweak in one array
	 * 
	 * @param bytes1 	vId 
	 * @param bytes2	mode
	 * @param bytes3	lambda
	 * @param bytes4 	outLen
	 * @param bytes5	sLen
	 * @param bytes6	additionalData
	 * 
	 * @return			all in one array
	 */
	public final byte[] concateByteArrays(byte[] bytes1, byte[] bytes2, 
			byte[] bytes3, byte[] bytes4, byte[] bytes5, byte[] bytes6){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(bytes1);
			out.write(bytes2);
			out.write(bytes3);
			out.write(bytes4);
			out.write(bytes5);
			out.write(bytes6);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * Helper-function
	 * Concatenation function to combine tweak array
	 * 
	 * @param bytes1 	t
	 * @param bytes2	pwd
	 * @param bytes3	salt
	 * @return			all in one array
	 */
	public final byte[] concateByteArrays(byte[] bytes1, byte[] bytes2, byte[] bytes3){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(bytes1);
			out.write(bytes2);
			out.write(bytes3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * Helper-function
	 * Concatenation function for byte arrays
	 * 
	 * @param bytes1 	byte[] 1
	 * @param bytes2	byte[] 2
	 * @param bytes3	byte[] 3
	 * @param bytes4	byte[] 4
	 * @return			all in one array
	 */
	public final byte[] concateByteArrays(byte[] bytes1, byte[] bytes2, byte[] bytes3, byte[] bytes4){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(bytes1);
			out.write(bytes2);
			out.write(bytes3);
			out.write(bytes4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * Helper-function
	 * Concatenation function to combine 2 byte arrays
	 * 
	 * @param bytes1 	first array
	 * @param bytes2	second array
	 * @return			all in one array
	 */
	public final byte[] concateByteArrays(byte[] bytes1, byte[] bytes2){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(bytes1);
			out.write(bytes2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * Converts Integer to byte arrays of size 2 or 4
	 * 
	 * @param data		input integer
	 * @param bytes		ammount of bytes in array
	 * @return			converted byte array
	 */
	public byte[] intToByteArray(int data, int bytes) {
	
		if (bytes == 4){
			byte[] result = new byte[4];
			
			result[0] = (byte) ((data & 0xFF000000) >> 24);
			result[1] = (byte) ((data & 0x00FF0000) >> 16);
			result[2] = (byte) ((data & 0x0000FF00) >>  8);
			result[3] = (byte) ((data & 0x000000FF)      );
			
			return result;
		} else if (bytes == 2){
			byte[] result = new byte[2];
			
			result[0] = (byte) ((data & 0xFF00) >> 8);
			result[1] = (byte) ((data & 0x00FF));
			
			return result;
		} else {
			return new byte[1];
		}
	}
	
	/**
	 * Converts Integer to byte arrays of size 2 or 4
	 * 
	 * @param data		input integer
	 * @param bytes		ammount of bytes in array
	 * @return			converted byte array
	 */
	public byte[] intToByteArrayLittleEndian(int data, int bytes) {
	
		if (bytes == 4){
			byte[] result = new byte[4];
			
			result[0] = (byte) ((data & 0x000000FF)      );
			result[1] = (byte) ((data & 0x0000FF00) >>  8);
			result[2] = (byte) ((data & 0x00FF0000) >> 16);
			result[3] = (byte) ((data & 0xFF000000) >> 24);
			
			return result;
		} else if (bytes == 2){
			byte[] result = new byte[2];
			
			result[0] = (byte) ((data & 0x00FF) 	);
			result[1] = (byte) ((data & 0xFF00) >> 8);
			
			return result;
		} else {
			return new byte[1];
		}
	}
	
	/**
	 * combines two dimensional byte array to one dimension 
	 * 
	 * @param bytes		two dim byte array
	 * @return			one dimensional array
	 */
	public byte[] twoDimByteArrayToOne(byte[][] bytes){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		for(int row =0 ; row < bytes.length; ++row){
			try {
				out.write(bytes[row]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toByteArray();
	}
	
	public byte[][] oneDimByteArrToTwoDim (byte[] b, int len){
		int bLen = b.length;
		int dimension = bLen/len;
		
		byte[][] twoDim = new byte[dimension][len];
		for (int i = 0; i < bLen/len; ++i ){
			byte[] tmp = new byte[len];
			System.arraycopy(b, i*len, tmp, 0, len);
			twoDim[i] = tmp;
		}
		return twoDim;
	}
	
	/**
	 * Helper-function
	 * Creates a new array of desired size and copies the old one in 
	 * 
	 * @param b		input array of size m
	 * @param l		desired size
	 * @return		array with padded 0's
	 */
	public final byte[] paddWithZero(byte[] b, int l){
		byte[] output = new byte[l];
		System.arraycopy(b, 0, output, 0, b.length);
		return output;
	}
	
	/**
	 * Helper-function
	 * truncates at the end of flap
	 * 
	 * @param x		the current array to be truncated
	 * @param m		the desired output length
	 * @return 		the resulting array
	 */
	public final byte[] truncate(byte[] x, int m){
		return Arrays.copyOfRange(x, 0, m);
	}
	
	/**
	 * XORing two byte arrays of equal size
	 * 
	 * @param arr1		first byte array
	 * @param arr2		second byte array
	 * @return			XORed byte array
	 */
	public byte[] xor (byte[] arr1, byte[] arr2){
		byte[] res = new byte[arr1.length];
		int i = 0;
		for (byte b : arr1)
		    res[i] = (byte) (b ^ arr2[i++]);
		return res;
	}
	
	/**
	 * Container for byte to long and long to byte transformation
	 */
	private ByteBuffer bufferLong = ByteBuffer.allocate(Long.BYTES);    

	/**
	 * Transforms long to byte array
	 * 
	 * @param x		long input
	 * @return		byte array
	 */
    public byte[] longToBytes(long x) {
    	bufferLong.putLong(0, x);
        return bufferLong.array();
    }

    /**
     * Transforms byte arrsy to long
     * 
     * @param bytes		bytes to transform to long
     * @return			long value
     */
    public long bytesToLong(byte[] bytes) {
    	bufferLong.put(bytes, 0, bytes.length);
    	bufferLong.flip();//need flip 
        return bufferLong.getLong();
    }
    

    /**
	 * Container for byte to int and int to byte transformation
	 */
    private ByteBuffer bufferInt = ByteBuffer.allocate(Integer.BYTES);

    /**
     * Transforms Integer to byte array
     * 
     * @param x			input integer
     * @return			byte array
     */
    public byte[] intToBytes(int x) {
    	bufferInt.clear();
    	bufferInt.putInt(x);
    	return bufferInt.array();
    }
    
    /**
     * Transforms byte array to Integer
     * 
     * @param bytes		bytes to be transformed to int
     * @return			int value
     */
    public int bytesToInt (byte[] bytes) {
    	bufferInt.put(bytes, 0, bytes.length);
    	bufferInt.flip();
    	return bufferInt.getInt();
    }
    
    public JSONArray jsonParserArr(File f){
		BufferedReader br;
		String all = "";
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				all+=line;
			}
		}catch (Exception e) {
		}
		return new JSONArray(all);
	}
    
    public JSONObject jsonParserObj(File f){
		BufferedReader br;
		String all = "";
		try {
			br = new BufferedReader(new FileReader(f));
			String line;
			while ((line = br.readLine()) != null) {
				all+=line;
			}
		}catch (Exception e) {
		}
		return new JSONObject(all);
	}
}
