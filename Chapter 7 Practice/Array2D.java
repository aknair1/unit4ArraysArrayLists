
public class Array2D
{
    private int[][] table = {
            {1,2,3},
            {4,5,6},
            {7,8,9},
            {10,11,12}
        };

    public Array2D()
    {

    }

    public String toString()
    {
        String str = "";
        for(int row = 0; row < table.length; row++)
        {
            for( int col = 0; col < table[row].length; col++)
            {
                str += table[row][col] + "\t";
            }
            str += "\n";
        }
        return str;
    }

    public String getRow( int row)
    {
        String str = "";
        for(int val: table[row])
        {
            str += val + "\t";
        }
        return str;
    }

    public String getCol(int col)
    {
        String str = "";
        for(int[] val: table)
        {
            str += val[col] + "\t";
        }
        return str;
    }

   

}
