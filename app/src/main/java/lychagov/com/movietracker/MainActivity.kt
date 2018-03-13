package lychagov.com.movietracker

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColor
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmsList = TMDBFilms()
        val filmsAdapter = FilmsListAdapter(filmsList)

        frameLayout {
            customView<RecyclerView> {
                layoutManager = LinearLayoutManager(context)
                adapter = filmsAdapter
            }
        }
        loadFilms(httpClient) {films ->
            runOnUiThread {
                filmsList.addAll(films)
                Log.v("films", films.toString())
                filmsAdapter.notifyDataSetChanged()
            }
        }
    }
}

data class TMDBFilmInfo(
    val title: String
)

class TMDBFilms : ArrayList<TMDBFilmInfo>()

fun loadFilms(
        httpClient: OkHttpClient,
        onComplete: (films: TMDBFilms) -> Unit
){
    Thread {
        val request = Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
                .build()
        val response = httpClient.newCall(request).execute()
        Log.v("films", response.body().toString())
        val obj : JsonObject = JsonParser().parse(response.body().toString()).getAsJsonObject()
        val text = obj.get("result").asString ?: "[]"
        val films: TMDBFilms = Gson().fromJson(text,TMDBFilms::class.java)

        onComplete(films)
    }
}

class FilmsListAdapter(
        private val films: TMDBFilms
) : RecyclerView.Adapter<FilmsListAdapter.FilmViewHolder>(){
    override fun getItemCount() = films.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilmViewHolder{
        val textView = TextView(parent!!.context).apply {
            textSize = 32f
            textColor = Color.BLUE
            padding = dip(16)
        }
        return FilmViewHolder(textView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder?, position: Int) {
        if (holder==null) return
        holder.filmName.text = films[position].title
    }
    class FilmViewHolder(
            val filmName: TextView
    ) : RecyclerView.ViewHolder(filmName)
}