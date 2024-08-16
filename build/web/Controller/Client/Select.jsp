<%-- 
    Document   : Select
    Created on : 25 juin 2024, 12:04:19
    Author     : pc
--%>

<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Client"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Client obj_client = new Client();
    JSONArray list = new JSONArray();
     list = obj_client.get_client();
    out.print(list.toJSONString());
    out.flush();
%>
