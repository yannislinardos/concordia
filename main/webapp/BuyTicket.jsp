<%@ page import="java.util.Random" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="util.*" import="beans.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Buy Ticket</title>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">

    <!--Source for jQuery script-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>


</head>

<body>

<div id="mod"></div>
<div id="bar"></div>


 <%String email = LoginDao.getToken(request.getCookies());
 if (email == null) {
	 email = "";
 }
 String dateid = request.getParameter("dateid");
 
 Event event = Queries.getEventFromDate(dateid);
 
 String date = Queries.getDate(dateid);
 %>
 

                <div class="container">
                
                    <div class="col-md-8 content">
        <form action="BuyTicket" method="post">
   
     <input type="hidden" name="dateid" value="<%=dateid%>">

                <div class="col-md-12 col-md-offset-0">
                    <div id="createEvent">

 <center><h2>You want to buy tickets for <%=event.getName() %> on <%=date %>
 <br/>
 Cost: <%=event.getTicketPrice() %> euros per ticket
 <br/>
 There are <%=Queries.getSeatsLeft(dateid) %> seats left</h2></center>

                        <fieldset class="form-group row">
                            <legend class="col-form-legend col-sm-2">Buy ticket</legend>
                           
                        </fieldset>


                        <div class="form-group row">
                            <div class="col-md-2 col-md-offset-0">
                               Your e-mail
                            </div>
                            <label for="EventName" class="col-sm-0 col-form-label"></label>
                            <div class="col-sm-10" style="font-family: Verdana">
                                <input type="text" class="form-control input-lg" id="email" name="email" value=<%=email %>>
                            </div>
                        </div>
						
                       
                        <div class="form-group">
                            <div class="col-md-2 col-md-offset-0">
                                Quantity
                            </div>
                            <label for="omschrijving"></label>
                            <div class="col-md-10" style="font-family: Verdana">
                           <select name="quantity">
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
                        </div>
                    <center><h2>LET'S SAY THAT PAYMENT IS DONE</h2></center>
                   
                        <input type="submit" name="BUY" class="btn btn-default">
                    </div>
                </div>
                </form>
                
    </div>
    
                
                
        </div>        
                          
                

<div id="foo"></div>
<!--SCRIPTS-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/scripts.js"></script>

</body>
</html>