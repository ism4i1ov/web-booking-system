<!DOCTYPE html>
<html>
<head>
    <title>Booking flight</title>
    <link href="../css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3>Welcome, ${user.getName()}</h3>
<form method="post">
    <input type="text" name="destination" placeholder="Destination">
    <input type="number" name="ticket_count" placeholder="Ticket count">
    <input type="submit" name="search" value="Search">
</form>


<form method="post">
    Online board:
    <table>
        <tr>
            <th>ID</th>
            <th>Airline name</th>
            <th>Flight from</th>
            <th>Destination</th>
            <th>Depart date time</th>
            <th>Arrive date time</th>
            <th>Free places</th>
        </tr>
        <#list flights as flight>
            <tr>
                <td>${flight.getId()}</td>
                <td>${flight.getAirlineName()}</td>
                <td>${flight.getFlightFrom()}</td>
                <td>${flight.getDestination()}</td>
                <td>${flight.getDepartDateTimeString()}</td>
                <td>${flight.getArrivalDateTimeString()}</td>
                <td>${flight.getFreePlaces()}</td>
            </tr>
        <#else>
          <p>Search flight!</p>
        </#list>
    </table>
    <input type="text" placeholder="Input flight id!" name="flight_id">
    <input type="submit" name="booking" value="Booking">
</form>
</body>
</html>