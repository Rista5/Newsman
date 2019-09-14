package com.newsman.newsman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.rest_connection.rest_connectors.UserConnector;
import com.newsman.newsman.model.db_entities.UserWithPassword;

import java.lang.ref.WeakReference;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        BackArrowHelper.displayBackArrow(this);
        setUpViews();
        findViewById(R.id.login_request_btn).setOnClickListener(view -> {
            setCreateAccountListener();
        });
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
            new CreateAccAsyncTask(this).execute(user);
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

    static class CreateAccAsyncTask extends AsyncTask<UserWithPassword, Void, Void> {

        private WeakReference<CreateAccountActivity> activityReference;

        CreateAccAsyncTask(CreateAccountActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(UserWithPassword... userWithPasswords) {
            if (userWithPasswords.length == 0) return null;
            UserConnector.createUser(userWithPasswords[0]).run();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            CreateAccountActivity activity = activityReference.get();
            if(activity == null || activity.isFinishing()) return;

            if(LoginState.getInstance().getUser().getId() != Constant.INVALID_USER_ID){
                Toast.makeText(activity, R.string.create_account_success_toast, Toast.LENGTH_LONG).show();
                activity.finish();
            }
            else
                Toast.makeText(activity, R.string.create_account_fail_toast, Toast.LENGTH_LONG).show();
        }
    }
}
