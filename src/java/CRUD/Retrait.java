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
public class Retrait {
    public JSONArray insert_values(String idrecep, String numtel, String montant, String daterecep) throws ClassNotFoundException, SQLException{
        JSONArray list = new JSONArray();
        int montants = Integer.valueOf(montant);
        DB_Connection obj_DB_Connection = new DB_Connection();
        try(Connection connection = obj_DB_Connection.get_connection()){
            String personne_retrait = "select solde from client where id_client = ?";
            String frais_recep = "SELECT frais_rec FROM taux_recep WHERE montant1 <= ? AND montant2 >= ?";
            try(PreparedStatement verifier_solde_ps = connection.prepareStatement(personne_retrait)){
                verifier_solde_ps.setString(1, numtel);
                try(ResultSet verifier_solde_rs = verifier_solde_ps.executeQuery()) {
                    if (verifier_solde_rs.next() == true) {
                        int solde = verifier_solde_rs.getInt(1);
                        try(PreparedStatement frais_retrait_ps = connection.prepareStatement(frais_recep)) {
                            frais_retrait_ps.setInt(1, montants);
                            frais_retrait_ps.setInt(2, montants);
                            try(ResultSet frais_retrait_rs = frais_retrait_ps.executeQuery()) {
                                if (frais_retrait_rs.next() == true) {
                                    int frais_retrait = frais_retrait_rs.getInt(1);
                                    int solde_final = solde - montants - frais_retrait;
                                    
                                    if (solde_final >= 0) {
                                        String insert_envoyer = "insert into retrait(idrecep, numtel, montant, daterecep) values(?, ?, ?, ?) ";
                                        String update_client = "update client set solde = ? where id_client = ?";
                                        try(PreparedStatement insert_envoyer_ps = connection.prepareStatement(insert_envoyer); PreparedStatement update_client_ps = connection.prepareStatement(update_client)){
                                            insert_envoyer_ps.setString(1, idrecep);
                                            insert_envoyer_ps.setString(2, numtel);
                                            insert_envoyer_ps.setString(3, montant);
                                            insert_envoyer_ps.setString(4, daterecep);
                                            insert_envoyer_ps.executeUpdate();
                                            
                                            update_client_ps.setInt(1, solde_final);
                                            update_client_ps.setString(2, numtel);
                                            update_client_ps.executeUpdate();

                                            JSONObject obj = new JSONObject();
                                            obj.put("message", "Retrait avec success");
                                            obj.put("status", "true");
                                            list.add(obj);
                                        }
                                        
                                    } else {
                                        JSONObject obj = new JSONObject();
                                        obj.put("message", "Votre solde est insuffisant");
                                        obj.put("status", "false");
                                        list.add(obj);
                                    }
                                } else {
                                    JSONObject obj = new JSONObject();
                                    obj.put("message", "Frais de retrait introvable");
                                    obj.put("status", "false");
                                    list.add(obj);
                                }
                            }
                        }
                    } else {
                        JSONObject obj = new JSONObject();
                        obj.put("message", "Le client n'existe pas");
                        obj.put("status", "false");
                        list.add(obj);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JSONObject obj_false = new JSONObject();
            obj_false.put("message", "Une erreur s'est produite");
            obj_false.put("status", "false");
            list.add(obj_false);
        }
        return list;
        
    }

    public JSONArray get_envoyer() throws SQLException {
        JSONArray list = new JSONArray();
        DB_Connection obj_db_connection = new DB_Connection();
        Connection connection = obj_db_connection.get_connection();
        PreparedStatement pst;
        ResultSet rs;

        Statement st = connection.createStatement();
        String sql = "SELECT \n"
                + "    retrait.id_retrait AS id_retrait, \n"
                + "    retrait.idrecep AS idrecep, \n"
                + "    retrait.numtel AS numtel, \n"
                + "    retrait.montant AS montant, \n"
                + "    retrait.daterecep AS daterecep, \n"
                + "    recepteur.nom AS nomRec, \n"
                + "    recepteur.numtel AS numRec \n"
                + "FROM \n"
                + "    retrait\n"
                + "INNER JOIN \n"
                + "    client AS recepteur ON retrait.numtel = recepteur.id_client;";
        rs = st.executeQuery(sql);

        while (rs.next()) {
            JSONObject obj = new JSONObject();
            String id = rs.getString("id_retrait");
            String idrecep = rs.getString("idrecep");
            String numtel = rs.getString("numtel");
            String montant = rs.getString("montant");
            String daterecep = rs.getString("daterecep");
            String nomRec = rs.getString("nomRec");
            String numRec = rs.getString("numRec");

            obj.put("id", id);
            obj.put("idrecep", idrecep);
            obj.put("numtel", numtel);
            obj.put("montant", montant);
            obj.put("daterecep", daterecep);
            obj.put("nomRec", nomRec);
            obj.put("numRec", numRec);
            list.add(obj);
        }

        return list;

    }
    
    public JSONArray get_retrait_edit(String id) throws SQLException {
        JSONArray list = new JSONArray();
        DB_Connection obj_db_connection = new DB_Connection();
        PreparedStatement st;
        ResultSet rs;
        Connection connection = obj_db_connection.get_connection();
        st = connection.prepareStatement("select id_retrait, idrecep, numtel, montant, daterecep from retrait where id_retrait = ?");
        st.setString(1, id);
        rs = st.executeQuery();

        if (rs.next() == true) {
            String id_retrait = rs.getString(1);
            String idrecep = rs.getString(2);
            String numtel = rs.getString(3);
            String montant = rs.getString(4);
            String daterecep = rs.getString(5);

            JSONObject obj = new JSONObject();

            obj.put("id", id_retrait);
            obj.put("idrecep", idrecep);
            obj.put("numtel", numtel);
            obj.put("montant", montant);
            obj.put("daterecep", daterecep);

            list.add(obj);
        }
        return list;
    }
    
    public JSONArray update_values(String id, String idrecep, String numtel, String montant, String daterecep) throws ClassNotFoundException, SQLException{
        JSONArray list = new JSONArray();
        int montants = Integer.valueOf(montant);
        DB_Connection obj_DB_Connection = new DB_Connection();
        String ancien_client_query = "SELECT e.idrecep as idrecep, e.numtel AS numtel, e.montant AS montant, e.daterecep AS daterecep, tr.frais_rec AS frais_rec FROM retrait e JOIN taux_recep tr ON e.montant BETWEEN tr.montant1 AND tr.montant2 WHERE e.id_retrait = ?";
        try(Connection connection = obj_DB_Connection.get_connection()) {
            try(PreparedStatement ancien_client_ps = connection.prepareStatement(ancien_client_query)){
                ancien_client_ps.setString(1, id);
                try(ResultSet ancien_client_rs = ancien_client_ps.executeQuery()){
                    if (ancien_client_rs.next() == true) {
                        String ancien_idrecep = ancien_client_rs.getString(1);
                        String ancien_numtel = ancien_client_rs.getString(2);
                        int ancien_montant = ancien_client_rs.getInt(3);
                        String ancien_date = ancien_client_rs.getString(4);
                        int ancien_frais = ancien_client_rs.getInt(5);
                        String ancien_client_update = "update client set solde = solde + ? where id_client = ?";
                        try(PreparedStatement ancien_client_update_rs = connection.prepareStatement(ancien_client_update)){
                            int final_ancien_solde = ancien_montant + ancien_frais;
                            ancien_client_update_rs.setInt(1, final_ancien_solde);
                            ancien_client_update_rs.setString(2, ancien_numtel);
                            ancien_client_update_rs.executeUpdate();
                            
                            String personne_retrait = "select solde from client where id_client = ?";
                            String frais_recep = "SELECT frais_rec FROM taux_recep WHERE montant1 <= ? AND montant2 >= ?";
                            
                            //update
                            try (PreparedStatement verifier_solde_ps = connection.prepareStatement(personne_retrait)) {
                                verifier_solde_ps.setString(1, numtel);
                                try (ResultSet verifier_solde_rs = verifier_solde_ps.executeQuery()) {
                                    if (verifier_solde_rs.next() == true) {
                                        int solde = verifier_solde_rs.getInt(1);
                                        try (PreparedStatement frais_retrait_ps = connection.prepareStatement(frais_recep)) {
                                            frais_retrait_ps.setInt(1, montants);
                                            frais_retrait_ps.setInt(2, montants);
                                            try (ResultSet frais_retrait_rs = frais_retrait_ps.executeQuery()) {
                                                if (frais_retrait_rs.next() == true) {
                                                    int frais_retrait = frais_retrait_rs.getInt(1);
                                                    int solde_final = solde - montants - frais_retrait;

                                                    if (solde_final >= 0) {
                                                        String insert_envoyer = "update retrait set idrecep = ?, numtel = ?, montant = ?, daterecep = ? where id_retrait = ?";
                                                        String update_client = "update client set solde = ? where id_client = ?";
                                                        try (PreparedStatement insert_envoyer_ps = connection.prepareStatement(insert_envoyer); PreparedStatement update_client_ps = connection.prepareStatement(update_client)) {
                                                            insert_envoyer_ps.setString(1, idrecep);
                                                            insert_envoyer_ps.setString(2, numtel);
                                                            insert_envoyer_ps.setString(3, montant);
                                                            insert_envoyer_ps.setString(4, daterecep);
                                                            insert_envoyer_ps.setString(5, id);
                                                            insert_envoyer_ps.executeUpdate();

                                                            update_client_ps.setInt(1, solde_final);
                                                            update_client_ps.setString(2, numtel);
                                                            update_client_ps.executeUpdate();

                                                            JSONObject obj = new JSONObject();
                                                            obj.put("message", "Retrait avec success");
                                                            obj.put("status", "true");
                                                            list.add(obj);
                                                        }

                                                    } else {
                                                        JSONObject obj = new JSONObject();
                                                        obj.put("message", "Votre solde est insuffisant");
                                                        obj.put("status", "false");
                                                        list.add(obj);
                                                    }
                                                } else {
                                                    JSONObject obj = new JSONObject();
                                                    obj.put("message", "Frais de retrait introvable");
                                                    obj.put("status", "false");
                                                    list.add(obj);
                                                }
                                            }
                                        }
                                    } else {
                                        JSONObject obj = new JSONObject();
                                        obj.put("message", "Le client n'existe pas");
                                        obj.put("status", "false");
                                        list.add(obj);
                                    }
                                }
                            }
                        }
                    } else {
                        JSONObject obj = new JSONObject();
                        obj.put("message", "Le client n'existe pas");
                        obj.put("status", "false");
                        list.add(obj);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JSONObject obj_false = new JSONObject();
            obj_false.put("message", "Une erreur s'est produite");
            obj_false.put("status", "false");
            list.add(obj_false);
        }
        return list;
    }
    
    public JSONArray delete_values(String id) throws ClassNotFoundException, SQLException{
        JSONArray list = new JSONArray();
        DB_Connection obj_DB_Connection = new DB_Connection();
        try(Connection connection = obj_DB_Connection.get_connection()) {
           String ancien_client_query = "SELECT e.numtel AS numtel, e.montant AS montant, tr.frais_rec AS frais_rec FROM retrait e JOIN taux_recep tr ON e.montant BETWEEN tr.montant1 AND tr.montant2 WHERE e.id_retrait = ?";
           try(PreparedStatement ancien_client_ps = connection.prepareStatement(ancien_client_query)){
               ancien_client_ps.setString(1, id);
               try(ResultSet ancien_client_rs = ancien_client_ps.executeQuery()){
                   if(ancien_client_rs.next() == true) {
                       String ancien_numtel = ancien_client_rs.getString(1);
                       int ancien_montant = ancien_client_rs.getInt(2);
                       int ancien_frais = ancien_client_rs.getInt(3);
                       
                       String ancien_client_update = "update client set solde = solde + ? where id_client = ?";
                       String delete_retrait = "delete from retrait where id_retrait = ?";
                       try (PreparedStatement ancien_client_update_rs = connection.prepareStatement(ancien_client_update); PreparedStatement delete_ps = connection.prepareStatement(delete_retrait)) {
                           int final_ancien_solde = ancien_montant + ancien_frais;
                           ancien_client_update_rs.setInt(1, final_ancien_solde);
                           ancien_client_update_rs.setString(2, ancien_numtel);
                           ancien_client_update_rs.executeUpdate();
                           
                           delete_ps.setString(1, id);
                           delete_ps.executeUpdate();
                           
                           JSONObject obj = new JSONObject();
                           obj.put("message", "operation supprimer avec success");
                           obj.put("status", "true");
                           list.add(obj);
                       }
                   } else {
                       JSONObject obj = new JSONObject();
                       obj.put("message", "Le client n'existe pas");
                       obj.put("status", "false");
                       list.add(obj);
                   }
               }
           }
        }
        catch (SQLException e) {
            e.printStackTrace();
            JSONObject obj_false = new JSONObject();
            obj_false.put("message", "Une erreur s'est produite");
            obj_false.put("status", "false");
            list.add(obj_false);
        }
        return list;
    }
}
