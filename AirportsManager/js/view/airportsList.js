let airports;

$(document).ready(function () {
    $('#most-passengers-search-btn').click(function () {
        let max = 0;
        let maxi = 0;
        let hasCountry = airports.some(airport => airport.country === $('#airportWithMostPassengersByCountry').val());

        if (!hasCountry) {
            for (let i = 0; i < airports.length; i++) {
                if (airports[i].annualNumberOfPassengers > max) {
                    max = airports[i].annualNumberOfPassengers;
                    maxi = i;
                }
            }
        } else {
            for (let i = 0; i < airports.length; i++) {
                if (airports[i].country === $('#airportWithMostPassengersByCountry').val()) {
                    if (airports[i].annualNumberOfPassengers > max) {
                        max = airports[i].annualNumberOfPassengers;
                        maxi = i;
                    }
                }
            }
        }
        $('#all-airports').html('<p>' + airports[maxi].code + ' - ' + airports[maxi].name + ' - ' + airports[maxi].country + ' - ' + airports[maxi].annualNumberOfPassengers + '</p>');
    });
});

function loadAirports(arr) {
    let table = $('<table>').addClass('table').addClass('table-striped');
    let header = $('<thead>').appendTo(table);
    $('<tr>').appendTo(header)
        .append($('<th>').text('Airport Code'))
        .append($('<th>').text('Airport Name'))
        .append($('<th>').text('Country'))
        .append($('<th>').text('Annual number of passengers'));
    let body = $('<tbody>').appendTo(table);
    $.each(arr, function (i, airport) {
        $('<tr>').appendTo(body)
            .append($('<td>').text(airport.code))
            .append($('<td>').text(airport.name))
            .append($('<td>').text(airport.country))
            .append($('<td>').text(airport.annualNumberOfPassengers))
            .append($('<td>')
                .append($('<a>').attr('href', '../edit/editAirportForm.html?edit=' + airport.code)
                    .append($('<button>').addClass('btn btn-edit').text('Edit')))
                .append($('<a>').attr('href', '#')
                    .append($('<button>').addClass('btn btn-delete').text('Delete')
                    .click(function () {
                            if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
                                $.ajax({
                                    type: 'POST',
                                    url: 'http://app:8080/api/airports/delete',
                                    data: {code: airport.code},
                                    headers: {
                                        'Authorization': localStorage.getItem('token')
                                    },
                                    success: function () {
                                        window.location.href = '../../html/view/airports.html';
                                    },
                                    error: function (data) {
                                        alert(data.status);
                                    }
                                });
                            }
                        }))));
    });
    $("#all-airports").empty().append(table);
}

if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
    $.ajax({
        type: 'GET',
        headers: {'Authorization': localStorage.getItem('token')},
        url: 'http://app:8080/api/airports',
        success: function (data) {
            airports = data;
            loadAirports(airports);
        },
        error: function (data) {
            if (data.status === 403) {
                alert("Please log in");
            } else {
                alert("Server not responding")
            }
        }
    });
} else {
    alert('Please Log in')
    window.location.href = '../../html/login/login.html';
}