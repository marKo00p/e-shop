$(function () {
    $('div.toggles div.theme-btn').on('click', function () {
        $('body').toggleClass('dark-mode')
        $('div.theme-btn span:first-child').toggleClass('active')
        $('div.theme-btn span:last-child').toggleClass('active')
    })


})

let currentActiveMenu = $('main aside div.sidebar div ul li a[href="' + location.pathname + '"]')
currentActiveMenu.addClass('active')



// for Edit product in service.html

// $(document).ready(function() {
//     $('.edit-table tbody tr td .Ebtn').on('click', function (event){
//         event.preventDefault();
//         const href = $(this).attr('href');
//         $.get(href, function (product,status){
//             $('#PId').val(product.id);
//             $('#PTitle').val(product.title);
//             $('#PPrice').val(product.price);
//             $('#PDescription').val(product.description);
//             $('#PQuantity').val(product.quantity);
//              $('#PImage').val(product.image);
//                 $('#editModal').modal();
//             })
//             .fail(function() {
//                 console.log('Error getting product');
//             });
//     });
// });

$(document).ready(function() {
    $('.edit-table tbody tr td .btn').on('click',function (event){
        event.preventDefault();
        let href = $(this).attr('href');
        $.get(href, function (product,status){
            $('#editModal #PId').val(product.id);
            $('#editModal #PTitle').val(product.title);
            $('#editModal #PPrice').val(product.price);
            $('#editModal #PDescription').val(product.description);
            $('#editModal #PQuantity').val(product.quantity);
            $('#editModal #PImage').val('src',product.image);
        });
        $('.formModal #editModal').modal();
        });
});



//JS for sing_up.html
$(document).ready(function() {
    let myInput = $("#psw");
    let letter = $("#letter");
    let capital = $("#capital");
    let number = $("#number");
    let length = $("#length");
    let message = $("#message");

// When the user clicks on the password field, show the message box
    myInput.focus(function() {
    message.css("display", "block");
});

// When the user clicks outside of the password field, hide the message box
    myInput.blur(function() {
    message.css("display", "none");
});

// When the user starts to type something inside the password field
    myInput.keyup(function() {
// Validate lowercase letters
    let lowerCaseLetters = /[a-z]/g;
    if(myInput.val().match(lowerCaseLetters)) {
    letter.removeClass("invalid");
    letter.addClass("valid");
} else {
    letter.removeClass("valid");
    letter.addClass("invalid");
}

// Validate capital letters
    let upperCaseLetters = /[A-Z]/g;
    if(myInput.val().match(upperCaseLetters)) {
    capital.removeClass("invalid");
    capital.addClass("valid");
} else {
    capital.removeClass("valid");
    capital.addClass("invalid");
}

// Validate numbers
    let numbers = /[0-9]/g;
    if(myInput.val().match(numbers)) {
    number.removeClass("invalid");
    number.addClass("valid");
} else {
    number.removeClass("valid");
    number.addClass("invalid");
}

// Validate length
    if(myInput.val().length >= 8) {
    length.removeClass("invalid");
    length.addClass("valid");
} else {
    length.removeClass("valid");
    length.addClass("invalid");
}
});
});

//JS for add_product.html
$(function () {
    function validateForm() {
        let formInputs = $("form[name='productForm']").serializeArray();

        for (let i = 0; i < formInputs.length; i++) {
            let input = formInputs[i];
            if (input.value == "") {
                alert(input.name + " must be filled out");
                return false;
            }
        }
        return true;
    }
})
//JS for search field in top_bar.html
$(document).on('keydown', '#search', function(event) {
    const val = $(this).val();
    if (val.length > 1) {
        $.ajax({
            type: 'GET',
            url: `/search?value=${val}`,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 6000
        })
            .done(function(data) {
                $('.dropdown-toggle').dropdown();
                $('#dropdown-container').empty().append(data);
            })
            .fail(function(error) {
                console.log('ERROR:', error);
            });
    }
});




