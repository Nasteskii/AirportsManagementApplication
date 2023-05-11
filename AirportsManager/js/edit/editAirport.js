const code = new URLSearchParams(window.location.search).get("edit");

$(document).ready(function () {
    $('#edit-airport-form').submit(function (event) {
            event.preventDefault();
            let formData = $('#edit-airport-form').serialize();

            if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
                $.ajax({
                    type: 'POST',
                    url: 'http://app:8080/api/airports/addAirport',
                    data: formData,
                    headers: {
                        'Authorization': localStorage.getItem('token')
                    },
                    success: function () {
                        window.location.href = '../../html/view/airports.html';
                    },
                    error: function (data) {
                        if (data.status === 403) {
                            alert("Please log in");
                        }
                        else if (data.status === 400 || data.status === 406) {
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
        if (code === arr[i].code) {
            document.getElementById("code").value = arr[i].code;
            document.getElementById("name").value = arr[i].name;
            document.getElementById("country").value = arr[i].country;
            document.getElementById("newCode").value = arr[i].code;
            document.getElementById("passengers").value = arr[i].annualNumberOfPassengers;
        }
    }
}

if (localStorage.getItem('token') !== null && localStorage.getItem('token') !== undefined) {
    $.ajax({
        type: 'GET',
        headers: {'Access-Control-Allow-Origin': 'app:8080', 'Authorization': localStorage.getItem('token')},
        url: 'http://app:8080/api/airports',
        success: function (data) {
            myFunction(data);
        },
        error: function (data) {
            alert('failed');
        }
    });
}