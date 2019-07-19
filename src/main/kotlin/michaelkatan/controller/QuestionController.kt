package michaelkatan.controller

import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import michaelkatan.models.QuestionRequest
import michaelkatan.models.RequestListener
import michaelkatan.models.RetroController
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader
import java.net.URL

object QuestionController
{

    private val gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder().baseUrl("https://opentdb.com/api.php/")
        .addConverterFactory(GsonConverterFactory.create(gson)).build()

    private val retroClient = retrofit.create(RetroController::class.java)

    val httpClient = OkHttpClient()

    fun getQuestions(amount: Int, category: Int, difficulty: String, type:String
                     ,requestListener: RequestListener)
    {


            val stringUrl = "https://opentdb.com/api.php/?amount=$amount" +
                    "&category=$category&difficulty=$difficulty&type=$type"

            val url = URL(stringUrl)

            val inputStreamReader = InputStreamReader(url.openStream())

            val encoding = inputStreamReader.readText()

            val fromJson = gson.fromJson(encoding, QuestionRequest::class.java)




                if(fromJson.response_code == 1)
                {
                    requestListener.onError("Error Found")
                }else
                {
                    requestListener.onComplete(fromJson)
                }



    }


    fun getQuestionsRetro(requestListener: RequestListener)
    {
        retroClient.getQuestions(10).enqueue(object : retrofit2.Callback<QuestionRequest>
        {
            override fun onFailure(call: retrofit2.Call<QuestionRequest>
                                   , t: Throwable)
            {
                requestListener.onError(t.message.toString())
            }

            override fun onResponse(call: retrofit2.Call<QuestionRequest>,
                response: retrofit2.Response<QuestionRequest>)
            {
                val body = response.body()

                println(body)
            }

        })
    }

    fun getQuizQuestions(amount: Int, requestListener: RequestListener)
    {
        retroClient.getQuizQuestions(amount)
            .enqueue(object : retrofit2.Callback<QuestionRequest>
            {
                override fun onFailure(call: retrofit2.Call<QuestionRequest>, t: Throwable)
                {
                    requestListener.onError(t.message.toString())
                }

                override fun onResponse(
                    call: retrofit2.Call<QuestionRequest>,
                    response: retrofit2.Response<QuestionRequest>)
                {
                    println(call.request())
                    //println(response.body())

                }

            })


    }




}