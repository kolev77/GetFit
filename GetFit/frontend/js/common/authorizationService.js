var app = app || {};

app.authorizationService = (function () {
    let authToken = null;

    return {
        getAuth: function () {
            return authToken;
        },
        setAuth: function (auth) {
            authToken = auth;
        },
        isAuthorized: function () {
            return authToken != null;
        },
        clearAuth: function () {
            authToken = null;
        }
    }
})();