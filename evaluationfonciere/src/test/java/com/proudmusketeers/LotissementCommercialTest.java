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
public class LotissementCommercialTest {
    JSONObject testData;
    LotissementCommercial lotTest;

    @Before
    public void setUp() throws Exception {
        testData = new JSONObject();
        testData.accumulate("description", "valide");
        testData.accumulate("nombre_droits_passage", 10);
        testData.accumulate("date_mesure", "2001-07-30");
    }

    //service = 1; superficie = 500 -> 1500
    @Test
    public void testMontantServicesSuperficieCinqCentsEtMoins() throws FormatInvalide {
        testData.accumulate("nombre_services", 1);
        testData.accumulate("superficie", 500);
        lotTest = new LotissementCommercial(testData);
        assertEquals(1500, lotTest.montantServices(), 0);
    }

    //service = 1; superficie = 501 -> 4500
    @Test
    public void testMontantServicesSuperficiePlusQueCinqCents() throws FormatInvalide {
        testData.accumulate("nombre_services", 1);
        testData.accumulate("superficie", 501);
        lotTest = new LotissementCommercial(testData);
        assertEquals(4500, lotTest.montantServices(), 0);
    }

    //service = 2; superficie = 501; resultat = 6000 -> 5000
    @Test
    public void testMontantServicesResultatPlusQueCinqMille() throws FormatInvalide {
        testData.accumulate("nombre_services", 2);
        testData.accumulate("superficie", 501);
        lotTest = new LotissementCommercial(testData);
        assertEquals(5000, lotTest.montantServices(), 0);
    }

    //superficie = 500; prixMax = 5 -> -3250
    @Test
    public void testMontantDroitDePassages() throws FormatInvalide {
        testData.accumulate("nombre_services", 1);
        testData.accumulate("superficie", 500);
        lotTest = new LotissementCommercial(testData);
        lotTest.setPrixMinMax(new double[]{1, 5});
        assertEquals(-3250, lotTest.montantDroitDePassages(), 0);
    }

    //superficie = 500; prixMax = 5 -> 2500
    @Test
    public void testValeurSuperficie() throws FormatInvalide {
        testData.accumulate("nombre_services", 1);
        testData.accumulate("superficie", 500);
        lotTest = new LotissementCommercial(testData);
        lotTest.setPrixMinMax(new double[]{1, 5});
        assertEquals(2500, lotTest.valeurSuperficie(), 0);
    }
}
