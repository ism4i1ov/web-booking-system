<!DOCTYPE html>
<html>
<head>
    <title>User panel</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/style.css" type="text/css">
</head>

<body>
<h3>Welcome, ${user.getName()}</h3>
<br>
<label>
    Online board:
    <table>
        <tr>
            <th>ID</th>
            <th>Airline name</th>
            <th>Flight from</th>
            <th>Destination</th>
            <th>Depart date time</th>
            <th>Free places</th>
        </tr>
        <#list flights as flight>
            <tr>
                <td>${flight.getId()}</td>
                <td>${flight.getAirlineName()}</td>
                <td>${flight.getFlightFrom()}</td>
                <td>${flight.getDestination()}</td>
                <td>${flight.getDepartDateTime()}</td>
                <td>${flight.getFreePlaces()}</td>
            </tr>
        </#list>
    </table>
</label>

<form method="post">
    <input type="submit" name="my_flights" value="My flights">
    <input type="submit" name="booking_flight" value="Booking flight">
    <input type="submit" name="logout" value="Logout">
</form>

</body>

</html>