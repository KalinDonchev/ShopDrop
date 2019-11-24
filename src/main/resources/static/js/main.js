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
                    $('#products-list').append(`<h1 class="text-center font-weight-bold">There are no products in the ${category} category.</h1>`)
                } else {
                    // for (let i = 0; i < json.length; i += 3) {
                    //     $('#products-list').append('<li class="list-group-item">');
                    //     if (i < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i]));
                    //     if (i + 1 < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i + 1]));
                    //     if (i + 2 < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i + 2]));
                    // }
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
        + `<h6 class="font-weight-bold my-2"><span>${product.price}</h6>`
        + `<a class="text-gray" href="#">${product.user}</a>`
        + `</div>`
        + `</div><a id="none" href="/product/details/${product.id}"><img\n` +
        `                                            src="${product.imageUrl}"\n` +
        `                                            alt="Generic placeholder image" width="200" class="ml-lg-5 order-1 order-lg-2"> </a>`
        + `</div>`

};


// $(document).ready(function () {
//
// });


// fetch(productUrlAll)
//     .then((response) => response.json())
//     .then((json) => {
//         $('#products-list').empty();
//
//         if (json.length === 0) {
//             $('#products-list').append(`<h1 class="text-center font-weight-bold">There are no products in the ${category} category.</h1>`)
//         } else {
//             for (let i = 0; i < json.length; i += 3) {
//                 $('#products-list').append('<li class="list-group-item">');
//                 if (i < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i]));
//                 if (i + 1 < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i + 1]));
//                 if (i + 2 < json.length) $('#products-list .list-group-item:last-child').append(formatProduct(json[i + 2]));
//             }
//         }
//     });


// $(document).on("click", ".addSo", function(){
//     var newOption = "<option value=''>Dynamic Entry</option>";
//     $("#selectmenu").append(newOption).selectmenu('refresh');
// });