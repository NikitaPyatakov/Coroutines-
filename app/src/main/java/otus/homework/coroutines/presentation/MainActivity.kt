package otus.homework.coroutines.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import otus.homework.coroutines.R
import otus.homework.coroutines.di.DaggerComponent
import otus.homework.coroutines.data.server.CatsPhotosService
import otus.homework.coroutines.data.server.CatsTextService
import androidx.activity.viewModels
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import otus.homework.coroutines.entity.CatData
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    private val viewModel: CatsViewModel by viewModels {
        CatsViewModel.Companion.CatsViewModelFactory(
            textByRetrofit.create(CatsTextService::class.java),
            photoByRetrofit.create(CatsPhotosService::class.java)
        )
    }

    private val mainActivityScope =
        CoroutineScope(Dispatchers.Main + Job() + CoroutineName("CatsCoroutine"))

    @Named("text")
    @Inject
    lateinit var textByRetrofit: Retrofit

    @Named("photo")
    @Inject
    lateinit var photoByRetrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerComponent.create().inject(this)
        setContentView(layoutInflater.inflate(R.layout.activity_main, null))
        setClickListeners()
        observeOnEvents()
        viewModel.onInitComplete()
    }

    private fun observeOnEvents() {
        mainActivityScope.launch {
            viewModel.catsData.collect { catData ->
                populate(catData)
            }
        }
        mainActivityScope.launch {
            viewModel.exceptionToast.collect { msg ->
                exceptionToast(msg)
            }
        }
    }

    private fun setClickListeners() {
        findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.onInitComplete()
        }
    }

    private fun populate(catData: CatData) {
        findViewById<TextView>(R.id.fact_textView).text = catData.factData?.fact
        catData.photoData?.let {photo ->
            viewModel.setUrlPhotoInView(findViewById(R.id.catPhoto), photo.url)
        }
    }

    private fun exceptionToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}