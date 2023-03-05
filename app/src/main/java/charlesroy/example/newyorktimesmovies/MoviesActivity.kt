package charlesroy.example.newyorktimesmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import charlesroy.example.newyorktimesmovies.databinding.ActivityMoviesBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var adapter: MoviesAdapter
    private var moviesList: List<Movie> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "NY Critics Picks"
        adapter = MoviesAdapter()
        binding.recyclerViewCriticsPicks.adapter = adapter

        val apiKey = "EAKAIHibAmyxvRd9KebTfLpgR91QaVjr"
        val base = "https://api.nytimes.com/svc/movies/v2/"
        val query = "reviews/picks.json?api-key="
        val url = "$base$query$apiKey"
        val thread = Thread {
            fetchMoviesList(url)
        }
        thread.start()
    }

    private fun fetchMoviesList(url: String) {
        val connection = URL(url).openConnection() as HttpsURLConnection
        if (connection.responseCode == 200) {
            val inputSystem = connection.inputStream
            val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
            val apiFormat = Gson().fromJson(inputStreamReader, APIFormat::class.java)
            moviesList = apiFormat.results ?: emptyList()
            updateUI()
            inputStreamReader.close()
            inputSystem.close()
        } else {
            Log.e("MoviesActivity", "Failed to fetch movies list")
        }
    }

    private fun updateUI() {
        runOnUiThread {
            adapter.setData(moviesList)
        }
    }
}