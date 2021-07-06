<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="beans.*" import="java.util.*" import="util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>

<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
}
th {
    text-align: center;
}
</style>

    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <title>Event searcher</title>

    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
	
	    <!-- Scripts -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

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
<body>

<div id="bar"></div>
<div id="mod"></div>

<div class="container content">
    <div>
        <div class="searchbar">
            <h1>SEARCH</h1>
            <form class="form-inline" action="AdminSearchEvent.jsp">
                <div class="form-group form-control-inline">
                    <input type="text" class="form-control" placeholder="Search Event" name="searchWords" id="searchWords" size="20" value='${param.searchWords}'/>
                </div>
                <div class="input-group form-control-inline">
                    <input class="form-control" placeholder="startdate" id="datepicker" name="from" value='${param.from}'/>
                    <span class="input-group-addon" id="sizing-addon1">to</span>
                    <input class="form-control" placeholder="enddate" id="datepicker2" name="until" value='${param.until}'/>
                </div>
                <div class="form-group form-control-inline">
                    <label>Show results</label>
                    <select class="form-control" name="limit">
                        <option value="no_limit" selected>no limit</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                </div>
                <div class="form-group form-control-inline">
                    <label>Type</label>
                    <select class="form-control" name="type">
                        <option value="ALL" selected>ALL</option>
                        <option value="THEATER">THEATER</option>
                        <option value="FILM">FILM</option>
                        <option value="EXPOSITION">EXPOSITION</option>
                    </select>
                </div>
                <input type="submit" class="btn" value="Search"/>
            </form>
        </div>
        <br>

        <%

            String limit = request.getParameter("limit");

            int lim = 1000000000;

            if (!"no_limit".equals(limit) && limit != null) {
                lim = Integer.valueOf(limit);
            }

            String type = request.getParameter("type");

            String searchWords = request.getParameter("searchWords");

            String from = request.getParameter("from");

            String until = request.getParameter("until");

            List<Event> events = new ArrayList<Event>();

            if((searchWords == null || "".equals(searchWords)) && (from == null
                    || "".equals(from)) && (until == null || "".equals(until))) {
                events = Queries.getByType(type);
            } else {
                events = Queries.searchEventsDate(searchWords, from, until, type);

            }


            if (events != null && events.size() > 0) {
        %>

        <%
            for (int i = 0; i < events.size() && i < lim; i++) {

                if (events.get(i).getName() != null) {
        %>

        <div class="timetable-body">
            <div class="timetable-table">
                <table>
                    <tr class="jumbotron">
                        <td class="unit-icon"><a href=<%= "\"AdminPage.jsp?Id=" + events.get(i).getId() + "\"" %>><img class="unit-image" src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="auto" height="180px"/></a></td>
                        <td style="padding: 10px" class="unit-description">
                            <h2> <a style="color: #932BA6" href=<%= "\"AdminPage.jsp?Id=" + events.get(i).getId() + "\"" %>><%=events.get(i).getName() %> </a></h2>
                            <h4><%=events.get(i).getDate() %></h4>
                            <p> <%=events.get(i).getDescription() %></p>
                        </td>
                    </tr>
                    <tr class="table-spacer">
                    </tr>

                </table>
                <br>
            </div>
        </div>

        <%
                }
            }

        } else {%>

        <center>
            <h2>NO RESULTS FOUND</h2>
        </center>

        <%}%>


    </div>
</div>

<div id="foo"></div>

<!--SCRIPTS-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>