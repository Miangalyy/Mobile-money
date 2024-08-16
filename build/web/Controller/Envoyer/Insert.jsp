<%-- 
    Document   : Insert
    Created on : 26 juin 2024, 08:52:31
    Author     : pc
--%>

<%@page import="CRUD.Envoyer"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String idEnv = request.getParameter("idEnv");
    String numEnv = request.getParameter("numEnv");
    String numRec = request.getParameter("numRec");
    String montant = request.getParameter("montant");
    String date = request.getParameter("date");
    String payer_frais = request.getParameter("payer_frais");
    String raison = request.getParameter("raison");
    Envoyer obj_client = new Envoyer();
    list = obj_client.insert_values(idEnv, numEnv, numRec, montant, date, payer_frais, raison);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
