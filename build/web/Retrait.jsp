<%-- 
    Document   : Retrait
    Created on : 27 juin 2024, 15:46:01
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
                            <a class="nav-link active" href="Retrait.jsp">Retrait</a>
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
        <br>
        <div class="row">
            <div class="col-sm-3">
                <div class="container">
                    <form action="action" name="frmStudent" id="frmStudent">
                        <div class="form-group form-floating mb-3">
                            <input type="text" id="idrecep" name="idrecep" placeholder="idrecep" size="30px" class="form-control" required />
                            <label for="idrecep">idrecep </label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                           <!-- <label>Num client :</label>
                            <input type="text" id="numtel" name="numtel" placeholder="numtel" size="30px" class="form-control" required />-->
                           <select name="numtel" id="numtel" class="form-select">
                               
                           </select>
                           <label for="sexe">Num client...</label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                            <input type="number" id="montant" name="montant" placeholder="montant" size="30px" class="form-control" required />
                            <label for='montant'>montant </label>
                        </div>
                        <br>
                        <div class="form-group form-floating mb-3">
                            <input type="datetime-local" id="daterecep" name="daterecep" placeholder="Email" size="30px" class="form-control" required />
                            <label for='daterecep'>Date </label>
                        </div>
                        <br>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                            <button class="btn btn-primary col-6" id="save" type="button" onclick="addClient()">Add</button>
                            <button class="btn btn-danger col-6" id="reset" type="button" onclick="reSet()">Reset</button>
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
            var stockid = null;
            getall();
            getClient();
            
            function reSet() {
                $('#idrecep').val("");
                $('#numtel').val("");
                $('#montant').val("");
                $('#daterecep').val("");
            }
            
            function addClient () {
                if ($("#frmStudent").valid()){
                var data = "";
                var url = "";
                var method;
                if (isNew === true) {
                    url = "Controller/Retrait/Insert.jsp";
                    data = $("#frmStudent").serialize();
                    method = 'POST';
                }
                else {
                    url = "Controller/Retrait/Update.jsp";
                    data = $("#frmStudent").serialize() + "&id=" +stockid;
                    method = 'POST';
                    }
                $.ajax({
                    type: method,
                    url: url,
                    dataType: 'JSON',
                    data: data,
                    
                    success: function (data) {
                        getall();
                        if (isNew === true) {
                           if (data[0].status == "true") {
                            reSet();
                           Swal.fire({
                           toast: true,
                           position: 'top-end',
                           icon: 'success',
                           title: "Retrait effectuer avec succes.",
                           showConfirmButton: false,
                           timerProgressBar: true,
                           timer: 2300
                         });
                         
                         }else {
                           Swal.fire({
                           icon: 'error',
                           title: "Erreur",
                           text: data[0].message+" pour effectuer cette operation.",
                           showConfirmButton: true
                         });
                            }
                           
                        } else {
                           if (data[0].status == "true") {
                           reSet();
                           Swal.fire({
                           toast: true,
                           position: 'top-end',
                           icon: 'success',
                           title: "Retrait effectuer avec succes.",
                           showConfirmButton: false,
                           timerProgressBar: true,
                           timer: 2300
                         });
                            }else {
                           Swal.fire({
                           icon: 'error',
                           title: "Erreur",
                           text: data[0].message+" pour effectuer cette operation.",
                           showConfirmButton: true
                         });
                            }
                        }
                    }
                });
                }
            }
            
                        
            function getall () {
                $('#tbl-student').dataTable().fnDestroy();
                $.ajax({
                    url: "Controller/Retrait/Select.jsp",
                    type: "GET",
                    dataType: "JSON",
                    
                    success:function(data) {
                        $('#tbl-student').dataTable({
                            "aaData":data,
                            "scrollX":true,
                            "aoColumns":
                               [
                           {
                               "sTitle": "idrecep", "mData":"idrecep"
                           },
                           {
                               "sTitle":"Telephone","mData":"numRec"
                           },
                                                      {
                               "sTitle":"Nom","mData":"nomRec"
                           },
                                                      {
                               "sTitle":"montant(Ar)","mData":"montant"
                           },
                                                      {
                               "sTitle":"date","mData":"daterecep"
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
                           }
                               ]
                        });
                    }
                });
            }
                        
            function get_details (id) {
                $.ajax({
                    type: "POST",
                    url: "Controller/Retrait/Edit.jsp",
                    data: {"id": id},
                    
                    success: function (data) {
                        isNew = false;
                        var obj = JSON.parse(data);
                        stockid = obj[0].id;
                        $('#idrecep').val(obj[0].idrecep);
                        $('#numtel').val(obj[0].numtel);
                        $('#montant').val(obj[0].montant);
                        $('#daterecep').val(obj[0].daterecep);

                    }
                    
                });
            }
            
            function get_delete (id) {
                $.ajax({
                    type: 'POST',
                    url: 'Controller/Retrait/Delete.jsp',
                    dataType: 'JSON',
                    data: {"id": id},
                    
                    success: function (data) {
                         Swal.fire({
                           toast: true,
                           position: 'top-end',
                           icon: 'success',
                           title: "Retrait supprimer dans la BD.",
                           showConfirmButton: false,
                           timerProgressBar: true,
                           timer: 2300
                         });
                        getall();
                    }
                });
            }
            
            function deletes(id) {   
              swal.fire({
              title: 'Voulez vous vraiment ?',
              text: "le supprimer",
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
            
            function getClient() {
            $.ajax({
                type: "GET",
                url: "Controller/Client/Select.jsp",
                success: function(data) {
                    var clientsList = JSON.parse(data);
                    var select = $('#numtel');
                    select.empty(); // Vider la liste déroulante avant de la remplir
                    for (var i = 0; i < clientsList.length; i++) {
                        var client = clientsList[i];
                        select.append('<option value="' + client.id + '">' + client.numtel + '</option>');
                    }
                },
                error: function() {
                    alert("Erreur lors de la récupération des clients.");
                }
            });
        }
    </script>
    </body>
</html>
