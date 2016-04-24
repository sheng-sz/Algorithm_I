/*
* @Author: S.Zhang
* @Date:   2016-04-23 20:32:24
* @Last Modified by:   S.Zhang
* @Last Modified time: 2016-04-24 15:05:33
*/

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph myGraph;
    private int length;
    private int ancestor;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }
        myGraph = new Digraph(G);
    }

    private void searchAncestor(BreadthFirstDirectedPaths bfs, int vertex) {
        Queue<Integer> qVertex = new Queue<Integer>();
        boolean[] marked;
        int[] distTo;

        marked = new boolean[myGraph.V()];
        distTo = new int[myGraph.V()];

        qVertex.enqueue(vertex);
        // StdOut.println("en q w" + w);

        marked[vertex] = true;
        distTo[vertex] = 0;

        while (!qVertex.isEmpty()) {
            int currentVertex = qVertex.dequeue();
            // StdOut.println("de q w" + currentW);
            if (bfs.hasPathTo(currentVertex)) {
                int tempDist = distTo[currentVertex] + bfs.distTo(currentVertex);
                if (length > tempDist) {
                    length = tempDist;
                    ancestor = currentVertex;
                    // StdOut.println("ancestor = " + ancestor);

                }
            }
            for (int adj: myGraph.adj(currentVertex)) {
                if (marked[adj] != true) {
                    qVertex.enqueue(adj);
                    // StdOut.println("en q w" + adjW);
                    marked[adj] = true;
                    distTo[adj] = distTo[currentVertex] + 1;
                }
            }
        }
    }

    private void searchAncestor(BreadthFirstDirectedPaths bfs, Iterable<Integer> vertex) {
        Queue<Integer> qVertex = new Queue<Integer>();
        boolean[] marked;
        int[] distTo;

        marked = new boolean[myGraph.V()];
        distTo = new int[myGraph.V()];

        for (int v : vertex) {
            qVertex.enqueue(v);
            marked[v] = true;
            distTo[v] = 0; 
        }

        while (!qVertex.isEmpty()) {
            int currentVertex = qVertex.dequeue();
            // StdOut.println("de q w" + currentW);
            if (bfs.hasPathTo(currentVertex)) {
                int tempDist = distTo[currentVertex] + bfs.distTo(currentVertex);
                if (length > tempDist) {
                    length = tempDist;
                    ancestor = currentVertex;
                    // StdOut.println("ancestor = " + ancestor);

                }
            }
            for (int adj: myGraph.adj(currentVertex)) {
                if (marked[adj] != true) {
                    qVertex.enqueue(adj);
                    // StdOut.println("en q w" + adjW);
                    marked[adj] = true;
                    distTo[adj] = distTo[currentVertex] + 1;
                }
            }
        }
    }

    private void runBFS(int v, int w) {
        length = Integer.MAX_VALUE;
        ancestor = -1;

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(myGraph, v);
        Queue<Integer> qW = new Queue<Integer>();
        searchAncestor(bfsV, w);

        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(myGraph, w);
        searchAncestor(bfsW, v);
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v >= myGraph.V() || w >= myGraph.V()) {
            throw new IndexOutOfBoundsException();
        }
        runBFS(v, w);
        return ancestor == -1 ? -1 : length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {

        if (v >= myGraph.V() || w >= myGraph.V()) {
            throw new IndexOutOfBoundsException();
        }
        runBFS(v, w);
        return ancestor;
    }

    private void runBFS(Iterable<Integer> v, Iterable<Integer> w) {
        length = Integer.MAX_VALUE;
        ancestor = -1;

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(myGraph, v);
        searchAncestor(bfsV, w);

        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(myGraph, w);
        searchAncestor(bfsV, v);

    }


    // // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException();
        for (int vi: v) {
            if (vi >= myGraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        for (int wi: w) {
            if (wi >= myGraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        runBFS(v, w);
        return ancestor == -1 ? -1 : length;
    }

    // // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new NullPointerException();
        for (int vi: v) {
            if (vi >= myGraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        for (int wi: w) {
            if (wi >= myGraph.V()) {
                throw new IndexOutOfBoundsException();
            }
        }
        runBFS(v, w);
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
