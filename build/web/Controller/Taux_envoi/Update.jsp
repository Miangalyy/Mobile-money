<%-- 
    Document   : Update
    Created on : 25 juin 2024, 18:03:50
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Taux_envoi"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    String idEnv = request.getParameter("idEnv");
    String montant1 = request.getParameter("montant1");
    String montant2 = request.getParameter("montant2");
    String frais_env = request.getParameter("frais_env");
    
    Taux_envoi obj_taux = new Taux_envoi();
    list = obj_taux.update_values(id, idEnv, montant1, montant2, frais_env);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
