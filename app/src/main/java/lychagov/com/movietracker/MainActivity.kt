package lychagov.com.movietracker

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.textChangedListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val films = Films()
        val linearLayout = LinearLayout(this).apply {
            backgroundColor = Color.RED

        }


        val filmsView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FilmsAdapter(films)
        }
        linearLayout.addView(filmsView)
        setContentView(linearLayout)
        //setContentView(filmsView)
        Thread({
            val TMDBFilms = loadFilms()
            films.addAll(TMDBFilms)
            runOnUiThread {
                filmsView.adapter.notifyDataSetChanged()
            }
        }).start()
       /* Thread({

        }).start()*/
        /*frameLayout {
                backgroundColor = Color.GRAY
                val editText = editText {
                    hint = "films, series, ..."
                }.lparams(width = matchParent) {
                    height = dip(50)
                    padding = dip(10)

                }
                filmsView.lparams{
                    topMargin = dip(50)
                }

            editText.textChangedListener {
                afterTextChanged {
                    //Log.v("films", it?.toString())
                    if (it?.isBlank() == false) {
                        Thread({
                            val TMDBFilms = loadFilms(editText.text.toString())
                            films.clear()
                            films.addAll(TMDBFilms)
                            runOnUiThread {
                                filmsView.adapter.notifyDataSetChanged()
                            }
                        }).start()
                    }
                    else{
                        films.clear()
                        filmsView.adapter.notifyDataSetChanged()
                    }

                }
            }
        }*/
    }
}
