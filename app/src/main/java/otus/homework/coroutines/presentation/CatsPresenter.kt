package otus.homework.coroutines.presentation

import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import otus.homework.coroutines.CrashMonitor
import otus.homework.coroutines.server.CatsPhotosService
import otus.homework.coroutines.server.CatsTextService
import java.lang.Exception
import java.net.SocketTimeoutException

class CatsPresenter(
    private val catsService: CatsTextService,
    private val catsPhotosService: CatsPhotosService
) {

    private var _catsView: ICatsView? = null
    private val job = Job()
    private val presenterScope = CoroutineScope(
        Dispatchers.Main + job + CoroutineName("CatsCoroutine")
    )

    fun onInitComplete() {
        presenterScope.launch {
            try {
                val res1 = async { catsService.getCatFact() }
                val res2 = async { catsPhotosService.getCatPhoto() }
                val fact = res1.await()
                val photo = res2.await().getOrNull(0)

                _catsView?.populate(fact, photo?.url)
            } catch (e: SocketTimeoutException) {
                _catsView?.exceptionToast(e.toString())
            } catch (e: Exception) {
                _catsView?.exceptionToast(e.toString())
                CrashMonitor.trackWarning()
            }
        }
    }

    fun setUrlPhotoInView(imageView: ImageView, photoUrl: String?) {
        Picasso.get().load(photoUrl).into(imageView)
    }

    fun attachView(catsView: ICatsView) {
        _catsView = catsView
    }

    fun detachView() {
        job.cancel()
        _catsView = null
    }
}