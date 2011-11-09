package com.lzwcompressor;


public class LZWTest {
	private Dictionary dicoCompression;
	private Dictionary dicoDecompression;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "tobeornottobeortobeornot";
		LZWTest lzw = new LZWTest();
		lzw.compression(s);
	}

	public void compression(String s) {
		dicoCompression = new Dictionary();
		dicoCompression.init();
		String w = "";
		char c;
		int i = 0;
		while (i < s.length()) {
			c = s.charAt(i);
			if (dicoCompression.containsValue(w + c)) {
				w = w + c;
			} else {
				dicoCompression.put(255 + (i-((w+c).length()-2)), w + c);
				if (dicoCompression.getKey(w) <= 255) {
					System.out.println(w);
				} else {
					 System.out.println(dicoCompression.getKey(w));
					//System.out.println(w);
				}
				w = String.valueOf(c);
			}
			i++;
		}
		 System.out.println(dicoCompression.getKey(w));
		//System.out.println(w);
	}

//	 public void decompression(ArrayList<String> chaine){
//	 String c = chaine.get(0);
//	 int i=1;
//	 String w = c;
//	 String entree;
//	 dicoDecompression = new Dictionary();
//	 while(i < chaine.size()){
//	 if(c>255 && dicoDecompression.containsValue(String.valueOf(c))){
//	 entree = w;
//	 }
//	 else if(c > 255 && !dicoDecompression.containsValue(String.valueOf(c))){
//	 entree = w + w.charAt(0);
//	 }
//	 else{
//	 entree = String.valueOf(c);
//	 }
//	 System.out.println(entree);
//	 dicoDecompression.put(255+i,w + entree.charAt(0));
//	 w = entree;
//	 i++;
//	 c = chaine.get(i);
//	 }
//	 }
}
