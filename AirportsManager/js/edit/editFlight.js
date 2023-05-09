const id = new URLSearchParams(window.location.search).get("edit");

$(document).ready(function () {
    $('#edit-flight-form').submit(function (event) {
            event.preventDefault();
            let formData = $('#edit-flight-form').serialize();

            if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
                $.ajax({
                    type: 'POST',
                    url: 'http://app:8080/api/flights/addFlight',
                    data: formData,
                    headers: {
                        'Authorization': localStorage.getItem('token')
                    },
                    success: function () {
                        window.location.href = '../../html/view/flights.html';
                    },
                    error: function (data) {
                        if (data.status === 403) {
                            alert("Please log in");
                        } else if (data.status === 400 || data.status === 406) {
                            alert("Please provide valid data")
                        }
                    }
                });
            }
        }
    );
});

function myFunction(arr) {
    for (let i = 0; i < arr.length; i++) {
        if (id == arr[i].id) {
            document.getElementById("flightId").value = arr[i].id;
            document.getElementById("codeFrom").value = arr[i].from.code;
            document.getElementById("codeTo").value = arr[i].to.code;
            document.getElementById("departureTime").value = arr[i].departureTimeInMinutesSinceMidnight;
            document.getElementById("duration").value = arr[i].durationInMinutes;
        }
    }
}

if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
    $.ajax({
        type: 'GET',
        headers: {'Access-Control-Allow-Origin': 'app:8080', 'Authorization': localStorage.getItem('token')},
        url: 'http://app:8080/api/flights',
        success: function (data) {
            myFunction(data);
        },
        error: function (data) {
            alert('failed');
        }
    });
}