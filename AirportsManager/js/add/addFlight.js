$(document).ready(function () {
    $('#add-flight-form').submit(function (event) {
            event.preventDefault();
            let formData = $('#add-flight-form').serialize();

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
                            alert("Please provide valid data");
                        }
                    }
                });
            }
        }
    );
});