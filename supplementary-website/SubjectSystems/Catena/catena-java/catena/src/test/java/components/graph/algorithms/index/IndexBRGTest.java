package test.java.components.graph.algorithms.index;

import static org.junit.Assert.*;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.graph.algorithms.GenericGraph;
import main.java.components.graph.algorithms.IdxInterface;
import main.java.components.graph.algorithms.index.IndexBRG;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Blake2b;
import main.java.components.hash.algorithms.Blake2b_1;

public class IndexBRGTest {
	
	private IdxInterface idxBrg = new IndexBRG();
	
	private String pathBrgAny = "src/test/resources/testvectors/brgAny.json";
	private File fileBrgAny = new File(pathBrgAny);
	
	private String pathbrgAnyFull = "src/test/resources/testvectors/brgAnyFull.json";
	private File filebrgAnyFull = new File(pathbrgAnyFull);
	
	private String pathbrgIndex = "src/test/resources/testvectors/brgIndex.json";
	private File filebrgIndex = new File(pathbrgIndex);
	
	
	private Helper helper = new Helper();

	@Test
	public void testBRGAny(){
		GenericGraph graph = new GenericGraph();
		
		IdxInterface idx = new IndexBRG();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b_1();
		
		graph.setH(h);
		graph.setHPrime(hPrime);
		graph.setIndexing(idx);
		
		JSONArray jsonObj = helper.jsonParserArr(fileBrgAny);
		for (int i = 0; i<jsonObj.length(); ++i ){
			
			hPrime.reset();
			
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int garlic = inputs.getInt("garlic");
			String state = inputs.getString("state");
			int lambda = inputs.getInt("lambda");
			
			byte[] stateArr = helper.hex2bytes(state);
			byte[][] twoDimStateArr = helper.oneDimByteArrToTwoDim(stateArr, 64);

			String res = outputs.getString("res");

			byte[][] graphOutput = graph.graph(garlic, twoDimStateArr, lambda);
			byte[] output = helper.twoDimByteArrayToOne(graphOutput);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testBRGAnyFull(){
		GenericGraph graph = new GenericGraph();
		
		IdxInterface idx = new IndexBRG();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		
		graph.setH(h);
		graph.setHPrime(hPrime);
		graph.setIndexing(idx);
		
		JSONArray jsonObj = helper.jsonParserArr(filebrgAnyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int garlic = inputs.getInt("garlic");
			String state = inputs.getString("state");
			int lambda = inputs.getInt("lambda");
			
			byte[] stateArr = helper.hex2bytes(state);
			byte[][] twoDimStateArr = helper.oneDimByteArrToTwoDim(stateArr, 64);

			String res = outputs.getString("res");

			byte[][] graphOutput = graph.graph(garlic, twoDimStateArr, lambda);
			byte[] output = helper.twoDimByteArrayToOne(graphOutput);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test 
	public void testBRGIndex(){
		
		
		JSONArray jsonObj = helper.jsonParserArr(filebrgIndex);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int index = inputs.getInt("index");
			int g = inputs.getInt("g");
			
			int exp = outputs.getInt("res");
			
			int act = idxBrg.getIndex(index, g);
			
			assertEquals(exp, act);
		}
	}
	
	@Test
	public void test() {
		assertEquals(12, idxBrg.getIndex(3, 4));
		assertEquals(0, idxBrg.getIndex(0, 4));
		assertEquals(1, idxBrg.getIndex(1, 1));
		assertEquals(15, idxBrg.getIndex(15, 4));
		assertEquals(1, idxBrg.getIndex(8, 4));
		assertEquals(4, idxBrg.getIndex(4, 5));
		assertEquals(86, idxBrg.getIndex(53, 7));
		assertEquals(2688, idxBrg.getIndex(42, 13));
		
//		System.out.println(idxBrg.getIndex(20, 21));
//		System.out.println(idxBrg.getIndex((long)2097151, 21));
//		
		assertEquals(12, idxBrg.getIndex((long)3, 4));
		assertEquals(0, idxBrg.getIndex((long)0, 4));
		assertEquals(1, idxBrg.getIndex((long)1, 1));
		assertEquals(15, idxBrg.getIndex((long)15, 4));
		assertEquals(1, idxBrg.getIndex((long)8, 4));
		assertEquals(4, idxBrg.getIndex((long)4, 5));
		assertEquals(86, idxBrg.getIndex((long)53, 7));
		assertEquals(2688, idxBrg.getIndex((long)42, 13));
	}
	
	@Test
	public void testIndexBRG1(){
		int input1 = 0;
		int input2 = 0;
		int input3 = 0;
		IdxInterface idx = new IndexBRG();
		long actual = idx.getIndex(input1, input2, input3);
		long expected = -1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIndexBRG2(){
		long input1 = 0;
		long input2 = 0;
		int input3 = 0;
		IdxInterface idx = new IndexBRG();
		long actual = idx.getIndex(input1, input2, input3);
		long expected = -1;
		assertEquals(expected, actual);
	}
}
