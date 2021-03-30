package com.mburakcakir.taketicket.util

object Constants {

    // warning
    const val INVALID_USERNAME = "Kullanıcı adı en az 4 haneli olmalıdır."
    const val INVALID_PASSWORD =
        "Şifre Büyük harf, küçük harf, sayı içermeli ve en az 6 haneli olmalıdır."
    const val INVALID_EMAIL = "Email formatı eşleşmiyor."

    // authentication
    private const val CLIENT_ID = "MjE2NTIxOTh8MTYxNzA5NjQzMi42MTIyMDUz"
    private const val API_KEY = "0084f501851d0d513240f2a30678bbe4"

    // base url
    const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/"
    const val BASE_URL_TICKET = "https://api.seatgeek.com/2/"
    const val BASE_URL_INFO = "https://taketicketandroid-default-rtdb.firebaseio.com/"

    // query
    const val QUERY_TICKET = "events/?client_id=$CLIENT_ID"

    const val QUERY_INFO = ".json"

    const val QUERY_MOVIE_TRENDING = "trending/all/day?api_key=$API_KEY"
    const val QUERY_MOVIE_UPCOMING = "movie/upcoming?api_key=$API_KEY"
    const val QUERY_MOVIE_POPULAR = "movie/popular?api_key=$API_KEY"


}