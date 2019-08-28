package com.newsman.newsman.auxiliary;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;


import com.newsman.newsman.rest_connection.ConnectionStrategy.PutNewsTest;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.rest_connection.WriteJson.WriteUserPass;
import com.newsman.newsman.server_entities.UserWithPassword;

import java.util.List;

public class UserLoader extends AsyncTaskLoader<Void> {

    private UserWithPassword user;

    public UserLoader(@NonNull Context context, UserWithPassword user) {
        super(context);
        this.user = user;
        forceLoad();
    }

    @Nullable
    @Override
    public Void loadInBackground() {

        new RestConnector(new PutNewsTest(new WriteUserPass(user)), Constant.USER_ROUTE).run();
        return null;
    }
}
