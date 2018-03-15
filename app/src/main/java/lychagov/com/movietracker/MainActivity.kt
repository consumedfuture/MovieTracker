package lychagov.com.movietracker

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.customView
import org.jetbrains.anko.sdk25.coroutines.textChangedListener
import org.w3c.dom.Text
import java.io.Serializable
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private val httpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filmsList = TMDBFilms()
        val filmsAdapter = FilmsListAdapter(filmsList)

        frameLayout {
                val editText = editText {
                    hint = "films, series, ..."
                }.lparams(width = matchParent) {
                    height = dip(50)
                    padding = dip(10)

                }
                customView<RecyclerView> {
                    layoutManager = LinearLayoutManager(context)
                    adapter = filmsAdapter
                }.lparams{
                    topMargin = dip(50)
                }

            editText.textChangedListener {
                afterTextChanged {
                    //Log.v("films", it?.toString())
                    if (it?.isBlank() == false)
                        loadFilms(httpClient, editText.text.toString()) { films ->
                            runOnUiThread {
                                filmsList.clear()
                                filmsList.addAll(films)
                                filmsAdapter.notifyDataSetChanged()
                            }
                        }
                    else{
                        filmsList.clear()
                        filmsAdapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }
}

data class TMDBFilmInfo(
    val title : String,
    val poster_path : String,
    val overview : String,
    val release_date : String,
    val vote_average : String
): Serializable

class TMDBFilms : ArrayList<TMDBFilmInfo>()

fun loadFilms(
        httpClient: OkHttpClient,
        title: String,
        onComplete: (films: TMDBFilms) -> Unit
){
    Thread {
        val request = Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=${URLEncoder.encode(title,"utf-8")}&api_key=b7c5b3c41053880dab8271e0fa4864da&language=ru")
                .build()
        val response = httpClient.newCall(request).execute()
        val obj = JsonParser().parse(response.body()?.string())
        val text = obj.asJsonObject.get("results") ?: JsonObject()
        val films: TMDBFilms = Gson().fromJson(text,TMDBFilms::class.java)
        onComplete(films)
    }.start()
}

class FilmsListAdapter(
        private val films: TMDBFilms
) : RecyclerView.Adapter<FilmsListAdapter.FilmViewHolder>(){
    override fun getItemCount() = films.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FilmViewHolder{

        val textView = TextView(parent!!.context).apply {
            textSize = 32f
            textColor = Color.BLUE
            padding = dip(10)
            isClickable = true
        }
        return FilmViewHolder(textView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder?, position: Int) {
        if (holder == null) return
        val itemFilm = films[position]
        holder.bindFilm(itemFilm)
        //holder.filmName.text = films[position].title
    }

    class FilmViewHolder(
           val filmName: TextView
    ) : RecyclerView.ViewHolder(filmName), View.OnClickListener{
        private var view: TextView = filmName
        private var film: TMDBFilmInfo? = null
        init {
            filmName.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //v?.context?.toast("${filmName.text} clicked")
            val context = itemView.context
            val showFilmIntent = Intent(context, FilmActivity::class.java)
            showFilmIntent.putExtra(FILM_KEY, film)
            context.startActivity(showFilmIntent)
        }

        fun bindFilm(film: TMDBFilmInfo){
            this.film = film
            //Picasso.get().load(film.poster_path).into(imageView)
            view.text = film.title
        }

        companion object {
            private val FILM_KEY = "film"
        }

    }

}