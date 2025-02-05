package com.master.quizzfront.Api;

import com.master.quizzfront.Enum.UtilisateurStatut;
import com.master.quizzfront.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface AdminApi {
    @GET("/api/admin/pending-users")
    Call<List<Utilisateur>> getPendingUsers();

    @PUT("/api/admin/user/{userId}/status")
    Call<Utilisateur> updateUserStatus(
            @Path("userId") Integer userId,
            @Body UtilisateurStatut statut
    );
}
