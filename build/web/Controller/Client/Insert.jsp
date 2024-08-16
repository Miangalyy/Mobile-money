<%-- 
    Document   : Insert
    Created on : 25 juin 2024, 11:26:39
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Client"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String numtel = request.getParameter("numtel");
    String nom = request.getParameter("nom");
    String sexe = request.getParameter("sexe");
    String email = request.getParameter("email");
    String solde = request.getParameter("solde");
    String age = request.getParameter("age");
    Client obj_client = new Client();
    obj_client.insert_values(numtel, nom, sexe, email, solde, age);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
