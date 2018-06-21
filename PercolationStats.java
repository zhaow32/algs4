import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats
{
    private int total_trials;
    private double[] resultset;
    private Percolation p;
    
    /*
     * perform trials independent experiments on n-by-n grid
     */
    public PercolationStats(int n,int trials)
    {
        if (n<= 0 || trials <= 0)
            throw new IllegalArgumentException("invalid inputs!");
        
        total_trials=trials;
        resultset=new double[total_trials];
        for (int i=0;i<total_trials;i++)
        {
            p=new Percolation(n);
            int opensite=0;
            while(!p.percolates())
            {
                int x=StdRandom.uniform(1,n+1);
                int y=StdRandom.uniform(1,n+1);
                if(!(p.isOpen(x,y)))
                {
                    p.open(x,y);
                    opensite++;
                }
            }
            double result=(double)opensite/(n*n);
            resultset[i]=result;
        }
    }
    /*
     * sample mean of percolation threshold
     */
    public double mean()
    {
        return StdStats.mean(resultset);
    }
    /*
     * sample standard deviation of percolation threshold
     */
    public double stddev()
    {
        return StdStats.stddev(resultset);
    }
    /*
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo()
    {
        return mean()-((1.96*stddev())/Math.sqrt(total_trials));
    }
    /*
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi()
    {
        return mean()+((1.96*stddev())/Math.sqrt(total_trials));
    }
    
    public static void main(String[] args)
    {
        
        // trial#1
        System.out.println("trial#1  200 100\n");
        PercolationStats p1=new PercolationStats(200,100);
        System.out.println("Mean=                   "+ p1.mean());
        System.out.println("Stddev=                 "+ p1.stddev());
        String confidence1 = p1.confidenceLo() + "," + p1.confidenceHi();
        System.out.println("95% confidence interval="+ confidence1);
        System.out.println("\n\n");
        // trial #2
        System.out.println("trial#2 200 100\n");
        PercolationStats p2=new PercolationStats(200,100);
        System.out.println("Mean=                   "+ p2.mean());
        System.out.println("Stddev=                 "+ p2.stddev());
        String confidence2 = p2.confidenceLo() + "," + p2.confidenceHi();
        System.out.println("95% confidence interval="+ confidence2);
        System.out.println("\n\n");
        // trial #3
        System.out.println("trial#3 2 10000\n");
        PercolationStats p3=new PercolationStats(2,10000);
        System.out.println("Mean=                   "+ p3.mean());
        System.out.println("Stddev=                 "+ p3.stddev());
        String confidence3 = p3.confidenceLo() + "," + p3.confidenceHi();
        System.out.println("95% confidence interval="+ confidence3);
        System.out.println("\n\n");
        // trial #4
        System.out.println("trial#4 2 100000\n");
        PercolationStats p4=new PercolationStats(2,100000);
        System.out.println("Mean=                   "+ p4.mean());
        System.out.println("Stddev=                 "+ p4.stddev());
        String confidence4 = p4.confidenceLo() + "," + p4.confidenceHi();
        System.out.println("95% confidence interval="+ confidence4);
        System.out.println("\n\n");
    }
}
