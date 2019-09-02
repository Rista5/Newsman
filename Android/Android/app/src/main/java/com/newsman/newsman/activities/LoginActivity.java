package com.newsman.newsman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.new_rest.UserConnector;
import com.newsman.newsman.server_entities.UserWithPassword;
import com.newsman.newsman.thread_management.AppExecutors;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BackArrowHelper.displayBackArrow(this);
        setUpViews();
        setLoginButtonListener();
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
                UserWithPassword userWithPassword = new UserWithPassword(Constant.INVALID_USER_ID, username, password);
                //TODO implement login
                AppExecutors.getInstance().getNetworkIO().execute(UserConnector.loadUser(userWithPassword));
                finish();
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
