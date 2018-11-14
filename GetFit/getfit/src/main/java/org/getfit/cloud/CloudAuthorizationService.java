package org.getfit.cloud;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CloudAuthorizationService {
    private static final String LOGIN_USERNAME = "rostislav.kolev77@gmail.com";

    private static final String LOGIN_PASSWORD = "700961pcloud";

    private static final String LOGIN_URL =
            "https://api.pcloud.com/userinfo?getauth=1&logout=1&username="
                    + LOGIN_USERNAME
                    + "&password="
                    + LOGIN_PASSWORD;

    private final HttpRequestExecutor httpRequestExecutor;

    private CloudAuthorizationCredentials credentials;

    public CloudAuthorizationService(HttpRequestExecutor httpRequestExecutor) {
        this.httpRequestExecutor = httpRequestExecutor;
    }

    public void login() throws IOException {
        String loginJsonResult = this.httpRequestExecutor.executeGetRequest(LOGIN_URL)
                .body()
                .string();

        this.credentials = new Gson().fromJson(loginJsonResult, CloudAuthorizationCredentials.class);
    }

    public String getAccessToken() throws IOException {
        if (this.credentials == null) this.login();

        return this.credentials.getAuth();
    }
}
