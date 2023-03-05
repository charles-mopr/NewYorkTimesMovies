package charlesroy.example.newyorktimesmovies

class APIFormat(
    var results: List<Movie>? = null
)

class Movie(
    val summary_short: String,
    val display_title: String,
    val mpaa_rating: String,
    val multimedia: Multimedia
)

data class Multimedia(
    val type: String,
    val src: String,
    val height: Int,
    val width: Int
)