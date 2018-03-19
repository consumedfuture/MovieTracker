package lychagov.com.movietracker

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.jetbrains.anko.wrapContent

/**
 * Created by LychagovAN on 19.03.2018.
 */

class FilmView(context: Context) : View(context) {
    private var poster : Bitmap? = null
    private var title : String = ""

    private val titlePaint = TextPaint().apply{
        textSize = 72f
        color = Color.WHITE
        textAlign = Paint.Align.LEFT
    }
    private val posterPaint = Paint()

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) return
        //val bounds = Rect()
        val posterWidth = poster?.width ?: 100
        val textWidth = canvas.width-posterWidth- dpToPx(16,context)
        val textLayout = StaticLayout(title,titlePaint, textWidth, Layout.Alignment.ALIGN_NORMAL,1.0f,1.0f,false)

        canvas.drawColor(Color.LTGRAY)
        if (poster != null)
            canvas.drawBitmap(poster,0f,0f, posterPaint)
        val x = (canvas.width-posterWidth - textWidth)/2f
        val y = (canvas.height-textLayout.height)/2f
        canvas.save()
        canvas.translate(x,y)
        textLayout.draw(canvas)
        canvas.restore()
        //canvas.drawText(title, dpToPx(100,context).toFloat(), canvas.height/2.toFloat(),titlePaint)
        //Log.v("title", title)
    }

    fun bind(film: FilmInfo) {
        title = film.title
        poster = null
        invalidate()
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = film.poster_path
        Glide.with(context)
                .asBitmap()
                .load(baseUrl+posterUrl)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return true
                    }
                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        if (resource == null) return false
                        poster = resource
                        invalidate()
                        return true
                    }
                })
                .submit(100, dpToPx(100,context))
        Log.v("text", poster.toString())
    }
}