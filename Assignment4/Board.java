import java.util.Arrays;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private final int[][] blocks;
    private final int dim;
    private  int r0;
    private  int c0;

    public Board(int[][] blocks) {
        dim = blocks[0].length;
        this.blocks = new int[dim][dim];

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                this.blocks[r][c] = blocks[r][c];
            }
        }

        outerloop:
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (blocks[r][c] == 0) {
                    r0 = r;
                    c0 = c;
                    break outerloop;
                }
            }
        }

    }
    // board dimension N
    public int dimension() {
        return dim;
    }
    // number of blocks out of place
    public int hamming() {
        int oop = 0;
        int i = 0;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                i++;
                if (blocks[r][c] == 0) continue;
                if (blocks[r][c] != i) oop++;
            }
        }
        return oop;
    } 
    // // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        int correct_c;
        int correct_r;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (blocks[r][c] == 0) continue;
                correct_c = (blocks[r][c] - 1) % dim;
                correct_r = (blocks[r][c] - 1 - correct_c)/dim;
                sum += Math.abs(correct_c - c) + 
                        Math.abs(correct_r - r); 
                // StdOut.println(r+""+c+""+correct_r+""+correct_c);
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int i=1;
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (r == dim-1 && c == dim-1) {
                    if (blocks[r][c] != 0)
                        return false;
                }
                else if (blocks[r][c] != i++) {
                    return false;
                }
            }
        }
        return true;
    }               
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int r1, c1, r2, c2;
        while (true) {
            r1 = StdRandom.uniform(dim);
            c1 = StdRandom.uniform(dim);
            if (!(r1 == r0 && c1 == c0)) break;
        }
        while (true) {
            r2 = StdRandom.uniform(dim);
            c2 = StdRandom.uniform(dim);
            if ((!(r2 == r0 && c2 == r0)) && (!(r2 == r1 && c2 == c1))) break;
        }
        int temp1 = blocks[r1][c1];

        int[][] newBlocks = new int[dim][dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                newBlocks[r][c] = blocks[r][c];
            }
        }
        newBlocks[r1][c1] = newBlocks[r2][c2];
        newBlocks[r2][c2] = temp1;
        return new Board(newBlocks);
    } 
    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return equal2DArray(that.blocks);
    }

    private boolean equal2DArray(int[][] that) {
        boolean result = true;
        outerloop:
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (blocks[r][c] != that[r][c]) {
                    result = false;
                    break outerloop;
                }
            }
        }
        return result;
    }

    private int[][] copyBlocks() {
        int blocksCopy[][] = new int[dim][dim];

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                blocksCopy[r][c] = blocks[r][c];
            }
        } 
        return blocksCopy;
    }

    // // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> nb = new Stack<Board>();
        int[][] blocksCopy = new int[dim][dim];

        int temp;
        if (r0 != 0){
            blocksCopy = copyBlocks();
            temp = blocksCopy[r0-1][c0];
            blocksCopy[r0-1][c0] = 0;
            blocksCopy[r0][c0] = temp;
            nb.push(new Board(blocksCopy));
        }
        if (r0 != dim-1){
            blocksCopy = copyBlocks();
            temp = blocksCopy[r0+1][c0];
            blocksCopy[r0+1][c0] = 0;
            blocksCopy[r0][c0] = temp;
            nb.push(new Board(blocksCopy));
        }
        if (c0 != 0){
            blocksCopy = copyBlocks();
            temp = blocksCopy[r0][c0-1];
            blocksCopy[r0][c0-1] = 0;
            blocksCopy[r0][c0] = temp;
            nb.push(new Board(blocksCopy));
        }
        if (c0 != dim-1){
            blocksCopy = copyBlocks();
            temp = blocksCopy[r0][c0+1];
            blocksCopy[r0][c0+1] = 0;
            blocksCopy[r0][c0] = temp;
            nb.push(new Board(blocksCopy));
        }
        return nb;
    }     

    // string representation of this board (in the output format specified below)
    public String toString() {
        String outstring = new String();
        outstring += dimension() + "\n";
        for (int[] row : blocks) {
            outstring += " ";
            for (int item : row) {
                outstring += item + "  ";
            }
            outstring += "\n";
        }
        return outstring;
    }



    // unit tests (not graded)
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
        // StdOut.println(initial.isGoal());

        // Board x = initial.twin();

        // StdOut.println(x.toString());
        Iterable<Board> my_nb = initial.neighbors();
        for (Board bd: my_nb) {
            StdOut.println(bd.toString());
        }

        StdOut.println("test equals");
        Board x = new Board(blocks);
        StdOut.println(initial.equals(x));
        StdOut.println(initial.equals(x.twin()));
        StdOut.println(initial.manhattan());

    }
}
