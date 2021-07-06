<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="member.SearchAndSubmitTagsID" import="java.util.*"
         import="util.*" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- If IE use the latest rendering engine -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <link rel="shortcut icon" href="sources/concordia-mini.ico" type="image/x-icon">

    <meta name="viewport" content="width = device-width, initial-scale = 1">
    <title>Profile Settings</title>
    <!-- Styles -->
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<!-- -------------------INSTEAD OF FILTER-------------------------->
        <%

        String email = LoginDao.getToken(request.getCookies());
        if (email == null) {
        	response.sendRedirect("HomePage.jsp");
        	} %>

 <!-- ------------------------------------------------------------->

<body>
<div id="mod"></div>
<div id="bar"></div>
<div class="container">


    <div class="col-md-2">
        <div class="list-group">
            <a href="ProfileDetails.jsp" class="list-group-item">Profile</a>
            <a href="ProfileSettings.jsp" class="list-group-item">Settings</a>
        </div>
    </div>
    <div class="col-md-8 content">
        <form id="change_settings" action="MemberActions" method="post"
              role="form">
            <div class="card-block">
                <div class="text-center">
                    <h2>Here you can change your profile settings!</h2>
                </div>
                <br>

                <h5><a style="color: #932BA6" href="PasswordReset.jsp">Reset your password</a></h5>

                <div class="form-group row">
                    <label class="col-lg-3 control-label" for="FirstName">Name</label>
                    <div class="col-md-6">
                        <input id="FirstName" name="Name" type="text"
                               placeholder="Your first name" class="form-control">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 control-label" for="Surname">Surname</label>
                    <div class="col-md-6">
                        <input id="Surname" name="Surname" type="text"
                               placeholder="Your surname" class="form-control">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 control-label" for="Country">Country</label>
                    <div class="col-md-6">
                        <input id="Country" name="Country" type="text" tabindex="7"
                               placeholder="The Netherlands" class="form-control">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 control-label" for="City">City</label>
                    <div class="col-md-6">
                        <input id="City" name="City" type="text" tabindex="8"
                               placeholder="City" class="form-control">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 control-label" for="Birthday">Birthday</label>
                    <div class="col-md-6">
                        <input id="Birthday" name="Birthday" type="date" tabindex="9"
                               placeholder="1990-01-01" class="form-control">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 control-label" for="MobilePhone">MobilePhone</label>
                    <div class="col-md-6">
                        <input id="MobilePhone" name="MobilePhone" type="tel"
                               tabindex="10" placeholder="06-12345678" class="form-control">
                    </div>
                </div>

            </div>
            <!--TAGS-->
            <br>
            <div class="col-lg-12">

                <%
                    SearchAndSubmitTagsID tags = new SearchAndSubmitTagsID();
                    //HASHMAP FOR tagid AND tag
                    HashMap<Integer, String> tagsMap = new HashMap<Integer, String>();
                    //TAKE EMAIL FROM COOKIES
                    if (tagsMap != null) {
                        String email1 = LoginDao.getToken(request.getCookies());
                        if (email != null) {
                            tagsMap = tags.searchMemberTags(email);
                        }
                    }
                    for (Integer key : tagsMap.keySet()) {
                %>
                <%--<div class="chip">--%>
                <%--<%=tagsMap.get(key)%>--%>
                <%--<!--Remove this tag when clicked-->--%>

                <%--</div>--%>

                <%
                    }
                %>
                <br>
                <br>

                <div class="col-md-6">
                    <p>ADD-Mutiple select list (hold shift to select more than one):</p>
                    <select id="allTags" name="allTags[]" multiple class="form-control">
                        <%
                            List<String> allTags = tags.getAllTagNames();
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
                </div>

                <div class="col-md-6">
                    <p>REMOVE-Mutiple select list (hold shift to select more than one):</p>
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
                <br>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-6 col-md-offset-3">
                            <input type="submit" name="change-submit" id="change-submit"
                                   tabindex="4" class="form-control btn btn-register"
                                   value="Submit Changes">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <div class="col-md-1">
        <%
            tags = new SearchAndSubmitTagsID();
            //HASHMAP FOR tagid AND tag
            tagsMap = new HashMap<Integer, String>();
            //TAKE EMAIL FROM COOKIES
            if (tagsMap != null) {
                String email1 = LoginDao.getToken(request.getCookies());
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
    </div>
</div>

<!-- END OF PAGE -->

<div id="foo"></div>

<!-- SCRIPTS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="js/scripts.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>
