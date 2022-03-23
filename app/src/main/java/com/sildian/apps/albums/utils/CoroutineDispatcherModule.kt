package com.sildian.apps.albums.utils

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/*************************************************************************************************
 * Module allowing to provide Coroutine's dispatchers instances
 ************************************************************************************************/

@Module
@InstallIn(ViewModelComponent::class)
object CoroutineDispatcherModule {

    @Provides
    @ViewModelScoped
    fun provideCoroutineDispatcher(): CoroutineDispatcher =
        Dispatchers.IO
}