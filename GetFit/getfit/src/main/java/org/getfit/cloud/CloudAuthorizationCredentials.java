package org.getfit.cloud;

public class CloudAuthorizationCredentials {
    private String email;

    private String auth;

    public CloudAuthorizationCredentials() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
