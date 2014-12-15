
                                                                                                                                                                                                                   


public class Primer
{
    public static void main(int n)
    {
        int list[] = new int[n-2];        int size = list.length;        for (int i = 3; i <= n; i++)        {            list[i-3] = i;        }        int num = 2;        System.out.println(num);

        while(num * num < n)        {            for(int i = num; i < n; i += num)          {               for(int p = 0; p < list.length; p++)               {                   if (i == list[p])                   {
                        size--;
                        int[] newList = new int[size];
                        for (
                        
                        
                        int
                        
                        q 
                        
                        = 0; q 
                        
                        <= size; q++)
                        {
                            if(q < p)                          {                         
                                
                                newList[q] = list[q];                       }
                            else if(q > p)
                            {
                                newList[q-1] = list[q];                            }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                           }
                        list = newList;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           }               }
            }
            num = list[0];           System.out.println(num);
            size--;
            
            int[] newList = new int[size];            for (int i = 0; i < size; i ++)
            {
                newList[i] = list[i+1];
            }
            list = newList;
            
            
        }
        for (int val: list)
        {
            if(val != n)
            System.out.println(val);        }
    }
}
