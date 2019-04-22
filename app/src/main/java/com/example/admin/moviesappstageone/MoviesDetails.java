package com.example.admin.moviesappstageone;

import android.app.LoaderManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesDetails extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks{

    @BindView(R.id.title_id)
    TextView title_textview;
    @BindView(R.id.rating_id)
    TextView vote_coun;
    @BindView(R.id.overview_id)
    TextView desc;
    @BindView(R.id.releaseDate_id)
    TextView releaseDate;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.reviews)
    CardView reviewss;
    @BindView(R.id.trailers)
    CardView trailers;

    @BindView(R.id.smallImage_id)
    ImageView poster;
    @BindView(R.id.backdrop_path_id)
    ImageView backdrop;
    @BindView(R.id.fav_img_id)
    ImageView favImage;
    private String video;
    private String popularity;
    private String original_language;
    private String adult;
    private String original_title;
    private String vote_count;
    private double vote_average;
    private String poster_path;
    private String backdrop_path;
    private String title;
    private String overview;
    private String release_date;
    private int id;

    private int TRAILER_LOADER_ID=1;
    private int REVIEW_LOADER_ID=2;

    @BindView(R.id.trailer_recyclerview_id)
    RecyclerView trailerRecyclerview;

    @BindView(R.id.reviews_recyclerview_id)
    RecyclerView reviewsRecyclerview;

    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        ButterKnife.bind(this);
        movieViewModel = ViewModelProviders.of(this)
                .get(MovieViewModel.class);

        ArrayList<MoviesInfo> list = getIntent().getParcelableArrayListExtra(Constants.MOVIE_DETAILS_KEY);
        int pos = getIntent().getIntExtra(Constants.POSITION,0);

        Toast.makeText(this, ""+list.get(pos).getOriginal_title(), Toast.LENGTH_LONG).show();

        vote_count = list.get(pos).getVote_count();
        id= list.get(pos).getId();
        video=list.get(pos).getVideo();
        vote_average = list.get(pos).getVote_average();
        title= list.get(pos).getTitle();
        popularity= list.get(pos).getPopularity();
        poster_path = list.get(pos).getPoster_path();
        backdrop_path = list.get(pos).getBackdrop_path();
        original_language = list.get(pos).getOriginal_language();
        original_title = list.get(pos).getOriginal_title();
        adult = list.get(pos).getAdult();
        overview = list.get(pos).getOverview();
        release_date = list.get(pos).getRelease_date();

        Picasso.with(this)
                .load(Constants.IMG_URL_500 + poster_path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(poster);
        Picasso.with(this)
                .load(Constants.IMG_URL_780 + backdrop_path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_image)
                .into(backdrop);

        title_textview.setText(title);
        vote_coun.setText(vote_count);
        desc.setText(overview);
        releaseDate.setText(release_date);
        rating.setText(""+vote_average);

        if (movieViewModel.checkFav(id)){
            favImage.setImageResource(R.drawable.fav_fill);
        }
        if (isNetworkAvailable()){
            getLoaderManager().initLoader(TRAILER_LOADER_ID,null,this);
            getLoaderManager().initLoader(REVIEW_LOADER_ID,null,this);
        }else {
            showAlert();
        }
    }
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nointernet);
        builder.setMessage(R.string.cantload);
        builder.setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(
                        Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        if (i==TRAILER_LOADER_ID){
            return new GetTrailersTask(this,id);
        }else if (i==REVIEW_LOADER_ID){
            return new GetReviewsTask(this,id);
        }
        return null;
    }
    @Override
    public void onLoadFinished(Loader loader, Object data) {
        int id=loader.getId();
        if (id==TRAILER_LOADER_ID){
            ArrayList<TrailerInfo> list=JsonUtils.parseTrailerInfoJson(data.toString());
            if (list.size()>0){
                trailerRecyclerview.setLayoutManager(new LinearLayoutManager(this));
                trailerRecyclerview.setAdapter(new TrailerAdapter(this,list));
            }else {
                trailers.setVisibility(View.GONE);
                Toast.makeText(this, R.string.notrailers, Toast.LENGTH_SHORT).show();
            }

        }else if (id==REVIEW_LOADER_ID){
            ArrayList<ReviewInfo>  list=JsonUtils.parseReviewInfoJson(data.toString());
            if (list.size()>0){
                reviewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
                reviewsRecyclerview.setAdapter(new ReviewsAdapter(this,list));
            }
            else {
                reviewss.setVisibility(View.GONE);
                Toast.makeText(this, R.string.noreviews, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
    public MoviesInfo setValuesToPojo(){
        MoviesInfo info=new MoviesInfo();
        info.setVote_count(vote_count);
        info.setId(id);
        info.setVideo(video);
        info.setVote_average(vote_average);
        info.setTitle(title);
        info.setPopularity(popularity);
        info.setPoster_path(poster_path);
        info.setBackdrop_path(backdrop_path);
        info.setOriginal_language(original_language);
        info.setOriginal_title(original_title);
        info.setAdult(adult);
        info.setOverview(overview);
        info.setRelease_date(release_date);
        return info;
    }

    public void favMethod(View view) {
        movieViewModel.checkFav(id);
        if (movieViewModel.checkFav(id)) {
            favImage.setImageResource(R.drawable.fav_button);

            movieViewModel.delete(setValuesToPojo());
            Toast.makeText(this, R.string.removed, Toast.LENGTH_SHORT).show();
        }
        else {
            favImage.setImageResource(R.drawable.fav_fill);

            movieViewModel.insert(setValuesToPojo());
            Toast.makeText(this, R.string.added, Toast.LENGTH_SHORT).show();
        }

    }
}
