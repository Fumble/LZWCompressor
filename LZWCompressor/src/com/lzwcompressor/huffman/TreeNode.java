package com.lzwcompressor.huffman;

// TreeNode.java: node in the Huffman tree, used for encode/decode
class TreeNode {
   public double weight; // probability of symb occurring
   public char symb; // the symbol to be encoded
   public String rep; // string of 0's and 1's, the huffman code word
   public TreeNode left, right; // tree pointeres
   public int step; // step number in construction (just for displaying tree)
}