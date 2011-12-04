package com.lzwcompressor.huffman;

// Huffman.java: the Huffman tree algorithm
import java.io.File;
import java.text.DecimalFormat;

public class Huffman {
	public TreeNode tree; // the decoding tree
	public Table t; // the frequency and encoding table
	public PQueue p; // priority queue for building the Huffman tree
	private int depth; // depth variable for debug printing of tree
	String encodedFile, decodedFile; // files as Strings
	char markerChar = '@'; // sentinal at end of file
	public DecimalFormat fourDigits = new DecimalFormat("0.0000");

	// Huffman: constructor, does all the work
	public Huffman(String fileName) {
		t = new Table(fileName);
		t.buildTable();
		// t.dumpTable();
		p = new PQueue();
		p.buildList(t.tab, t.currTableSize);
		// p.dumpList();
		tree = huffman(t.currTableSize);
		insertRep(tree, t.tab, t.currTableSize, "");

		// displayTree(tree);
		// t.dumpTable();

		System.out.println("\n***** Huffman compression *****");
		// System.out.println("\nInput file (as a String):");
		// System.out.println(t.file);
		encodedFile = encode(t.file);
		// System.out.println("\nEncoded file (as a String):");
		// System.out.println(encodedFile);
		decodedFile = decode(encodedFile);
		System.out.println("LZW compressed file size: "
				+ new File(fileName).length());
		System.out.println("Huffman compressed file size: "
				+ encodedFile.length() / 8);
		// System.out.println("\nDecoded file (as a String):");
		// System.out.println(decodedFile);
		System.out.println("Entropy: " + t.entropy() + ", Ave. Code Length: "
				+ t.aveCodeLen());
	}

	// encode: translate the input file to binary Huffman file
	public String encode(String file) {
		String returnFile = ""; // encoded file to return (as a String)
		for (int i = 0; i < file.length(); i++) {
			int loc = t.lookUp(file.charAt(i));
			if (loc == -1) {
				System.out.println("Error in encode: can't find: "
						+ file.charAt(i));
				System.exit(0);
			}
			returnFile += t.tab[loc].rep;
		}
		return returnFile;
	}

	// decode: translate the binary file (as a string) back to chars
	public String decode(String file) {
		String returnFile = ""; // decoded file to return (as a String)
		TreeNode treeRef; // local tree variable to keep chasing into tree
		int i = 0; // index in the Huffman String
		while (i < file.length()) { // keep going to end of String
			treeRef = tree; // start at root of tree
			while (true) {
				if (treeRef.symb != markerChar) { // at a leaf node
					returnFile += treeRef.symb;
					break;
				} else if (file.charAt(i) == '0') { // go left with '0'
					treeRef = treeRef.left;
					i++;
				} else { // go right with '1'
					treeRef = treeRef.right;
					i++;
				}
			} // while (true)
		} // while
		return returnFile;
	}

	// huffman: construct the Huffman tree, for decoding
	public TreeNode huffman(int n) {
		int i;
		TreeNode tree = null; // = null for compiler
		for (i = 0; i < n - 1; i++) {
			tree = new TreeNode();
			tree.left = p.least();
			tree.left.step = i + 1; // just for displaying tree
			tree.right = p.least();
			tree.right.step = i + 1; // just for displaying tree
			tree.weight = tree.left.weight + tree.right.weight;
			tree.symb = markerChar; // must not use '@' in input file
			tree.rep = "";
			p.insert(tree);
		}
		return tree;
	}

	// displayTree: print out the tree, with initial and final comments
	public void displayTree(TreeNode tree) {
		System.out.println("\nDisplay of Huffman coding tree\n");
		depth = 0;
		displayTreeRecurs(tree);
	}

	// displayTreeRecurs: need recursive function for inorder traveral
	public void displayTreeRecurs(TreeNode tree) {
		depth++; // depth of recursion
		String s = "";
		if (tree != null) {
			s = display(tree.rep + "0");
			System.out.println(s);
			displayTreeRecurs(tree.left);
			s = display(tree.rep);
			System.out.print(s + "+---");
			if (depth != 1) {
				if (tree.symb == markerChar)
					System.out.print("+---");
			}
			System.out.print(tree.symb + ": " + fourDigits.format(tree.weight)
					+ ", " + tree.rep);
			if (depth != 1)
				System.out.println(" (step " + tree.step + ")");
			else
				System.out.println();
			displayTreeRecurs(tree.right);
			s = display(tree.rep + "1");
			System.out.println(s);
		}
		depth--;
	}

	// display: output blanks and verical lines to display tree
	private String display(String rep) {
		// tricky use of rep string to display correctly
		String s = "   ";
		for (int i = 0; i < rep.length() - 1; i++) { // initial chars
			if (rep.charAt(i) != rep.charAt(i + 1))
				s += "|";
			else
				s += " ";
			s += "   ";
		}
		return s;
	}

	// insertRep: tricky function to use Huffman tree to create representation
	public void insertRep(TreeNode tree, Entry tab[], int n, String repr) {
		// recursive function to insert Huffman codewords at each node.
		// this could just insert at the leaves.
		String s1, s2;
		tree.rep = repr;
		if ((tree.left) == null && (tree.right) == null) {
			for (int i = 0; i < n; i++)
				if (tree.symb == tab[i].symb)
					tab[i].rep = tree.rep;
			return;
		}
		s1 = repr;
		s1 += "0";
		insertRep(tree.left, tab, n, s1); // recursive call to the left
		s2 = repr;
		s2 += "1";
		insertRep(tree.right, tab, n, s2); // recursive call to the right
	}

}