package com.example.admin.moviesappstageone;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.admin.moviesappstageone.Constants.SORT_ORDER_KEY;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerView;

    private MovieViewModel mMovieViewModel;



    SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog dialog;

    private static final int POPULAR_KEY = 0;
    private static final int TOP_RATED_KEY = 1;
    private static final int FAV_KEY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);

        mMovieViewModel = ViewModelProviders.of(this)
                .get(MovieViewModel.class);

        preferences = getSharedPreferences(SORT_ORDER_KEY, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();

        if (isNetworkAvailable()) {
            doCall(preferences.getInt(SORT_ORDER_KEY, 0));
        } else {
            showAlert();
        }
    }

    private void doCall(Integer orderKey) {
        switch (orderKey) {
            case POPULAR_KEY:
                callPopularMethod();
                break;
            case TOP_RATED_KEY:
                callTopRated();
                break;
            case FAV_KEY:
                getFavourates();
                break;
        }
    }


    private void getFavourates() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mMovieViewModel.getList().observe(this, new Observer<List<MoviesInfo>>() {
            @Override
            public void onChanged(@Nullable List<MoviesInfo> moviesInfos) {
                if (moviesInfos != null && moviesInfos.size() > 0) {
                    MoviesListAdapter adapter = new MoviesListAdapter(MainActivity.this,
                            (ArrayList<MoviesInfo>) moviesInfos);
                    adapter.setWords(moviesInfos);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, R.string.nofavv, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nointernet);
        builder.setMessage(R.string.no_internet_message);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    private void callPopularMethod() {
        showProgress();
        GetMoviesDataTask task = new GetMoviesDataTask(this, new AsyncTaskDelegate() {
            @Override
            public void processFinished(String data) {
                parseAndSetRecyclerview(data);
            }
        });
        task.execute(Constants.PATH, Constants.POPULAR);
    }

    private void callTopRated() {
        showProgress();

        GetMoviesDataTask task = new GetMoviesDataTask(this, new AsyncTaskDelegate() {
            @Override
            public void processFinished(String data) {
                parseAndSetRecyclerview(data);
            }
        });
        task.execute(Constants.PATH, Constants.TOP_RATED);

    }

    private void showProgress() {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle(getString(R.string.getting_data));
        dialog.setMessage(getString(R.string.set_message));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_menuId: {
                editor.putInt(SORT_ORDER_KEY, POPULAR_KEY);
                editor.commit();
                if (isNetworkAvailable()) {
                    callPopularMethod();
                } else {
                    showAlert();
                }

                break;
            }
            case R.id.toprated_menuId: {
                editor.putInt(SORT_ORDER_KEY, TOP_RATED_KEY);
                editor.commit();

                if (isNetworkAvailable()) {
                    callTopRated();
                } else {
                    showAlert();
                }
                break;
            }
            case R.id.fav_menuId: {
                editor.putInt(SORT_ORDER_KEY, FAV_KEY);
                editor.commit();

                getFavourates();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void parseAndSetRecyclerview(String object) {
        MoviesListAdapter adapter = new MoviesListAdapter(this,
                JsonUtils.parseMoviesInfoJson(object));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }
}
