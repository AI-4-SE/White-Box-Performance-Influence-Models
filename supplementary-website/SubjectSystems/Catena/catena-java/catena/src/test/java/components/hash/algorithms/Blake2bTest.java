package test.java.components.hash.algorithms;

/**
 * @author Max Weber
 * hex2bytes and bytes2hex are from 
 * https://github.com/BLAKE2/
 */
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.hash.algorithms.Blake2b;

public class Blake2bTest {
	
	static class TestVector {
		public String Input, Key, Hash;
		
		public TestVector(String in, String key, String hash){
			Input = in;
			Key = key;
			Hash = hash;			
		}
	};
	 
	private Helper helper = new Helper();
	
	private static final String testVectorSource = 
			"src/test/resources/testvectors/blake2b.json";
	private static final File fileBlake2b = new File(testVectorSource);
	
	String input1 = "";
	String key = "000102030405060708090a0b0c0d0e0f10111213"
			+ "1415161718191a1b1c1d1e1f2021222324252627282"
			+ "92a2b2c2d2e2f303132333435363738393a3b3c3d3e"
			+ "3f";
	String hash1 = "10ebb67700b1868efb4417987acf4690ae9d97"
			+ "2fb7a590c2f02871799aaa4786b5e996e8f0f4eb981"
			+ "fc214b005f42d2ff4233499391653df7aefcbc13fc5"
			+ "1568";
	String zeroes = "0000000000000000000000000000000000000"
			+ "0000000000000000000000000000000000000000000"
			+ "0000000000000000000000000000000000000000000"
			+ "0000000";
	String zeroesRes = "061D94FA094482C5688AB83995F17AEC64"
			+ "F955D59E9EB406EA9BBA84DF7D6DA86D1C88D519669"
			+ "159E409A926A1D9E151A4F69EF110EAD0A1E5B62A42"
			+ "E15C396F";
	String zeroes2 = "0100000000000000000000000000000000000"
			+ "0000000000000000000000000000000000000000000"
			+ "0000000000000000000000000000000000000000000"
			+ "0000000";
	String zeroesRes2 = "A4C37D9E1FD03BA5E8EB64D307225A959"
			+ "50A2211BDC83B4C1009600DCAD956182B5B5639B860"
			+ "E8FC583A4A482FAC45E8CBA97996737B4E376C884C8"
			+ "0BFD7D010";
	
	/**
	 * simple test example for Blake2b
	 * one vector from "https://github.com/BLAKE2/"
	 */
	@Test
	public void testsimple() {
		
		byte[] anyByteArray1 = helper.hex2bytes(input1);
		byte[] exp = helper.hex2bytes(hash1);
		byte[] keyInBytes = helper.hex2bytes(key);
		
		Blake2b b = new Blake2b(keyInBytes);
		b.update( anyByteArray1 );
		byte[] hash = new byte[64];
		hash = b.doFinal();
		  
		assertTrue(helper.bytes2hex(hash).equals(helper.bytes2hex(exp)));
	}
	
	@Test
	public void testsimple2 () {
		byte[] anyByteArray1 = helper.hex2bytes(input1);
		
		byte[] exp = helper.hex2bytes(hash1);
		byte[] keyInBytes = helper.hex2bytes(key);
		
		Blake2b b = new Blake2b(keyInBytes);
		b.update( anyByteArray1 );
		byte[] hash = new byte[64];
		hash = b.doFinal();
		  
		assertTrue(helper.bytes2hex(hash).equals(helper.bytes2hex(exp)));
	}
	
	@Test
	public void testZeroes(){
		byte[] anyByteArray1 = helper.hex2bytes(zeroes);
		byte[] exp = helper.hex2bytes(zeroesRes);
		
		Blake2b b = new Blake2b();
		b.update( anyByteArray1 );
		byte[] hash = new byte[64];
		hash = b.doFinal();
		
		assertTrue(helper.bytes2hex(hash).equals(helper.bytes2hex(exp)));
	}
	
	@Test
	public void testZeroes2(){
		byte[] anyByteArray1 = helper.hex2bytes(zeroes2);
		byte[] exp = helper.hex2bytes(zeroesRes2);
		
		Blake2b b = new Blake2b();
		b.update( anyByteArray1 );
		byte[] hash = new byte[64];
		hash = b.doFinal();
		
//		System.out.println(helper.bytes2hex(hash));
		
		assertTrue(helper.bytes2hex(hash).equals(helper.bytes2hex(exp)));
	}
	
	/**
	 * parsing and testing against all official test vectors 
	 * from "https://github.com/BLAKE2/"
	 */
	@Test
	public void testAllVectors() {
		
		JSONArray jsonObj = jsonParser(fileBlake2b);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String in = inputs.getString("in");
			String k = inputs.getString("key");
			
			String res = outputs.getString("res");
			
			byte[] input = helper.hex2bytes(in);
			byte[] key   = helper.hex2bytes(k);
			byte[] hash  = helper.hex2bytes(res);
		
			Blake2b b = new Blake2b(key);
			b.update(input);
			byte[] output = new byte[64];
			output = b.doFinal();
			
			assertTrue(helper.bytes2hex(hash).equals(helper.bytes2hex(output)));
		}
	}
	
	/**
	 * Test output Length of h with keyed hashing
	 */
	@Test
	public void testOutputLength1(){
		byte[] anyByteArray1 = helper.hex2bytes(input1);
		byte[] keyInBytes = helper.hex2bytes(key);
		
		Blake2b b = new Blake2b(keyInBytes);
		b.update( anyByteArray1 );
		byte[] hash = new byte[64];
		hash = b.doFinal();
		
		assertTrue(hash.length==b.getOutputSize());
	}
	
	/**
	 * Test output Length of h without keyed hashing
	 */
	@Test
	public void testOutputLength2(){
		byte[] anyByteArray1 = helper.hex2bytes(input1);
		
		Blake2b b = new Blake2b();
		b.update( anyByteArray1 );
		byte[] hash = new byte[64];
		hash = b.doFinal();
		
		assertTrue(hash.length==b.getOutputSize());
	}
	
	/**
	 * Test output Length of h without key and
	 * empty input
	 */
	@Test
	public void testOutputLength3(){
		Blake2b b = new Blake2b();
		byte[] hash = new byte[64];
		hash = b.doFinal();
		
		assertTrue(hash.length==b.getOutputSize());
	}
	
	@Test
	public void testBlake2bConstructor(){
		byte[] key = {};
		byte[] _salt = new byte[16];
		int _digestLength = 1;
		byte[]_personalization = new byte[16];
		
		new Blake2b(key, _digestLength, _salt, _personalization);
		
		byte[] act = {};
		byte[] exp = {};
		
		assertArrayEquals(exp, act);
	}
	
		
//====================================================================
// Helper functions:
	
	/**
	 * Helper-function
	 * reads test vector file
	 * creates test vector object 
	 */
	
	private JSONArray jsonParser(File f){
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
}
