import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String[] str;
        str = StdIn.readAllStrings();

        for (String item : str) {
            rq.enqueue(item);
        }

        StdOut.println(rq.size());

        int N = Integer.parseInt(args[0]);
        for (int i = 0; i < N; i++ ) {
            StdOut.print(rq.dequeue());
        }
    }
}