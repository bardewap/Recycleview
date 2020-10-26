package com.example.leaderboard;

import android.content.Context;
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

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<StoreDataModel> storeDataModelList;
    private Context context;

    public StoreAdapter(List<StoreDataModel> storeDataModelList, Context context) {
        this.storeDataModelList = storeDataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        StoreViewHolder viewHolder = new StoreViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {


        holder.textView.setText(storeDataModelList.get(position).getName());
//        holder.textView2.setText(storeDataModelList.get(position).getCurrency());
        Glide
                .with(context)
                .load(R.drawable.ic_baseline_account_circle_24)
                .centerCrop()
                .into(holder.imageView);


        holder.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "HEllo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return storeDataModelList.size();
    }

    class  StoreViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public TextView textView2;
        public LinearLayout linearLayout;
        public ImageView imageView;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textView2 = (TextView) itemView.findViewById(R.id.details);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
