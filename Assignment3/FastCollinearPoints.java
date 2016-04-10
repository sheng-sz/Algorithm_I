import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private int numSeg;
    private LineSegment[] ls;
    // finds all line segments containing 4 or more points

    private void printPoints(Point[] points) {
        for (Point tp : points ) {
            StdOut.print(tp.toString());
        }
        StdOut.println();
    }

    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new NullPointerException("Brute force constructor argument == null");
        for (Point tp : points) {
             if (tp == null) {
                throw new NullPointerException("Brute force constructor point == null");
             }
         } 

        numSeg = 0;
        ls = new LineSegment[points.length - 1];
        double[] slope = new double[points.length - 1];
        int next;
        Arrays.sort(points);
        // printPoints(points);

        for (int i = 0; i < points.length-3; i++) {
            // StdOut.println(i+points[i].toString());
            // next = i+2;
            next = i;
            Arrays.sort(points, i+1, points.length, points[i].slopeOrder());
        // printPoints(points);


            if (points[i].slopeTo(points[i+1]) == Double.NEGATIVE_INFINITY)
                throw new IllegalArgumentException("repeated point");

            // while(points[i].slopeTo(points[next-1]) == points[i].slopeTo(points[next]) 
            //         && points[i].slopeTo(points[next]) == points[i].slopeTo(points[next+1])) {
            //     next++;
            //     if (next == points.length-1) break;
            // }

            for (int j = i+1; j< points.length-2; j++) {
                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j+1])
                    && points[i].slopeTo(points[j+1]) == points[i].slopeTo(points[j+2])) {
                    next = j+2;
                    // StdOut.println("ijnext"+ i+"-"+j+"-"+next+points[next].toString());
                } else {
                    // StdOut.println("j"+j +"not found");
                    if (next != i) break;
                }
            }

            if (next != i)
                ls[numSeg++] = new LineSegment(points[i], points[next]);
            Arrays.sort(points, i+1, points.length);

        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lsout = new LineSegment[numSeg];
        for (int i = 0; i < numSeg; i++) {
            lsout[i] = ls[i];
        }
        return lsout;
    }

    // client
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);

        int N = in.readInt();
        // StdOut.println(N);

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            // StdOut.println("x" + x + "y" + y);

        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // StdOut.println("sorting done");

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}