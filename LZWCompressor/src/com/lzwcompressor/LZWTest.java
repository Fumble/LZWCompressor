package com.lzwcompressor;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LZWTest {
	private Dictionary dicoCompression;
	private Dictionary dicoDecompression;
	private int numBits;
	
	public LZWTest(){
		numBits = 12;
	}
	
	public LZWTest(int numBits){
		this.numBits = numBits;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//String s = "tobeornottobeortobeornot";
		String s ="Loremipsumdolorsitamet,consecteturadipiscingelit.Fuscenecturpisquisnequepulvinarporttitoracatodio.";
		LZWTest lzw = new LZWTest();
		lzw.compression(s);
	}

	public void compression(String s) throws IOException {
		dicoCompression = new Dictionary(1<<numBits);
		dicoCompression.init();
		String w = "";
		char c;
		int i = 0;
		String filename = "test.txt";
		DataOutputStream os = new DataOutputStream(new FileOutputStream(
				filename));
		while (i < s.length()) {
			c = s.charAt(i);
			if (dicoCompression.containsValue(w + c)) {
				w = w + c;
			} else {
				dicoCompression.put(255 + (i - ((w + c).length() - 2)), w + c);
				if (dicoCompression.getKey(w) <= 255) {
					System.out.println(w);
					os.writeByte(w.charAt(0));
				} else {
					System.out.println(dicoCompression.getKey(w));
					writeCode(os, dicoCompression.getKey(w));
				}
				w = String.valueOf(c);
			}
			i++;
		}
		System.out.println(dicoCompression.getKey(w));
		writeCode(os, dicoCompression.getKey(w));
		os.flush();
		os.close();
	}

	void writeCode(DataOutputStream os, int code) throws IOException {
//		System.out.println("BIT TO WRITE "
//				+ (32 - Integer.numberOfLeadingZeros(code)));
		for (int i = 0; i < (32 - Integer.numberOfLeadingZeros(code)); ++i) {
			os.write(code & 1);
			code /= 2;
		}
	}

	// public void decompression(ArrayList<String> chaine){
	// String c = chaine.get(0);
	// int i=1;
	// String w = c;
	// String entree;
	// dicoDecompression = new Dictionary();
	// while(i < chaine.size()){
	// if(c>255 && dicoDecompression.containsValue(String.valueOf(c))){
	// entree = w;
	// }
	// else if(c > 255 && !dicoDecompression.containsValue(String.valueOf(c))){
	// entree = w + w.charAt(0);
	// }
	// else{
	// entree = String.valueOf(c);
	// }
	// System.out.println(entree);
	// dicoDecompression.put(255+i,w + entree.charAt(0));
	// w = entree;
	// i++;
	// c = chaine.get(i);
	// }
	// }
}
