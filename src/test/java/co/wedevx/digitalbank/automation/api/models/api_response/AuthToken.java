package co.wedevx.digitalbank.automation.api.models.api_response;

public class AuthToken {
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authToken='" + authToken + '\'' +
                '}';
    }
}

