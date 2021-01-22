package test.java.components.gamma.algorithms;

import static org.junit.Assert.*;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Helper;
import main.java.components.gamma.GammaInterface;
import main.java.components.gamma.algorithms.SaltMix;
import main.java.components.hash.HashInterface;
import main.java.components.hash.algorithms.Blake2b;

public class SaltMixTest {
	
	private String path = "src/test/resources/testvectors/saltmixAnyFull.json";
	private File testFileSaltMix = new File(path);
	
	Helper helper = new Helper();

	@Test
	public void testSaltMix() {
		HashInterface h = new Blake2b();
		HashInterface hPrime = new Blake2b();
		GammaInterface gamma = new SaltMix();
		gamma.setH(h);
		gamma.setHPrime(hPrime);
		
		
		JSONArray jsonObj = helper.jsonParserArr(testFileSaltMix);
		
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs  = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String hashIn = inputs.getString("hash");
			byte[][] in = helper.oneDimByteArrToTwoDim(helper.hex2bytes(hashIn), 64);
			int garlic = inputs.getInt("garlic");
			String salt = inputs.getString("salt");
			
			String exp = outputs.getString("output_hash");
			
			byte[] s = helper.hex2bytes(salt);			
			
			byte[][] output = gamma.gamma(garlic, in, s);
			
			assertTrue(exp.equals(helper.bytes2hex(helper.twoDimByteArrayToOne(output)).toLowerCase()));
		}
	}
}
