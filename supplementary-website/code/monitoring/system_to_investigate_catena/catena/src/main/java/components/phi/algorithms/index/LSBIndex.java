package main.java.components.phi.algorithms.index;

import main.java.Helper;
import main.java.components.phi.algorithms.IdxStateInterface;

public class LSBIndex implements IdxStateInterface {

	Helper h = new Helper();
	
	public int getLsbIndex(byte[] state, int garlic){
		
		int bytes = garlic + 7 >>>3;
		int shiftBits = (8 - (garlic % 8)) % 8;
        int mask = 0xff >>> shiftBits;
        int len = state.length;
        int first = len - bytes;
        int val = 0;
        
        val = state[first] & mask;
        for(int i = first + 1; i < len; ++i){
        	val = (val << 8) | state[i] & 0xff;
        }
        return val;
	}
}
