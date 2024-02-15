package otus.homework.coroutines.presentation

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import otus.homework.coroutines.data.CrashMonitor
import otus.homework.coroutines.entity.CatData
import otus.homework.coroutines.data.server.CatsPhotosService
import otus.homework.coroutines.data.server.CatsTextService
import java.lang.Exception
import java.net.SocketTimeoutException

class CatsViewModel(
    private val catsService: CatsTextService,
    private val catsPhotosService: CatsPhotosService
) : ViewModel() {

    val catsData = MutableSharedFlow<CatData>()
    val exceptionToast = MutableSharedFlow<String>()

    fun onInitComplete() {
        viewModelScope.launch {
            try {
                val res1 = async { catsService.getCatFact() }
                val res2 = async { catsPhotosService.getCatPhoto() }
                val fact = res1.await()
                val photo = res2.await().getOrNull(0)

                catsData.emit(CatData(fact, photo))
            } catch (e: SocketTimeoutException) {
                exceptionToast.emit(e.toString())
            } catch (e: Exception) {
                exceptionToast.emit(e.toString())
                CrashMonitor.trackWarning()
            }
        }
    }

    fun setUrlPhotoInView(imageView: ImageView, photoUrl: String) {
        Picasso.get().load(photoUrl).into(imageView)
    }

    companion object {
        class CatsViewModelFactory(
            private val catsService: CatsTextService,
            private val catsPhotosService: CatsPhotosService
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CatsViewModel(catsService, catsPhotosService) as T
            }
        }
    }
}