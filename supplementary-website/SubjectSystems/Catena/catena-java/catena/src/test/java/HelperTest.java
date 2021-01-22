package test.java;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.Helper;


public class HelperTest {
	
	private Helper helper = new Helper();
	
	String hash1XORING = "1568";
	String hash2XORING = "10ab";

	@Test
	public void testConcateByteArrays() {
		byte[] a = new byte[2];
		byte[] b = new byte[3];
		
		byte[] c = helper.concateByteArrays(a, b);
		
		assertEquals(c.length, a.length+b.length);
	}
	
	@Test
	public void testoneDimByteArrToTwoDim(){
		int len = 64;
		byte[] b = helper.hex2bytes("3d6d5d5b1b159210437827d4956b9775bfd3"
				+ "dab68f98e958cff9f64f62155168b432f1e248b41573918"
				+ "7dc7894e2c53167e0c3142dea5a8e4faac7d29fdac127b7"
				+ "7b4f34758083a59b44e9c22917d6ee598485f414500bbd0"
				+ "69c142177fba6b7815ab47fe7a6a01e7ab20f9217a5549a"
				+ "f3aaba255957bb979f46db5e2014cbb733e161e765fa206"
				+ "42ef4e1c18a8b7fee7339cd547aaf9bdbdbe5f11739118b"
				+ "b3f6c9abce48337e6cfaff168d7910fbcb0aa3c265ba426"
				+ "ff71ff5d2c2d74c5f769716a0ee29b7bf4ad1f1f9eab0a4"
				+ "58cf222a89f0a1822c5d21b3d7db9092aacabaee9cd97b6"
				+ "870987b9fddf97180cbd496c2e459eca027eae9f9798acc"
				+ "255835");
		
		byte[][] expected = new byte[4][64];
		expected[0] = helper.hex2bytes("3d6d5d5b1b159210437827d4956"
				+ "b9775bfd3dab68f98e958cff9f64f62155168b432f1e248b"
				+ "415739187dc7894e2c53167e0c3142dea5a8e4faac7d29fd"
				+ "ac127");
		expected[1] = helper.hex2bytes("b77b4f34758083a59b44e9c2291"
				+ "7d6ee598485f414500bbd069c142177fba6b7815ab47fe7a"
				+ "6a01e7ab20f9217a5549af3aaba255957bb979f46db5e201"
				+ "4cbb7");
		expected[2] = helper.hex2bytes("33e161e765fa20642ef4e1c18a8"
				+ "b7fee7339cd547aaf9bdbdbe5f11739118bb3f6c9abce483"
				+ "37e6cfaff168d7910fbcb0aa3c265ba426ff71ff5d2c2d74"
				+ "c5f76");
		expected[3] = helper.hex2bytes("9716a0ee29b7bf4ad1f1f9eab0a"
				+ "458cf222a89f0a1822c5d21b3d7db9092aacabaee9cd97b6"
				+ "870987b9fddf97180cbd496c2e459eca027eae9f9798acc2"
				+ "55835");

		byte[][] out = helper.oneDimByteArrToTwoDim(b, len);
		
		for (int i = 0; i < expected.length; ++i){
			assertArrayEquals(expected[i], out[i]);
		}
	}
	
	@Test
	public void testXor(){
		byte[] a = new byte[]{0,1,0,1};
		byte[] b = new byte[]{1,1,0,1};
		
		byte[] res = new byte[]{1,0,0,0};
		
		byte[] c = helper.xor(a, b);
		for (int i=0; i<res.length; ++i){
			assertEquals(res[i], c[i]);
		}
	}
	
	@Test
	public void testXorHash(){
		byte[] a = helper.hex2bytes(hash1XORING);
		byte[] b = helper.hex2bytes(hash1XORING);
		byte[] c = helper.hex2bytes(hash2XORING);
		
		byte[] x = helper.xor(a, b);
		byte[] y = helper.xor(a, c);
		
		byte[] resAB = helper.hex2bytes("0000");
		byte[] resAC = helper.hex2bytes("05c3");
		
		assertArrayEquals(resAB, x);
		assertArrayEquals(resAC, y);
	}
	
	@Test
	public void testIntToByteArray2(){
		int input = 12;
		byte[] exp = {0,12};
		
		byte[] act = helper.intToByteArray(input, 2);
		
		assertArrayEquals(exp, act);
	}
	
	@Test
	public void testIntToByteArray4(){
		int input = 12;
		byte[] exp = {0, 0, 0, 12};
		
		byte[] act = helper.intToByteArray(input, 4);
		
		assertArrayEquals(exp, act);
	}
	
	@Test
	public void testIntToByteArray0(){
		int input = 12;
		byte[] exp = new byte[1];
		
		byte[] act = helper.intToByteArray(input, 0);
		
		assertArrayEquals(exp, act);
	}
	
	@Test
	public void testBytesToInt(){
		byte[] bytes = {0,0,0,12};
		int act = helper.bytesToInt(bytes);
		int exp = 12;
		
		assertEquals(exp, act);
	}
	
	@Test
	public void testIntToBytes(){
		byte[] exp = {0,0,0,12};
		byte[] act = helper.intToBytes(12);

		assertArrayEquals(exp, act);
	}
	
	@Test
	public void testBytesToLong(){
		byte[] bytes = {0,0,0,0,0,0,0,12};
		long act = helper.bytesToLong(bytes);
		long exp = 12;
		
		assertEquals(exp, act);
	}
	
	@Test
	public void testLongToBytes(){
		byte[] exp = {0,0,0,0,0,0,0,12};
		byte[] act = helper.longToBytes(12);

		assertArrayEquals(exp, act);
	}
	
	@Test
	public void testPaddWithZero(){
		byte[] inp = {12,13,14};
		int l = 10;
		byte[] act = helper.paddWithZero(inp, l);
		byte[] exp = {12,13,14,0,0,0,0,0,0,0};
		
		assertArrayEquals(exp, act);
	}
	
	@Test
	public void testintToByteArrayLittleEndian(){
		int input = 12;
		int bytes = 4;
		byte[] actual = helper.intToByteArrayLittleEndian(input, bytes);
		byte[] expected = {12,0,0,0};
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testintToByteArrayLittleEndian0(){
		int input = 12;
		int bytes = 1;
		byte[] actual = helper.intToByteArrayLittleEndian(input, bytes);
		byte[] expected = new byte[1];
		assertArrayEquals(expected, actual);
	}
	
}
