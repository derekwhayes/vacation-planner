package dev.derekhayes.vacationplanner.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

        // check if no existing account
        if (encryptedSharedPreferences.getString("username", "defaultUser").equals("defaultUser")) {
            isNewAccount = true;
            startButton.setText(R.string.create_account);
        }

        startButton.setOnClickListener(view -> onClickGetStarted());

    }

    public void onClickGetStarted() {
        usernameView = findViewById(R.id.username_input_edit);
        passwordView = findViewById(R.id.password_input_edit);

        if (usernameView.length() > 0 && passwordView.length() > 0) {
            username = usernameView.getText().toString();
            password = passwordView.getText().toString();
        }

        Log.d("MYTAG", "entered username: " + username);
        Log.d("MYTAG", "entered password: " + password);

        if (isNewAccount) {
            // set new username and password
            SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.apply();

            Log.d("MYTAG", "new username: " + username);
            Log.d("MYTAG", "new password" + password);
            startActivity(new Intent(this, VacationListActivity.class));
        }
        else if (encryptedSharedPreferences.getString("username", "defaultUser")
                .equals(username) && encryptedSharedPreferences.getString("password", "defaultPass").equals(password)) {

            Log.d("MYTAG", "stored username: " + encryptedSharedPreferences.getString("username", "defaultUser"));
            Log.d("MYTAG", "stored password" + encryptedSharedPreferences.getString("password", "defaultPass"));

            startActivity(new Intent(this, VacationListActivity.class));
        }
        else {
            findViewById(R.id.login_error_text).setVisibility(View.VISIBLE);
        }
    }
}