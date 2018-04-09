package lychagov.com.movietracker

import android.app.AppOpsManager
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

fun saveFilms(
        app: App,
        films: List<FilmInfo>,
        coroutineContext: CoroutineContext = CommonPool
): Deferred<Unit> = async(coroutineContext) {
    app.database.filmsDao().insertAll(films)
}