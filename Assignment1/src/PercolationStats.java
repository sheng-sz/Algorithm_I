import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// import java.util.Arrays;

public class PercolationStats {
   // perform T independent experiments on an N-by-N grid

    private double [] threshold;
    private int numT;

   public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N>0. T>0.");
        }
        threshold = new double[T];
        numT = T;
        for (int i = 0; i < T; i++)
            threshold[i] = 0;
        // Arrays.fill(threshold,0);
        for (int i = 0; i < T; i++) {
                Percolation perc = new Percolation(N);
                while (!perc.percolates()) {
                    int rand1 = StdRandom.uniform(N)+1;
                    int rand2 = StdRandom.uniform(N)+1;
                    // System.out.println("Open "+rand1+rand2);
                    if (!perc.isOpen(rand1, rand2)) {
                        perc.open(rand1, rand2);    
                        threshold[i]++;                    
                    }
                }
                threshold[i] /= N*N;
                
                // threshold[i] = perc[i].getNumOpened()/N/N;
                // System.out.println(threshold[i]);

            }            
   }

   // sample mean of percolation threshold
   public double mean() {
        return StdStats.mean(threshold);
   }
   // sample standard deviation of percolation threshold
   public double stddev() {
        return StdStats.stddev(threshold);
   }                    
   // low  endpoint of 95% confidence interval
   public double confidenceLo() {
        return mean()-1.96*stddev()/Math.sqrt(numT);
   }              
    // high endpoint of 95% confidence interval
   public double confidenceHi() {
        return mean()+1.96*stddev()/Math.sqrt(numT);
   }            
   // test client (described below)
   public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        Stopwatch watch = new Stopwatch();

        PercolationStats stat = new PercolationStats(N, T);
        System.out.println(watch.elapsedTime());

        System.out.printf("%-30s = %f\n", "mean", stat.mean());
        System.out.printf("%-30s = %f\n", "stdev", stat.stddev());
        System.out.printf("%-30s = %f,%f\n", "95% confidence level",
         stat.confidenceHi(), stat.confidenceLo());
   }
}