import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.RedBlackBST;
import java.util.ArrayList;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class WordNet {
    private Digraph myGraph;
    private RedBlackBST<String, ArrayList<Integer>> dict;
    private RedBlackBST<Integer, String> dict2;
    private SAP mysap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException();
        }
        In sysnetsFile = new In(synsets);
        In hypernymsFile = new In(hypernyms);
        int numVertex = 0;

        dict = new RedBlackBST<String, ArrayList<Integer>>();
        dict2 = new RedBlackBST<Integer, String>();

        while (sysnetsFile.hasNextLine()) {
            numVertex++;
            String line = sysnetsFile.readLine();
            // StdOut.println(line);

            String[] fields = line.split(",");
            String[] nouns = fields[1].split(" ");

            for (String noun : nouns) {
                ArrayList<Integer> val = new ArrayList<Integer>();
                if (dict.contains(noun)) {
                    val = dict.get(noun);
                    val.add(Integer.parseInt(fields[0]));      
                } else {
                    val.add(Integer.parseInt(fields[0]));      
                }
                dict.put(noun, val);
            }
            dict2.put(Integer.parseInt(fields[0]),fields[1]);
        }
        // StdOut.println(numVertex);
        myGraph = new Digraph(numVertex);
        while (hypernymsFile.hasNextLine()) {
            String line = hypernymsFile.readLine();
            // StdOut.println(line);
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);
                myGraph.addEdge(v, w);
            }
        }

        DirectedCycle dc = new DirectedCycle(myGraph);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }

        int rootNum = 0;
        for (int v = 0; v < myGraph.V(); v++) {
            if (myGraph.indegree(v) != 0 && myGraph.outdegree(v) == 0) rootNum++;
        }
        if (rootNum != 1) {
            throw new IllegalArgumentException();
        }

        mysap = new SAP(myGraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return dict.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return dict.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        ArrayList<Integer> a = dict.get(nounA);
        ArrayList<Integer> b = dict.get(nounB);

        // StdOut.println(a);
        // StdOut.println(b);
        // StdOut.println(mysap.ancestor(a, b));
        return mysap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }

        ArrayList<Integer> a = dict.get(nounA);
        ArrayList<Integer> b = dict.get(nounB);

        // StdOut.println(nounA + a);
        // StdOut.println(nounB + b);

        // StdOut.println(mysap.ancestor(a, b));
        return dict2.get(mysap.ancestor(a, b));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        String fname1 = "h.txt";
        String fname2 = "s.txt";

        WordNet wn = new WordNet(fname2, fname1);
        // for (String n : wn.nouns()) {
            // StdOut.println(n);
        // }
        // StdOut.println(wn.sap("b","f"));
        String nounA = args[0];
        String nounB = args[1];
        StdOut.println(wn.sap(nounA, nounB));
    }
}