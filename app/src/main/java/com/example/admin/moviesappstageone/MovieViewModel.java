package com.example.admin.moviesappstageone;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by ADMIN on 12/5/2018.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<List<MoviesInfo>> list;

   public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        list = mRepository.getAllWords();

    }

    public LiveData<List<MoviesInfo>> getList() {
        return list;
    }
    void insert(MoviesInfo moviesInfo){
        mRepository.insert(moviesInfo);
    }
    void delete(MoviesInfo moviesInfo){
        mRepository.delete(moviesInfo);
    }

    public boolean checkFav(int id) {
        return mRepository.checkFav(id);
    }
}
