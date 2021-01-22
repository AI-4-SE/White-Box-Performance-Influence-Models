package test.java.components.hash.algorithms;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Argon2CF;

public class Argon2CFTest {

	Helper helper = new Helper();
	
	String pathGL = "src/test/resources/testvectors/cfArgon2Gl.json";
	File fileGL = new File(pathGL);
	
	String pathGB = "src/test/resources/testvectors/cfArgon2Gb.json";
	File fileGB = new File(pathGB);
	
	@Test
	public void testGL(){
		
		JSONArray jsonObj = helper.jsonParserArr(fileGL);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String func = inputs.getString("mixFunc");
			String dataString = inputs.getString("data");

			String res = outputs.getString("res");
			
			boolean useGL = true;
			
			if (func.equals("G_{L}")){
				useGL = true;
			} else {
				useGL = false;
			}
			
			HashInterface h = new Argon2CF(useGL);
			h.update(helper.hex2bytes(dataString));
			byte[] output = h.doFinal();
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	
	@Test
	public void testGB(){
		
		JSONArray jsonObj = helper.jsonParserArr(fileGB);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String func = inputs.getString("mixFunc");
			String dataString = inputs.getString("data");

			String res = outputs.getString("res");
			
			boolean useGL = false;
			
			if (func.equals("G_{B}")){
				useGL = false;
			} else {
				useGL = true;
			}
			
			HashInterface h = new Argon2CF(useGL);
			h.update(helper.hex2bytes(dataString));
			byte[] output = h.doFinal();
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
}
