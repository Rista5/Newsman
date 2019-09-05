package com.newsman.newsman.rest_connection.rest_connectors;

import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.model.dtos.UserWithPasswordDTO;
import com.newsman.newsman.rest_connection.retrofit_services.UserService;
import com.newsman.newsman.model.db_entities.UserWithPassword;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserConnector {
    public static Runnable createUser(final UserWithPassword user) {
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            UserService service = retrofit.create(UserService.class);
            Call<UserWithPasswordDTO> request = service.putUser(new UserWithPasswordDTO(user));
            try {
                Response<UserWithPasswordDTO> response = request.execute();
                UserWithPasswordDTO result = response.body();
                if(result != null)
                    LoginState.getInstance().setUser(UserWithPasswordDTO.getUserWithPassword(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable loadUser(final UserWithPassword user) {
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            UserService service = retrofit.create(UserService.class);
            Call<UserWithPasswordDTO> request = service.getUser(new UserWithPasswordDTO(user));
            try {
                Response<UserWithPasswordDTO> response = request.execute();
                UserWithPasswordDTO result = response.body();
                if(result != null)
                    LoginState.getInstance().setUser(UserWithPasswordDTO.getUserWithPassword(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
