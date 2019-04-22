package com.example.admin.moviesappstageone;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class MovieRepository {
    private MovieDAO mMovieDao;
    private LiveData<List<MoviesInfo>> mAllWords;

    MovieRepository(Application application) {
        MoviesDatabase db = MoviesDatabase.getDatabase(application);
        mMovieDao = db.myMovieDaoMehod();
        mAllWords = mMovieDao.getAllMovies();
    }

    LiveData<List<MoviesInfo>> getAllWords() {
        return mAllWords;
    }

    void insert(MoviesInfo word) {
        new insertAsyncTask(mMovieDao).execute(word);
    }
    void delete(MoviesInfo word) {
        new deleteAsyncTask(mMovieDao).execute(word);
    }

    boolean checkFav(int id) {
        return mMovieDao.checkFav(id);
    }

    private static class insertAsyncTask extends AsyncTask<MoviesInfo, Void, Void> {

        private MovieDAO mAsyncTaskDao;

        insertAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoviesInfo... params) {
            mAsyncTaskDao.saveAsFavourates(params[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<MoviesInfo, Void, Void> {

        private MovieDAO mAsyncTaskDao;

        deleteAsyncTask(MovieDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoviesInfo... params) {
            mAsyncTaskDao.removeFromFavourates(params[0]);
            return null;
        }
    }
}
