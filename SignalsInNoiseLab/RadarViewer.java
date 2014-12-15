import javax.swing.JFrame;
import java.util.Scanner;

/**
 * Class that contains the main method for the program and creates the frame containing the component.
 * 
 * @author @gcschmit
 * @version 19 July 2014
 */
public class RadarViewer
{
    /**
     * main method for the program which creates and configures the frame for the program
     *
     */
    public static void main(String[] args) throws InterruptedException
    {
        Scanner in = new Scanner(System.in);
        // create the radar, set the monster location, and perform the initial scan
        final int ROWS = 100;
        final int COLS = 100;
        Radar radar = new Radar(ROWS, COLS);
        radar.setNoiseFraction(0.01);
        System.out.print("What is the row speed of the monster: ");
        int dRow = in.nextInt();
        System.out.print("What is the column speed of the monster: ");
        int dCol = in.nextInt();
        System.out.print("What is the starting row of the monster: ");
        int row = in.nextInt();
        System.out.print("What is the starting column of the monster: ");
        int col = in.nextInt();
        radar.setMonsterStats(dRow,dCol,row,col);
        radar.setup();        
        JFrame frame = new JFrame();        
        frame.setTitle("Signals in Noise Lab");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        // a frame contains a single component; create the radar component and add it to the frame
        RadarComponent component = new RadarComponent(radar);
        frame.add(component);        
        // set the size of the frame to encompass the contained component
        frame.pack();        
        // make the frame visible which will result in the paintComponent method being invoked on the
        //  component.
        frame.setVisible(true);        
        // perform 100 scans of the radar wiht a slight pause between each
        // after each scan, instruct the Java Run-Time to redraw the window
        while(radar.scan())
        {
            Thread.sleep(100); // sleep 100 milliseconds (1/10 second)                      
            frame.repaint();
        }        
    }

}
