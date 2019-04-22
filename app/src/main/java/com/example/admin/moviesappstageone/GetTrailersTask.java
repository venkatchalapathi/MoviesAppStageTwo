package com.example.admin.moviesappstageone;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ADMIN on 12/6/2018.
 */

class GetTrailersTask extends AsyncTaskLoader<String> {
    int id;

    public GetTrailersTask(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        StringBuilder builder = new StringBuilder();
        try {
            Uri uri = Uri.parse(Constants.PATH)
                    .buildUpon()
                    .appendPath(String.valueOf(id))
                    .appendPath(Constants.VIDEOS)
                    .appendQueryParameter(Constants.Q_PARAM,BuildConfig.THE_MOVIE_DB_API_TOKEN)
                    .build();
            Log.i("URL==>",uri.toString());
            URL url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = reader.readLine();
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
