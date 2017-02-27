package net.arvin.androidart.retrofit;

import net.arvin.androidart.entities.GitHubRepos;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * created by arvin on 17/2/27 20:05
 * email：1035407623@qq.com
 */
public interface ArtApi {
    String baseUrl = "https://api.github.com/";

    @GET("users/arvinljw/repos")
    Observable<List<GitHubRepos>> repos(@Query("page") int page,@Query("per_page")int per_page);
}
