import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;
    private int numNode;
    private boolean containsP;
    // construct an empty set of points 
    public KdTree() {
        root = null;
        containsP = false;
        numNode = 0;
    }

    private class Node {
        // private double key;
        // private double val;
        private Point2D p;
        private RectHV rect;
        private Node left, right;

        public Node(Point2D p,
                    RectHV rect) {
            this.p = new Point2D(p.x(), p.y());
            this.rect = new RectHV(rect.xmin(), rect.ymin(),
                                   rect.xmax(), rect.ymax());
            // if (level % 2 == 0) {
            //     key = p.x();
            //     val = p.y();
            // } else {                
            //     key = p.y();
            //     val = p.x();
            // }
        }

        public double getX() {
            return p.x();
        }

        public double getY() {
            return p.y();
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
        if (p == null) throw new NullPointerException();
        root = put(root, p, 0,
                   0, 1, 0, 1);
    }

    private Node put(Node x, Point2D p, int level, 
                     double xmin, double xmax, double ymin, double ymax) {
        if (x == null) {
            numNode++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        if (level % 2 == 0) {

            if (x.p.x() == p.x() && x.p.y() == p.y()) return x;

            if (x.p.x() > p.x() ) x.left = put(x.left, p, level+1,
                                            xmin, x.p.x(), ymin, ymax);
            else x.right = put(x.right, p, level+1,
                               x.p.x(), xmax, ymin, ymax);
            return x;

        } else {
            if (x.p.y() == p.y() && x.p.x() == p.x()) return x;

            if (x.p.y() > p.y() ) x.left = put(x.left, p, level+1,
                                            xmin, xmax, ymin, x.p.y());
            else x.right = put(x.right, p, level+1,
                               xmin, xmax, x.p.y(), ymax);
            return x;
        }
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        
        return search(root, p, 0);
    } 


    private boolean search(Node x, Point2D p, int level) {
        boolean result = false;
        if (x == null) {
            return false;
        }

        if (x.p.x() == p.x() && x.p.y() == p.y()) {
            return true;
        }

        if (level % 2 == 0) {
            if (x.p.x() > p.x()) 
                result = search(x.left, p, level+1);
            else 
                result = search(x.right, p, level+1);
        } else {
            if (x.p.y() > p.y()) 
                result = search(x.left, p, level+1);
            else 
                result = search(x.right, p, level+1);
        }

        return result;
    }

    // draw all points to standard draw 
    public void draw() {
        drawLevel(root, 0, 0, 1, 0, 1);
        StdDraw.show();
    }

    private void drawLevel(Node x, int level, double xmin, double xmax, double ymin, double ymax) {
        if (x == null) return;

        if (level % 2 == 0) {
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(x.getX(), x.getY());
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(x.getX(), ymin, x.getX(), ymax);
            // StdOut.println("ymin " + ymin + " ymax " + ymax);
            // if (x.right != null)
                // StdOut.println("xright" + x.right.p.toString());
            drawLevel(x.left, level + 1, xmin, x.getX(), ymin, ymax);
            drawLevel(x.right, level + 1, x.getX(), xmax, ymin, ymax);
        } else {
            StdDraw.setPenRadius(.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(x.getX(), x.getY());
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(xmin, x.getY(), xmax, x.getY());
            StdOut.println("xmin " + xmin + " xmax " + xmax);
            drawLevel(x.left, level + 1, xmin, xmax, ymin, x.getY());
            drawLevel(x.right, level + 1, xmin, xmax, x.getY(), ymax);
        }
    }

    // all points that are inside the rectangle 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();

        Queue<Point2D> q = new Queue<Point2D>();
        rangeSearch(root, rect, q);

        return q;
    } 

    private void rangeSearch(Node n, RectHV that, Queue<Point2D> q) {
        if (n == null) return;

        if (n.rect.intersects(that)) {
            if (that.contains(n.p)) q.enqueue(n.p);
            rangeSearch(n.left, that, q);
            rangeSearch(n.right, that, q);
        }
        return;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("my error");
        if (numNode == 0) return null;

        Point2D closest = root.p;
        closest = nearestSearch(root, p, closest, 0);
        return closest;
    }

    private Point2D nearestSearch(Node n, Point2D target, Point2D closest, int level) {
        if (n == null)
            return closest;
        if (n.rect.distanceTo(target) > target.distanceTo(closest)) 
            return closest;

        if (n.p.distanceTo(target) < target.distanceTo(closest))
            closest = n.p;

        if (level % 2 == 0) {
            if (n.getX() > target.x()) {
                closest = nearestSearch(n.left, target, closest, level+1);
                closest = nearestSearch(n.right, target, closest, level+1);
            } else {
                closest = nearestSearch(n.right, target, closest, level+1);
                closest = nearestSearch(n.left, target, closest, level+1);
            }
        } else {
            if (n.getY() > target.y()) {
                closest = nearestSearch(n.left, target, closest, level+1);
                closest = nearestSearch(n.right, target, closest, level+1);
            } else {
                closest = nearestSearch(n.right, target, closest, level+1);
                closest = nearestSearch(n.left, target, closest, level+1);
            }
        }
        return closest;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
}