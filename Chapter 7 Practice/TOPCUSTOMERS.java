
public class TOPCUSTOMERS
{
    private int[] values;

    public ArrayAlgorithms()
    {
        values = new int[10];
    }

    public String toString()
    {
        String str = "[";
        for(int val : values)
        {
            str += val + ", ";
        }
        str += "]";
        return str;
    }

    public void fillWithSquares()
    {
        for(int i = 0; i < values.length; i++)
        {
            values[i] = i * i;
        }
    }

    public double getAverage()
    {
        double sum = 0;

        for(int val: values)
        {
            sum += val;
        }
        double average = sum / values.length;

        return average;
    }
    
    public int max()
    {
        int maximumValue = values[0];
        int index = 0;
        for( int i = 1; i< values.length; i++)
        {
            if( values[i] > maximumValue)
            {
                maximumValue = values[i];
                index = i;
            }
        }
        return index;
    }
    
   
}