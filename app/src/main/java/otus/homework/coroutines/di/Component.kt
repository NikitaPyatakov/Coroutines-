package otus.homework.coroutines.di

import dagger.Component
import otus.homework.coroutines.presentation.MainActivity

@Component(modules = [ServerModule::class])
interface Component {

    fun inject(mainActivity: MainActivity)
}