package com.lzwcompressor.huffman;

// Table.java: Huffman code frequency table
import java.io.*;
class Table {
   public final int MAXT = 100; // maximum number of different symbols
   public int currTableSize; // current size as table is constructed
   public Entry[] tab; // the table array, not allocated
   private Reader in; // internal file name for input stream
   String file = ""; // the whole input file as a String
   private boolean fileOpen = false; // is the file open yet?
   private String fileName; // name of input file, if present
   private int totalChars = 0; // total number of chars read
   char markerChar = '@'; // sentinal at end of file

   // Table: constructor, input parameter: input file name or null
   public Table(String f) {
      fileName = f;
      currTableSize = 0;
      tab = new Entry[MAXT];
   } 

   // dumpTable: debug dump of contents of Table
   public void dumpTable() {
      int i;
      System.out.println("\nDump of Table ----->");
      System.out.println("  Size: " + currTableSize);
      for (i = 0; i < currTableSize; i++) {
         System.out.println("Entry " + i + ". Symbol: " +
            tab[i].symb + ", Weight: " + tab[i].weight +
            ", Representation: " + tab[i].rep);
      }
      System.out.println("----> End Dump of Table\n");
   }

   // getNextChar: fetches next char.  Also opens input file
   private char getNextChar() {
      char ch = ' '; // = ' ' to keep compiler happy
      if (!fileOpen) {
         fileOpen = true;
         if (fileName == null)
            in = new InputStreamReader(System.in);
         else {
            try {
               in = new FileReader(fileName);
            } catch (IOException e) {
               System.out.println("Exception opening " + fileName);
            }
         }
      }
      try {
         ch = (char)in.read();
      } catch (IOException e) {
         System.out.println("Exception reading character");
      }
      return ch;
   }

   // buildTable: fetch each character and build the Table
   public void buildTable() {
      char ch = getNextChar();
      while (ch != 65535 && ch != markerChar) { // EOF or special sentinal #
         totalChars++;
         file += ch;
         int i = lookUp(ch);
         if (i == -1) { // new entry
            tab[currTableSize] = new Entry();
            tab[currTableSize].symb = ch;
            tab[currTableSize].weight = 1.0;
            tab[currTableSize].rep = "";
            currTableSize++;
         }
         else { // existing entry
            tab[i].weight += 1.0;
         }
         // System.out.print(ch); // for debug
         ch = getNextChar();
      } // while
      // finish calculating the weights
      for (int j = 0; j < currTableSize; j++)
         tab[j].weight /= (double)totalChars;
      // System.out.println(); // for debug
   }
   
   // lookUp: loop up the next char in the Table tab
   public int lookUp(char ch) {
      for (int j = 0; j < currTableSize; j++)
         if (tab[j].symb == ch) return j;
      return -1;
   }
   
   // log2: Logarithm base 2
   public double log2(double d) {
      return Math.log(d) / Math.log(2.0);
   }
   
   // entropy: calculate entropy of the Table
   public double entropy() {
      double res = 0.0;
      for (int i = 0; i < currTableSize; i++)
         res += tab[i].weight * log2(1.0/tab[i].weight);
      return res;
   } 
     
   // aveCodeLen: calculate average code length
   public double aveCodeLen() {
      double res = 0.0;
      for (int i = 0; i < currTableSize; i++)
         res += tab[i].weight * tab[i].rep.length();
      return res;
   }
}