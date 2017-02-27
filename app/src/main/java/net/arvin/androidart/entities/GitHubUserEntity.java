package net.arvin.androidart.entities;

/**
 * created by arvin on 17/2/27 20:14
 * email：1035407623@qq.com
 */
public class GitHubUserEntity {
    private String login;
    private String url;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
