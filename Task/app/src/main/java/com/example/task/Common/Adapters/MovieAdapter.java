    package com.example.task.Common.Adapters;

    import android.content.Context;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.example.task.models.Movie;
    import com.example.task.databinding.MyMovieItemBinding;


    import java.util.List;

    public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

        private List<Movie> movieList;
        private Context context;
        private LayoutInflater layoutInflater;

        public MovieAdapter(List<Movie> movieList, Context context) {
            this.movieList = movieList;
            this.context = context;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if(layoutInflater == null){
                layoutInflater = LayoutInflater.from(viewGroup.getContext());
            }
            MyMovieItemBinding myMovieItemBinding = MyMovieItemBinding.inflate(layoutInflater, viewGroup, false);
            return new MovieHolder(myMovieItemBinding);


        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
            final Movie movie = movieList.get(position);
            holder.bind(movie);

            Glide
                    .with(context)
                    .load(movieList.get(position).getPoster())
                    .centerCrop()
                    .into( holder.myMovieItemBinding.imageView);




        }

        @Override
        public int getItemCount() {

            Log.d("ODOSDOSBDOBD", "getItemCount: " + movieList.size());
            return movieList.size();
        }

        class MovieHolder extends RecyclerView.ViewHolder {
            private MyMovieItemBinding myMovieItemBinding;


            private MovieHolder(@NonNull final MyMovieItemBinding myMovieItemBinding) {
                super(myMovieItemBinding.getRoot());
                this.myMovieItemBinding = myMovieItemBinding;
            }
            void bind(Movie movie){
                this.myMovieItemBinding.setMyMovie(movie);
            }

            Movie getMovie(){
                return  myMovieItemBinding.getMyMovie();
            }

            }

        }


