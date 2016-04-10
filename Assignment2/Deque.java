import java.util.Iterator;
// import java.lang.NullPointerException;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
// import java.lang.UnsupportedOperationException;


// Corner cases. Throw a java.lang.NullPointerException if the client attempts
// to add a null item; throw a java.util.NoSuchElementException if the client
// attempts to remove an item from an empty deque; throw a
// java.lang.UnsupportedOperationException if the client calls the remove()
// method in the iterator; throw a java.util.NoSuchElementException if the
// client calls the next() method in the iterator and there are no more items to
// return.

// Performance requirements.   Your deque implementation must support each deque
// operation in constant worst-case time. A deque containing N items must use at
// most 48N + 192 bytes of memory. and use space proportional to the number of
// items currently in the deque. Additionally, your iterator implementation must
// support each operation (including construction) in constant worst-case time.

public class Deque<Item> implements Iterable<Item> { //mem=16N
// public class Deque<Item> {

    private Node first; // mem=8N
    private Node last; // mem=8N
    private int numNodes; // mem=32N

    private class Node { // mem=8
        private Item content;
        private Node next;
        private Node previous;
    }

    // construct an empty deque
    public Deque() {
        numNodes = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return numNodes == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numNodes;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("trying to insert null item");

        Node newfirst = new Node();
        newfirst.next = first;
        newfirst.previous = null;
        newfirst.content = item;

        if (size() == 0) {
            first = newfirst;
            last = first;
        } else {
            first.previous = newfirst;
            first = newfirst;
        }

        numNodes++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException("trying to insert null item");

        Node newlast = new Node();
        newlast.content = item;
        newlast.next = null;
        newlast.previous = last;
        if (size() == 0) {
            last = newlast;
            first = last;
        }
        else {
            last.next = newlast;
            last = newlast;
        }

        // if (size() == 1) first.next = last;
        numNodes++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque is empty");

        Item temp = first.content;
        first = first.next;
        if (first != null)
            first.previous = null;
        else
            last = null;
        numNodes--;
        return temp;

    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Deque is empty");

        Item temp = last.content;
        last = last.previous;
        if (last != null)
            last.next = null;
        else
            first = null;
        numNodes--;

        return temp;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current;

        public ListIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException("no more items");
            Node temp = current;
            current = current.next;
            return temp.content;
        }
    }


    // unit testing
    public static void main(String[] args) {
        Deque<Integer> dec = new Deque<Integer>();
        StdOut.printf("%s,%d\n", dec.isEmpty(), dec.size());


        dec.addFirst(1);
        dec.addFirst(2);
        dec.addFirst(3);
        dec.addFirst(4);
        dec.addLast(5);
        dec.addLast(6);
        dec.addLast(7);
        dec.removeLast();
        dec.removeLast();
        dec.removeLast();
        dec.removeFirst();
        dec.removeFirst();
        dec.removeFirst();
        dec.removeFirst();
        dec.addFirst(1);
        dec.addLast(5);
        dec.addLast(7);


        // StdOut.println(dec.removeFirst());
        // StdOut.println(dec.removeLast());

        // Iterator<Integer> i = dec.iterator();

        // while (i.hasNext()) {
        //     StdOut.print(i.next());
        //     StdOut.print("->");
        // }

        for (int item : dec) {
            StdOut.print(item+"->");
        }
        StdOut.println("");

        for (int item1 : dec) {
            StdOut.print(item1+"->");
        }
        StdOut.println("");

    }
}