<%@ page import="model.User" %>
<%@ page import="model.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Event Manager</title>
    <link href="/css/main.css" rel="stylesheet">
    <link href="/css/riot-slider.min.css" rel="stylesheet">
    <script src="/js/jquery-3.6.1.js"></script>
    <script src="/js/riot-slider.min.js"></script>
    <script src="/js/myjs.js"></script>
</head>

<%
    User user = (User) session.getAttribute("user");

%>
<body>
<div class="mainDiv">
    <div>
        <ul class="riot-slider">
            <!-- each slide is an li tag (list item) -->
            <li><img src="/image/pexels-pixabay-60597.jpg" width="1000"/></li>
            <li><img src="/image/img.png" width="1000" /></li>
            <li><img src="/image/img_1.png"  width="1000"/></li>
        </ul>
    </div>
    <div>
        <br>
        <a href="/events">Show All Events</a>
        <%
            if (user != null && user.getUserRole() == UserRole.ADMIN) {
        %>
        <a href="/events/add">Add Event</a>
        <%}%>

        <%
            if (user != null) {
        %>
        <a href="/users">Show all users</a>
        <a href="/logout">Logout</a>
        <%} else {%>
        <a href="/users/add">Register </a>

        <a href="/login">Login</a>
        <%}%>

    </div>

<div style="width: 200px; margin: 0 auto">
    <button class="colorBtn" onclick="onBtnClick(this)">Red</button>
    <button class="colorBtn" onclick="onBtnClick(this)">Orange</button>
    <button class="colorBtn" onclick="onBtnClick(this)">Black</button>
</div>
<br>
<br>
<br>
    <div>
        <div style="width: 200px; height: 200px; border: 1px solid red; background-color: blue; margin: 0 auto" id="myDiv"></div>
    </div>
</div>

</body>
</html>
