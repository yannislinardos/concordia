<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="beans.*" import="java.util.*" import="util.*" %>
<%@ page import="AdminDashBoard.AdminActions" %>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Concordia Homepage</title>

    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    <!-- Scripts -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

</head>
<body>
<div id="bar"></div>
<div id="mod"></div>

<!--Super Fancy Carousel-->
<div class="container carousel" style="padding-left: 0px; padding-right: 0px">
    <div class="row">
        <div class="col-md-12">
            <div id="myCarousel" class="carousel slide" data-ride="carousel">

                <div class="carousel-inner">
                    <div class="item carouselImg active">
                        <img src="sources/drama.jpg" class="img-responsive">
                        <%--<div clas="carousel-caption">--%>
                        <%--<h4><a href="#">Como estais amigos!</a></h4>--%>
                        <%--<p>Give us your money - Walmart</p>--%>
                        <%--<br><br>--%>
                        <%--</div>--%>
                    </div>
                    <div class="item carouselImg">
                        <img src="sources/movie.jpg" class="img-responsive">
                        <%--<div class="carousel-caption">--%>
                        <%--<h4><a href="#">Como estais amigos!</a></h4>--%>
                        <%--<p>Give us your moneyGive us your money - WalmaGive us your money - WalmaGive us your money--%>
                        <%--- WalmaGive us your money - WallmartGive us your--%>
                        <%--money - WalmartGive us your money - WalmartGive us your money - Walmart</p>--%>
                        <%--<br><br>--%>
                        <%--</div>--%>
                    </div>
                    <div class="item carouselImg">
                        <img src="sources/theaterzaal.jpg" class="img-responsive">
                        <%--<div class="carousel-caption">--%>
                        <%--<h4><a href="#">Como estais amigos!</a></h4>--%>
                        <%--<p>Give us your money - Walmart</p>--%>
                        <%--<br><br>--%>
                        <%--</div>--%>
                    </div>
                    <%--<div class="item carouselImg">--%>
                        <%--<img src="sources/bottle.jpg" class="img-responsive">--%>
                        <%--&lt;%&ndash;<div class="carousel-caption">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<h4><a href="#">Como estais amigos!</a></h4>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<p>Give us your money - Walmart</p>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<br><br>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                    <%--</div>--%>
                </div>

                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left"></span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right"></span>
                </a>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid background">
    <div class="container content">
        <br>
        <div class="row">
            <div class="col-lg-4 col-sm-6">

                <div class="col-md-12">
                    <form class="form-group" role="search" action="SearchEvent.jsp">
                        <div class="input-group homepage-searchbar" style="font-family: Verdana">
                            <input type="text" class="form-control" placeholder="Search" name="searchWords">
                            <span class="input-group-addon">
                                <button type="submit"><span class="glyphicon glyphicon-search"></span></button>
                            </span>
                        </div>
                    </form>
                    <hr>
                </div>

                <%
                    String email = LoginDao.getToken(request.getCookies());
                    if (email != null) {
                %>

                <div class="text-center">
                    <h2 style="color: #06014A">Recommended for you</h2>
                </div>
                <%
                    List<Event> eventsRecommended = null;
                    eventsRecommended = Queries.getUserSuggestions(email);
                    int counter = 0;

                    if (eventsRecommended.size() == 0) {%>

                <div class="text-center">
                    <p>You're account does not have tags set. <a style="color: #62D1B5" href="ProfileSettings.jsp">Click
                        here</a> to add tags
                        to
                        your profile!</p>
                </div>

                <%
                } else {

                    for (int i = 0; i < eventsRecommended.size(); i++) {
                %>

                <div class="col-xs-12 well">
                    <div class="col-sm-6">
                        <a href=<%= "\"EventPage.jsp?Id=" + eventsRecommended.get(i).getId() + "\"" %>>
                            <img class="img-rounded center-block" src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="140"
                                 height="auto"></a>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-center">
                            <a style="color: #06014A"
                               href=<%= "\"EventPage.jsp?Id=" + eventsRecommended.get(i).getId() + "\"" %>>
                                <h4><%=eventsRecommended.get(i).getName()%>
                                </h4></a>
                        </div>
                        <hr>
                        <div><p><%=eventsRecommended.get(i).getDescription()%>
                        </p></div>
                    </div>
                </div>

                <%
                            counter++;
                            if (counter == 3) {
                                break;
                            }
                        }
                    }
                    ;%>

                <div class="col-xs-12">
                    <hr>
                </div>

                <%}%>


                <div class="text-center">
                    <a style="color: #e87a28"
                       href="SearchEvent.jsp?searchWords=&from=&until=&limit=no_limit&type=FILM">
                        <h2 class="couple" style="color: #e87a28">Film</h2>
                        <h2 class="couple" style="color: #0f0f0f">Agenda</h2></a>
                </div>
                <%
                    List<Event> eventsFilm = null;
                    eventsFilm = Queries.getUpcoming("FILM");
                    int counter = 0;

                    if (eventsFilm.size() == 0) {%>

                <div class="text-center">
                    <p>There are no upcoming films</p>
                </div>


                <%
                } else {
                   for (int i = 0; i < eventsFilm.size(); i++) {
                %>

                <div class="col-xs-12 well">
                    <div class="col-sm-6">
                        <a href=<%= "\"EventPage.jsp?Id=" + eventsFilm.get(i).getId() + "\"" %>>
                            <img class="img-rounded center-block" src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="140"
                                 height="auto"></a>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-center">
                            <a style="color: #e87a28"
                               href=<%= "\"EventPage.jsp?Id=" + eventsFilm.get(i).getId() + "\"" %>>
                                <h4><%=eventsFilm.get(i).getName()%>
                                </h4></a>
                        </div>
                        <hr>
                        <div><p><%=eventsFilm.get(i).getDescription()%>
                        </p></div>
                    </div>
                </div>

                <%
                            counter++;
                            if (counter == 3) {
                                break;
                            }
                        }
                    }
                    ;%>



                <div class="col-xs-12">
                    <hr>
                </div>


                <div class="text-center">
                    <a style="color: #62D1B5"
                       href="SearchEvent.jsp?searchWords=&from=&until=&limit=no_limit&type=THEATER">
                        <h2 class="couple" style="color: #62D1B5">Theater</h2>
                        <h2 class="couple" style="color: #0f0f0f">Agenda</h2></a>
                </div>
                <%
                    List<Event> eventsTheater = null;
                    eventsTheater = Queries.getUpcoming("THEATER");
                    counter = 0;

                    if (eventsTheater.size() == 0) {%>

                <div class="text-center">
                    <p>There are no upcoming theater performances</p>
                </div>

                <%
                } else {

                    for (int i = 0; i < eventsTheater.size(); i++) {
                %>

                <div class="col-xs-12 well">
                    <div class="col-sm-6">
                        <a href=<%= "\"EventPage.jsp?Id=" + eventsTheater.get(i).getId() + "\"" %>>
                            <img class="img-rounded center-block" src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="140"
                                 height="auto"></a>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-center">
                            <a style="color: #62D1B5"
                               href=<%= "\"EventPage.jsp?Id=" + eventsTheater.get(i).getId() + "\"" %>>
                                <h4><%=eventsTheater.get(i).getName()%>
                                </h4></a>
                        </div>
                        <hr>
                        <div><p><%=eventsTheater.get(i).getDescription()%>
                        </p></div>
                    </div>
                </div>

                <%
                            counter++;
                            if (counter == 3) {
                                break;
                            }
                        }
                    }
                    ;%>
                <div class="col-xs-12">
                    <hr>
                </div>

                <div class="text-center">
                    <a style="color: #932BA6"
                       href="SearchEvent.jsp?searchWords=&from=&until=&limit=no_limit&type=EXPOSITION">
                        <h2 class="couple" style="color: #932BA6">Exposition</h2>
                        <h2 class="couple" style="color: #0f0f0f">Agenda</h2></a>
                </div>
                <%
                    List<Event> eventsExpo = null;
                    eventsExpo = Queries.getUpcoming("EXPOSITION");
                    counter = 0;

                    if (eventsExpo.size() == 0) {%>

                <div class="text-center">
                    <p>There are no upcoming expositions</p>
                </div>
                <%
                } else {

                    for (int i = 0; i < eventsExpo.size(); i++) {
                %>

                <div class="col-xs-12 well">
                    <div class="col-sm-6">
                        <a href=<%= "\"EventPage.jsp?Id=" + eventsExpo.get(i).getId() + "\"" %>>
                            <img class="img-rounded center-block" src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg" width="140"
                                 height="auto"></a>
                    </div>
                    <div class="col-sm-6">
                        <div class="text-center">
                            <a style="color: #932BA6"
                               href=<%= "\"EventPage.jsp?Id=" + eventsExpo.get(i).getId() + "\"" %>>
                                <h4><%=eventsExpo.get(i).getName()%>
                                </h4></a>
                        </div>
                        <hr>
                        <div><p><%=eventsExpo.get(i).getDescription()%>
                        </p></div>
                    </div>
                </div>

                <%
                            counter++;
                            if (counter == 3) {
                                break;
                            }
                        }
                    }
                    ;%>
                <div class="col-xs-12">
                    <hr>
                </div>

                <div class="twitterfeed">
                    <div class="col-xs-12">
                        <a class="twitter-timeline" data-height="1200" data-link-color="#932BA6"
                           href="https://twitter.com/concordia053">
                            Tweets by @concordia053
                        </a>
                        <script async src="//platform.twitter.com/widgets.js" charset="utf-8"></script>
                        <div class="col-xs-12">
                            <hr>
                        </div>
                    </div>
                </div>

            </div>


            <div class="col-lg-8 col-sm-6">

                <div class="col-md-6 col-md-offset-6">
                    <div class="col-xs-4 text-center">
                        <h4>
                            <a href="https://twitter.com/concordia053" style="color: cornflowerblue"><i
                                    class="fa fa-twitter"></i></a></h4>
                    </div>
                    <div class="col-xs-4 text-center">
                        <h4>
                            <a href="https://www.facebook.com/concordia053/" style="color: darkblue"><i
                                    class="fa fa-facebook"></i></a></h4>
                    </div>
                    <div class="col-xs-4 text-center">
                        <h4>
                            <a href="https://www.youtube.com/user/Concordia053" style="color: #ab0000"><i
                                    class="fa fa-youtube"></i></a></h4>
                    </div>
                </div>

                <div class="col-xs-12">
                    <hr>
                </div>

                <!-- ----------------------------------------------------------------------------------------- -->

                <%
                    List<Article> articles = Queries.getArticles();

                    for (int i = 0; i < articles.size() && i < 4; i++) {
                %>


                <div class="col-xs-12">
                    <div class="col-xs-12">
                        <div class="text-center">
                            <h2>
                                <a style="color:
                                   #62D1B5" href=<%= "\"NewsArticle.jsp?Id=" + articles.get(i).getArticleid() + "\"" %>><%=articles.get(i).getTitle() %>
                                </a></h2>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6">
                        <img href="<%= "\"NewsArticle.jsp?Id=" + articles.get(i).getArticleid() + "\"" %>" src="sources/placeholder<%=(int )(Math.random() * 10 + 1)%>.jpg"
                             width="180px" height="auto" class="img-rounded center-block">
                    </div>

                    <%--<div class="col-lg-8 col-md-6">--%>

                    <p style="font-family: Arial"><%=articles.get(i).getBody() %>
                    </p>
                    <div class="col-xs-12">
                        <blockquote style="color: #932BA6"><%=articles.get(i).getDate() %>
                        </blockquote>
                        <hr>
                    </div>
                </div>
                <%--</div>--%>

                <%} %>

            </div>


            <!-- ----------------------------------------------------------------------------------------- -->

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