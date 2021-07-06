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
    <title>PasswordReset</title>

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


<div class="container content">
    <br>

    <%
        String question = (String) request.getAttribute("secQuestion");
        boolean notCorrect = false;
        if (request.getAttribute("notCorrect") != null) {
            notCorrect = (boolean) request.getAttribute("notCorrect");
        }
        boolean passwordReset = false;
        if (request.getAttribute("passwordReset") != null) {
            passwordReset = (boolean) request.getAttribute("passwordReset");
        }
        if (notCorrect) { %>

    <center><h1>Your response to the security question is not correct</h1></center>

    <%} else if (passwordReset) {%>

    <center><h1>Your password has been reset</h1></center>

    <% } else if (question == null) { %>

    <form id="PasswordReset" action="GetSecurityQuestion" method="post">

        <%--<div class="col-md-12">--%>
        <div class="form-group">
            <label class="control-label" for="Email"><h3>Please type your Email here</h3></label>
            <h1 style="font-family:verdana;">
                <input type="email" class="form-control input-lg" id="Email"
                       placeholder="Email@Email.com" name="email">
            </h1>
        </div>
        <input type="submit" name="PasswordReset" class="btn btn-default">
    </form>


    <%
    } else {
        request.setAttribute("email", request.getParameter("email"));

    %>

    <form id="Reset" action="./ResetPassword" method="post">

        <fieldset disabled>
            <div class="col-md-2">
                <h3 style="font-family:verdana;">
                    Email:
                </h3>
            </div>
            <div class="col-md-10">
                <h3 style="font-family:verdana;">
                    <%=request.getParameter("email") %>
                </h3>
                <%--<label class="control-label" for="Email"></label>--%>
                <%--<h1 style="font-family:verdana;">--%>
                <%--<input type="email" class="form-control input-lg" id="Email"--%>
                <%--placeholder=<%=request.getParameter("email") %>>--%>
                <%--</h1>--%>
            </div>


            <div class="col-md-2">
                <!-- ------------------------------------------------------------------------------------------- -->
                <h3 style="font-family:verdana;">
                    Question:
                </h3>
                <!-- ----------------------------------------------------------------------------------- -->
            </div>

            <div class="col-md-10">
                <!-- ------------------------------------------------------------------------------------------- -->
                <h3 style="font-family:verdana;">
                    <%=question%>
                </h3>
                <!-- ----------------------------------------------------------------------------------- -->
            </div>
        </fieldset>

        <input type="hidden" name="email" value=<%=request.getParameter("email") %>>

        <input style="font-family: Verdana" type="hidden" name="secQuestion" value=<%=question %>>


        <div class="form-group">
            <label class="control-label" for="Answer"></label>
            <input style="font-family: Verdana" type="text" class="form-control input-lg" id="Answer"
                   placeholder="Answer the security question" name="answer">
        </div>

        <div class="form-group">
            <label class="control-label" for="Password"></label>
            <input style="font-family: Verdana" type="password" class="form-control input-lg" id="Password"
                   placeholder="Password" name="newPass">
        </div>

        <div class="form-group">
            <label class="control-label" for="rePassword"></label>
            <input style="font-family: Verdana" type="password" class="form-control input-lg" id="rePassword"
                   placeholder="Confirm Password" name="reNewPass">
        </div>

        <input type="submit" name="Reset" class="btn btn-default">
    </form>


    <%} %>

    <br>
</div>

<div id="foo"></div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>


</body>
</html>


