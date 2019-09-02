package com.newsman.newsman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.server_entities.UserWithPassword;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        BackArrowHelper.displayBackArrow(this);
        setUpViews();
        setCreateAccountListener();
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

    private void setCreateAccountListener(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();
        if(validInput(username, password, confirmPass)) {
            //TODO create account
            UserWithPassword user = new UserWithPassword(Constant.INVALID_USER_ID, username, password);

        } else {
            Toast.makeText(this, R.string.create_account_invalid_toast, Toast.LENGTH_LONG).show();
        }
    }

    private boolean validInput(String username, String password, String confirmPass) {
        return username != null && !username.equals("") && password != null
                && !password.equals("") && confirmPass != null && confirmPass.equals(password);
    }

    private void setUpViews() {
        etUsername = findViewById(R.id.create_account_username);
        etPassword = findViewById(R.id.create_account_password);
        etConfirmPassword = findViewById(R.id.create_account_confirm_password);
    }

}
