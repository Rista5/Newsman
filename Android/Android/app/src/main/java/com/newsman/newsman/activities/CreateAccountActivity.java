package com.newsman.newsman.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.auxiliary.UserLoader;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Post;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Put;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.rest_connection.WriteJson.WriteUserPass;
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

    private void setCreateAccountListener() {
        findViewById(R.id.create_account_send_request).setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPass = etConfirmPassword.getText().toString();
            if (validInput(username, password, confirmPass)) {
                new AsyncCreateAccLoader().execute(new UserWithPassword(Constant.INVALID_USER_ID, username, password));
            } else {
                Toast.makeText(this, R.string.create_account_invalid_toast, Toast.LENGTH_LONG).show();
            }
        });
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

    class AsyncCreateAccLoader extends AsyncTask<UserWithPassword, Void, Void> {

        @Override
        protected Void doInBackground(UserWithPassword... userWithPasswords) {
            if (userWithPasswords.length == 0) return null;
            new RestConnector(new Put(getApplicationContext(), new WriteUserPass(userWithPasswords[0])), Constant.USER_ROUTE).run();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(LoginState.getInstance().getUser().getId() != Constant.INVALID_USER_ID){
                Toast.makeText(getApplicationContext(), R.string.create_account_success_toast, Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(getApplicationContext(), R.string.create_account_fail_toast, Toast.LENGTH_LONG).show();
        }
    }
}

//
//    private void createLoader(UserWithPassword user) {
//        Bundle data = new Bundle();
//        data.putParcelable(Constant.USER_BUNDLE_KEY, user);
//        LoaderManager loaderManager = LoaderManager.getInstance(this);
//        Loader<Void> loader = loaderManager.getLoader(Constant.USER_LOADER);
//
//        if (loader == null) {
//            loader = loaderManager.initLoader(Constant.USER_LOADER, data, this);
//        } else {
//            loader = loaderManager.restartLoader(Constant.USER_LOADER, data, this);
//        }
//        loader.startLoading();
//    }

//    @NonNull
//    @Override
//    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
//        UserWithPassword user = args.getParcelable(Constant.USER_BUNDLE_KEY);
//        return new UserLoader(getApplicationContext(), user);
//    }
//
//    @Override
//    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {
//        if(Constant.getThisUser().getId() != Constant.INVALID_USER_ID){
//            Toast.makeText(this, R.string.create_account_success_toast, Toast.LENGTH_LONG).show();
//            finish();
//        }
//        else
//            Toast.makeText(this, R.string.create_account_fail_toast, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull Loader<Void> loader) {
//
//    }

