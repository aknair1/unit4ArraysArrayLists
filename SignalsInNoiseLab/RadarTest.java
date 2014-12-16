

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RadarTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class RadarTest
{
    @Test
    public void test1()
    {
        Radar radar = new Radar(100,100);
        radar.setNoiseFraction(0.01);
        radar.setMonsterStats(1,1,0,0);
        radar.setup();
        while(radar.scan());
        assertEquals(1,radar.getGeussDRow());
        assertEquals(1,radar.getGeussDCol());
        assertEquals(0,radar.getGeussRow());
        assertEquals(0,radar.getGeussCol());
    }
    @Test
    public void test2()
    {
        Radar radar = new Radar(100,100);
        radar.setNoiseFraction(0.01);
        radar.setMonsterStats(1,-2,20,98);
        radar.setup();
        while(radar.scan());
        assertEquals(1,radar.getGeussDRow());
        assertEquals(-2,radar.getGeussDCol());
        assertEquals(20,radar.getGeussRow());
        assertEquals(98,radar.getGeussCol());
    }
    @Test
    public void test3()
    {
        Radar radar = new Radar(100,100);
        radar.setNoiseFraction(0.01);
        radar.setMonsterStats(-1,2,67,3);
        radar.setup();
        while(radar.scan());
        assertEquals(-1,radar.getGeussDRow());
        assertEquals(2,radar.getGeussDCol());
        assertEquals(67,radar.getGeussRow());
        assertEquals(3,radar.getGeussCol());
    }
    @Test
    public void test4()
    {
        Radar radar = new Radar(100,100);
        radar.setNoiseFraction(0.01);
        radar.setMonsterStats(-2,-2,99,99);
        radar.setup();
        while(radar.scan());
        assertEquals(-2,radar.getGeussDRow());
        assertEquals(-2,radar.getGeussDCol());
        assertEquals(99,radar.getGeussRow());
        assertEquals(99,radar.getGeussCol());
    }
}
