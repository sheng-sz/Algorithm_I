/*
* @Author: S.Zhang
* @Date:   2016-04-12 20:59:55
* @Last Modified by:   S.Zhang
* @Last Modified time: 2016-04-16 15:35:24
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
    private boolean solvable;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        numMoves = 0;

        MinPQ<Node> pq = new MinPQ<Node>(this.comparePFM());
        MinPQ<Node> pqTwin = new MinPQ<Node>(this.comparePFM());

        Node root = new Node(null, initial, 0);
        Node rootTwin = new Node(null, initial.twin(), 0);

        Node searchNode = root;
        Node searchNodeTwin = rootTwin;

        pq.insert(searchNode);
        pqTwin.insert(searchNodeTwin);
        // StdOut.println("setup");

        while (!searchNode.board.isGoal() && !searchNodeTwin.board.isGoal()){
            searchNode = pq.delMin();
            // StdOut.println("--moves--" + searchNode.moves);
            // StdOut.println("sn mp " + searchNode.manPriority);
            searchNodeTwin = pqTwin.delMin();
            // StdOut.println("sntwin mp " + searchNodeTwin.manPriority);
            for (Board b: searchNode.board.neighbors()) {
                if (searchNode != root){
                    if (!b.equals(searchNode.previous.board))
                        pq.insert(new Node(searchNode, b, searchNode.moves+1));
                } else
                    pq.insert(new Node(searchNode, b, searchNode.moves+1));
            }
            for (Board b: searchNodeTwin.board.neighbors()) {
                if (searchNodeTwin != rootTwin) {
                    if (!b.equals(searchNodeTwin.previous.board))
                        pqTwin.insert(new Node(searchNodeTwin, b, searchNodeTwin.moves+1));
                } else
                    pqTwin.insert(new Node(searchNodeTwin, b, searchNodeTwin.moves+1));
            }
        }

        if (searchNode.board.isGoal()) {
            sol = new ArrayDeque<Board>();
            Node current = searchNode;

            while (true) {
                if (current == root) break;
                else {
                    sol.push(current.board);
                    current = current.previous;
                    numMoves++;
                }                
            }
            sol.push(current.board);
            solvable = true;
        } else {
            sol = null;
            solvable = false;
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
        return solvable;
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
        StdOut.println(initial.toString());

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