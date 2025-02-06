package com.master.quizzfront.Api;

import com.master.quizzfront.DTO.UtilisateurDTO;
import com.master.quizzfront.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UtilisateurApi {

    @GET("/api/utilisateur/all")
    Call<List<UtilisateurDTO>> getAllUsers();

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

