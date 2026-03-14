package models;

public class LoginResponse {
    private String token;

    public LoginResponse() {
        // Default Constructor
    }

    public LoginResponse(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
