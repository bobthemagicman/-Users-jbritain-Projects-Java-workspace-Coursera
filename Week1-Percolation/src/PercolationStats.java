import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final String ILLEGAL_ARG_LESS_THAN_0 = "Illegal argument, %s "
            + "cannot be less than or equal to 0";

    private final double[] thresholds;
    private final int rowSize;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(final int n, final int trials) {
        rowSize = n;
        
        if (rowSize <= 0) {
            throw new IllegalArgumentException(String.format(ILLEGAL_ARG_LESS_THAN_0,
                    "n"));
        }

        if (trials <= 0) {
            throw new IllegalArgumentException(String.format(ILLEGAL_ARG_LESS_THAN_0,
                    "trials"));
        }
        
        thresholds = new double[trials];

        int x = trials;
        while (x > 0) {
            Percolation p = new Percolation(rowSize);

            double openSites = 0;
            while (!p.percolates()) {
                final int row = StdRandom.uniform(1, rowSize + 1);
                final int col = StdRandom.uniform(1, rowSize + 1);

                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    openSites++;
                } else {
                    continue;
                }
            }

            thresholds[--x] = openSites / (rowSize * rowSize);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(thresholds.length));
    }

    // test client (described below)
    public static void main(String[] args) {
        final int rowSize = Integer.parseInt(args[0]);
        final int trials = Integer.parseInt(args[1]);

        final PercolationStats ps = new PercolationStats(rowSize, trials);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", "
                + ps.confidenceHi());
    }
}
