<!DOCTYPE html>
<html>
<head>
    <title>${user.getName()} flights</title>
    <link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<table>
    <form method="post">
        <tr>
            <th>Booking id</th>
            <th>Flight id</th>
            <th>Ticket count</th>
            <th>Remove</th>
        </tr>
        <#list bookings as booking>
            <tr>
                <td>${booking.getId()}</td>
                <td>${booking.getFlightId()}</td>
                <td>${booking.getTicketCount()}</td>
                <td>
                    <button type="submit" name="remove_booking" value="${booking.getId()}">Remove</button>
                </td>
            </tr>
        </#list>
    </form>
</table>
</body>
</html>