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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author pc
 */
public class Envoyer {
    public JSONArray insert_values(String idEnv, String numEnv, String numRec, String montant, String date, String payer_frais, String raison) throws ClassNotFoundException, SQLException {
    DB_Connection obj_DB_Connection = new DB_Connection();
    Connection connection = obj_DB_Connection.get_connection();
    JSONArray list = new JSONArray();
    PreparedStatement ps = null;
    PreparedStatement taux_ps = null;
    PreparedStatement verifie_montant_ps = null;
    PreparedStatement frais_recep_ps = null;
    PreparedStatement moins_ps = null;
    PreparedStatement plus_ps = null;
    ResultSet rs, verifie_montant_rs, frais_recep_rs;

    try {
        String query = "insert into envoyer(idEnv, numEnv, numRec, montant, date, payer_frais, raison)values(?, ?, ?, ?, ?, ?, ?)";
        String taux_query = "select frais_env from taux_envoi where montant1 <= ? and montant2 >= ?";
        String taux_query_recep = "select frais_rec from taux_recep where montant1 <= ? and montant2 >= ?";
        String verifie_montant_query = "select solde,email,nom, numtel from client where id_client = ?";
        int montants = Integer.valueOf(montant);
        verifie_montant_ps = connection.prepareStatement(verifie_montant_query);
        verifie_montant_ps.setString(1, numEnv);
        verifie_montant_rs = verifie_montant_ps.executeQuery();
        
            if (verifie_montant_rs.next() == true) {

                int solde = verifie_montant_rs.getInt(1);
                String email = verifie_montant_rs.getString(2);
                String nom = verifie_montant_rs.getString(3);
                String numtel = verifie_montant_rs.getString(4);
                
                String moins_env = "update client set solde = ? where id_client = ?";
                String plus_recep = "update client set solde = solde + ? where id_client = ?";
                moins_ps = connection.prepareStatement(moins_env);
                plus_ps = connection.prepareStatement(plus_recep);
                taux_ps = connection.prepareStatement(taux_query);

                taux_ps.setInt(1, montants);
                taux_ps.setInt(2, montants);
                rs = taux_ps.executeQuery();
                if (rs.next() == true) {
                    int frais_env = rs.getInt(1);
                    int enfin_solde;
                    if (payer_frais.equals("non")) {
                        enfin_solde = solde - montants - frais_env;
                        
                        if (enfin_solde >= 0) {

                            moins_ps.setInt(1, enfin_solde);
                            moins_ps.setString(2, numEnv);
                            plus_ps.setInt(1, montants);
                            plus_ps.setString(2, numRec);
                            plus_ps.executeUpdate();
                            moins_ps.executeUpdate();

                            ps = connection.prepareStatement(query);
                            int numEnvs = Integer.valueOf(numEnv);
                            int numRecs = Integer.valueOf(numRec);
                            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            // LocalDate localDate = LocalDate.parse(date, formatter);
                            // Date dates = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                            ps.setString(1, idEnv);
                            ps.setInt(2, numEnvs);
                            ps.setInt(3, numRecs);
                            ps.setInt(4, montants);
                            ps.setString(5, date);
                            ps.setString(6, payer_frais);
                            ps.setString(7, raison);
                            ps.executeUpdate();
                            //Envoi email
                            String recepteur_query  = "select email, nom, solde, numtel from client where id_client = ?";
                            PreparedStatement recepteur_ps = connection.prepareStatement(recepteur_query);
                            recepteur_ps.setString(1, numRec);
                            ResultSet recepteur_rs = recepteur_ps.executeQuery();
                            if(recepteur_rs.next() == true) {
                                String email_recepteur = recepteur_rs.getString(1);
                                String nom_recepteur = recepteur_rs.getString(2);
                                String solde_recepteur = recepteur_rs.getString(3);
                                String numtel_recepteur = recepteur_rs.getString(4);
                                SimpleEmail se = new SimpleEmail();
                                String subject = "Transfert d'argent";
                                String body = "Vous avez transferer de " + montant + " Ariary a "+nom_recepteur+ " ("+numtel_recepteur+ ") le "+date+" .De raison : "+raison+" . Et votre solde principal est : "+enfin_solde+" Ariary.";
                                se.envoyerMail(email, subject, body);
                                
                                SimpleEmail se_recepteur = new SimpleEmail();
                                String subject_recepteur = "Reception d'argent";
                                String body_recepteur = "Vous avez recu de " + montant + " Ariary de "+nom+ " ("+numtel+ ") le "+date+".De raison de "+raison+" . Et votre solde principal est : "+solde_recepteur+" Ariary.";
                                se_recepteur.envoyerMail(email_recepteur, subject_recepteur, body_recepteur);
                                
                                
                                JSONObject obj = new JSONObject();
                                obj.put("message", "Envoyer avec success");
                                obj.put("status", "true");
                                list.add(obj);
                                
                            } else {
                                JSONObject obj = new JSONObject();
                                obj.put("message", "Recepteur introuvable");
                                obj.put("status", "false");
                                list.add(obj);
                            }
                            
                        } else {
                            JSONObject obj_false = new JSONObject();
                            obj_false.put("message", "Votre solde est insuffisant");
                            obj_false.put("status", "false");
                            list.add(obj_false);
                        }
                    } else {
                        
                        frais_recep_ps = connection.prepareStatement(taux_query_recep);
                        frais_recep_ps.setInt(1, montants);
                        frais_recep_ps.setInt(2, montants);
                        frais_recep_rs = frais_recep_ps.executeQuery();
                        
                        if (frais_recep_rs.next() == true) {
                            int frais_recep = frais_recep_rs.getInt(1);
                            enfin_solde = solde - montants - frais_recep - frais_env;
                            
                            if (enfin_solde >= 0) {
                                
                                moins_ps.setInt(1, enfin_solde);
                                moins_ps.setString(2, numEnv);
                                plus_ps.setInt(1, (montants+frais_recep));
                                plus_ps.setString(2, numRec);
                                plus_ps.executeUpdate();
                                moins_ps.executeUpdate();

                                ps = connection.prepareStatement(query);
                                int numEnvs = Integer.valueOf(numEnv);
                                int numRecs = Integer.valueOf(numRec);
                                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                // LocalDate localDate = LocalDate.parse(date, formatter);
                                // Date dates = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                                ps.setString(1, idEnv);
                                ps.setInt(2, numEnvs);
                                ps.setInt(3, numRecs);
                                ps.setInt(4, montants+frais_recep);
                                ps.setString(5, date);
                                ps.setString(6, payer_frais);
                                ps.setString(7, raison);
                                ps.executeUpdate();
                                //Envoi email

                                String recepteur_query = "select email, nom, solde, numtel from client where id_client = ?";
                                PreparedStatement recepteur_ps = connection.prepareStatement(recepteur_query);
                                recepteur_ps.setString(1, numRec);
                                ResultSet recepteur_rs = recepteur_ps.executeQuery();
                                if (recepteur_rs.next() == true) {
                                    String email_recepteur = recepteur_rs.getString(1);
                                    String nom_recepteur = recepteur_rs.getString(2);
                                    String solde_recepteur = recepteur_rs.getString(3);
                                    String numtel_recepteur = recepteur_rs.getString(4);
                                    int montant_env = montants + frais_recep;
        
                                    SimpleEmail se = new SimpleEmail();
                                    String subject = "Transfert d'argent";
                                    String body = "Vous avez transferer de " + montant_env + " Ariary a " + nom_recepteur + " (" + numtel_recepteur + ") le " + date + " .De raison : " + raison + " . Et votre solde principal est : " + enfin_solde+" Ariary.";
                                    se.envoyerMail(email, subject, body);

                                    SimpleEmail se_recepteur = new SimpleEmail();
                                    String subject_recepteur = "Reception d'argent";
                                    String body_recepteur = "Vous avez recu de " + montant_env + " Ariary de " + nom + " (" + numtel + ") le " + date +  " , dont "+frais_recep+" Ariary est le frais de reception .De raison de " + raison + " . Et votre solde principal est : " + solde_recepteur+" Ariary.";
                                    se_recepteur.envoyerMail(email_recepteur, subject_recepteur, body_recepteur);

                                    JSONObject obj = new JSONObject();
                                    obj.put("message", "Envoyer avec success");
                                    obj.put("status", "true");
                                    list.add(obj);

                                } else {
                                    JSONObject obj = new JSONObject();
                                    obj.put("message", "Recepteur introuvable");
                                    obj.put("status", "false");
                                    list.add(obj);
                                }
                            } else {
                                JSONObject obj_false = new JSONObject();
                                obj_false.put("message", "Votre solde est insuffisant");
                                obj_false.put("status", "false");
                                list.add(obj_false);
                            }
                        } else {
                            
                            JSONObject obj_false = new JSONObject();
                            obj_false.put("message", "Aucun frais trouve");
                            obj_false.put("status", "false");
                            list.add(obj_false);                     
                        }
                        
                    }

                } else {
                    JSONObject obj_false = new JSONObject();
                    obj_false.put("message", "Aucun frais trouvé pour le montant spécifié");
                    obj_false.put("status", "false");
                    list.add(obj_false);
                }

            } else {
                JSONObject obj_false = new JSONObject();
                obj_false.put("message", "Client non trouvé");
                obj_false.put("status", "false");
                list.add(obj_false);
            }

    } catch (SQLException ex) {
        System.err.println(ex);
     }
 finally {
            if (verifie_montant_ps != null) {
                verifie_montant_ps.close();
            }
            if (taux_ps != null) {
                taux_ps.close();
            }
            if (moins_ps != null) {
                moins_ps.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (frais_recep_ps != null) {
                frais_recep_ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
     return list;
  }
    
    public JSONArray get_envoyer () throws SQLException {
        JSONArray list = new JSONArray();
        DB_Connection obj_db_connection = new DB_Connection();
        Connection connection = obj_db_connection.get_connection();
        PreparedStatement pst;
        ResultSet rs;
        
        Statement st = connection.createStatement();
        String sql = "SELECT \n"
                + "    envoyer.id_envoyer AS id_envoyer, \n"
                + "    envoyer.idEnv AS idEnv, \n"
                + "    envoyer.numEnv AS numEnv, \n"
                + "    envoyer.numRec AS numRec, \n"
                + "    envoyer.montant AS montant, \n"
                + "    envoyer.date AS date, \n"
                + "    envoyer.payer_frais AS payer_frais, \n"
                + "    envoyer.raison AS raison, \n"
                + "    envoyeur.nom AS nomEnv, \n"
                + "    recepteur.nom AS nomRec\n"
                + "FROM \n"
                + "    envoyer\n"
                + "INNER JOIN \n"
                + "    client AS envoyeur ON envoyer.numEnv = envoyeur.id_client\n"
                + "INNER JOIN \n"
                + "    client AS recepteur ON envoyer.numRec = recepteur.id_client;";
        rs = st.executeQuery(sql);
        
        while (rs.next()) {
        JSONObject obj = new JSONObject();
        String id = rs.getString("id_envoyer");
        String idEnv = rs.getString("idEnv");
        String numRec = rs.getString("numRec");
        String numEnv = rs.getString("numEnv");
        String montant = rs.getString("montant");
        String date = rs.getString("date");
        String payer_frais = rs.getString("payer_frais");
        String raison = rs.getString("raison");
        String nomEnv = rs.getString("nomEnv");
        String nomRec = rs.getString("nomRec");
        
        obj.put("id", id);
        obj.put("idEnv", idEnv);
        obj.put("numEnv", numEnv);
        obj.put("numRec", numRec);
        obj.put("montant", montant);
        obj.put("date", date);
        obj.put("payer_frais", payer_frais);
        obj.put("raison", raison);
        obj.put("nomEnv", nomEnv);
        obj.put("nomRec", nomRec);
        list.add(obj);        
    }        
        
        return list;
        
        
    }
    
    public JSONArray get_envoyer_edit (String id) throws SQLException {
    JSONArray list = new JSONArray();
    DB_Connection obj_db_connection = new DB_Connection();
    PreparedStatement st;
    ResultSet rs;
    Connection connection = obj_db_connection.get_connection();
    st = connection.prepareStatement("select id_envoyer, idEnv, numEnv, numRec, montant, date, payer_frais, raison from envoyer where id_envoyer = ?");
    st.setString(1, id);
    rs = st.executeQuery();
    
    if (rs.next() == true){
    String id_envoyer = rs.getString(1);
    String idEnv = rs.getString(2);
    String numEnv = rs.getString(3);
    String numRec = rs.getString(4);
    String montant = rs.getString(5);
    String date = rs.getString(6);
    String payer_frais = rs.getString(7);
    String raison = rs.getString(8);
    
    JSONObject obj = new JSONObject();
    
    obj.put("id", id_envoyer);
    obj.put("idEnv", idEnv);
    obj.put("numEnv", numEnv);
    obj.put("numRec", numRec);
    obj.put("montant", montant);
    obj.put("date", date);
    obj.put("payer_frais", payer_frais);
    obj.put("raison", raison);
    
    list.add(obj);
    }
    return list;
    }
    
    public JSONArray update_values(String id, String idEnv, String numEnv, String numRec, String montant, String date, String payer_frais, String raison) throws ClassNotFoundException, SQLException {
        DB_Connection obj_DB_Connection = new DB_Connection();
        JSONArray list = new JSONArray();
        
        try (Connection connection = obj_DB_Connection.get_connection()) {
            String personne_modifier = "SELECT e.numEnv AS numEnv, e.numRec AS numRec, e.montant AS montant, e.payer_frais AS payer_frais, tr.frais_rec AS frais_rec, te.frais_env AS frais_env "
                    + "FROM envoyer e "
                    + "JOIN taux_recep tr ON e.montant BETWEEN tr.montant1 AND tr.montant2 "
                    + "JOIN taux_envoi te ON e.montant BETWEEN te.montant1 AND te.montant2 "
                    + "WHERE e.id_envoyer = ?";

            String query = "UPDATE envoyer SET idEnv = ?, numEnv = ?, numRec = ?, montant = ?, date = ?, payer_frais = ?, raison = ? WHERE id_envoyer = ?";
            String taux_query = "SELECT frais_env FROM taux_envoi WHERE montant1 <= ? AND montant2 >= ?";
            String taux_query_recep = "SELECT frais_rec FROM taux_recep WHERE montant1 <= ? AND montant2 >= ?";
            String verifie_montant_query = "SELECT solde FROM client WHERE id_client = ?";
            int montants = Integer.valueOf(montant);

            try (PreparedStatement personne_modifier_ps = connection.prepareStatement(personne_modifier)) {
                personne_modifier_ps.setString(1, id);
                try (ResultSet personne_modifier_rs = personne_modifier_ps.executeQuery()) {
                    if (personne_modifier_rs.next()) {
                        String ancien_env = personne_modifier_rs.getString(1);
                        String ancien_rec = personne_modifier_rs.getString(2);
                        int ancien_montant = personne_modifier_rs.getInt(3);
                        String ancien_payer = personne_modifier_rs.getString(4);
                        int ancien_frais_rec = personne_modifier_rs.getInt(5);
                        int ancien_frais_env = personne_modifier_rs.getInt(6);

                        String ancien_env_query = "UPDATE client SET solde = solde + ? WHERE id_client = ?";
                        String ancien_rec_query = "UPDATE client SET solde = solde - ? WHERE id_client = ?";

                        String nouveau_env_query = "UPDATE client SET solde = solde - ? WHERE id_client = ?";
                        String nouveau_rec_query = "UPDATE client SET solde = solde + ? WHERE id_client = ?";

                        if (ancien_payer.equals("non")) {
                            int ancien_fin_solde = ancien_montant + ancien_frais_env;
                            int ancien_fin_solde_rec = ancien_montant;
                            try (PreparedStatement ancien_env_ps = connection.prepareStatement(ancien_env_query); PreparedStatement ancien_rec_ps = connection.prepareStatement(ancien_rec_query)) {
                                ancien_env_ps.setInt(1, ancien_fin_solde);
                                ancien_env_ps.setString(2, ancien_env);
                                ancien_env_ps.executeUpdate();

                                ancien_rec_ps.setInt(1, ancien_fin_solde_rec);
                                ancien_rec_ps.setString(2, ancien_rec);
                                ancien_rec_ps.executeUpdate();

                                // Verification de montant
                                try (PreparedStatement verifie_montant_ps = connection.prepareStatement(verifie_montant_query)) {
                                    verifie_montant_ps.setString(1, numEnv);
                                    try (ResultSet verifie_montant_rs = verifie_montant_ps.executeQuery()) {
                                        if (verifie_montant_rs.next()) {
                                            int solde = verifie_montant_rs.getInt(1);

                                            String moins_env = "UPDATE client SET solde = ? WHERE id_client = ?";
                                            String plus_recep = "UPDATE client SET solde = solde + ? WHERE id_client = ?";
                                            try (PreparedStatement moins_ps = connection.prepareStatement(moins_env); PreparedStatement plus_ps = connection.prepareStatement(plus_recep); PreparedStatement taux_ps = connection.prepareStatement(taux_query)) {

                                                taux_ps.setInt(1, montants);
                                                taux_ps.setInt(2, montants);
                                                try (ResultSet rs = taux_ps.executeQuery()) {
                                                    if (rs.next()) {
                                                        int frais_env = rs.getInt(1);
                                                        int enfin_solde;
                                                        if (payer_frais.equals("non")) {
                                                            enfin_solde = solde - montants - frais_env;

                                                            if (enfin_solde >= 0) {
                                                                moins_ps.setInt(1, enfin_solde);
                                                                moins_ps.setString(2, numEnv);
                                                                plus_ps.setInt(1, montants);
                                                                plus_ps.setString(2, numRec);
                                                                plus_ps.executeUpdate();
                                                                moins_ps.executeUpdate();

                                                                try (PreparedStatement ps = connection.prepareStatement(query)) {
                                                                    int numEnvs = Integer.valueOf(numEnv);
                                                                    int numRecs = Integer.valueOf(numRec);

                                                                    ps.setString(1, idEnv);
                                                                    ps.setInt(2, numEnvs);
                                                                    ps.setInt(3, numRecs);
                                                                    ps.setInt(4, montants);
                                                                    ps.setString(5, date);
                                                                    ps.setString(6, payer_frais);
                                                                    ps.setString(7, raison);
                                                                    ps.setString(8, id);
                                                                    ps.executeUpdate();

                                                                    JSONObject obj = new JSONObject();
                                                                    obj.put("message", "Envoyer avec succès");
                                                                    obj.put("status", "true");
                                                                    list.add(obj);
                                                                }
                                                            } else {
                                                                JSONObject obj_false = new JSONObject();
                                                                obj_false.put("message", "Votre solde est insuffisant");
                                                                obj_false.put("status", "false");
                                                                list.add(obj_false);
                                                            }
                                                        } else {
                                                            try (PreparedStatement frais_recep_ps = connection.prepareStatement(taux_query_recep)) {
                                                                frais_recep_ps.setInt(1, montants);
                                                                frais_recep_ps.setInt(2, montants);
                                                                try (ResultSet frais_recep_rs = frais_recep_ps.executeQuery()) {
                                                                    if (frais_recep_rs.next()) {
                                                                        int frais_recep = frais_recep_rs.getInt(1);
                                                                        enfin_solde = solde - montants - frais_recep - frais_env;

                                                                        if (enfin_solde >= 0) {
                                                                            moins_ps.setInt(1, enfin_solde);
                                                                            moins_ps.setString(2, numEnv);
                                                                            plus_ps.setInt(1, montants + frais_recep);
                                                                            plus_ps.setString(2, numRec);
                                                                            plus_ps.executeUpdate();
                                                                            moins_ps.executeUpdate();

                                                                            try (PreparedStatement ps = connection.prepareStatement(query)) {
                                                                                int numEnvs = Integer.valueOf(numEnv);
                                                                                int numRecs = Integer.valueOf(numRec);

                                                                                ps.setString(1, idEnv);
                                                                                ps.setInt(2, numEnvs);
                                                                                ps.setInt(3, numRecs);
                                                                                ps.setInt(4, montants + frais_recep);
                                                                                ps.setString(5, date);
                                                                                ps.setString(6, payer_frais);
                                                                                ps.setString(7, raison);
                                                                                ps.setString(8, id);
                                                                                ps.executeUpdate();

                                                                                JSONObject obj = new JSONObject();
                                                                                obj.put("message", "Envoyer avec succès");
                                                                                obj.put("status", "true");
                                                                                list.add(obj);
                                                                            }
                                                                        } else {
                                                                            JSONObject obj_false = new JSONObject();
                                                                            obj_false.put("message", "Votre solde est insuffisant");
                                                                            obj_false.put("status", "false");
                                                                            list.add(obj_false);
                                                                        }
                                                                    } else {
                                                                        JSONObject obj_false = new JSONObject();
                                                                        obj_false.put("message", "Aucun frais trouvé");
                                                                        obj_false.put("status", "false");
                                                                        list.add(obj_false);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        JSONObject obj_false = new JSONObject();
                                                        obj_false.put("message", "Aucun frais trouvé pour le montant spécifié");
                                                        obj_false.put("status", "false");
                                                        list.add(obj_false);
                                                    }
                                                }
                                            }
                                        } else {
                                            JSONObject obj_false = new JSONObject();
                                            obj_false.put("message", "Client non trouvé");
                                            obj_false.put("status", "false");
                                            list.add(obj_false);
                                        }
                                    }
                                }
                            }
                        } else {
                            int ancien_fin_solde = ancien_montant + ancien_frais_env + ancien_frais_rec;
                            int ancien_fin_solde_rec = ancien_montant + ancien_frais_rec;
                            try (PreparedStatement ancien_env_ps = connection.prepareStatement(ancien_env_query); PreparedStatement ancien_rec_ps = connection.prepareStatement(ancien_rec_query)) {
                                ancien_env_ps.setInt(1, ancien_fin_solde);
                                ancien_env_ps.setString(2, ancien_env);
                                ancien_env_ps.executeUpdate();

                                ancien_rec_ps.setInt(1, ancien_fin_solde_rec);
                                ancien_rec_ps.setString(2, ancien_rec);
                                ancien_rec_ps.executeUpdate();

                                // Verification de montant
                                try (PreparedStatement verifie_montant_ps = connection.prepareStatement(verifie_montant_query)) {
                                    verifie_montant_ps.setString(1, numEnv);
                                    try (ResultSet verifie_montant_rs = verifie_montant_ps.executeQuery()) {
                                        if (verifie_montant_rs.next()) {
                                            int solde = verifie_montant_rs.getInt(1);

                                            String moins_env = "UPDATE client SET solde = ? WHERE id_client = ?";
                                            String plus_recep = "UPDATE client SET solde = solde + ? WHERE id_client = ?";
                                            try (PreparedStatement moins_ps = connection.prepareStatement(moins_env); PreparedStatement plus_ps = connection.prepareStatement(plus_recep); PreparedStatement taux_ps = connection.prepareStatement(taux_query)) {

                                                taux_ps.setInt(1, montants);
                                                taux_ps.setInt(2, montants);
                                                try (ResultSet rs = taux_ps.executeQuery()) {
                                                    if (rs.next()) {
                                                        int frais_env = rs.getInt(1);
                                                        int enfin_solde;
                                                        if (payer_frais.equals("non")) {
                                                            enfin_solde = solde - montants - frais_env;

                                                            if (enfin_solde >= 0) {
                                                                moins_ps.setInt(1, enfin_solde);
                                                                moins_ps.setString(2, numEnv);
                                                                plus_ps.setInt(1, montants);
                                                                plus_ps.setString(2, numRec);
                                                                plus_ps.executeUpdate();
                                                                moins_ps.executeUpdate();

                                                                try (PreparedStatement ps = connection.prepareStatement(query)) {
                                                                    int numEnvs = Integer.valueOf(numEnv);
                                                                    int numRecs = Integer.valueOf(numRec);

                                                                    ps.setString(1, idEnv);
                                                                    ps.setInt(2, numEnvs);
                                                                    ps.setInt(3, numRecs);
                                                                    ps.setInt(4, montants);
                                                                    ps.setString(5, date);
                                                                    ps.setString(6, payer_frais);
                                                                    ps.setString(7, raison);
                                                                    ps.setString(8, id);
                                                                    ps.executeUpdate();

                                                                    JSONObject obj = new JSONObject();
                                                                    obj.put("message", "Envoyer avec succès");
                                                                    obj.put("status", "true");
                                                                    list.add(obj);
                                                                }
                                                            } else {
                                                                JSONObject obj_false = new JSONObject();
                                                                obj_false.put("message", "Votre solde est insuffisant");
                                                                obj_false.put("status", "false");
                                                                list.add(obj_false);
                                                            }
                                                        } else {
                                                            try (PreparedStatement frais_recep_ps = connection.prepareStatement(taux_query_recep)) {
                                                                frais_recep_ps.setInt(1, montants);
                                                                frais_recep_ps.setInt(2, montants);
                                                                try (ResultSet frais_recep_rs = frais_recep_ps.executeQuery()) {
                                                                    if (frais_recep_rs.next()) {
                                                                        int frais_recep = frais_recep_rs.getInt(1);
                                                                        enfin_solde = solde - montants - frais_recep - frais_env;

                                                                        if (enfin_solde >= 0) {
                                                                            moins_ps.setInt(1, enfin_solde);
                                                                            moins_ps.setString(2, numEnv);
                                                                            plus_ps.setInt(1, montants + frais_recep);
                                                                            plus_ps.setString(2, numRec);
                                                                            plus_ps.executeUpdate();
                                                                            moins_ps.executeUpdate();

                                                                            try (PreparedStatement ps = connection.prepareStatement(query)) {
                                                                                int numEnvs = Integer.valueOf(numEnv);
                                                                                int numRecs = Integer.valueOf(numRec);

                                                                                ps.setString(1, idEnv);
                                                                                ps.setInt(2, numEnvs);
                                                                                ps.setInt(3, numRecs);
                                                                                ps.setInt(4, montants + frais_recep);
                                                                                ps.setString(5, date);
                                                                                ps.setString(6, payer_frais);
                                                                                ps.setString(7, raison);
                                                                                ps.setString(8, id);
                                                                                ps.executeUpdate();

                                                                                JSONObject obj = new JSONObject();
                                                                                obj.put("message", "Envoyer avec succès");
                                                                                obj.put("status", "true");
                                                                                list.add(obj);
                                                                            }
                                                                        } else {
                                                                            JSONObject obj_false = new JSONObject();
                                                                            obj_false.put("message", "Votre solde est insuffisant");
                                                                            obj_false.put("status", "false");
                                                                            list.add(obj_false);
                                                                        }
                                                                    } else {
                                                                        JSONObject obj_false = new JSONObject();
                                                                        obj_false.put("message", "Aucun frais trouvé");
                                                                        obj_false.put("status", "false");
                                                                        list.add(obj_false);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        JSONObject obj_false = new JSONObject();
                                                        obj_false.put("message", "Aucun frais trouvé pour le montant spécifié");
                                                        obj_false.put("status", "false");
                                                        list.add(obj_false);
                                                    }
                                                }
                                            }
                                        } else {
                                            JSONObject obj_false = new JSONObject();
                                            obj_false.put("message", "Client non trouvé");
                                            obj_false.put("status", "false");
                                            list.add(obj_false);
                                        }
                                    }
                                }
                            }
                        }
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

    public JSONArray delete_values(String id) throws ClassNotFoundException, SQLException {
    DB_Connection obj_db_connection = new DB_Connection();
    JSONArray list = new JSONArray();
    
    try (Connection connection = obj_db_connection.get_connection()) {
        String personne_modifier = "SELECT e.numEnv AS numEnv, e.numRec AS numRec, e.montant AS montant, e.payer_frais AS payer_frais, tr.frais_rec AS frais_rec, te.frais_env AS frais_env "
                + "FROM envoyer e "
                + "JOIN taux_recep tr ON e.montant BETWEEN tr.montant1 AND tr.montant2 "
                + "JOIN taux_envoi te ON e.montant BETWEEN te.montant1 AND te.montant2 "
                + "WHERE e.id_envoyer = ?";
        String ancien_env_query = "UPDATE client SET solde = solde + ? WHERE id_client = ?";
        String ancien_rec_query = "UPDATE client SET solde = solde - ? WHERE id_client = ?";
        try (PreparedStatement personne_modifier_ps = connection.prepareStatement(personne_modifier)) {
            personne_modifier_ps.setString(1, id);
            try (ResultSet personne_modifier_rs = personne_modifier_ps.executeQuery()) {
                if (personne_modifier_rs.next() == true) {
                    String ancien_env = personne_modifier_rs.getString(1);
                    String ancien_rec = personne_modifier_rs.getString(2);
                    int ancien_montant = personne_modifier_rs.getInt(3);
                    String ancien_payer = personne_modifier_rs.getString(4);
                    int ancien_frais_rec = personne_modifier_rs.getInt(5);
                    int ancien_frais_env = personne_modifier_rs.getInt(6);
                    
                    if (ancien_payer.equals("non")) {
                        int ancien_fin_solde = ancien_montant + ancien_frais_env;
                        int ancien_fin_solde_rec = ancien_montant;
                        try(PreparedStatement ancien_env_ps = connection.prepareStatement(ancien_env_query); PreparedStatement ancien_rec_ps = connection.prepareStatement(ancien_rec_query); PreparedStatement pst = connection.prepareStatement("delete from envoyer where id_envoyer = ?")) {
                            ancien_env_ps.setInt(1, ancien_fin_solde);
                            ancien_env_ps.setString(2, ancien_env);
                            ancien_env_ps.executeUpdate();

                            ancien_rec_ps.setInt(1, ancien_fin_solde_rec);
                            ancien_rec_ps.setString(2, ancien_rec);
                            ancien_rec_ps.executeUpdate();
                            
                            pst.setString(1, id);
                            pst.executeUpdate();
                            
                            JSONObject obj = new JSONObject();
                            obj.put("message", "Operation supprimer avec success");
                            obj.put("status", "true");
                            list.add(obj);
                        }
                    } else {
                        int ancien_fin_solde = ancien_montant + ancien_frais_env + ancien_frais_rec;
                        int ancien_fin_solde_rec = ancien_montant + ancien_frais_rec;
                        try (PreparedStatement ancien_env_ps = connection.prepareStatement(ancien_env_query); PreparedStatement ancien_rec_ps = connection.prepareStatement(ancien_rec_query); PreparedStatement pst = connection.prepareStatement("delete from envoyer where id_envoyer = ?")) {
                            ancien_env_ps.setInt(1, ancien_fin_solde);
                            ancien_env_ps.setString(2, ancien_env);
                            ancien_env_ps.executeUpdate();

                            ancien_rec_ps.setInt(1, ancien_fin_solde_rec);
                            ancien_rec_ps.setString(2, ancien_rec);
                            ancien_rec_ps.executeUpdate();

                            pst.setString(1, id);
                            pst.executeUpdate();

                            JSONObject obj = new JSONObject();
                            obj.put("message", "Operation supprimer avec success");
                            obj.put("status", "true");
                            list.add(obj);
                    }
                }
                } else {
                    JSONObject obj_false = new JSONObject();
                    obj_false.put("message", "Client supprimer non trouve");
                    obj_false.put("status", "true");
                    list.add(obj_false);
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
