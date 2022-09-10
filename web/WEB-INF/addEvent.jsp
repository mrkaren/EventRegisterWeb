<%--
  Created by IntelliJ IDEA.
  User: karen
  Date: 31.08.22
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Add event
    </title>
</head>
<body>
Please input event's data:
<form action="/events/add" method="post">
    <input type="text" name="name" placeholder="please input name"/> <br>
    <input type="text" name="place" placeholder="please input place"/> <br>
    Is Online? <br>
    Yes <input type="radio" name="isOnline" value="TRUE">
    No <input type="radio" name="isOnline" value="FALSE">
    <br>
    Event Type
    <select name="eventType">
        <option value="FILM">FILM</option>
        <option value="SPORT">SPORT</option>
        <option value="GAME">GAME</option>
    </select>
    <br>
    <input type="number" name="price" placeholder="Please input price">
    <br>
    <input type="date" name="eventDate"/> <br>
    <input type="submit" value="Add">
</form>
</body>
</html>
