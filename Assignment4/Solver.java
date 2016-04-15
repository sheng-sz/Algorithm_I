/*
* @Author: S.Zhang
* @Date:   2016-04-12 20:59:55
* @Last Modified by:   S.Zhang
* @Last Modified time: 2016-04-14 23:34:04
*/
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Deque;
import java.util.ArrayDeque;

public class Solver {
    private int numMoves;
    private Deque<Board> sol;
    private MinPQ<Node> pq;
    private Board myinit;

    private Node root;
    private Node goal;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        myinit = initial;
        numMoves = 0;

        if (isSolvable()) {
            pq = new MinPQ<Node>(this.comparePFH());
            root = new Node(null, initial, 0);

            Node searchNode = root;

            pq.insert(searchNode);

            while (!searchNode.board.isGoal()){
                searchNode = pq.delMin();
                for (Board b: searchNode.board.neighbors()) {
                    pq.insert(new Node(searchNode, b, searchNode.moves+1));
                }
            }
            goal = searchNode;
            sol = new ArrayDeque<Board>();
            Node current;
            current = goal;
            do {
                numMoves++;
                sol.push(current.board);
                current = current.previous;
            } while (current.previous != null);
            sol.push(current.board);
        }
    }

    private class Node {
        public final Board board;
        public final int moves;
        public final int hamPriority;
        public final int manPriority;
        public final Node previous;

        public Node(Node parent, Board board, int moves) {
            this.board = board;
            this.moves = moves;
            this.manPriority = board.manhattan() + this.moves;
            this.hamPriority = board.hamming() + this.moves;
            previous = parent;
        }
        public void printP() {
            StdOut.println(board.toString());
            StdOut.println("hamming priority = " + hamPriority);
        }
    }

    private Comparator<Node> comparePFM() {
        return new PFM();
    } 

    private Comparator<Node> comparePFH() {
        return new PFH();
    } 


    private class PFM implements Comparator<Node> {
        public int compare(Node x, Node y) {
            if (x.manPriority == y.manPriority) {
                if (x.hamPriority > y.hamPriority) return +1;
                if (x.hamPriority < y.hamPriority) return +1;
                return 0;
            }
            else if (x.manPriority < y.manPriority) return -1;
            else return +1;
        }
    }

    private class PFH implements Comparator<Node> {
        public int compare(Node x, Node y) {
            if (x.hamPriority == y.hamPriority) {
                if (x.manPriority > y.manPriority) return +1;
                if (x.manPriority < y.manPriority) return +1;
                return 0;
            }
            else if (x.hamPriority < y.hamPriority) return -1;
            else return +1;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        // return true;
        Board myinit2 = myinit.twin();

        // StdOut.println("init is " + myinit.toString());
        // StdOut.println("twin is " + myinit2.toString());

        MinPQ<Node> pq1 = new MinPQ<Node>(comparePFH());
        MinPQ<Node> pq2 = new MinPQ<Node>(comparePFH());

        Node rt1 = new Node(null, myinit, 0);
        Node rt2 = new Node(null, myinit2, 0);
        Node sn1 = rt1;
        Node sn2 = rt2;
        pq1.insert(sn1);
        pq2.insert(sn2);

        boolean result = false;
        Node newnode2;

        int mv = 0;

        while (!sn1.board.isGoal() && !sn2.board.isGoal()) {
            // StdOut.println("------------------");
            // StdOut.println("moves = " + mv);
            sn1 = pq1.delMin();
            sn2 = pq2.delMin();
            // mv++;

            for (Board b: sn1.board.neighbors()) {
                if (sn1.previous == null) {
                    pq1.insert(new Node(sn1, b, sn1.moves+1));
                } else if (!b.equals(sn1.previous.board))
                    pq1.insert(new Node(sn1, b, sn1.moves+1));
            }

            // StdOut.println("***neighbors***");
            for (Board b: sn2.board.neighbors()) {
                if (sn2.previous == null) {
                    newnode2 = new Node(sn2, b, sn2.moves+1);
                    pq2.insert(newnode2);
                    // newnode2.printP();
                } else if (!b.equals(sn2.previous.board)) {
                    newnode2 = new Node(sn2, b, sn2.moves+1);
                    pq2.insert(newnode2);
                    // newnode2.printP();
                }
            }
        }
            // StdOut.println("***next serarch node***");
            // sn2.printP();

            if (sn2.board.isGoal()) {
                result = false;
            } else {
                result = true;
            }
        // StdOut.println("******");
        // StdOut.println(result);

        return result;
    } 

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable() == false) return -1;
        return numMoves;
    }       
    // sequence of boards in a shortest solution; null if unsolvable             
    public Iterable<Board> solution() {

        return sol;
    }      
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}