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
                new LoginAsyncTask(this).execute(userWithPassword);
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

    static class LoginAsyncTask extends AsyncTask<UserWithPassword, Void, Void> {

        private WeakReference<LoginActivity> activityRefrence;

        LoginAsyncTask(LoginActivity context) {
            activityRefrence = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(UserWithPassword... userWithPasswords) {
            if(userWithPasswords.length == 0) return null;
            UserConnector.loadUser(userWithPasswords[0]).run();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            LoginActivity activity = activityRefrence.get();
//            if(activity == null || activity.isFinishing()) return;
            if(LoginState.getInstance().getUser().getId() != Constant.INVALID_USER_ID){
                Toast.makeText(activity, R.string.login_successful_toast, Toast.LENGTH_LONG).show();
                activity.finish();
            }
            else
                Toast.makeText(activity, R.string.create_account_fail_toast, Toast.LENGTH_LONG).show();
        }
    }
}
