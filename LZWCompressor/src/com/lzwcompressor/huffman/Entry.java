package com.lzwcompressor.huffman;

// Entry.java: entry in the code frequency table
class Entry {
   public char symb; // character to be encoded
   public double weight; // probability of occurrence of the character
   public String rep; // string giving 0-1 Huffman codeword for the char
}