
/**
 * The model for radar scan and accumulator
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class Radar
{

    // stores whether each cell triggered detection for the current scan and the last scan of the radar
    private boolean[][][] scans;

    // value of each cell is incremented for each scan in which that cell triggers detection 
    private int[][] accumulator;

    // location of the monster
    private int monsterLocationRow;
    private int monsterLocationCol;

    // probability that a cell will trigger a false detection (must be >= 0 and < 1)
    private double noiseFraction;

    // number of scans of the radar since construction
    private int numScans;
    // max speed threshold
    private final int MAXSPEEDTHRESHOLD = 5;
    //
    private final int CHECKSPEEDTHRESHOLD = 10;
    // monster speeds(x per frame, y per frame)
    private int dRow;
    private int dCol;
    // rows and columns
    private int rows;
    private int cols;
    //The algorithm's geuss of the monster's speed
    public int geussDRow;
    public int geussDCol;
    //The algorithm's geuss of the monster's starting location
    public int geussRow;
    public int geussCol;

    /**
     * Constructor for objects of class Radar
     * 
     * @param   rows    the number of rows in the radar grid
     * @param   cols    the number of columns in the radar grid
     */
    public Radar(int rows, int cols)
    {
        // initialize instance variables
        scans = new boolean[rows][rows][cols];// elements will be set to false
        accumulator = new int[2*MAXSPEEDTHRESHOLD+1][2*MAXSPEEDTHRESHOLD+1]; // speed usage will be set to 0
        this.cols = cols;
        this.rows = rows;

        // randomly set the location of the monster (can be explicity set through the
        //  setMonsterLocation method
        monsterLocationRow = (int)(Math.random() * rows);
        monsterLocationCol = (int)(Math.random() * cols);
        // randomly set the speed of the monster (can be explicity set through the
        // setMonsterLocationSpeed method
        dRow = (int)(Math.random() * MAXSPEEDTHRESHOLD*2)-MAXSPEEDTHRESHOLD;
        dCol = (int)(Math.random() * MAXSPEEDTHRESHOLD*1)-MAXSPEEDTHRESHOLD;
        // nosie fraction defaults to 0.10
        noiseFraction = 0.10;
        // seting the number of scans to zero
        numScans = 0;        
    }

    /**
     * Performs a scan of the radar. Noise is injected into the grid and the accumulator is updated.
     * @return returns true if the monster is still in the screen and more scans are needed to find its speed and starting location
     * 
     */
    public boolean scan()
    {
        if(monsterLocationRow < rows && monsterLocationCol < cols && monsterLocationRow > 0 && monsterLocationCol > 0)
        {            
            // detect the monster       
            scans[numScans][monsterLocationRow][monsterLocationCol] = true;        
            // inject noise into the grid
            injectNoise();        
            // udpate the accumulator
            for(int row = 0; row < scans.length; row++)
            {
                for(int col = 0; col < scans.length; col++)
                {
                    if(scans[numScans][row][col])
                    {
                        for(int row2 = 0; row2 < scans.length; row2++)
                        {
                            for(int col2 = 0; col2 < scans[0].length; col2++)
                            {
                                if (scans[numScans-1][row2][col2])
                                {
                                    int diffRow = row - row2;
                                    int diffCol = col - col2;
                                    if(Math.abs(diffRow) <= MAXSPEEDTHRESHOLD && Math.abs(diffCol) <= MAXSPEEDTHRESHOLD)
                                    {
                                        accumulator[diffRow+MAXSPEEDTHRESHOLD][diffCol+MAXSPEEDTHRESHOLD]++;
                                    }
                                }
                            }
                        }    
                    }
                }
            }    
            // updates the monster location
            updateMonsterLocation(); 
            // keep track of the total number of scans
            numScans++;
            // checks if a highest used speed has been discovered, if it has it ends the program by returning false
            if(checkForHighestSpeed())
            {
                System.out.println("The row speed is " + geussDRow + " and the column speed is " + geussDCol);
                // finds the starting location of the monster based on the speed it discovered
                finish();
                return false;
            }
            else
            {
                return true;
            }            
        }
        else
        {
            System.out.println("The monster has escaped!!!");
            return false;
        }
    }

    /**
     * Sets up a scan so that the program can compare scans for the first run of the scan method
     */
    public void setup()
    {     
        scans[numScans][monsterLocationRow][monsterLocationCol] = true;
        injectNoise();
        updateMonsterLocation();
        numScans++;
    }

    /**
     * checks to see if one speed has been used at least CHEKSPEEDTHRESHOLD times more than the others
     * @return returns true if one speed has been used at least CHEKSPEEDTHRESHOLD times more than the others
     */
    public boolean checkForHighestSpeed()
    {    
        // finds the speed that was used the most
        int highest = 0;  
        for(int row = 0; row < accumulator.length; row++)
        {
            for(int col = 0; col < accumulator[0].length; col++)
            {   
                if(highest < accumulator[row][col])
                {
                    highest = accumulator[row][col];
                    geussDRow = row - MAXSPEEDTHRESHOLD;
                    geussDCol = col - MAXSPEEDTHRESHOLD;
                }                
            }
        }
        int check = 0;
        for(int row = 0; row < accumulator.length; row++)
        {
            for(int col = 0; col < accumulator[0].length; col++)
            {   
                if(check < accumulator[row][col] && accumulator[row][col] != highest)
                {
                    check = accumulator[row][col];
                }                
            }
        }
        // checks to see if the most used speed is used at least CHECKSPEEDTHRESHOLD more times than the others
        return check + CHECKSPEEDTHRESHOLD == highest;
    }

    /**
     * does one more scan so that it can finds the starting monster location based on geussDRow and geussDCol
     */
    public void finish()
    {
        scans[numScans][monsterLocationRow][monsterLocationCol] = true;
        injectNoise();        
        for(int row = 0; row < scans.length; row++)
        {
            for(int col = 0; col < scans.length; col++)
            {
                if(scans[numScans][row][col])
                {
                    for(int row2 = 0; row2 < scans.length; row2++)
                    {
                        for(int col2 = 0; col2 < scans[0].length; col2++)
                        {
                            if (scans[numScans-1][row2][col2])
                            {
                                int diffRow = row - row2;
                                int diffCol = col - col2;                                
                                if(diffRow == geussDRow && geussDCol == diffCol)
                                {
                                    boolean flag = true;
                                    int counter = 0;
                                    //checks to see if there is a detection in every scan that follows the trail created by the speeds geussDRow and geussDCol in order to find the starting location
                                    for(int i = numScans; i >= 0; i--)
                                    {
                                        int row3 = row-(geussDRow*counter);
                                        int col3 = col-(geussDCol*counter);
                                        if(row3 < 0 || col3 < 0 ||  row3 > 99 || col3 > 99 || !scans[i][row3][col3])
                                        {                                            
                                            flag = false;
                                        }
                                        counter++;
                                    }
                                    if(flag)
                                    {
                                        geussRow = row-geussDRow*numScans;
                                        geussCol = col-geussDCol*numScans;
                                        System.out.println("The monster starting row position is " + geussRow + " and the starting column position is " + geussCol);
                                    }
                                }
                            }
                        }
                    }    
                }
            }
        }    
    }

    /**
     * Changes the monster location based on dRow and dCol
     */
    public void updateMonsterLocation()
    {
        // remember the row and col of the monster's location
        monsterLocationRow = monsterLocationRow  + dRow;
        monsterLocationCol = monsterLocationCol  + dCol;

    }

    /**
     * Sets the location of the monster
     * 
     * @param   dRow    the diffence in the row position of the monster between each frame
     * @param   dCol    the diffence in the column position of the monster between each frame
     * @param   row     the row in which the monster starts
     * @param   col     the column in which the monster starts
     * @pre row and col must be within the bounds of the radar grid
     */
    public void setMonsterStats(int dRow, int dCol, int row, int col)
    {
        // remember the row and col and dRow and dColof the monster's location 
        monsterLocationRow = row;
        monsterLocationCol = col;
        this.dRow = dRow;
        this.dCol = dCol;

    }

    /**
     * Sets the probability that a given cell will generate a false detection
     * 
     * @param   fraction    the probability that a given cell will generate a flase detection expressed
     *                      as a fraction (must be >= 0 and < 1)
     */
    public void setNoiseFraction(double fraction)
    {
        noiseFraction = fraction;
    }

    /**
     * Returns true if the specified location in the radar grid triggered a detection.
     * 
     * @param   row     the row of the location to query for detection
     * @param   col     the column of the location to query for detection
     * @return true if the specified location in the radar grid triggered a detection
     */
    public boolean isDetected(int row, int col)
    {
        return scans[numScans][row][col];
    }

    /**
     * Returns the number of rows in the radar grid
     * 
     * @return the number of rows in the radar grid
     */
    public int getNumRows()
    {
        return scans.length;
    }
    
    /**
     * Returns the number algorithm's geuss of the monsters row speed
     * 
     * @return the number algorithm's geuss of the monsters row speed
     */
    public int getGeussDRow()
    {
        return geussDRow;
    }

    /**
     * Returns the number algorithm's geuss of the monsters column speed
     * 
     * @return the number algorithm's geuss of the monsters column speed
     */
    public int getGeussDCol()
    {
        return geussDCol;
    }  
    
    /**
     * Returns the number algorithm's geuss of the monsters starting row
     * 
     * @return the number algorithm's geuss of the monsters starting row
     */
    public int getGeussRow()
    {
        return geussRow;
    }  
 
    /**
     * Returns the number algorithm's geuss of the monsters starting column
     * 
     * @return the number algorithm's geuss of the monsters starting collumn
     */
    public int getGeussCol()
    {
        return geussCol;
    }  
    

    /**
     * Returns the number of columns in the radar grid
     * 
     * @return the number of columns in the radar grid
     */
    public int getNumCols()
    {
        return scans.length;
    }

    /**
     * Returns the number of scans that have been performed since the radar object was constructed
     * 
     * @return the number of scans that have been performed since the radar object was constructed
     */
    public int getNumScans()
    {
        return numScans;
    }

    /**
     * Sets cells as falsely triggering detection based on the specified probability
     * 
     */
    private void injectNoise()
    {
        for(int row = 0; row < scans.length; row++)
        {
            for(int col = 0; col < scans.length; col++)
            {
                // each cell has the specified probablily of being a false positive
                if(Math.random() < noiseFraction)
                {
                    scans[numScans][row][col] = true;
                }
            }
        }
    }

}
