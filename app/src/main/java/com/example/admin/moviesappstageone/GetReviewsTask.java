package com.example.admin.moviesappstageone;

import android.content.AsyncTaskLoader;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class GetReviewsTask extends AsyncTaskLoader<String> {
    int id;

    public GetReviewsTask(MoviesDetails moviesDetails, int id) {
        super(moviesDetails);
        this.id=id;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        StringBuilder builder=new StringBuilder();
        try {
            Uri uri = Uri.parse(Constants.PATH)
                    .buildUpon()
                    .appendPath(String.valueOf(id))
                    .appendPath(Constants.REVIEWS)
                    .appendQueryParameter(Constants.Q_PARAM,BuildConfig.THE_MOVIE_DB_API_TOKEN)
                    .build();
            URL url=new URL(uri.toString());
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null){
                line=reader.readLine();
                builder.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}