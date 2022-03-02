package com.sildian.apps.albums.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*************************************************************************************************
 * Model class Song
 ************************************************************************************************/

data class Song(
    @SerializedName("albumId") @Expose val albumId: Int? = null,
    @SerializedName("id") @Expose val id: Int? = null,
    @SerializedName("title") @Expose val title: String? = null,
    @SerializedName("url") @Expose val url: String? = null,
    @SerializedName("thumbnailUrl") @Expose val thumbnailUrl: String? = null
)
