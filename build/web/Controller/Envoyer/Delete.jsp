<%-- 
    Document   : Delete
    Created on : 27 juin 2024, 14:17:56
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Envoyer"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    Envoyer obj_delete = new Envoyer();
    list = obj_delete.delete_values(id);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
    
%>
