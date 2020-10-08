package com.example.recycleview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<Movie> movieList;
    private Context context;

    public UserAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        UserHolder viewHolder = new UserHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {


        holder.textView.setText(movieList.get(position).getTitle());

        holder.textView2.setText(movieList.get(position).getYear());
        Glide
                .with(context)
                .load(movieList.get(position).getPoster())
                .centerCrop()
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, movieList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {

        Log.d("ODOSDOSBDOBD", "getItemCount: " + movieList.size());

        return movieList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView2;
        public LinearLayout linearLayout;
        public ImageView imageView;

        public UserHolder(@NonNull View itemView) {

            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView1);
            this.textView2 = (TextView) itemView.findViewById(R.id.textView2);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }
    }
}
