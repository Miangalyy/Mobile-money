<%-- 
    Document   : Edit
    Created on : 25 juin 2024, 14:14:11
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Client"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
    JSONArray list = new JSONArray();
    Client obj_client = new Client();
    list = obj_client.get_client_edit(id);
    
        
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.print(list.toJSONString());
    out.flush();
    
%>
