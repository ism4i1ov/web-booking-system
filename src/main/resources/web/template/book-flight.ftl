<!DOCTYPE html>
<html>
<head>
    <title>Booking flight</title>
    <link href="../css/style.css" rel="stylesheet" type="text/css">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
<h3>Welcome, ${user.getName()}</h3>
<form method="post">
    <label for="destination">
        <input id="destination" type="text" name="destination" placeholder="Destination">
    </label>
    <label for="ticket_count">
        <input id="ticket_count" type="number" name="ticket_count" placeholder="Ticket count">
    </label>
    <label for="depart_date">
        <input id="depart_date" type="date" name="depart_date" placeholder="Depart date">
    </label>
    <input type="submit" name="search" value="Search">

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
            <th>Booking</th>
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
                <td>
                    <button type="button" data-toggle="modal" data-target="#myModal">Book flight</button>
                </td>
            </tr>
        <#else>
            <p>Search flight!</p>
        </#list>
    </table>
</form>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <form method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Book flight</h4>
                </div>
                <div class="modal-body">
                    <label>
                        <input type="number" placeholder="Ticket count" name="ticket_count">
                    </label>
                    <label>
                        <input type="number" placeholder="Flight id" name="flight_id">
                    </label>
                </div>
                <div class="modal-footer">
                    <button type="submit" name="booking" class="btn btn-default">Booking</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>