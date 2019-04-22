package com.example.admin.moviesappstageone;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/4/2018.
 */

class JsonUtils {

    private static String RESULT = "results";
    private static String VOTE_COUNT = "vote_count";
    private static String ID = "id";
    private static String VIDEO = "video";
    private static String VOTE_AVG = "vote_average";
    private static String TITLE = "title";
    private static String POPULARITY = "popularity";
    private static String POSTER_PATH = "poster_path";
    private static String BACKDROP_PATH = "backdrop_path";
    private static String ORIGINAL_LANGUAGE = "original_language";
    private static String ORIGINAL_TITLE = "original_title";
    private static String ADULT = "adult";
    private static String OVERVIEW = "overview";
    private static String RELEASE_DATE = "release_date";

    private static String ISO_639_1 = "iso_639_1";
    private static String ISO_3166_1 = "iso_3166_1";
    private static String KEY = "key";
    private static String NAME = "name";
    private static String SITE = "site";
    private static String SIZE = "size";
    private static String TYPE = "type";
    private static String AUTHOR = "author";
    private static String CONTENT = "content";
    private static String URL = "url";


    public static ArrayList<MoviesInfo> parseMoviesInfoJson(String s) {
        ArrayList<MoviesInfo> movieData = new ArrayList<>();
        if (s != null) {
            try {
                JSONObject json = new JSONObject(s);
                JSONArray results = json.getJSONArray(RESULT);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject position = results.getJSONObject(i);
                    String vote_count = position.getString(VOTE_COUNT);
                    int id = position.getInt(ID);

                    String video = position.optString(VIDEO);
                    double vote_average = position.optDouble(VOTE_AVG);
                    String title = position.optString(TITLE);
                    String popularity = position.optString(POPULARITY);
                    String poster_path = position.optString(POSTER_PATH);//"https://image.tmdb.org/t/p/w500"+
                    String backdrop_path = position.optString(BACKDROP_PATH);
                    String original_language = position.optString(ORIGINAL_LANGUAGE);
                    String original_title = position.optString(ORIGINAL_TITLE);
                    String adult = position.optString(ADULT);
                    String overview = position.optString(OVERVIEW);
                    String release_date = position.optString(RELEASE_DATE);
                    MoviesInfo mainData = new MoviesInfo();
                    mainData.setVote_count(vote_count);
                    mainData.setId(id);
                    mainData.setVideo(video);
                    mainData.setVote_average(vote_average);
                    mainData.setTitle(title);
                    mainData.setPopularity(popularity);
                    mainData.setPoster_path(poster_path);
                    mainData.setBackdrop_path(backdrop_path);
                    mainData.setOriginal_language(original_language);
                    mainData.setOriginal_title(original_title);
                    mainData.setAdult(adult);
                    mainData.setOverview(overview);
                    mainData.setRelease_date(release_date);
                    movieData.add(mainData);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return movieData;
    }

    public static ArrayList<TrailerInfo> parseTrailerInfoJson(String s) {
        ArrayList<TrailerInfo> list = new ArrayList<>();
        if (s != null) {
            try {
                JSONObject json = new JSONObject(s);
                JSONArray results = json.getJSONArray(RESULT);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject position = results.getJSONObject(i);

                    String id = position.optString(ID);
                    String iso_639_1 = position.optString(ISO_639_1);
                    String iso_3166_1 = position.optString(ISO_3166_1);
                    String key = position.optString(KEY);
                    String name = position.optString(NAME);
                    String site = position.optString(SITE);
                    String size = position.optString(SIZE);
                    String type = position.optString(TYPE);

                    TrailerInfo info = new TrailerInfo();
                    info.setId(id);
                    info.setIso_639_1(iso_639_1);
                    info.setIso_3166_1(iso_3166_1);
                    info.setKey(key);
                    info.setName(name);
                    info.setSite(site);
                    info.setSize(size);
                    info.setType(type);

                    list.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static ArrayList<ReviewInfo> parseReviewInfoJson(String s) {
        ArrayList<ReviewInfo> list = new ArrayList<>();
        if (s != null) {
            try {
                JSONObject json = new JSONObject(s);
                JSONArray results = json.getJSONArray(RESULT);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject position = results.getJSONObject(i);
                    String author = position.optString(AUTHOR);
                    String content = position.optString(CONTENT);
                    String id = position.optString(ID);
                    String url = position.optString(URL);

                    ReviewInfo info = new ReviewInfo();
                    info.setAuthor(author);
                    info.setContent(content);
                    info.setId(id);
                    info.setUrl(url);

                    list.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
