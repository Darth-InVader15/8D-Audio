package com.cubesoft.musicx


import android.net.Uri

class SongInfo(
    var count: Int,
    var title: String,
    var artist: String,
    var albumArtUri: Uri,
    var dataUri: Uri
)