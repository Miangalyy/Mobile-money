<%-- 
    Document   : Update
    Created on : 27 juin 2024, 18:50:48
    Author     : pc
--%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Retrait"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    String idrecep = request.getParameter("idrecep");
    String numtel = request.getParameter("numtel");
    String montant = request.getParameter("montant");
    String daterecep = request.getParameter("daterecep");

    Retrait obj_client = new Retrait();
    list = obj_client.update_values(id, idrecep, numtel, montant, daterecep);
    
    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.println(list.toJSONString());
    out.flush();
%>
