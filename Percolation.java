import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private int N;// total number of sites
    private boolean[] state;//the state of all sites
    private WeightedQuickUnionUF uf;
    private int top;
    private int bottom;
    
    public Percolation(int n)//create n-by-n grid,with all sites block
    {
        if(n<=0)
            throw new IllegalArgumentException("N is not an efficient number!");
        
        N=n;
        state=new boolean[n*n+2];//including the top and bottom virtual sites
        uf=new WeightedQuickUnionUF(N*n+2);
        top=0;
        bottom=N*N+1;
        
        for(int i=1;i<=n*n;i++)
            state[i]=false;
        
    }
    
    
   
    public void open(int row,int col)// open site(row,col) if it is not open already
    {
        validate_site(row,col);
        int index=matrixto1D(row,col);
        state[index]=true;
        if(row==1)
            uf.union(index,top);
        if(!(percolates()))
        {
            if(row==N)
                uf.union(index,bottom);
        }
        if(row< N && state[index+N])
            uf.union(index,index+N);
        if(row>1 && state[index-N])
            uf.union(index,index-N);
        if(col<N && state[index+1])
            uf.union(index,index+1);
        if(col>1 && state[index-1])
            uf.union(index,index-1);
            
    }
    
    public boolean isOpen(int row,int col)//is site(row,col) open?
    {
        validate_site(row,col);
        return state[matrixto1D(row,col)];
    }
    public boolean isFull(int row,int col)// is site(row,col) full?
    {
        validate_site(row,col);
        return uf.connected(top,matrixto1D(row,col));//why???
        
    }
    public int numberOfOpenSites()//number of open sites
    {
        int count=0;
        for(int i=0;i<N;i++)
            for(int j=0;j<N;j++)
        {
            if(isOpen(i,j))
                count++;
        }
        return count;
    }
    public boolean percolates()//does the system percolate？
    {
        return uf.connected(top,bottom);
    }
    // own functions
     private void validate_site(int i,int j)
    {
        if(!(i>=1 && i<=N && j>=1 && j<=N))
            throw new IndexOutOfBoundsException("invalid site!");
    }
    
    private int matrixto1D(int i,int j)//convert 2D to 1D for each(i,j)
    {
        validate_site(i,j);
        return j+(i-1)*N;
    }
    
}

