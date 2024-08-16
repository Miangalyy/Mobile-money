<%-- 
    Document   : Imprimer
    Created on : 28 juin 2024, 12:46:23
    Author     : pc
--%>

<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="CRUD.Pdf"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    JSONArray list = new JSONArray();
    String id = request.getParameter("id");
    String year = request.getParameter("year");
    String month = request.getParameter("month");
    
    Pdf pdf = new Pdf();
    pdf.imprimer(id, year, month);

    JSONObject obj = new JSONObject();
    obj.put("name", "success");
    list.add(obj);
    out.print(list.toJSONString());
    out.flush();
%>
