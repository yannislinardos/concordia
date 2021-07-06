<%@ page import="util.*" %>
<!-- Collapsible Navigation Bar -->


<!-- .navbar-fixed-top, or .navbar-fixed-bottom can be added to keep the nav bar fixed on the screen -->
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">

        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">


            <!-- Button that toggles the navbar on and off on small screens -->
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-top">

                <!-- Draws 3 bars in navbar button when in small mode -->
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="HomePage.jsp" class="navbar-brand"><img src="sources/concordia.jpg" width="auto" height="30"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-top">
            <ul class="nav navbar-nav navbar-right">

                <%String email = LoginDao.getToken(request.getCookies());
                if (email != null) {%>
                <li><a style="color: #e87a28"> Welcome <%=Queries.getName(email)%></a></li>
                <%}%>

                <li><a style="color: #932BA6" href="HomePage.jsp">Home</a></li>
                <li><a style="color: #932BA6" href="NewsArticleList.jsp">News</a></li>
                <li><a style="color: #932BA6" href="SearchEvent.jsp?searchWords=&from=&until=&limit=no_limit&type=ALL">Events</a></li>

                <!-- dropdown list-item -->
                <li class="dropdown">
                    <a style="color: #932BA6" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Misc<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a style="color: #932BA6" href="Contact.jsp">Contact</a></li>
                        <li><a style="color: #932BA6" href="Service.jsp">Service</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a style="color: #932BA6" href="PasswordReset.jsp">Reset Password</a></li>
                    </ul>
                </li>

                    <%if (email != null) {
                    %>
                    <li> <a style="color: #932BA6" href="ProfileSettings.jsp"> Profile Settings</a></li>
                        <%}%>

                <%if (email == null) {%>
                <li><a style="color: #06014A" href="#" data-toggle="modal" data-target="#signIn">Sign In</a></li>

                <%} else {%>
                <li><a style="color: #06014A" data-toggle="modal" href="./Logout">Logout</a></li>
                <% } %>

            </ul>
        </div>
    </div>
</nav>