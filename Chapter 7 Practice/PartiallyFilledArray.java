
public class PartiallyFilledArray
{
    private int[] values = {1,2,3,4,5,7,7};
    private int currentSize;
    public PartiallyFilledArray()
    {
       
        currentSize = 7;
    }  

    public String toString()
    {
        String str = "[";
        for(int i = 0; i < currentSize; i++)
        {
            if (i > 0)
            {
                str += ", ";
            }
            str += values[i];
        }
        str += "]";
        return str;
    }

    public void fill(int n, int max)
    {
        currentSize = n;
        for(int i = 0; i < n; i++)
        {
            values[i] = (int)(Math.random() * max);
        }
    }

    public void removeElementAtIndex(int index)
    {
        values[index] = values[currentSize - 1];
        currentSize--; 
    }

    public void swap(int i1, int i2)
    {
        int temp = values[i1];
        values[i1] = values[i2];
        values[i2] = temp;
    }

    public void insert(int val)
    {
        if( currentSize >= values.length)
        {
            growArray();
        }
        values[currentSize] = val;
        currentSize++;
    }

    private void growArray()
    {
        int[] newArray = new int[values.length * 2];
        for (int i = 0; i < values.length; i++)
        {
            newArray[i] = values[i];
        }

        values = newArray;
    }

    public String isDuplicate()
    {
        String flag = "No Duplicates";
        for (int i = 0; i < currentSize; i++)
        {
            int[] newArray = new int[currentSize];
            for (int p = 0; p < currentSize; p++)
            {
                newArray[p] = values[p];
            }
            newArray[i] = newArray[currentSize - 1];
            for (int j = 0; j < currentSize - 1; j++)
            {
                if (values[i] == newArray[j])
                {
                    flag = "There is a duplicate";
                    break;
                }
            }
        }
        return flag;
    }
}

