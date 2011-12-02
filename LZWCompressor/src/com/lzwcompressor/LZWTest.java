package com.lzwcompressor;

import java.io.IOException;

public class LZWTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String s = "tobeornottobeortobeornot";
		// String s =
		// "Loremipsumdolorsitamet,consecteturadipiscingelit.Fuscenecturpisquisnequepdldgjskqdndsglqshdfdkfnqsifhdkflsdgiuqsjdkosidghjqspofhdfsoflqsjfiudsfhqsfghdsfiolulvinarporttitoracatodio.";
		LZW lzw = new LZW(12);
		//lzw.compression(s);
		lzw.compressNoFile(s);
		lzw.decompressNoFile();
		//lzw.decompression("test.txt");
	}

}
