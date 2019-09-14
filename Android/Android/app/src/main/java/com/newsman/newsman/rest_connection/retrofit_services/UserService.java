package com.newsman.newsman.rest_connection.retrofit_services;

import com.newsman.newsman.model.dtos.*;

import retrofit2.*;
import retrofit2.http.*;

public interface UserService {

    @POST("User")
    Call<UserWithPasswordDTO> getUser(@Body UserWithPasswordDTO user);

    @PUT("User/")
    Call<UserWithPasswordDTO> putUser(@Body UserWithPasswordDTO user);
}
