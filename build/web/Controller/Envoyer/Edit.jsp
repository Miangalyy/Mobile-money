<%-- 
    Document   : Edit
    Created on : 27 juin 2024, 09:43:21
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="CRUD.Envoyer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
    JSONArray list = new JSONArray();
    Envoyer obj_envoyer = new Envoyer();
    list = obj_envoyer.get_envoyer_edit(id);
    
        
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.print(list.toJSONString());
    out.flush();
    
%>
