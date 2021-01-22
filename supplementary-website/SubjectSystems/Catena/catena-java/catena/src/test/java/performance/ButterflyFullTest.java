package test.java.performance;

import static org.junit.Assert.*;

import org.junit.Test;

import main.java.Catena;
import main.java.DefaultInstances;
import main.java.Helper;

public class ButterflyFullTest {

	@Test
	public void testButterflyFull10x() {
		
		DefaultInstances instance = new DefaultInstances();
		Catena c = instance.initButterflyFull();
		
		Helper helper = new Helper();
		
		byte[] pwd = helper.hex2bytes("012345");
		byte[] salt = helper.hex2bytes("6789ab");
		byte[] gamma = helper.hex2bytes("6789ab");
		byte[] aData = helper.hex2bytes("000000");
	    int outputLength = 64;
	    
	    long start = System.currentTimeMillis();
	    long current=0;
	    for (int i = 0; i<10; ++i){
	    	c.catena(pwd, salt, aData, gamma, outputLength);
	    	current = System.currentTimeMillis();
	    	System.out.println("Time used for Butterfly Full " + i + " times: " + (current-start));
	    	start=current;
	    }
	    
	    assertTrue(true);
	}
}
