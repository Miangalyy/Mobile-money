<%-- 
    Document   : Insert
    Created on : 25 juin 2024, 19:14:15
    Author     : pc
--%>

<%@page import="CRUD.Taux_recep"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String idEnv = request.getParameter("idRec");
    String montant1 = request.getParameter("montant1");
    String montant2 = request.getParameter("montant2");
    String frais_env = request.getParameter("frais_rec");
    Taux_recep obj_taux = new Taux_recep();
    obj_taux.insert_values(idEnv, montant1, montant2, frais_env);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
