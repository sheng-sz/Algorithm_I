/*
* @Author: S.Zhang
* @Date:   2016-04-23 20:32:48
* @Last Modified by:   S.Zhang
* @Last Modified time: 2016-04-24 23:26:46
*/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Outcast {
    // constructor takes a WordNet object
    private WordNet wn;

    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] dist = new int[nouns.length];
        int max = 0;
        int maxidx = 0;
        for (int i = 0; i < nouns.length; i++) {
            dist[i] = 0;
            for (int j = 0; j < nouns.length; j++) {
                dist[i] += wn.distance(nouns[i], nouns[j]);
            }
            if (dist[i] > max) {
                max = dist[i];
                maxidx = i;
            }
        }
        return nouns[maxidx];
    }
    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
