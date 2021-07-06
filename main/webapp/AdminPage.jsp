<%@ page import="java.util.*" %>
<%@ page import="util.*" import="beans.*" import="AdminDashBoard.*" import="java.io.*" import="member.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="servlets.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <title>Admin page</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    <!--Source for jQuery script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <!-- Use downloaded version of Bootstrap -->
    <script src="js/addfield.js"></script>
    <script>
        $(document).ready(function () {
            $("#datepicker").datepicker();
        });
    </script>
    <script>
        $(document).ready(function () {
            $("#datepicker2").datepicker()
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

<div class="container content">
    <div class="row">

        <!------------------------LOGOUT------------------------------>

        <form action="./AdminLogout" class="inline">
            <button class="float-left submit-button" name="Logout">Logout</button>
        </form>

        <!-- ----------------------------------------------------- -->

        <!------------------------Add Article------------------------------>

        <form action="addArticle.jsp" class="inline">
            <button class="float-left submit-button" name="addArticle">Add new Article</button>
        </form>

        <!-- ----------------------------------------------------- -->

        <form action="AdminSearchEvent.jsp" class="inline">
            <button class="float-left submit-button" name="search">Search
                Events to Edit
            </button>
        </form>

        <form action="AdminPage.jsp" class="inline">
            <button class="float-left submit-button" name="refresh">Refresh</button>
        </form>

        <div class="container">
            <div class="controls">
                <form action="AdminEventsServlet" method="post" name="myForm" onsubmit="return validateForm()"
                      enctype="multipart/form-data">
                    <script type="text/javascript">
                        function validateForm() {
                            var x = document.forms["myForm"]["name"].value;
                            // var x0 = document.forms["myForm"]["StartDate[]"].value;
                            //var x1 = document.forms["myForm"]["StartTime[]"].value;
                            //var x2 = document.forms["myForm"]["EndTime[]"].value;
                            if (x == "" || x0 == "" || x1 == "" || x2 == "") {
                                alert("Please fill all the required fields!");
                                return false;
                            }
                            if (x0 == "") {
                                alert("Date must be filled out");
                                return false;
                            }
                            if (x1 == "") {
                                alert("TimeStart must be filled out");
                                return false;
                            }
                            if (x2 == "") {
                                alert("TimeEnd must be filled out");
                                return false;
                            }
                        }

                    </script>
                    <%
                        String id = null;

                        if (request.getParameter("Id") != null) {
                            id = request.getParameter("Id");
                        } else {
                            id = "-1";
                        }

                        System.out.println("in page " + id);

                        String location = "";
                        int capacity = 0;
                        double ticketprice = 0;
                        String description = "";
                        String name = "";
                        String artist = "";
                        //TYPE
                        String type = "";
                        String theater = "";
                        String film = "";
                        String exposition = "";
                        //INSTANCES
                        int instances = 1;
                        //	int inst = 0;
                        //	String refresh = request.getParameter("refresh");
                        //	if(refresh.equals("refresh")){
                        //	inst = Integer.parseInt(request.getParameter("instances"));
                        //}

                        //DATE AND TIME
                        //String date = request.getParameter("date");
                        //String timestart = request.getParameter("timeend");

                        String action = "";
                        String crime = "";
                        String romance = "";
                        String thriller = "";
                        String adventure = "";
                        String historical = "";
                        String fantasy = "";
                        String satire = "";
                        String drama = "";
                        String comedy = "";
                        String horror = "";

                        //DATE AND TIME
                        List<Date> dates = new ArrayList<Date>();
                        HashMap<String, String> times = new HashMap<String, String>();


                        if (id != "-1") {

                            Event event = Queries.getEvent(id);
                            //ATTRIBUTES INITIALIZATION
                            location = event.getLocation();
                            capacity = Queries.getSeatsLeft(id);
                            ticketprice = event.getTicketPrice();
                            description = event.getDescription();
                            name = event.getName();
                            artist = event.getArtist();

                            //LIST WITH TAGS
                            List<String> allTags = AdminActions.getAlltagsNames();
                            List<String> tags = Queries.getEventTags(id);
                            System.out.println(tags);
                            System.out.println(allTags);

                            // DATES AND TIMES
                            dates = AdminActions.getallDatesAndTime(Integer.parseInt(id));
                            times = AdminActions.getAllTime(Integer.parseInt(id));

                            for (int i = 0; i < allTags.size(); i++) {
                                if (tags.contains(allTags.get(i))) {
                                    if (allTags.get(i).equals("Crime")) {
                                        crime = "checked";
                                    }
                                    if (allTags.get(i).equals("Romance")) {
                                        romance = "checked";
                                    }
                                    if (allTags.get(i).equals("Action")) {
                                        action = "checked";
                                    }
                                    if (allTags.get(i).equals("Thriller")) {
                                        thriller = "checked";
                                    }
                                    if (allTags.get(i).equals("Adventure")) {
                                        adventure = "checked";
                                    }
                                    if (allTags.get(i).equals("Satire")) {
                                        satire = "checked";
                                    }
                                    if (allTags.get(i).equals("Drama")) {
                                        drama = "checked";
                                    }
                                    if (allTags.get(i).equals("Comedy")) {
                                        comedy = "checked";
                                    }
                                    if (allTags.get(i).equals("Fantasy")) {
                                        fantasy = "checked";
                                    }
                                    if (allTags.get(i).equals("Satire")) {
                                        satire = "checked";
                                    }
                                    if (allTags.get(i).equals("Historical")) {
                                        historical = "checked";
                                    }
                                    if (allTags.get(i).equals("Horror")) {
                                        horror = "checked";
                                    }
                                }
                            }

                            //TYPE
                            type = event.getType();
                            System.out.println(type);
                            if (type.equals("THEATER")) {
                                theater = "checked";
                            }
                            if (type.equals("FILM")) {
                                film = "checked";
                            }
                            if (type.equals("EXPOSITION")) {
                                exposition = "checked";
                            }
                        }
                    %>
                    <!-- This is to assign the id of the Event if you wanna do UPDATES  , else is  (ID = 0) -->
                    <div>
                        <input type="hidden" id="idofEvent" name="Eventid" value="<%=id%>">
                    </div>

                    <div class="container">
                        <div class="row">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-6 col-md-offset-4">
                                        <input type="submit" name="change-submit" id="change-submit"
                                               tabindex="4" class="form-control btn btn-register"
                                               value="Submit Changes">
                                    </div>
                                    <div class="col-md-6 col-md-offset-4">
                                        <button class="form-control btn btn-register"
                                                name="delete" value="Delete">Delete
                                            Event
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-md-offset-0">
                                <div id="createEvent">

                                    <fieldset class="form-group row">
                                        <legend class="col-form-legend col-sm-2">Type of event</legend>
                                        <div class="col-sm-10">
                                            <div class="form-check">
                                                <label class="form-check-label">
                                                    <input class="form-check-input" type="radio" name="type"
                                                           value="THEATER"
                                                           id="gridRadios1"
                                                        <%=theater %>>
                                                    Theatre
                                                </label>
                                            </div>
                                            <div class="form-check">
                                                <label class="form-check-label">
                                                    <input class="form-check-input" type="radio" name="type"
                                                           value="FILM"
                                                           id="gridRadios2"
                                                        <%=film %>>
                                                    Film
                                                </label>
                                            </div>
                                            <div class="form-check disabled">
                                                <label class="form-check-label">
                                                    <input class="form-check-input" type="radio" name="type"
                                                           value="EXPOSITION"
                                                           id="gridRadios3"
                                                        <%=exposition %>>
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
                                        <div class="col-sm-10" style="font-family: Verdana">
                                            <input type="text" class="form-control input-lg" id="EventName"
                                                   name="name"
                                            <%--placeholder="The sense of an ending"--%>
                                                   value="<%=name%>">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-md-2 col-md-offset-0">
                                            Price
                                        </div>
                                        <label for="ticketprice" class="col-sm-2 col-form-label"></label>
                                        <div class="col-sm-10">
                                            <input type="number" class="form-control input-lg"
                                                   id="ticketprice" name="ticketprice"
                                            <%--placeholder="9.00"--%>
                                                   value="<%=ticketprice%>">
                                        </div>
                                    </div>


                                    <div class="form-group row">
                                        <div class="col-md-2 col-md-offset-0">
                                            Location
                                        </div>
                                        <label for="locatie" class="col-sm-2 col-form-label"></label>
                                        <div class="col-sm-10" style="font-family: Verdana">
                                            <input type="text" class="form-control input-lg" id="locatie"
                                                   name="location"
                                            <%--placeholder="Filmzaal 2"--%>
                                                   value="<%=location%>">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-md-2 col-md-offset-0">
                                            Capacity
                                        </div>
                                        <label for="capacity" class="col-sm-2 col-form-label"></label>
                                        <div class="col-sm-10">
                                            <input type="number" class="form-control input-lg" id="capacity"
                                                   name="capacity"
                                            <%--placeholder="109"--%>
                                                   value="<%=capacity%>">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-md-2 col-md-offset-0">
                                            Author/Artist/Creator
                                        </div>
                                        <label for="Creator" class="col-sm-2 col-form-label"></label>
                                        <div class="col-sm-10" style="font-family: Verdana">
                                            <input type="text" class="form-control input-lg" id="Creator"
                                                   name="artist"
                                            <%--placeholder="Ritesh Batra"--%>
                                                   value="<%=artist%>">
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <div class="col-md-2 col-md-offset-0">
                                            Description
                                        </div>
                                        <label for="omschrijving"></label>
                                        <div class="col-md-10" style="font-family: Verdana">
                        <textarea class="form-control" rows="7" name="description"
                        <%--placeholder="Verfilming van het met de Booker Prize bekroonde boek (Alsof het Voorbij Is) van Julian Barnes. Met Charlotte Rampling en Jim Broadbent.--%>

                        <%--Jim Broadbent speelt Tony Webster, een man die een teruggetrokken en stil leven leidt. Wanneer zijn verleden hem plotseling in lijkt te halen, is het gedaan met de rust. Hij wordt gedwongen de confrontatie aan te gaan en de waarheid over zijn eerste liefde (Charlotte Rampling) en de ondraaglijke consequenties van de beslissingen die hij lang geleden maakte onder ogen te zien.--%>
                        <%--The Sense of an Ending is gebaseerd op het gelijknamige, met de Booker Prize bekroonde, boek van Julian Barnes. Het is de tweede speelfilm van de maker van de veelgeroemde Indiase film The Lunchbox."--%>
                                  id="omschrijving"><%=description%></textarea>
                                        </div>
                                    </div>
                                    <div class="col-md-2 col-md-offset-0">tags</div>
                                    <div class="col-lg-10">


                                        <%
                                            AdminActions tags = new AdminActions();
                                            //HASHMAP FOR tagid AND tag
                                            HashMap<Integer, String> tagsMap = new HashMap<Integer, String>();
                                            //TAKE EMAIL FROM COOKIES
                                            if (tagsMap != null) {
                                                if (id != "-1") {
                                                    tagsMap = tags.searchEventTags(id);
                                                }
                                            }
                                            for (Integer key : tagsMap.keySet()) {
                                        %>
                                        <div class="chip">
                                            <%=tagsMap.get(key)%>
                                            <!--Remove this tag when clicked-->

                                        </div>

                                        <%
                                            }
                                        %>
                                        <br>
                                        <br>

                                        <p>ADD-Mutiple select list (hold shift to select more than one):</p>
                                        <select id="allTags" name="allTags[]" multiple class="form-control">

                                            <%
                                                List<String> allTags = AdminActions.getAlltagsNames();
                                                //REMOVE ALL TAGS THAT MEMBER HAS
                                                for (String value : tagsMap.values()) {
                                                    if (allTags.contains(value)) {
                                                        allTags.remove(value);
                                                    }
                                                }

                                                //NOTE THAT: in here you are going to use searchTags FROM CLASS searchadnsubmitTags
                                                for (int i = 0; i < allTags.size(); i++) {
                                            %>
                                            <option value="<%=allTags.get(i)%>"><%=allTags.get(i)%>
                                            </option>
                                            <%
                                                }
                                            %>
                                        </select>

                                        <p>REMOVE-Mutiple select list (hold shift to select more than
                                            one):</p>
                                        <select id="myTags" name="myTags[]" multiple class="form-control">

                                            <%
                                                //NOTE THAT: in here you are going to use searchTags FROM CLASS searchadnsubmitTags
                                                for (Integer key : tagsMap.keySet()) {
                                            %>
                                            <option value="<%=tagsMap.get(key)%>"><%=tagsMap.get(key)%>
                                            </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>

                                    <%--<div class="col-md-12">--%>
                                    <%--<div class="form-group">--%>
                                    <%--<label class="control-label" for="imageInput">Upload an--%>
                                    <%--image</label>--%>
                                    <%--<!-- image-preview-filename input [CUT FROM HERE]-->--%>
                                    <%--<div id="imageInput" class="input-group image-preview">--%>
                                    <%--<input type="text"--%>
                                    <%--class="form-control image-preview-filename"--%>
                                    <%--disabled="disabled" placeholder="Upload an image">--%>
                                    <%--<!-- don't give a name === doesn't send on POST/GET -->--%>
                                    <%--<span class="input-group-btn"> <!-- image-preview-clear button -->--%>
                                    <%--<button type="button"--%>
                                    <%--class="btn btn-default image-preview-clear"--%>
                                    <%--style="display: none;">--%>
                                    <%--<span class="glyphicon glyphicon-remove"></span> Clear--%>
                                    <%--</button> <!-- image-preview-input -->--%>
                                    <%--<div class="btn btn-default image-preview-input">--%>
                                    <%--<span class="glyphicon glyphicon-folder-open"></span> <span--%>
                                    <%--class="image-preview-input-title">Browse</span> <input--%>
                                    <%--type="file" accept="image/png, image/jpeg, image/gif"--%>
                                    <%--name="images[]" multiple size="10"/>--%>
                                    <%--<!-- rename it -->--%>
                                    <%--</div>--%>
                                    <%--</span>--%>
                                    <%--</div>--%>
                                    <%--<!-- /input-group image-preview [TO HERE]-->--%>
                                    <%--</div>--%>
                                    <%--</div>--%>
                                    <div class="row">
                                        <div class="control-group" id="fields">
                                            <div class="entry input-group col-md-12">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label"
                                                               for="StartDate[]">Date</label>
                                                        <input type="date" class="form-control input-lg"
                                                               id="StartDate[]" name="StartDate[]">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label" for="StartTime[]">Start
                                                            Time</label>
                                                        <input type="time" class="form-control input-lg"
                                                               id="StartTime[]" name="StartTime[]"
                                                               placeholder="13:45:00">
                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="control-label" for="EndTime[]">End
                                                            Time</label>
                                                        <input type="time" class="form-control input-lg"
                                                               id="EndTime[]" name="EndTime[]"
                                                               placeholder="13:45:00">
                                                    </div>
                                                </div>
                                                <span class="input-group-btn">
			                                        <button class="btn btn-success btn-add" id="add" type="button">
							                                <span class="glyphicon glyphicon-plus"></span>
							                            </button>
							                        </span>
                                                <%
                                                    if (!dates.isEmpty()) {
                                                        for (int i = 0; i < dates.size(); i++) {
                                                %>
                                                <script type="text/javascript">
                                                    document.getElementById("add").click();
                                                </script>
                                                <%
                                                        }
                                                    }
                                                %>
                                                <input type="text" value="<%=dates.size()%>">
                                            </div>
                                        </div>
                                    </div>
                                    <!-- --------IMAGE---- ---------------------->
                                    <% List<String> images = null;
                                        if (id != "-1") {
                                            images = AdminActions.retrieveImage(id);
                                        }
                                        if (images != null) {
                                            for (int i = 0; i < images.size(); i++) { %>
                                    <img src="<%=images.get(i)%>" style="width:304px;height:228px;">
                                    <%
                                            }
                                        }
                                    %>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!--Page header title-->
<div id="foo"></div>
<!--SCRIPTS-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>