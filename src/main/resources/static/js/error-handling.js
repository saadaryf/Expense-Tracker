
// to show error messages received from ResponseEntity for transaction and user validation
$(document).ready(function () {
    $('#transaction-form').submit(function (event) {
        event.preventDefault();
        $.ajax({
            url: this.action,
            type: this.method,
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (data) {
                $('#error-popup').removeClass("active");
                window.location.href = '/';
            },
            error: function (jqXHR, textStatus, errorThrown) {
                var errorMessage = "Error fetching data";
                try {
                    var errorResponse = JSON.parse(jqXHR.responseText);
                    if (errorResponse.details) {
                        errorMessage = errorResponse.details;
                    }
                } catch (parseError) {
                    console.log('Error parsing JSON response:', parseError);
                }
                $('#error-popup').text(errorMessage).addClass("active");
            }
        }); 
    });
    
});

// for user registration
$('#signup-form').submit(function (event){
    event.preventDefault();
    $.ajax({
        url: this.action,
        method: this.method,
        data: new FormData(this),
        processData: false,
        contentType: false,
        success: function (data) {
            $('#login-error-popup').removeClass("active");
            window.location.href = '/login';
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var errorMessage = "Error fetching data";
            try {
                var errorResponse = JSON.parse(jqXHR.responseText);
                if (errorResponse.details) {
                    errorMessage = errorResponse.details;
                }
            } catch (parseError) {
                console.log('Error parsing JSON response:', parseError);
            }
            $('#login-error-popup').text(errorMessage).addClass("active");
        }
    });
});