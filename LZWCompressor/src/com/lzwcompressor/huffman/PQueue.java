package com.lzwcompressor.huffman;

// PQueue.java: implement a priority queue as a linked list of trees
//   Initialize it as a linked list of singleton trees
class PQueue {
   ListNode list = null; // this points to the main list

   // insert: insert new entry into the list
   public void insert(TreeNode t) {
      ListNode l = new ListNode();
      l.hufftree = t;
      l.next = list;
      list = l;
   }

   // buildList: create the initial list with singleton trees
   public void buildList(Entry[] tab, int n) {
      int i;
      TreeNode tNode;
      for (i = 0; i < n; i++) {
         tNode = new TreeNode();
         tNode.weight = tab[i].weight;
         tNode.left = tNode.right = null;
         tNode.symb = tab[i].symb;
         tNode.rep = "";
         insert(tNode);
      }
   }

   // dumpList: debug dump of the list
   public void dumpList() {
      System.out.println("\nDump of List ----->");
      ListNode l = list;
      while (l != null) {
         System.out.print("Symb: " + l.hufftree.symb);
         System.out.println(", Weight: " + l.hufftree.weight);
         l = l.next;
      }
      System.out.println("----> End Dump of List\n");
   }

   // least: Remove and return from the list tree with greatest root weight
   //   sort of a pain in the ass to write
   public TreeNode least() {
      ListNode l, oldl, minl = null, oldminl = null; // = null: for compiler
      double minw = 1000000;
      oldl = list;
      l = list;
      while (l != null) {
         if (l.hufftree.weight < minw) {
            minw = l.hufftree.weight;
            oldminl = oldl;
            minl = l;
         }
         oldl = l;
         l = l.next;
      }
      if (minl == oldminl) {
            list = list.next;
            return minl.hufftree;
      }
      oldminl.next = minl.next;
      return minl.hufftree;
   }
}