import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numSeg;
    private LineSegment[] ls;

// finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("Brute force constructor argument == null");
        for (Point tp : points) {
             if (tp == null) {
                throw new NullPointerException("Brute force constructor point == null");
             }
         } 

        Point[] mypoints = Arrays.copyOf(points, points.length);

        numSeg = 0;
        // StdOut.println(points.length);
        Arrays.sort(mypoints);

        ls = new LineSegment[mypoints.length - 1];
        for (int i = 0; i < mypoints.length; i++) {
            for (int j = i + 1; j < mypoints.length; j++) {
                if (mypoints[i].slopeTo(mypoints[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("repeated point");
                for (int k = j + 1; k < mypoints.length; k++) {
                    if (mypoints[i].slopeTo(mypoints[j]) == mypoints[j].slopeTo(mypoints[k])) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (mypoints[j].slopeTo(mypoints[k]) == mypoints[k].slopeTo(mypoints[l])) {
                                ls[numSeg++] = new LineSegment(mypoints[i], mypoints[l]);
                            }
                        }
                    }
                }
            }
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}

}
