<%@ page import="beans.Article" %>
<%@ page import="util.Queries" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">

    <%String id = request.getParameter("Id");
    Article article = Queries.getArticle(id);%>
    <title><%=article.getTitle()%></title>

    <!--Styles-->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    <!--Source for jQuery script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>


</head>

<body>
<div id="bar"></div>
<div id="mod"></div>



<!--Page header title-->
<div class="container content">
    <div class="row">
        <div class="col-xs-12">
            <div class="text-center">
                <h2><%=article.getTitle() %></h2>
            </div>
        </div>

        <div class="col-lg-4 col-md-6">
            <img src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="180px" height="auto" class="img-rounded center-block">
        </div>

        <%--<div class="col-lg-8 col-md-6">--%>

        <p style="font-family: Arial"><%=article.getBody() %>
        </p>
        <div class="col-xs-12">
            <blockquote style="color: #932BA6"><%=article.getDate() %>
            </blockquote>
            <hr>
        </div>


    </div>
</div>

<div id="foo"></div>
<!--SCRIPTS-->
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>