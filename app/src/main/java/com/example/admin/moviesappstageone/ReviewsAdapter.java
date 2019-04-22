package com.example.admin.moviesappstageone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>{
    MoviesDetails context;
    ArrayList<ReviewInfo> list;

    public ReviewsAdapter(MoviesDetails context, ArrayList<ReviewInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.reviews_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ReviewInfo pos=list.get(position);
        holder.contenT.setText("\t\t"+pos.getContent());
        holder.url.setText(R.string.moredetails);
        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(pos.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        else return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.content_id)
        TextView contenT;
        @BindView(R.id.reviewUrl_id)
        TextView url;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
