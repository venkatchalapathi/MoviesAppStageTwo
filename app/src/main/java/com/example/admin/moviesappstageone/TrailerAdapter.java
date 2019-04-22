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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{
    MoviesDetails context;
    ArrayList<TrailerInfo> list;

    public TrailerAdapter(MoviesDetails context, ArrayList<TrailerInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.trailer_row,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TrailerInfo info=list.get(position);

        holder.trailerTv.setText(info.getName());
        holder.trailerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=" +info.getKey());
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
        @BindView(R.id.trailerUrl_id)
        TextView trailerTv;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
