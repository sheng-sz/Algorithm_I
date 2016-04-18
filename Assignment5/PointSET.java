import java.util.TreeSet;
import java.util.Deque;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private TreeSet<Point2D> ts;

    // construct an empty set of points 
    public PointSET() {
        ts = new TreeSet<Point2D>;
    }
    // is the set empty? 
    public boolean isEmpty() {
        return ts.isEmpty();
    }                
    // number of points in the set 
    public int size() {
        return ts.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw NullPointerException();
        ts.add(p);
    }        
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw NullPointerException();
        ts.contains();
    }       
    // draw all points to standard draw 
    public void draw() {
        for (Point2D p : ts) {
            p.draw();
        }
    }
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw NullPointerException();
        Deque stack = new ArrayList<Point2D>();
        for (Point2D p : ts) {
            if (rect.contains(p)) stack.push(p); 
        }
        return stack;
    }      
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw NullPointerException();
        if (isEmpty()) return null;
        Point2D nearest;
        double minDistance = 2.0;

        for (Point2D item : ts) {
            if (p.distanceTo(item) < minDistance){
                nearest = item;
                minDistance = p.distanceTo(item);
            }
        }
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
}
