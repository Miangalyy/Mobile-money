<%-- 
    Document   : Select
    Created on : 27 juin 2024, 16:23:55
    Author     : pc
--%>

<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Retrait"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Retrait obj_client = new Retrait();
    JSONArray list = new JSONArray();
     list = obj_client.get_envoyer();
    out.print(list.toJSONString());
    out.flush();
%>
