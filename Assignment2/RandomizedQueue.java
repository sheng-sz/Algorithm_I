import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;
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
   private int debug = 0;
   private int first; // mem=4N
   private int last; // mem=4N
   private Item[] q;
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

      if (last == q.length || last == 0) expandQ();
      q[last++] = item;
      numItems++;
      if (debug == 1)
         StdOut.println("enqueue val "+item + " -> index " + (last-1) +
            " | f/l = " + first + "/" + last + " | Q.len = " + q.length +
             " | N = " + numItems);
   }
   // remove and return a random item
   public Item dequeue() {
      if (numItems == 0)
         throw new NoSuchElementException("trying to dequeue from empty Q");

      int randPtr = StdRandom.uniform(numItems);
      Item temp = q[randPtr+first];
      q[randPtr+first] = q[first];
      q[first] = null;
      first++;
      numItems--;
      if ((numItems > 0 && numItems == q.length/4) || numItems == 0) shrinkQ();

      if (debug == 1)
         StdOut.println("dequeue index " + randPtr + " -> val " + temp +
            " | f/l = " + first + "/" + last + " | Q.len = " + q.length +
            " | N = " + numItems);

      return temp;
   }

   private void expandQ() {
      Item[] newQ = (Item[]) new Object[q.length * 2];
      for (int i = 0; i < numItems; i++) {
            newQ[i] = q[first + i];
      }
      first = 0;
      last = numItems;
      q = newQ;
   }

   private void shrinkQ() {
      Item[] newQ = (Item[]) new Object[q.length/2];
      for (int i = 0; i < numItems; i++) {
            newQ[i] = q[first + i];
      }
      first = 0;
      last = numItems;
      q = newQ;
   }

   // return (but do not remove) a random item
   public Item sample() {
      if (numItems == 0)
         throw new NoSuchElementException("trying to sample from empty Q");

      int randPtr = StdRandom.uniform(numItems);
      return q[first+randPtr];
   }

   // return an independent iterator over items in random order
   public Iterator<Item> iterator() { return new ListIterator(); }

   private class ListIterator implements Iterator<Item> {
      private int[] seq;
      private int current;
      private int l;

      public ListIterator() {
         current = first;
         seq = new int[numItems];

         for (int i = 0; i < numItems; i++) {
            seq[i] = first + i;
         }

         StdRandom.shuffle(seq);
      }

      public boolean hasNext() { return !(current == last); }

      public void remove() {
         throw new UnsupportedOperationException("remove not supported");
      }

      public Item next() {
         if (debug == 1)
            StdOut.printf("current %d, first %d, last %d\n",
               current, first, last);
         if (current == last)
            throw new NoSuchElementException("no more items");
         return q[seq[current++ - first]];
      }
   }
   // unit testing
   public static void main(String[] args) {

      RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
      // int N = 10;
      // for (int i = 0; i < N; i++) {
      //    rq.enqueue(i);
      // }
      // StdOut.println("---");

      // N = 100;
      // for (int i = 0; i < N; i++) {
      //    if (StdRandom.uniform(10) > 3)
      //       rq.enqueue(StdRandom.uniform(10));
      //    else
      //       rq.dequeue();
      // }
      rq.enqueue(5);
      rq.dequeue();
      rq.enqueue(5);


      // for (int i = 1; i < 8; i++) {
      //    rq.enqueue(i*5);
      // }

      // Iterator<Integer> q0 = rq.iterator();
      // // Iterator<Integer> q1 = rq.iterator();
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());
      // StdOut.println("next" + q0.next());

      int x = 0;

      for (int item : rq) {
         StdOut.println(item);
      }
      // StdOut.println("");

      // for (int item : rq) {
      //    StdOut.print(item+"-");
      // }
      // StdOut.println("");

      // for (int i = 1; i < 8; i++)
      //    StdOut.println("sample " + rq.sample());


      // for (int i = 1; i < 8; i++)
         // rq.dequeue();

   }
}