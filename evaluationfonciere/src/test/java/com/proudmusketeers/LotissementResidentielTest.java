package com.proudmusketeers;

import static org.junit.Assert.*;
import org.junit.*;

import net.sf.json.JSONObject;

/**
 * 
 *
 * @author Leonid Glazyrin GLAL77080105
 *         Goldlen Chhun CHHG20069604
 *         Steven Chieng CHIS01069604
 *         Eric Drapeau DRAE21079108
 * 
 */
public class LotissementResidentielTest {
    JSONObject testData;
    LotissementResidentiel lotTest;

    @Before
    public void setUp() throws Exception {
        testData = new JSONObject();
        testData.accumulate("description", "valide");
        testData.accumulate("nombre_droits_passage", 10);
        testData.accumulate("date_mesure", "2001-07-30");
        testData.accumulate("nombre_services", 1);
    }

    //superficie = 500 -> 0
    @Test
    public void testMontantServicesAvecSuperficieCinqCentsEtMoins() throws FormatInvalide {
        testData.accumulate("superficie", 500);
        lotTest = new LotissementResidentiel(testData);
        assertEquals(0, lotTest.montantServices(), 0);
    }

    //superficie = 10000 -> 1500
    @Test
    public void testMontantServicesAvecSuperficieEntreCinqCentsEtDixMille() throws FormatInvalide {
        testData.accumulate("superficie", 10000);
        lotTest = new LotissementResidentiel(testData);
        assertEquals(1500, lotTest.montantServices(), 0);
    }

    //superficie = 20000 -> 3000
    @Test
    public void testMontantServicesAvecSuperficiePlusQueDixMille() throws FormatInvalide {
        testData.accumulate("superficie", 10001);
        lotTest = new LotissementResidentiel(testData);
        assertEquals(3000, lotTest.montantServices(), 0);
    }

    //superficie = 50000; prixMin = 1; prixMax = 5 -> -149500
    @Test
    public void testMontantDroitDePassages() throws FormatInvalide {
        testData.accumulate("superficie", 50000);
        lotTest = new LotissementResidentiel(testData);
        lotTest.setPrixMinMax(new double[]{1, 5});
        assertEquals(-149500, lotTest.montantDroitDePassages(), 0);
    }

    //superficie = 50000; prixMin = 1; prixMax = 5 -> 150000
    @Test
    public void testValeurSuperficie() throws FormatInvalide {
        testData.accumulate("superficie", 50000);
        lotTest = new LotissementResidentiel(testData);
        lotTest.setPrixMinMax(new double[]{1, 5});
        assertEquals(150000, lotTest.valeurSuperficie(), 0);
    }
}