package test.java.components.graph.algorithms.index;

import static org.junit.Assert.*;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.graph.GraphInterface;
import main.java.components.graph.algorithms.DoubleButterflyGraph;
import main.java.components.graph.algorithms.IdxInterface;
import main.java.components.graph.algorithms.index.IndexDBG;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Blake2b;
import main.java.components.hash.algorithms.Blake2b_1;

public class IndexDBGTest {
	
	Helper helper = new Helper();

	private String pathDbhAny = "src/test/resources/testvectors/dbhAny.json";
	private File fileDbhAny = new File(pathDbhAny);
	
	private String pathDbhAnyFull = "src/test/resources/testvectors/dbhAnyFull.json";
	private File fileDbhAnyFull = new File(pathDbhAnyFull);
	
	private String pathDbhIndex = "src/test/resources/testvectors/dbhIndex.json";
	private File fileDbhIndex = new File(pathDbhIndex);

	@Test
	public void testDbhAny(){
		
		GraphInterface graph = new DoubleButterflyGraph();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b_1();
		IdxInterface idx = new IndexDBG();
		
		graph.setH(h);
		graph.setHPrime(hPrime);
		graph.setIndexing(idx);
		
		JSONArray jsonObj = helper.jsonParserArr(fileDbhAny);
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
	public void testDbhAnyFull(){
		
		GraphInterface graph = new DoubleButterflyGraph();
		
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		IdxInterface idx = new IndexDBG();
		
		graph.setH(h);
		graph.setHPrime(hPrime);
		graph.setIndexing(idx);
		
		JSONArray jsonObj = helper.jsonParserArr(fileDbhAnyFull);
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
	public void testDBHIndexing(){
		
		IdxInterface idx = new IndexDBG();
		
		JSONArray jsonObj = helper.jsonParserArr(fileDbhIndex);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int g = inputs.getInt("g");
			int j = inputs.getInt("j");
			int i_in = inputs.getInt("i");

			long exp = outputs.getLong("res");
			
			long act = idx.getIndex(g, j, i_in);
			
			assertEquals(exp, act);
		}
	}
}
