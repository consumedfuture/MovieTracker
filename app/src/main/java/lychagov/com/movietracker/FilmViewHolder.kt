package lychagov.com.movietracker

import android.support.v7.widget.RecyclerView


/**
 * Created by LychagovAN on 19.03.2018.
 */

class FilmViewHolder(
        val view: FilmView
) : RecyclerView.ViewHolder(view)
/*
class FilmViewHolder(
        val filmName: TextView
) : RecyclerView.ViewHolder(filmName), View.OnClickListener{
    private var view: TextView = filmName
    private var film: FilmInfo? = null
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

    fun bindFilm(film: FilmInfo){
        this.film = film
        //Picasso.get().load(film.poster_path).into(imageView)
        view.text = film.title
    }

    companion object {
        private val FILM_KEY = "film"
    }
}
        */