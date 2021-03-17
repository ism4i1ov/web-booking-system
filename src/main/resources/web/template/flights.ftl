<!DOCTYPE html>
<html>
<head>
    <title>${user.getName()} flights</title>
    <link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<form method="post">
    <table>
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
    </table>
    <input type="submit" name="main_page" value="Main page">
    <input type="submit" name="logout" value="Logout">
</form>
</body>
</html>