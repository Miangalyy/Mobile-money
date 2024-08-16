<%-- 
    Document   : Select
    Created on : 26 juin 2024, 23:24:22
    Author     : pc
--%>

<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Envoyer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Envoyer obj_client = new Envoyer();
    JSONArray list = new JSONArray();
     list = obj_client.get_envoyer();
    out.print(list.toJSONString());
    out.flush();
%>
