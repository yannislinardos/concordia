<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="util.*" import="beans.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Tickets Bought</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    <!--Source for jQuery script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>


</head>

<body>

<div id="mod"></div>
<div id="bar"></div>

<%
boolean success = (boolean) request.getAttribute("success");%>

                <div class="container content">
                
                    <div class="col-md-8">
  
  <% if(success) { %>
  <center><h1>Congratulations, you have booked your ticket(s)!</h1></center>
  
  <% } else { %>
  
    <center><h1>Sorry! There are not so many seats left</h1></center>
  
  <%} %>
                
    </div>
         
                
        </div>        
                          
                

<div id="foo"></div>
<!--SCRIPTS-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>