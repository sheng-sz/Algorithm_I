/*
* @Author: S.Zhang
* @Date:   2016-04-12 20:59:55
* @Last Modified by:   S.Zhang
* @Last Modified time: 2016-04-14 20:58:18
*/
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Solver {
    private int numMoves;
    private Stack<Board> sol;
    private MinPQ<Board> gametree;
    private Board myinit;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        myinit = initial;

        

        if (solvable == true) {
            gametree = new MinPQ<Board>(this.comparePFH());
            sol = new Stack<Board>();
            numMoves = 0;

            gametree.insert(initial);

            Board searchNode = initial;
            Board minNode;

            while (!searchNode.isGoal()){
                minNode = gametree.delMin();
                numMoves++;
                sol.push(minNode);
                for (Board b: minNode.neighbors()) {
                    gametree.insert(b);
                }
                searchNode = gametree.min();
            }
            sol.push(searchNode);
        }
    }

    private Comparator<Board> comparePFM() {
        return new PFM();
    } 

    private Comparator<Board> comparePFH() {
        return new PFH();
    } 


    private class PFM implements Comparator<Board> {
        public int compare(Board x, Board y) {
            if (x.manhattan()+numMoves == y.manhattan()+numMoves) return 0;
            else if (x.manhattan()+numMoves < y.manhattan()+numMoves) return -1;
            else return +1;
        }
    }

    private class PFH implements Comparator<Board> {
        public int compare(Board x, Board y) {
            if (x.hamming()+numMoves == y.hamming()+numMoves) return 0;
            else if (x.hamming()+numMoves < y.hamming()+numMoves) return -1;
            else return +1;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        Board myinit2 = myinit.twin();
        StdOut.println("twin is "+myinit2.toString());

        Solver svr1 = new Solver(myinit);
        Solver svr2 = new Solver(myinit2);

        MinPQ<Board> gt1 = new MinPQ<Board>(svr1.comparePFH());
        MinPQ<Board> gt2 = new MinPQ<Board>(svr2.comparePFH());

        gt1.insert(myinit);
        gt2.insert(myinit2);



        // sol = new Stack<Board>();
        // numMoves = 0;


        Board sn1 = myinit;
        Board sn2 = myinit2;
        Board minNode1;
        Board minNode2;
        boolean result = true;

        while (true){
            StdOut.print(".");

            minNode1 = gt1.delMin();
            minNode2 = gt2.delMin();
            // sol.push(minNode);
            for (Board b: minNode1.neighbors()) {
                gt1.insert(b);
            }
            for (Board b: minNode2.neighbors()) {
                gt2.insert(b);
            }
            sn1 = gt1.min();
            sn2 = gt2.min();
            if (sn2.isGoal()) {
                result = false;
                break;
            }
            if (sn1.isGoal()) {
                break;
            }
        }
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
        // if (false)
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
