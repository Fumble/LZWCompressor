package com.lzwcompressor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LZWTest {
	private HashMap<Integer,String> dicoCompression;
	private HashMap<Integer,String> dicoDecompression;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "tobeornottobeortobeornot";
		LZWTest lzw = new LZWTest();
		lzw.compression(s);
	}
	
	public void compression(String s){
		Set<Integer> keyset;
		dicoCompression = new HashMap<Integer,String>(); 
		String w = "";
		char c;
		int i =0;
		while(i<s.length()){
			c = s.charAt(i);
			i++;
			if(dicoCompression.containsValue(w+c)){
				w = w+c;
				System.out.println(dicoCompression);
			}
			else{
				dicoCompression.put(255+i, w+c);
				System.out.println(w);
				w = String.valueOf(c);
			}	
		}
	}
	
	public void decompression(ArrayList<String> chaine){
		String c = chaine.get(0);
		int i=1;
		String w = c;
		String entree;
		dicoDecompression = new HashMap<Integer,String>(); 
		while(i < chaine.size()){
			if(c>255 && dicoDecompression.containsValue(String.valueOf(c))){
				entree = w; 
			}
			else if(c > 255 && !dicoDecompression.containsValue(String.valueOf(c))){
				entree = w + w.charAt(0);
			}
			else{
				entree = String.valueOf(c);
			}
			System.out.println(entree);
			dicoDecompression.put(255+i,w + entree.charAt(0));
			w = entree;
			i++;
			c = chaine.get(i);
		} 
	}
}
