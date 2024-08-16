<%-- 
    Document   : Edit
    Created on : 27 juin 2024, 16:42:08
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Retrait"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
    JSONArray list = new JSONArray();
    Retrait obj_envoyer = new Retrait();
    list = obj_envoyer.get_retrait_edit(id);
    
        
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.print(list.toJSONString());
    out.flush();
    
%>
