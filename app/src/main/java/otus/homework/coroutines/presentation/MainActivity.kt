package otus.homework.coroutines.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import otus.homework.coroutines.R
import otus.homework.coroutines.di.DaggerComponent
import otus.homework.coroutines.server.CatsPhotosService
import otus.homework.coroutines.server.CatsTextService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    lateinit var catsPresenter: CatsPresenter

    @Named("text")
    @Inject
    lateinit var textByRetrofit: Retrofit

    @Named("photo")
    @Inject
    lateinit var photoByRetrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerComponent.create().inject(this)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        catsPresenter = CatsPresenter(
            textByRetrofit.create(CatsTextService::class.java),
            photoByRetrofit.create(CatsPhotosService::class.java)
        )
        view.presenter = catsPresenter
        catsPresenter.attachView(view)
        catsPresenter.onInitComplete()
    }

    override fun onStop() {
        if (isFinishing) {
            catsPresenter.detachView()
        }
        super.onStop()
    }
}