package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.derekhayes.vacationplanner.R;

public class MainActivity extends AppCompatActivity {

    public static int numAlert;
    SharedPreferences encryptedSharedPreferences;
    boolean isNewAccount = false;
    Button startButton;
    String username;
    String password;
    TextInputEditText usernameView;
    TextInputEditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // setup EncryptedSharedPreferences
        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "secure_prefs",
                    masterKeyAlias,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }

        // set views
        startButton = findViewById(R.id.start_button);
        usernameView = findViewById(R.id.username_input_edit);
        passwordView = findViewById(R.id.password_input_edit);

        // check if no existing account
        checkIfNewAccount();

        startButton.setOnClickListener(view -> onClickGetStarted());

    }

    public void onResume() {
        super.onResume();
        findViewById(R.id.login_error_text).setVisibility(View.GONE);
        usernameView.setText("");
        passwordView.setText("");
        usernameView.clearFocus();
        passwordView.clearFocus();
        checkIfNewAccount();
    }

    private void checkIfNewAccount() {
        if (encryptedSharedPreferences.getString("username", "defaultUser").equals("defaultUser")) {
            isNewAccount = true;
            startButton.setText(R.string.create_account);
        }
        else {
            isNewAccount = false;
            startButton.setText(R.string.log_in);
        }
    }

    public void onClickGetStarted() {

        TextView loginError = findViewById(R.id.login_error_text);

        if (usernameView.length() > 0 && passwordView.length() > 0) {
            username = usernameView.getText().toString().trim();
            password = passwordView.getText().toString().trim();
        }

        if (isNewAccount) {
            if (validatePassword(password)) {
                // set new username and password
                SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();

                startActivity(new Intent(this, VacationListActivity.class));
            }
            else {
                loginError.setText(R.string.validate_password);
                loginError.setVisibility(View.VISIBLE);
            }
        }
        else if (encryptedSharedPreferences.getString("username", "defaultUser")
                .equals(username) && encryptedSharedPreferences.getString("password", "defaultPass").equals(password)) {

            startActivity(new Intent(this, VacationListActivity.class));
        }
        else {
            loginError.setVisibility(View.VISIBLE);
        }
    }

    public boolean validatePassword(String password) {

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);

        if (password == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}