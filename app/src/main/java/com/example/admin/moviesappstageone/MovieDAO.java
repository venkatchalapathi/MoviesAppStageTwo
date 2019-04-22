package com.example.admin.moviesappstageone;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by ADMIN on 12/5/2018.
 */
@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movies_table")
    LiveData<List<MoviesInfo>> getAllMovies();

    @Query("SELECT * FROM movies_table WHERE id = :idd")
    boolean checkFav(int idd);

    @Insert
    void saveAsFavourates(MoviesInfo moviesInfo);

    @Delete
    void removeFromFavourates(MoviesInfo moviesInfo);
}