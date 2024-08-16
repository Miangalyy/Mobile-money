<%-- 
    Document   : Delete
    Created on : 25 juin 2024, 18:10:13
    Author     : pc
--%>

<%@page import="CRUD.Taux_envoi"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    Taux_envoi obj_delete = new Taux_envoi();
    obj_delete.delete_values(id);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
    
%>
