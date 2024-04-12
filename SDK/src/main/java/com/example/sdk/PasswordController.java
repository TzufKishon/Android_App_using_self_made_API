package com.example.sdk;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasswordController implements Callback<PasswordAPI.PasswordResponse> {

    static final String BASE_URL = "http://192.168.68.138:8089/"; // Adjusted to your Flask API

    public interface PasswordResponseListener {
        void onPasswordReceived(String password);
        void onFailed(String message);
    }

    private PasswordResponseListener listener;

    public PasswordController(PasswordResponseListener listener) {
        this.listener = listener;
    }

    public void generatePassword(String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PasswordAPI passwordAPI = retrofit.create(PasswordAPI.class);

        Call<PasswordAPI.PasswordResponse> call = passwordAPI.generatePassword(new PasswordAPI.PasswordRequest(password));
        call.enqueue(this);
    }

    public void getLatestPassword() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PasswordAPI passwordAPI = retrofit.create(PasswordAPI.class);

        Call<PasswordAPI.PasswordResponse> call = passwordAPI.getLatestPassword();
        call.enqueue(this); // This will use the same Callback implementation to handle the response
    }

    @Override
    public void onResponse(Call<PasswordAPI.PasswordResponse> call, Response<PasswordAPI.PasswordResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            listener.onPasswordReceived(response.body().getPassword());
        } else {
            listener.onFailed("Failed to generate or fetch password.");
        }
    }

    @Override
    public void onFailure(Call<PasswordAPI.PasswordResponse> call, Throwable t) {
        listener.onFailed("API call failed: " + t.getMessage());
    }
}
