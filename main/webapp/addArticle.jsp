<%@ page import="java.util.*" %>
<%@ page import="servlets.*" import="beans.*" import="AdminDashBoard.*"
         import="util.*" import="AdminDashBoard.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Create Articles</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <!--Source for jQuery script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

    <script>
        $(document).ready(function () {
            $("#datepicker").datepicker();
        });
    </script>

</head>

<!-- ----------REDIRECT if not logged in -------------------->

<%
    String admin = AdminLogin.getToken(request.getCookies());

    if (admin == null) {
        response.sendRedirect("AdminLogin.jsp");
    }
%>

<!-- -------------------------------------- -->

<body>

<div id="bar"></div>
<div id="mod"></div>

<%
    boolean added = false;

    if (request.getAttribute("success") != null) {
        added = (boolean) request.getAttribute("success");
    }

    if (added) {

%>
<div class="container content">
    <center><h1>ARTICLE ADDED SUCCESSFULLY</h1>
        <br/>
        <a href="addArticle.jsp">ADD NEW</a></center>
</div>
<% } else { %>


<div class="container content">
    <div class="row">

        <!------------------------LOGOUT------------------------------>

        <form action="./AdminLogout" class="inline">
            <button class="float-left submit-button" name="delete">Logout</button>
        </form>

        <!-- ----------------------------------------------------- -->

        <form action="addArticle" method="post">

            <div class="col-md-12 col-md-offset-0">
                <div id="createEvent">

                    <fieldset class="form-group row">
                        <legend class="col-form-legend col-sm-2">Create a new article</legend>

                    </fieldset>


                    <div class="form-group row">
                        <div class="col-md-2 col-md-offset-0">
                            Title
                        </div>
                        <label for="EventName" class="col-sm-0 col-form-label"></label>
                        <div class="col-sm-10" style="font-family: Verdana">
                            <input type="text" class="form-control input-lg" id="EventName" name="title"
                                   value='${param.title}'>
                        </div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-2 col-md-offset-0">
                            Date
                        </div>
                        <div class="input-group form-control-inline">
                            <input class="form-control" id="datepicker" name="date" value='${param.date}'/>

                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-2 col-md-offset-0">
                            Article body
                        </div>
                        <label for="omschrijving"></label>
                        <div class="col-md-10" style="font-family: Verdana">
                            <textarea class="form-control" rows="7" name="body" id="omschrijving"></textarea>
                        </div>
                    </div>

                    <%--<div class="col-md-2">--%>
                    <%--upload an image--%>
                    <%--</div>--%>
                    <%--<div class="col-md-10">--%>
                    <%--<!-- image-preview-filename input [CUT FROM HERE]-->--%>
                    <%--<div id="imageInput" class="input-group image-preview">--%>
                    <%--<input type="text" class="form-control image-preview-filename" disabled="disabled">--%>
                    <%--<!-- don't give a name === doesn't send on POST/GET -->--%>
                    <%--<span class="input-group-btn">--%>
                    <%--<!-- image-preview-clear button -->--%>
                    <%--<button type="button" class="btn btn-default image-preview-clear" style="display:none;">--%>
                    <%--<span class="glyphicon glyphicon-remove"></span> Clear--%>
                    <%--</button>--%>
                    <%--<!-- image-preview-input -->--%>
                    <%--<div class="btn btn-default image-preview-input">--%>
                    <%--<span class="glyphicon glyphicon-folder-open"></span>--%>
                    <%--<span class="image-preview-input-title">Browse</span>--%>
                    <%--<input type="file" accept="image/png, image/jpeg, image/gif" name="input-file-preview"/>--%>
                    <%--<!-- rename it -->--%>
                    <%--</div>--%>
                    <%--</span>--%>
                    <%--</div><!-- /input-group image-preview [TO HERE]-->--%>
                    <%--</div>--%>
                    <input type="submit" name="createArticle" class="btn btn-default">
                </div>
            </div>
        </form>
    </div>
</div>
<%} %>
<!--Page header title-->

<div id="foo"></div>
<!--SCRIPTS-->
<!-- Use downloaded version of Bootstrap -->
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>