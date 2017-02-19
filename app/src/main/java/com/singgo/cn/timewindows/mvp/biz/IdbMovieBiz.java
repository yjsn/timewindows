package com.singgo.cn.timewindows.mvp.biz;

import com.singgo.cn.timewindows.mvp.bean.Celebrity;
import com.singgo.cn.timewindows.mvp.bean.Code;
import com.singgo.cn.timewindows.mvp.bean.MovieList;
import com.singgo.cn.timewindows.mvp.bean.Subjects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hxz on 2016/12/8.
 */

public interface IdbMovieBiz {
    /**
    获取近期热映的电影
    count 单页数
    start 开始个数
     */
    @GET("movie/in_theaters")
    Call<MovieList> getInTheaters(@Query("count") int count, @Query("start") int start);

    /**
     *获得即将上映电影
     * @param count
     * @param start
     */
    @GET("movie/coming_soon")
    Call<MovieList> getComingSoon(@Query("count") int count, @Query("start") int start);

    @GET("movie/top250")
    Call<MovieList> getTop250(@Query("count") int count, @Query("start") int start);

    @GET("movie/subject/{id}")
    Call<Subjects> getMovie(@Path("id")String id);

    @GET("movie/celebrity/{id}")
    Call<Celebrity> getCelebrity(@Path("id")String id);

    @GET("movie/search")
    Call<MovieList> getSearchList(@Query("q") String search,@Query("count") int count, @Query("start") int start);

    @GET("movie/search")
    Call<MovieList> getTagList(@Query("tag")String tag,@Query("count") int count, @Query("start") int start);

}
