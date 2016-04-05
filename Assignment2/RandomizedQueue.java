import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;
import java.util.Iterator;

// Corner cases. The order of two or more iterators to the same randomized queue
// must be mutually independent; each iterator must maintain its own random
// order. Throw a java.lang.NullPointerException if ÷the client attempts to add
// a null item; throw a java.util.NoSuchElementException if the client attempts
// to sample or dequeue an item from an empty randomized queue; throw a
// java.lang.UnsupportedOperationException if the client calls the remove()
// method in the iterator; throw a java.util.NoSuchElementException if the
// client calls the next() method in the iterator and there are no more items to
// return.

public class RandomizedQueue<Item> implements Iterable<Item> {
// public class RandomizedQueue<Item> {

   private int first; // mem=4N
   private int last; // mem=4N
   public Item[] q;
   private int numItems; // mem=32N

   // construct an empty randomized queue
   public RandomizedQueue() {
      q = (Item[]) new Object[1];
      numItems = 0;
      first = 0;
      last = 0;
   }
   // is the queue empty?
   public boolean isEmpty() {
      return numItems == 0;
   }
   // return the number of items on the queue
   public int size() {
      return numItems;
   }
   // add the item
   public void enqueue(Item item) {
      if (item == null)
         throw new NullPointerException("null item");

      if (numItems == q.length/2) expandQ();
      q[last++] = item;
      numItems++;
      StdOut.println("enqueue val "+item + " -> index " + (last-1) + " | f/l = " + first + "/" + last + " | Q.len = " + q.length + " | N = " + numItems);
   }
   // remove and return a random item
   public Item dequeue() {
      int randPtr = StdRandom.uniform(numItems);
      Item temp = q[randPtr+first];
      q[randPtr+first] = q[first];
      first++;
      numItems--;
      if ( (numItems > 0 && numItems == q.length/4) || numItems ==0) shrinkQ();

      StdOut.println("dequeue index " + randPtr + " -> val " + temp + " | f/l = " + first + "/" + last + " | Q.len = " + q.length + " | N = " + numItems);

      return temp;
   }

   private void expandQ() {
      Item[] newQ = (Item[]) new Object[q.length * 2];
      for (int i = 0; i < q.length*2; i++ ) {
         if (i < numItems)
            newQ[i] = q[first + i];
         else
            newQ[i] = null;
      }
      first = 0;
      last = numItems;
      q = newQ;
   }

   private void shrinkQ() {
      Item[] newQ = (Item[]) new Object[q.length/2];
      for (int i = 0; i< q.length/2; i++) {
         if (i < numItems)
            newQ[i] = q[first + i];
         else
            newQ[i] = null;
      }
      first = 0;
      last = numItems;
      q = newQ;
   }

   // return (but do not remove) a random item
   public Item sample() {
      int randPtr = StdRandom.uniform(numItems);
      return q[first+randPtr];
   }

   // return an independent iterator over items in random order
   public Iterator<Item> iterator() { return new ListIterator(); }

   private class ListIterator implements Iterator<Item> {
      int[] seq;

      int current = 0;

      public ListIterator(){
         seq = new int[numItems];

         for (int i = 0; i < numItems; i++) {
            seq[i] = i;
         }

         StdRandom.shuffle(seq);
      }
      public boolean hasNext() {return current == numItems - 1; }
      public void remove() {
         throw new UnsupportedOperationException("remove not supported");
      }
      public Item next(){
         // StdOut.print("test");

         if (current == numItems)
            throw new NoSuchElementException("no more items");
         return q[seq[current++]];
      }
   }
   // unit testing
   public static void main(String[] args) {

      RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
      int N = 8;
      for (int i = 1; i < 8; i++) {
         rq.enqueue(i);
      }

      Iterator<Integer> q0 = rq.iterator();
      // // Iterator<Integer> q1 = rq.iterator();
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());


      for ( Integer item : rq) {
         StdOut.print(item);
      }
      StdOut.println("");

      for (int item : rq) {
         StdOut.print(item+"-");
      }
      StdOut.println("");

      // for (int i = 1; i < 8; i++)
         // rq.dequeue();

   }
}