package test.java.components.hash.algorithms;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Blake2b_1;

public class Blake2b_1Test {
	
	Helper helper = new Helper();
	
	private static final String testVectorSource = 
			"src/test/resources/testvectors/blake2b1.json";
	private static final File file = new File(testVectorSource);
	
	@Test
	public void testAllVectors() {
		Helper helper = new Helper();		
		HashInterface hPrime = new Blake2b_1();
		
		byte[] actualResult = new byte[64];
		
		boolean reset = false;
		int r = 0;
		
		JSONArray jsonObj = jsonParser(file);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			reset = inputs.getBoolean("reset");
			byte[] input = helper.hex2bytes(inputs.getString("data"));
			r = inputs.getInt("r");
			
			byte[] output = helper.hex2bytes(outputs.getString("res"));
			
			if (reset){
				hPrime.reset();
			}
			
			hPrime.setVertexIndex(r);
			hPrime.update(input);
			actualResult = hPrime.doFinal();
			
			assertTrue(helper.bytes2hex(actualResult).
					equals(helper.bytes2hex(output)));
		}
	}

//		------------------- Helper Area
		
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
