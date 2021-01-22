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
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Blake2b;
import main.java.components.hash.algorithms.Blake2b_1;

public class IndexGRGTest {

	Helper helper = new Helper();

	private String pathGrgIndex = "src/test/resources/testvectors/grgIndex.json";
	private File fileGrgIndex = new File(pathGrgIndex);
	
	private String pathGrgAny = "src/test/resources/testvectors/grgAny.json";
	private File fileGrgAny = new File(pathGrgAny);
	
	private String pathGrgAnyFull = "src/test/resources/testvectors/grgAnyFull.json";
	private File fileGrgAnyFull = new File(pathGrgAnyFull);
	
	@Test
	public void testIndexGRG(){
		JSONArray jsonObj = helper.jsonParserArr(fileGrgIndex);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			long index = inputs.getLong("index");
			int g = inputs.getInt("g");
			int l = inputs.getInt("l");

			long exp = outputs.getLong("res");
			
			IdxInterface idx = new IndexGRG(l);
			long act = idx.getIndex(index, g);
			
			assertEquals(exp, act);
		}
	}
	
	@Test
	public void testGrgAny(){
		JSONArray jsonObj = helper.jsonParserArr(fileGrgAny);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int garlic = inputs.getInt("garlic");
			String state = inputs.getString("state");
			int lambda = inputs.getInt("lambda");
			int l = inputs.getInt("l");
			
			GraphInterface graph = new GenericGraph();
			
			HashInterface h = new Blake2b();
			HashInterface hPrime = new Blake2b_1();
			IdxInterface idx = new IndexGRG(l);
			
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
		JSONArray jsonObj = helper.jsonParserArr(fileGrgAnyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int garlic = inputs.getInt("garlic");
			String state = inputs.getString("state");
			int lambda = inputs.getInt("lambda");
			int l = inputs.getInt("l");
			
			GraphInterface graph = new GenericGraph();
			
			HashInterface h = new Blake2b();
			HashInterface hPrime = new Blake2b();
			IdxInterface idx = new IndexGRG(l);
			
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
	public void testIndexGRG1(){
		int input1 = 0;
		int input2 = 0;
		int input3 = 0;
		IdxInterface idx = new IndexGRG(2);
		long actual = idx.getIndex(input1, input2, input3);
		long expected = -1;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testIndexGRG2(){
		long input1 = 0;
		long input2 = 0;
		int input3 = 0;
		IdxInterface idx = new IndexGRG(2);
		long actual = idx.getIndex(input1, input2, input3);
		long expected = -1;
		assertEquals(expected, actual);
	}
}
