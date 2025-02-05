package com.master.quizzfront.Api;

import com.master.quizzfront.DTO.ReponseUtilisateurDTO;
import com.master.quizzfront.DTO.StartTentativeRequestDTO;
import com.master.quizzfront.DTO.TentativeDTO;
import com.master.quizzfront.Models.Tentative;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface TentativeApi {
    @POST("/api/tentative/start")
    Call<Tentative> startTentative(@Body StartTentativeRequestDTO request);

    @POST("/api/tentative/{tentativeId}/reponse")
    Call<Void> traiterReponse(
            @Path("tentativeId") Integer tentativeId,
            @Body ReponseUtilisateurDTO reponse
    );

    @GET("/api/tentative/{tentativeId}/score")
    Call<Integer> getScore(@Path("tentativeId") Integer tentativeId);

    @GET("/api/tentative/user/{userId}/history")
    Call<List<TentativeDTO>> getUserHistory(@Path("userId") Integer userId);
}