package com.newsman.newsman.new_rest.retrofit_services;

import com.newsman.newsman.new_rest.dtos.*;

import retrofit2.*;
import retrofit2.http.*;

public interface UserService {

    @GET("User/{id}")
    Call<UserDTO> getUserByID(@Path("id") int userId);

    @PUT("User/")
    Call<UserWithPasswordDTO> putUser(@Body UserWithPasswordDTO user);
}
