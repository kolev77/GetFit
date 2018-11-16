let constants = {
    serviceUrl: "http://127.0.0.1:8000"
};

app.router.on("#/", null, function () {
    if (app.authorizationService.isAuthorized()) {
        app.router.redirect('#/home');
        return;
    }
    app.templateLoader.loadTemplate('.app', 'guest-home', function () {
        $('.custom-btn').click(function (ev) {
                ev.preventDefault();
                if (ev['currentTarget']['className'].indexOf("reg-client-btn") > 0) {

                    app.router.redirect('#/client/register');
                }
                else if (ev['currentTarget']['className'].indexOf("reg-coach-btn") > 1) {
                    app.router.redirect('#/coach/register');
                }
            }
        );
    })
    ;
});
let allUserImages;

let allExercisesImages;

app.router.on("#/coach/profile", null, function () {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/');
        return;
    }

    let username = localStorage.getItem("username");
    let profilePictureUrl = localStorage.getItem("profilePictureUrl");

    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/coach/profile?username=' + username,
        success: function (data) {
            app.templateLoader.loadTemplate('.app', 'coach/coach-profile', function () {

                $('.myProfile').append(
                    '<div class="col-md-5 client-container">'
                    + '<div>'
                    + '<img src="' + profilePictureUrl + '" class="myProfilePicture">'
                    + '</div>'
                    + '<a class="user-data" href="#/coach/details?id=' + data['id'] + '"/>'
                    + '<div class="user-data">'
                    + '<img src="assets/user.png" class="profile-ico">'
                    + '<p>' + data['username'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/phone.png" class="profile-ico">'
                    + '<p>' + data['phoneNumber'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/email.png" class="profile-ico">'
                    + '<p>' + data['email'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/description.png" class="profile-ico">'
                    + '<p>' + data['description'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/certificates.png" class="profile-ico">'
                    + '<p>' + data['certificates'] + '</p>'
                    + '</div>'
                    + '</div>'
                    + '</a>');


            });

        }
    });
});

app.router.on("#/client/profile", null, function () {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/');
        return;
    }

    let username = localStorage.getItem("username");
    let profilePictureUrl = localStorage.getItem("profilePictureUrl");

    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/client/profile?username=' + username,
        success: function (data) {

            let clientData = JSON.parse(data[0]);
            let coachData = JSON.parse(data[1]);

            app.templateLoader.loadTemplate('.app', 'client/client-profile', function () {

                $('.myProfile').append(
                    '<div class="col-md-5 client-container">'
                    + '<div>'
                    + '<img src="' + profilePictureUrl + '" class="myProfilePicture">'
                    + '</div>'
                    + '<div class="user-data">'
                    + '<img src="assets/user.png" class="profile-ico">'
                    + '<p>' + clientData['username'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/phone.png" class="profile-ico">'
                    + '<p>' + clientData['phoneNumber'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/email.png" class="profile-ico">'
                    + '<p>' + clientData['email'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/description.png" class="profile-ico">'
                    + '<p>' + clientData['description'] + '</p>'
                    + '</div>'
                    + '</div>'
                    + '</div>');

                if (coachData.length > 0) {
                    $('.myCoaches').append('<h1 class="text-center">Your coaches : </h1>');
                    $('.myCoaches').append('<hr/>');
                    $('.myCoaches').append('<div class="row coaches-row">');
                    for (let coach of coachData) {
                        let coachPicture = localStorage.getItem(coach['profilePictureFileName']);

                        $('.coaches-row ').append(
                            '<div class="col-md-2 client-container">'
                            + '<a class="details-link" href="#/coach/details?username=' + coach['username'] + '">'
                            + '<div>'
                            + '<img src="' + coachPicture + '" class="profilePicture">'
                            + '</div>'
                            + '<div class="user-data">'
                            + '<img src="assets/user.png" class="profile-ico">'
                            + '<p>' + coach['username'] + '</p>'
                            + '</div>'
                            + '<div class="user-data mt-2">'
                            + '<img src="assets/phone.png" class="profile-ico">'
                            + '<p>' + coach['phoneNumber'] + '</p>'
                            + '</div>'
                            + '</div>'
                            + '</a>'
                            + '</div>');
                    }
                    $('.coaches').append('</div>');
                }


            });

        }
    });
});

function addCoach(ev) {
    ev.preventDefault();

    let clientName = localStorage.getItem("username");
    let coachName = localStorage.getItem("coachName");


    let usersData = JSON.stringify({
        "coachName": coachName,
        "clientName": clientName,
    });

    $.ajax({
        type: 'POST',
        url: constants.serviceUrl + '/client/add-coach',
        data: usersData,
        cache: false,
        processData: false,
        contentType: "application/json",
        success: function () {
            localStorage.removeItem("coachName");
            app.router.redirect('#/');
        }
    });
}

app.router.on('#/client/details', ['username'], function (username) {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/');
        return;
    }
    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/client/details?username=' + username,
        success: function (data) {


            let imgUrl = allUserImages.filter(i => i[0] == data['profilePictureFileName'])[0][1];

            app.templateLoader.loadTemplate('.app', 'client/client-details', function () {

                $('.clientDetails').append(
                    '<div class="col-md-5 client-container">'
                    + '<div>'
                    + '<img src="' + imgUrl + '" class="myProfilePicture">'
                    + '</div>'
                    + '<a class="user-data" href="#/coach/details?id=' + data['id'] + '"/>'
                    + '<div class="user-data">'
                    + '<img src="assets/user.png" class="profile-ico">'
                    + '<p class="coach-name">' + data['username'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/phone.png" class="profile-ico">'
                    + '<p>' + data['phoneNumber'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/email.png" class="profile-ico">'
                    + '<p>' + data['email'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/description.png" class="profile-ico">'
                    + '<p>' + data['description'] + '</p>'
                    + '</div>'
                    + '</div>'
                    + '</a>');

            });
        }
    });
});

app.router.on('#/coach/details', ['username'], function (username) {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/');
        return;
    }
    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/coach/details?username=' + username,
        success: function (data) {
            localStorage.setItem("coachName", data['username']);

            let imgUrl = allUserImages.filter(i => i[0] == data['profilePictureFileName'])[0][1];

            app.templateLoader.loadTemplate('.app', 'coach/coach-details', function () {

                $('.coachDetails').append(
                    '<div class="col-md-5 client-container">'
                    + '<div>'
                    + '<img src="' + imgUrl + '" class="myProfilePicture">'
                    + '</div>'
                    + '<a class="user-data" href="#/coach/details?id=' + data['id'] + '"/>'
                    + '<div class="user-data">'
                    + '<img src="assets/user.png" class="profile-ico">'
                    + '<p class="coach-name">' + data['username'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/phone.png" class="profile-ico">'
                    + '<p>' + data['phoneNumber'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/email.png" class="profile-ico">'
                    + '<p>' + data['email'] + '</p>'
                    + '</div>'
                    + '<div class="user-data mt-2">'
                    + '<img src="assets/description.png" class="profile-ico">'
                    + '<p>' + data['description'] + '</p>'
                    + '</div>'
                    + '</div>'
                    + '</a>');


                if (data['clientsNames'].indexOf(localStorage.getItem("username")) < 0) {

                    $('.coachDetails').append(
                        '<div>'
                        + '<form>'
                        + ' <button id="subscribe-button" type="button" class="btn btn-primary" onclick="addCoach(event)">Subscribe</button>'
                        + '</div>'
                        + '</form>'
                    );
                }

            });
        }
    });
});

app.router.on("#/home", null, function () {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/');
        return;
    }

    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/home',
        success: function (data) {

            let users = JSON.parse(data[0]);
            let role = JSON.parse(data[1])[0]['role'];
            let images = JSON.parse(data[2]);
            let loggedUserPictureId = data[3];
            allUserImages = images;

            localStorage.setItem("profilePictureUrl", allUserImages.filter(i => i[0] == data[3])[0][1]);
            localStorage.setItem("r", role);

            if (role == "ROLE_COACH") {
                app.templateLoader.loadTemplate('.app', 'coach/coach-home', function () {

                    $('.clients').append('<div class="row clients-row">');
                    for (let client of users) {
                        let imageUrl = allUserImages.filter(i => i[0] == client['profilePictureFileName'])[0][1];

                        $('.clients-row ').append(
                            '<div class="col-md-2 client-container">'
                            + '<a class="details-link" href="#/client/details?username=' + client['username'] + '">'
                            + '<div>'
                            + '<img src="' + imageUrl + '" class="profilePicture">'
                            + '</div>'
                            + '<div class="user-data">'
                            + '<img src="assets/user.png" class="profile-ico">'
                            + '<p>' + client['username'] + '</p>'
                            + '</div>'
                            + '<div class="user-data mt-2">'
                            + '<img src="assets/phone.png" class="profile-ico">'
                            + '<p>' + client['phoneNumber'] + '</p>'
                            + '</div>'
                            + '</div>'
                            + '</a>');
                    }

                    $('.clients').append('</div>');

                });
            } else if (role === "ROLE_CLIENT") {
                app.templateLoader.loadTemplate('.app', 'client/client-home', function () {

                    $('.coaches').append('<div class="row coaches-row">');
                    for (let coach of users) {
                        let imageUrl = allUserImages.filter(i => i[0] == coach['profilePictureFileName'])[0][1];

                        $('.coaches-row ').append(
                            '<div class="col-md-2 client-container">'
                            + '<a class="details-link" href="#/coach/details?username=' + coach['username'] + '">'
                            + '<div>'
                            + '<img src="' + imageUrl + '" class="profilePicture">'
                            + '</div>'
                            + '<div class="user-data">'
                            + '<img src="assets/user.png" class="profile-ico">'
                            + '<p>' + coach['username'] + '</p>'
                            + '</div>'
                            + '<div class="user-data mt-2">'
                            + '<img src="assets/phone.png" class="profile-ico">'
                            + '<p>' + coach['phoneNumber'] + '</p>'
                            + '</div>'
                            + '<div class="user-data mt-2">'
                            + '<p>' + coach['subscribers'] + '</p>'
                            + '</div>'
                            + '</div>'
                            + '</a>'
                            + '</div>');
                    }
                    $('.coaches').append('</div>');
                });
            }
        },
        error: function (xhr) {
            //Do Something to handle error
        }
    })
});

app.router.on("#/login", null, function () {
    localStorage.clear();
    if (app.authorizationService.isAuthorized()) {
        app.router.redirect('#/home');
        return;
    }

    app.templateLoader.loadTemplate('.app', 'login', function () {
        $('#login-button').click(function (ev) {
            ev.preventDefault();

            let username = $('#username').val();
            let password = $('#password').val();

            localStorage.setItem("username", username);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8000/login',
                headers: {
                    "Content-Type": "application/json"
                },
                data: JSON.stringify({
                    "username": username,
                    "password": password
                })
            }).done(function (body) {

                let auth = body["Authorization"];
                app.authorizationService.setAuth(auth);
                app.router.redirect('#/home');

            }).fail(function (xhr, status, error) {
                localStorage.clear();
                new Noty({
                    text: 'ERROR: There was an error with your login.',
                    layout: 'topCenter',
                    type: 'error',
                    theme: 'mint',
                    timeout: 3000
                }).show();
            });
        });

    });
});

app.router.on("#/client/register", null, function () {
    if (app.authorizationService.isAuthorized()) {
        app.router.redirect('#/home');
        return;
    }

    app.templateLoader.loadTemplate('.app', 'register-client', function () {

        $('.custom-btn').click(function (ev) {
                ev.preventDefault();
                if (ev['currentTarget']['className'].indexOf("reg-client-btn") > 0) {

                    app.router.redirect('#/client/register');
                }
                else if (ev['currentTarget']['className'].indexOf("reg-coach-btn") > 1) {
                    app.router.redirect('#/coach/register');
                }
            }
        );

        $('#register-button').click(function (ev) {
            ev.preventDefault();

            let username = $('#username').val();
            let password = $('#password').val();
            let email = $('#email').val();
            let phoneNumber = $('#phoneNumber').val();
            let description = $('#description').val();
            let profilePicture = $('#profilePicture')[0]['files'][0];
            let userJson = JSON.stringify({
                "username": username,
                "password": password,
                "email": email,
                "phoneNumber": phoneNumber,
                "description": description
            });
            let userData = new FormData();
            userData.append("profilePicture", profilePicture);
            userData.append("userInfo", userJson);

            $.ajax({
                type: 'POST',
                url: constants.serviceUrl + '/client/register',
                data: userData,
                cache: false,
                processData: false,
                contentType: false
            }).done(function (data) {
                app.router.redirect('#/login');
            }).fail(function (xhr, status, error) {
                new Noty({
                    text: 'ERROR [' + xhr['status'] + ']: ' + xhr['responseText'],
                    layout: 'topCenter',
                    type: 'error',
                    theme: 'mint',
                    timeout: 3000
                }).show();
            });
        });
    });
});

app.router.on("#/coach/register", null, function () {
    if (app.authorizationService.isAuthorized()) {
        app.router.redirect('#/home');
        return;
    }

    app.templateLoader.loadTemplate('.app', 'register-coach', function () {
        $('.custom-btn').click(function (ev) {
                ev.preventDefault();
                if (ev['currentTarget']['className'].indexOf("reg-client-btn") > 0) {

                    app.router.redirect('#/client/register');
                }
                else if (ev['currentTarget']['className'].indexOf("reg-coach-btn") > 1) {
                    app.router.redirect('#/coach/register');
                }
            }
        );

        $('#register-button').click(function (ev) {
            ev.preventDefault();

            let username = $('#username').val();
            let password = $('#password').val();
            let email = $('#email').val();
            let phoneNumber = $('#phoneNumber').val();
            let description = $('#description').val();
            let certificates = $('#certificates').val();
            let profilePicture = $('#profilePicture')[0]['files'][0];

            // // compressing
            // let quality = 30,
            // output_format = 'jpg',
            // asd = jic.compress(profilePicture, quality, output_format).src;
            //
            // console.log(asd);
            // console.log("etoot");

            let userJson = JSON.stringify({
                "username": username,
                "password": password,
                "email": email,
                "phoneNumber": phoneNumber,
                "description": description,
                "certificates": certificates
            });

            let userData = new FormData();
            userData.append("profilePicture", profilePicture);
            userData.append("userInfo", userJson);
            $.ajax({
                type: 'POST',
                url: constants.serviceUrl + '/coach/register',
                data: userData,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    app.router.redirect('#/login')
                }
            })
        }).fail(function (xhr, status, error) {
            new Noty({
                text: 'ERROR [' + xhr['status'] + ']: ' + xhr['responseText'],
                layout: 'topCenter',
                type: 'error',
                theme: 'mint',
                timeout: 3000
            }).show();
        });
    });
});

app.router.on("#/coach/create-exercise", null, function () {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/home');
        return;
    }

    app.templateLoader.loadTemplate('.app', 'coach/create-exercise', function () {


        $('.createExerciseBtn').click(function (ev) {
            ev.preventDefault();

            let exerciseName = $('#name').val();
            let creatorName = localStorage.getItem("username");
            let description = $('#description').val();
            let levelOfDifficulty = $('.exerciseDiff.active input[type="radio"]').val();
            let muscleGroup = $('.muscleGr.active input[type="radio"]').val();
            let images;

            if (parseInt($('#images').get(0).files.length) > 3 || parseInt($('#images').get(0).files.length) < 3) {
                alert("You need to upload exactly 3 photos !")
            } else {
                images = $('#images')[0]['files'];
            }

            let exerciseJson = JSON.stringify({
                "exerciseName": exerciseName,
                "creatorName": creatorName,
                "description": description,
                "levelOfDifficulty": levelOfDifficulty,
                "muscleGroup": muscleGroup,
            });

            let exerciseData = new FormData();
            exerciseData.append("exerciseInfo", exerciseJson);
            exerciseData.append("image1", images[0]);
            exerciseData.append("image2", images[1]);
            exerciseData.append("image3", images[2]);

            $.ajax({
                type: 'POST',
                url: constants.serviceUrl + '/coach/create-exercise',
                data: exerciseData,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    app.router.redirect('#/login')
                }
            });


        })
    });

});

app.router.on("#/exercises/all", null, function () {
    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/exercises/all',
        success: function (data) {
            let exercises = JSON.parse(data[0]);
            let exercisesPhotos = JSON.parse(data[1]);
            allExercisesImages = exercisesPhotos;

            app.templateLoader.loadTemplate('.app', 'exercise/exercises-all', function () {

                $('.exercises').append('<div class="row exercises-row">');
                for (let exercise of exercises) {
                    let regex = /\,"(.*?).jpg"/gm;
                    let photoName = regex.exec(exercise['photosInfo'])[1] + '.jpg';

                    let imageUrl = allUserImages.filter(i => i[0] == photoName)[0][1];

                    $('.exercises-row').append(
                        '<div class="col-md-2 client-container">'
                        + '<a class="details-link" href="#/exercises/details?exercisename=' + exercise['exerciseName'] + '">'
                        + '<div>'
                        + '<img src="' + imageUrl + '" class="profilePicture">'
                        + '</div>'
                        + '<div class="user-data">'
                        + '<img src="assets/user.png" class="profile-ico">'
                        + '<p>' + exercise['exerciseName'] + '</p>'
                        + '</div>'
                        + '<div class="user-data mt-2">'
                        + '<img src="assets/phone.png" class="profile-ico">'
                        + '<p>' + exercise['levelOfDifficulty'] + '</p>'
                        + '</div>'
                        + '<div class="user-data mt-2">'
                        + '<img src="assets/phone.png" class="profile-ico">'
                        + '<p>' + exercise['muscleGroup'] + '</p>'
                        + '</div>'
                        + '</div>'
                        + '</a>');
                }

                $('.exercises').append('</div>');

            });
        }
    })
    ;
});

app.router.on('#/exercises/details', ['exercisename'], function (exercisename) {
    if (!app.authorizationService.isAuthorized()) {
        app.router.redirect('#/');
        return;
    }
    $.ajax({
        type: 'GET',
        headers: {
            'Authorization': app.authorizationService.getAuth()
        },
        url: constants.serviceUrl + '/exercises/details?exercisename=' + exercisename,
        success: function (data) {
            console.log(data);
            let photos = Object.keys(JSON.parse(data['photosInfo']));
            let exerciseName = data['exerciseName'];
            let creatorName = data['creatorName'];
            let description = data['description'];
            let levelOfDifficulty = data['levelOfDifficulty'];
            let muscleGroup = data['muscleGroup'];

            //TODO : finish the slider and go to sleep

            app.templateLoader.loadTemplate('.app', 'exercise/exercise-details', function () {
                let i = 0;
                for (let photo of photos) {
                    let photoUrl = allExercisesImages.filter(i => i[0] === photo)[0][1];
                    if (i == 0) {
                        $('.exercise-slider-image')
                            .append('<div class="carousel-item active">'
                                + '<img class="d-block" src="' + photoUrl + '">'
                                + '</div>');
                    } else {
                        $('.exercise-slider-image')
                            .append('<div class="carousel-item">'
                                + '<img class="d-block" src="' + photoUrl + '">'
                                + '</div>');
                    }
                    i++;
                }

            });
        }
    });
});


window.location.href = '#/';