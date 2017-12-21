package com.nian.xxx.zuul;

import java.util.ArrayList;
import java.util.List;

public class MemoryLeakTest {
	static List<int[]> cache = new ArrayList<int[]>();

	public static void main(String[] args) throws InterruptedException {
	    Thread.currentThread().setName("Memory Leak Thread");  
        
        do {  
            cache.add(new int[1024 * 50]);// 50Kb                
            Thread.sleep(500);  
        } while(true);  

	}

}
