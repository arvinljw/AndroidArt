package net.arvin.androidart.retrofit;

import android.support.v7.widget.RecyclerView;

import net.arvin.afbaselibrary.nets.callbacks.AbsAPICallback;
import net.arvin.afbaselibrary.nets.exceptions.ApiException;
import net.arvin.afbaselibrary.uis.activities.BaseRefreshLoadingActivity;
import net.arvin.afbaselibrary.uis.adapters.BaseAdapter;
import net.arvin.afbaselibrary.uis.adapters.MultiItemTypeAdapter;
import net.arvin.afbaselibrary.uis.adapters.holders.CommonHolder;
import net.arvin.androidart.R;
import net.arvin.androidart.entities.GitHubRepos;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 17/2/27 20:28
 * email：1035407623@qq.com
 */
public class GithubReposActivity extends BaseRefreshLoadingActivity<GitHubRepos> {
    @Override
    protected MultiItemTypeAdapter<GitHubRepos> getAdapter() {
        return new BaseAdapter<GitHubRepos>(this, R.layout.item_repos, mItems) {
            @Override
            protected void convert(CommonHolder holder, GitHubRepos item, int position) {
                holder.setText(R.id.tv_full_name, item.getFull_name());
                holder.setText(R.id.tv_star_count, "star：" + item.getStargazers_count() + "        fork：" + item.getForks_count());
            }
        };
    }

    @Override
    protected void loadData(final int page) {
        ArtNet.getInstance().getApi().repos(page, DEFAULT_SIZE).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<GitHubRepos>>() {
                    @Override
                    protected void onResultError(ApiException ex) {
                        showToast(ex.getDisplayMessage());
                        refreshComplete(false);
                    }

                    @Override
                    public void onNext(List<GitHubRepos> gitHubRepos) {
                        if (page == FIRST_PAGE) {
                            mItems.clear();
                        }
                        mItems.addAll(gitHubRepos);
                        refreshComplete(gitHubRepos.size() > 0);
                    }
                });
    }

    @Override
    protected String getTitleText() {
        return "arvinljw的github仓库";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_github_repos;
    }
}
