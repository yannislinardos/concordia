<%--
  Created by IntelliJ IDEA.
  User: Thomas Stouten
  Date: 24/06/2017
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="servlets.*"%>
<!DOCTYPE html>
<html lang="en">
<!-- ----------REDIRECT if not logged in -------------------->

<%
    String admin = AdminLogin.getToken(request.getCookies());

    if (admin == null) {
        response.sendRedirect("AdminLogin.jsp");
    }
%>

<!-- -------------------------------------- -->
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Admin page</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/upload_file.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">

    <!--Source for jQuery script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/scripts.js"></script>


    <!-- Use downloaded version of Bootstrap -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/upload_file.js"></script>


</head>

<body>
<div id="bar"></div>
<div id="mod"></div>

<div class="container content">
    <div class="row">
        <p><a href="AdminPage.jsp">back to the admin page</a></p>
    </div>
    <div class="row">

        <div class="col-md-12 col-md-offset-0">
            <div id="createEvent" action="CreateEvent" method="post">


                <fieldset class="form-group row">

                    <div class="col-md-2 col-md-offset-0">
                        Event ID
                    </div>
                    <label for="EventID" class="col-sm-0 col-form-label"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-lg" id="EventID"
                               placeholder="123456">
                    </div>

                    <legend class="col-form-legend col-sm-2">Type of event</legend>
                    <div class="col-sm-10">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1"
                                       value="option1">
                                Theatre
                            </label>
                        </div>
                        <div class="form-check">
                            <label class="form-check-label">
                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2"
                                       value="option2">
                                Film
                            </label>
                        </div>
                        <div class="form-check disabled">
                            <label class="form-check-label">
                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios3"
                                       value="option3">
                                Exposition
                            </label>
                        </div>
                    </div>
                </fieldset>
                <div class="form-group row">
                    <div class="col-md-2 col-md-offset-0">
                        Name of event
                    </div>
                    <label for="EventName" class="col-sm-0 col-form-label"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-lg" id="EventName"
                               placeholder="The sense of an ending">
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-md-2 col-md-offset-0">
                        Price
                    </div>
                    <label for="price" class="col-sm-2 col-form-label"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-lg" id="price" placeholder="9.00">
                    </div>
                </div>


                <div class="form-group row">
                    <div class="col-md-2 col-md-offset-0">
                        Locatiion
                    </div>
                    <label for="locatie" class="col-sm-2 col-form-label"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-lg" id="locatie" placeholder="Filmzaal 2">
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-md-2 col-md-offset-0">
                        Capacity
                    </div>
                    <label for="capacity" class="col-sm-2 col-form-label"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-lg" id="capacity" placeholder="109">
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-md-2 col-md-offset-0">
                        Author/Artist/Creator
                    </div>
                    <label for="Creator" class="col-sm-2 col-form-label"></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-lg" id="Creator" placeholder="Ritesh Batra">
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-md-2 col-md-offset-0">
                        Description
                    </div>
                    <label for="omschrijving"></label>
                    <div class="col-md-10">
                        <textarea class="form-control" rows="7" placeholder="Verfilming van het met de Booker Prize bekroonde boek (Alsof het Voorbij Is) van Julian Barnes. Met Charlotte Rampling en Jim Broadbent.

Jim Broadbent speelt Tony Webster, een man die een teruggetrokken en stil leven leidt. Wanneer zijn verleden hem plotseling in lijkt te halen, is het gedaan met de rust. Hij wordt gedwongen de confrontatie aan te gaan en de waarheid over zijn eerste liefde (Charlotte Rampling) en de ondraaglijke consequenties van de beslissingen die hij lang geleden maakte onder ogen te zien.

The Sense of an Ending is gebaseerd op het gelijknamige, met de Booker Prize bekroonde, boek van Julian Barnes. Het is de tweede speelfilm van de maker van de veelgeroemde Indiase film The Lunchbox."
                                  id="omschrijving"></textarea>
                    </div>
                </div>

                <div class="col-md-2 col-md-offset-0">
                    tags
                </div>
                <div class="col-md-10 col-md-offset-0">
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox1" value="option1"> 1
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox2" value="option2"> 2
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox3" value="option3"> 3
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox4" value="option1"> 4
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox5" value="option2"> 5
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox6" value="option3"> 6
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox7" value="option1"> 7
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox8" value="option2"> 8
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="inlineCheckbox9" value="option3"> 9
                    </label>
                </div>

                <div class="col-md-2">
                    upload an image
                </div>
                <div class="col-md-10">
                    <!-- image-preview-filename input [CUT FROM HERE]-->
                    <div id="imageInput" class="input-group image-preview">
                        <input type="text" class="form-control image-preview-filename" disabled="disabled"
                               placeholder="Upload een afbeelding">
                        <!-- don't give a name === doesn't send on POST/GET -->
                        <span class="input-group-btn">
                    <!-- image-preview-clear button -->
                    <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                        <span class="glyphicon glyphicon-remove"></span> Clear
                    </button>
                            <!-- image-preview-input -->
                    <div class="btn btn-default image-preview-input">
                        <span class="glyphicon glyphicon-folder-open"></span>
                        <span class="image-preview-input-title">Browse</span>
                        <input type="file" accept="image/png, image/jpeg, image/gif" name="input-file-preview"/>
                        <!-- rename it -->
                    </div>
                </span>
                    </div><!-- /input-group image-preview [TO HERE]-->
                </div>
                <input type="submit" name="createEvent" class="btn btn-default">
            </div>
        </div>
    </div>
</div>
<br>
<!--Page header title-->

<div id="foo"></div>
<!--SCRIPTS-->

</body>
</html>