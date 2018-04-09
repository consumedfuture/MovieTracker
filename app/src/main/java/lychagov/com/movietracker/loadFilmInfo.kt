package lychagov.com.movietracker

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsyncResult
import java.net.URLEncoder
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by LychagovAN on 20.03.2018.
 */

fun loadFilmInfo(
        id: Int,
        coroutineContext: CoroutineContext = CommonPool
): Deferred<FilmInfo> = async(coroutineContext){
    val httpClient = OkHttpClient()

    val request = Request.Builder()
            //.url("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
            .url("https://api.themoviedb.org/3/movie/${id}?api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
            .build()
    val response = httpClient.newCall(request).execute()
    val text = response.body()?.string() ?: "{}"
    val details: FilmInfo = Gson().fromJson(text,FilmInfo::class.java)
    details
}
