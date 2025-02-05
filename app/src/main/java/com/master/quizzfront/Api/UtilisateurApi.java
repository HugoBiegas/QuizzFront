package com.master.quizzfront.Api;

import com.master.quizzfront.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.*;

public interface UtilisateurApi {

    @GET("/api/utilisateur/{userId}")
    Call<Utilisateur> getUser(@Path("userId") Integer userId);

    @PUT("/api/utilisateur/{userId}")
    Call<Utilisateur> updateUser(
            @Path("userId") Integer userId,
            @Body Utilisateur utilisateur
    );

    @DELETE("/api/utilisateur/{userId}")
    Call<Void> deleteUser(@Path("userId") Integer userId);
}

