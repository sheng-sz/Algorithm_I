import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class TestDeque {
    public Deque<Integer> q;

    public TestDeque() {
        q = new Deque<Integer>();
    }

    public void testCalls(int numTest, double pAF, double pAL,
                          double pRF, double pRL,
                          double pE, double pS) {
        for (int i = 0; i < numTest; i++) {
            if (StdRandom.bernoulli(pAF)) q.addFirst(i);
            if (StdRandom.bernoulli(pAL)) q.addLast(i);
            if (StdRandom.bernoulli(pRF)) q.removeFirst();
            if (StdRandom.bernoulli(pRL)) q.removeLast();
            if (StdRandom.bernoulli(pE)) q.isEmpty();
            if (StdRandom.bernoulli(pS)) q.size();
        }
    }

    public static void main(String[] args) {

        TestDeque decTest = new TestDeque();
        for (int i = 5; i < 5001 ; i *= 10) {
            StdOut.println("N = " + i);
            decTest.testCalls(i, 0.4, 0.4, 0.0, 0.0, 0.0, 0.2);
        }

        for (int i = 5; i < 5001 ; i *= 10) {
            StdOut.println("N = " + i);
            decTest.testCalls(i, 0.1, 0.0, 0.8, 0.0, 0.1, 0.0);
        }
        // decTest.addFirstN(8);
        // decTest.testIterate();
    }
}