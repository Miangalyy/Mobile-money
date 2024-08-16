<%-- 
    Document   : Update
    Created on : 27 juin 2024, 11:23:36
    Author     : pc
--%>

<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Envoyer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    String idEnv = request.getParameter("idEnv");
    String numEnv = request.getParameter("numEnv");
    String numRec = request.getParameter("numRec");
    String montant = request.getParameter("montant");
    String date = request.getParameter("date");
    String payer_frais = request.getParameter("payer_frais");
    String raison = request.getParameter("raison");
    Envoyer obj_client = new Envoyer();
    list = obj_client.update_values(id, idEnv, numEnv, numRec, montant, date, payer_frais, raison);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
