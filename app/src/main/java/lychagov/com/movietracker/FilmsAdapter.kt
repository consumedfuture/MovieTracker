<<<<<<< HEAD
package lychagov.com.movietracker

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by LychagovAN on 19.03.2018.
 */
fun dpToPx(dp: Int, context: Context): Int {
    val density = context.getResources()
            .getDisplayMetrics()
            .density
    return Math.round(dp.toFloat() * density)
}


class FilmsAdapter(
        private val films: Films
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun getItemCount() = films.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = FilmView(parent.context)
        val width = RecyclerView.LayoutParams.MATCH_PARENT
        val height = dpToPx(200, parent.context)
        view.layoutParams = RecyclerView.LayoutParams(width, height)

        val holder = FilmViewHolder(view)
        //Log.v("123",holder.toString())
        return holder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val filmView = holder.view
        val film = films[position]
        filmView.bind(film)
        filmView.setOnClickListener {
            it.onClick {
                val context = it?.context
                val showFilmIntent = Intent(context, FilmActivity::class.java)
                showFilmIntent.putExtra("film_id", film.id)
                context?.startActivity(showFilmIntent)
                Toast.makeText(filmView.context,film.title,Toast.LENGTH_SHORT).show()
            }
        }
    }
=======
package lychagov.com.movietracker

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * Created by LychagovAN on 19.03.2018.
 */
fun dpToPx(dp: Int, context: Context): Int {
    val density = context.getResources()
            .getDisplayMetrics()
            .density
    return Math.round(dp.toFloat() * density)
}


class FilmsAdapter(
        private val films: Films
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun getItemCount() = films.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = FilmView(parent.context)
        val width = RecyclerView.LayoutParams.MATCH_PARENT
        val height = dpToPx(200, parent.context)
        view.layoutParams = RecyclerView.LayoutParams(width, height)

        val holder = FilmViewHolder(view)
        //Log.v("123",holder.toString())
        return holder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val filmView = holder.view
        val film = films[position]
        filmView.bind(film)
        filmView.setOnClickListener {
            it.onClick {
                val context = it?.context
                val showFilmIntent = Intent(context, FilmActivity::class.java)
                showFilmIntent.putExtra("film_id", film.id)
                context?.startActivity(showFilmIntent)
                Toast.makeText(filmView.context,film.title,Toast.LENGTH_SHORT).show()
            }
        }
    }
>>>>>>> 4e763b6afe6496549a8e2e8da29416fec2788fa2
}