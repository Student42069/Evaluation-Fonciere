package com.proudmusketeers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * Classe avec les methodes de validations du formatage des
 * donnees d'entree du terrain.
 *
 * @author Leonid Glazyrin GLAL77080105
 *         Goldlen Chhun CHHG20069604
 *         Steven Chieng CHIS01069604
 *         Eric Drapeau DRAE21079108
 * 
 */
class ValiderValeursTerrain {

    private final JSONObject JSONSource;

    public ValiderValeursTerrain(JSONObject JSONSource) {
        this.JSONSource = JSONSource;
    }
    
    double prixMin() throws FormatInvalide {
        try {
            double prixMin = stringEnDouble(JSONSource.getString("prix_m2_min"));
            if(prixMin < 0){
                throw new FormatInvalide("La propriete <prix_m2_min> ne peut pas etre negative.");
            }
            return prixMin;
        } catch (JSONException e) {
            throw new FormatInvalide("La propriete <prix_m2_min> est manquante dans le fichier d'entree.");
        }
    }
    
    double prixMax() throws FormatInvalide {
        try {
            double prixMax = stringEnDouble(JSONSource.getString("prix_m2_max"));
            if(prixMax < 0){
                throw new FormatInvalide("La propriete <prix_m2_max> ne peut pas etre negative.");
            }
            return prixMax;
        } catch (JSONException e) {
            throw new FormatInvalide("La propriete <prix_m2_max> est manquante dans le fichier d'entree.");
        }
    }
    
    int typeTerrain() throws FormatInvalide {
        try {
            int typeTerrain = JSONSource.getInt("type_terrain");
            if(typeNonValide(typeTerrain)){
                throw new FormatInvalide("La propriete <type_terrain> n'est pas la valeur 0, 1 ou 2.");
            }
            return typeTerrain;
        } catch (JSONException e) {
            throw new FormatInvalide("La propriete <type_terrain> est manquante dans le fichier d'entree.");
        }
    }
    
    Lotissement[] lotissements() throws FormatInvalide{
        try {
            JSONArray lots = JSONSource.getJSONArray("lotissements");
            if(lots.size() > 10){
                throw new FormatInvalide("Le nombre de lots dans la propriete <lotissements> ne doit pas depasser 10 lots.");
            } else if(lots.size() < 1){
                throw new FormatInvalide("La propriete <lotissements> doit comporter au moins un lot.");
            }
            return formaterLot(lots, typeTerrain());
        } catch (JSONException e) {
            throw new FormatInvalide("La propriete <lotissements> est manquante dans le fichier d'entree.");
        }
    }
    
    private Lotissement[] formaterLot(JSONArray lots, int typeDeTerrain) throws FormatInvalide {
        CreerTypeLotissement createur = new CreerTypeLotissement();
        Lotissement[] lotissements = new Lotissement[lots.size()];
        JSONObject unLot;
        for (int i = 0; i < lots.size(); i++) {
            unLot = lots.getJSONObject(i);
            lotissements[i] = createur.creerLotissement(typeDeTerrain, unLot);
            lotissements[i].setPrixMinMax(new double[] {prixMin(), prixMax()});
        }
        verifierDescriptionUnique(lotissements);
        return lotissements;
    }
   
    private void verifierDescriptionUnique(Lotissement[] lotissements) throws FormatInvalide{
        List<Lotissement> lots = Arrays.asList(lotissements);
        List<Lotissement> lotsUnique = descriptionsUniques(lots);
        
        if(lotsUnique.size() != lots.size())
            throw new FormatInvalide("Une ou plusieurs proprietes <description> des lots ne sont pas uniques.");
    }

    private List descriptionsUniques(List<Lotissement> lots) {
        return lots.stream().map(lot->lot.getDescription()).distinct().collect(Collectors.toList());
    }
  
    private double stringEnDouble(String prixEnString){
       //On le separe du signe $ et on remplace les , par . s'il y en a
       prixEnString = prixEnString.split(" ")[0].replaceAll(",",".");
       return Double.parseDouble(prixEnString);
    }
    
    private boolean typeNonValide(int type) {
        return type != 0 && type != 1 && type != 2;
    }
}
