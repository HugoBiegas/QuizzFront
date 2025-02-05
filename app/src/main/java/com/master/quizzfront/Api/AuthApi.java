package com.master.quizzfront.Api;

import com.master.quizzfront.DTO.LoginRequestDTO;
import com.master.quizzfront.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.*;

public interface AuthApi {
    @POST("/api/auth/login")
    Call<Utilisateur> login(@Body LoginRequestDTO request);

    @POST("/api/auth/register")
    Call<Utilisateur> register(@Body Utilisateur utilisateur);
}
