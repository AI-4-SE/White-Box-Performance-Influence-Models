package test.java.components.phi.algorithms.index;

import static org.junit.Assert.*;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.phi.algorithms.IdxStateInterface;
import main.java.components.phi.algorithms.index.LSBIndex;

public class LSBIndexTest {

	Helper helper = new Helper();
	
	
	String pathlsbIndex = "src/test/resources/testvectors/lsbIndex.json";
	File fileLSBIndex = new File(pathlsbIndex); 
	
	@Test
	public void test() {
		IdxStateInterface idx = new LSBIndex();
	
		JSONArray jsonObj = helper.jsonParserArr(fileLSBIndex);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String state = inputs.getString("state");
			int numBits = inputs.getInt("num_bits");
			byte[] stateArr = helper.hex2bytes(state);

			int expRes = outputs.getInt("res");
			int actRes = idx.getLsbIndex(stateArr, numBits);
			
			assertEquals(expRes, actRes);
		}
	}

}
