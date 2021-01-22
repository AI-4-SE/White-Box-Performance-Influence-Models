package test.java;

import static org.junit.Assert.*;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import main.java.Catena;
import main.java.Catena.POWstruct;
import main.java.DefaultInstances;
import main.java.Helper;

public class CatenaTest {
	
	Helper helper = new Helper();
	
	private String pathHInit = "src/test/resources/testvectors/hInitAnyFull.json";
	private File fileHInit = new File(pathHInit);
	
	
	private String pathtweakButterfly = "src/test/resources/testvectors/tweakButterfly.json";
	private File filetweakButterfly = new File(pathtweakButterfly);
	
	private String pathtweakButterflyFull = "src/test/resources/testvectors/tweakButterflyFull.json";
	private File filetweakButterflyFull = new File(pathtweakButterflyFull);
	
	private String pathtweakDragonfly = "src/test/resources/testvectors/tweakDragonfly.json";
	private File filetweakDragonfly = new File(pathtweakDragonfly);
	
	private String pathtweakDragonflyFull = "src/test/resources/testvectors/tweakDragonflyFull.json";
	private File filetweakDragonflyFull = new File(pathtweakDragonflyFull);
	
	
	private String pathFlapDragonfly = "src/test/resources/testvectors/flapDragonfly.json";
	private File fileFlapDragonfly = new File(pathFlapDragonfly);
	
	private String pathFlapDragonflyFull = "src/test/resources/testvectors/flapDragonflyFull.json";
	private File fileFlapDragonflyFull = new File(pathFlapDragonflyFull);
	
	private String pathFlapButterfly = "src/test/resources/testvectors/flapButterfly.json";
	private File fileFlapButterfly = new File(pathFlapButterfly);
	
	private String pathFlapButterflyFull = "src/test/resources/testvectors/flapButterflyFull.json";
	private File fileFlapButterflyFull = new File(pathFlapButterflyFull);
	
	
	
	private String pathciUpdateDragonflyReduced = "src/test/resources/testvectors/ciUpdateDragonflyReduced.json";
	private File fileciUpdateDragonflyReduced = new File(pathciUpdateDragonflyReduced);
	
	private String pathciUpdateKeyedDragonflyReduced = "src/test/resources/testvectors/ciUpdateKeyedDragonflyReduced.json";
	private File fileciUpdateKeyedDragonflyReduced = new File(pathciUpdateKeyedDragonflyReduced);
	
//	private String pathkeyDerivationButterfly = "src/test/resources/keyDerivationButterfly.json";
//	private File filekeyDerivationButterfly = new File(pathkeyDerivationButterfly);
//
//	private String pathkeyDerivationButterflyFull = "src/test/resources/keyDerivationButterflyFull.json";
//	private File filekeyDerivationButterflyFull = new File(pathkeyDerivationButterflyFull);
	
	private String pathkeyDerivationButterflyReduced = "src/test/resources/testvectors/keyDerivationButterflyReduced.json";
	private File filekeyDerivationButterflyReduced = new File(pathkeyDerivationButterflyReduced);
	
//	private String pathkeyDerivationDragonfly = "src/test/resources/keyDerivationDragonfly.json";
//	private File filekeyDerivationDragonfly = new File(pathkeyDerivationDragonfly);
//	
//	private String pathkeyDerivationDragonflyFull = "src/test/resources/keyDerivationDragonflyFull.json";
//	private File filekeyDerivationDragonflyFull = new File(pathkeyDerivationDragonflyFull);
	
	private String pathkeyDerivationDragonflyReduced = "src/test/resources/testvectors/keyDerivationDragonflyReduced.json";
	private File filekeyDerivationDragonflyReduced = new File(pathkeyDerivationDragonflyReduced);
	
//	private String pathkeyedHashButterfly = "src/test/resources/keyedHashButterfly.json";
//	private File filekeyedHashButterfly = new File(pathkeyedHashButterfly);
//	
//	private String pathkeyedHashButterflyFull = "src/test/resources/keyedHashButterflyFull.json";
//	private File filekeyedHashButterflyFull = new File(pathkeyedHashButterflyFull);
	
	private String pathkeyedHashButterflyReduced = "src/test/resources/testvectors/keyedHashButterflyReduced.json";
	private File filekeyedHashButterflyReduced = new File(pathkeyedHashButterflyReduced);
	
//	private String pathkeyedHashDragonfly = "src/test/resources/keyedHashDragonfly.json";
//	private File filekeyedHashDragonfly = new File(pathkeyedHashDragonfly);
//	
//	private String pathkeyedHashDragonflyFull = "src/test/resources/keyedHashDragonflyFull.json";
//	private File filekeyedHashDragonflyFull = new File(pathkeyedHashDragonflyFull);
	
	private String pathkeyedHashDragonflyReduced = "src/test/resources/testvectors/keyedHashDragonflyReduced.json";
	private File filekeyedHashDragonflyReduced = new File(pathkeyedHashDragonflyReduced);
	
	private String pathserverReliefClientButterflyReducedDifferentG = "src/test/resources/testvectors/serverReliefClientButterflyReducedDifferentG.json";
	private File fileserverReliefClientButterflyReducedDifferentG = new File(pathserverReliefClientButterflyReducedDifferentG);
	
	private String pathserverReliefClientButterflyReduced = "src/test/resources/testvectors/serverReliefClientButterflyReduced.json";
	private File fileserverReliefClientButterflyReduced = new File(pathserverReliefClientButterflyReduced);
	
	private String pathserverReliefClientDragonflyReduced = "src/test/resources/testvectors/serverReliefClientDragonflyReduced.json";
	private File fileserverReliefClientDragonflyReduced = new File(pathserverReliefClientDragonflyReduced);
	
	private String pathserverReliefServerButterflyReduced = "src/test/resources/testvectors/serverReliefServerButterflyReduced.json";
	private File fileserverReliefServerButterflyReduced = new File(pathserverReliefServerButterflyReduced);
	
	private String pathserverReliefServerDragonflyReduced = "src/test/resources/testvectors/serverReliefServerDragonflyReduced.json";
	private File fileserverReliefServerDragonflyReduced = new File(pathserverReliefServerDragonflyReduced);
	
	private String pathproofOfWorkServerSaltButterflyReduced = "src/test/resources/testvectors/proofOfWorkServerSaltButterflyReduced.json";
	private File fileproofOfWorkServerSaltButterflyReduced = new File(pathproofOfWorkServerSaltButterflyReduced);
	
	private String pathproofOfWorkServerPwdButterflyReduced = "src/test/resources/testvectors/proofOfWorkServerPwdButterflyReduced.json";
	private File fileproofOfWorkServerPwdButterflyReduced = new File(pathproofOfWorkServerPwdButterflyReduced);
	
	private String pathproofOfWorkClientPwdButterflyReduced = "src/test/resources/testvectors/proofOfWorkClientPwdButterflyReduced.json";
	private File fileproofOfWorkClientPwdButterflyReduced = new File(pathproofOfWorkClientPwdButterflyReduced);
	
	private String pathproofOfWorkClientSaltButterflyReduced = "src/test/resources/testvectors/proofOfWorkClientSaltButterflyReduced.json";
	private File fileproofOfWorkClientSaltButterflyReduced = new File(pathproofOfWorkClientSaltButterflyReduced);
	
	
	@Test
	public void testServerReliefServerButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(fileserverReliefServerButterflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String hash = inputs.getString("hash");
			int outputLength = inputs.getInt("outputLength");
			
			byte[] hashArr = helper.hex2bytes(hash);
			
			String res = outputs.getString("res");
			
			byte[] output = c.serverReliefServer(outputLength, hashArr);
			
			assertEquals(res,helper.bytes2hex(output).toLowerCase());
		}
	}
	
	@Test
	public void testServerReliefServerDragonflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initDragonfly();
		
		c.setGHigh(14);
		c.setGLow(14);
		
		JSONArray jsonObj = helper.jsonParserArr(fileserverReliefServerDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String hash = inputs.getString("hash");
			int outputLength = inputs.getInt("outputLength");
			
			byte[] hashArr = helper.hex2bytes(hash);
			
			String res = outputs.getString("res");
			
			byte[] output = c.serverReliefServer(outputLength, hashArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testServerReliefClientButterflyReduceddifferentG(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		JSONArray jsonObj = helper.jsonParserArr(fileserverReliefClientButterflyReducedDifferentG);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String ad = inputs.getString("aData");
			int outputLength = inputs.getInt("outputLength");
			int gLow = inputs.getInt("gLow");
			int gHigh = inputs.getInt("gHigh");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] adArr = helper.hex2bytes(ad);
			
			String res = outputs.getString("res");
			
			c.setGLow(gLow);
			c.setGHigh(gHigh);
			
			byte[] output = c.serverReliefClient(pwdArr, saltArr, adArr, outputLength, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testServerReliefClientButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(fileserverReliefClientButterflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String ad = inputs.getString("aData");
			int outputLength = inputs.getInt("outputLength");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] adArr = helper.hex2bytes(ad);
			
			String res = outputs.getString("res");
			
			byte[] output = c.serverReliefClient(pwdArr, saltArr, adArr, outputLength, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testServerReliefClientDragonflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initDragonfly();
		
		c.setGHigh(14);
		c.setGLow(14);
		
		JSONArray jsonObj = helper.jsonParserArr(fileserverReliefClientDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String ad = inputs.getString("aData");
			int outputLength = inputs.getInt("outputLength");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] adArr = helper.hex2bytes(ad);
			
			String res = outputs.getString("res");
			
			byte[] output = c.serverReliefClient(pwdArr, saltArr, adArr, outputLength, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	

//	@Test
//	public void testKeyedHashButterfly(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initButterfly();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyedHashButterfly);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String key = inputs.getString("key");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String ad = inputs.getString("aData");
//			int outputLength = inputs.getInt("outputLength");
//			String userID = inputs.getString("userID");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] keyArr = helper.hex2bytes(key);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] userIDArr = helper.hex2bytes(userID);
//			
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyedPasswordHashing(pwdArr, keyArr, saltArr, gammaArr, adArr, outputLength, userIDArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}

	
//	@Test
//	public void testKeyedHashButterflyFull(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initButterflyFull();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyedHashButterflyFull);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String key = inputs.getString("key");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String ad = inputs.getString("aData");
//			int outputLength = inputs.getInt("outputLength");
//			String userID = inputs.getString("userID");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] keyArr = helper.hex2bytes(key);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] userIDArr = helper.hex2bytes(userID);
//			
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyedPasswordHashing(pwdArr, keyArr, saltArr, gammaArr, adArr, outputLength, userIDArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}
	
	@Test
	public void testKeyedHashButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(filekeyedHashButterflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String key = inputs.getString("key");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String ad = inputs.getString("aData");
			int outputLength = inputs.getInt("outputLength");
			String userID = inputs.getString("userID");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] keyArr = helper.hex2bytes(key);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] adArr = helper.hex2bytes(ad);
			byte[] userIDArr = helper.hex2bytes(userID);
			
			String res = outputs.getString("res");
			
			byte[] output = c.keyedPasswordHashing(pwdArr, keyArr, saltArr, gammaArr, adArr, outputLength, userIDArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
//	@Test
//	public void testKeyedHashDragonfly(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initDragonfly();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyedHashDragonfly);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String key = inputs.getString("key");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String ad = inputs.getString("aData");
//			int outputLength = inputs.getInt("outputLength");
//			String userID = inputs.getString("userID");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] keyArr = helper.hex2bytes(key);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] userIDArr = helper.hex2bytes(userID);
//			
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyedPasswordHashing(pwdArr, keyArr, saltArr, gammaArr, adArr, outputLength, userIDArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}
	
//	@Test
//	public void testKeyedHashDragonflyFull(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initDragonflyFull();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyedHashDragonflyFull);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String key = inputs.getString("key");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String ad = inputs.getString("aData");
//			int outputLength = inputs.getInt("outputLength");
//			String userID = inputs.getString("userID");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] keyArr = helper.hex2bytes(key);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] userIDArr = helper.hex2bytes(userID);
//			
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyedPasswordHashing(pwdArr, keyArr, saltArr, gammaArr, adArr, outputLength, userIDArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}

	
	@Test
	public void testKeyedHashDragonflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initDragonfly();
		
		c.setGHigh(14);
		c.setGLow(14);
		
		JSONArray jsonObj = helper.jsonParserArr(filekeyedHashDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String key = inputs.getString("key");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String ad = inputs.getString("aData");
			int outputLength = inputs.getInt("outputLength");
			String userID = inputs.getString("userID");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] keyArr = helper.hex2bytes(key);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] adArr = helper.hex2bytes(ad);
			byte[] userIDArr = helper.hex2bytes(userID);
			
			String res = outputs.getString("res");
			
			byte[] output = c.keyedPasswordHashing(pwdArr, keyArr, saltArr, gammaArr, adArr, outputLength, userIDArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	

//	@Test
//	public void testKeyDerivationButterfly(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initButterfly();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyDerivationButterfly);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String ad = inputs.getString("ad");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String keyIdentifier = inputs.getString("key_identifier");
//			int outputLength = inputs.getInt("output_length");
//			int keySize = inputs.getInt("key_size");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] keyIdentifierArr = helper.hex2bytes(keyIdentifier);
//
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyDerivation(pwdArr, saltArr, adArr, gammaArr, outputLength, keySize, keyIdentifierArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}
//	


//	@Test
//	public void testKeyDerivationButterflyFull(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initButterflyFull();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyDerivationButterflyFull);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String ad = inputs.getString("ad");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String keyIdentifier = inputs.getString("key_identifier");
//			int outputLength = inputs.getInt("output_length");
//			int keySize = inputs.getInt("key_size");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] keyIdentifierArr = helper.hex2bytes(keyIdentifier);
//
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyDerivation(pwdArr, saltArr, adArr, gammaArr, outputLength, keySize, keyIdentifierArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}
	
	@Test
	public void testKeyDerivationButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGLow(9);
		c.setGHigh(9);
		
		JSONArray jsonObj = helper.jsonParserArr(filekeyDerivationButterflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String ad = inputs.getString("ad");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String keyIdentifier = inputs.getString("key_identifier");
			int outputLength = inputs.getInt("output_length");
			int keySize = inputs.getInt("key_size");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] adArr = helper.hex2bytes(ad);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] keyIdentifierArr = helper.hex2bytes(keyIdentifier);

			String res = outputs.getString("res");
			
			byte[] output = c.keyDerivation(pwdArr, saltArr, adArr, gammaArr, outputLength, keySize, keyIdentifierArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	
//	@Test
//	public void testKeyDerivationDragonfly(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initDragonfly();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyDerivationDragonfly);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String ad = inputs.getString("ad");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String keyIdentifier = inputs.getString("key_identifier");
//			int outputLength = inputs.getInt("output_length");
//			int keySize = inputs.getInt("key_size");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] keyIdentifierArr = helper.hex2bytes(keyIdentifier);
//
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyDerivation(pwdArr, saltArr, adArr, gammaArr, outputLength, keySize, keyIdentifierArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}
//	
//	@Test
//	public void testKeyDerivationDragonflyFull(){
//		DefaultInstances instance = new DefaultInstances();
//		Catena c = instance.initDragonflyFull();
//		
//		JSONArray jsonObj = helper.jsonParserArr(filekeyDerivationDragonflyFull);
//		for (int i = 0; i<jsonObj.length(); ++i ){
//			JSONObject o = (JSONObject) jsonObj.get(i);
//			
//			JSONObject inputs = o.getJSONObject("inputs");
//			JSONObject outputs = o.getJSONObject("outputs");
//			
//			String pwd = inputs.getString("pwd");
//			String ad = inputs.getString("ad");
//			String salt = inputs.getString("salt");
//			String gamma = inputs.getString("gamma");
//			String keyIdentifier = inputs.getString("key_identifier");
//			int outputLength = inputs.getInt("output_length");
//			int keySize = inputs.getInt("key_size");
//			
//			byte[] pwdArr = helper.hex2bytes(pwd);
//			byte[] adArr = helper.hex2bytes(ad);
//			byte[] saltArr = helper.hex2bytes(salt);
//			byte[] gammaArr = helper.hex2bytes(gamma);
//			byte[] keyIdentifierArr = helper.hex2bytes(keyIdentifier);
//
//			String res = outputs.getString("res");
//			
//			byte[] output = c.keyDerivation(pwdArr, saltArr, adArr, gammaArr, outputLength, keySize, keyIdentifierArr);
//			
//			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
//		}
//	}

	
	@Test
	public void testKeyDerivationDragonflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initDragonfly();
		
		c.setGHigh(14);
		c.setGLow(14);
		
		JSONArray jsonObj = helper.jsonParserArr(filekeyDerivationDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String pwd = inputs.getString("pwd");
			String ad = inputs.getString("ad");
			String salt = inputs.getString("salt");
			String gamma = inputs.getString("gamma");
			String keyIdentifier = inputs.getString("key_identifier");
			int outputLength = inputs.getInt("output_length");
			int keySize = inputs.getInt("key_size");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] adArr = helper.hex2bytes(ad);
			byte[] saltArr = helper.hex2bytes(salt);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] keyIdentifierArr = helper.hex2bytes(keyIdentifier);

			String res = outputs.getString("res");
			
			byte[] output = c.keyDerivation(pwdArr, saltArr, adArr, gammaArr, outputLength, keySize, keyIdentifierArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testCIUpdateDragonflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initDragonfly();
		
		int gOld = 14;
		
		c.setGHigh(gOld);
		c.setGLow(gOld);
		
		JSONArray jsonObj = helper.jsonParserArr(fileciUpdateDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String oldHash = inputs.getString("oldHash");
			int gNew = inputs.getInt("gNew");
			String gamma = inputs.getString("gamma");
			int outLen = inputs.getInt("outLen");
			
			byte[] oldHashArr = helper.hex2bytes(oldHash);
			byte[] gammaArr = helper.hex2bytes(gamma);

			String res = outputs.getString("res");
			
			byte[] output = new byte[0];
			try {
				output = c.clientIndependentUpdate(oldHashArr, gOld, gNew, gammaArr, outLen);
			} catch (Exception e) {
				System.out.println("Could not update Hash.");
				e.printStackTrace();
			}
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testCIUpdateKeyedDragonflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initDragonfly();
		
		int gOld = 14;
		
		c.setGHigh(gOld);
		c.setGLow(gOld);
		
		JSONArray jsonObj = helper.jsonParserArr(fileciUpdateKeyedDragonflyReduced);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			String oldHash = inputs.getString("oldHash");
			int gNew = inputs.getInt("gNew");
			String gamma = inputs.getString("gamma");
			int outLen = inputs.getInt("outLen");
			String key = inputs.getString("key");
			String userID = inputs.getString("userID");
			
			byte[] oldHashArr = helper.hex2bytes(oldHash);
			byte[] gammaArr = helper.hex2bytes(gamma);
			byte[] keyArr = helper.hex2bytes(key);
			byte[] userIDArr = helper.hex2bytes(userID);

			String res = outputs.getString("res");
			
			byte[] output = new byte[0];
			try {
				output = c.keyedClientIndependentUpdate(oldHashArr, gOld, gNew, gammaArr, outLen, keyArr, userIDArr);
			} catch (Exception e) {
				System.out.println("Could not update Hash.");
				e.printStackTrace();
			}
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testFlapButterfly(){
		
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initButterfly();
	
		JSONArray jsonObj = helper.jsonParserArr(fileFlapButterfly);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int g = inputs.getInt("g");
			String pwd = inputs.getString("pwd");
			String gamma = inputs.getString("gamma");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] gammaArr = helper.hex2bytes(gamma);

			String res = outputs.getString("res");
			
			byte[] output = c.flapPub(g, pwdArr, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testFlapButterflyFull(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initButterflyFull();
	
		JSONArray jsonObj = helper.jsonParserArr(fileFlapButterflyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int g = inputs.getInt("g");
			String pwd = inputs.getString("pwd");
			String gamma = inputs.getString("gamma");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] gammaArr = helper.hex2bytes(gamma);

			String res = outputs.getString("res");
			
			byte[] output = c.flapPub(g, pwdArr, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testFlapDragonfly(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonfly();
		
		JSONArray jsonObj = helper.jsonParserArr(fileFlapDragonfly);
		for (int i = 0; i<jsonObj.length(); ++i ){
			c = defaultInstance.initDragonfly();
			
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int g = inputs.getInt("g");
			String pwd = inputs.getString("pwd");
			String gamma = inputs.getString("gamma");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] gammaArr = helper.hex2bytes(gamma);

			String res = outputs.getString("res");
			
			byte[] output = c.flapPub(g, pwdArr, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testFlapDragonflyFull(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonflyFull();
	
		JSONArray jsonObj = helper.jsonParserArr(fileFlapDragonflyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int g = inputs.getInt("g");
			String pwd = inputs.getString("pwd");
			String gamma = inputs.getString("gamma");
			
			byte[] pwdArr = helper.hex2bytes(pwd);
			byte[] gammaArr = helper.hex2bytes(gamma);

			String res = outputs.getString("res");
			
			byte[] output = c.flapPub(g, pwdArr, gammaArr);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testTweakButterfly(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initButterfly();
	
		JSONArray jsonObj = helper.jsonParserArr(filetweakButterfly);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int domain = inputs.getInt("d");
			int outLen = inputs.getInt("outLen");
			int sLen = inputs.getInt("sLen");
			String aD = inputs.getString("aData");
			byte[] aData = helper.string2Bytes(aD);

			String res = outputs.getString("res");
			
			byte[] output = c.testCompTweak(c.getVID(), domain, c.getLambda(), outLen, sLen, aData);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testTweakButterflyFull(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initButterflyFull();
	
		JSONArray jsonObj = helper.jsonParserArr(filetweakButterflyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int domain = inputs.getInt("d");
			int outLen = inputs.getInt("outLen");
			int sLen = inputs.getInt("sLen");
			String aD = inputs.getString("aData");
			byte[] aData = helper.string2Bytes(aD);

			String res = outputs.getString("res");
			
			byte[] output = c.testCompTweak(c.getVID(), domain, c.getLambda(), outLen, sLen, aData);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testTweakDragonfly(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonfly();
	
		JSONArray jsonObj = helper.jsonParserArr(filetweakDragonfly);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int domain = inputs.getInt("d");
			int outLen = inputs.getInt("outLen");
			int sLen = inputs.getInt("sLen");
			String aD = inputs.getString("aData");
			byte[] aData = helper.string2Bytes(aD);

			String res = outputs.getString("res");
			
			byte[] output = c.testCompTweak(c.getVID(), domain, c.getLambda(), outLen, sLen, aData);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testTweakDragonflyFull(){
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonflyFull();
	
		JSONArray jsonObj = helper.jsonParserArr(filetweakDragonflyFull);
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");
			
			int domain = inputs.getInt("d");
			int outLen = inputs.getInt("outLen");
			int sLen = inputs.getInt("sLen");
			String aD = inputs.getString("aData");
			byte[] aData = helper.string2Bytes(aD);

			String res = outputs.getString("res");
			
			byte[] output = c.testCompTweak(c.getVID(), domain, c.getLambda(), outLen, sLen, aData);
			
			assertTrue(res.equals(helper.bytes2hex(output).toLowerCase()));
		}
	}
	
	@Test
	public void testHInit(){
		
		DefaultInstances defaultInstance = new DefaultInstances();
		Catena c = defaultInstance.initDragonflyFull();
		
		JSONArray jsonObj = helper.jsonParserArr(fileHInit);
		
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs  = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");

			String x 	  = inputs.getString("x");
			byte[] xArray = helper.hex2bytes(x);
			
			JSONArray out = outputs.getJSONArray("v");
			
			String v0 = out.getString(0);
			String v1 = out.getString(1);
			
			byte[] result = c.testHInit(xArray);
			
			String v = v0+v1;
			
			
			assertTrue(v.equals(helper.bytes2hex(result)));
		}
	}
	
	@Test
	public void testProofOfWorkServerSaltButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(fileproofOfWorkServerSaltButterflyReduced);
		
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs  = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");

			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
			byte[] salt = helper.hex2bytes(inputs.getString("salt"));
			byte[] aData = helper.hex2bytes(inputs.getString("aData"));
			byte[] gamma = helper.hex2bytes(inputs.getString("gamma"));
			int outLen = inputs.getInt("outLen");
			int p = inputs.getInt("p");
			int mode = inputs.getInt("mode");
			
			byte[] pwdExp = helper.hex2bytes(outputs.getString("pwd"));
			byte[] saltExp = helper.hex2bytes(outputs.getString("salt"));
			byte[] aDataExp = helper.hex2bytes(outputs.getString("aData"));
			byte[] gammaExp = helper.hex2bytes(outputs.getString("gamma"));
			int outLenExp = outputs.getInt("outLen");
			byte[] outHashExp = helper.hex2bytes(outputs.getString("outHash"));
			int pExp = outputs.getInt("p");
			int modeExp = outputs.getInt("mode");
			
			
			POWstruct output = c.proofOfWorkServer(pwd, salt, aData, outLen, gamma, p, mode);
			
			assertArrayEquals(pwdExp, output.pwd);
			assertArrayEquals(saltExp, output.salt);
			assertArrayEquals(aDataExp, output.aData);
			assertArrayEquals(gammaExp, output.gamma);
			assertArrayEquals(outHashExp, output.rHash);
			assertEquals(outLenExp, output.outLen);
			assertEquals(pExp, output.p);
			assertEquals(modeExp, output.mode);
		}
	}

	
	
	@Test
	public void testProofOfWorkServerPwdButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(fileproofOfWorkServerPwdButterflyReduced);
		
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs  = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");

			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
			byte[] salt = helper.hex2bytes(inputs.getString("salt"));
			byte[] aData = helper.hex2bytes(inputs.getString("aData"));
			byte[] gamma = helper.hex2bytes(inputs.getString("gamma"));
			int outLen = inputs.getInt("outLen");
			int p = inputs.getInt("p");
			int mode = inputs.getInt("mode");
			
			byte[] pwdExp = helper.hex2bytes(outputs.getString("pwd"));
			byte[] saltExp = helper.hex2bytes(outputs.getString("salt"));
			byte[] aDataExp = helper.hex2bytes(outputs.getString("aData"));
			byte[] gammaExp = helper.hex2bytes(outputs.getString("gamma"));
			int outLenExp = outputs.getInt("outLen");
			byte[] outHashExp = helper.hex2bytes(outputs.getString("outHash"));
			int pExp = outputs.getInt("p");
			int modeExp = outputs.getInt("mode");
			
			
			POWstruct output = c.proofOfWorkServer(pwd, salt, aData, outLen, gamma, p, mode);
			
			assertArrayEquals(pwdExp, output.pwd);
			assertArrayEquals(saltExp, output.salt);
			assertArrayEquals(aDataExp, output.aData);
			assertArrayEquals(gammaExp, output.gamma);
			assertArrayEquals(outHashExp, output.rHash);
			assertEquals(outLenExp, output.outLen);
			assertEquals(pExp, output.p);
			assertEquals(modeExp, output.mode);
		}
	}
	
	@Test
	public void testProofOfWorkClientPwdButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(fileproofOfWorkClientPwdButterflyReduced);
		
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs  = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");

			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
			byte[] salt = helper.hex2bytes(inputs.getString("salt"));
			byte[] aData = helper.hex2bytes(inputs.getString("aData"));
			byte[] gamma = helper.hex2bytes(inputs.getString("gamma"));
			byte[] hash = helper.hex2bytes(inputs.getString("hash"));
			int outLen = inputs.getInt("outLen");
			int p = inputs.getInt("p");
			int mode = inputs.getInt("mode");
			
			byte[] resExp = helper.hex2bytes(outputs.getString("res"));
			
			POWstruct input = c.new POWstruct();
			input.pwd = pwd;
			input.salt = salt;
			input.aData = aData;
			input.gamma = gamma;
			input.outLen = outLen;
			input.p = p;
			input.mode = mode;
			input.rHash = hash;
			
			byte[] actuals = c.proofOfWorkClient(input);
			
			assertArrayEquals(resExp, actuals);
		}
	}
	
	@Test
	public void testProofOfWorkClientSaltButterflyReduced(){
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterfly();
		
		c.setGHigh(9);
		c.setGLow(9);
		
		JSONArray jsonObj = helper.jsonParserArr(fileproofOfWorkClientSaltButterflyReduced);
		
		for (int i = 0; i<jsonObj.length(); ++i ){
			JSONObject o = (JSONObject) jsonObj.get(i);
			
			JSONObject inputs  = o.getJSONObject("inputs");
			JSONObject outputs = o.getJSONObject("outputs");

			byte[] pwd = helper.hex2bytes(inputs.getString("pwd"));
			byte[] salt = helper.hex2bytes(inputs.getString("salt"));
			byte[] aData = helper.hex2bytes(inputs.getString("aData"));
			byte[] gamma = helper.hex2bytes(inputs.getString("gamma"));
			byte[] hash = helper.hex2bytes(inputs.getString("hash"));
			int outLen = inputs.getInt("outLen");
			int p = inputs.getInt("p");
			int mode = inputs.getInt("mode");
			
			byte[] resExp = helper.hex2bytes(outputs.getString("res"));
			
			POWstruct input = c.new POWstruct();
			input.pwd = pwd;
			input.salt = salt;
			input.aData = aData;
			input.gamma = gamma;
			input.outLen = outLen;
			input.p = p;
			input.mode = mode;
			input.rHash = hash;
			
			byte[] actuals = c.proofOfWorkClient(input);
			
			assertArrayEquals(resExp, actuals);
		}
	}
	
}
