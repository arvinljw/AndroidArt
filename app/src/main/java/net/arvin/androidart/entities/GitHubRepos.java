package net.arvin.androidart.entities;

/**
 * created by arvin on 17/2/27 20:13
 * email：1035407623@qq.com
 */
public class GitHubRepos {
    private String name;
    private String full_name;
    private GitHubUserEntity owner;
    private int stargazers_count;
    private int forks_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public GitHubUserEntity getOwner() {
        return owner;
    }

    public void setOwner(GitHubUserEntity owner) {
        this.owner = owner;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getForks_count() {
        return forks_count;
    }

    public void setForks_count(int forks_count) {
        this.forks_count = forks_count;
    }
}
