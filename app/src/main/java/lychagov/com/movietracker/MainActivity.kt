package lychagov.com.movietracker

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext
import org.jetbrains.anko.backgroundColor
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.sdk25.coroutines.textChangedListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val films = Films()
        val linearLayout = LinearLayout(this).apply {
            backgroundColor = Color.rgb(200,200,200)
            orientation = LinearLayout.VERTICAL
            title = "Search"
        }
        val filmsView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FilmsAdapter(films)
            addItemDecoration(DividerItemDecoration(context,LinearLayout.VERTICAL))
        }
        val searchFilm = EditText(this).apply {
            hint = "films, series, ..."
            backgroundColor = Color.rgb(220,220,220)
            padding = dip(10)

        }

        searchFilm.textChangedListener {
            var filmsJob: Job? = null
            this.afterTextChanged {
                filmsJob?.cancel()
                films.clear()
                if (it?.isBlank() == false) {
                   filmsJob = launch(UI) {
                        val asyncContext = newSingleThreadContext("loading_films")
                        val job = loadFilmsAsync(asyncContext, searchFilm.text.toString())
                        films.addAll(job.await())
                    }
                    filmsJob?.join()
                    filmsView.adapter.notifyDataSetChanged()
                }
                else{
                    filmsView.adapter.notifyDataSetChanged()
                }
            }
        }


        linearLayout.addView(searchFilm)
        linearLayout.addView(filmsView)
        setContentView(linearLayout)
    }
}
