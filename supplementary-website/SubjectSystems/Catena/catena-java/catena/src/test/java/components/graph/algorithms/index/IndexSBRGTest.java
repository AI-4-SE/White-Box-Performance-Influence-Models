package test.java.components.graph.algorithms.index;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.graph.GraphInterface;
import main.java.components.graph.algorithms.GenericGraph;
import main.java.components.graph.algorithms.IdxInterface;
import main.java.components.graph.algorithms.index.IndexGRG;
import main.java.components.graph.algorithms.index.IndexSBRG;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Blake2b;
import main.java.components.hash.algorithms.Blake2b_1;

public class IndexSBRGTest {
	Helper helper = new Helper();

	private String pathSbrgIndex = "src/test/resources/testvectors/sbrgIndex.json";
	private File fileSbrgIndex = new File(pathSbrgIndex);
	
	private String pathSbrgAny = "src/test/resources/testvectors/sbrgAny.json";
	private File fileSbrgAny = new File(pathSbrgAny);
	
	private String pathSbrgAnyFull = "src/test/resources/testvectors/sbrgAnyFull.json";
	private File fileSbrgAnyFull = new File(pathSbrgAnyFull);
	

	@Test
	public void testIndexing(){
		JSONArray jsonObj = helper.jsonParserArr(fileSbrgIndex);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			long index = inputs.getLong("index");
			int g = inputs.getInt("g");
			int c = inputs.getInt("c");

			long exp = outputs.getLong("res");
			
			IdxInterface idx = new IndexGRG(c);
			long act = idx.getIndex(index, g);
			
			assertEquals(exp, act);
		}
	}
	
	@Test
	public void testGrgAny(){
		JSONArray jsonObj = helper.jsonParserArr(fileSbrgAny);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int garlic = inputs.getInt("garlic");
			String state = inputs.getString("state");
			int lambda = inputs.getInt("lambda");
			int c = inputs.getInt("c");
			
			GraphInterface graph = new GenericGraph();
			
			HashInterface h = new Blake2b();
			HashInterface hPrime = new Blake2b_1();
			IdxInterface idx = new IndexSBRG(c);
			
			graph.setH(h);
			graph.setHPrime(hPrime);
			graph.setIndexing(idx);
			
			byte[] stateArr = helper.hex2bytes(state);
			byte[][] twoDimStateArr = helper.oneDimByteArrToTwoDim(stateArr, 64);

			String res = outputs.getString("res");
			
			byte[][] graphOutput = graph.graph(garlic, twoDimStateArr, lambda);
			byte[] output = helper.twoDimByteArrayToOne(graphOutput);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testGrgAnyFull(){
		JSONArray jsonObj = helper.jsonParserArr(fileSbrgAnyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int garlic = inputs.getInt("garlic");
			String state = inputs.getString("state");
			int lambda = inputs.getInt("lambda");
			int c = inputs.getInt("c");
			
			GraphInterface graph = new GenericGraph();
			
			HashInterface h = new Blake2b();
			HashInterface hPrime = new Blake2b();
			IdxInterface idx = new IndexSBRG(c);
			
			graph.setH(h);
			graph.setHPrime(hPrime);
			graph.setIndexing(idx);
			
			byte[] stateArr = helper.hex2bytes(state);
			byte[][] twoDimStateArr = helper.oneDimByteArrToTwoDim(stateArr, 64);

			String res = outputs.getString("res");
			
			byte[][] graphOutput = graph.graph(garlic, twoDimStateArr, lambda);
			byte[] output = helper.twoDimByteArrayToOne(graphOutput);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testIndexSBRG0(){
		int inpint = 0; 
		long inplong = 0;
		IdxInterface idx = new IndexSBRG(2);
		long actual = idx.getIndex(inplong, inpint);
		long expected = 0;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIndexSBRG1(){
		int input1 = 0;
		int input2 = 0;
		int input3 = 0;
		IdxInterface idx = new IndexSBRG(2);
		long actual = idx.getIndex(input1, input2, input3);
		long expected = 0;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIndexSBRG2(){
		long input1 = 0;
		long input2 = 0;
		int input3 = 0;
		IdxInterface idx = new IndexSBRG(2);
		long actual = idx.getIndex(input1, input2, input3);
		long expected = 0;
		assertEquals(expected, actual);
	}
}
