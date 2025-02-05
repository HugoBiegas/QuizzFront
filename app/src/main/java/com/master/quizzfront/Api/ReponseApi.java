package com.master.quizzfront.Api;

import com.master.quizzfront.Models.Reponse;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface ReponseApi {
    @GET("/api/questionnaire/{questionnaireId}/question/{questionId}/reponse")
    Call<List<Reponse>> getReponses(
            @Path("questionnaireId") Integer questionnaireId,
            @Path("questionId") Integer questionId
    );

    @POST("/api/questionnaire/{questionnaireId}/question/{questionId}/reponse")
    Call<Reponse> addReponse(
            @Path("questionnaireId") Integer questionnaireId,
            @Path("questionId") Integer questionId,
            @Body Reponse reponse
    );

    @PUT("/api/questionnaire/{questionnaireId}/question/{questionId}/reponse/{reponseId}")
    Call<Reponse> updateReponse(
            @Path("questionnaireId") Integer questionnaireId,
            @Path("questionId") Integer questionId,
            @Path("reponseId") Integer reponseId,
            @Body Reponse reponse
    );

    @DELETE("/api/questionnaire/{questionnaireId}/question/{questionId}/reponse/{reponseId}")
    Call<Void> deleteReponse(
            @Path("questionnaireId") Integer questionnaireId,
            @Path("questionId") Integer questionId,
            @Path("reponseId") Integer reponseId
    );
}
