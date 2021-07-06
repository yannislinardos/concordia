<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="beans.*" import="java.util.*" import="util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Admin Login</title>

    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>

<div id="bar"></div>
<div id="mod"></div>

<div class="container content">

    <%for (int i = 0; i < 5; i++) {%>
    <br><%}%>

    <div class="modal-body">
        <div class="row">
            <div class="col-xs-6 col-xs-offset-3">


                <form id="login-form" action="AdminLogin" method="post" role="form"
                      style="display: block;">
                    <div class="form-group" style="font-family: Verdana">
                        <input type="text" name="admin" id="admin"
                               class="form-control" placeholder="Username" value="">
                        <!--</div>-->
                        <!--<div class="form-group">-->
                        <input type="password" name="password" id="login_password"

                               class="form-control" placeholder="Password">
                        <!--</div>-->
                        <!--<div class="form-group">-->
                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-3" style="font-family: Concordia_Font">
                                </br>
                                <input type="submit" name="login-submit"
                                       id="login-submit"
                                       class="form-control btn btn-login"
                                       value="Log In">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%for (int i = 0; i < 10; i++) {%>
    <br><%}%>

</div>


<div id="foo"></div>

<!--SCRIPTS-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>