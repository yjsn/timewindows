package com.singgo.cn.timewindows.retrofit;


import com.singgo.cn.timewindows.mvp.biz.IGankBiz;
import com.singgo.cn.timewindows.mvp.biz.IUserBiz;
import com.singgo.cn.timewindows.mvp.biz.IdbMovieBiz;
import com.singgo.cn.timewindows.retrofit.http.RetrofitClient;

/**
 * author: baiiu
 * date: on 16/5/16 17:42
 * description:
 */
public enum ApiFactory {
  INSTANCE;

  private static IUserBiz userBiz;
  private static GitHubAPI git;
  private static IdbMovieBiz movieBiz;
  private static IGankBiz gankBiz;

  ApiFactory() {
  }

  public static IUserBiz getUserApi() {
    if (userBiz == null) {
      userBiz = RetrofitClient.INSTANCE.getRetrofit().create(IUserBiz.class);
    }
    return userBiz;
  }

  public static IdbMovieBiz getDBApi(){
    if(movieBiz == null){
      movieBiz = RetrofitClient.INSTANCE.getRetrofit().create(IdbMovieBiz.class);
    }
    return movieBiz;
  }

  public static IGankBiz getGinkApi(){
    if(gankBiz == null){
      gankBiz = RetrofitClient.GANK.getRetrofit().create(IGankBiz.class);
    }
    return gankBiz;
  }

  public static GitHubAPI get(){
    if (git == null) {
      git = RetrofitClient.INSTANCE.getRetrofit().create(GitHubAPI.class);
    }
    return git;
  }

}
