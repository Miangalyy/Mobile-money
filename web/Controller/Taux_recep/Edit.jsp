<%-- 
    Document   : Edit
    Created on : 25 juin 2024, 19:37:39
    Author     : pc
--%>

<%@page import="CRUD.Taux_recep"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String id = request.getParameter("id");
    JSONArray list = new JSONArray();
    Taux_recep obj_taux = new Taux_recep();
    list = obj_taux.get_taux_edit(id);
    
        
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.print(list.toJSONString());
    out.flush();
    
%>
