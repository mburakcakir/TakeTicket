package com.mburakcakir.taketicket.data.remote.model.event

data class Performer(
    val away_team: Boolean,
    val colors: Any,
    val divisions: Any,
    val genres: List<Genre>,
    val has_upcoming_events: Boolean,
    val home_team: Boolean,
    val home_venue_id: Any,
    val id: Int,
    val image: String,
    val image_attribution: String,
    val image_license: String,
    val images: ImagesX,
    val location: Any,
    val name: String,
    val num_upcoming_events: Int,
    val popularity: Int,
    val primary: Boolean,
    val score: Double,
    val short_name: String,
    val slug: String,
    val type: String,
    val url: String
)