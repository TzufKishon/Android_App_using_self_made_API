package com.example.apiproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Toast;
import com.example.sdk.PasswordAPI;
import com.example.sdk.PasswordController;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PasswordController.PasswordResponseListener {

    private EditText passwordInput;
    private TextView resultText;
    private ImageButton checkPasswordButton, copyButton;

    private PasswordController passwordController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordInput = findViewById(R.id.passwordInput);
        resultText = findViewById(R.id.resultText);
        checkPasswordButton = findViewById(R.id.checkPasswordButton);
        copyButton = findViewById(R.id.copyButton);

        passwordController = new PasswordController(this);

        checkPasswordButton.setOnClickListener(view -> {
            String password = passwordInput.getText().toString();
            if (!password.isEmpty()) {
                passwordController.generatePassword(password);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
            }
        });

        copyButton.setOnClickListener(view -> {
            // Get the password text from the resultText TextView
            String passwordToCopy = resultText.getText().toString();

            // Check if there's actually something to copy
            if (!passwordToCopy.isEmpty()) {
                // Get the ClipboardManager service
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                // Create a ClipData holding the password text
                ClipData clip = ClipData.newPlainText("password", passwordToCopy);
                // Set the ClipData to the clipboard
                clipboard.setPrimaryClip(clip);
                // Show a confirmation toast
                Toast.makeText(MainActivity.this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
            } else {
                // Show a warning toast if there's nothing to copy
                Toast.makeText(MainActivity.this, "No password to copy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPasswordReceived(String password) {
        resultText.setText(password);
        // Copy to clipboard logic can also be moved here if desired
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
