<%-- 
    Document   : Total
    Created on : 2 juil. 2024, 09:03:42
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
        <link href="component/bootstrap/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
        <link href="component/bootstrap/css/bootstrap.min_1.css" rel="stylesheet" type="text/css"/>
        <script src="component/bootstrap/js/bootstrap.min_1.js" type="text/javascript"></script>
        <script src="component/bootstrap/js/cdn.jsdelivr.net_npm_bootstrap@5.0.0-beta3_dist_js_bootstrap.bundle.min.js" type="text/javascript"></script>
        <link href="component/Style.css" rel="stylesheet" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <nav class="navbar sticky-top navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#" style="margin-right: 100px; width: 500px;">
                    <img src="6272789.jpg" alt="Logo" width="60" height="60" class="d-inline-block align-text-center"style="border-radius: 50%;" >
                    Mobile money
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item mr-2">
                            <a class="nav-link" aria-current="page" href="/Projet_Mobile_money/">Client</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Taux_recep.jsp">Taux reception</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Taux_envoi.jsp">Taux d'envoi</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Envoyer.jsp">Envoyer</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Retrait.jsp">Retrait</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="Total.jsp">Recette</a>
                        </li>
                    </ul>

                    <form class="d-flex" role="search">
                        <input class="form-control me-2 disabled" type="search" placeholder="Search" aria-label="Search" disabled>
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
        <br>
        <div class="row position-relative">
            <div class="card shadow-sm p-3 mb-5 bg-body-tertiary rounded" style="margin-top: 70px; width: 50rem; position: absolute; top: 50%; left: 50%;transform: translateX(-50%);">
                <img src="somme.jpg" class="card-img-top img-fluid rounded" alt="..." style="height: 20rem;">
                <div class="card-body">
                    <h5 class="card-title">Recette total</h5>
                    <p class="card-text"> Recette total de l’opérateur (somme des frais d’envoi et retrait) : </p>
                    <input type="button" id="somme" name="somme" size="30px" class="btn btn-outline-primary btn-lg" />
                </div>
            </div>
        </div>

         <script src="component/jquery/jquery.js" type="text/javascript"></script>
         <script src="component/jquery-3.4.1.min.js" type="text/javascript"></script>
         <script src="component/jquery.validate.min.js" type="text/javascript"></script>
         <script src="component/jquery/jquery.dataTables.min.js" type="text/javascript"></script>
         <script src="component/sweetalert2.min.js" type="text/javascript"></script>
         <link href="component/bootstrap/css/sweetalert2.css" rel="stylesheet" type="text/css"/>
         
         <script>
    
        getSomme();
        function getSomme() {
            $.ajax({
                type: "GET",
                url: "Controller/Somme/Somme.jsp",
                dataType: "JSON",
                success: function(data) {
                    var valeur = data[0].somme+" Ariary";
                    //alert(data[0].somme);
                    // Vider la liste déroulante avant de la remplir
                    $('#somme').val(valeur);
                },
                     error: function () {
                         alert("Erreur lors de la récupération des clients.");
                     }
                 });
             }
         </script>
    </body>
</html>
