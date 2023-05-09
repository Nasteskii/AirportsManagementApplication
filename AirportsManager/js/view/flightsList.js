$(document).ready(function () {
    let flights;
    $('#flights-from-search-btn').click(function () {
        const airportCode = $('#flightsFromAirport').val();
        const flightsFromAirport = flights.filter(flight => flight.from.code === airportCode);
        const airport = {airportCode, flightsFromAirport};
        $('#all-flights').html(JSON.stringify(airport));
    });

    $('#flights-to-search-btn').click(function () {
        const airportCode = $('#flightsToAirport').val();
        const filteredFlights = flights.filter(flight => flight.to.code === airportCode);
        generateTable(filteredFlights);
    });

    $('#flights-fromAtoB-search-btn').click(function () {
        const originCode = $('#flightsFromA').val();
        const destinationCode = $('#flightsToB').val();
        const filteredFlights = flights.filter(flight => flight.from.code === originCode && flight.to.code === destinationCode);
        generateTable(filteredFlights);
    });

    function generateTable(flightsArr) {
        const table = $('<table>').addClass('table').addClass('table-striped');
        const header = $('<thead>').appendTo(table);
        $('<tr>').appendTo(header)
            .append($('<th>').text('From airport'))
            .append($('<th>').text('To airport'))
            .append($('<th>').text('Departure time in minutes since 00:00'))
            .append($('<th>').text('Duration in minutes'));

        let body = $('<tbody>').appendTo(table);
        flightsArr.forEach(flight => {
            $('<tr>').appendTo(body)
                .append($('<td>').text(flight.from.code))
                .append($('<td>').text(flight.to.code))
                .append($('<td>').text(flight.departureTimeInMinutesSinceMidnight))
                .append($('<td>').text(flight.durationInMinutes))
                .append($('<td>')
                    .append($('<a>').attr('href', '../edit/editFlightForm.html?edit=' + flight.id)
                        .append($('<button>').addClass('btn btn-edit').text('Edit')))
                    .append($('<a>').attr('href', '#')
                        .append($('<button>').addClass('btn btn-delete').text('Delete')
                            .click(function () {
                                if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
                                    $.ajax({
                                        type: 'POST',
                                        url: 'http://app:8080/api/flights/delete',
                                        data: {id: flight.id},
                                        headers: {
                                            'Authorization': localStorage.getItem('token')
                                        },
                                        success: function () {
                                            window.location.href = '../../html/view/flights.html';
                                        },
                                        error: function (data) {
                                            alert(data.status);
                                        }
                                    });
                                }
                            }))));
        });
        $("#all-flights").empty().append(table);

    }

    if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
        $.ajax({
            type: 'GET',
            headers: {'Authorization': localStorage.getItem('token')},
            url: 'http://app:8080/api/flights',
            success: function (data) {
                flights = data;
                generateTable(flights);
            },
            error: function (data) {
                if (data.status === 403) {
                    alert("Please log in");
                } else {
                    alert("Server not responding")
                }            }
        });
    } else {
        alert('Please Log in');
        window.location.href = '../../html/login/login.html';
    }
});