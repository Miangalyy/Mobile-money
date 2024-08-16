<%-- 
    Document   : index
    Created on : 25 juin 2024, 09:19:07
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
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="">Client</a>
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
                            <a class="nav-link" href="Total.jsp">Recette</a>
                        </li>
                    </ul>
                   
                    <form class="d-flex" role="search">
                        <input class="form-control me-2 disabled" type="search" placeholder="Search" aria-label="Search" disabled>
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
<!-- Button trigger modal
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
  Launch demo modal
</button> -->

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Saissez la date </h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>MonthDate :</label>
                    <input type="month" id="month" name="month" placeholder="Solde" size="30px" class="form-control" required />
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                <button type="button" class="btn btn-primary" onclick="imprimer()">Imprimer</button>
            </div>
        </div>
    </div>
</div>
        <br>
        <div class="row">
            <div class="col-sm-3">
                <div class="container">
                    <form action="action" name="frmStudent" id="frmStudent">
                        <div class="form-group form-floating mb-3">
                            <input type="text" id="numtel" name="numtel" placeholder="Telephone" class="form-control mb-3" required />
                            <label for="numtel1">Numero telephone :</label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                            <input type="text" id="nom" name="nom" placeholder="Nom" size="30px" class="form-control" required />
                            <label for="nom">Nom </label>
                        </div>
                        <br>
                        <div class="form-group form-floating">
                            <select class="form-select" id="sexe" name="sexe">
                                <option value="masculin">Homme</option>
                                <option value="feminin">Femme</option>
                            </select>
                            <label for="sexe">Sexe...</label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                            <input type="number" id="age" name="age" placeholder="Age" size="30px" class="form-control" required />
                            <label for="age">Age </label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                            <input type="email" id="email" name="email" placeholder="Email" size="30px" class="form-control" required />
                            <label for="email">Email </label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                            <input type="number" id="solde" name="solde" placeholder="Solde" size="30px" class="form-control" required />
                            <label for="solde">Solde </label>
                        </div>
                        <br>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <button class="btn btn-primary col-6" id="save" type="button" onclick="addClient()">Enregistrer</button>
                            <button class="btn btn-danger col-6" id="reset" type="button" onclick="reSet()">Reinitialiser</button>
                        </div>
                    </form>
                </div>
            </div>
                <div class="col-sm-9">
                    <div class="card text-center">
                        <div class="card-header">
                            Affichage des clients
                        </div>
                        <div class="card-body">
                            <div class="panel-body">
                                <table class="table table-striped" id="tbl-student" cellspacing="0" cellpadding="0">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th></th> 
                                            <th></th>
                                            <th></th> 
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                </table>                   
                            </div>
                        </div>
                        <div class="card-footer text-body-secondary">
                            2 days ago
                        </div>
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
            var isNew = true;
            var texte = null;
            var modalId = null;
            var stockid = null;
            getall();
            
            function reSet() {
                $('#nom').val("");
                $('#numtel').val("");
                $('#sexe').val("");
                $('#email').val("");
                $('#solde').val("");
                $('#age').val("");      
            }
            
            function addClient () {
                if ($("#frmStudent").valid()){
                var data = "";
                var url = "";
                var method;
                if (isNew === true) {
                    url = "Controller/Client/Insert.jsp";
                    data = $("#frmStudent").serialize();
                    method = 'POST';
                }
                else {
                    url = "Controller/Client/Update.jsp";
                    data = $("#frmStudent").serialize() + "&id=" +stockid;
                    method = 'POST';
                    }
                $.ajax({
                    type: method,
                    url: url,
                    dataType: 'JSON',
                    data: data,
                    
                    success: function (data) {
                        reSet();
                        getall();
                        if (isNew === true) {
                           Swal.fire({
                           toast: true,
                           position: 'top-end',
                           icon: 'success',
                           title: "Client enregistrer dans la BD :)",
                           showConfirmButton: false,
                           timerProgressBar: true,
                           timer: 2300
                         });
                        } else {
                           Swal.fire({
                           toast: true,
                           position: 'top-end',
                           icon: 'success',
                           title: "Client enregistrer dans la BD :)",
                           showConfirmButton: false,
                           timerProgressBar: true,
                           timer: 2300
                         });
                        }
                    }
                });
                }
            }
            
                        
            function getall () {
                $('#tbl-student').dataTable().fnDestroy();
                $.ajax({
                    url: "Controller/Client/Select.jsp",
                    type: "GET",
                    dataType: "JSON",
                    
                    success:function(data) {
                        $('#tbl-student').dataTable({
                            "aaData":data,
                            "scrollX":true,
                            "aoColumns":
                               [
                           {
                               "sTitle": "Telephone", "mData":"numtel"
                           },
                           {
                               "sTitle":"Nom","mData":"nom"
                           },
                                                      {
                               "sTitle":"Sexe","mData":"sexe"
                           },
                                                      {
                               "sTitle":"Age","mData":"age"
                           },
                                                      {
                               "sTitle":"Email","mData":"email"
                           },
                                                      {
                               "sTitle":"Solde(Ar)","mData":"solde"
                           },
                           {
                               "sTitle":"Edit",
                               "mData":"id",
                               "render": function(mData, type, row, meta) {
                                   return '<button class="btn btn-outline-primary" type="button" onclick="get_details('+mData+')">Edit</button>';
                                   
                               }
                           },
                           {
                               "sTitle":"Delete",
                               "mData":"id",
                               "render": function(mData, type, row, meta) {
                                   return '<button class="btn btn-outline-danger" type="button" onclick="deletes('+mData+')">Delete</button>';
                                   
                               }
                           },
                                               {
                               "sTitle":"Imprimer",
                               "mData":"id",
                               "render": function(mData, type, row, meta) {
                                   return '<button class="btn btn-outline-success" type="button" onclick="afficherModal('+mData+')" data-bs-toggle="modal" data-bs-target="#exampleModal">Imprimer</button>';
                                   
                               }
                           }
                               ]
                        });
                    }
                });
            }
                        
            function get_details (id) {
                $.ajax({
                    type: "POST",
                    url: "Controller/Client/Edit.jsp",
                    data: {"id": id},
                    
                    success: function (data) {
                        isNew = false;
                        var obj = JSON.parse(data);
                        stockid = obj[0].id;
                        $('#numtel').val(obj[0].numtel);
                        $('#nom').val(obj[0].nom);
                        $('#sexe').val(obj[0].sexe);
                        $('#email').val(obj[0].email);
                        $('#solde').val(obj[0].solde);
                        $('#age').val(obj[0].age);
                    }
                    
                });
            }
            
            function deletes(id) {   
              swal.fire({
              title: 'Voulez vous vraiment ?',
              text: "supprimer",
              icon: 'warning',
              showCancelButton: true,

              confirmButtonText: 'Oui, continuer.',
              cancelButtonText: 'Non, fermer.'
             }).then((result) => {
               if (result.isConfirmed) {
                 get_delete(id);
                } else if (result.isDenied) {
                 swal.close();
            }
              });
            }
            
            function get_delete (id) {
                $.ajax({
                    type: 'POST',
                    url: 'Controller/Client/Delete.jsp',
                    dataType: 'JSON',
                    data: {"id": id},
                    
                    success: function (data) {
                        Swal.fire({
                           toast: true,    
                           position: 'top-end',
                           icon: 'success',
                           title: "Client effacer dans la BD :)",
                           showConfirmButton: false,
                           timerProgressBar: true,
                           timer: 2300
                         });
                        getall();
                    }
                });
            }
            function afficherModal(id) {
                modalId = id;
            }
            function imprimer() {
                var SelectedValue = document.getElementById('month').value;
                var year = SelectedValue.substring(0, 4);
                var month = SelectedValue.substring(5, 7);
               //alert ("Id : "+modalId+" et years : "+year+" et mois : "+month+".");
               
                $.ajax({
                    type: 'POST',
                    url: 'Controller/Client/Imprimer.jsp',
                    dataType: 'JSON',
                    data: {"id": modalId,
                           "year": year,
                           "month": month
                    },
                    
                    success: function (data) {
                        console.log("Stock deleted !!!!");
                    }
                });
               
            }
    </script>
    </body>
</html>
