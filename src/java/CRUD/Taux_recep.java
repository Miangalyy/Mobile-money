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
import java.sql.Statement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author pc
 */
public class Taux_recep {
    public void insert_values(String idRec, String montant1, String montant2, String frais_rec) throws ClassNotFoundException, SQLException {
    DB_Connection obj_DB_Connection = new DB_Connection();
    Connection connection = obj_DB_Connection.get_connection();
    
    PreparedStatement ps = null;

    try {
        String query = "insert into taux_recep(idRec, montant1, montant2, frais_rec)values(?, ?, ?, ?)";
        ps = connection.prepareStatement(query);
        int montants1 = Integer.valueOf(montant1);
        int montants2 = Integer.valueOf(montant2);
        int frais = Integer.valueOf(frais_rec);
        
        ps.setString(1, idRec);
        ps.setInt(2, montants1);
        ps.setInt(3, montants2);
        ps.setInt(4, frais);
        ps.executeUpdate();
    } catch (SQLException ex) {
        System.err.println(ex);
     }
  }
    
    public JSONArray get_Taux () throws SQLException {
        JSONArray list = new JSONArray();
        DB_Connection obj_db_connection = new DB_Connection();
        Connection connection = obj_db_connection.get_connection();
        PreparedStatement pst;
        ResultSet rs;
        
        Statement st = connection.createStatement();
        String sql = "select * from taux_recep";
        rs = st.executeQuery(sql);
        
        while (rs.next()) {
        JSONObject obj = new JSONObject();
        String id = rs.getString("id_recep");
        String idEnv = rs.getString("idRec");
        String montant1 = rs.getString("montant1");
        String montant2 = rs.getString("montant2");
        String frais_env = rs.getString("frais_rec");
        
        obj.put("idRec", idEnv);
        obj.put("montant1", montant1);
        obj.put("montant2", montant2);
        obj.put("frais_rec", frais_env);
        obj.put("id", id);

        list.add(obj);        
    }        
        
        return list;
        
        
    }
    
    public JSONArray get_taux_edit (String id) throws SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement st;
    ResultSet rs;
    Connection connection = obj_db_connection.get_connection();
    st = connection.prepareStatement("select id_recep, idRec, montant1, montant2, frais_rec from taux_recep where id_recep = ?");
    st.setString(1, id);
    rs = st.executeQuery();
    
    if (rs.next() == true){
    String id1 = rs.getString(1);
    String idEnv = rs.getString(2);
    String montant1 = rs.getString(3);
    String montant2 = rs.getString(4);
    String frais_env = rs.getString(5);
    
    JSONObject obj = new JSONObject();
    
    obj.put("id", id1);
    obj.put("idRec", idEnv);
    obj.put("montant1", montant1);
    obj.put("montant2", montant2);
    obj.put("frais_rec", frais_env);
    
    list.add(obj);
    }
    return list;
    }
    
    public JSONArray update_values(String id, String idRec, String montant1, String montant2, String frais_rec) throws ClassNotFoundException, SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_DB_Connection = new DB_Connection();
    Connection connection = obj_DB_Connection.get_connection();
    
    PreparedStatement ps = null;

    try {
        String query = "update taux_recep set idRec = ?, montant1 = ?, montant2 = ?, frais_rec = ? where id_recep = ?";
        ps = connection.prepareStatement(query);
        
        int montants1 = Integer.valueOf(montant1);
        int montants2 = Integer.valueOf(montant2);
        int frais = Integer.valueOf(frais_rec);
        
        ps.setString(1, idRec);
        ps.setInt(2, montants1);
        ps.setInt(3, montants2);
        ps.setInt(4, frais);
        ps.setString(5, id);
        
        ps.executeUpdate();
        
        JSONObject obj = new JSONObject();
        obj.put("text", "Taux_envoi updated");
        obj.put("hi", "hii");
        list.add(obj);
    } catch (SQLException ex) {
        System.err.println(ex);
     }
    return list;
  }
    
    public void delete_values(String id) throws SQLException {
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement pst;
    Connection connection = obj_db_connection.get_connection();
    pst = connection.prepareStatement("delete from taux_recep where id_recep = ?");
    pst.setString(1, id);
    pst.executeUpdate();
    }
}
