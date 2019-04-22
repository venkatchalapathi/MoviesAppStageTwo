package com.example.admin.moviesappstageone;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by ADMIN on 12/4/2018.
 */
@Entity(tableName = "movies_table")
class MoviesInfo implements Parcelable{
    private String vote_count;

    @PrimaryKey
    private int id;

    private String video;
    private double vote_average;
    private String title;
    private String popularity;
    private String poster_path;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private String adult;
    private String overview;
    private String release_date;


    public MoviesInfo() {

    }

    protected MoviesInfo(Parcel in) {
        vote_count = in.readString();
        id = in.readInt();
        video = in.readString();
        vote_average = in.readDouble();
        title = in.readString();
        popularity = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        adult = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<MoviesInfo> CREATOR = new Creator<MoviesInfo>() {
        @Override
        public MoviesInfo createFromParcel(Parcel in) {
            return new MoviesInfo(in);
        }

        @Override
        public MoviesInfo[] newArray(int size) {
            return new MoviesInfo[size];
        }
    };

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(vote_count);
        parcel.writeInt(id);
        parcel.writeString(video);
        parcel.writeDouble(vote_average);
        parcel.writeString(title);
        parcel.writeString(popularity);
        parcel.writeString(poster_path);
        parcel.writeString(backdrop_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeString(adult);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }
}