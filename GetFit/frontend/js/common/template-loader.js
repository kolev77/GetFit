var app = app || {};

function loadNavbar() {
    if (app.authorizationService.isAuthorized()) {

        if (localStorage.getItem("r")=="ROLE_CLIENT") {
            app.templateLoader.loadTemplate('.navbar-holder', 'navbar-client', function () {
                $('#logout-button').click(function (e) {
                    e.preventDefault();

                    app.authorizationService.clearAuth();
                    localStorage.clear();
                    app.router.redirect('#/');
                });
            });
        } else if (localStorage.getItem("r")=="ROLE_COACH"){
            app.templateLoader.loadTemplate('.navbar-holder', 'navbar-coach', function () {
                $('#logout-button').click(function (e) {
                    e.preventDefault();

                    app.authorizationService.clearAuth();
                    localStorage.clear();
                    app.router.redirect('#/');
                });
            });
        }
    } else {
        app.templateLoader.loadTemplate('.navbar-holder', 'navbar');
    }
}

app.templateLoader = (function templateLoader() {
    return {
        loadTemplate: function (element, template, callback) {
            $(element).empty();

            if (element === '.app') {
                loadNavbar();
            }

            $.get('fragments/' + template + '.html').then((template) => {
                $(element).html(template);
                if (callback) callback();
            }).catch((error) => {
                console.log("Error loading template!");
                console.log("Error: " + error);
            });
        }
    }
})();