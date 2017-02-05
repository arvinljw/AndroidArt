package net.arvin.afbaselibrary.nets.entities;

import java.util.List;

/**
 * created by arvin on 16/8/3 14:48
 * email：1035407623@qq.com
 */
public class PageListEntity<T> {
    private List<T> content;

    public PageListEntity() {
    }

    public PageListEntity(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
