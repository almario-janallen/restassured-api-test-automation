package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
    @JsonProperty("data")
    private UserData userData;
    private Support Support;

    public UserResponse() {
        //Default Constructor
    }

    public UserResponse(UserData userData, Support Support) {
        this.userData = userData;
        this.Support = Support;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Support getSupport() {
        return Support;
    }

    public void setSupport(Support Support) {
        this.Support = Support;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "data=" + userData +
                ",support=" + Support +
                '}';
    }
}
