<%-- 
    Document   : Delete
    Created on : 26 juin 2024, 08:04:41
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Taux_recep"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    Taux_recep obj_delete = new Taux_recep();
    obj_delete.delete_values(id);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
    
%>
