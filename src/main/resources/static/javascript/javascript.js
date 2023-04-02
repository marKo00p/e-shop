$(function () {
    $('div.toggles div.theme-btn').on('click', function () {
        $('body').toggleClass('dark-mode')
        $('div.theme-btn span:first-child').toggleClass('active')
        $('div.theme-btn span:last-child').toggleClass('active')
    })
})

let currentActiveMenu = $('main aside div.sidebar div ul li a[href="' + location.pathname + '"]')
currentActiveMenu.addClass('active')

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






