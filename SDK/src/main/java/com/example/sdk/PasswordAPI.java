package com.example.sdk;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PasswordAPI {

    @POST("/generate_password")
    Call<PasswordResponse> generatePassword(@Body PasswordRequest password);

    // Updated to reflect the new endpoint without an ID
    @GET("/get_password")
    Call<PasswordResponse> getLatestPassword(); // Method name updated for clarity

    class PasswordRequest {
        final String password;

        public PasswordRequest(String password) {
            this.password = password;
        }
    }

    class PasswordResponse {
        String password;
        // Removed the id field as it's no longer used
        // If there's an error message instead of a password, consider adding that field here as well.

        public String getPassword() {
            return password;
        }

        // Removed getId method as the id field is removed
    }
}
