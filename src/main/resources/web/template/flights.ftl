<!DOCTYPE html>
<html>
<head>
    <title>${user.getName()} flights</title>
    <link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<table>
    <tr>
        <th>Booking id</th>
        <th>Flight id</th>
        <th>Ticket count</th>
    </tr>
    <#list bookings as booking>
        <tr>
            <td>${booking.getId()}</td>
            <td>${booking.getFlightId()}</td>
            <td>${booking.getTicketCount()}</td>
        </tr>
    <#else>
        <p>You don't have flights!</p>
    </#list>
</table>
</body>
</html>