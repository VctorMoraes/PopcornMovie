package com.victor.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victor.core.model.MovieModel
import com.victor.feature.home.R
import com.victor.feature.home.databinding.MovieItemBinding

class MovieAdapter(private val openFragment: (movie: MovieModel) -> Unit) :
    PagingDataAdapter<MovieModel, MovieViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent, openFragment)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
                oldItem == newItem
        }
    }
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

