package charlesroy.example.newyorktimesmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MoviesAdapter :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var data: List<Movie> = emptyList()

    fun setData(data: List<Movie>){
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewTitle: TextView
        var textViewSummary: TextView
        var textViewMPAARating: TextView
        var imageView: ImageView

        init {
            textViewTitle = view.findViewById(R.id.title_text)
            textViewSummary = view.findViewById(R.id.summary_text)
            textViewMPAARating = view.findViewById(R.id.rating_text)
            imageView = view.findViewById(R.id.multimedia_imageView)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        var movie = data.get(position)
        viewHolder.textViewTitle.setText(movie.display_title)
        viewHolder.textViewSummary.setText(movie.summary_short)
        if (movie.mpaa_rating == ""){
            viewHolder.textViewMPAARating.setText("No Rating")
        }
        else{
            viewHolder.textViewMPAARating.setText(movie.mpaa_rating)
        }

        Picasso.get().load(movie.multimedia.src).into(viewHolder.imageView)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}