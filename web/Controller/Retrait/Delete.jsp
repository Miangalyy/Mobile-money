<%-- 
    Document   : Delete
    Created on : 27 juin 2024, 20:37:32
    Author     : pc
--%>

<%@page import="CRUD.Retrait"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    Retrait obj_delete = new Retrait();
    list = obj_delete.delete_values(id);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
    
%>
