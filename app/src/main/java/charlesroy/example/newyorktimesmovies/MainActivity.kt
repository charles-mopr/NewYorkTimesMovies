package charlesroy.example.newyorktimesmovies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import charlesroy.example.newyorktimesmovies.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.title = "New York Times Critic's Picks movies"
        setContentView(binding.root)

        val apiKey = "EAKAIHibAmyxvRd9KebTfLpgR91QaVjr"
        val base = "https://api.nytimes.com/svc/movies/v2/"
        val query = "reviews/picks.json?api-key="
        val url = "$base$query$apiKey"

        val thread = Thread {
            val connection = URL(url).openConnection() as HttpsURLConnection
            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val apiFormat = Gson().fromJson(inputStreamReader, APIFormat::class.java)
                val copyright = apiFormat?.copyright
                if (copyright != null) {
                    runOnUiThread {
                        binding.textViewCopyright.text = copyright
                    }
                }
                val moviesList = apiFormat.results ?: emptyList()
                runOnUiThread {
                    binding.mainButton.setOnClickListener {
                        val intent = Intent(this, MoviesActivity::class.java)
                        intent.putExtra("moviesList", Gson().toJson(moviesList))
                        startActivity(intent)
                    }
                }
                inputStreamReader.close()
                inputSystem.close()
            }
        }
        thread.start()
    }
}
