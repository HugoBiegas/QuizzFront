package com.master.quizzfront.Api;

import com.master.quizzfront.DTO.QuestionnaireDTO;
import com.master.quizzfront.DTO.QuestionnaireDetailDTO;
import com.master.quizzfront.Models.Questionnaire;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface QuestionnaireApi {
    @GET("/api/questionnaire/{id}")
    Call<QuestionnaireDetailDTO> getQuestionnaireDetails(@Path("id") Integer id);

    @GET("/api/questionnaire/all")
    Call<List<QuestionnaireDTO>> getAllQuestionnaires();

    @POST("/api/questionnaire")
    Call<Questionnaire> createQuestionnaire(@Body Questionnaire questionnaire);

    @PUT("/api/questionnaire/{id}")
    Call<Questionnaire> updateQuestionnaire(
            @Path("id") Integer id,
            @Body Questionnaire questionnaire
    );

    @DELETE("/api/questionnaire/{id}")
    Call<Void> deleteQuestionnaire(@Path("id") Integer id);
}

