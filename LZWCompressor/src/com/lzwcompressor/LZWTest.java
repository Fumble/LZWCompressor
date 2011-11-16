package com.lzwcompressor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class LZWTest {
	private Dictionary dicoCompression;
	private Dictionary dicoDecompression;
	
    private File file ;
    private FileInputStream file_input;
    private DataInputStream data_input ;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String s = "tobeornottobeortobeornot";
		LZWTest lzw = new LZWTest();
		lzw.compression(s);
	}

	public void compression(String s) throws IOException {
		dicoCompression = new Dictionary();
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
					// System.out.println(w);
					writeCode(os, dicoCompression.getKey(w));
				}
				w = String.valueOf(c);
			}
			i++;
		}
		System.out.println(dicoCompression.getKey(w));
		// System.out.println(w);
		writeCode(os, dicoCompression.getKey(w));
		os.flush();
		os.close();
	}

	public void decompression(String filename) {
        Short code = null;
        String c = null,w = null, entree = null;
        
        
        file=new File(filename);
        try {
			file_input = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        data_input    = new DataInputStream(file_input);
        
        try {
			code = data_input.readShort();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        c= dicoCompression.getValue(code);
        
		int i = 1;
		w = c;
		dicoDecompression = new Dictionary();
		while (code != -1) {
			if (code > 255 && dicoDecompression.containsKey(code)) {
				entree = w;
			} else if (code > 255
					&& !dicoDecompression.containsKey(code)) {
				entree = w + w.charAt(0);
			} else {
				entree = c;
			}
			System.out.println(entree);
			dicoDecompression.put(255 + i, w + entree.charAt(0));
			w = entree;
			i++;
			c= dicoCompression.getValue(code);
		}
	}


	void writeCode(DataOutputStream os, int code) throws IOException {
//		System.out.println("BIT TO WRITE "
//				+ (32 - Integer.numberOfLeadingZeros(code)));
		for (int i = 0; i < (32 - Integer.numberOfLeadingZeros(code)); ++i) {
			os.write(code & 1);
			code /= 2;
		}
	}
}
