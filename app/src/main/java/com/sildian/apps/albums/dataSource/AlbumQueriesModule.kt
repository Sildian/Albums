package com.sildian.apps.albums.dataSource

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/*************************************************************************************************
 * Module allowing to provide AlbumQueries instance
 ************************************************************************************************/

@Module
@InstallIn(ViewModelComponent::class)
object AlbumQueriesModule {

    @Provides
    @ViewModelScoped
    fun provideAlbumQueries(): AlbumQueries =
        Retrofit.Builder()
            .baseUrl(AlbumQueries.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
}