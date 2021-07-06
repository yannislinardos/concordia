<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" import="util.*" import="beans.*" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    <%
        String id = request.getParameter("Id");
        Event event = Queries.getEvent(Integer.parseInt(id));
        String date = Queries.getDateRange(id);
        String description = event.getDescription();
        String location = event.getLocation();
        String name = event.getName();
        float price = event.getTicketPrice();
        String type = event.getType();
        String artist = event.getArtist();

        if (artist == null) {
            artist = "";
        }

        List<String[]> instances = Queries.getEventDates(id);

    %>
    <title><%=name %>
    </title>

</head>

<body>
<div id="bar"></div>
<div id="mod"></div>

<div class="container content">
    <div class="row">
        <div class="col-md-6">
            <h1 class="text-center" style="color: <%if (type.equals("FILM")) {%>
                    #e87a28
                <% } else if (type.equals("THEATER")) {%>
                    #62D1B5
                <%} else if (type.equals("EXPOSITION")) {%>
                    #932BA6
                <%}%>
                    ">
                <%=name%>
            </h1>
            <hr>
            <h5 style="font-family: Verdana"><%=description%>
            </h5>
            <hr>

            <div class="col-xs-12">
                <div class="col-xs-4 col-xs-offset-2">
                    <h4>Artist:</h4>
                </div>
                <div class="col-xs-6">
                    <h4><%=artist%>
                    </h4>
                </div>
            </div>

            <div class="col-xs-12">
                <div class="col-xs-4 col-xs-offset-2">
                    <h4>Price:</h4>
                </div>
                <div class="col-xs-6">
                    <h4><%=price%>
                    </h4>
                </div>
            </div>

            <div class="col-xs-12">
                <div class="col-xs-4 col-xs-offset-2">
                    <h4>Location:</h4>
                </div>
                <div class="col-xs-6">
                    <h4><%=location%>
                    </h4>
                </div>
            </div>

            <div class="col-xs-12">
                <div class="col-xs-4 col-xs-offset-2">
                    <h4>Date:</h4>
                </div>
                <div class="col-xs-6">
                    <h4><%=date%>
                    </h4>
                </div>
            </div>


            <%--<div class="col-xs-4 col-xs-offset-2">--%>
            <%--<h4>Artist:--%>
            <%--</h4>--%>
            <%--<h4>Price:--%>
            <%--</h4>--%>
            <%--<h4>Location:--%>
            <%--</h4>--%>
            <%--<h4>Date:--%>
            <%--</h4>--%>
            <%--</div>--%>
            <%--<div class="col-xs-6">--%>
            <%--<h4><%=artist%>--%>
            <%--</h4>--%>
            <%--<h4><%=price%>--%>
            <%--</h4>--%>
            <%--<h4><%=location%>--%>
            <%--</h4>--%>
            <%--<h4><%=date%>--%>
            <%--</h4>--%>
            <%--</div>--%>
            <div class="col-xs-12">
                <div class="text-center">
                    <hr>
                    <%
                        List<String> tags = Queries.getEventTags(id);

                        if (tags.size() != 0) {

                            int i = 0;
                            while (i < tags.size()) {

                    %>
                    <div class="chip">
                        <%=tags.get(i)%>
                    </div>
                    <%
                                i++;
                            }
                        } %>
                </div>
            </div>


        </div>
        <div class="col-md-6">
            <br>
            <img src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="100%" height="auto">
            <%
                if (Queries.isUpcoming(id)) {
            %>
            <div class="text-center">
                <form action="BuyTicket.jsp">
                    <select name="dateid">
                        <%for (int i = 0; i < instances.size(); i++) { %>
                        <option value=<%=instances.get(i)[0]%>>
                            <%=instances.get(i)[1] %>
                        </option>
                        <%} %>
                    </select>


                    <input type="submit" value="Add to cart" class="btn btn-default">
                </form>
            </div>

            <%} else {%>
            <h4>There are no upcoming presentations of this event...</h4>
            <%} %>

        </div>
        <div class="col-xs-12">

            <%
                List<Event> suggestions = Queries.getEventSuggestions(id);

                if (suggestions.size() > 0) {

            %>

            <hr>
            <h2 class="text-center" style="color: #932BA6">You may also like:</h2>

            <%

                for (int i = 1; i < suggestions.size(); i++) {
            %>
            <div class="col-md-3 col-sm-4 col-xs-6">
                <a href=<%= "\"EventPage.jsp?Id=" + suggestions.get(i).getId() + "\"" %>>
                    <a href=<%= "\"EventPage.jsp?Id=" + suggestions.get(i).getId() + "\"" %>><img
                            src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="100%" height="auto"/></a>
                    <h3><a style="color: #62D1B5"
                           href=<%= "\"EventPage.jsp?Id=" + suggestions.get(i).getId() + "\"" %>>
                        <%=suggestions.get(i).getName()%>
                    </a></h3>
                    <h4><%=suggestions.get(i).getDate()%>
                    </h4>
            </div>

            <%
                        if (i >= 4) {
                            break;
                        }
                    }
                }%>

        </div>
    </div>
</div>

<div id="foo"></div>
<!--SCRIPTS-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>