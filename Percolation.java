import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation
{
    private int n; // total number of sites in grid
    private WeightedQuickUnionUF uf1; // uf with top and bottom virtual sites
    private WeightedQuickUnionUF uf2; // uf with only top virtual site
    private boolean[] stateofsite;
    
    public Percolation ( int n )
    {
        if ( n <= 0 )
            throw new IllegalArgumentException();
        this.n = n;
        uf1 = new WeightedQuickUnionUF(n*n + 2);
        uf2 = new WeightedQuickUnionUF(n*n + 1);
        stateofsite = new boolean[n*n+2];
        for ( int i = 1; i <= n; i++ )
        { // all first line connect to top
            uf1.union ( 0, i );
            uf2.union ( 0, i );
        }
        for ( int i = 1; i <= n; i++)
        { // all bottom line of uf1 connect to bottom;
            uf1.union ( getlocate ( n, i ), n * n + 1);
        }
        for ( int i = 1; i <= n*n; i++ )
            stateofsite[i] = false;
         
    }
    public void open ( int row, int col)
    {
        validate_site ( row, col );
        stateofsite[getlocate(row, col)] = true;
        if (row == 1)
        {
            uf1.union(0, getlocate(row, col));
            uf2.union(0, getlocate(row, col));
        }
        if (row == n)
        {
            uf1.union(getlocate(row, col), n*n + 1);
        }
        if (row > 1 && isOpen(row-1, col))
        { // connect to the upper line
            uf1.union(getlocate(row, col), getlocate(row-1, col));
            uf2.union(getlocate(row, col), getlocate(row-1, col));
        }
        if (row < n && isOpen(row+1, col))
        { // down connect
            uf1.union(getlocate(row, col),getlocate(row+1, col));
            uf2.union(getlocate(row, col),getlocate(row+1, col));
        }
        if (col > 1 && isOpen(row, col-1))
        { // left connect
            uf1.union(getlocate(row, col), getlocate(row, col-1));
            uf2.union(getlocate(row, col), getlocate(row, col-1));
        }
        if (col < n && isOpen(row, col+1))
        { // right connect
            uf1.union ( getlocate ( row, col), getlocate ( row, col+1 ) );
            uf2.union ( getlocate ( row, col), getlocate ( row, col+1 ) );
        }
            
    }
    
    public boolean isOpen(int row, int col)
    {
        validate_site(row, col);
        return stateofsite[getlocate(row, col)];
    }
    
    public boolean isFull(int row, int col)
    {
        validate_site(row, col);
        if (isOpen(row, col))
        {
            if (n == 1)
                return true;
            else
            {
                return uf2.connected(getlocate(row, col), 0);
            }
        }
        return false;

    }
  
    public boolean percolates()// is the system percolate or not?
    {
        if (n == 1)
            return isFull(1, 1);
        else
        {
            return uf1.connected(0, n * n + 1);
        }
    }
    public int numberOfOpenSites()
    {
        int count = 0;
        for(int i = 1; i <= n; i++)
            for(int j = 1;j <= n; j++)
        {
            if ( isOpen (i, j) )
                count ++;
        }
        return count;
        

    }
    
    private int getlocate(int i, int j)
    {
        return j + (i - 1) * n;
    }
    private void validate_site(int i, int j)
    {
        if (i < 1 || i > n || j < 1 || j > n)
            throw new IllegalArgumentException();
    }
    
}