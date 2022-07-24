package com.proudmusketeers;

import static org.junit.Assert.*;
import org.junit.*;


public class TerrainTest
{

    @Test
    public void testArrondiAuZeroCentimesDirect(){
        assertEquals(10.00, Terrain.arrondiAu5sous(10.00), 0);
    }

    @Test
    public void testArrondiAuCinqCentimesDirect(){
        assertEquals(10.05, Terrain.arrondiAu5sous(10.05), 0);
    }

    @Test
    public void testArrondiAuZeroCentimeVersLeHaut()
    {
        assertEquals(10.00, Terrain.arrondiAu5sous(9.98),  0);
    }

    @Test
    public void testArrondiAuZeroCentimeVersLeBas()
    {
        assertEquals(10.00, Terrain.arrondiAu5sous(10.02), 0);
    }

    @Test
    public void testArrondiAuCinqCentimesVersLeHaut()
    {
        assertEquals(10.05, Terrain.arrondiAu5sous(10.03), 0);
    }

    @Test
    public void testArrondiAuCinqCentimesVersLeBas()
    {
        assertEquals(10.05, Terrain.arrondiAu5sous(10.07), 0);
    }
}
