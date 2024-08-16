<%-- 
    Document   : Delete
    Created on : 25 juin 2024, 14:50:40
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Client"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    Client obj_delete = new Client();
    obj_delete.delete_values(id);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
    
%>
