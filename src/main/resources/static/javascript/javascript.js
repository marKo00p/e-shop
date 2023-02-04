$(function () {
    $('main aside div.sidebar div.toggles div.theme-btn').on('click', function () {
        $('body').toggleClass('dark-mode')
        $('div.theme-btn span:first-child').toggleClass('active')
        $('div.theme-btn span:last-child').toggleClass('active')
    })
})

let currentActiveMenu = $('main aside div.sidebar div ul li a[href="' + location.pathname + '"]')
currentActiveMenu.addClass('active')