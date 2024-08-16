<%-- 
    Document   : EnvoyerMail
    Created on : 30 juin 2024, 20:30:45
    Author     : pc
--%>

<%@page import="EMAIL.User"%>
<%@page import="EMAIL.SendEmail"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
                String name = request.getParameter("username");
                    String email = request.getParameter("useremail");

                    SendEmail sm = new SendEmail();
                    String code = sm.getRandom();

                    User user = new User(name, email, code);

                    boolean test = sm.sendEmail(user);
%>
