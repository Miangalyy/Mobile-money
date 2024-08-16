<%-- 
    Document   : TestEmail
    Created on : 30 juin 2024, 18:50:44
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="Controller/Envoyer/EnvoyerMail.jsp" method="post">
            <label>User name :</label><br>
            <input type="text" name="username" /><br>
            <label>Email Adress :</label><br>
            <input type="text" name="useremail" /><br>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>
