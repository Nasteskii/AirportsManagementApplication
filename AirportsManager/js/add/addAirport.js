$(document).ready(function () {
    $('#add-airport-form').submit(function (event) {
            event.preventDefault();
            let formData = $('#add-airport-form').serialize();

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
                            alert("Please provide valid data");
                        }
                        else if (data.status === 500) {
                            alert("The airport with the code you entered may exist");
                        }
                    }
                });
            }
        }
    );
});