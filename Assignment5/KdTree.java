import java.util.Deque;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root;
    private int numNode;
    // construct an empty set of points 
    public KdTree() {
        root = null;
    }

    private class Node extends Comparable {
        private double key;
        private double val;
        private Node left, right;

        public Node(Point2D p, int level) {
            if (level % 2 == 0) {
                key = p.x();
                val = p.y();
            } else {                
                key = p.y();
                val = p.x();
            }
        }
    }

    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }
    // number of points in the set 
    public int size() {
        return numNode;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw NullPointerException();
        root = put(root, p, 0);
    }

    private Node put(Node x, Point2D p, int level) {
        if (x == null) return new Node(p, level);


    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw NullPointerException();
    }       
    // draw all points to standard draw 
    public void draw() {

    }
    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw NullPointerException();

    }      
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw NullPointerException();

    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
}