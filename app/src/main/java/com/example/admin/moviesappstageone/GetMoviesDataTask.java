package com.example.admin.moviesappstageone;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ADMIN on 12/4/2018.
 */

class GetMoviesDataTask extends AsyncTask<String, Void, String> {

    MainActivity context;
    private AsyncTaskDelegate delegate = null;

    GetMoviesDataTask(MainActivity mainActivity,AsyncTaskDelegate delegate) {
        context = mainActivity;
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        try {
            Uri uri = Uri.parse(strings[0])
                    .buildUpon()
                    .appendPath(strings[1])
                    .appendQueryParameter(Constants.Q_PARAM, BuildConfig.THE_MOVIE_DB_API_TOKEN)
                    .build();

            URL url = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                result.append(line);
            }
            return result.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (delegate != null) {
            delegate.processFinished(s);
        }
    }
}