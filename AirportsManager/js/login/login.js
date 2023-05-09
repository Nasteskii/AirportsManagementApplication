$(document).ready(function() {
    $('#login-form').submit(function(event) {
        event.preventDefault();
        let formData = $('#login-form').serialize();

        $.ajax({
            type: 'POST',
            url: 'http://app:8080/api/login',
            data: formData,
            success: function(response) {
                localStorage.setItem('token', response);
                window.location.href = '../../html/view/airports.html';
            },
            error: function(response) {
                if (response.status === 0) {
                    alert("Not connected to server");
                } else {
                    alert(response.status + ": Invalid credentials");
                    $('#username-id').val('');
                    $('#password-id').val('');
                }
            }
        });
    });
});