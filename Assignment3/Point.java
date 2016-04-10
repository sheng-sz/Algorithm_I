import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    // constructs the point (x, y)
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // // draws this point
    public   void draw() {
        StdDraw.point(x, y);
    }
    // // draws the line segment from this point to that point
    public   void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) return 1;
        if (this.y < that.y) return -1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }
    // the slope between this point and that point
    public double slopeTo(Point that) {
        double y1 = that.y;
        double y0 = this.y;
        double x1 = that.x;
        double x0 = this.x;
        if (x1 == x0 && y1 == y0) return Double.NEGATIVE_INFINITY;
        if (x1 == x0) return Double.POSITIVE_INFINITY; 
        if (y1 == y0) return +0.0;
        return (y1-y0)/(x1-x0);
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double s1 = slopeTo(p1);
            double s2 = slopeTo(p2);
            if (s1 < s2) return -1;
            if (s1 > s2) return 1;
            return 0;
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    public static void main(String[] args) {
        // Point p0 = new Point(1, 0);
        // Point p1 = new Point(3, 4);
        // Point p2 = new Point(2, 2);

        // StdOut.println(p0.toString());
        // StdOut.println(p1.toString());
        // StdOut.println(p2.toString());

        // // p0.draw();;

        // // p1.draw();

        // StdOut.println(p0.slopeTo(p1));

        // p0.drawTo(p1);

    }
}