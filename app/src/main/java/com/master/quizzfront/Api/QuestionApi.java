package com.master.quizzfront.Api;

import com.master.quizzfront.Models.Question;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface QuestionApi {
    @PUT("/api/questionnaire/{questionnaireId}/question/{questionId}")
    Call<Question> updateQuestion(
            @Path("questionnaireId") Integer questionnaireId,
            @Path("questionId") Integer questionId,
            @Body Question question
    );

    @POST("/api/questionnaire/{questionnaireId}/question")
    Call<Question> addQuestion(
            @Path("questionnaireId") Integer questionnaireId,
            @Body Question question
    );

    @DELETE("/api/questionnaire/{questionnaireId}/question/{questionId}")
    Call<Void> deleteQuestion(
            @Path("questionnaireId") Integer questionnaireId,
            @Path("questionId") Integer questionId
    );

    @GET("/api/questionnaire/{questionnaireId}/question")
    Call<List<Question>> getAllQuestions(
            @Path("questionnaireId") Integer questionnaireId
    );
}

