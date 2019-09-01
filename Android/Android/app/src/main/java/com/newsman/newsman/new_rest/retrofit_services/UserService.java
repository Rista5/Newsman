package com.newsman.newsman.new_rest.retrofit_services;

import com.newsman.newsman.new_rest.dtos.*;
import com.newsman.newsman.server_entities.UserWithPassword;

import retrofit2.*;
import retrofit2.http.*;

public interface UserService {

    @GET("User")
    Call<UserWithPasswordDTO> getUser(@Body UserWithPasswordDTO user);

    @PUT("User/")
    Call<UserWithPasswordDTO> putUser(@Body UserWithPasswordDTO user);
}
