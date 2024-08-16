<%-- 
    Document   : Select
    Created on : 25 juin 2024, 19:30:54
    Author     : pc
--%>

<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Taux_recep"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Taux_recep obj_taux = new Taux_recep();
    JSONArray list = new JSONArray();
    list = obj_taux.get_Taux();
    out.print(list.toJSONString());
    out.flush();
%>
