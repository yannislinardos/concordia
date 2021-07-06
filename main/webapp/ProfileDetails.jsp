<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="member.SearchAndSubmitTagsID" import="java.util.*"
         import="util.*" import="member.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!-- Set the page to the width of the device and set the zoom level -->
    <meta name="viewport" content="width = device-width, initial-scale = 1">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <title>Profile Details</title>
    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div id="mod"></div>
<div id="bar"></div>

<!-- ---------------------------FILTER-------------------------->
        <%

        String email1 = LoginDao.getToken(request.getCookies());
        if (email1 == null) {
        	response.sendRedirect("HomePage.jsp");
        	} %>

 <!-- ------------------------------------------------------------->
<div class="container">
    <div class="col-md-2">
        <div class="list-group">
            <a href="ProfileDetails.jsp" class="list-group-item">Profile</a>
            <a href="ProfileSettings.jsp" class="list-group-item">Settings</a>
        </div>
    </div>
    <div class="col-md-8 content">
        <%
            String name;
            String surname;
            String email;
            String city;
            String country;
            String phone_number;
            String birthday;

            String emailC = LoginDao.getToken(request.getCookies());
            MemberQueries query = new MemberQueries();

            List<String> all = MemberQueries.getAllDetails(emailC);
    		if(!all.isEmpty() && all.size() >3){
    			name = all.get(5);
    			surname = all.get(6);
    			email = all.get(0);
    			if(all.get(2) == null){
    				city="";
    			}else{
    				city = all.get(2);
    			}
    			if(all.get(3) == null){
    				country ="" ;
    			}else{
    				country = all.get(3);
    			}
    			if(all.get(4) == null){
    				phone_number = "";
    			}else{
    				phone_number = all.get(4);
    			}
    			if(all.get(1) == null){
    				birthday = "";
    			}else{
    				birthday = all.get(1);
    			}
    		}else if(all.size() == 3){
    			name = all.get(1);
    			surname = all.get(2);
    			email = all.get(0);
    			city = "";
    			country ="";
    			phone_number = "";
    			birthday = "";
    		}else{
    			name = "";
    			surname = "";
    			email = "";
    			city = "";
    			country ="";
    			phone_number = "";
    			birthday = "";
    		}
        %>
        <div class="card-block">
            <!-- Card header -->
            <div class="text-center">
                <h2>Your Profile Details</h2>
            </div>
            <!-- Card body -->

            <hr class="mt-2 mb-2">
            <br> <label class="col-lg-1 control-label">Email</label>
            <div class="col-lg-11 col-sm-12">
                <p><%=email%>
                </p>
            </div>
            <hr class="mt-2 mb-2">
            <div class="panel panel-default">
                <br> <label class="col-lg-1 control-label" >Name</label>
                <div class="col-lg-11 col-sm-12">
                    <p><%=name%>
                    </p>
                </div>
                <hr class="mt-2 mb-2">
                <br> <label class="col-lg-1 control-label">Surname</label>
                <div class="col-lg-11 col-sm-12">
                    <p><%=surname%>
                    </p>
                </div>
                <hr class="mt-2 mb-2">
                <label class="col-lg-1 control-label">Country</label>
                <div class="col-lg-11 col-sm-12">
                    <p><%=country%>
                    </p>
                </div>
                <hr class="mt-2 mb-2">
                <br> <label class="col-lg-1 control-label">City</label>
                <div class="col-lg-11 col-sm-12">
                    <p><%=city%>
                    </p>
                </div>
                <hr class="mt-2 mb-2">
                <br> <label class="col-lg-1 control-label">Birthday</label>
                <div class="col-lg-11 col-sm-12">
                    <p><%=birthday%>
                    </p>
                </div>
                <hr class="mt-2 mb-2">
                <br> <label class="col-lg-1 control-label">MobilePhone</label>
                <div class="col-lg-11 col-sm-12">
                    <p><%=phone_number%>
                    </p>
                </div>
                <br>
            </div>
            <!--TAGS-->
        </div>
    </div>
    <div class="col-lg-1">

        <%
            SearchAndSubmitTagsID tags = new SearchAndSubmitTagsID();
            //HASHMAP FOR tagid AND tag
            HashMap<Integer, String> tagsMap = new HashMap<Integer, String>();
            //TAKE EMAIL FROM COOKIES
            if (tagsMap != null) {
                email = LoginDao.getToken(request.getCookies());
                if (email != null) {
                    tagsMap = tags.searchMemberTags(email);
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

    </div>

</div>
	<div id="foo"></div>

	<!-- SCRIPTS -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<script src="js/scripts.js"></script>
	<script src="js/bootstrap.min.js"></script>
				
</body>
</html>