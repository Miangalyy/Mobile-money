<%-- 
    Document   : Update
    Created on : 25 juin 2024, 14:29:54
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Client"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    String nom = request.getParameter("nom");
    String numtel = request.getParameter("numtel");
    String sexe = request.getParameter("sexe");
    String email = request.getParameter("email");
    String solde = request.getParameter("solde");
    String age = request.getParameter("age");
    
    Client obj_client = new Client();
    list = obj_client.update_values(id, numtel, nom, sexe, email, solde, age);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
