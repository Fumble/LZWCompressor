package com.lzwcompressor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LZW {
	private File file;
	private FileInputStream file_input;
	private DataInputStream data_input;
	private Dictionary dicoCompression;
	private Dictionary dicoDecompression;
	private int numBits;
	private int output_bit_buffer = 0;
	private int output_bit_count = 0;

	public LZW() {
		numBits = 12;
	}

	public LZW(int numBits) {
		this.numBits = numBits;
	}

	public void compression(String s) throws IOException {
		dicoCompression = new Dictionary(1 << numBits);
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
				dicoCompression.put(dicoCompression.getIndex(), w + c);
				if (dicoCompression.getKey(w) <= 255) {
					System.out.println(w);
					os.write(w.charAt(0));
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

	public void decompression(String filename) throws IOException {
		int code;
		String c = null, w = null, entree = null;

		ArrayList<Integer> compressed = new ArrayList<Integer>();

		dicoDecompression = new Dictionary();
		dicoDecompression.init();

		file = new File(filename);

		file_input = new FileInputStream(file);
		compressed = readCompressedFile(file_input);
		code = compressed.get(0);

		// data_input = new DataInputStream(file_input);

		compressed = readCompressedFile(file_input);
		code = compressed.get(0);

		c = dicoDecompression.getValue(code);

		int i = 1;
		w = c;

		while (code != -1) {

			if (code > 255 && dicoDecompression.containsKey(code)) {
				entree = w;
			} else if (code > 255 && !dicoDecompression.containsKey(code)) {
				entree = w + w.charAt(0);
			} else {
				entree = c;
			}
			System.out.println(entree);
			dicoDecompression.put(255 + i, w + entree.charAt(0));
			w = entree;
			i++;

			c = dicoCompression.getValue(code);

			// code = read_char(file_input);
			System.out.println(code);
		}
	}

	private int read_char(FileInputStream file) throws IOException {
		int return_value;
		int input_bit_count = 0;
		long input_bit_buffer = 0;

		while (input_bit_count <= 24) {
			input_bit_buffer |= (long) file_input.read() << (24 - input_bit_count);
			input_bit_count += 8;
		}
		return_value = (int) (input_bit_buffer >> (32 - numBits));
		input_bit_buffer <<= numBits;
		input_bit_count -= numBits;

		return (return_value);
	}

	private ArrayList<Integer> readCompressedFile(FileInputStream file)
			throws IOException {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int code = 0;
		while (code != -1) {
			code = read_char(file);
			result.add(code);
		}
		return result;
	}

	private void writeCode(DataOutputStream os, int code) {
		output_bit_buffer |= code << (32 - numBits - output_bit_count);
		output_bit_count += numBits;

		while (output_bit_count >= 8) {
			try {
				os.write(output_bit_buffer >> 24);
			} catch (IOException e) {
				System.out
						.println("IOException while writing the output file !");
				System.exit(1);
			}
			output_bit_buffer <<= 8;
			output_bit_count -= 8;
		}
	}

}
