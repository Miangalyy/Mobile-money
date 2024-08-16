/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUD;

import DB.DB_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author pc
 */
public class Somme {
        
    public JSONArray get_somme () throws SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement st;
    ResultSet rs;
    PreparedStatement st_rec;
    ResultSet rs_rec;
    Connection connection = obj_db_connection.get_connection();
    st = connection.prepareStatement("select SUM(te.frais_env) as somme_env FROM envoyer e JOIN taux_envoi te ON e.montant BETWEEN te.montant1 AND te.montant2");
    rs = st.executeQuery();
    
    if (rs.next() == true){
    int somme_env = rs.getInt(1);
    
        st_rec = connection.prepareStatement("select SUM(tr.frais_rec) as somme_rec FROM retrait r JOIN taux_recep tr ON r.montant BETWEEN tr.montant1 AND tr.montant2");
        rs_rec = st_rec.executeQuery();

        if (rs_rec.next() == true) {
            int somme_rec = rs.getInt(1);
            int somme = somme_rec + somme_env;

            JSONObject obj = new JSONObject();

            obj.put("somme", somme);
            obj.put("status", "true");

            list.add(obj);
        }
    }
    return list;
    }
    
    public JSONArray get_somme_rec () throws SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement st;
    ResultSet rs;
    Connection connection = obj_db_connection.get_connection();
    st = connection.prepareStatement("select SUM(tr.frais_rec) as somme_rec FROM retrait r JOIN taux_recep tr ON r.montant BETWEEN tr.montant1 AND tr.montant2");
    rs = st.executeQuery();
    
    if (rs.next() == true){
    int somme_rec = rs.getInt(1);
    
    JSONObject obj = new JSONObject();
    
    obj.put("somme_rec", somme_rec);
    obj.put("status", "true");
    
    list.add(obj);
    }
    return list;
    }
}
