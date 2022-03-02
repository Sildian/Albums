package com.sildian.apps.albums.dataSource

import com.sildian.apps.albums.model.Song
import retrofit2.http.GET

/*************************************************************************************************
 * List queries related to albums
 ************************************************************************************************/

interface AlbumQueries {

    companion object {
        const val URL_BASE = "https://static.leboncoin.fr/"
        const val URL_ALL_SONGS = "img/shared/technical-test.json"
    }

    @GET(URL_ALL_SONGS)
    suspend fun getAllSongs(): List<Song>
}