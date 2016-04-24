import edu.princeton.cs.algs4.Queue;
public class MyBFS {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public MyBFS(int V) {
        marked = new boolean[V];
        edgeTo = new int[V];
        distTo = new int[V];
    }

    public void sap(Digraph G, int s, int v, int w) {
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        marked[s] = true;
        distTo[s] = 0;

        while(!q.isEmpty()) {
            int currentV = q.dequeue();
            for (int vert: G.adj(currentV)) {
                if (!marked[vert]) {
                    marked[vert] = true;
                    edgeTo[vert] = currentV;
                    distTo[vert] = distTo[currentV] + 1;
                    if (vert == v || vert == w)
                        break;
                    else
                        q.enqueue(vert);
                }
            }
        }
    }
}