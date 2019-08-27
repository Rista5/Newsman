package com.newsman.newsman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.server_entities.UserWithPassword;

public class LoginActivity3 extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        BackArrowHelper.displayBackArrow(this);
        setUpViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return BackArrowHelper.backArrowClicked(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpViews() {
        etUsername = findViewById(R.id.login_username);
        etPassword = findViewById(R.id.login_password);
    }

    private void setLoginButtonListener() {
        findViewById(R.id.login_request_btn).setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            if(validInput(username, password)){
                UserWithPassword userWithPassword = new UserWithPassword(username, password);
                //TODO implement login
            } else {
                Toast.makeText(this, R.string.login_invalid_toast, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validInput(String username, String password){
        return username != null && !username.equals("")
                && password != null && !password.equals("");
    }
}
