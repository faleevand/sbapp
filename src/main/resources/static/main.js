// $(document).ready(function () {
//
//     $('.nBtn, .table .eBtn').on('click', function (event) {
//        // event.preventDefault();
//         // var href = $(this).attr('href');
//         // var text = $(this).text(); //return New or Edit
//
//         $('#eexampleModal').modal;
//
//         // if (text === 'Edit') {
//         //     $.get(href, function (country, status) {
//         //         $('.myForm #username').val(user.username);
//         //         $('.myForm #name').val(country.name);
//         //         $('.myForm #capital').val(country.capital);
//         //     });
//         //     $('.myForm #eexampleModal').modal();
//         // } else {
//         {
//             $('.myForm #id').val('1');
//             $('.myForm #name').val('2');
//             $('.myForm #capital').val('3');
//             $('.myForm #eexampleModal').modal();
//         }
//     });
// });

// $('.table .delBtn').on('click', function (event) {
//     event.preventDefault();
//     var href = $(this).attr('href');
//     $('#myModal #delRef').attr('href', href);
//     $('#myModal').modal();
// });
// });

$(document).ready(function () {

    $('.nBtn, .table .btn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); //return New or Edit
        // $('.myForm, #eexampleModal').modal();

        //     if (text === 'Edit') {
                $.get(href, function (user, status) {
                    $('.myForm #id').val(user.id);
                    $('.myForm #username').val(user.username);
                    $('.myForm #password').val(user.password);
                    $('.myForm #surname').val(user.surname);
                    $('.myForm #age').val(user.age);
                    // $('.myForm #roles').val(user.roles.length);

                     });
               $('.myForm #eexampleModal').modal();
        //     } else {
        // $('.myForm #username').val('1');
        // $('.myForm #password').val('2');
        // $('.myForm #surname').val('3');
        // $('.myForm #age').val('4');
        // $('.myForm #eexampleModal').modal();

        // });
        //
        // $('.table .delBtn').on('click', function (event) {
        //     event.preventDefault();
        //     var href = $(this).attr('href');
        //     $('#myModal #delRef').attr('href', href);
        //     $('#myModal').modal();
        // });
    });
});