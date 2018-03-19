package lychagov.com.movietracker

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup


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
        val height = dpToPx(100, parent.context)
        view.layoutParams = RecyclerView.LayoutParams(width, height)

        val holder = FilmViewHolder(view)
        //Log.v("123",holder.toString())
        return holder
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val filmView = holder.view
        val film = films[position]
        filmView.bind(film)
    }
}