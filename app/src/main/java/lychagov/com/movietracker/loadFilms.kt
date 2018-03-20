package lychagov.com.movietracker

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

/**
 * Created by LychagovAN on 19.03.2018.
 */

fun loadFilms(
        title: String
): Films{
    val httpClient = OkHttpClient()

    val request = Request.Builder()
            //.url("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
            .url("https://api.themoviedb.org/3/search/movie?query=${URLEncoder.encode(title,"utf-8")}&api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
            .build()
    val response = httpClient.newCall(request).execute()
    val obj = JsonParser().parse(response.body()?.string())
    val text = obj.asJsonObject.get("results") ?: JsonObject()
    val films: Films = Gson().fromJson(text,Films::class.java)
    return films
}