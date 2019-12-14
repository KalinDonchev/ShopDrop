// $('#myForm').validator()
//
// // SELECTING ALL TEXT ELEMENTS
// const username = document.forms['vform']['username'];
// const email = document.forms['vform']['email'];
// const password = document.forms['vform']['password'];
// const password_confirm = document.forms['vform']['password_confirm'];
// // SELECTING ALL ERROR DISPLAY ELEMENTS
// const name_error = document.getElementById('name_error');
// const email_error = document.getElementById('email_error');
// const password_error = document.getElementById('password_error');
// // SETTING ALL EVENT LISTENERS
// username.addEventListener('blur', nameVerify, true);
// email.addEventListener('blur', emailVerify, true);
// password.addEventListener('blur', passwordVerify, true);
// // validation function
// /**
//  * @return {boolean}
//  */
// function validate() {
//     // validate username
//     if (username.value === "") {
//         username.style.border = "1px solid red";
//         document.getElementById('username_div').style.color = "red";
//         name_error.textContent = "Username is required";
//         username.focus();
//         return false;
//     }
//     // validate username
//     if (username.value.length < 3) {
//         username.style.border = "1px solid red";
//         document.getElementById('username_div').style.color = "red";
//         name_error.textContent = "Username must be at least 3 characters";
//         username.focus();
//         return false;
//     }
//     // validate email
//     if (email.value === "") {
//         email.style.border = "1px solid red";
//         document.getElementById('email_div').style.color = "red";
//         email_error.textContent = "Email is required";
//         email.focus();
//         return false;
//     }
//     // validate password
//     if (password.value === "") {
//         password.style.border = "1px solid red";
//         document.getElementById('password_div').style.color = "red";
//         password_confirm.style.border = "1px solid red";
//         password_error.textContent = "Password is required";
//         password.focus();
//         return false;
//     }
//     // check if the two passwords match
//     if (password.value !== password_confirm.value) {
//         password.style.border = "1px solid red";
//         document.getElementById('pass_confirm_div').style.color = "red";
//         password_confirm.style.border = "1px solid red";
//         password_error.innerHTML = "The two passwords do not match";
//         return false;
//     }
// }
// // event handler functions
// function nameVerify() {
//     if (username.value !== "") {
//         username.style.border = "1px solid #5e6e66";
//         document.getElementById('username_div').style.color = "#5e6e66";
//         name_error.innerHTML = "";
//         return true;
//     }
// }
// function emailVerify() {
//     if (email.value !== "") {
//         email.style.border = "1px solid #5e6e66";
//         document.getElementById('email_div').style.color = "#5e6e66";
//         email_error.innerHTML = "";
//         return true;
//     }
// }
// function passwordVerify() {
//     if (password.value !== "") {
//         password.style.border = "1px solid #5e6e66";
//         document.getElementById('pass_confirm_div').style.color = "#5e6e66";
//         document.getElementById('password_div').style.color = "#5e6e66";
//         password_error.innerHTML = "";
//         return true;
//     }
//     if (password.value === password_confirm.value) {
//         password.style.border = "1px solid #5e6e66";
//         document.getElementById('pass_confirm_div').style.color = "#5e6e66";
//         password_error.innerHTML = "";
//         return true;
//     }
// }


const categoryUrl = '/category/fetch';
// const productUrlAll = 'http://localhost:8000/product/fetch/all';
const productUrl = '/product/fetch/';
//console.log("over!");

$(document).ready(function () {
    $("#dropdown-menu").on('click', "a", function () {
        console.log("dropdown!");
        let category = $(this).text();
        fetch(productUrl + category)
            .then((response) => response.json())
            .then((json) => {
                $('#products-list').empty();

                if (json.length === 0) {
                    $('#products-list')
                        .append(`<h1 class="text-center font-weight-bold">There are no products in the ${category} category.</h1>`)
                } else {
                    for (let i = 0; i < json.length; i++) {
                        $('#products-list').append('<li class="list-group-item">');
                        if (i < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i]));
                        $('#products-list').append('</li>');
                    }
                }
            });
    });
});

fetch(categoryUrl)
    .then((response) => response.json())
    .then((json) => {
        json.forEach((category) => $('#category-menu').append(`<option value="${category.id}">${category.name}</option>`));
    })
    .catch((err) => console.log(err));


fetch(categoryUrl)
    .then((response) => response.json())
    .then((json) => {
        json.forEach((category) => $('#dropdown-menu').append(`<a class="dropdown-item">${category.name}</a>`));
    })
    .catch((err) => console.log(err));

const formatProduct = (product) => {
    return `<div class="media align-items-lg-center flex-column flex-lg-row p-3">`
        + `<div class="media-body order-2 order-lg-1">`
        + `<h5 class="mt-0 font-weight-bold mb-2">${product.name}</h5>`
        + `<h5 class="font-italic text-muted mb-0 small">${product.description}</h5>`
        + `<div class="d-flex align-items-center justify-content-between mt-1">`
        + `<h6 class="font-weight-bold my-2"><span>${product.price.toFixed(2)}$</h6>`
        + `<a class="text-gray" href="/product/all/${product.user}">${product.user}</a>`
        + `</div>`
        + `</div><a id="none" href="/product/details/${product.id}"><img\n` +
        `                                            src="${product.imageUrl}"\n` +
        `                                            alt="Generic placeholder image" width="200" height="150" class="ml-lg-5 order-1 order-lg-2"> </a>`
        + `</div>`

};


// $(document).ready(function () {
//
// });

