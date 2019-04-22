package com.example.admin.moviesappstageone;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 12/4/2018.
 */

class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {
    private List<MoviesInfo> myList;
    private Context context;

    public MoviesListAdapter(MainActivity context, ArrayList<MoviesInfo> myList) {
        this.myList = myList;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MoviesInfo data = myList.get(position);
        Picasso.with(context)
                .load(Constants.IMG_URL_780 + data.getPoster_path())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (myList != null) {
            return myList.size();
        } else return 0;
    }

    public void setWords(List<MoviesInfo> moviesInfos) {
        myList = moviesInfos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_id)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(context, MoviesDetails.class);
            intent.putExtra(Constants.POSITION,position);
            intent.putParcelableArrayListExtra(Constants.MOVIE_DETAILS_KEY, (ArrayList<? extends Parcelable>) myList);
            context.startActivity(intent);
        }
    }
}