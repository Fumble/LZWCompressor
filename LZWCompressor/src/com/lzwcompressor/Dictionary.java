package com.lzwcompressor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Dictionary {
	private TreeMap<Integer, String> dico;
	
	public Dictionary(){
		dico = new TreeMap<Integer, String>();
	}
	
	public static String readFileAsString(String filePath)
			throws java.io.IOException {
		StringBuilder fileData = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			fileData.append(buf, 0, numRead);
		}
		reader.close();
		return fileData.toString();
	}
	
	public void display()
	{
		Set<Integer> keySet = dico.keySet();
		Iterator<Integer> it = keySet.iterator();
		int nomCurr;
		while(it.hasNext())
		{
			nomCurr = it.next();
			System.out.println(nomCurr+" ==> "+dico.get(nomCurr));
		}
	}

}
