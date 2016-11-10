package ckl.lfspersson.ckl;

/**
 * Created by LFSPersson on 09/11/16.
 */

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;

public interface RestService {

    @GET("/challenge")
    Call<List<ArticleModel>> getArticles();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
