public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int[][]
    public Board(int[][] blocks) {

    }
    // board dimension N
    public int dimension() {

    }
    // number of blocks out of place
    public int hamming() {

    }       
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {

    }
    // is this board the goal board?
    public boolean isGoal() {

    }               
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

    }                   
    // does this board equal y?
    public boolean equals(Object y) {

    }        
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }     
    // string representation of this board (in the output format specified below)
    public String toString() {
        String outstring = new String();
        outstring += dimension() + "\n";
        for (int[] row : blocks) {
            outstring += " "
            for (int item : row) {
                outstring += item + "  ";
            }
            outstring += "\n";
        }
    }

    public static void main(String[] args) // unit tests (not graded)
}
