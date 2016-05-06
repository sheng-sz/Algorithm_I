import edu.princeton.cs.algs4.Picture;
// import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
// import java.lang.instrument.Instrumentation;


public class SeamCarver {
    // private Picture pic;
    private double[][] energyArray;
    // private double[][] energyTo;
    // private int[][] pxlTo;
    private double energyToBottom;
    // private int pxlToBottom;
    private int width;
    private int height;
    private int[][] pxls;
    // private int orientation;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException();
        }
        // pic = picture;
        // orientation = 0;

        width = picture.width();
        height = picture.height();

        energyArray   = new double[height][width];
        // energyTo = new double[height][width];
        pxls = new int[height][width];
        // pxlTo    = new int[height][width];

        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                pxls[r][c] = picture.get(c, r).getRGB();
            }
        }

        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                updateEnergy(r, c);
            }
        }
    }
    // current picture
    public Picture picture() {
        // if (orientation == 1){
        //     transpose();
        // }
        Picture out = new Picture(width, height);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                out.set(c, r, new Color(pxls[r][c]));
            }
        }
        return out;
    }
    // width of current picture
    public int width() {
        return width;
    }
    // height of current picture
    public int height() {
        return height;
    }
    // energy of pixel at column x and row y

    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException();
        }
        return energyArray[y][x];
    }

    private double calEnergy(int x, int y) {
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
            return 1000.0;

        double dxr = ((pxls[y][x-1] >> 16) & 0xFF) - ((pxls[y][x+1] >> 16) & 0xFF);
        double dxg = ((pxls[y][x-1] >> 8) & 0xFF) - ((pxls[y][x+1] >> 8) & 0xFF);
        double dxb = ((pxls[y][x-1]) & 0xFF) - ((pxls[y][x+1]) & 0xFF);

        double dyr = ((pxls[y-1][x] >> 16) & 0xFF) - ((pxls[y+1][x] >> 16) & 0xFF);
        double dyg = ((pxls[y-1][x] >> 8) & 0xFF) - ((pxls[y+1][x] >> 8) & 0xFF);
        double dyb = ((pxls[y-1][x]) & 0xFF) - ((pxls[y+1][x]) & 0xFF);

        double dx = dxr * dxr + dxg * dxg + dxb * dxb;
        double dy = dyr * dyr + dyg * dyg + dyb * dyb;

        return Math.sqrt(dx + dy);

    }
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // if (orientation == 0){
            transpose();
        // }
        int[] seam = findVerticalSeam();
            transpose();
            return seam;

    }
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        int[] seam = new int[height];
        energyToBottom = Double.MAX_VALUE;
        double[][] energyTo = new double[height][width];
        int[][] pxlTo = new int[height][width];
        int pxlToBottom;


        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                if (r == 0) {
                    energyTo[r][c] = energyArray[r][c];
                } else {
                    energyTo[r][c] = Double.MAX_VALUE;
                }
            }
        }

        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                relax(r, c, energyTo, pxlTo, seam);
            }
        }

        // seam[height() - 1] = pxlToBottom;
        for (int r = height() - 2; r >= 0; r--) {
            seam[r] = pxlTo[r + 1][seam[r + 1]];
        }

        // StdOut.println(seam[0]);
        return seam;
    }

    private void relax(int r, int c,
                        double[][] energyTo,
                        int[][] pxlTo, int[] seam) {
        if (r == height() - 1) {
            if (energyToBottom > energyTo[r][c]) {
                energyToBottom = energyTo[r][c];
                seam[height - 1] = c;
            }
            return;
        }
        int[] adj;
        if (c == 0) {
            if (width > 1)
                adj = new int[] {c, c + 1};
            else
                adj = new int[] {c};
        }
        else if (c == width - 1)
            adj = new int[] {c - 1, c};
        else
            adj = new int[] {c - 1, c, c + 1};

        for (int cidx : adj) {
            // StdOut.println(height);
            if (energyTo[r + 1][cidx] > energyTo[r][c] + energyArray[r+1][cidx]) {
                energyTo[r + 1][cidx] = energyTo[r][c] + energyArray[r+1][cidx];
                pxlTo[r + 1][cidx] = c;
            }
        }
    }

    // // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }

        checkSeam(seam, width, height);

        if (height <= 1) {
            throw new IllegalArgumentException();
        }

        // if (orientation == 0){
        // StdOut.println("remove horizon");
            transpose();
        // }
        removeVerticalSeam(seam);
            transpose();

    }
    private void transpose() {
        // StdOut.println("width"+width+"height"+height);
        pxls        = transposeArray(pxls);
        energyArray = transposeArray(energyArray);
        // energyTo    = transposeArray(energyTo);
        // pxlTo       = transposeArray(pxlTo);
        int temp = height;
        height = width;
        width = temp;
        // orientation = (~orientation) & 1;
        // StdOut.println(orientation);
    }

    private int[][] transposeArray(int[][] a) {
        int[][] temp = new int[width][height];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                temp[r][c] = a[c][r];
            }
        }
        return temp;
    }

    private double[][] transposeArray(double[][] a) {
        double[][] temp = new double[width][height];
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                temp[r][c] = a[c][r];
            }
        }
        return temp;
    }


    private void checkSeam(int[] seam, int len1, int len2) {
        if (seam.length != len1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < len1 - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                // StdOut.println(i +" "+seam[i]+" "+seam[i+1]);
                throw new IllegalArgumentException();
            }
        }
        for (int s : seam) {
            if (s >= len2 || s < 0) {
                // StdOut.println(i +" "+seam[i]+" "+seam[i+1]);
                throw new IllegalArgumentException();
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }

        checkSeam(seam, height, width);

        if (width <= 1) {
            throw new IllegalArgumentException();
        }


        for (int r = 0; r < height; r++) {
            System.arraycopy(pxls[r], seam[r]+1, pxls[r], seam[r], width - seam[r]-1);
            System.arraycopy(energyArray[r], seam[r]+1, energyArray[r], seam[r], width - seam[r]-1);
            // for (int idx = seam[r]; idx < width-1; idx++) {
            //     pxls[r][idx] = pxls[r][idx + 1];
            //     energyArray[r][idx] = energyArray[r][idx + 1];
            // }
        }
        width--;
        // update energyArray
        for (int r = 0; r < height; r++) {
            updateEnergy(r, seam[r]);
            updateEnergy(r-1, seam[r]);
            updateEnergy(r-2, seam[r]);
            updateEnergy(r+1, seam[r]);
            updateEnergy(r+2, seam[r]);
            updateEnergy(r, seam[r]-1);
            updateEnergy(r, seam[r]-2);
            updateEnergy(r, seam[r]+1);
            updateEnergy(r, seam[r]+2);
        }
        // StdOut.println("seam removed");
    }

    private void updateEnergy(int r, int c) {
        if (c < 0 || c >= width || r < 0 || r >= height)
            return;
        energyArray[r][c] = calEnergy(c, r);
    }
}