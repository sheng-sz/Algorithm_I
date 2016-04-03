import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// import java.util.Arrays;

public class Percolation {
    private boolean [][] grids;
    // private int [][] grids_UF;
    private WeightedQuickUnionUF unionFind;
    private int top;
    private int bottom;
    private int dim;
    private double numOpened;
    // create N-by-N grid, with all sites blocked

    // private double getNumOpened(){
    //     return numOpened;
    // }

    // private void printGrid(){
    //     System.out.println("Grids:");
    //     int rowNum = 0;
    //     for (boolean [] row : grids ) {
    //         System.out.println("grids[" + rowNum + "] "+Arrays.toString(row));
    //         rowNum++;
    //     }
    // }

    // private void printUF(){
    //     System.out.println("UF Data structure:");
    //     System.out.println(unionFind.find(dim*dim));

    //     for (int idx = 0; idx < dim*dim ; idx++) {
    //         System.out.printf("%4d",unionFind.find(idx));
    //         if ((idx+1)%5 == 0){
    //             System.out.print("\n");
    //         }
    //     }

    //     System.out.println(unionFind.find(dim*dim+1));

    // }

    public Percolation(int N) {
        if (N <= 0) {
            System.out.println("Error: N should be greater thatn 0");
            throw new IllegalArgumentException();
        }

        numOpened = 0;
        grids = new boolean[N+1][N+1];
        dim  = N;
        // grids_UF = new int[N][N];

        for (int i = 1; i < N+1; i++) {
            for (int j = 1; j < N+1; j++) {
                grids[i][j] = false;
            }
        }
        // for (boolean[] row : grids ) {
        //     Arrays.fill(row,false);
        // }

        unionFind = new WeightedQuickUnionUF(N*N+2);
        top = N*N;
        bottom = N*N+1;
        for (int i = 0; i < N; i++) {
            unionFind.union(i, top);
            //unionFind.union(i+N*(N-1), bottom);
        }

    }

    private boolean isInBound(int i, int j) {
        return !(i <= 0 || i > dim || j <= 0 || j > dim);
    }
    private void unionBottom(int idx, int i) {
        if (i == dim) {
            unionFind.union(idx, bottom);
        }
       // open site (row i, column j) if it is not open already
    }
    public void open(int i, int j) {
        if (i <= 0 || i > dim || j <= 0 || j > dim) {
            System.out.println("Error: OOB"+"ij"+i+j);
            throw new IndexOutOfBoundsException();
        }
        int idx = (j-1)+ dim*(i-1);
        if (!isOpen(i, j)) {
            numOpened++;
            // System.out.println("site "+i+j+" got opened");
            grids[i][j] = true;
            unionBottom(idx, i);
            // check (i-1,j) (i+1,j) (i,j-1) (i,j+1), if open, connect
            if (isInBound(i-1, j)) {
                if (isOpen(i-1, j)) {
                    unionFind.union(idx, idx-dim);
                }
            }
            if (isInBound(i+1, j)) {
                if (isOpen(i+1, j)) {
                    unionFind.union(idx, idx+dim);
                    unionBottom(idx, i+1);

                }
            }
            if (isInBound(i, j-1)) {
                if (isOpen(i, j-1)) {
                    unionFind.union(idx, idx-1);
                }
            }
            if (isInBound(i, j+1)) {
                if (isOpen(i, j+1)) {
                    unionFind.union(idx, idx+1);
                }
            }
        }
    }          
       // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {

        if (i <= 0 || i > dim || j <= 0 || j > dim) {
            System.out.println("Error: OOB");
            throw new IndexOutOfBoundsException();
        }
        // if (grids[i][j]) {
        //     // System.out.println("site " + i + j + " is open");    
        // }
        return grids[i][j];
    }
       // is site (row i, column j) full?     
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > dim || j <= 0 || j > dim) {
            System.out.println("Error: OOB");
            throw new IndexOutOfBoundsException();
        }        // if (!grids[i][j]) {
        //     // System.out.println("site " + i + j + " is full");

        // }

            int idx = (i-1)*dim + j-1;
            return unionFind.connected(idx, top) && isOpen(i, j);
        }


       // does the system percolate?     
        public boolean percolates() {
            boolean debug = false;
        // if (debug) {
        //     printUF();
        //     printGrid();
        //     System.out.println(numOpened);
        // }

            boolean result = unionFind.connected(top, bottom) && numOpened > 0;
        // if (result && debug) {
        //     System.out.println("Percolates!");
        // }

            return result;
        }             
       // test client (optional)
        public static void main(String[] args) {
            Percolation perc = new Percolation(5);
            // perc.printGrid();
            // perc.printUF();

            // // perc.percolates();
            System.out.println(perc.isOpen(2, 3));
            System.out.println(perc.isFull(1, 1));

            perc.open(2, 3);
            perc.open(1, 1);
            System.out.println(perc.isOpen(2, 3));
            System.out.println(perc.isFull(2, 3));
            System.out.println(perc.isFull(1, 1));

            // perc.open(4, 3); 
            // perc.open(3, 2); 
            // perc.open(3, 4); 
            // perc.printGrid();
            // perc.printUF();
            // perc.open(3, 3);
            // perc.open(1, 3);
            // perc.open(5, 3);
            // perc.printGrid();
            // perc.printUF();


            // perc.isOpen(1,1);
            // perc.printGrid();
            // perc.percolates();
            // perc.open(3,1);
            // perc.open(4,1);
            // System.out.println(perc.percolates());

            // perc.open(5,1);
            // perc.open(2,3);
            // perc.open(2,2);


            // perc.printGrid();
            // System.out.println(perc.percolates());
            // perc.printUF();


        // System.out.println(perc.grids);

        }
    }
