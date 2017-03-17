package com.formation.cdb.ui;

import java.util.ArrayList;
import java.util.List;

public abstract class Page<T> {
    int maxInPage = 0;
    int indexMaPage = 0;
    List<T> list = new ArrayList<T>();

    public int getMaxInPages() {
        return maxInPage;
    }

    public void setMaxInPages(int maxPages) {
        this.maxInPage = maxPages;
    }

    public int getIndexMaPage() {
        return indexMaPage;
    }

    public void setIndexMaPage(int indexMaPage) {
        this.indexMaPage = indexMaPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Page [maxPages=" + maxInPage + ", indexMaPage=" + indexMaPage + ", list=" + list + "]";
    }
}
