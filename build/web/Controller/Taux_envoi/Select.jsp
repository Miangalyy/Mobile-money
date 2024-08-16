<%-- 
    Document   : Select
    Created on : 25 juin 2024, 17:27:27
    Author     : pc
--%>

<%@page import="CRUD.Taux_envoi"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Taux_envoi obj_taux = new Taux_envoi();
    JSONArray list = new JSONArray();
    list = obj_taux.get_Taux();
    out.print(list.toJSONString());
    out.flush();
%>
