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
public class Client {
    public void insert_values(String numtel, String nom, String sexe, String email, String solde, String age) throws ClassNotFoundException, SQLException {
    DB_Connection obj_DB_Connection = new DB_Connection();
    Connection connection = obj_DB_Connection.get_connection();
    
    PreparedStatement ps = null;

    try {
        String query = "insert into client(numtel, nom, sexe, email, solde, age)values(?, ?, ?, ?, ?, ?)";
        ps = connection.prepareStatement(query);
        int soldes = Integer.valueOf(solde);
        int ages = Integer.valueOf(age);
        
        ps.setString(1, numtel);
        ps.setString(2, nom);
        ps.setString(3, sexe);
        ps.setString(4, email);
        ps.setInt(5, soldes);
        ps.setInt(6, ages);
        ps.executeUpdate();
    } catch (SQLException ex) {
        System.err.println(ex);
     }
  }
    
    public JSONArray get_client () throws SQLException {
        JSONArray list = new JSONArray();
        DB_Connection obj_db_connection = new DB_Connection();
        Connection connection = obj_db_connection.get_connection();
        PreparedStatement pst;
        ResultSet rs;
        
        Statement st = connection.createStatement();
        String sql = "select * from client";
        rs = st.executeQuery(sql);
        
        while (rs.next()) {
        JSONObject obj = new JSONObject();
        String id = rs.getString("id_client");
        String numtel = rs.getString("numtel");
        String nom = rs.getString("nom");
        String sexe = rs.getString("sexe");
        String email = rs.getString("email");
        String solde = rs.getString("solde");
        String age = rs.getString("age");
        
        obj.put("numtel", numtel);
        obj.put("nom", nom);
        obj.put("sexe", sexe);
        obj.put("email", email);
        obj.put("age", age);
        obj.put("solde", solde);
        obj.put("id", id);
        list.add(obj);        
    }        
        
        return list;
        
        
    }
    
    public JSONArray get_client_edit (String id) throws SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement st;
    ResultSet rs;
    Connection connection = obj_db_connection.get_connection();
    st = connection.prepareStatement("select id_client, numtel, nom, sexe, email, solde, age from client where id_client = ?");
    st.setString(1, id);
    rs = st.executeQuery();
    
    if (rs.next() == true){
    String id1 = rs.getString(1);
    String numtel = rs.getString(2);
    String nom = rs.getString(3);
    String sexe = rs.getString(4);
    String email = rs.getString(5);
    String solde = rs.getString(6);
    String age = rs.getString(7);
    
    JSONObject obj = new JSONObject();
    
    obj.put("id", id1);
    obj.put("numtel", numtel);
    obj.put("nom", nom);
    obj.put("sexe", sexe);
    obj.put("email", email);
    obj.put("solde", solde);
    obj.put("age", age);
    
    list.add(obj);
    }
    return list;
    }
    
    public JSONArray update_values(String id, String numtel, String nom, String sexe, String email, String solde, String age) throws ClassNotFoundException, SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_DB_Connection = new DB_Connection();
    Connection connection = obj_DB_Connection.get_connection();
    
    PreparedStatement ps = null;

    try {
        String query = "update client set numtel = ?, nom = ?, sexe = ?, email = ?, solde = ?, age = ? where id_client = ?";
        ps = connection.prepareStatement(query);
        
        int soldes = Integer.valueOf(solde);
        int ages = Integer.valueOf(age);
        
        ps.setString(1, numtel);
        ps.setString(2, nom);
        ps.setString(3, sexe);
        ps.setString(4, email);
        ps.setInt(5, soldes);
        ps.setInt(6, ages);
        ps.setString(7, id);
        
        ps.executeUpdate();
        
        JSONObject obj = new JSONObject();
        obj.put("text", "Client updated");
        obj.put("hi", "hii");
        list.add(obj);
    } catch (SQLException ex) {
        System.err.println(ex);
     }
    return list;
  }
    
    public void delete_values(String id) throws SQLException {
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement pst, ps_envoyer, ps_retrait;
    Connection connection = obj_db_connection.get_connection();
    pst = connection.prepareStatement("delete from client where id_client = ?");
    
    ps_envoyer = connection.prepareStatement("delete from envoyer where numEnv = ? OR numRec = ?" );
    ps_retrait = connection.prepareStatement("delete from retrait where numtel = ? ");
    ps_envoyer.setString(1, id);
    ps_envoyer.setString(2, id);
    ps_retrait.setString(1, id);
    ps_envoyer.executeUpdate();
    ps_retrait.executeUpdate();
    
    pst.setString(1, id);
    pst.executeUpdate();
    }
}
