package apiKoel.responses;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("errorMessage")
    private String errorMessage;
    // No-argument constructor (required for Jackson)
    public LoginResponse() {
    }

    // Constructor with arguments
    public LoginResponse(String token, String errorMessage) {
        this.token = token;
        this.errorMessage = errorMessage;
    }

    // Getter for token
    public String getToken() {
        return token;
    }

    // Setter for token
    public void setToken(String token) {
        this.token = token;
    }

    // Getter for errorMessage
    public String getErrorMessage() {
        return errorMessage;
    }

    // Setter for errorMessage
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
