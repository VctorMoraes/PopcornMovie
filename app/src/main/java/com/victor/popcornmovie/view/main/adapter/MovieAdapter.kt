package com.victor.popcornmovie.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.popcornmovie.R
import com.victor.popcornmovie.databinding.MovieItemBinding
import com.victor.popcornmovie.model.MovieModel

class MovieAdapter(private val openFragment: (movie: MovieModel) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: ArrayList<MovieModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent, openFragment)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return when {
            movieList.isNullOrEmpty() -> 0
            else -> movieList.size
        }
    }

    fun configure(movieList: ArrayList<MovieModel>, loadMore: Boolean = false) {
        if (!loadMore) {
            this.movieList.clear()
        }
        this.movieList.addAll(movieList)
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        private val viewBinding: MovieItemBinding,
        private val openFragment: (movie: MovieModel) -> Unit
    ) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(movie: MovieModel) {
            viewBinding.apply {
                Glide.with(viewBinding.root)
                    .load(IMAGE_BASE_URL + movie.posterPath)
                    .centerInside()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(moviePoster)

                movieTitle.text = movie.title

                movieCard.setOnClickListener {
                    openFragment(movie)
                }
            }
        }

        companion object {
            const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

            fun create(
                parent: ViewGroup,
                openFragment: (movie: MovieModel) -> Unit
            ): MovieViewHolder {
                val itemBinding = MovieItemBinding.inflate(
                    LayoutInflater
                        .from(parent.context), parent, false
                )
                return MovieViewHolder(itemBinding, openFragment)
            }
        }
    }
}

