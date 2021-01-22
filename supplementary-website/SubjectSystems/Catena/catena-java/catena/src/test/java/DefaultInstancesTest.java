package test.java;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Catena;
import main.java.DefaultInstances;
import main.java.Helper;

public class DefaultInstancesTest {
	
	Helper helper = new Helper();
	
//	private String pathDragonfly = "src/test/resources/catenaDragonfly.json";
//	private File fileDragonfly = new File(pathDragonfly);
//	
//	private String pathDragonflyFull = "src/test/resources/catenaDragonflyFull.json";
//	private File fileDragonflyFull = new File(pathDragonflyFull);
//	
//	private String pathButterfly = "src/test/resources/catenaButterfly.json";
//	private File butterflyFile = new File(pathButterfly);
//	
//	private String pathButterflyFull = "src/test/resources/catenaButterflyFull.json";
//	private File butterflyFileFull = new File(pathButterflyFull);
	
	
	private String pathDragonflyReduced = "src/test/resources/testvectors/catenaDragonflyReduced.json";
	private File fileDragonflyReduced = new File(pathDragonflyReduced);
	
	private String pathDragonflyFullReduced = "src/test/resources/testvectors/catenaDragonflyFullReduced.json";
	private File fileDragonflyFullReduced = new File(pathDragonflyFullReduced);
	
	private String pathButterflyReduced = "src/test/resources/testvectors/catenaButterflyReduced.json";
	private File butterflyFileReduced = new File(pathButterflyReduced);
	
	private String pathButterflyFullReduced = "src/test/resources/testvectors/catenaButterflyFullReduced.json";
	private File butterflyFileFullReduced = new File(pathButterflyFullReduced);
	
	
	private String pathcatenaHorseflyFullReduced ="src/test/resources/testvectors/catenaHorseflyFullReduced.json";
	private File filecatenaHorseflyFullReduced = new File(pathcatenaHorseflyFullReduced);
	
	private String pathcatenaLanternflyFullReduced ="src/test/resources/testvectors/catenaLanternflyFullReduced.json";
	private File filecatenaLanternflyFullReduced = new File(pathcatenaLanternflyFullReduced);
	
	private String pathcatenaMydasflyFullReduced ="src/test/resources/testvectors/catenaMydasflyFullReduced.json";
	private File filecatenaMydasflyFullReduced = new File(pathcatenaMydasflyFullReduced);
	
	private String pathcatenaStoneflyFullReduced ="src/test/resources/testvectors/catenaStoneflyFullReduced.json";
	private File filecatenaStoneflyFullReduced = new File(pathcatenaStoneflyFullReduced);
	
	
	private String pathcatenaHorseflyReduced ="src/test/resources/testvectors/catenaHorseflyReduced.json";
	private File filecatenaHorseflyReduced = new File(pathcatenaHorseflyReduced);
	
	private String pathcatenaLanternflyReduced ="src/test/resources/testvectors/catenaLanternflyReduced.json";
	private File filecatenaLanternflyReduced = new File(pathcatenaLanternflyReduced);
	
	private String pathcatenaMydasflyReduced ="src/test/resources/testvectors/catenaMydasflyReduced.json";
	private File filecatenaMydasflyReduced = new File(pathcatenaMydasflyReduced);
	
	private String pathcatenaStoneflyReduced ="src/test/resources/testvectors/catenaStoneflyReduced.json";
	private File filecatenaStoneflyReduced = new File(pathcatenaStoneflyReduced);
	
	@Test
	public void testcatenaHorseflyReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initHorsefly();
		
		c.setGHigh(13);
		c.setGLow(13);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaHorseflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaLanternflyReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initLanternfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaLanternflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaMydasflyReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initMydasfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaMydasflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaStoneflyReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initStonefly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaStoneflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaHorseflyFullReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initHorseflyFull();
		
		c.setGHigh(13);
		c.setGLow(13);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaHorseflyFullReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaLanternflyFullReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initLanternflyFull();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaLanternflyFullReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaMydasflyFullReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initMydasflyFull();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaMydasflyFullReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testcatenaStoneflyFullReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initStoneflyFull();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(filecatenaStoneflyFullReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			
			assertEquals(expectedResult, actualResult);
		}
	}

//	@Test
//	public void testButterfly(){
//		DefaultInstances defaultInstance = new DefaultInstances();
//		Catena c = defaultInstance.initButterfly();
//		
//		byte[] pwd;
//		byte[] salt;
//		byte[] gamma;
//		byte[] aData;
//		int outputLength;
//		
//		String actualResult;
//		String expectedResult;
//		
//		JSONArray jsonObj = helper.jsonParserArr(butterflyFile);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			pwd = helper.hex2bytes(inputs.getString("pwd"));
//			salt = helper.hex2bytes(inputs.getString("salt")); 
//			gamma = helper.hex2bytes(inputs.getString("gamma"));
//			aData = helper.hex2bytes(inputs.getString("aData"));
//			outputLength = inputs.getInt("outputLength");
//
//			expectedResult = outputs.getString("res");
//			
//			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
//			assertEquals(expectedResult, actualResult.toLowerCase());
//		}
//		
//	}
//
//	@Test
//	public void testButterflyFull(){
//		DefaultInstances defaultInstance = new DefaultInstances();
//		Catena c = defaultInstance.initButterflyFull();
//		
//		JSONArray jsonObj = helper.jsonParserArr(butterflyFileFull);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
//			byte[] salt = helper.hex2bytes(inputs.getString("salt")); 
//			byte[] gamma = helper.hex2bytes(inputs.getString("gamma"));
//			byte[] aData = helper.hex2bytes(inputs.getString("aData"));
//			int outputLength = inputs.getInt("outputLength");
//
//			String expectedResult = outputs.getString("res");
//			
//			String actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
//			assertEquals(expectedResult, actualResult.toLowerCase());
//		}
//	}
//	
//	@Test
//	public void testDragonfly(){
//		DefaultInstances defaultInstance = new DefaultInstances();
//		Catena c = defaultInstance.initDragonfly();
//		
//		JSONArray jsonObj = helper.jsonParserArr(fileDragonfly);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
//			byte[] salt = helper.hex2bytes(inputs.getString("salt")); 
//			byte[] gammaValue = helper.hex2bytes(inputs.getString("gamma"));
//			byte[] publicInput = helper.hex2bytes(inputs.getString("aData"));
//			
//			int m = inputs.getInt("outputLength");
//
//			String expectedResult = outputs.getString("res");
//			
//			byte[] actualResult = c.catena(pwd, salt, publicInput, gammaValue, m);
//			
//			assertEquals(expectedResult, helper.bytes2hex(actualResult).toLowerCase());
//		}
//	}
//	
//	@Test
//	public void testDragonflyFull(){
//		DefaultInstances defaultInstance = new DefaultInstances();
//		Catena c = defaultInstance.initDragonflyFull();
//		
//		byte[] pwd;
//		byte[] salt;
//		byte[] publicInput;
//		byte[] gammaValue;
//		int m;
//		
//		byte[] actualResult;
//		String expectedResult;
//		
//		JSONArray jsonObj = helper.jsonParserArr(fileDragonflyFull);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			pwd = helper.hex2bytes(inputs.getString("pwd"));
//			salt = helper.hex2bytes(inputs.getString("salt")); 
//			gammaValue = helper.hex2bytes(inputs.getString("gamma"));
//			publicInput = helper.hex2bytes(inputs.getString("aData"));
//			m = inputs.getInt("outputLength");
//
//			expectedResult = outputs.getString("res");
//			
//			actualResult = c.catena(pwd, salt, publicInput, gammaValue, m);
//			
//			assertEquals(expectedResult, helper.bytes2hex(actualResult).toLowerCase());
//		}
//	}

	
	@Test
	public void testButterflyReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initButterfly();
		
		// reduced gHigh
		c.setGLow(9);
		c.setGHigh(9);
		
		byte[] pwd;
		byte[] salt;
		byte[] gamma;
		byte[] aData;
		int outputLength;
		
		String actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(butterflyFileReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gamma = helper.hex2bytes(inputs.getString("gamma"));
			aData = helper.hex2bytes(inputs.getString("aData"));
			outputLength = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			
			actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
		
	}
	
	@Test
	public void testButterflyFullReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initButterflyFull();
		
		// reduced gHigh
		c.setGLow(9);
		c.setGHigh(9);
		
		JSONArray jsonObj = helper.jsonParserArr(butterflyFileFullReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
			byte[] salt = helper.hex2bytes(inputs.getString("salt")); 
			byte[] gamma = helper.hex2bytes(inputs.getString("gamma"));
			byte[] aData = helper.hex2bytes(inputs.getString("aData"));
			int outputLength = inputs.getInt("outputLength");

			String expectedResult = outputs.getString("res");
			
			String actualResult = helper.bytes2hex(c.catena(pwd, salt, aData, gamma, outputLength));
			assertEquals(expectedResult, actualResult.toLowerCase());
		}
	}
	
	@Test
	public void testDragonflyReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonfly();
		
		// reduced gHigh
		c.setGLow(14);
		c.setGHigh(14);
		
		JSONArray jsonObj = helper.jsonParserArr(fileDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
			byte[] salt = helper.hex2bytes(inputs.getString("salt")); 
			byte[] gammaValue = helper.hex2bytes(inputs.getString("gamma"));
			byte[] publicInput = helper.hex2bytes(inputs.getString("aData"));
			
			int m = inputs.getInt("outputLength");

			String expectedResult = outputs.getString("res");
			
			byte[] actualResult = c.catena(pwd, salt, publicInput, gammaValue, m);
			
			assertEquals(expectedResult, helper.bytes2hex(actualResult).toLowerCase());
		}
	}
	
	@Test
	public void testDragonflyFullReduced(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonflyFull();
		
		// reduced gHigh
		c.setGLow(14);
		c.setGHigh(14);
		
		byte[] pwd;
		byte[] salt;
		byte[] publicInput;
		byte[] gammaValue;
		int m;
		
		byte[] actualResult;
		String expectedResult;
		
		JSONArray jsonObj = helper.jsonParserArr(fileDragonflyFullReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			pwd = helper.hex2bytes(inputs.getString("pwd"));
			salt = helper.hex2bytes(inputs.getString("salt")); 
			gammaValue = helper.hex2bytes(inputs.getString("gamma"));
			publicInput = helper.hex2bytes(inputs.getString("aData"));
			m = inputs.getInt("outputLength");

			expectedResult = outputs.getString("res");
			
			actualResult = c.catena(pwd, salt, publicInput, gammaValue, m);
			
			assertEquals(expectedResult, helper.bytes2hex(actualResult).toLowerCase());
		}
	}
}
