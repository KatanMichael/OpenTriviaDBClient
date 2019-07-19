package michaelkatan.models

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RetroController
{
    @GET("/")
    fun getQuestions(@Query("amount") amount: Int) : Call<QuestionRequest>

    @GET("api.php")
    fun getQuizQuestions(@Query("amount") amount: Int):
            Call<QuestionRequest>

}